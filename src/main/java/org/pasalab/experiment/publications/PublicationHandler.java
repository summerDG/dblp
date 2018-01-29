package org.pasalab.experiment.publications;

import org.pasalab.experiment.fields.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.pasalab.experiment.utils.PCDATA;
import org.pasalab.experiment.utils.Schema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PublicationHandler extends DefaultHandler {
    private static Set<String> publicationTypes = new HashSet<String>();
    private String publicationType;
    private Publication publication;
    private Field field;
    private String value;
    private FileOutputStream out;
    private Schema schema;
    public PublicationHandler(Schema schema, File file) {
        try {
            this.out = new FileOutputStream(file);
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
        publicationTypes.add("article");
        publicationTypes.add("inproceedings");
        publicationTypes.add("proceedings");
        publicationTypes.add("book");
        publicationTypes.add("incollection");
        publicationTypes.add("phdthesis");
        publicationTypes.add("mastersthesis");
        publicationTypes.add("www");
        publicationTypes.add("data");
    }

    boolean isEnd = false;

    //用来遍历xml的开始标签
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // TODO Auto-generated method stub
        super.startElement(uri, localName, qName, attributes);
        value = "";
        if(publicationTypes.contains(qName)) {
            publicationType = qName;
            String sub = qName.substring(0, 1).toUpperCase() + qName.substring(1);
            if (qName.equals("article")) {
                publication = new Article(attributes);
            } else if (qName.equals("inproceedings")) {
                publication = new Inproceedings(attributes);
            } else if (qName.equals("proceedings")) {
                publication = new Proceedings(attributes);
            } else if (qName.equals("book")) {
                publication = new Book(attributes);
            } else if (qName.equals("incollection")) {
                publication = new Incollection(attributes);
            } else if (qName.equals("phdthesis")) {
                publication = new Phdthesis(attributes);
            } else if (qName.equals("mastersthesis")) {
                publication = new Mastersthesis(attributes);
            } else if (qName.equals("www")) {
                publication = new Www(attributes);
            } else if (qName.equals("data")) {
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
            List<String> rows = schema.toRows(publication);
            for (String row : rows) {
                // 提交或写入一次
                StringBuilder builder = new StringBuilder();
                builder.append("{");
                builder.append(row);
                builder.append("}\n");
                try {
                    out.write(builder.toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if (attributeTypes.contains(qName) && isEnd ==false) {
            field.setKey(new PCDATA(value));
            publication.addField(field);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // TODO Auto-generated method stub
        super.characters(ch, start, length);
        value = new String(ch, start, length);
    }
}
