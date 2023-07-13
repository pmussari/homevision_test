package com.homevision.exercise;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.homevision.exercise.exception.ServiceUnavailableException;
import com.homevision.exercise.model.House;
import com.homevision.exercise.service.HouseService;
import com.homevision.exercise.service.StorageService;

public class HousePicturesProcessorTest {

  HouseService mockedHouseService;
  StorageService mockedStorageService;
  HousePicturesProcessor housePicturesProcessor;

  @BeforeEach
  public void setUp() {
    mockedHouseService = mock(HouseService.class);
    mockedStorageService = mock(StorageService.class);
    housePicturesProcessor = new HousePicturesProcessor(mockedHouseService, mockedStorageService);
  }

  @Test
  public void shouldIterateAsManyPages() throws Exception {
    // Number of pages to iterate
    int PAGES = 4;
    when(mockedHouseService.getHouses(anyInt())).thenReturn(Collections.emptyList());
    housePicturesProcessor.startProcess(PAGES);
    verify(mockedHouseService, times(PAGES)).getHouses(anyInt());
  }
  
  @Test
  public void shouldIterateSamePageIfServiceFails() throws Exception {
    // For page 1 would fail once
    when(mockedHouseService.getHouses(eq(1)))
        .thenThrow(new ServiceUnavailableException())
        .thenReturn(Collections.emptyList());
    // For page 2 wont fail
    when(mockedHouseService.getHouses(eq(2)))
        .thenReturn(Collections.emptyList());
    // Will iterate only 2 pages
    housePicturesProcessor.startProcess(2);
    verify(mockedHouseService, times(2)).getHouses(eq(1));
    verify(mockedHouseService, times(1)).getHouses(eq(2));
  }

  @Test
  public void shouldAbortIterationOnUnexpectedError() throws Exception {
    // For page 1 would fail once
    when(mockedHouseService.getHouses(eq(1)))
        .thenThrow(new IOException("Unexpected error"))
        .thenReturn(Collections.emptyList());
    // For page 2 wont fail
    when(mockedHouseService.getHouses(eq(2)))
        .thenReturn(Collections.emptyList());
    assertThrows(IOException.class, () -> {
      housePicturesProcessor.startProcess(2);
    });
    verify(mockedHouseService, times(1)).getHouses(eq(1));
    verify(mockedHouseService, times(0)).getHouses(eq(2));
  }

  @Test
  public void shouldCallSavePicturePerEachHouse() throws Exception {
    int PAGES = 4;
    House mockedHouse1 = mock(House.class);
    House mockedHouse2 = mock(House.class);
    when(mockedHouseService.getHouses(anyInt()))
        .thenReturn(Arrays.asList(mockedHouse1, mockedHouse2));
    housePicturesProcessor.startProcess(PAGES);
    verify(mockedStorageService, times(PAGES)).saveHousePicture(mockedHouse1);
    verify(mockedStorageService, times(PAGES)).saveHousePicture(mockedHouse2);
  }

}
