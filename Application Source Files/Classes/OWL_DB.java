package Classes;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OWL_DB
{
    static String str;
    static String path = "C:\\Users\\Adham\\Desktop\\sem 8\\Ontologies\\proj\\Ontologies Project Final\\Movies_Ontology_Project\\Application Source Files\\Movies.owl";

    // Connect to the ontology
    static OntModel ConnectToOwl(){

        OntModel model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_RULE_INF );
        java.io.InputStream input = FileManager.get().open( path );
        if (input == null) {
            throw new IllegalArgumentException("No Such OWL file");
        }
        return  (OntModel) model.read(input, "");
    }

    // Query the OWL file
    static  org.apache.jena.query.ResultSet querySPARQL(String Query){

        org.apache.jena.query.Query query = QueryFactory.create(Query);
        QueryExecution q = QueryExecutionFactory.create(query, ConnectToOwl());
        org.apache.jena.query.ResultSet results = q.execSelect();
        //System.out.println("The Query: "+Query);
        return results;

    }
    // Query the owl file and return the result as a string
    static  String GetQueryResultAsString(String Query){
        try {
            org.apache.jena.query.Query query = QueryFactory.create(Query);
            org.apache.jena.query.ResultSet results = querySPARQL(Query);
            if(results.hasNext()){
                ByteArrayOutputStream go = new ByteArrayOutputStream ();
                ResultSetFormatter.out((OutputStream)go ,results, query);
                //  String s = go.toString();
                str = new String(go.toByteArray(), "UTF-8");
            }
            else{
                str = "END";
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OWL_DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }
}
