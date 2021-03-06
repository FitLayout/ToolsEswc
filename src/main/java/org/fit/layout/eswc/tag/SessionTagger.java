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
 *
 * @author burgetr
 */
public class SessionTagger extends BaseTagger
{
    private static final float YES = 0.6f;
    private static final float NO = 0.0f;

    protected final int MIN_WORDS = 2;
    /** The expression the whole area must start with */
    protected Pattern areaexpr = Pattern.compile("[A-Z0-9]"); //uppercase or number
    /** The expression describing the allowed title format */
    protected Pattern titleexpr = Pattern.compile("[A-Z][A-Za-z0-9\\s\\:\\-\\p{Pd}]*");  //p{Pd} ~ Unicode Punctuation-dashes category
    /** The expression describing the allowed format of the title continuation */
    protected Pattern contexpr = Pattern.compile("[A-Za-z\\s\\:\\-\\p{Pd}]+"); 

    /** Words that are required in the session title */
    protected Vector<String> whitelist;
    /** Words that are not allowed in the session title */
    protected Vector<String> blacklist;
    
    public SessionTagger()
    {
        whitelist = new Vector<String>();
        //whitelist.add("session");
        blacklist = new Vector<String>();
        blacklist.add("chair");
    }
    
    @Override
    public String getId()
    {
        return "ESWC.Tag.Session";
    }

    @Override
    public String getName()
    {
        return "Session titles";
    }

    @Override
    public String getDescription()
    {
        return "...";
    }

    public TextTag getTag()
    {
        return new TextTag("session", this);
    }

    public float belongsTo(Area node)
    {
        if (node.isLeaf())
        {
            String text = node.getText().trim();
            if (areaexpr.matcher(text).lookingAt()) //check the allowed text start
            {
                //check if there is a substring with the allowed format
                Matcher match = titleexpr.matcher(text);
                while (match.find())
                {
                    String s = match.group();
                    String[] words = s.split("\\s+");
                    if (words.length >= MIN_WORDS
                        && !containsListedWord(blacklist, words)
                        /*&& containsListedWord(whitelist, words)*/)
                        return YES;
                }
                return NO;
            }
        }
        return NO;
    }

    public boolean allowsContinuation(Area node)
    {
        if (node.isLeaf())
        {
            String text = node.getText().trim();
            if (contexpr.matcher(text).lookingAt()) //must start with the allowed format
            {
                return true;
            }
        }
        return false;
    }

    public boolean allowsJoining()
    {
        return true;
    }
    
    public boolean mayCoexistWith(Tag other)
    {
        return (!other.getValue().equals("persons") && !other.getValue().equals("title"));
    }
    
    public List<String> extract(String src)
    {
        Vector<String> ret = new Vector<String>();
        
        Matcher match = titleexpr.matcher(src);
        while (match.find())
        {
            String s = match.group();
            String[] words = s.split("\\s+");
            if (words.length >= MIN_WORDS)
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
    
    //=================================================================================================
    
    protected boolean containsListedWord(Vector<String> list, String[] words)
    {
        for (String w : words)
        {
            if (list.contains(w.toLowerCase()))
                return true; 
        }
        return false;
    }
}
