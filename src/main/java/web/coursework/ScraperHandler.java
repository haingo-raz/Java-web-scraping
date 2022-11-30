package web.coursework;

import java.util.List;

public class ScraperHandler {

    private static List<Thread> scraperList;

    //Empty constructor
    ScraperHandler(){
    }

    //Getter
    public static List<Thread> getScraperList(){
        return scraperList;
    }

    public static void setScraperList(List<Thread> sList) {
        scraperList = sList;
    }

    public void startThreads(){
        for (Thread LaptopScraper : scraperList){
            LaptopScraper.start();
        }
    }


    //Join scraper
//    public void joinThreads(){
//        for (Thread AmazonScraper : scraperList) {
//
//            try {
//                AmazonScraper.join();
//            }
//            catch (InterruptedException ex){
//                System.err.println(ex.getMessage());
//            }
//        }
//    }
}
