package com.homevision.exercise.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import com.homevision.exercise.model.House;

/*
* This class is getting too much knowledge about houses and how to read and write pictures.
* It aims to abstract the process in the main app.
*/
//TODO: Extract logic of images processing to different client abstractions in order
// to send the basic information for read and write.
public class StorageService {
  
  private int BUFFER_SIZE = 1024;
  private String targetFolder;
  
  public StorageService(String outputFolder) throws IOException {
    File directory = new File(outputFolder);
    if (! directory.exists()){
        directory.mkdirs();
    }
    targetFolder = directory.getAbsolutePath();
  }

  public void saveHousePicture(House house) {
    System.out.println("Processing house " + house.getId());
    try {
      BufferedInputStream in = new BufferedInputStream(new URL(house.getPhotoURL()).openStream());
      //TODO: Extract to an util to define better names.
      //Ex: address to kebabCase and padding Ids
      String filename = String.format("%d_%s%s",
        house.getId(), house.getAddress(),
          house.getPhotoURL().substring(house.getPhotoURL().lastIndexOf(".")));
      File targetFile = new File(targetFolder, filename);
      //TODO: Abstract the outputStream to extend then in different implementations
      // Ex: LocalFiles, S3, etc
      FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
      //TODO: Improve this buffer memory handling and try to pass directly
      // to the outputstream
      byte dataBuffer[] = new byte[BUFFER_SIZE];
      int bytesRead;
      while ((bytesRead = in.read(dataBuffer, 0, BUFFER_SIZE)) != -1) {
        fileOutputStream.write(dataBuffer, 0, bytesRead);
      }
      fileOutputStream.close();
    } catch (IOException e) {
      //TODO: Add mechanism to retry in case of error.
      e.printStackTrace();
      System.out.println("Error downloading image for house " + house.getId());
    }
  }

}
