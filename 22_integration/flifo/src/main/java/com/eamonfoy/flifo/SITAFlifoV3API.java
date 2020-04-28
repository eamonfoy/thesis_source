package com.eamonfoy.flifo;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SITAFlifoV3API extends BaseApiProcessor {

    private static final Logger logger = LoggerFactory.getLogger(SITAFlifoV3API.class);

    private String  baseUrl = "https://flifo.api.aero/flifo/v3/flights/%s//%s?futureWindow=%d&pastWindow=%d";
    private int     futureWindow = 10;
    private int     pastWindow = 10;
    private String  apiKey;


    public SITAFlifoV3API(String apiKey) {
        this.apiKey=apiKey;
    }

    private JSONObject execute(String homeAirport, String type) throws UnirestException {
        JSONObject rc = null;

        String url ;
        url = String.format(baseUrl, homeAirport, type,futureWindow,pastWindow);

        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("x-apikey", this.apiKey);

        logger.info("Flifo request: {}", url);

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.get(url)
                .headers(headers)
                .asJson();

        if (response.getStatus()==200) {
            logger.info(  "Flifo response: {}",response.getBody().toString());
            rc = response.getBody().getObject();
        } else {
            logger.error("No response from Flifo");
        }
        return rc;
    }

    public JSONObject getArivals(String homeAirport) throws UnirestException {
        return execute(homeAirport, "A");
    }

    public JSONObject getDepartures(String homeAirport) throws UnirestException {
        return execute(homeAirport, "D");
    }

    public ArrayList<String> getDepartureAirports(String homeAirport) throws UnirestException {
        ArrayList<String> rc =  new ArrayList<>();

        JSONObject detartures = execute(homeAirport, "D");

        final JSONArray flightrecords = detartures.getJSONArray("flightRecord");

        for (int i = 0; i < flightrecords.length(); i++) {
            final JSONObject record = flightrecords.getJSONObject(i);
            rc.add(record.getString("airportCode"));
        }

        // remove the duplicates
        List<String> rc2 = rc.stream().distinct().collect(Collectors.toList());

        rc = new ArrayList<String>(rc2);

        return rc;
    }



}
