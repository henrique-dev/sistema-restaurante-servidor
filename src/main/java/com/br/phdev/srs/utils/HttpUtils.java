/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.utils;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class HttpUtils {
    
    public void showHeaders(HttpServletRequest req) {
        Enumeration<String> headers = req.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            System.out.print(headerName + ": ");
            Enumeration<String> headersValues = req.getHeaders(headerName);
            while (headersValues.hasMoreElements()) {
                String value = headersValues.nextElement();
                System.out.print(value);
                if (headersValues.hasMoreElements())
                    System.out.print("; ");
            }
            System.out.println("");
        }
    }
    
}
