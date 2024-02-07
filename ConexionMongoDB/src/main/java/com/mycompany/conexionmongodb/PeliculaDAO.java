package com.mycompany.conexionmongodb;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
/**
 *
 * @author SamuelPardo
 */
public class PeliculaDAO {
    
    MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    
    public void insertarPelicula(String nombre, List<String> generos, Date fechaEstreno, String resumen, List<String> premios, double recaudacion, List<String> actoresIDs) {
        MongoDatabase db = mongoClient.getDatabase("GestionBase");
        MongoCollection<Document> coleccion = db.getCollection("peliculas");

        Document nuevaPelicula = new Document("nombre", nombre)
                .append("generos", generos)
                .append("fechaEstreno", fechaEstreno)
                .append("resumen", resumen)
                .append("premios", premios)
                .append("recaudacion", recaudacion)
                .append("actores", actoresIDs);

        coleccion.insertOne(nuevaPelicula);
    }
    
    public List<Document> obtenerPeliculas() {
        MongoDatabase db = MongoDBConexion.obtenerDB();
        MongoCollection<Document> coleccion = db.getCollection("peliculas");

        List<Document> peliculas = new ArrayList<>();
        coleccion.find().iterator().forEachRemaining(peliculas::add);

        return peliculas;
    }
    
    public void actualizarPelicula(String id, String nombre, List<String> generos, Date fechaEstreno, String resumen, List<String> premios, double recaudacion, List<String> actoresIDs) {
    MongoDatabase db = MongoDBConexion.obtenerDB();
    MongoCollection<Document> coleccion = db.getCollection("peliculas");

    Document actualizacion = new Document();
    if (nombre != null && !nombre.isEmpty()) {
        actualizacion.append("nombre", nombre);
    }
    if (generos != null && !generos.isEmpty()) {
        actualizacion.append("generos", generos);
    }
    if (fechaEstreno != null) {
        actualizacion.append("fechaEstreno", fechaEstreno);
    }
    if (resumen != null && !resumen.isEmpty()) {
        actualizacion.append("resumen", resumen);
    }
    if (premios != null && !premios.isEmpty()) {
        actualizacion.append("premios", premios);
    }
    if (recaudacion > 0) {
        actualizacion.append("recaudacion", recaudacion);
    }
    if (actoresIDs != null && !actoresIDs.isEmpty()) {
        actualizacion.append("actores", actoresIDs);
    }

        coleccion.updateOne(eq("_id", new ObjectId(id)), new Document("$set", actualizacion));
    }
    
    public void eliminarPelicula(String id) {
        MongoDatabase db = MongoDBConexion.obtenerDB();
        MongoCollection<Document> coleccion = db.getCollection("peliculas");

        coleccion.deleteOne(eq("_id", new ObjectId(id)));
    }
}
