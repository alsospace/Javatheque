package fr.javatheque.json.serializer;

import com.google.gson.*;
import fr.javatheque.json.ISerializer;
import fr.javatheque.json.object.Film;
import fr.javatheque.json.object.Library;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LibrarySerializer implements ISerializer<Library> {
    @Override
    public JsonElement serialize(Library library, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ownerId", library.getOwner_id());

        JsonArray filmsArray = new JsonArray();
        for (Film film : library.getFilms()) {
            filmsArray.add(jsonSerializationContext.serialize(film));
        }
        jsonObject.add("films", filmsArray);

        return jsonObject;
    }

    @Override
    public Library deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String ownerId = jsonObject.get("ownerId").getAsString();

        JsonArray filmsArray = jsonObject.getAsJsonArray("films");
        List<Film> films = new ArrayList<>();
        for (JsonElement filmElement : filmsArray) {
            Film film = jsonDeserializationContext.deserialize(filmElement, Film.class);
            films.add(film);
        }

        return new Library(ownerId, films);
    }
}
