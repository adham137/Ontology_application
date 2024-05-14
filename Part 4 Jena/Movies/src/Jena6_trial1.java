import org.apache.jena.query.*;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.*;
import org.apache.jena.vocabulary.RDF;

public class Jena6_trial1 {
    public static void main(String[] args) {
        // Load the ontology
        Model model = ModelFactory.createDefaultModel();
        FileManager fileManager = FileManager.get();
        String owlFile = "data/Movies.owl"; // Replace with the actual filename
        model.read(fileManager.open(owlFile), null);

        // Define rules
        String rule1 = "[ruleHasTwoRoles: (?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasActor> ?person) (?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasDirector> ?person) -> (?person <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#HasTwoRoles> ?movie)]";
        String rule2 = "[ruleMoviesDirectedByQT: (?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasDirector> <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#Quentin_Tarantino>) -> (?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#DirectedByQT> ?movie)]";
        String rule3 = "[ruleMoviesInEgypt: (?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasCountry> \"Egypt\"^^xsd:string) (?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasActor> ?actor) -> (?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#ActorName> ?actor)]";
        String rule4 = "[ruleMoviesInEgypt: (?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasGenre> \"Thriller\"^^xsd:string) (?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasActor> ?actor) -> (?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#ThrillerActor> ?actor)]";
        String rule5 = "[ruleMoviesInEgypt: (?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasGenre> \"Romance\"^^xsd:string) (?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasActor> ?actor) -> (?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#RomanceActor> ?actor)]";


        // Create a reasoner with the rules
        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rule1 + " " + rule2 + " " + rule3+ " "+rule4+ " " + rule5));

        // Apply the reasoner to the model
        InfModel infModel = ModelFactory.createInfModel(reasoner, model);

        // Execute queries
        askForTwoRoles(infModel);
        askMoviesDirectedByQuentinTarantino(infModel);
        askMoviesInCountry(infModel, "Egypt");
        askMoviesInGenre(infModel, "Thriller");
        askMoviesInGenreRomance(infModel, "Romance");
    }

    // Rule 1: Persons Who Are Actors And Directors
    private static void askForTwoRoles(Model model) {
        System.out.println("Persons who are actors and directors:");
        String queryString = "SELECT ?person WHERE {?person <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#HasTwoRoles> ?movie}";
        executeAndPrintQuery(model, queryString);
    }

    // Rule 2: Movies Directed by Quentin Tarantino
    private static void askMoviesDirectedByQuentinTarantino(Model model) {
        System.out.println("Movies directed by Quentin Tarantino:");
        String queryString = "SELECT ?movieTitle WHERE {?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#DirectedByQT> ?movie . ?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasTitle> ?movieTitle}";
        executeAndPrintQuery(model, queryString);
    }

    // Rule 3: Movies Released in a Specific Country with Directors' Names
    private static void askMoviesInCountry(Model model, String country) {
        System.out.println("Movies released in " + country + " along with their actor:");
        String queryString = "SELECT ?movieTitle ?actor WHERE {?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#ActorName> ?actor . ?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasTitle> ?movieTitle}";
        executeAndPrintQuery(model, queryString);
    }
    
    // Rule 4: Movies with Thriller genre
    private static void askMoviesInGenre(Model model, String genre) {
    	System.out.println("Movies have thriller genre " + genre + " along with their actor:");
        String queryString = "SELECT ?movieTitle ?actor WHERE {?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#ThrillerActor> ?actor . ?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasTitle> ?movieTitle}";
        executeAndPrintQuery(model, queryString);
    }
 // Rule 4: Movies with Romance genre
    private static void askMoviesInGenreRomance(Model model, String genre1) {
    	System.out.println("Movies have thriller genre " + genre1 + " along with their actor:");
        String queryString = "SELECT ?movieTitle ?actor WHERE {?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#RomanceActor> ?actor . ?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasTitle> ?movieTitle}";
        executeAndPrintQuery(model, queryString);
    }
    

    // Helper method to execute and print query results
    private static void executeAndPrintQuery(Model model, String queryString) {
        try (QueryExecution qexec = QueryExecutionFactory.create(queryString, model)) {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(System.out, results);
        }
    }
}
