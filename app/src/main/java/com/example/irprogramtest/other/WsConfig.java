package com.example.irprogramtest.other;

public class WsConfig {
    public final static int PORT_NUMBER = 8080;
    public final static String HOST_ADDRESS = "192.168.56.1";//localhost is: 10.0.2.2 (192.168.56.1 on genymotion)
    public final static String WEB_SOCKET_URL =
            "ws://" +HOST_ADDRESS+ ":" +PORT_NUMBER+ "/PechPechOnWeb/chat?name=";

}
