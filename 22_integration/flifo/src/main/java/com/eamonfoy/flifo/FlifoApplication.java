package com.eamonfoy.flifo;


import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class FlifoApplication extends BaseApiProcessor {


    public FlifoApplication() throws IOException, UnirestException {

        SITAFlifoV3API flifo =  new SITAFlifoV3API("358235cb905d77c4f617c64526faa442");

        JSONObject arrivals = flifo.getArivals("DUB");

        JSONObject departures = flifo.getDepartures("DUB");


        toFile( arrivals,"flifo_arrivals.json" );
        toFile( departures,"flifo_departures.json" );

    }

    public static void main(String[] args) {
        SpringApplication.run(FlifoApplication.class, args);

    }

}
