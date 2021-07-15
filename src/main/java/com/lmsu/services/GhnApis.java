package com.lmsu.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GhnApis {
    public static String getWardList(String district_id){
        String output = "";
        try {
            URL url = new URL("https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.addRequestProperty("token", "***REMOVED***");
            conn.setDoOutput(true);
            // Quan 9 district code: 1451
            String requestBody = "{\"district_id\":"+district_id+"}";
            OutputStream os = conn.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String line;
            while ((line = br.readLine()) != null) {
                output += line;
            }
            conn.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e){
            e.printStackTrace();
        }
        return output;
    }
    public static String getDistrictList(String province_id){
        String output = "";
        try {
            URL url = new URL("https://online-gateway.ghn.vn/shiip/public-api/master-data/district");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.addRequestProperty("token", "***REMOVED***");
            conn.setDoOutput(true);
            // Quan 9 district code: 1451
            String requestBody = "{\"province_id\":"+province_id+"}";
            OutputStream os = conn.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String line;
            while ((line = br.readLine()) != null) {
                output += line;
            }
            conn.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e){
            e.printStackTrace();
        }
        return output;
    }
    public static String getProvinceList(){
        String output = "";
        try {
            URL url = new URL("https://online-gateway.ghn.vn/shiip/public-api/master-data/province");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.addRequestProperty("token", "***REMOVED***");
            conn.setDoOutput(true);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String line;
            while ((line = br.readLine()) != null) {
                output += line;
            }
            conn.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e){
            e.printStackTrace();
        }
        return output;
    }
    public static String calculateFee(String to_district_id, String to_ward_code, int quantity) {
        String output = "";
        try {
            URL url = new URL("https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.addRequestProperty("token", "***REMOVED***");
            conn.addRequestProperty("ShopId", "***REMOVED***");
            conn.setDoOutput(true);
            int avgHeight = 4 * quantity;
            int avgWidth = 15 * quantity;
            int avgLength = 22 * quantity;
            int avgWeight = 500 * quantity;
            // Quan 9 district code: 1451
            String requestBody = "{\"service_id\":null,\"service_type_id\": 2,\"insurance_value\":10000,"
                    + "\"coupon\":null,\"from_district_id\":1451,"
                    + "\"to_district_id\":" + to_district_id + " ,\"to_ward_code\":" + to_ward_code + ","
                    + "\"height\":" + avgHeight + ",\"length\":" + avgLength + ",\"weight\":" + avgWeight + ",\"width\":" + avgWidth + "}";
            OutputStream os = conn.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String line;
            while ((line = br.readLine()) != null) {
                output += line;
            }
            conn.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e){
            e.printStackTrace();
        }
        return output;
    }
}
