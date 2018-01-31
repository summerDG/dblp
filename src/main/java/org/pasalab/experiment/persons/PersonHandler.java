package org.pasalab.experiment.persons;

import org.pasalab.experiment.fields.Author;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.pasalab.experiment.utils.PCDATA;
import org.pasalab.experiment.utils.Schema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PersonHandler extends DefaultHandler {
    private String value;
    private FileOutputStream out;
    private Schema schema;


    /**
     * @param schema 给person的field起别名, 并作为表的输出属性
     * @param file 表文件
     */
    public PersonHandler(Schema schema, File file){
        try {
            this.out = new FileOutputStream(file);
            this.schema = schema;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals("author")) {
            value = "";
        }
    }

    //用来遍历xml的结束标签
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if(qName.equals("dblp")){
            // 关闭文件
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(qName.equals("author") && !Person.created(value)){
            final Author a= new Author(new PCDATA(value));
            Person p = new Person(value, a.getUrlpt());
            List<String> rows = schema.toRows(p);
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
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // TODO Auto-generated method stub
        super.characters(ch, start, length);
        value = new String(ch, start, length);
    }
}
