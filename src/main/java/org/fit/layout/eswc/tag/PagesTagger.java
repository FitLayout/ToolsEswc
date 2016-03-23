/**
 * PagesTagger.java
 *
 * Created on 28. 2. 2015, 21:02:19 by burgetr
 */
package org.fit.layout.eswc.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fit.layout.classify.TextTag;
import org.fit.layout.classify.taggers.BaseTagger;
import org.fit.layout.model.Area;
import org.fit.layout.model.Tag;

/**
 * Very simple page numbers tagger. Recognizes the numeric ranges (e.g. 12-24)
 * @author burgetr
 */
public class PagesTagger extends BaseTagger
{
    private static final float YES = 0.95f;
    private static final float NO = 0.0f;
    
    protected Pattern pgexpr = Pattern.compile("[1-9][0-9]*(\\s*\\p{Pd}\\s*[1-9][0-9]*)?");
    
    @Override
    public String getId()
    {
        return "ESWC.Tag.Pages";
    }

    @Override
    public String getName()
    {
        return "Page ranges";
    }

    @Override
    public String getDescription()
    {
        return "Very simple page numbers tagger. Recognizes the numeric ranges (e.g. 12-24)";
    }
    
    public TextTag getTag()
    {
        return new TextTag("pages", this);
    }

    public float belongsTo(Area node)
    {
        if (node.isLeaf())
        {
            String text = node.getText();
            Matcher match = pgexpr.matcher(text);
            while (match.find())
            {
                final int ms = match.start();
                final int me = match.end();
                if ((ms == 0 || text.charAt(ms) == ' ') &&
                    (me == text.length() || text.charAt(ms) == ' '))
                {
                    return YES;
                }
            }
        }
        return NO;
    }
    
    public boolean allowsContinuation(Area node)
    {
        return false;
    }

    public boolean allowsJoining()
    {
        return false;
    }
    
    public boolean mayCoexistWith(Tag other)
    {
        return true;
    }
    
    public List<String> extract(String src)
    {
        Vector<String> ret = new Vector<String>();
        
        Matcher match = pgexpr.matcher(src);
        while (match.find())
        {
            String s = match.group();
            ret.add(s);
        }
        
        return ret;
    }
    
    @Override
    public List<String> split(String src)
    {
        // TODO splitting is not implemented for this tagger; the whole string is returned
        List<String> ret = new ArrayList<String>(1);
        ret.add(src);
        return ret;
    }

}
