package org.pasalab.experiment.fields;

import org.apache.commons.lang3.StringEscapeUtils;
import org.xml.sax.Attributes;
import org.pasalab.experiment.utils.CDATA;
import org.pasalab.experiment.utils.PCDATA;

public class Author extends Field {
    private CDATA aux;
    private CDATA bibtex;
    private CDATA orcid;
    private CDATA label;
    private CDATA type;
    public Author(PCDATA key) {
        super(key);
    }
    public Author(Author o) {
        super(o.key);
        this.aux = o.aux;
        this.bibtex = o.bibtex;
        this.orcid = o.orcid;
        this.label = o.label;
        this.type = o.type;
    }
    public Author(Attributes attributes) {
        super(attributes);
    }

    public CDATA getAux() {
        return aux;
    }

    public void setAux(CDATA aux) {
        this.aux = aux;
    }

    public CDATA getBibtex() {
        return bibtex;
    }

    public void setBibtex(CDATA bibtex) {
        this.bibtex = bibtex;
    }

    public CDATA getOrcid() {
        return orcid;
    }

    public void setOrcid(CDATA orcid) {
        this.orcid = orcid;
    }

    public CDATA getLabel() {
        return label;
    }

    public void setLabel(CDATA label) {
        this.label = label;
    }

    public CDATA getType() {
        return type;
    }

    public void setType(CDATA type) {
        this.type = type;
    }

    public void set(String attr, String value) {
        if (attr.equals("aux")) {
            setAux(new CDATA(value));
        } else if (attr.equals("bibtex")) {
            setBibtex(new CDATA(value));
        } else if (attr.equals("orcid")) {
            setOrcid(new CDATA(value));
        } else if (attr.equals("label")) {
            setLabel(new CDATA(value));
        } else if (attr.equals("type")) {
            setType(new CDATA(value));
        }
    }

    @Override
    public String toString() {
        return key.toString();
    }

    public String getUrlpt() {
        return toUrlpt(toHtml(key.toString()));
    }

    private String toUrlpt(String name) {
        int i = getFirstNameIndex(name);
        StringBuilder builder = new StringBuilder();
        String lastName;
        if (i < 0) {
            lastName = format(name);
            builder.append(Character.toLowerCase(lastName.charAt(0)));
            builder.append('/');
        } else {
            String firstName = format(name.substring(i + 1));
            builder.append(Character.toLowerCase(firstName.charAt(0)));
            builder.append('/');
            builder.append(firstName);
            builder.append(":");
            lastName = format(name.substring(0, i));
        }
        builder.append(lastName);
        return builder.toString();
    }
    private String format(String s) {
        return s.replaceAll("(\\.|;|-|&|')", "=").replace(' ', '_');
    }
    private int getFirstNameIndex(String name) {
        if (name.length() < 1) return -1;
        for (int i = name.length() - 2; i >= 0; i--) {
            char c = name.charAt(i);
            char next = name.charAt(i + 1);
            if (c == ' ' && !isDisgit(next)) {
                return i;
            }
        }
        return -1;
    }
    private String toHtml(String s) {
        return StringEscapeUtils.escapeHtml4(s);
    }

    private boolean isDisgit(char c) {
        return c >= '0' && c <= '9';
    }
}
