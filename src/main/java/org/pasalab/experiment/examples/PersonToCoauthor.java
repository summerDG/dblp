package org.pasalab.experiment.examples;

import org.pasalab.experiment.persons.PersonHandler;
import org.pasalab.experiment.utils.Schema;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PersonToCoauthor {
    public static void main(String[] args) {
        String input = args[0];
        String output = args[1];
        File inFile = new File(input);
        File outFile = new File(output);
        if (!outFile.exists()) {
            try {
                outFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            InputStream in = new FileInputStream(inFile);
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            Map<String, String> pMap = new HashMap<String, String>();
            pMap.put("url", "urlpt");
            pMap.put("coauthor", "coauthors");
            PersonHandler personHandler = new PersonHandler(
                    new Schema(pMap, "org.pasalab.experiment.persons.Person"),
                    outFile);
            parser.parse(in, personHandler);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
