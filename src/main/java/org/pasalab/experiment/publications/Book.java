package org.pasalab.experiment.publications;

import org.xml.sax.Attributes;
import org.pasalab.experiment.utils.CDATA;

import java.util.HashSet;
import java.util.Set;

public class Book extends Publication {
    public static Set<String> attributes = new HashSet<String>();
    static {
        attributes.add("key");
        attributes.add("mdate");
        attributes.add("publtype");
        attributes.add("cdate");
    }

    public Book(CDATA key) {
        this.key = key;
    }

    public Book(Attributes attributes) {
        for (int i =0; i < attributes.getLength(); i++) {
            String localName = attributes.getLocalName(i);
            if (Article.attributes.contains(localName)) {
                set(localName, attributes.getValue(i));
            }
        }
    }


    public void set(String attr, String value) {
        if (attr.equals("key")) {
            setKey(new CDATA(value));
        } else if (attr.equals("mdate")) {
            setMdate(new CDATA(value));
        } else if (attr.equals("publtype")) {
            setPublitype(new CDATA(value));
        } else if (attr.equals("cdate")) {
            setCdate(new CDATA(value));
        }
    }
}
