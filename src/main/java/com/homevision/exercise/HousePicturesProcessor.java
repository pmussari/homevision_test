package com.homevision.exercise;

import java.io.IOException;

import com.homevision.exercise.exception.ServiceRequestException;
import com.homevision.exercise.exception.ServiceUnavailableException;
import com.homevision.exercise.service.HouseService;
import com.homevision.exercise.service.StorageService;

/*
 * This is the main process for the exercise.
 * Only have the structure to get amount of pages and
 * some extra configuration as folder output of images
 */
public class HousePicturesProcessor {

  HouseService houseService;
  StorageService storageService;

  //TODO: constructors could be replaces by Dependency Injection with Spring or other libraries.
  public HousePicturesProcessor(HouseService houseService, StorageService storageService) {
    this.houseService = houseService;
    this.storageService = storageService;
  }

  public HousePicturesProcessor(String outputFolder) throws IOException {
    houseService = new HouseService();
    storageService = new StorageService(outputFolder);
  }

  public void startProcess(int pageToProcess) throws IOException,
    InterruptedException, ServiceRequestException {
    int currentPage = 1;
    while (currentPage <= pageToProcess) {
      try {
        houseService.getHouses(currentPage).parallelStream()
          .forEach(storageService::saveHousePicture);
        currentPage++;
      } catch (ServiceUnavailableException serviceUnavailable) {
        //TODO: Add some kind of boundaries to avoid retrying indefinitely
        System.out.println( "Error getting houses, retrying...");
      }
    }
  }
}
