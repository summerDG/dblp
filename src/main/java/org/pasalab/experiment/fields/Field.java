package org.pasalab.experiment.fields;

import org.xml.sax.Attributes;
import org.pasalab.experiment.utils.PCDATA;

abstract public class Field {
    protected PCDATA key;
    public Field(PCDATA key) {
        this.key = key;
    }
    public Field(Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            String attr = attributes.getLocalName(i);
            String value = attributes.getValue(i);
            set(attr, value);
        }
    }
    public PCDATA getKey() {
        return this.key;
    }
    abstract public void set(String attr, String value);
    public void setKey(PCDATA key) {
        this.key = key;
    }
    @Override
    public String toString() {
        return key.toString();
    }
}
