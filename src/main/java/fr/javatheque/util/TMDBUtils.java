package fr.javatheque.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.javatheque.database.model.Film;
import fr.javatheque.database.model.Person;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Utility class for working with TMDB API responses.
 */
public class TMDBUtils {

    private static final TMDBRequest TMDB_REQUEST = TMDBRequest.getInstance();
    private static final Gson gson = new Gson();
    private static final int MAX_ACTORS = 10;

    /**
     * Retrieves film details from the TMDB API based on the provided ID, language, and support.
     *
     * @param id       the ID of the film to retrieve
     * @param lang     the language of the film details
     * @param support  the support type of the film
     * @param response the HttpServletResponse object for handling the response
     * @return the Film object containing the retrieved details
     * @throws IOException if an I/O error occurs during the API request
     */
    public static Film getFilmFromTMDB(int id, String lang, String support, HttpServletResponse response) throws IOException {
        JsonObject movieDetails = getJsonObjectFromApi(TMDB_REQUEST.getMovieDetails(id, lang));
        JsonObject movieCredits = getJsonObjectFromApi(TMDB_REQUEST.getCreditDetails(id, lang));

        String poster = movieDetails.get("poster_path").getAsString();
        String title = movieDetails.get("title").getAsString();
        String description = movieDetails.get("overview").getAsString();
        String releaseDate = movieDetails.get("release_date").getAsString();
        String year = releaseDate.split("-")[0];
        if (year.isEmpty()) {
            year = "No year found";
        }

//        Person director = getDirectorFromCredits(movieCredits.getAsJsonArray("cast"));
//        List<Person> actors = getActorsFromCredits(movieCredits.getAsJsonArray("cast"));

        return new Film(id, null, poster, lang, support, title, description, releaseDate, year,
                0.0f, "no opinion yet", new Person("John", "Doe"), new ArrayList<>());
    }

    /**
     * Parses the JSON string from the API response into a JsonObject.
     *
     * @param json the JSON string from the API response
     * @return the parsed JsonObject
     */
    private static JsonObject getJsonObjectFromApi(String json) {
        return gson.fromJson(json, JsonObject.class);
    }

    /**
     * Retrieves the director information from the credits JSON array.
     *
     * @param crewArray the JSON array containing crew information
     * @return the Person object representing the director
     */
    private static Person getDirectorFromCredits(JsonArray crewArray) {
        return StreamSupport.stream(crewArray.spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .filter(crewJson -> "Director".equalsIgnoreCase(crewJson.get("job").getAsString()))
                .findFirst()
                .map(crewJson -> parsePersonName(crewJson.get("name").getAsString()))
                .orElse(new Person("Doe", "John"));
    }

    /**
     * Retrieves the list of actors from the credits JSON array.
     *
     * @param castArray the JSON array containing cast information
     * @return the list of Person objects representing the actors
     */
    private static List<Person> getActorsFromCredits(JsonArray castArray) {
        return StreamSupport.stream(castArray.spliterator(), false)
                .map(JsonElement::getAsJsonObject)
                .filter(actorJson -> "Acting".equalsIgnoreCase(actorJson.get("known_for_department").getAsString()))
                .limit(MAX_ACTORS)
                .map(actorJson -> parsePersonName(actorJson.get("name").getAsString()))
                .collect(Collectors.toList());
    }

    /**
     * Parses a person's full name into a Person object with last name and first name.
     *
     * @param fullName the full name of the person
     * @return the Person object with last name and first name
     */
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