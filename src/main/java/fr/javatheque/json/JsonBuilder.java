package fr.javatheque.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonBuilder {

    /**
     * Attribute(s)
     */
    private final GsonBuilder gsonBuilder;

    /**
     * Constructor by default
     */
    public JsonBuilder() {
        this.gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.setPrettyPrinting();
    }

    /**
     * Register an adapter to gson builder
     *
     * @param clazz          class of adapter
     * @param jsonSerializer json serializer of this adapter
     */
    public void registerTypeAdapter(Class<?> clazz, ISerializer<?> jsonSerializer) {
        gsonBuilder.registerTypeAdapter(clazz, jsonSerializer);
    }

    /**
     * Create a GSON
     *
     * @return {@link Gson}
     */
    public Gson createGson() {
        return gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
    }
}
