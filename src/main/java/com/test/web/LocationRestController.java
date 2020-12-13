package com.test.web;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class LocationRestController {
    @Autowired
    RestTemplate restTemplate;
    private String API_KEY = "b4bc98201ef4fbaca2e89f55e1553217";

    @GetMapping("/locations")
    public List<Location> getName(@RequestParam Double lat, @RequestParam Double lang, @RequestParam Integer maxDistance) {
        JSONArray employeeList = null;
        List<Location> locations = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("locations.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            employeeList = (JSONArray) obj;
            System.out.println(employeeList);

            //Iterate over location array
            employeeList.forEach(emp -> addLocation((JSONObject) emp, locations, lat, lang, maxDistance));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return locations;
    }


    private void addLocation(JSONObject employee, List<Location> locations, double lat, double lang, int maxDistance) {
        Location location = new Location();
        location.setAddress((String) employee.get("address"));
        location.setLat((double) employee.get("lat"));
        location.setLng((double) employee.get("lng"));
        location.setUrl((String) employee.get("url"));
        if (distance(location.getLat(), location.getLng(), lat, lang, 'K') <= maxDistance) {
            if (isGoodWeather(location.getLat(), location.getLng())) {
                locations.add(location);
            }
        }
    }

    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public boolean isGoodWeather(double lat, double lon) {
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY;
        JSONObject object = this.restTemplate.getForObject(url, JSONObject.class);
        if (object != null && object.get("main") != null) {
            double minTemp = (double) ((LinkedHashMap) object.get("main")).get("temp_min");
            double maxTemp = (double) ((LinkedHashMap) object.get("main")).get("temp_max");
            if (minTemp > 250 && maxTemp < 290) {
                return true;
            }
        }
        return false;
    }
}
