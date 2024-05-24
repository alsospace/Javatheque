package fr.javatheque.json;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface ISerializer<T> extends JsonSerializer<T>, JsonDeserializer<T> {

}
