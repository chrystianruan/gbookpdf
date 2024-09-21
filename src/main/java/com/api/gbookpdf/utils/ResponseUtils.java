package com.api.gbookpdf.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
    public static Map<String, String> makeMessage(String message) {
        Map<String, String> finalMessage = new HashMap<>();
        finalMessage.put("response", message);

        return finalMessage;
    }
    public static Map<String, String> makeMessageWithCode(String message, String code) {
        Map<String, String> finalMessage = makeMessage(message);
        finalMessage.put("code", code);

        return finalMessage;
    }
}
