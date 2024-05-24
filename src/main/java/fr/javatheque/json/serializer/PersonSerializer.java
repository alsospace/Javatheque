package fr.javatheque.json.serializer;

import com.google.gson.*;
import fr.javatheque.json.ISerializer;
import fr.javatheque.json.object.Film;
import fr.javatheque.json.object.Person;

import java.lang.reflect.Type;

public class PersonSerializer implements ISerializer<Person> {
    @Override
    public JsonElement serialize(Person person, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lastname", person.getLastname());
        jsonObject.addProperty("firstname", person.getFirstname());
        return jsonObject;
    }

    @Override
    public Person deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String lastname = jsonObject.get("lastname").getAsString();
        String firstname = jsonObject.get("firstname").getAsString();
        return new Person(lastname, firstname);
    }
}
