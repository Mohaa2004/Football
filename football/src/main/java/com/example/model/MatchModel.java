package com.example.model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class MatchModel {

    private static final String BASE_URL = "https://api-three-black-69.vercel.app";
    private HttpClient client = HttpClient.newHttpClient();

    public void insertMatch(Match match) {
        String json = matchToJson(match);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/matches"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(r -> System.out.println("Insertat: " + r.statusCode()));
    }

    public void updateMatch(String id, Match match) {
        String json = matchToJson(match);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/matches/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(r -> System.out.println("Actualitzat: " + r.statusCode()));
    }

    public void deleteMatch(String id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/matches/" + id))
                .DELETE()
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(r -> System.out.println("Eliminat: " + r.statusCode()));
    }

    public List<Match> getAllMatches() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/matches"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();
            return parseMatches(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Match> getMatchesByDate(String start, String end) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/matches?start=" + start + "&end=" + end))
                    .GET()
                    .build();
            HttpResponse<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();
            return parseMatches(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Match> getMatchesByTeam(String team) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/matches?team=" + team))
                    .GET()
                    .build();
            HttpResponse<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();
            return parseMatches(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private String matchToJson(Match match) {
        Document doc = match.toDocument();
        return "{" +
                "\"equipoLocal\":\"" + doc.getString("equipoLocal") + "\"," +
                "\"equipoVisitante\":\"" + doc.getString("equipoVisitante") + "\"," +
                "\"golesLocal\":" + doc.getInteger("golesLocal") + "," +
                "\"golesVisitante\":" + doc.getInteger("golesVisitante") + "," +
                "\"competicion\":\"" + doc.getString("competicion") + "\"," +
                "\"fechaPartido\":\"" + doc.getString("fechaPartido") + "\"" +
                "}";
    }

    private List<Match> parseMatches(String json) {
        List<Match> list = new ArrayList<>();
        json = json.trim();
        if (json.startsWith("[")) json = json.substring(1, json.length() - 1);
        if (json.isBlank()) return list;

        int depth = 0;
        int start = 0;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') { if (depth == 0) start = i; depth++; }
            else if (c == '}') {
                depth--;
                if (depth == 0) {
                    String obj = json.substring(start, i + 1);
                    list.add(parseMatch(obj));
                }
            }
        }
        return list;
    }

    private Match parseMatch(String obj) {
        Document doc = new Document();
        doc.put("_id", new org.bson.types.ObjectId(extractValue(obj, "_id")));
        doc.put("equipoLocal", extractValue(obj, "equipoLocal"));
        doc.put("equipoVisitante", extractValue(obj, "equipoVisitante"));
        doc.put("golesLocal", Integer.parseInt(extractValue(obj, "golesLocal")));
        doc.put("golesVisitante", Integer.parseInt(extractValue(obj, "golesVisitante")));
        doc.put("competicion", extractValue(obj, "competicion"));
        doc.put("fechaPartido", extractValue(obj, "fechaPartido"));
        return new Match(doc);
    }

    private String extractValue(String json, String key) {
        String search = "\"" + key + "\"";
        int idx = json.indexOf(search);
        if (idx == -1) return "0";
        idx += search.length();
        while (idx < json.length() && (json.charAt(idx) == ':' || json.charAt(idx) == ' ')) idx++;
        if (json.charAt(idx) == '"') {
            int end = json.indexOf('"', idx + 1);
            return json.substring(idx + 1, end);
        } else {
            int end = idx;
            while (end < json.length() && json.charAt(end) != ',' && json.charAt(end) != '}') end++;
            return json.substring(idx, end).trim();
        }
    }
}