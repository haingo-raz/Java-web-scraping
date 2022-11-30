package web.coursework;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.*;

@Configuration
public class AppConfig {

    SessionFactory sessionFactory;

    /**
     *
     * @return scraperHandler
     */

    @Bean
    public ScraperHandler scraperHandler(){
        ScraperHandler scraperHandler = new ScraperHandler();

        List<Thread> scraperList = new ArrayList();
        scraperList.add(scraper1());
        scraperList.add(scraper2());
        scraperList.add(scraper3());
        scraperList.add(scraper4());
        scraperList.add(scraper5());
        ScraperHandler.setScraperList(scraperList);

        //return handler object
        return scraperHandler;
    }

    /**
     *
     * @return scraper1
     */
    @Bean
    public AmazonScraper scraper1(){
        AmazonScraper scraper1 = new AmazonScraper();
        return scraper1;
    }

    /**
     *
     * @return scraper2
     */
    @Bean
    public BoxScraper scraper2(){
        BoxScraper scraper2 = new BoxScraper();
        return scraper2;
    }

    /**
     *
     * @return scraper3
     */
    @Bean
    public JohnLewisScraper scraper3(){
        JohnLewisScraper scraper3 = new JohnLewisScraper();
        return scraper3;
    }

    /**
     *
     * @return scraper4
     */
    @Bean
    public LaptopOutletScraper scraper4(){
        LaptopOutletScraper scraper4 = new LaptopOutletScraper();
        return scraper4;
    }

    /**
     *
     * @return scraper5
     */
    @Bean
    public QuzoScraper scraper5(){
        QuzoScraper scraper5 = new QuzoScraper();
        return scraper5;
    }

    /**
     *
     * @return hibernate
     */
    @Bean
    public HibernateXml hibernate(){
        HibernateXml hibernate = new HibernateXml();
        hibernate.setSessionFactory(sessionFactory());
        return hibernate;
    }

    /**
     *SessionFactory Bean
     * @return sessionFactory
     */
    public SessionFactory sessionFactory(){
        if(sessionFactory == null){
            try{
                //Create a builder for the standard registry
                StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

                //Load configuration file
                standardServiceRegistryBuilder.configure("hibernate.cfg.xml");

                //Create registry
                StandardServiceRegistry registry = standardServiceRegistryBuilder.build();

                try{
                    sessionFactory = new MetadataSources(registry)
                            .buildMetadata()
                            .buildSessionFactory();
                } catch (Exception e){
                    System.err.print("Session factory build failed");
                    StandardServiceRegistryBuilder.destroy(registry);
                }
                //result
                System.out.println("Session factory built.");
            }catch (Throwable ex){
                System.err.println("SessionFactory creation failed. " + ex);
            }
        }
        return sessionFactory;
    }
}
