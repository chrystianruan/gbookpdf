package com.api.gbookpdf.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
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
    public static Map<String, String> makeMessageWithToken(String message, String token) {
        Map<String, String> finalMessage = makeMessage(message);
        finalMessage.put("token", token);

        return finalMessage;
    }
}
