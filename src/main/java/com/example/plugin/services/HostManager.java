package com.example.plugin.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.plugin.model.Host;

public class HostManager {

    private static final String BASE = "https://172.17.41.44/";
    private static final String AUTH_ENDPOINT = "rest/com/vmware/cis/session";
    private static final String HOST_LIST_ENDPOINT = "rest/vcenter/host";
    private static final String USERNAME = "calsoft@vsphere.local";
    private static final String PASSWORD = "Passwd!05";

    public static List<Host> fetchHosts() throws Exception {
        RestClient.AllowAllHosts();
        String key = getApiKey();
        List<Host> a = getListOfHosts(key);
        return a;
    }

    public static String getApiKey() {
        Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", RestClient.createBasicAuthToken(USERNAME, PASSWORD));

        try {
            String response = RestClient.makeCall(BASE + AUTH_ENDPOINT, "POST", null, headers);
            Map<String, Object> responseMap = Common.parseJsonToMap(response);
            return (String) responseMap.get("value");

        } catch (IOException e) {
            System.err.println("BAD AUTH URL");
            return null;
        }
    }

    public static List<Host> getListOfHosts(String api_key) {
        Map<String, String> headers = new HashMap<>();
        headers.put("vmware-api-session-id", api_key);

        try {
            String response = RestClient.makeCall(BASE + HOST_LIST_ENDPOINT, "GET", null, headers);
            List<Host> hostList = Common.parseHostsFromJson(response);
            return hostList;
        } catch (IOException e) {
            System.err.println("BAD HOST LIST URL");
            return null;
        }
    }

}
