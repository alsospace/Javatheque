package fr.javatheque.json;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import fr.javatheque.json.object.Film;
import fr.javatheque.json.object.Library;
import fr.javatheque.json.object.Person;
import fr.javatheque.json.object.User;
import fr.javatheque.json.serializer.FilmSerializer;
import fr.javatheque.json.serializer.LibrarySerializer;
import fr.javatheque.json.serializer.PersonSerializer;
import fr.javatheque.json.serializer.UserSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class JsonManager {

    /**
     * Singleton instance
     */
    private static JsonManager instance;

    /**
     * Attribute(s)
     */
    private JsonBuilder gsonBuilder;
    private Gson gson;

    /**
     * Private constructor to prevent instantiation
     */
    private JsonManager() {
        gsonBuilder = new JsonBuilder();
        addAdapter(User.class, new UserSerializer());
        addAdapter(Person.class, new PersonSerializer());
        addAdapter(Library.class, new LibrarySerializer());
        addAdapter(Film.class, new FilmSerializer());
    }

    /**
     * Public method to provide access to the singleton instance
     * @return singleton instance of JsonManager
     */
    public static JsonManager getInstance() {
        if (instance == null) {
            instance = new JsonManager();
        }
        return instance;
    }

    /**
     * Add a TypeAdapter to the JsonManager
     *
     * @param clazz       TypeAdapter Class
     * @param iSerializer Serializer/Deserializer
     */
    public void addAdapter(Class<?> clazz, ISerializer<?> iSerializer) {
        gsonBuilder.registerTypeAdapter(clazz, iSerializer);
    }

    /**
     * Enable the JsonManager to Serialize/Deserialize
     */
    public void enable() {
        gson = gsonBuilder.createGson();
    }

    /**
     * Serialize the passed object
     *
     * @param obj  Object who need to be serialized
     * @param type Object's type
     * @param <T>  Generic type
     * @return Json in string format
     */
    public <T> String serialize(Object obj, Class<T> type) {
        try {
            return gson.toJson(obj, type);
        } catch (Exception ignore) {
            return null;
        }
    }

    /**
     * Create an object with the content of the specified file
     *
     * @param file File which contain the object
     * @param type Type of the Object
     * @param <T>  Generic type
     * @return Object
     */
    public <T> T deserialize(File file, Class<T> type) {
        try {
            return gson.fromJson(new JsonParser().parse(new FileReader(file)), type);
        } catch (Exception ignore) {
            return null;
        }
    }

    /**
     * Create an object with the content of the specified file
     *
     * @param str  String which contain the object
     * @param type Type of the Object
     * @param <T>  Generic type
     * @return Object
     */
    public <T> T deserialize(String str, Class<T> type) {
        return gson.fromJson(new JsonParser().parse(str), type);
    }

    public <T> void serialize(Object obj, Class<T> type, File out) throws IOException {
        String serial = serialize(obj, type);
        try (FileOutputStream fos = new FileOutputStream(out)) {
            fos.write(serial.getBytes());
            fos.flush();
        }
    }

    public JsonBuilder getGsonBuilder() {
        return gsonBuilder;
    }

    public void setGsonBuilder(JsonBuilder gsonBuilder) {
        this.gsonBuilder = gsonBuilder;
    }

    public Gson getGson() {
        return gson;
    }
}