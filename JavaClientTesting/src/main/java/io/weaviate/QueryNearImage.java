package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.query.QueryResult;
import java.io.IOException;
import java.util.Map;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;


public class QueryNearImage {
    public static void run(WeaviateClient client) throws IOException {
        var products = client.collections.use(Constants.PRODUCT_COLLECTION_NAME);
        String encodedImage = fetchAndEncodeImage(Constants.PRODUCT_PHONE_IMAGE_URL);
        QueryResult<Map<String, Object>> result = products.query.nearImage(
                encodedImage,
                opt -> opt.limit(1)
        );
        System.out.println("NearImage query result: " + result);
    }

    public static String fetchAndEncodeImage(String imageUrl) throws IOException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(imageUrl))
                    .GET()
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() == 200) {
                byte[] imageBytes = response.body();
                return Base64.getEncoder().encodeToString(imageBytes);
            } else {
                throw new IOException("Failed to fetch image: HTTP " + response.statusCode());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Image fetch interrupted: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error fetching or encoding image: " + e.getMessage(), e);
        }
    }
}