package me.toadless.Krisp.Utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class Config {
    public static String token;
    public static String prefix;
    public static String database;
    public static void loadSettings() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("settings.json"));
        String out = br.lines().collect(Collectors.joining("\n"));
        JSONObject obj = new JSONObject(out);
        token = obj.getString("token");
        prefix = obj.getString("prefix");
        database = obj.getString("mongodb");
    }
}
