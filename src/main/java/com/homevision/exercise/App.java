package com.homevision.exercise;

public class App 
{
  //TODO: this 2 parameters can be extracted to be arguments of the main app.  
  static final int PAGES_TO_PROCESS = 10;
  static final String OUTPUT_FOLDER = "pictures";

  public static void main( String[] args ) {
    System.out.println( "Processing houses pictures..." );
    try {
      HousePicturesProcessor housePicturesProcessor = new HousePicturesProcessor(OUTPUT_FOLDER);
      housePicturesProcessor.startProcess(PAGES_TO_PROCESS);
      System.out.println( "Houses pictures have been processed");
    } catch (Exception e) {
      System.out.println( "Unexpected error processing houses");        
      e.printStackTrace();
    }
  }

}
