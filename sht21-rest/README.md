# SHT21-REST
[![Build Status](https://travis-ci.org/stefanfreitag/SHT21.REST.svg)](https://travis-ci.org/stefanfreitag/SHT21.REST)
[![Coverage Status](https://coveralls.io/repos/stefanfreitag/SHT21-REST/badge.svg?branch=master&service=github)](https://coveralls.io/github/stefanfreitag/SHT21-REST?branch=master)

This applications provides a REST interface to retrieve measurements from an SHT21. Internally it utilizes
this [SHT21 library](https://github.com/stefanfreitag/SHT21). 

## Usage
The applicaton starts a Jetty-based web server and offers the service on port 8080. The entry point is
*http://localhost:8080*. By attaching the following you can query for different information

- Temperature measurement: `/getTemperature`
- Humidity measurement: `/getHumidity`
- Resolution setting: `/getResolution`
- Heater status: `/getHeaterStatus`
- End of battery alert: `/getEoBAlert`