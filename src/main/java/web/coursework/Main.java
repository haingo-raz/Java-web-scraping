package web.coursework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        //start the scraping
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ScraperHandler scraperHandler = (ScraperHandler) context.getBean("scraperHandler");
        scraperHandler.startThreads();
    }
}