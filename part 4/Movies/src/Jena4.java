import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

import java.util.Iterator;

public class Jena4 {

    public static void main(String[] args) {
        // Load the ontology
    	OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
    	FileManager fileManager = FileManager.get();
        String owlFile = "data/Movies.owl"; 
        model.read(fileManager.open(owlFile), null);

        // Prompt user to enter the name of the movie
        String movieName = System.console().readLine("Enter the name of the movie: ");

        // Query to retrieve information about the movie
        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX movies: <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#>" +
                "SELECT ?year ?country ?genre ?actorName " +
                "WHERE {" +
                "  ?movie rdf:type movies:Movies ." +
                "  ?movie movies:hasTitle ?title ." +
                "  FILTER (str(?title) = \"" + movieName + "\") ." +
                "  OPTIONAL { ?movie movies:hasYear ?year }" +
                "  OPTIONAL { ?movie movies:hasCountry ?country }" +
                "  OPTIONAL { ?movie movies:hasGenre ?genre }" +
                "  ?movie movies:hasActor ?actor ." +
                "  ?actor rdf:type movies:Actor." +
                "  ?actor movies:hasName ?actorName ." +
                "}";

        // Execute the query
        try (QueryExecution qexec = QueryExecutionFactory.create(queryString, model)) {
            ResultSet results = qexec.execSelect();
            if (results.hasNext()) {
                System.out.println("Movie Information:");
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    RDFNode year = soln.get("year");
                    RDFNode country = soln.get("country");
                    RDFNode genre = soln.get("genre");
                    RDFNode actor = soln.get("actorName");
                    
                    if (year != null) {
                        System.out.println("Year: " + year.toString());
                    }
                    if (country != null) {
                        System.out.println("Country: " + country.toString());
                    }
                    if (genre != null) {
                        System.out.println("Genre: " + genre.toString());
                    }
                    if (actor != null) {
                        System.out.println("Actor: " + actor.toString());
                    }
                }
            } else {
                System.out.println("Movie not found or information not available.");
            }
        }
    }
}
