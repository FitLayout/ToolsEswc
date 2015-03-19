/**
 * FindEditorsOperator.java
 *
 * Created on 6. 3. 2015, 15:13:45 by burgetr
 */
package org.fit.layout.eswc.op;

import java.util.Vector;

import org.fit.layout.classify.StyleCounter;
import org.fit.layout.impl.BaseOperator;
import org.fit.layout.impl.DefaultTag;
import org.fit.layout.model.Area;
import org.fit.layout.model.AreaTree;
import org.fit.layout.model.Rectangular;
import org.fit.layout.model.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Finds the editor names in the given area of the page.
 * 
 * @author burgetr
 */
public class FindEditorsOperator extends BaseOperator
{
    private static Logger log = LoggerFactory.getLogger(FindEditorsOperator.class);
    private static final String TT = "FitLayout.TextTag";
    
    private final String[] paramNames = {};
    private final ValueType[] paramTypes = {};

    private Rectangular bounds;
    
    
    public FindEditorsOperator()
    {
        this(0, 300, 1200, 600); //just a guess
    }
    
    public FindEditorsOperator(int x1, int y1, int x2, int y2)
    {
        bounds = new Rectangular(x1, y1, x2, y2);
    }
    
    public FindEditorsOperator(Rectangular r)
    {
        bounds = new Rectangular(r);
    }
    
    @Override
    public String getId()
    {
        return "Eswc.Tag.Editors";
    }
    
    @Override
    public String getName()
    {
        return "Tag workshop editors";
    }

    @Override
    public String getDescription()
    {
        return "";
    }

    @Override
    public String[] getParamNames()
    {
        return paramNames;
    }

    @Override
    public ValueType[] getParamTypes()
    {
        return paramTypes;
    }

    public Rectangular getBounds()
    {
        return bounds;
    }

    //==============================================================================

    @Override
    public void apply(AreaTree atree)
    {
        apply(atree, atree.getRoot());
    }

    @Override
    public void apply(AreaTree atree, Area root)
    {
        //find tagged names in the area
        Vector<Area> names = new Vector<Area>();
        Tag tag = new DefaultTag(TT, "persons");
        float support = 0.5f;
        findTagsInArea(root, bounds, tag, support, true, names);
        /*for (Area a : names)
            System.out.println("name: " + a);*/
        
        if (names.isEmpty()) //no names found -- try again with lower support (to obey some uncertain hints)
        {
            support = 0.25f;
            findTagsInArea(root, bounds, tag, support, true, names);
        }
        
        if (!names.isEmpty())
        {
            //find the group containing the last name discovered
            Vector<Area> leaves = new Vector<Area>();
            findLeavesInArea(root, bounds, leaves);
            int last = leaves.indexOf(names.lastElement());
            
            //go until the beginning of the group
            int first = last;
            Area prev = names.lastElement();
            for (int i = last - 1; i >= 0; i--)
            {
                Area cur = leaves.elementAt(i);
                if (AreaUtils.isNeighbor(cur, prev))
                {
                    first = i;
                    prev = cur;
                }
                //else continue because some of the previous boxes may be a neighbor too 
            }
            //go until the end of the group
            prev = names.lastElement();
            for (int i = last + 1; i < leaves.size(); i++)
            {
                Area cur = leaves.elementAt(i);
                if (AreaUtils.isNeighbor(cur, prev))
                {
                    last = i;
                    prev = cur;
                }
            }
            log.debug("Editors start: {} end: {}", leaves.elementAt(first), leaves.elementAt(last));

            //check if there are links to at least some author
            boolean authorsLinked = false;
            for (int i = first; i <= last; i++)
            {
                Area a = leaves.elementAt(i);
                if (a.hasTag(tag, support) && AreaUtils.isLink(a))
                {
                    authorsLinked = true;
                    break;
                }
            }
            
            //build statistics about names
            prev = null;
            int sameline = 0;
            int nextline = 0;
            int other = 0;
            int minx = -1;
            StyleCounter<FontNodeStyle> estyles = new StyleCounter<FontNodeStyle>();
            for (int i = first; i <= last; i++)
            {
                Area a = leaves.elementAt(i);
                //when some names are links, use only those for statistics 
                if (a.hasTag(tag, support) && (!authorsLinked || AreaUtils.isLink(a)))
                {
                    estyles.add(new FontNodeStyle(a));
                    final int x = a.getTopology().getPosition().getX1();
                    if (prev != null)
                    {
                        if (AreaUtils.isOnSameLine(prev, a))
                            sameline++;
                        else if (AreaUtils.isInSameColumn(prev, a))
                            nextline++;
                        else
                            other++;
                        if (x < minx)
                            minx = x;
                    }
                    else
                        minx = x;
                    prev = a;
                }
            }
            FontNodeStyle estyle = estyles.getMostFrequent();
            log.info("Layout: same line {}, next line {}, other {}, minx {}, style {}, linked {}", sameline, nextline, other, minx, estyle, authorsLinked);
            
            //tag the appropriate names
            for (int i = first; i <= last; i++)
            {
                Area a = leaves.elementAt(i);
                System.out.println("Test " + a);
                if (estyle.equals(new FontNodeStyle(a)))
                {
                    if (nextline >= sameline) //probably names on separate lines
                    {
                        if (a.getTopology().getPosition().getX1() == minx)
                            a.addTag(new EswcTag("veditor"), 0.7f);
                    }
                    else //multiple names on lines
                    {
                        //TODO some conditions?
                        a.addTag(new EswcTag("veditor"), 0.7f);
                    }
                }
            }
            
        }
        else
            log.warn("Could not find any names for editors!");
        
    }
    
    //==============================================================================
    
    private void findLeavesInArea(Area root, Rectangular limit, Vector<Area> dest) 
    {
        if (root.isLeaf() && getBounds().intersects(limit))
            dest.add(root);
        for (int i = 0; i < root.getChildCount(); i++)
            findLeavesInArea(root.getChildArea(i), limit, dest);
    }
    
    private void findTagsInArea(Area root, Rectangular limit, Tag tag, float minSupport, 
                                boolean startWithLetter, Vector<Area> dest) 
    {
        if (root.hasTag(tag, minSupport) && root.getBounds().intersects(limit))
        {
            if (startWithLetter)
            {
                String text = root.getText().trim();
                if (!text.isEmpty() && Character.isAlphabetic(text.codePointAt(0)))
                    dest.add(root);
            }
            else
                dest.add(root);
        }
        for (int i = 0; i < root.getChildCount(); i++)
            findTagsInArea(root.getChildArea(i), limit, tag, minSupport, startWithLetter, dest);
    }
    
    
}