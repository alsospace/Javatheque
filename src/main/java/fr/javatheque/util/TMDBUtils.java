package fr.javatheque.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.javatheque.database.model.Film;
import fr.javatheque.database.model.Person;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TMDBUtils {

    private static final TMDBRequest TMDB_REQUEST = TMDBRequest.getInstance();
    private static final Gson gson = new Gson();
    private static final int MAX_ACTORS = 10;

    public static Film getFilmFromTMDB(int id, String lang, String support) throws IOException {
        JsonObject movieDetails = getJsonObjectFromApi(TMDB_REQUEST.getMovieDetails(id, lang));
        JsonObject movieCredits = getJsonObjectFromApi(TMDB_REQUEST.getCreditDetails(id, lang));

        String poster = movieDetails.get("poster_path").getAsString();
        String title = movieDetails.get("title").getAsString();
        String description = movieDetails.get("overview").getAsString();
        String releaseDate = movieDetails.get("release_date").getAsString();
        int year = Integer.parseInt(releaseDate.substring(0, 4));

        Person director = getDirectorFromCredits(movieCredits.getAsJsonArray("crew"));
        List<Person> actors = getActorsFromCredits(movieCredits.getAsJsonArray("cast"));

        return new Film(id, null, poster, lang, support, title, description, releaseDate, year,
                0.0f, "", director, actors);
    }

    private static JsonObject getJsonObjectFromApi(String json) {
        return gson.fromJson(json, JsonObject.class);
    }

    private static Person getDirectorFromCredits(JsonArray crewArray) {
        return StreamSupport.stream(crewArray.spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .filter(crewJson -> "Director".equalsIgnoreCase(crewJson.get("job").getAsString()))
                .findFirst()
                .map(crewJson -> parsePersonName(crewJson.get("name").getAsString()))
                .orElse(new Person("Doe", "John"));
    }

    private static List<Person> getActorsFromCredits(JsonArray castArray) {
        return StreamSupport.stream(castArray.spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .filter(actorJson -> "Acting".equalsIgnoreCase(actorJson.get("known_for_department").getAsString()))
                .limit(MAX_ACTORS)
                .map(actorJson -> parsePersonName(actorJson.get("name").getAsString()))
                .collect(Collectors.toList());
    }

    private static Person parsePersonName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return new Person("Doe", "John");
        }

        String[] nameParts = fullName.trim().split("\\s+");
        if (nameParts.length == 1) {
            return new Person(nameParts[0], "");
        }

        String lastName = nameParts[nameParts.length - 1];
        String firstName = String.join(" ", Arrays.copyOfRange(nameParts, 0, nameParts.length - 1));
        return new Person(lastName, firstName);
    }

}
