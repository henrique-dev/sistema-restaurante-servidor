/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.phdev.srs.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar
 */
public class HttpConnection {        
    
    //private final String urlContext = "http://35.202.51.59/mrfood/";
    //private final String urlContext = "http://localhost:8084/mrfood/";
    
    public HttpURLConnection getConnection(String url) {
        try {            
            URL urlToConnect = new URL(url);
            return (HttpURLConnection) urlToConnect.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
