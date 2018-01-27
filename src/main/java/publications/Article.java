package publications;

import org.xml.sax.Attributes;
import utils.CDATA;

import java.util.HashSet;
import java.util.Set;

public class Article extends Publication {
    protected CDATA reviewid;
    protected CDATA rating;

    public static Set<String> attributes = new HashSet<String>();
    static {
        attributes.add("key");
        attributes.add("mdate");
        attributes.add("publtype");
        attributes.add("reviewid");
        attributes.add("rating");
        attributes.add("cdate");
    }

    /**
     * author->0
     * cite->1
     * pages->2
     * title->3
     * year->4
     *
     * @param key
     */
    public Article(CDATA key) {
        this.key = key;
    }

    public Article(Attributes attributes) {
        for (int i =0; i < attributes.getLength(); i++) {
            String localName = attributes.getLocalName(i);
            if (Article.attributes.contains(localName)) {
                set(localName, attributes.getValue(i));
            }
        }
    }

    public CDATA getReviewid() {
        return reviewid;
    }

    public void setReviewid(CDATA reviewid) {
        this.reviewid = reviewid;
    }

    public CDATA getRating() {
        return rating;
    }

    public void setRating(CDATA rating) {
        this.rating = rating;
    }

    public void set(String attr, String value) {
        if (attr.equals("key")) {
            setKey(new CDATA(value));
        } else if (attr.equals("mdate")) {
            setMdate(new CDATA(value));
        } else if (attr.equals("publtype")) {
            setPublitype(new CDATA(value));
        } else if (attr.equals("reviewid")) {
            setReviewid(new CDATA(value));
        } else if (attr.equals("rating")) {
            setRating(new CDATA(value));
        } else if (attr.equals("cdate")) {
            setCdate(new CDATA(value));
        }
    }
}
