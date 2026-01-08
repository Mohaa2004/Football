package com.example;

import java.util.Date;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class Main {

    public static void main(String[] args) {

        String uri = "";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("Football");
        MongoCollection<Document> partits = database.getCollection("Matches");

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ PARTITS ---");
            System.out.println("1. Listar todos los partidos");
            System.out.println("2. Añadir un partido");
            System.out.println("3. Actualizar goles de un partido");
            System.out.println("4. Eliminar un partido");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    for (Document doc : partits.find()) {
                        System.out.println(doc.toJson());
                    }
                    break;

                case 2:
                    System.out.print("Equipo local: ");
                    String local = sc.nextLine();
                    System.out.print("Equipo visitante: ");
                    String visitante = sc.nextLine();
                    System.out.print("Goles local: ");
                    int golesLocal = sc.nextInt();
                    System.out.print("Goles visitante: ");
                    int golesVisitante = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Competición: ");
                    String competicion = sc.nextLine();
                    Document nuevoPartit = new Document("equipoLocal", local)
                            .append("equipoVisitante", visitante)
                            .append("golesLocal", golesLocal)
                            .append("golesVisitante", golesVisitante)
                            .append("competicion", competicion)
                            .append("fechaPartido", new Date());
                    partits.insertOne(nuevoPartit);
                    System.out.println("Partido añadido!");
                    break;

                case 3:
                    System.out.print("Equipo local del partido a actualizar: ");
                    String localActualizar = sc.nextLine();
                    System.out.print("Nuevos goles local: ");
                    int nuevosGolesLocal = sc.nextInt();
                    System.out.print("Nuevos goles visitante: ");
                    int nuevosGolesVisitante = sc.nextInt();
                    sc.nextLine();
                    partits.updateOne(eq("equipoLocal", localActualizar),
                            set("golesLocal", nuevosGolesLocal));
                    partits.updateOne(eq("equipoLocal", localActualizar),
                            set("golesVisitante", nuevosGolesVisitante));
                    System.out.println("Partido actualizado!");
                    break;

                case 4:
                    System.out.print("Equipo visitante del partido a eliminar: ");
                    String visitanteEliminar = sc.nextLine();
                    partits.deleteOne(eq("equipoVisitante", visitanteEliminar));
                    System.out.println("Partido eliminado!");
                    break;

                case 5:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 5);

        sc.close();
        mongoClient.close();
    }
}
