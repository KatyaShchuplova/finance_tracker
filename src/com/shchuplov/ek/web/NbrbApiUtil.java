package com.shchuplov.ek.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class NbrbApiUtil {
	
	private int CUR_ID = 145;
	private String URL = "http://www.nbrb.by/API/ExRates/Rates/" + CUR_ID + "?onDate=";
	
	private HttpURLConnection connection;
	private JSONObject rateJson;
	
	public NbrbApiUtil() {
		
	}
	   
    public String getRate(String date) throws Exception {
    	   	
        try {
        	connection = getConnection(URL+date);
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            	rateJson = receiveDataFromNbrb(connection);
            }

        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
                return rateJson.getString("Cur_OfficialRate");
            }
        }
		return rateJson.getString("Cur_OfficialRate");
    }
    

	private JSONObject receiveDataFromNbrb(HttpURLConnection connection) throws Exception {
		
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    	StringBuilder stringBuilder = new StringBuilder();
  	
    	String line;
        while ((line = in.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }
        JSONObject json = new JSONObject(stringBuilder.toString());
        return json;

	}

	
	private HttpURLConnection getConnection(String url) throws Exception {
		
		HttpURLConnection connection = null;
        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("charset", "utf-8");
        connection.setUseCaches(false);
        connection.setConnectTimeout(2500);
        connection.setReadTimeout(2500);
        connection.connect();       
		return connection;
	}


}
