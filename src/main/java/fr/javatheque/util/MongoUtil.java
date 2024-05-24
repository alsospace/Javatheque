package fr.javatheque.util;

import org.bson.Document;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MongoUtil {

    public static <T> Document objectToDocument(T obj) {
        Document document = new Document();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value != null) {
                    if (value instanceof List<?> list) {
                        List<Document> documentList = new ArrayList<>();
                        for (Object item : list) {
                            documentList.add(objectToDocument(item));
                        }
                        document.append(field.getName(), documentList);
                    } else {
                        document.append(field.getName(), objectToDocument(value));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return document;
    }

    public static <T> T documentToObject(Document document, Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = document.get(field.getName());
                if (value != null) {
                    if (value instanceof List) {
                        List<Document> documentList = (List<Document>) value;
                        List<Object> objectList = new ArrayList<>();
                        for (Document doc : documentList) {
                            objectList.add(documentToObject(doc, field.getType().getComponentType()));
                        }
                        field.set(obj, objectList);
                    } else {
                        field.set(obj, documentToObject((Document) value, field.getType()));
                    }
                }
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}