package fr.javatheque.json.serializer;

import com.google.gson.*;
import fr.javatheque.json.ISerializer;
import fr.javatheque.json.object.Film;
import fr.javatheque.json.object.Library;
import fr.javatheque.json.object.User;

import java.lang.reflect.Type;

public class UserSerializer implements ISerializer<User> {
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", user.getId());
        jsonObject.addProperty("lastname", user.getLastname());
        jsonObject.addProperty("firstname", user.getFirstname());
        jsonObject.addProperty("email", user.getEmail());
        jsonObject.addProperty("password", user.getPassword());
        jsonObject.add("library", jsonSerializationContext.serialize(user.getLibrary()));
        return jsonObject;
    }

    @Override
    public User deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String id = jsonObject.get("id").getAsString();
        String lastname = jsonObject.get("lastname").getAsString();
        String firstname = jsonObject.get("firstname").getAsString();
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();
        Library library = jsonDeserializationContext.deserialize(jsonObject.get("library"), Library.class);
        return new User(lastname, firstname, email, password, library, id);
    }
}
