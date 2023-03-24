package com.example.plugin;

import com.example.plugin.services.HostManager;

public class App {
    public static void main(String args[]) throws Exception {
        System.out.println(HostManager.fetchHosts());

    }

}