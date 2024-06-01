package fr.javatheque.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TMDBApiClient {
    private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";
    private static final String TMDB_API_KEY = "YOUR_API_KEY";

    private final HttpClient httpClient;

    public TMDBApiClient() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public String searchMovies(String query, String language, int page) throws IOException, InterruptedException {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = TMDB_API_BASE_URL + "/search/movie?api_key=" + TMDB_API_KEY +
                "&query=" + encodedQuery +
                "&language=" + language +
                "&page=" + page;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response);
    }

    public String getMovieDetails(int movieId, String language) throws IOException, InterruptedException {
        String url = TMDB_API_BASE_URL + "/movie/" + movieId + "?api_key=" + TMDB_API_KEY +
                "&language=" + language;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response);
    }

    private String handleResponse(HttpResponse<String> response) throws IOException {
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new IOException("Erreur lors de la requête API: " + response.statusCode());
        }
    }
}