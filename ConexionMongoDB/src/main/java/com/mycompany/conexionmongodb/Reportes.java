package com.mycompany.conexionmongodb;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import java.util.Arrays;
import java.util.Date;
import org.bson.conversions.Bson;

/**
 *
 * @author SamuelPardo
 */
public class Reportes {
    public static void main(String[] args) {
        
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("gestionbase");
        MongoCollection<Document> collection = database.getCollection("peliculas");
           
        System.out.println("----------------------------------------------------");
        System.out.println("----Películas Estrenadas en un Periodo de Tiempo----");
        System.out.println("----------------------------------------------------");
        // Consulta Películas Estrenadas en un Periodo de Tiempo
        
        Bson match = Aggregates.match(
                Filters.and(
                        Filters.gte("releaseDate", new Date(2022-1900, 1, 1)), // Año, Mes (0-index), Día
                        Filters.lte("releaseDate", new Date(2022-1900, 11, 31))
                )
        );
        
        // Películas con Mayor Recaudación
        System.out.println("----------------------------------------------------");
        System.out.println("-----------Películas con Mayor Recaudación----------");
        System.out.println("----------------------------------------------------");
        
        collection.aggregate(Arrays.asList(match))
                .iterator()
                .forEachRemaining(System.out::println);

        Bson sort = Aggregates.sort(Sorts.descending("revenue"));
        Bson limit = Aggregates.limit(10);

        collection.aggregate(Arrays.asList(sort, limit))
                .iterator()
                .forEachRemaining(System.out::println);

        System.out.println("----------------------------------------------------");
        System.out.println("------------Películas por Actor/Actriz--------------");
        System.out.println("----------------------------------------------------");
        
        Bson matchActor = Aggregates.match(Filters.in("actores", "Ryan Gosling"));

        collection.aggregate(Arrays.asList(matchActor))
                .iterator()
                .forEachRemaining(System.out::println);
        
        System.out.println("----------------------------------------------------");
        System.out.println("----------Películas Estrenadas por género-----------");
        System.out.println("----------------------------------------------------");
        
        Bson matchGenre = Aggregates.match(Filters.in("generos", "Comedia")); // Puedes añadir más géneros a la lista

        collection.aggregate(Arrays.asList(matchGenre))
                .iterator()
                .forEachRemaining(System.out::println);
    }
}
