package fields;

import org.xml.sax.Attributes;
import utils.CDATA;
import utils.PCDATA;

public class Author extends Field {
    private CDATA aux;
    private CDATA bibtex;
    private CDATA orcid;
    private CDATA label;
    private CDATA type;
    public Author(PCDATA key) {
        super(key);
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
}
