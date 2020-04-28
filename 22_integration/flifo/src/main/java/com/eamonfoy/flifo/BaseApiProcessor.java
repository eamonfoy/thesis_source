package com.eamonfoy.flifo;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;

public class BaseApiProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PushWeatherToWeatherAPI.class);


    static public void toFile(JSONObject flifo, String fileName) throws IOException {
        FileWriter file = null;

        try {

            // Constructs a FileWriter given a file name, using the platform's default charset
            file = new FileWriter(fileName);
            file.write(flifo.toString());
        } finally {
            if (file != null) {
                file.close();
            }

        }
    }

    static public String get(JSONObject obj, String key) {

        String rc = "";
        try {
            rc = obj.getString(key);
        } catch (Exception x) {

        }
        return rc;

    }

    static public String getDate(JSONObject obj, String key) {

        String rc = "";
        try {
            rc = obj.getString(key);
        } catch (Exception x) {

        }

        if (!rc.isEmpty()) {
            rc = fixDate(rc);
        }

        return rc;

    }



    static String getStr(JSONObject obj, String key) {

        String rc = "";
        try {
            rc = obj.getString(key);
        } catch (Exception x) {

        }
        return rc;
    }

    static int getInt(JSONObject obj, String key) {

        int rc = 0;
        try {
            rc = obj.getInt(key);
        } catch (Exception x) {

        }
        return rc;
    }


    public static String getJWTToken(String host, String userName, String password) throws Exception {

        String token = null;

        Unirest.setTimeouts(0,0);
        HttpResponse<JsonNode> response = Unirest.post(host+ "/api/authenticate")
            .header("Content-Type", "application/json")
            .body(String.format("{\n    \"password\": \"%s\",\n    \"username\": \"%s\"\n}", userName, password))
            .asJson();


        if (response.getStatus()==200) {
            logger.info(  "Login response: {}",response.getBody().toString());
            JSONObject rc = response.getBody().getObject();
            if(rc!=null) {
                token = rc.getString("id_token");
            }
        } else {
            logger.error("No response from Login request :" + response.getStatus());

            throw new Exception("No response from Login request :" + response.getStatus() + ", " + response.getStatusText());

        }

        return token;
    }

    public static String fixDate(String flifoDate) {
        StringBuffer str = new StringBuffer(flifoDate);
        int len = str.length();
        str.insert(len-2,':');

        OffsetDateTime odt = OffsetDateTime.parse( str.toString());
        Instant instant = odt.toInstant();

        return instant.toString();
    }

}
