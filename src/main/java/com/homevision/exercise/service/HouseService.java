package com.homevision.exercise.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homevision.exercise.client.RestClient;
import com.homevision.exercise.exception.ServiceRequestException;
import com.homevision.exercise.exception.ServiceUnavailableException;
import com.homevision.exercise.model.House;
import com.homevision.exercise.model.HousesResponse;

public class HouseService {

  //TODO: This can be extracted to a config file
  private String urlFormat = "http://app-homevision-staging.herokuapp.com/api_project/houses?page=%d";

  private RestClient client;
  private ObjectMapper mapper;

  public HouseService(RestClient client, ObjectMapper mapper) {
    this.client = client;
    this.mapper = mapper;
  }

  public HouseService() {
    client = new RestClient();
    mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public List<House> getHouses(int page) throws IOException, InterruptedException,
    ServiceUnavailableException, ServiceRequestException {
    //TODO: Can be refactored to a specific http client
    // to pass through query params instead of replace here
    String housesString = client.get(String.format(urlFormat, page));
    //TODO: Extract to a mapper that can be part of the RestClient initialization
    // to parse the response directly
    HousesResponse response = mapper.readValue(housesString, HousesResponse.class);
    return response.getHouses();
  }

}
