/**
 * CountriesTagger.java
 *
 * Created on 30. 3. 2015, 13:30:19 by burgetr
 */
package org.fit.layout.eswc.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.fit.layout.classify.TextTag;
import org.fit.layout.classify.taggers.BaseTagger;
import org.fit.layout.eswc.Countries;
import org.fit.layout.model.Area;
import org.fit.layout.model.Tag;

/**
 * 
 * @author burgetr
 */
public class CountriesTagger extends BaseTagger
{
    
    @Override
    public String getId()
    {
        return "ESWC.Country";
    }

    @Override
    public String getName()
    {
        return "Countries";
    }

    @Override
    public String getDescription()
    {
        return "";
    }
    
    public TextTag getTag()
    {
        return new TextTag("countries", this);
    }

    public double getRelevance()
    {
        return 0.95;
    }
    
    public boolean belongsTo(Area node)
    {
        if (node.isLeaf())
        {
            String text = node.getText();
            return !Countries.getCountryNames(text).isEmpty();
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
        return new Vector<String>(Countries.getCountryNames(src));
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
