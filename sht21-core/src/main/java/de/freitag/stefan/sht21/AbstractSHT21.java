package de.freitag.stefan.sht21;

import java.time.format.DateTimeFormatter;

abstract class AbstractSHT21 implements  SHT21{

    protected static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

}
