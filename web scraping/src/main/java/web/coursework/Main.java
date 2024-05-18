package web.coursework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main class, starting point of the program
 */
public class Main {
    public static void main(String[] args) {
        // Instruct Spring to create and wire beans using annotations
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Get the scraper handler bean and start the threads
        ScraperHandler scraperHandler = (ScraperHandler) context.getBean("scraperHandler");
        scraperHandler.startThreads();
    }
}