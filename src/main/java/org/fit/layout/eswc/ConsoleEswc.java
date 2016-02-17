/**
 * ConsoleEswc.java
 *
 * Created on 26. 2. 2015, 15:46:07 by burgetr
 */
package org.fit.layout.eswc;

import java.io.IOException;

import javax.script.ScriptException;

import org.fit.layout.api.AreaTreeOperator;
import org.fit.layout.classify.InstanceExtractor;
import org.fit.layout.classify.op.TagEntitiesOperator;
import org.fit.layout.eswc.classify.ProgrammesFeatureExtractor;
import org.fit.layout.eswc.tag.CountriesTagger;
import org.fit.layout.eswc.tag.ShortNameTagger;
import org.fit.layout.tools.Console;

/**
 * 
 * @author burgetr
 */
public class ConsoleEswc extends Console
{
    private InstanceExtractor extractor;

    @Override
    protected void init()
    {
        super.init();
        //custom instance extractor for training data extraction
        extractor = new InstanceExtractor(new ProgrammesFeatureExtractor(), "CEUR");
        getProcessor().put("extr", extractor);
        //init custom taggers
        AreaTreeOperator tcls = getProcessor().getOperators().get("FitLayout.Tag.Entities");
        if (tcls != null && tcls instanceof TagEntitiesOperator)
        {
            ((TagEntitiesOperator) tcls).addTagger(new CountriesTagger());
            ((TagEntitiesOperator) tcls).addTagger(new ShortNameTagger());
        }
        else
            System.err.println("Couldn't configure FitLayout.Tag.Entities!");
        
    }

    @Override
    protected void initSession() throws ScriptException
    {
        super.initSession();
        getProcessor().execInternal("js/eswc_init.js");
    }

    public void browser()
    {
        BlockBrowserEswc.main(new String[0]);
    }
    
    public void dumpIndex(String destfile)
    {
        IndexFile.dumpIndex(destfile);
    }
    
    public String dumpIndex()
    {
        return IndexFile.dumpIndex();
    }
    
    public void dumpEditors(String destfile)
    {
        IndexFile.dumpEditors(destfile);
    }
    
    public String dumpEditors()
    {
        return IndexFile.dumpEditors();
    }
    
    public static void main(String[] args)
    {
        System.out.println("FitLayout interactive console [ESWC]");
        Console con = new ConsoleEswc();
        try
        {
            con.interactiveSession(System.in, System.out, System.err);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
