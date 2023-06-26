/*
package org.cs;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Arce {
    public static void main(String[] args) throws IOException {
        List list = Arrays.asList("username", "EQUALS", "Garimag");
        System.out.println(
                apiResponseParser(list, 0)
        );
    }

    public static List<Integer> apiResponseParser(List<String> inputList, int size) {
        Gson g = new Gson();

        String url = "https://raw.githubusercontent.com/arcjsonapi/ApiSampleData/master/api/users";
        JsonArray jsonArray = null;
        try {
            jsonArray = restCall(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonArray array = jsonArray;

        String s = inputList.get(2);
        List<Integer> result;

        result = IntStream
                .range(0, jsonArray.size())
                .mapToObj(i -> g.fromJson(array.get(i), User.class))
                .filter(user -> {
                            if (inputList.get(0).equals("username")) return user.getUsername().equals(s);
                            else return false;
                        }
                )
                .map(x -> x.getId())
                .collect(Collectors.toList());

        return result;
    }

    static JsonArray restCall(String address) throws IOException {

        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(2000);
        conn.setRequestProperty("Accept", "application/json");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP Error Code : "
                    + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        String output;
        StringBuffer buffer = new StringBuffer();
        while ((output = br.readLine()) != null) {
            buffer.append(output);
        }
        conn.disconnect();
        JsonParser parse = new JsonParser();
        JsonElement object = parse.parse(String.valueOf(buffer));
        return object.getAsJsonArray();
    }

}
class User {
    int id;
    String name;
    String username;
    String email;
    Address address;
    String website;
    Company company;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public String getWebsite() {
        return website;
    }

    public Company getCompany() {
        return company;
    }
}

class Address {
    String street;
    String suite;
    String city;
    String zipcode;
    Geo geo;

    public String getStreet() {
        return street;
    }

    public String getSuite() {
        return suite;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Geo getGeo() {
        return geo;
    }
}

class Geo {
    double lat;
    double lng;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}

class Company {
    String name;
    String basename;

    public String getName() {
        return name;
    }

    public String getBasename() {
        return basename;
    }
}*/
