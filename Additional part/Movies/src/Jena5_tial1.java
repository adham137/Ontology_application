import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;


public class Jena5_tial1 {
	public static void main(String[] args) 
    {
		Model model = ModelFactory.createDefaultModel();
		model.read( "data/Movies.owl" );
    
		Reasoner reasoner = new GenericRuleReasoner( Rule.rulesFromURL( "data/Jena5_rules.txt" ) );
		InfModel infModel = ModelFactory.createInfModel( reasoner, model );
		
		readPersonAge(infModel);
    }
	
	public static void readPersonAge(Model model) {
	    String query = "PREFIX ns: <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#> " +
	                   "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
	                   "SELECT ?person ?movie1 ?movie2 " +
	                   "WHERE { " +
	                   "  ?person rdf:type ns:ActorDirector . " +
	                   "  ?person ns:isActorOf ?movie1 . " +
	                   "  ?person ns:isDirectorOf ?movie2 . " +
	                   "}";
	    executeQuery(model, query);
	}

// Helper method to execute and print query results
private static void executeQuery(Model model, String queryString) {
    try (QueryExecution qexec = QueryExecutionFactory.create(queryString, model)) {
        ResultSet results = qexec.execSelect();
        ResultSetFormatter.out(System.out, results);
    }
}
}



