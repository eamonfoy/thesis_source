package com.eamonfoy.flifo;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.eamonfoy.flifo.SITAFlifoV3API.toFile;
import static java.lang.Thread.sleep;

public class PushWeatherToWeatherAPI extends BaseApiProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PushWeatherToWeatherAPI.class);

    final String url_post_new_weather = "/services/weather/api/weathers";
    final String url_delete_all_weather = "/services/weather/api/weathers/all";

    private String jwtToken;
    private String url;

    public PushWeatherToWeatherAPI(String url, String jwtToken) {
        this.jwtToken=jwtToken;
        this.url=url;
    }

    private String getPostNewWeatherURL() {
        return url + url_post_new_weather;
    }
    private String getDeleteAllWeatherURL() {
        return url + url_delete_all_weather;
    }


    JSONObject prepareJson(JSONObject record) {

        JSONObject result = new JSONObject();

        result.put("airportCode", getStr(record,"airportCode"));
        result.put("dayName", getStr(record,"dayName"));
        result.put("feelsLikeHighTemperature", getInt(record,"feelsLikeHighTemperature"));
        result.put("feelsLikeLowTemperature", getInt(record,"feelsLikeLowTemperature"));
        result.put("forecastDate", getStr(record,"forecastDate") + "T00:00:00.00Z");
        result.put("highTemperatureValue", getInt(record,"highTemperatureValue"));
        result.put("icon", getInt(record,"icon"));
        result.put("lowTemperatureValue", getInt(record,"lowTemperatureValue"));
        result.put("nightIcon", getInt(record,"nightIcon"));
        result.put("nightPhrase", getStr(record,"nightPhrase"));
        result.put("nightProbabilityOfPrecip", getInt(record,"nightProbabilityOfPrecip"));
        result.put("nightProbabilityOfPrecipUnits", getStr(record,"nightProbabilityOfPrecipUnits"));
        result.put("phrase", getStr(record,"phrase"));
        result.put("probabilityOfPrecip", getInt(record,"probabilityOfPrecip"));
        result.put("probabilityOfPrecipUnits", getStr(record,"probabilityOfPrecipUnits"));

        return result;
    }

    public void postNewWeatherRecrod(JSONObject record) throws UnirestException {

        JSONObject rc = null;

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.post(getPostNewWeatherURL())
            .header("Content-Type", "application/json")
            .header("accept", "*/*")
            .header("Authorization", "Bearer " + jwtToken)
            .body(record.toString())
            .asJson();

        if (response.getStatus()==201) {
            logger.info(  "Weather response: {}",response.getBody().toString());
            rc = response.getBody().getObject();
        } else {
            logger.error("No response from Weather ");
        }
    }

    public void deleteAllWeatherRecords() throws UnirestException {
        //step 1 : setup the weather class and pass in the base url/port and the jwttoken
        DeleteWeather delete =  new DeleteWeather(url,jwtToken);

        //step 2 : get list of flight id's so that we can delete
        ArrayList<String> idList = delete.getIdList();

        //step 3 : delete all departure flights
        delete.deleteIdList(idList);
    }


//    public static void main(String[] args) throws Exception {
//
//        String APIHost              = "http://localhost:8080";
//        String sitaFLIFOAPIKey      = "358235cb905d77c4f617c64526faa442";
//        String sitaWeatherAPIKey    = "cc1e533df7c95de5e0c4056ce7b133f4";
//        String homeAirport          = "DUB";
//        String userName             = "admin";
//        String password             = "admin";
//
//        //step 0 : get JWT Token
//        String jwtTokenWeatherAPI = getJWTToken(APIHost,userName,password);
//
//        PushWeatherToWeatherAPI pushWeatherApi = new PushWeatherToWeatherAPI(APIHost,jwtTokenWeatherAPI);
//
//        pushWeatherApi.deleteAllWeatherRecords();
//
//        // step 1 : download all arrival flights for the home Airport
//        SITAFlifoV3API flifoAPI =  new SITAFlifoV3API(sitaFLIFOAPIKey);
//
//        // step 2 : get a list of departure airport codes
//        ArrayList<String> departureAirports = flifoAPI.getDepartureAirports(homeAirport);
//
//        SITAWeatherAPI weatherAPI = new SITAWeatherAPI(sitaWeatherAPIKey);
//
//        // step 3 : cycle through the list of departure airports
//        departureAirports.forEach( airport -> {
//            try {
//
//                // step 4 : return the 7 day weather forecast for each airport code
//                JSONObject weatherForAirport = weatherAPI.getWeatherForecastForCity(airport);
//
//                try {
//
//                    // step 5 : get all forecast record
//                    final JSONArray weatherRecords = weatherForAirport.getJSONArray("weatherForecast");
//
//                    // step 6 : cycle through each forecast record
//                    weatherRecords.forEach(weatherRecord -> {
//
//                        // step 7 : for easy transformation add the sirport code to each weatherForecastRecord
//                        JSONObject record = ((JSONObject) weatherRecord);
//                        record.put("airportCode", airport);
//
//                        // step 8 : transform the SITAFliFo Weather forecast record to  yej new weatherAPIPayload
//                        JSONObject weatherAPIPayload = pushWeatherApi.prepareJson(record);
//
//                        // step 9 : post the new record to the weather API
//                        try {
//                            pushWeatherApi.postNewWeatherRecrod(weatherAPIPayload);
//                        } catch (UnirestException e) {
//                            logger.error("error posting New Weather Recrod", e);
//                        }
//                    });
//
//                } catch (org.json.JSONException e) {
//                    logger.error("error no weather found for this city :" + airport , e);
//                }
//
//
//
//            } catch (UnirestException e) {
//                logger.error("error getting SITA Weather information", e);
//            }
//
//        });
//
//
//
//    }

}
