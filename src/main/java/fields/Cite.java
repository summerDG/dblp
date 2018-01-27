package fields;

import org.xml.sax.Attributes;
import utils.CDATA;
import utils.PCDATA;

public class Cite extends Field {
    private CDATA aux;
    private CDATA ref;
    private CDATA label;
    private CDATA type;
    public Cite(PCDATA key) {
        super(key);
    }
    public Cite(Attributes attributes) {
        super(attributes);
    }

    public CDATA getAux() {
        return aux;
    }

    public void setAux(CDATA aux) {
        this.aux = aux;
    }

    public CDATA getRef() {
        return ref;
    }

    public void setRef(CDATA ref) {
        this.ref = ref;
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
        } else if (attr.equals("ref")) {
            setRef(new CDATA(value));
        }  else if (attr.equals("label")) {
            setLabel(new CDATA(value));
        } else if (attr.equals("type")) {
            setType(new CDATA(value));
        }
    }
}
