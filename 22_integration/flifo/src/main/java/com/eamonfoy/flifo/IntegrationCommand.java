package com.eamonfoy.flifo;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;
import com.google.devtools.common.options.OptionsParser;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.eamonfoy.flifo.BaseApiProcessor.getJWTToken;
import static com.eamonfoy.flifo.BaseApiProcessor.toFile;



public class IntegrationCommand {

    private static final Logger logger = LoggerFactory.getLogger(IntegrationCommand.class);


    public static void main(String[] args) throws Exception {
        OptionsParser parser = OptionsParser.newOptionsParser(IntergrationOptions.class);
        parser.parseAndExitUponError(args);
        IntergrationOptions options = parser.getOptions(IntergrationOptions.class);


        if (options.APIHost.isEmpty() ||
                options.userName.isEmpty() ||
                options.password.isEmpty() ||
                options.sitaFLIFOAPIKey.isEmpty() ||
                options.homeAirport.isEmpty() ||
                options.arrivalFlifoJsonFileName.isEmpty() ||
                options.departureFlifoJsonFileName.isEmpty() ||
                options.sitaWeatherAPIKey.isEmpty()

        ) {
            printUsage(parser);
            return;
        }

        logger.info("Starting integration job...");

        IntegrationCommand.pushArrivalFlights(options);
        IntegrationCommand.pushDepartureFlights(options);
        IntegrationCommand.pushWeather(options);

    }


    private static void pushArrivalFlights(IntergrationOptions options) throws Exception {
        logger.info("Pushing Arrival flights...");


        //step 0 : get JWT Token
        String jwtToken = getJWTToken(options.APIHost,options.userName,options.password);

        //step 1 : download all arrival flights for the home Airport
        SITAFlifoV3API flifo =  new SITAFlifoV3API(options.sitaFLIFOAPIKey);
        JSONObject arrivals = flifo.getArivals(options.homeAirport);

        //step 2 : Save this information for later
        toFile( arrivals, options.arrivalFlifoJsonFileName );

        // step 3 : read in flifo arrival flights
        InputStream jsonInputStream = new FileInputStream(new File(options.arrivalFlifoJsonFileName));

        // step 3 : push all new arrival flights in to ArrivalFlightAPI
        PushFlifoToArrivalFlightAPI push = new PushFlifoToArrivalFlightAPI(options.APIHost,jwtToken);

        // step 4 : delete all previous arrival flights
        push.deleteAllArrivalFlights();

        // step 5 : post new arrival flights to the ArrivalFlightAPI
        push.processAllArrivalFlights(jsonInputStream);

    }


    private static void pushDepartureFlights(IntergrationOptions options) throws Exception {
        logger.info("Pushing Departure flights...");

        //step 0 : get JWT Token
        String jwtToken = getJWTToken(options.APIHost,options.userName,options.password);

        //step 1 : download all arrival flights for the home Airport
        SITAFlifoV3API flifo =  new SITAFlifoV3API(options.sitaFLIFOAPIKey);
        JSONObject arrivals = flifo.getDepartures(options.homeAirport);

        //step 2 : Save this information for later
        toFile( arrivals,options.departureFlifoJsonFileName );

        // step 3 : read in flifo arrival flights
        InputStream jsonInputStream = new FileInputStream(new File(options.departureFlifoJsonFileName));

        // step 3 : push all new arrival flights in to ArrivalFlightAPI
        PushFlifoToDepartureFlightAPI push = new PushFlifoToDepartureFlightAPI(options.APIHost,jwtToken);

        // step 4 : delete all previous arrival flights
        push.deleteAllArrivalFlights();

        // step 5 : post new arrival flights to the ArrivalFlightAPI
        push.processAllArrivalFlights(jsonInputStream);

    }

    private static void pushWeather(IntergrationOptions options) throws Exception {
        logger.info("Pushing Weather flights...");

        //step 0 : get JWT Token
        String jwtTokenWeatherAPI = getJWTToken(options.APIHost,options.userName,options.password);

        PushWeatherToWeatherAPI pushWeatherApi = new PushWeatherToWeatherAPI(options.APIHost,jwtTokenWeatherAPI);

        pushWeatherApi.deleteAllWeatherRecords();

        // step 1 : download all arrival flights for the home Airport
        SITAFlifoV3API flifoAPI =  new SITAFlifoV3API(options.sitaFLIFOAPIKey);

        // step 2 : get a list of departure airport codes
        ArrayList<String> departureAirports = flifoAPI.getDepartureAirports(options.homeAirport);

        SITAWeatherAPI weatherAPI = new SITAWeatherAPI(options.sitaWeatherAPIKey);

        // step 3 : cycle through the list of departure airports
        departureAirports.forEach( airport -> {
            try {

                // step 4 : return the 7 day weather forecast for each airport code
                JSONObject weatherForAirport = weatherAPI.getWeatherForecastForCity(airport);

                try {

                    // step 5 : get all forecast record
                    final JSONArray weatherRecords = weatherForAirport.getJSONArray("weatherForecast");

                    // step 6 : cycle through each forecast record
                    weatherRecords.forEach(weatherRecord -> {

                        // step 7 : for easy transformation add the sirport code to each weatherForecastRecord
                        JSONObject record = ((JSONObject) weatherRecord);
                        record.put("airportCode", airport);

                        // step 8 : transform the SITAFliFo Weather forecast record to  yej new weatherAPIPayload
                        JSONObject weatherAPIPayload = pushWeatherApi.prepareJson(record);

                        // step 9 : post the new record to the weather API
                        try {
                            pushWeatherApi.postNewWeatherRecrod(weatherAPIPayload);
                        } catch (UnirestException e) {
                            logger.error("error posting New Weather Recrod", e);
                        }
                    });

                } catch (org.json.JSONException e) {
                    logger.error("error no weather found for this city :" + airport , e);
                }



            } catch (UnirestException e) {
                logger.error("error getting SITA Weather information", e);
            }

        });

    }

    private static void printUsage(OptionsParser parser) {
        System.out.println("Usage: java -jar server.jar OPTIONS");
        System.out.println(parser.describeOptions(Collections.<String, String>emptyMap(),
                OptionsParser.HelpVerbosity.LONG));
    }

}
