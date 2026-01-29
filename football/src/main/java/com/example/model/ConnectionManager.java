package com.example.model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConnectionManager {
    public static MongoDatabase getConnection() {
        String uri = ""; 
        MongoClient client = MongoClients.create(uri);
        MongoDatabase db = client.getDatabase("Football");
        return db;
    }
}
