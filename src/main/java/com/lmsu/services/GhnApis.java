package com.lmsu.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Date;

public class GhnApis {
    private static String token = "***REMOVED***";
    private static String shopId = "***REMOVED***";

    //This function will return the amount of time needed to delivering in DAY(S)
    public static Date calculateExpectedDeliveryTime(String to_district_id, String to_ward_code) {
        String output = "";
        try {
            URL url = new URL("https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/leadtime");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.addRequestProperty("ShopId", shopId);
            conn.addRequestProperty("token", token);
            conn.setDoOutput(true);
            // Quan 9 district code: 1451

            String requestBody = "    {\"from_district_id\": 1451,\n" +
                    "    \"to_district_id\": " + to_district_id + ",\n" +
                    "    \"to_ward_code\": \"" + to_ward_code + "\",\n" +
                    "    \"service_id\":53320}";
            OutputStream os = conn.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()), "UTF-8"));
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
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        JsonParser parser = new JsonParser();
        JsonObject outputObject = parser.parse(output).getAsJsonObject();
        JsonObject objData = outputObject.getAsJsonObject("data");
        String leadtime = objData.get("leadtime").getAsString();
        long expectedTimeInSeconds = Long.parseLong(leadtime);
        //Because Date constructor only accept millis but we have second so we need to multiply 1000
        Date expectedDate = new Date(expectedTimeInSeconds * 1000);
        return expectedDate;
    }

    public static String cancelOrder(String order_code) {
        String output = "";
        try {
            URL url = new URL("https://dev-online-gateway.ghn.vn/shiip/public-api/v2/switch-status/cancel");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.addRequestProperty("ShopId", shopId);
            conn.addRequestProperty("token", token);
            conn.setDoOutput(true);
            String requestBody = "{\"order_codes\":[\"" + order_code + "\"]}";
            OutputStream os = conn.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()), "UTF-8"));
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
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static String createOrder(String to_district_id,
                                     String to_ward_code,
                                     int quantity,
                                     String to_name,
                                     String to_phone, //must be correct, 0123456789 is invalid somehow
                                     String to_address) {

        String output = "";
        try {
//            {
//                "code": 200,
//                    "message": "Success",
//                    "data":[
//                {
//                    "service_id":53319
//                    "short_name":"Nhanh"
//                    "service_type_id":1
//                },
//                {
//                    "service_id":53320
//                    "short_name":"Chuẩn"
//                    "service_type_id":2
//                },
//                {
//                    "service_id":53330
//                    "short_name":""
//                    "service_type_id":0
//                },
//                {
//                    "service_id":53321
//                    "short_name":"Tiết kiệm"
//                    "service_type_id":3
//                },
//    ]
//            }
            URL url = new URL("https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.addRequestProperty("ShopId", shopId);
            conn.addRequestProperty("token", token);
            conn.setDoOutput(true);
            // Quan 9 district code: 1451
            int avgHeight = 4 * quantity;
            int avgWidth = 15;
            int avgLength = 22;
            int avgWeight = 50 * quantity;
            String requestBody = "{\n" +
                    "    \"payment_type_id\": 2,\n" +
                    "    \"required_note\": \"CHOTHUHANG\",\n" +
                    "    \"to_name\": \"" + to_name + "\",\n" +
                    "    \"to_phone\": \"" + to_phone + "\",\n" +
                    "    \"to_address\": \"" + to_address + "\",\n" +
                    "    \"to_ward_code\": \"" + to_ward_code + "\",\n" +
                    "    \"to_district_id\": " + to_district_id + ",\n" +
                    "    \"weight\": " + avgWeight + ",\n" +
                    "    \"length\": " + avgLength + ",\n" +
                    "    \"width\": " + avgWidth + ",\n" +
                    "    \"height\": " + avgHeight + ",\n" +
                    "    \"pick_station_id\": 1444,\n" +
                    "    \"service_id\": null,\n" +
                    "    \"service_type_id\":2,\n" +
                    "    \"items\": [\n" +
                    "         {\n" +
                    "             \"name\":\"Sách\",\n" +
                    "             \"quantity\": " + quantity + "\n" +
                    "         }\n" +
                    "     ]\n" +
                    "}";
            OutputStream os = conn.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()), "UTF-8"));
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
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static String getWardList(String district_id) {
        String output = "";
        try {
            URL url = new URL("https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.addRequestProperty("token", token);
            conn.setDoOutput(true);
            // Quan 9 district code: 1451
            String requestBody = "{\"district_id\":" + district_id + "}";
            OutputStream os = conn.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()), "UTF-8"));
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
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static String getDistrictList(String province_id) {
        String output = "";
        try {
            URL url = new URL("https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.addRequestProperty("token", token);
            conn.setDoOutput(true);
            // Quan 9 district code: 1451
            String requestBody = "{\"province_id\":" + province_id + "}";
            OutputStream os = conn.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()), "UTF-8"));
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
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static String getProvinceList() {
        String output = "";
        try {
            URL url = new URL("https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.addRequestProperty("token", token);

            conn.setDoOutput(true);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()), "UTF-8"));
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
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static String calculateFee(String to_district_id, String to_ward_code, int quantity) {
        String output = "";
        try {
            URL url = new URL("https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.addRequestProperty("token", token);
            conn.addRequestProperty("ShopId", shopId);
            conn.setDoOutput(true);
            int avgHeight = 4 * quantity;
            int avgWidth = 15;
            int avgLength = 22;
            int avgWeight = 50 * quantity;
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
                    (conn.getInputStream()), "UTF-8"));
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
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return output;
    }
}
