package fr.javatheque.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.X509Certificate;

/**
 * Utility class for making requests to the TMDB API.
 */
public class TMDBRequest {
    private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";
    private static final String TMDB_API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOWE0ZmYwOTNiZmMzMDU3NmExODJjOTExOWE3NGJlOCIsInN1YiI6IjY1OGRlYTk5YTMzNjEyNTk5MDU5NjM5NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z0foqNQE3_wbsEuVYquq0MB_5H_zngBJ2t9VilLk6oQ";

    private static TMDBRequest instance;
    private final OkHttpClient httpClient;

    /**
     * Private constructor to create a TMDBRequest instance.
     * Configures an OkHttpClient with a custom SSL context that trusts all certificates.
     */
    private TMDBRequest() {
        // Trust manager that trusts all certificates
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };

        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create a ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            this.httpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the singleton instance of TMDBRequest.
     *
     * @return the TMDBRequest instance
     */
    public static TMDBRequest getInstance() {
        if (instance == null) {
            instance = new TMDBRequest();
        }
        return instance;
    }

    /**
     * Searches for movies based on the provided title, language, and page number.
     *
     * @param title    the movie title to search for
     * @param language the language of the movie results
     * @param page     the page number of the search results
     * @return the JSON response string containing the search results
     * @throws IOException if an I/O error occurs during the request
     */
    public String searchMovies(String title, String language, int page) throws IOException {
        String url = TMDB_API_BASE_URL + "/search/movie?query=" + title +
                "&language=" + language +
                "&page=" + page;
        return getResponse(url);
    }

    /**
     * Retrieves the details of a movie based on the provided movie ID and language.
     *
     * @param movieId  the ID of the movie to retrieve details for
     * @param language the language of the movie details
     * @return the JSON response string containing the movie details
     * @throws IOException if an I/O error occurs during the request
     */
    public String getMovieDetails(int movieId, String language) throws IOException {
        String url = TMDB_API_BASE_URL + "/movie/" + movieId +
                "?language=" + language;
        return getResponse(url);
    }

    /**
     * Retrieves the credit details of a movie based on the provided movie ID and language.
     *
     * @param movieId  the ID of the movie to retrieve credit details for
     * @param language the language of the credit details
     * @return the JSON response string containing the credit details
     * @throws IOException if an I/O error occurs during the request
     */
    public String getCreditDetails(int movieId, String language) throws IOException {
        String url = TMDB_API_BASE_URL + "/movie/" + movieId +
                "/credits?language=" + language;
        return getResponse(url);
    }

    /**
     * Sends a GET request to the specified URL and returns the response body as a string.
     *
     * @param url the URL to send the request to
     * @return the response body as a string
     * @throws IOException if an I/O error occurs during the request
     */
    private String getResponse(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + TMDB_API_KEY)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }
}