package fr.javatheque.json.serializer;

import com.google.gson.*;
import fr.javatheque.json.ISerializer;
import fr.javatheque.json.object.Film;
import fr.javatheque.json.object.Person;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FilmSerializer implements ISerializer<Film> {

    @Override
    public JsonElement serialize(Film film, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", film.getId());
        jsonObject.addProperty("poster", film.getPoster());
        jsonObject.addProperty("lang", film.getLang());
        jsonObject.addProperty("support", film.getSupport());
        jsonObject.addProperty("title", film.getTitle());
        jsonObject.addProperty("description", film.getDescription());
        jsonObject.addProperty("releaseDate", film.getReleaseDate());
        jsonObject.addProperty("year", film.getYear());
        jsonObject.addProperty("rate", film.getRate());
        jsonObject.addProperty("opinion", film.getOpinion());
        jsonObject.add("director", jsonSerializationContext.serialize(film.getDirector()));

        JsonArray actorsArray = new JsonArray();
        for (Person actor : film.getActors()) {
            actorsArray.add(jsonSerializationContext.serialize(actor));
        }
        jsonObject.add("actors", actorsArray);

        return jsonObject;
    }

    @Override
    public Film deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        String poster = jsonObject.get("poster").getAsString();
        String lang = jsonObject.get("lang").getAsString();
        String support = jsonObject.get("support").getAsString();
        String title = jsonObject.get("title").getAsString();
        String description = jsonObject.get("description").getAsString();
        String releaseDate = jsonObject.get("releaseDate").getAsString();
        int year = jsonObject.get("year").getAsInt();
        float rate = jsonObject.get("rate").getAsFloat();
        String opinion = jsonObject.get("opinion").getAsString();
        Person director = jsonDeserializationContext.deserialize(jsonObject.get("director"), Person.class);

        JsonArray actorsArray = jsonObject.getAsJsonArray("actors");
        List<Person> actors = new ArrayList<>();
        for (JsonElement actorElement : actorsArray) {
            Person actor = jsonDeserializationContext.deserialize(actorElement, Person.class);
            actors.add(actor);
        }

        return new Film(id, poster, lang, support, title, description, releaseDate, year, rate, opinion, director, actors);
    }
}
