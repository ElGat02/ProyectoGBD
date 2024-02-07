/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conexionmongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author David
 */
public class PremiosDAO {
    
    public List<Document> obtenerPremios() {
        MongoDatabase db = MongoDBConexion.obtenerDB();
        MongoCollection<Document> coleccion = db.getCollection("premios");

        List<Document> premios = new ArrayList<>();
        coleccion.find().iterator().forEachRemaining(premios::add);

        return premios;
    }
    
}
