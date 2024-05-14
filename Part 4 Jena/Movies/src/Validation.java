import java.util.Iterator;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.RDF;


public class Validation {
	 public static void main(String[] args) {
	//Model model = ModelFactory.createDefaultModel();
    //FileManager fileManager = FileManager.get();
    String fname = "data/Movies.owl"; 
   // model.read(fileManager.open(owlFile), null);
	Model data = RDFDataMgr.loadModel(fname);
	InfModel infmodel = ModelFactory.createRDFSModel(data);
	ValidityReport validity = infmodel.validate();
	if (validity.isValid()) {
	    System.out.println("OK");
	} else {
	    System.out.println("Conflicts");
	    for (Iterator i = validity.getReports(); i.hasNext(); ) {
	        System.out.println(" - " + i.next());
	    }
	}
	 }
}
