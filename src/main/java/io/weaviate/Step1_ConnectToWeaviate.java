package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;

public class Step1_ConnectToWeaviate {
    public static WeaviateClient run() {
        String httpHost = "localhost";
        int httpPort = 8080;
        return WeaviateClient.local(conn -> conn.host(httpHost).httpPort(httpPort));
    }
}