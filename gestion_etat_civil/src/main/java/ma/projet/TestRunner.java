package ma.projet;

import ma.projet.test.TestEtatCivil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TestRunner {
    public static void main(String[] args) {
        // Set a different port for the test runner
        System.setProperty("server.port", "8081");
        
        // Start Spring Boot application
        ConfigurableApplicationContext context = SpringApplication.run(TestRunner.class, args);
        
        // Run the test
        TestEtatCivil.main(args);
        
        // Keep the application running
        try {
            Thread.sleep(30000); // Keep running for 30 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        context.close();
    }
}
