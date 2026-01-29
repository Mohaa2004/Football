package com.example.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MatchModel {

    private MongoCollection<Document> collection;

    public MatchModel() {
        MongoDatabase db = ConnectionManager.getConnection();
        collection = db.getCollection("Matches");
    }

    public void insertMatch(Match match) {
        collection.insertOne(match.toDocument());
    }

    public void updateMatch(String id, Match match) {
        collection.updateOne(
                Filters.eq("_id", new ObjectId(id)),
                new Document("$set", match.toDocument())
        );
    }

    public void deleteMatch(String id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    public List<Match> getAllMatches() {
        List<Match> list = new ArrayList<>();
        for (Document d : collection.find()) {
            list.add(new Match(d));
        }
        return list;
    }

    public List<Match> getMatchesByDate(String start, String end) {
        List<Match> list = new ArrayList<>();
        for (Document d : collection.find(Filters.and(
                Filters.gte("fechaPartido", start),
                Filters.lte("fechaPartido", end)
        ))) {
            list.add(new Match(d));
        }
        return list;
    }

    public List<Match> getMatchesByTeam(String team) {
        List<Match> list = new ArrayList<>();
        for (Document d : collection.find(
                Filters.or(
                        Filters.regex("equipoLocal", team, "i"),
                        Filters.regex("equipoVisitante", team, "i")
                )
        )) {
            list.add(new Match(d));
        }
        return list;
    }
}
