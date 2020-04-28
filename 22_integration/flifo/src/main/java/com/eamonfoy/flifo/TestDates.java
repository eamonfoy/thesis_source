package com.eamonfoy.flifo;

import org.json.JSONObject;

import java.time.Instant;
import java.time.OffsetDateTime;

public class TestDates {


    public static String fixDate(String flifoDate) {
        StringBuffer str = new StringBuffer(flifoDate);
        int len = str.length();
        str.insert(len-2,':');

        OffsetDateTime odt = OffsetDateTime.parse( str.toString());
        Instant instant = odt.toInstant();

        return instant.toString();
    }



    public static void main(String[] args) throws Exception {

        OffsetDateTime odt = OffsetDateTime.parse( "2010-12-27T10:50:44.000-08:00" );
        Instant instant = odt.toInstant();

        String s = instant.toString();

        System.out.println(s);

        odt = OffsetDateTime.parse( "2020-04-02T21:30:00+01:00" );
        instant = odt.toInstant();

        s = instant.toString();

        System.out.println(s);


        s = "2020-04-02T21:30:00+0100";

        System.out.println(fixDate(s));

    }

}


