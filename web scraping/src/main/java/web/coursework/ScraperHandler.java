package web.coursework;

import java.util.List;


/**
 * Manage the scrapers
 */
public class ScraperHandler {

    private static List<Thread> scraperList;

    //Empty constructor
    ScraperHandler(){
    }

    //Getter
    public static List<Thread> getScraperList(){ return scraperList; }

    public static void setScraperList(List<Thread> sList) {
        scraperList = sList;
    }

    //Start a thread
    public void startThreads(){
        for (Thread LaptopScraper : scraperList){
            LaptopScraper.start();
        }
    }

    //Join scraper
    public void joinThreads(){
        for (Thread LaptopScraper : scraperList) {

            try {
                LaptopScraper.join();
            }
            catch (InterruptedException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
}
