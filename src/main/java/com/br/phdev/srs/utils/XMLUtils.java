/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.utils;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class XMLUtils {

    public String getFirstElement(String tag, String body) {
        int open = body.indexOf("<" + tag + ">") + tag.length() + 2;
        int close = body.indexOf("</" + tag + ">");
        return body.substring(open, close);
    }        

}
