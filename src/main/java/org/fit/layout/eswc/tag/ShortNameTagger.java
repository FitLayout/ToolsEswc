/**
 * ShortNameTagger.java
 *
 * Created on 10. 4. 2015, 11:36:13 by burgetr
 */
package org.fit.layout.eswc.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.fit.layout.classify.TextTag;
import org.fit.layout.classify.taggers.BaseTagger;
import org.fit.layout.eswc.op.AreaUtils;
import org.fit.layout.model.Area;
import org.fit.layout.model.Tag;

/**
 * 
 * @author burgetr
 */
public class ShortNameTagger extends BaseTagger
{
    
    @Override
    public String getId()
    {
        return "ESWC.ShortName";
    }

    @Override
    public String getName()
    {
        return "Short names";
    }

    @Override
    public String getDescription()
    {
        return "";
    }
    
    public TextTag getTag()
    {
        return new TextTag("short", this);
    }

    public double getRelevance()
    {
        return 0.7;
    }
    
    public boolean belongsTo(Area node)
    {
        if (node.isLeaf())
        {
            return !AreaUtils.findShortTitles(node).isEmpty();
        }
        return false;
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
    
    public Vector<String> extract(String src)
    {
        return AreaUtils.findShortTitles(src);
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
