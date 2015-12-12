# SHT21

[![Build Status](https://travis-ci.org/stefanfreitag/SHT21.svg?branch=master)](https://travis-ci.org/stefanfreitag/SHT21)
[![Coverage Status](https://coveralls.io/repos/stefanfreitag/SHT21/badge.svg)](https://coveralls.io/r/stefanfreitag/SHT21)
[![Download](https://api.bintray.com/packages/stefanfreitag/maven/SHT21/images/download.svg)](https://bintray.com/stefanfreitag/maven/SHT21/_latestVersion)

The SHT21 is a digital humidity and temperature sensor made by [Sensirion](https://www.sensirion.com).
This repository contains a Java-based library for communication with such a SHT21 sensor. 
The code is based on the exemplary sample code on [Sensirions home page](www.sensirion.com/sht21) 

Supported operations:

 * Temperature and humidity measurements.                                    
 * Retrieve resolution for temperature and humidity measurements. 
 * Retrieve status of the end-of-battery alert.
 * Retrieve the heater status.

## Usage
To use this library, you have to specify the I2C address of the SHT21 sensor. After this you
can poll measurements for temperature and relative humidity:

    int I2C_ADDRESS = 0x40;
    
    SHT21 sht21 = SHT21Impl.create(I2CBus.BUS_1, I2C_ADDRESS);
    
    Measurement measurement_1 = sht21.measurePoll(MeasureType.HUMIDITY);
          
    Measurement measurement_2 = sht21.measurePoll(MeasureType.TEMPERATURE);
