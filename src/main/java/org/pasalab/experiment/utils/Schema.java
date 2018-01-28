package org.pasalab.experiment.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Schema {
    private Map<String, String> attributes;
    private String table;
    private Map<String, Method> extractors = new HashMap<String, Method>();
    public Schema(String name, Map<String, String> attributes, String type) {
        try {
            Class clazz = getClass().getClassLoader().loadClass(type);
            this.table = name;
            this.attributes = attributes;
            Iterator<String> itr = attributes.keySet().iterator();
            while (itr.hasNext()){
                String k = itr.next();
                String attr = attributes.get(k);
                String methodName = "get"+attr.substring(0,1).toUpperCase() + attr.substring(1);
                try {
                    Method mtd = clazz.getMethod(methodName);
                    extractors.put(attr, mtd);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //TODO: 有些属性是一个数组, 所以返回应该是一个数组
    public List<String> toRows(Object obj) {
        StringBuilder builder = new StringBuilder();
        List<String> rows = new ArrayList<String>();
        boolean f = false;
        Iterator<String> itr = attributes.keySet().iterator();
        while (itr.hasNext()){
            String k = itr.next();
            String attr = attributes.get(k);
            if (f) builder.append(",");
            builder.append("\""+attr+"\":");
            Method mtd = extractors.get(attr);
            try {
                if (mtd.getReturnType().isArray()) {
                    Object[] objs = (Object[]) mtd.invoke(obj);
                    for (Object o : objs) {
                        rows.add(builder.toString() + o.toString());
                    }
                } else {
                    String value = mtd.invoke(obj).toString();
                    builder.append(value);
                    rows.add(builder.toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            f = true;
        }
        return rows;
    }
}
