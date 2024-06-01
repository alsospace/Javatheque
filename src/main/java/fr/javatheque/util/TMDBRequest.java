package fr.javatheque.util;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class TMDBRequest {
    private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";
    private static final String TMDB_API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOWE0ZmYwOTNiZmMzMDU3NmExODJjOTExOWE3NGJlOCIsInN1YiI6IjY1OGRlYTk5YTMzNjEyNTk5MDU5NjM5NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z0foqNQE3_wbsEuVYquq0MB_5H_zngBJ2t9VilLk6oQ";

    private static TMDBRequest instance;
    private final OkHttpClient httpClient;

    private TMDBRequest() {
        this.httpClient = new OkHttpClient();
    }

    public static synchronized TMDBRequest getInstance() {
        if (instance == null) {
            instance = new TMDBRequest();
        }
        return instance;
    }

    public String searchMovies(String title, String language, int page) throws IOException {
        String url = TMDB_API_BASE_URL + "/search/movie?query=" + title +
                "&language=" + language +
                "&page=" + page;
        return getResponse(url);

    }

    public String getMovieDetails(int movieId, String language) throws IOException {
        String url = TMDB_API_BASE_URL + "/movie/" + movieId +
                "?language=" + language;
        return getResponse(url);
    }
    public String getCreditDetails(int movieId, String language) throws IOException {
        String url = TMDB_API_BASE_URL + "/movie/" + movieId +
                "?credits=" + language;
        return getResponse(url);
    }

    private String getResponse(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + TMDB_API_KEY)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }
}