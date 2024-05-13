import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Jena6 {
    public static void main(String[] args) {
        // Load the ontology
        Model model = ModelFactory.createDefaultModel();
        FileManager fileManager = FileManager.get();
        String owlFile = "data/Movies.owl"; // Replace with the actual filename
        model.read(fileManager.open(owlFile), null);

        // Execute and display results for Rule 1
        executeRule(model, "data/age_rule.txt");

        // Execute and display results for Rule 2
        executeRule(model, "data/genre_rule.txt");
    }

    private static void executeRule(Model model, String ruleFile) {
        // Read the SPARQL query from file
        String queryString = readQueryFromFile(ruleFile);

        // Execute the query
        try (QueryExecution qexec = QueryExecutionFactory.create(queryString, model)) {
            ResultSet results = qexec.execSelect();

            // Print the results
            ResultSetFormatter.out(results);
        }
    }

    // Helper method to read the query from file
    private static String readQueryFromFile(String queryFile) {
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = FileManager.get().open(queryFile)) {
            if (inputStream != null) {
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
