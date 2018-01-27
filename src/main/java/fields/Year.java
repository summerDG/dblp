package fields;

import org.xml.sax.Attributes;
import utils.CDATA;
import utils.PCDATA;

public class Year extends Field {
    private CDATA aux;
    private CDATA label;
    private CDATA type;
    public Year(PCDATA key) {
        super(key);
    }
    public Year(Attributes attributes) {
        super(attributes);
    }

    public CDATA getAux() {
        return aux;
    }

    public void setAux(CDATA aux) {
        this.aux = aux;
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
        } else if (attr.equals("label")) {
            setLabel(new CDATA(value));
        } else if (attr.equals("type")) {
            setType(new CDATA(value));
        }
    }
}
