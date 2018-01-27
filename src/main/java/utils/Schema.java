package utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Schema {
    private String[] attributes;
    private String table;
    private Map<String, Method> extractors = new HashMap<String, Method>();
    public Schema(String name, String[] attributes, String type) {
        try {
            Class clazz = getClass().getClassLoader().loadClass(type);
            this.table = name;
            this.attributes = attributes;
            for (String attr : attributes) {
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
    public String toRow(Object obj) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        boolean f = false;
        for (String attr : attributes) {
            if (f) builder.append(",");
            builder.append("\""+attr+"\":");
            Method mtd = extractors.get(attr);
            try {
                String value = mtd.invoke(obj).toString();
                builder.append(value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            f = true;
        }
        builder.append("}");
        return builder.toString();
    }
}
