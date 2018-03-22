package de.freitag.stefan.spring.sht21.server.api.model;

public class Sensors {

 public static boolean isValidUuid(final String text){
     if (text==null) {
         return false;
     }
     return text.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
 }

}