package Testing;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.ValidityReport;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;


import org.junit.Test;

public class Consistency_Test {
    @Test
    public void testOntologyConsistency() {
        // Load the ontology
        OntModel model = ModelFactory.createOntologyModel();
        model.read("data/Movies.owl");

        // Perform consistency checking
        ValidityReport validityReport = model.validate();
        
        // Assert that the ontology is consistent
        assertTrue("Ontology should be consistent", validityReport.isValid());
    }
    @Test
    public void testOntologyConsistency1() {
    	// Load the ontology
        OntModel model = ModelFactory.createOntologyModel();
        model.read("data/Movies.owl");

        // Perform consistency checking
        ValidityReport validityReport = model.validate();
        
        // Assert that the ontology is consistent
        assertTrue("Ontology should be consistent", validityReport.isValid());
        
        // Additionally, check if there are any inconsistencies reported
        if (!validityReport.isValid()) {
            System.out.println("Inconsistencies found in the ontology:");
            Iterator<ValidityReport.Report> iterator = validityReport.getReports();
            while (iterator.hasNext()) {
                System.out.println(iterator.next().getDescription());
            }
        }
    }

}
