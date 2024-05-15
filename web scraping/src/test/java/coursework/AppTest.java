package coursework;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.junit.jupiter.api.Assertions.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.annotation.Order;

import web.coursework.AppConfig;
import web.coursework.HibernateXml;
import web.coursework.Laptops;
import web.coursework.ScraperHandler;

import java.io.IOException;
import java.util.ArrayList;

/**
 Unit testing.
**/

public class AppTest {

    ScraperHandler scraperHandler;
    static SessionFactory sessionFactory;
    static Session session;
    static int laptopId;
    static int comparisonId;

    // Session Factory Test
    @Test
    @Order(1)
    @DisplayName("Test1: Session Factory")
    void sessionFactoryTest() {
        HibernateXml hibernate = new HibernateXml();
        AppConfig app = new AppConfig();

        try {
            hibernate.setSessionFactory(app.sessionFactory());
        } catch (Exception ex) {
            fail("Error: Can't Set Session Factory: " + ex.getMessage());
        }

        //Asserting that the session factory is not null
        assertNotNull(hibernate.getSessionFactory());
        System.out.println("Test 1 has been completed");
    }


    //Scraper List Test
    @Test
    @Order(2)
    @DisplayName("Test 2: Scraper")
    void scraperListTest() {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ScraperHandler handler = (ScraperHandler) context.getBean("scraperHandler");

        //Asserting that the handler is not null
        assertNotNull(handler);
        System.out.println("Test 2 has been completed");
    }



    //Running Threads Test
    @Test
    @Order(3)
    @DisplayName("Test 3: Running the threads")
    void runningThreadsTest() {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ScraperHandler handler = (ScraperHandler) context.getBean("scraperHandler");

        try {
            for (Thread thread : ScraperHandler.getScraperList()) {
                thread.start();
            }
        } catch (Exception ex) {
            fail("Failed to start the threads" + ex.getMessage());
        }

        //Assering that the threads are alive
        for (Thread scraperThread : ScraperHandler.getScraperList()) {
            assertEquals(true, scraperThread.isAlive());
        }
        System.out.println("Test 3 has been completed");
    }

    @BeforeAll
    static void initAll() {
        try {
            //Create a builder for the standard service registry
            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

            //Load configuration from hibernate configuration file.
            standardServiceRegistryBuilder.configure("resources/hibernate.cfg.xml");

            //Create the registry to build the session factory
            StandardServiceRegistry registry = standardServiceRegistryBuilder.build();
            try {
                //Create the session factory
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                /* The registry would be destroyed by the SessionFactory,
                    but we had trouble building the SessionFactory, so destroy it manually 
                */
                System.err.println("Session Factory build failed.");
                StandardServiceRegistryBuilder.destroy(registry);
            }
            //Output result
            System.out.println("Session factory built.");
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("SessionFactory creation failed." + ex);
        }
    }



    //Check if web scraper is scraping products Test.
    @Test
    @Order(4)
    @DisplayName("Test 4: Web scrapper")
    void deleteTVTest() throws IOException {

        //Download HTML document from website
        Document doc = Jsoup.connect("https://www.amazon.com/s?k=laptop&page=1")
                .userAgent("A student experiencing web scraping for a university coursework.")
                .get();

        //Get all products on the page
        Elements prods1 = doc.select(".s-result-item");
        Elements prods = prods1.select(".s-asin");

        //Work through the products
        for (Element prod : prods) {
            assertNotNull(prod);
        }
        System.out.println("Test 4 has been completed");
    }


    //Adding a laptop with hibernate
    @Test
    @Order(5)
    @DisplayName("Test 5: Add laptop with hibernate")
    void saveLaptopTest() {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        HibernateXml hibernate = (HibernateXml) context.getBean("hibernate");

        //Laptops laptop = new Laptops();

        String brand = "Lenovo";
        String model = "Thinkpad";
        String description = "Model 2020";
        String url = "https://test-url.com";
        String source_url = "https://test-source-url.com";
        String price = "2000";
        String logoUrl = "https://test-logo-url.com";

        //Use Hibernate to save Laptop
        ArrayList<Integer> savedLaptopArray = hibernate.addLaptop(brand, model, description, url, source_url, price, logoUrl);

        System.out.println("Test 5 has been completed");
    }

    @After
    public void after() {
        sessionFactory.close();
    }
}
