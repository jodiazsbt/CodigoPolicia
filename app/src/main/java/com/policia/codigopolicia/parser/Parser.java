package com.policia.codigopolicia.parser;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * Created by 1085253556 on 20/12/2017.
 */

public class Parser {

    public static String XMLtoJSON(String xml_to_parse) throws JSONException {
        JSONObject jsonObj = null;
        jsonObj = XML.toJSONObject(xml_to_parse);
        return String.valueOf(jsonObj);
    }
}
