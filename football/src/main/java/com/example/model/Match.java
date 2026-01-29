package com.example.model;

import org.bson.Document;

public class Match {

    private String id;
    private String equipoLocal;
    private String equipoVisitante;
    private int golesLocal;
    private int golesVisitante;
    private String competicion;
    private String fechaPartido;

    public Match(String equipoLocal, String equipoVisitante, int golesLocal, int golesVisitante, String competicion, String fechaPartido) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.competicion = competicion;
        this.fechaPartido = fechaPartido;
    }

    public Match(Document doc) {
        this.id = doc.getObjectId("_id").toString();
        this.equipoLocal = doc.getString("equipoLocal");
        this.equipoVisitante = doc.getString("equipoVisitante");
        this.golesLocal = doc.getInteger("golesLocal");
        this.golesVisitante = doc.getInteger("golesVisitante");
        this.competicion = doc.getString("competicion");
        this.fechaPartido = doc.getString("fechaPartido");
    }

    public Document toDocument() {
        return new Document("equipoLocal", equipoLocal)
                .append("equipoVisitante", equipoVisitante)
                .append("golesLocal", golesLocal)
                .append("golesVisitante", golesVisitante)
                .append("competicion", competicion)
                .append("fechaPartido", fechaPartido);
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return id + " | " + equipoLocal + " vs " + equipoVisitante + " | " + golesLocal + "-" + golesVisitante + " | " + fechaPartido;
    }
}
