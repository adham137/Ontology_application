package Classes;

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
        ArrayList<String> inc_actors = Actors.getIncludedActors();
        ArrayList<String> exc_actors = Actors.getExcludedActors();

        //////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////

        return "";
    }
}
