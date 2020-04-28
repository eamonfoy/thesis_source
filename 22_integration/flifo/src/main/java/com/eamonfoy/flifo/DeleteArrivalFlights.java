package com.eamonfoy.flifo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DeleteArrivalFlights extends BaseApiProcessor {


    private static final Logger logger = LoggerFactory.getLogger(DeleteArrivalFlights.class);

    final String url_arrival_flights = "/services/flight/api/arrival-flights?size=10000";
    final String url_delete_arrival_flight = "/services/flight/api/arrival-flights/";

    private String jwtToken;
    private String baseUrl;

    public DeleteArrivalFlights(String baseUrl, String jwtToken) {
        this.jwtToken=jwtToken;
        this.baseUrl=baseUrl;
    }


    private String getArrivalFlightURL() {
        return baseUrl + url_arrival_flights;
    }

    private String getDeleteArrivalFlightURL(String id) { return baseUrl + url_delete_arrival_flight + id; }

    public JsonNode getArrivals() throws UnirestException {
        JsonNode rc = null;

        String url ;
        url = getArrivalFlightURL();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + jwtToken);

        logger.info("getArrivals URL: {}", url);

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.get(url)
                .headers(headers)
                .asJson();

        if (response.getStatus()==200) {
            logger.info(  "getArrivals response: {}",response.getBody().toString());
            rc = response.getBody();
        } else {
            logger.error("getArrivals: No response ");
        }
        return rc;
    }

    public ArrayList<String> getArrivalsIdList() throws UnirestException {

        ArrayList<String> rc = new ArrayList<>();

        JSONObject arrivals = new JSONObject(getArrivals());

        JSONArray arrayNode = arrivals.getJSONArray("array");

        arrayNode.forEach( node -> {

            JSONObject n =(JSONObject)node;

            rc.add(String.valueOf(n.getInt("id")));
            logger.info("Arrival flight : {}", node);

        });

        logger.info("idList : {}", rc);

        return rc;
    }


    public void delete(String id) throws UnirestException {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.delete( getDeleteArrivalFlightURL(id) )
                .header("Authorization", "Bearer " + jwtToken)
                .asString();

        if (response.getStatus()==204) {
            logger.info("DeleteArrivalFlight {} response: {}:{}", id, response.getStatus());

        } else {
            logger.error("Issue deleting Arrival flight {}: {}:{}", id, response.getStatus());
        }

    }

    public void deleteIdList(ArrayList<String> idList) throws UnirestException {

        idList.forEach( id -> {
            try {
                this.delete(id);
            } catch (UnirestException e) {
                logger.error("Issue deleting Arrival flight: {}: exception: {} ", id, e);
            }
        });
    }


    public static void main(String[] args) throws Exception {

        String APIHost                  = "http://10.0.0.11:81";
        String arrivalFlifoJsonFileName = "get_arrivals_flights.json";
        String userName                 = "admin";
        String password                 = "admin";

        //step 0 : get JWT Token
        String jwtToken = getJWTToken(APIHost,userName,password);

        //step 1 : download all arrival flights for the home Airport
        DeleteArrivalFlights deleteArrivalFlights =  new DeleteArrivalFlights(APIHost,jwtToken);

        //step 2 : Save this information for later
        //toFile( arrivals,arrivalFlifoJsonFileName );


        //step 3 : get list of flight id's so that we can delete
        ArrayList<String> idList = deleteArrivalFlights.getArrivalsIdList();

        logger.info("->idList : {}", idList);

        //step 4 : delete all arrival flights
        deleteArrivalFlights.deleteIdList(idList);
    }


}
