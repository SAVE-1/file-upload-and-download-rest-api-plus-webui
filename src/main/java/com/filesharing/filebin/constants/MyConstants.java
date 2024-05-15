package com.filesharing.filebin.constants;

import com.filesharing.filebin.requests.RequestStates;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class MyConstants {

    public static final String FILE_ALREADY_EXISTS = jsonifyString(String.format("{'status':{'code':%s, 'message': 'file already exists'}}",
            RequestStates.FILE_ALREADY_EXISTS.ordinal()));

    // this function exists solely, so that I don't need to
    private static String jsonifyString(String stringToParse) {
        Gson g = new Gson();
        JsonElement root = g.fromJson(stringToParse, JsonElement.class);
        return g.toJson(root);
    }
}
