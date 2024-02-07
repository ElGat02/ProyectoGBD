package com.mycompany.conexionmongodb;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConexion {

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public static void conectar() {
        mongoClient = MongoClients.create("mongodb://localhost:27017"); // Usa tu URI de conexión
        database = mongoClient.getDatabase("gestionbase"); // Nombre de tu base de datos
    }

    public static MongoDatabase obtenerDB() {
        return database;
    }

    public static void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
