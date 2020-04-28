package com.eamonfoy.flifo;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class SITAWeatherAPI extends BaseApiProcessor {

    private static final Logger logger = LoggerFactory.getLogger(SITAWeatherAPI.class);

    private String  baseUrl = "https://weather.api.aero/weather/v1/forecast/%s?duration=7";
    private String  apiKey;


    public SITAWeatherAPI(String apiKey) {
        this.apiKey=apiKey;
    }

    public JSONObject getWeatherForecastForCity(String airport) throws UnirestException {
        JSONObject rc = null;

        String url ;
        url = String.format(baseUrl, airport);

        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("x-apikey", this.apiKey);

        logger.info("Weather request: {}", url);

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.get(url)
                .headers(headers)
                .asJson();

        if (response.getStatus()==200) {
            logger.info(  "Weather response: {}",response.getBody().toString());
            rc = response.getBody().getObject();
        } else {
            logger.error("No response from Weather ");
        }
        return rc;
    }

    public static void main(String[] args) throws Exception {

        SITAWeatherAPI api =  new SITAWeatherAPI("cc1e533df7c95de5e0c4056ce7b133f4");

        JSONObject weather = api.getWeatherForecastForCity("DUB");

        toFile( weather,"weather_dub.json" );

    }


}
