import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.LocationMapper;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
public class Jena1 {

	public static void main(String[] args) {
		// Load the ontology
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        FileManager fileManager = FileManager.get();
        String owlFile = "data/Movies.owl"; // Change the file path as needed
        model.read(fileManager.open(owlFile), null);

        // Retrieve the Person class
        OntClass personClass = model.getOntClass("http://www.semanticweb.org/hp/ontologies/2024/3/Movies#Persons");
        if (personClass != null) {
            // List all individuals of the Person class
            ExtendedIterator<? extends OntResource> personIterator = personClass.listInstances();
            if (personIterator.hasNext()) {
                System.out.println("Persons:");
                while (personIterator.hasNext()) {
                    OntResource personResource = personIterator.next();
                    System.out.println(" - " + personResource.getLocalName());
                }
            } else {
                System.out.println("No individuals of the Person class found.");
            }
        } else {
            System.out.println("Person class not found in the ontology.");
        }
    }
}