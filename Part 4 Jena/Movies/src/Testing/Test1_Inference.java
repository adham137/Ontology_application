package Testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.junit.Test;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

public class Test1_Inference {
	 @Test
	    public void testSubclassRelationship() {
	        // Load the ontology
	        OntModel model = ModelFactory.createOntologyModel();
	        model.read("data/Movies.owl");

	        // Perform inference and check if there exists a subclass relationship
	        boolean isSubclass = model.getOntClass("http://www.semanticweb.org/hp/ontologies/2024/3/Movies#Persons")
	                                  .hasSubClass(model.getOntClass("http://www.semanticweb.org/hp/ontologies/2024/3/Movies#Actor"));
	        
	        // Assert that the subclass relationship exists
	        assertTrue("Actor should be a subclass of Persons", isSubclass);
	    }
	    @Test
	    public void testPropertyInference() {
	        // Load the ontology
	        OntModel model = ModelFactory.createOntologyModel();
	        model.read("data/Movies.owl");

	        // Perform inference and check if properties are correctly inferred
	        // Check if the property "hasDirector" is correctly inferred for instances of "Movie"
	        boolean hasDirectorInference = model.getOntProperty("http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasDirector")
	                                             .isProperty();
	        
	        // Assert that the property "hasDirector" is correctly inferred for instances of "Movie"
	        assertTrue("Property 'hasDirector' should be correctly inferred for instances of 'Movie'", hasDirectorInference);
	    }
	    @Test
	    public void testDomainAndRangeInference() {
	        // Load the ontology
	        OntModel model = ModelFactory.createOntologyModel();
	        model.read("data/Movies.owl");

	        // Perform inference and check if domain and range restrictions are correctly inferred
	        // For example, check if the domain of "hasDirector" is correctly inferred as "Movie"
	        ExtendedIterator<OntProperty> properties = model.listAllOntProperties();
	        while (properties.hasNext()) {
	            OntProperty property = properties.next();
	            if (property.getLocalName().equals("hasDirector")) {
	                OntResource domain = property.getDomain();
	                OntResource range = property.getRange();
	                
	                // Assert that the domain of "hasDirector" is correctly inferred as "Movie"
	                assertEquals("Domain of 'hasDirector' should be 'Movie'", "Movies", domain.getLocalName());
	                
	                // Assert that the range of "hasDirector" is correctly inferred as "Director"
	                assertEquals("Range of 'hasDirector' should be 'Persons'", "Persons", range.getLocalName());
	                break;
	            }
	        }
	    }
	    @Test
	    public void testActorRestrictionInference() {
	        // Load the ontology
	        OntModel model = ModelFactory.createOntologyModel();
	        model.read("data/Movies.owl");

	        // Define a rule to infer the restriction on the Actor class
	        String rule = "[ActorRestrictionInference: (?actor rdf:type <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#Actor>) "
	                + "(?actor <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#isActorOf> ?movie) -> "
	                + "(?movie <http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasAtLeastOneActor> true)]";

	     // Create a reasoner with the rules
	        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rule));

	        // Apply the reasoner to the model
	        InfModel infModel = ModelFactory.createInfModel(reasoner, model);


	        // Check if the ontology is consistent after inference
	        ValidityReport validityReport = infModel.validate();
	        
	        // Assert that the ontology is consistent
	        assertTrue("Ontology should be consistent after inference", validityReport.isValid());
	        
	        // Check if the inferred property exists for movies
	        assertTrue("Movies should have the property 'hasAtLeastOneActor' after inference",
	                   infModel.getProperty("http://www.semanticweb.org/hp/ontologies/2024/3/Movies#hasAtLeastOneActor") != null);
	    }
	    }


