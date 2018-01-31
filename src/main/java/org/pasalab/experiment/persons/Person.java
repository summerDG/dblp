package org.pasalab.experiment.persons;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

/**
 * Person有三个属性: name, urlpt, id
 */
public class Person {

    private String name;
    private String urlpt;
    private int id;
    private static Map<String, Integer> nameToId = new HashMap<String, Integer>();

    /*
     * Create a new Person object.
     */
    public Person(String name, String urlpt) {
        this.name = name;
        this.urlpt = urlpt;
        this.id = nameToId.size();
        nameToId.put(name, id);
    }

    public static boolean created(String name) {
        return nameToId.containsKey(name);
    }



    /*
     * Coauthor information is loaded on demand only
     */

    private boolean coauthorsLoaded;
    private String[] coauthors;

    static private SAXParser coauthorParser;
    static private CAConfigHandler coauthorHandler;
    static private List<String> plist = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public String[] getCoauthors() {
        if (!coauthorsLoaded) {
            loadCoauthors();
        }
        return coauthors;
    }

    public int[] getCoauthorIds() {
        String[] ca = getCoauthors();
        int[] ids = new int[ca.length];
        int i = 0;
        for (String c : ca) {
            ids[i++] = nameToId.get(c);
        }

        return ids;
    }
    static private class CAConfigHandler extends DefaultHandler {

        private String Value, urlpt;

        private boolean insideAuthor;

        public void startElement(String namespaceURI, String localName,
                                 String rawName, Attributes atts) throws SAXException {
            if (insideAuthor = rawName.equals("author")) {
                Value = "";
                urlpt = atts.getValue("urlpt");
            }
        }

        public void endElement(String namespaceURI, String localName,
                               String rawName) throws SAXException {
            if (rawName.equals("author") && Value.length() > 0) {
                plist.add(Value);
                /* System.out.println(p + "   " + urlpt + "   " + plist.size()); */
            }
        }
        public void characters(char[] ch, int start, int length)
                throws SAXException {

            if (insideAuthor)
                Value += new String(ch, start, length);
        }
    }

    static {
        try {
            coauthorParser = SAXParserFactory.newInstance().newSAXParser();

            coauthorHandler = new CAConfigHandler();
            coauthorParser.getXMLReader().setFeature(
                    "http://xml.org/sax/features/validation", false);

        } catch (ParserConfigurationException e) {
            System.out.println("Error in XML parser configuration: "
                    + e.getMessage());
            System.exit(1);
        } catch (SAXException e) {
            System.out.println("Error in parsing: " + e.getMessage());
            System.exit(2);
        }
    }

    private void loadCoauthors() {
        if (coauthorsLoaded)
            return;
        plist.clear();
        try {
            URL u = new URL("http://dblp.uni-trier.de/rec/pers/" + urlpt
                    + "/xc");
            coauthorParser.parse(u.openStream(), coauthorHandler);
        } catch (IOException e) {
            System.out.println("Error reading URI: " + e.getMessage());
            coauthors = new String[0];
            return;
        } catch (SAXException e) {
            System.out.println("Error in parsing: " + name + " "+ e.getMessage());
            coauthors = new String[0];
            return;
        }
        coauthors = new String[plist.size()];
        coauthors = plist.toArray(coauthors);
        coauthorsLoaded = true;
    }

    @Override
    public String toString() {
        return name;
    }
}
