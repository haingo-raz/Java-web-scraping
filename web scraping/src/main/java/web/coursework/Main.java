package web.coursework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main class, starting point of the program
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ScraperHandler scraperHandler = (ScraperHandler) context.getBean("scraperHandler");
        scraperHandler.startThreads();
    }
}