package publications;

import fields.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import utils.PCDATA;
import utils.Schema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PublicationHandler extends DefaultHandler {
    private String publicationType;
    private Publication publication;
    private Field field;
    private String value;
    private FileOutputStream out;
    private Schema schema;
    public PublicationHandler(String publication, Schema schema, File file) {
        try {
            this.out = new FileOutputStream(file);
            this.publicationType = publication;
            this.schema = schema;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Set<String> attributeTypes = new HashSet<String>();
    static {
        attributeTypes.add("author");
        attributeTypes.add("cite");
        attributeTypes.add("pages");
        attributeTypes.add("title");
        attributeTypes.add("year");
    }

    boolean isEnd = false;

    //用来遍历xml的开始标签
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // TODO Auto-generated method stub
        super.startElement(uri, localName, qName, attributes);
        if(publicationType.equals(qName)) {
            String sub = publicationType.substring(0, 1).toUpperCase() + publicationType.substring(1);
            if (publicationType.equals("article")) {
                publication = new Article(attributes);
            } else if (publicationType.equals("inproceedings")) {
                publication = new Inproceedings(attributes);
            } else if (publicationType.equals("proceedings")) {
                publication = new Proceedings(attributes);
            } else if (publicationType.equals("book")) {
                publication = new Book(attributes);
            } else if (publicationType.equals("incollection")) {
                publication = new Incollection(attributes);
            } else if (publicationType.equals("phdthesis")) {
                publication = new Phdthesis(attributes);
            } else if (publicationType.equals("mastersthesis")) {
                publication = new Mastersthesis(attributes);
            } else if (publicationType.equals("www")) {
                publication = new Www(attributes);
            } else if (publicationType.equals("data")) {
                publication = new Data(attributes);
            }
            isEnd = false;
        } else if (attributeTypes.contains(qName)) {
            if (qName.equals("author")) {
                field = new Author(attributes);
            } else if (qName.equals("cite")) {
                field = new Cite(attributes);
            } else if (qName.equals("pages")) {
                field = new Pages(attributes);
            } else if (qName.equals("title")) {
                field = new Title(attributes);
            } else if (qName.equals("year")) {
                field = new Year(attributes);
            }
        }
    }

    //用来遍历xml的结束标签
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // TODO Auto-generated method stub
        super.endElement(uri, localName, qName);
        if(qName.equals("dblp")){
            // 关闭文件
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(publicationType.equals(qName)){
            isEnd = true;
            publication.addField(field);
            String row = schema.toRow(publication);
            // 提交或写入一次
            try {
                out.write(row.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (attributeTypes.contains(qName) && isEnd ==false) {
            field.setKey(new PCDATA(value));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // TODO Auto-generated method stub
        super.characters(ch, start, length);
        value = new String(ch, start, length);
    }
}
