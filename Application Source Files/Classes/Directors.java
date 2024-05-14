package Classes;

import org.apache.jena.query.QuerySolution;

import java.util.ArrayList;
import java.util.HashMap;

public class Directors {
    private static HashMap<String, Boolean> availableDirectors;
    private static ArrayList<String> includedDirectors;
    private static ArrayList<String> excludedDirectors;

    public Directors(){
        // Query the ontology to find all directors and populate availableDirectors map //

        // Initialize data structures
        availableDirectors = new HashMap<>();
        includedDirectors = new ArrayList<>();
        excludedDirectors = new ArrayList<>();

        // Query String to find all directors
        String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX eg: <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#>\n" +
                "\n" +
                "SELECT ?directorName\n" +
                "WHERE {\n" +
                "  ?movie rdf:type eg:Movies.\n" +
                "  ?movie eg:hasDirector ?director.\n" +
                "  ?director eg:hasName ?directorName.\n" +
                "}";

        org.apache.jena.query.ResultSet results = OpenOWL.ExecSparQl(queryString);

        // Populate availbleDirectors
        while(results.hasNext()){
            QuerySolution soln = results.nextSolution();
            availableDirectors.put(soln.getLiteral("directorName").getString(), true);
        }
//        availableDirectors.put("Adham", true);
//        availableDirectors.put("Ashraf", true);
    }

    public static ArrayList<String> getIncludedDirectors() {
        return includedDirectors;
    }

    public static ArrayList<String> getExcludedDirectors() {
        return excludedDirectors;
    }

    public static ArrayList<String> getAllAvailableDirectors(){

        ArrayList <String> res = new ArrayList();
        for(String n : availableDirectors.keySet()){
            if(availableDirectors.get(n)) res.add(n);
        }
        return res;
        // return availableDirectors.keySet().stream()
        //                         .filter(n -> availableDirectors.get(n))
        //                         .collect(Collectors.toCollection(ArrayList::new));
    }

    public static boolean addIncludedDirectors(String name){
        if(availableDirectors.containsKey(name) && availableDirectors.get(name)){
            availableDirectors.put(name, false);
            includedDirectors.add(name);
            return true;
        }
        return false;
    }
    public static boolean removeIncludedDirectors(String name){
        if(availableDirectors.containsKey(name)){
            availableDirectors.put(name, true);
            includedDirectors.remove(name);
            return true;
        }
        return false;
    }
    public static boolean addExcludedDirectors(String name){
        if(availableDirectors.containsKey(name) && availableDirectors.get(name)){
            availableDirectors.put(name, false);
            excludedDirectors.add(name);
            return true;
        }
        return false;
    }
    public static boolean removeExcludedDirectors(String name){
        if(availableDirectors.containsKey(name)){
            availableDirectors.put(name, true);
            excludedDirectors.remove(name);
            return true;
        }
        return false;
    }

    public static String getIncludedDirectorsAsString(){

        String res = "";
        for(String n : includedDirectors) res += n + " ";
        return res;

    }
    public static String getExcludedDirectorsAsString(){

        String res = "";
        for(String n : excludedDirectors) res += n + " ";
        return res;

    }
    public static String getLastIncludedDirector(){
        return (includedDirectors.size() != 0)? includedDirectors.get(includedDirectors.size()-1) : "";
    }
    public static String getLastExcludedDirector(){
        return (excludedDirectors.size() != 0)? excludedDirectors.get(excludedDirectors.size()-1) : "";
    }
}
