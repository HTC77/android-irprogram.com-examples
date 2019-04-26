/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irprogram.ppweb;

import org.json.JSONObject;

/**
 *
 * @author HTC
 */
public class JSONUtils {
    private final static String FLAG_SELF = "self";
    private final static String FLAG_NEW = "new";
    private final static String FLAG_MESSAGE = "message";
    private final static String FLAG_EXIT = "exit";

    public JSONUtils() {
        
    }
    
    public String getClientDetailsJson(String sessionId, String message){      
       String json = null;	
        try
        {
            JSONObject jObj = new JSONObject();

            jObj.put("flag", FLAG_SELF);
            jObj.put("sessionId", sessionId);
            jObj.put("message", message);

            json = jObj.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return json; 
    }
    
    public String getNewClientJson( String sessionId, String name, String msg, int onlineCount)
    {
	String json = null;
	
	try
	{
            JSONObject jObj = new JSONObject();

            jObj.put("flag", FLAG_NEW);
            jObj.put("name", name);
            jObj.put("sessionId", sessionId);
            jObj.put("message", msg);
            jObj.put("onlineCount", onlineCount);

            json = jObj.toString();
	}
	catch(Exception ex)
	{
            ex.printStackTrace();
	}
	
	return json;
    }

    public String getClientExitJson( String sessionId, String name, String msg, int onlineCount)
    {
	String json = null;
	
	try
	{
            JSONObject jObj = new JSONObject();

            jObj.put("flag", FLAG_EXIT);
            jObj.put("name", name);
            jObj.put("sessionId", sessionId);
            jObj.put("message", msg);
            jObj.put("onlineCount", onlineCount);

            json = jObj.toString();
	}
	catch(Exception ex)
	{
            ex.printStackTrace();
	}
	
	return json;
    }

    public String getSendAllMessageJson(String sessionId, String fromName, String msg)
    {
	String json = null;
	
	try
	{
            JSONObject jObj = new JSONObject();

            jObj.put("flag", FLAG_MESSAGE);
            jObj.put("name", fromName);
            jObj.put("sessionId", sessionId);
            jObj.put("message", msg);

            json = jObj.toString();
	}
	catch(Exception ex)
	{
            ex.printStackTrace();
	}
	
	return json;
    }
    
}
