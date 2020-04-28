package com.eamonfoy.flifo;

import com.mashape.unirest.http.HttpResponse;
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

public class PushFlifoToDepartureFlightAPI extends BaseApiProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PushFlifoToDepartureFlightAPI.class);
    final String url_post_new_arrival_flights = "/services/flight/api/departure-flights";
    final String url_delete_all_arrivals = "/services/flight/api/departure-flights/all";

    private String jwtToken;
    private String url;

    public PushFlifoToDepartureFlightAPI(String url, String jwtToken) {
        this.jwtToken=jwtToken;
        this.url=url;
    }


    private String getPostNewFlightURL() {
        return url + url_post_new_arrival_flights;
    }

    private String getDeleteAllArrivalFlightsURL() {
        return url + url_delete_all_arrivals;
    }


    JSONObject prepareJson(JSONObject record) {

        JSONObject result = new JSONObject();

        result.put("actual",getDate(record,"actual"));
        result.put("aircraft",get(record,"aircraft"));

        JSONObject operatingCarrier = record.getJSONObject("operatingCarrier");
        result.put("airline",get(operatingCarrier,"airline"));
        result.put("airportCode",get(record,"airportCode"));
        result.put("flightNumber",get(operatingCarrier,"flightNumber"));

        result.put("city",get(record,"city"));
        result.put("gate",get(record,"gate"));
        result.put("duration",get(record,"duration"));
        result.put("estimated",getDate(record,"estimated"));
        result.put("scheduled",getDate(record,"scheduled"));
        result.put("status",get(record,"status"));
        result.put("statusText",get(record,"statusText"));
        result.put("tailNumber",get(record,"tailNumber"));
        result.put("terminal",get(record,"terminal"));

        return result;
    }

    public ArrayList transform(InputStream jsonInputStream) throws IOException {
        ArrayList<JSONObject> rc = new ArrayList();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = jsonInputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        byte[] byteArray = buffer.toByteArray();

        String text = new String(byteArray, StandardCharsets.UTF_8);

        JSONObject obj = new JSONObject(text);

        final JSONArray flightrecords = obj.getJSONArray("flightRecord");

        for (int i = 0; i < flightrecords.length(); i++) {
            final JSONObject record = flightrecords.getJSONObject(i);
            JSONObject arrival = prepareJson(record);
            rc.add(arrival);
        }

        return rc;
    }


    public void postDepartureRecrod(JSONObject record) throws UnirestException {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(getPostNewFlightURL())
            .header("Content-Type", "application/json")
            .header("accept", "*/*")
            .header("Authorization", "Bearer " + jwtToken)
            .body(record.toString())
            .asString();

        if (response.getStatus()==201) {
            logger.info("successful postDepartureRecord response: {}",response.getBody().toString());

        } else {
            logger.error("Issue postDepartureRecord: {}", response.getBody().toString());
        }


    }

    public void deleteAllArrivalFlights() throws UnirestException {

        //step 1 : download all departure flights
        DeleteDepartureFlights delete =  new DeleteDepartureFlights(url,jwtToken);

        //step 2 : get list of flight id's so that we can delete
        ArrayList<String> idList = delete.getIdList();

        //step 3 : delete all departure flights
        delete.deleteIdList(idList);
    }

    public  void processFlight(JSONObject record) {
        logger.info("process Departure Flight: {}",record);
        try {
            postDepartureRecrod(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void processAllArrivalFlights(InputStream jsonInputStream) throws IOException {

        ArrayList<JSONObject> rc = this.transform(jsonInputStream);

        rc.forEach(record -> {

            this.processFlight(record);

            try {
                sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


//    public static void main(String[] args) throws Exception {
//
//        String APIHost                  = "http://localhost:8080";
//        String sitaFLIFOAPIKey          = "358235cb905d77c4f617c64526faa442";
//        String arrivalFlifoJsonFileName = "flifo_arrivals.json";
//        String homeAirport              = "DUB";
//        String userName                 = "admin";
//        String password                 = "admin";
//
//        //step 0 : get JWT Token
//        String jwtToken = getJWTToken(APIHost,userName,password);
//
//        //step 1 : download all arrival flights for the home Airport
//        SITAFlifoV3API flifo =  new SITAFlifoV3API(sitaFLIFOAPIKey);
//        JSONObject arrivals = flifo.getDepartures(homeAirport);
//
//        //step 2 : Save this information for later
//        toFile( arrivals,arrivalFlifoJsonFileName );
//
//        // step 3 : read in flifo arrival flights
//        InputStream jsonInputStream = new FileInputStream(new File(arrivalFlifoJsonFileName));
//
//        // step 3 : push all new arrival flights in to ArrivalFlightAPI
//        PushFlifoToDepartureFlightAPI push = new PushFlifoToDepartureFlightAPI(APIHost,jwtToken);
//
//        // step 4 : delete all previous arrival flights
//        push.deleteAllArrivalFlights();
//
//        // step 5 : post new arrival flights to the ArrivalFlightAPI
//        push.processAllArrivalFlights(jsonInputStream);
//    }

}
