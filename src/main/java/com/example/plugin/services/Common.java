package com.example.plugin.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.example.plugin.model.Host;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Common {
    static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static Map<String, Object> parseJsonToMap(String json) throws JsonMappingException, JsonProcessingException {
        return mapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });
    }

    public static List<Host> parseHostsFromJson(String json) throws JsonMappingException, JsonProcessingException {
        return Arrays.asList(mapper.readValue(json, new TypeReference<Map<String, Host[]>>() {
        }).get("value"));
    }

}
