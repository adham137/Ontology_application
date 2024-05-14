package Classes;

import org.apache.jena.query.QuerySolution;

import java.util.ArrayList;
import java.util.HashMap;

public class Actors {

    private static HashMap<String, Boolean> availableActors;
    private static ArrayList<String> includedActors;
    private static ArrayList<String> excludedActors;

    public Actors() {
        // Query the ontology to find all actors and populate availableActors map //

        // Initialize data structures
        availableActors = new HashMap<>();
        includedActors = new ArrayList<>();
        excludedActors = new ArrayList<>();

        // Query String to find all actors
        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX eg: <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#>\n" +
                "\n" +
                "SELECT ?actorName\n" +
                "WHERE {\n" +
                "  ?movie rdf:type eg:Movies.\n" +
                "  ?movie eg:hasActor ?actor.\n" +
                "  ?actor eg:hasName ?actorName.\n" +
                "}";

        org.apache.jena.query.ResultSet results = OWL_DB.querySPARQL(queryString);

        // Populate availbleActors
        while(results.hasNext()){
            QuerySolution soln = results.nextSolution();
            availableActors.put(soln.getLiteral("actorName").getString(), true);
        }
//        availableActors.put("Adham", true);
//        availableActors.put("Ashraf", true);
    }

    public static ArrayList<String> getIncludedActors() {
        return includedActors;
    }

    public static ArrayList<String> getExcludedActors() {
        return excludedActors;
    }

    public static ArrayList<String> getAllAvailableActors(){

        ArrayList <String> res = new ArrayList();
        for(String n : availableActors.keySet()){
            if(availableActors.get(n)) res.add(n);
        }
        return res;
        // return availableActors.keySet().stream()
        //                         .filter(n -> availableActors.get(n))
        //                         .collect(Collectors.toCollection(ArrayList::new));
    }

    public static boolean addIncludedActor(String name){
        if(availableActors.containsKey(name) && availableActors.get(name)){
            availableActors.put(name, false);
            includedActors.add(name);
            return true;
        }
        return false;
    }
    public static boolean removeIncludedActor(String name){
        if(availableActors.containsKey(name)){
            availableActors.put(name, true);
            includedActors.remove(name);
            return true;
        }
        return false;
    }
    public static boolean addExcludedActor(String name){
        if(availableActors.containsKey(name) && availableActors.get(name)){
            availableActors.put(name, false);
            excludedActors.add(name);
            return true;
        }
        return false;
    }
    public static boolean removeExcludedActor(String name){
        if(availableActors.containsKey(name)){
            availableActors.put(name, true);
            excludedActors.remove(name);
            return true;
        }
        return false;
    }

    public static String getIncludedActorsAsString(){

        String res = "";
        for(String n : includedActors) res += n + " ";
        return res;

    }
    public static String getExcludedActorsAsString(){

        String res = "";
        for(String n : excludedActors) res += n + " ";
        return res;

    }
    public static String getLastIncludedActor(){
        return (includedActors.size() != 0)? includedActors.get(includedActors.size()-1) : "";
    }
    public static String getLastexcludedActor(){
        return (excludedActors.size() != 0)? excludedActors.get(excludedActors.size()-1) : "";
    }
}
