package com.eamonfoy.flifo;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

/**
 * Command-line options definition for example server.
 */
public class IntergrationOptions extends OptionsBase {

    @Option(
            name = "help",
            abbrev = 'h',
            help = "Prints usage info.",
            defaultValue = "true"
    )
    public boolean help;

    @Option(
            name = "APIHost",
            abbrev = 'a',
            help = "The API Host",
            category = "integration",
            //defaultValue = "http://localhost:8080"
            defaultValue = "http://10.0.0.11:80"
    )
    public String APIHost;

    @Option(
            name = "userName",
            abbrev = 'u',
            help = "User Name",
            category = "integration",
            defaultValue = "admin"
    )
    public String userName;


    @Option(
            name = "password",
            abbrev = 'p',
            help = "password",
            category = "integration",
            defaultValue = "admin"
    )
    public String password;


    @Option(
            name = "sitaFLIFOAPIKey",
            abbrev = 's',
            help = "Sita Flifo API Key",
            category = "integration",
            defaultValue = "358235cb905d77c4f617c64526faa442"
    )
    public String sitaFLIFOAPIKey;

    @Option(
            name = "arrivalFlifoJsonFileName",
            abbrev = 'f',
            help = "Arrival Flifo Json File Name",
            category = "integration",
            defaultValue = "flifo_arrivals.json"
    )
    public String arrivalFlifoJsonFileName;

    @Option(
            name = "homeAirport",
            abbrev = 'o',
            help = "Home Airport",
            category = "integration",
            defaultValue = "DUB"
    )
    public String homeAirport;

    @Option(
            name = "departureFlifoJsonFileName",
            abbrev = 'd',
            help = "Departure Flifo Json File Name",
            category = "integration",
            defaultValue = "flifo_departures.json"
    )
    public String departureFlifoJsonFileName;

    @Option(
            name = "sitaWeatherAPIKey",
            abbrev = 'w',
            help = "sita Weather API Key",
            category = "integration",
            defaultValue = "cc1e533df7c95de5e0c4056ce7b133f4"
    )
    public String sitaWeatherAPIKey;

}
