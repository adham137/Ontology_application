import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.*;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class Jena6_rules {
	public static void main(String[] args) 
    {
        Model model = ModelFactory.createDefaultModel();
        model.read( "data/Movies1.owl" );
        
        Reasoner reasoner = new GenericRuleReasoner( Rule.rulesFromURL( "data/rul1.txt" ) );
        
        InfModel infModel = ModelFactory.createInfModel( reasoner, model );
        StmtIterator it = infModel.listStatements();
        
        while ( it.hasNext() )
        {
            Statement stmt = it.nextStatement();
            
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();
            System.out.println( subject.toString() + " " + predicate.toString() + " " + object.toString() );
        }
    }
}

