package org.pasalab.experiment.examples;

import org.pasalab.experiment.publications.PublicationHandler;
import org.pasalab.experiment.utils.Schema;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PublicationToAuthor {
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
            pMap.put("key", "key");
            pMap.put("author", "authors");
            PublicationHandler publicationHandler = new PublicationHandler(
                    new Schema(pMap, "org.pasalab.experiment.publications.Publication"), outFile);
            parser.parse(in, publicationHandler);

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
