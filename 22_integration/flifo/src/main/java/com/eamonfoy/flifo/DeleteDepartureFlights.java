package com.eamonfoy.flifo;

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


public class DeleteDepartureFlights extends BaseApiProcessor {


    private static final Logger logger = LoggerFactory.getLogger(DeleteDepartureFlights.class);

    final String url_get = "/services/flight/api/departure-flights?size=10000";
    final String url_delete = "/services/flight/api/departure-flights/";

    private String jwtToken;
    private String baseUrl;
    private String context = "Departures";

    public DeleteDepartureFlights(String baseUrl, String jwtToken) {
        this.jwtToken=jwtToken;
        this.baseUrl=baseUrl;
    }

    private String getURL() {
        return baseUrl + url_get;
    }

    private String getDeleteURL(String id) { return baseUrl + url_delete + id; }

    public JsonNode get() throws UnirestException {
        JsonNode rc = null;

        String url ;
        url = getURL();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + jwtToken);

        logger.info("get{} URL: {}", context, url);

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.get(url)
                .headers(headers)
                .asJson();

        if (response.getStatus()==200) {
            logger.info(  "get{} response: {}",context, response.getBody().toString());
            rc = response.getBody();
        } else {
            logger.error("get{}: No response ",context);
        }
        return rc;
    }

    public ArrayList<String> getIdList() throws UnirestException {

        ArrayList<String> rc = new ArrayList<>();

        JSONObject arrivals = new JSONObject(get());

        JSONArray arrayNode = arrivals.getJSONArray("array");

        arrayNode.forEach( node -> {

            JSONObject n =(JSONObject)node;

            rc.add(String.valueOf(n.getInt("id")));
            logger.info("{} : {}", context ,node);

        });

        logger.info("idList : {}", rc);

        return rc;
    }


    public void delete(String id) throws UnirestException {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.delete( getDeleteURL(id) )
                .header("Authorization", "Bearer " + jwtToken)
                .asString();

        if (response.getStatus()==204) {
            logger.info("Delete{} {} response: {}:{}",context, id, response.getStatus());

        } else {
            logger.error("Issue deleting {} {}: {}:{}", context, id, response.getStatus());
        }

    }

    public void deleteIdList(ArrayList<String> idList) throws UnirestException {

        idList.forEach( id -> {
            try {
                this.delete(id);
            } catch (UnirestException e) {
                logger.error("Issue deleting {}: {}: exception: {} ", context, id, e);
            }
        });
    }


    public static void main(String[] args) throws Exception {

        String APIHost                  = "http://10.0.0.11:81";
        String userName                 = "admin";
        String password                 = "admin";

        //step 0 : get JWT Token
        String jwtToken = getJWTToken(APIHost,userName,password);

        //step 1 : download all departure flights
        DeleteDepartureFlights delete =  new DeleteDepartureFlights(APIHost,jwtToken);

        //step 2 : get list of flight id's so that we can delete
        ArrayList<String> idList = delete.getIdList();

        logger.info("->idList : {}", idList);

        //step 3 : delete all departure flights
        delete.deleteIdList(idList);
    }


}
