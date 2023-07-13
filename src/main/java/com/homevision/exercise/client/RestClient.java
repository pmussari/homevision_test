package com.homevision.exercise.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import com.homevision.exercise.exception.ServiceRequestException;
import com.homevision.exercise.exception.ServiceUnavailableException;

public class RestClient {

  private final int TIMEOUT_S = 20;

  public String get(String url) throws IOException, InterruptedException,
      ServiceUnavailableException, ServiceRequestException {
    HttpClient client = HttpClient.newBuilder()
      .connectTimeout(Duration.ofSeconds(TIMEOUT_S))
      .build();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(url))
      .build();
    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
    if (response.statusCode() >= 200 && response.statusCode() < 300) {
      return response.body();
    } else if (response.statusCode() >= 500 && response.statusCode() < 600) {
      throw new ServiceUnavailableException();
    } else {
      throw new ServiceRequestException();
    }
  }

}
