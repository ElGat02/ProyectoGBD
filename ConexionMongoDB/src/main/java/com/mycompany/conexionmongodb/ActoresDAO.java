package com.mycompany.conexionmongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActoresDAO {

     MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
     MongoDatabase db = mongoClient.getDatabase("GestionBase");
     MongoCollection<Document> coleccion = db.getCollection("Actor");
    
    public void insertarActor(String nombre, String pais, Date fechaNacimiento, String tipo, String genero) {
        
        Document actor = new Document("nombre", nombre)
                .append("pais", pais)
                .append("fechaNacimiento", fechaNacimiento)
                .append("tipo", tipo) // principal, reparto o secundario
                .append("genero", genero); // hombre, mujer

        coleccion.insertOne(actor);
    }

    public void actualizarActor(String id, String nombre, String pais, Date fechaNacimiento, String tipo, String genero) {
        Document actualizacion = new Document();
        if (nombre != null && !nombre.isEmpty()) actualizacion.append("nombre", nombre);
        if (pais != null && !pais.isEmpty()) actualizacion.append("pais", pais);
        if (fechaNacimiento != null) actualizacion.append("fechaNacimiento", fechaNacimiento);
        if (tipo != null && !tipo.isEmpty()) actualizacion.append("tipo", tipo);
        if (genero != null && !genero.isEmpty()) actualizacion.append("genero", genero);

        coleccion.updateOne(Filters.eq("_id", new ObjectId(id)), new Document("$set", actualizacion));
    }

    public void eliminarActor(String id) {
        coleccion.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    public List<Document> obtenerActores() {
        List<Document> actores = new ArrayList<>();
        coleccion.find().iterator().forEachRemaining(actores::add);
        return actores;
    }

    // Método para buscar actores por nombre
    public List<Document> buscarActoresPorNombre(String nombre) {
        List<Document> actores = new ArrayList<>();
        coleccion.find(Filters.eq("nombre", nombre)).iterator().forEachRemaining(actores::add);
        return actores;
    }
}
