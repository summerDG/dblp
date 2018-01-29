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
    private List filter(Object[] objs) {
        List list = new ArrayList();
        for (Object obj : objs) {
            if (!obj.toString().equals("...")) list.add(obj);
        }
        return list;
    }
    private List filter(List objs) {
        List list = new ArrayList();
        for (Object obj : objs) {
            if (!obj.toString().equals("...")) list.add(obj);
        }
        return list;
    }
    //TODO: 有些属性是一个数组, 所以返回应该是一个数组
    public List<String> toRows(Object obj) {
        List<String> rows = new ArrayList<String>();
        Iterator<String> itr = attributes.keySet().iterator();
        while (itr.hasNext()){
            String k = itr.next();
            String attr = attributes.get(k);
            Method mtd = extractors.get(attr);
            try {
                Class listClazz = List.class;
                if (mtd.getReturnType().isArray()) {
                    List objs = filter((Object[]) mtd.invoke(obj));
                    if (objs.isEmpty()) return new ArrayList<String>();
                    if (rows.size() > 0) {
                        List<String> freshRows = new ArrayList<String>();
                        for (int i = 0; i < rows.size(); i++) {
                            String pre = rows.get(i);
                            for (Object o : objs) {
                                freshRows.add(pre + ",\"" +k +"\":"+o.toString());
                            }
                        }
                        rows = freshRows;
                    } else {
                        for (Object o : objs) {
                            rows.add("\"" +k +"\":"+o.toString());
                        }
                    }
                }else if (listClazz.isAssignableFrom(mtd.getReturnType())){
                    List objs = filter((List)mtd.invoke(obj));
                    if (objs.isEmpty()) return new ArrayList<String>();
                    if (rows.size() > 0) {
                        List<String> freshRows = new ArrayList<String>();
                        for (int i = 0; i < rows.size(); i++) {
                            String pre = rows.get(i);
                            for (Object o : objs) {
                                freshRows.add(pre + ",\"" +k +"\":"+o.toString());
                            }
                        }
                        rows = freshRows;
                    } else {
                        for (Object o : objs) {
                            rows.add("\"" +k +"\":"+o.toString());
                        }
                    }
                } else {
                    String value = mtd.invoke(obj).toString();
                    if (rows.size() > 0) {
                        for (int i = 0; i < rows.size(); i++) {
                            String pre = rows.get(i);
                            rows.set(i, pre + ",\"" +k +"\":"+value);
                        }
                    } else {
                        rows.add("\"" +k +"\":"+value);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return rows;
    }
}
