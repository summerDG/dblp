package org.pasalab.experiment.publications;

import org.pasalab.experiment.fields.*;
import org.pasalab.experiment.utils.CDATA;

import java.util.ArrayList;
import java.util.List;

public abstract class Publication {
    protected CDATA key;
    protected CDATA mdate;
    protected CDATA publitype;
    protected CDATA cdate;
    protected List<Field> fields=new ArrayList<Field>();
    /**
     * author->0
     * cite->1
     * pages->2
     * title->3
     * year->4
     */
    protected List<Integer>[] map;
    {
        map = new List[5];
        for (int i = 0; i < 5; i++) {
            map[i] = new ArrayList<Integer>();
        }
    }

    public CDATA getKey() {
        return key;
    }

    public CDATA getMdate() {
        return mdate;
    }

    public CDATA getPublitype() {
        return publitype;
    }

    public CDATA getCdate() {
        return cdate;
    }

    public List<Author> getAuthors() {
        List<Author> authors = new ArrayList<Author>();
        for (int i : map[0]) {
            Author a = (Author)(fields.get(i));
            authors.add(a);
        }
        return authors;
    }
    public List<Cite> getCites() {
        List<Cite> cites = new ArrayList<Cite>();
        for (int i : map[1]) {
            Cite c = (Cite)(fields.get(i));
            cites.add(c);
        }
        return cites;
    }
    public Pages getPages() {
        if (map[2].size() > 0) {
            Pages t = (Pages) (fields.get(map[2].get(0)));
            return t;
        } else return null;
    }
    public Title getTitle() {
        if (map[3].size() > 0) {
            Title t = (Title) (fields.get(map[3].get(0)));
            return t;
        } else return null;
    }
    public Year getYear() {
        if (map[4].size() > 0) {
            Year t = (Year) (fields.get(map[4].get(0)));
            return t;
        } else return null;
    }

    public void setKey(CDATA key) {
        this.key = key;
    }
    public void setMdate(CDATA mdate){
        this.mdate = mdate;
    }
    public void setPublitype(CDATA publitype) {
        this.publitype = publitype;
    }
    public void setCdate(CDATA cdate) {
        this.cdate = cdate;
    }
    public void addField(Field field) {
        int id = fields.size();
        if (field instanceof Author) {
            map[0].add(id);
        } else if (field instanceof Cite) {
            map[1].add(id);

        } else if (field instanceof Pages) {
            map[2].add(id);

        } else if (field instanceof Title) {
            map[3].add(id);

        } else if (field instanceof Year) {
            map[4].add(id);
        }
        fields.add(field);
    }
    abstract public void set(String attr, String value);
}
