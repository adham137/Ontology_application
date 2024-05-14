package Classes;

import org.apache.jena.query.QuerySolution;

import java.util.ArrayList;
import java.util.HashMap;

public class Genres {
    private static HashMap<String, Boolean> availableGenres;
    private static ArrayList<String> includedGenres;
    private static ArrayList<String> excludedGenres;

    public Genres() {
        // Query the ontology to find all genres and populate availableGenres map //

        // Initialize data structures
        availableGenres = new HashMap<>();
        includedGenres = new ArrayList<>();
        excludedGenres = new ArrayList<>();

        // Query String to find all genres
        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX eg: <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#>\n" +
                "\n" +
                "SELECT ?genre\n" +
                "WHERE {\n" +
                "  ?movie rdf:type eg:Movies.\n" +
                "  ?movie eg:hasGenre ?genre.\n" +
                "}";

        org.apache.jena.query.ResultSet results = OWL_DB.querySPARQL(queryString);

        // Populate availableGenres
        while(results.hasNext()){
            QuerySolution soln = results.nextSolution();
            availableGenres.put(soln.getLiteral("genre").getString(), true);
        }
//        availableGenres.put("Adham", true);
//        availableGenres.put("Ashraf", true);
    }

    public static ArrayList<String> getIncludedGenres() {
        return includedGenres;
    }

    public static ArrayList<String> getExcludedGenres() {
        return excludedGenres;
    }

    public static ArrayList<String> getAllAvailableGenres(){

        ArrayList <String> res = new ArrayList();
        for(String n : availableGenres.keySet()){
            if(availableGenres.get(n)) res.add(n);
        }
        return res;
        // return availableActors.keySet().stream()
        //                         .filter(n -> availableActors.get(n))
        //                         .collect(Collectors.toCollection(ArrayList::new));
    }

    public static boolean addIncludedGenre(String name){
        if(availableGenres.containsKey(name) && availableGenres.get(name)){
            availableGenres.put(name, false);
            includedGenres.add(name);
            return true;
        }
        return false;
    }
    public static boolean removeIncludedGenre(String name){
        if(availableGenres.containsKey(name)){
            availableGenres.put(name, true);
            includedGenres.remove(name);
            return true;
        }
        return false;
    }
    public static boolean addExcludedGenre(String name){
        if(availableGenres.containsKey(name) && availableGenres.get(name)){
            availableGenres.put(name, false);
            excludedGenres.add(name);
            return true;
        }
        return false;
    }
    public static boolean removeExcludedGenre(String name){
        if(availableGenres.containsKey(name)){
            availableGenres.put(name, true);
            excludedGenres.remove(name);
            return true;
        }
        return false;
    }

    public static String getIncludedGenresAsString(){

        String res = "";
        for(String n : includedGenres) res += n + " ";
        return res;

    }
    public static String getExcludedGenresAsString(){

        String res = "";
        for(String n : excludedGenres) res += n + " ";
        return res;

    }
    public static String getLastIncludedGenre(){
        return (includedGenres.size() != 0)? includedGenres.get(includedGenres.size()-1) : "";
    }
    public static String getLastExcludedGenre(){
        return (excludedGenres.size() != 0)? excludedGenres.get(excludedGenres.size()-1) : "";
    }
}
