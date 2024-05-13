package Classes;

import org.apache.jena.query.QuerySolution;

import java.util.ArrayList;

public class LabelValues {
    public static String includedActors;
    public static String excludedActors;

    public static String includedDirectors;
    public static String excludedDirectors;

    public static String includedGenres;
    public static String excludedGenres;

    public static String SearchResult;


    public LabelValues() {
        includedActors = "";
        includedDirectors = "";
        includedGenres = "";
        excludedActors = "";
        excludedDirectors = "";
        excludedGenres = "";
        SearchResult = "";
    }

    public static String getSearchResult(){

        StringBuilder res = new StringBuilder();
        String queryString = constructQuery();
        System.out.println(queryString);
        org.apache.jena.query.ResultSet results = OpenOWL.ExecSparQl(queryString);


        while(results.hasNext()){
            QuerySolution soln = results.nextSolution();
            res.append("Movie Title: " + soln.getLiteral("movieTitle").getString());
            res.append("    Release Year: " + soln.getLiteral("releaseYear").getString()+"\n");
        }
        //System.out.println(res.toString());
        return res.toString();
    }
    private static String constructQuery(){
        ArrayList<String> inc_actors = Actors.getIncludedActors();
        //System.out.println("Size of included actors array: "+ inc_actors.size());
        ArrayList<String> exc_actors = Actors.getExcludedActors();

        ArrayList<String> inc_directors = Directors.getIncludedDirectors();

        ArrayList<String> inc_genres = Genres.getIncludedGenres();

        //////////////////////////////////////////////////////////////////////////////
        StringBuilder queryString = new StringBuilder();
        queryString.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n");
        queryString.append("PREFIX eg: <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#>\n");
        queryString.append("SELECT DISTINCT ?movieTitle ?releaseYear\n");
        queryString.append("WHERE {\n");
        queryString.append("  ?movie rdf:type eg:Movies .\n");
        queryString.append("  ?movie eg:hasTitle ?movieTitle .\n");
        queryString.append("  ?movie eg:hasYear ?releaseYear . \n");
        queryString.append("  ?movie eg:hasActor ?actor . \n");     //////
        queryString.append("  ?movie eg:hasDirector ?director . \n");     //////
        queryString.append("  ?movie eg:hasGenre ?genre . \n");     //////

        // Add triple patterns for each condition with UNION
        boolean isFirstCondition = true;

        if (inc_genres.size() > 0){
            queryString.append(" FILTER( ");
            for (String genre : inc_genres) {
                queryString.append("?genre = ").append("\""+genre+"\"");
                if(inc_genres.indexOf(genre) == inc_genres.size()-1){
                    queryString.append(" )\n");
                }else{
                    queryString.append(" ||");
                }
            }
        }

        if (inc_directors.size() > 0) {
            queryString.append("  ?director eg:hasName ?directorName .\n");
            queryString.append(" FILTER( ");
            for (String director : inc_directors) {
                queryString.append("?directorName = ").append("\""+director+"\"");
                if(inc_directors.indexOf(director) == inc_directors.size()-1){
                    queryString.append(" )\n");
                }else{
                    queryString.append(" || ");
                }
            }
        }

        if (inc_actors.size() > 0){
            queryString.append("  ?actor eg:hasName ?actorName .\n");
            queryString.append(" FILTER( ");
            for (String actor : inc_actors) {
                queryString.append("?actorName = ").append("\""+actor+"\"");
                if(inc_actors.indexOf(actor) == inc_actors.size()-1){
                    queryString.append(" )\n");
                }else{
                    queryString.append(" || ");
                }
            }
        }


        queryString.append("}");
        //////////////////////////////////////////////////////////////////////////////
        return queryString.toString();
    }
}
