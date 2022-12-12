package web.coursework;

import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AmazonScraper extends Thread {

    //Interval between HTTP requests to the server in seconds.
    private int crawlDelay = 2;

    //Allows clean application shutdown
    volatile private boolean runThread = false;

    @Override
    public void run(){
        runThread = true;

        //While loop will keep running until runThread is set to false;
        while(runThread){
            System.out.println("AmazonScraper thread is scraping data.");

            //Scrape data from Amazon
            for (int pageNumber = 1; pageNumber < 10; pageNumber++) {
                try{
                    Document doc = Jsoup.connect("https://www.amazon.com/s?k=laptop&page=" + pageNumber)
                            .userAgent("A student experiencing web scraping for a university coursework.")
                            .get();

                    //Get all the products on the page
                    Elements prods1 = doc.select(".s-result-item");
                    Elements prods = prods1.select(".s-asin");

                    //Work through the products
                    for (int i = 0; i < prods.size(); ++i) {

                        //Get the product price
                        Elements laptopPrice = prods.get(i).select("span.a-price-whole");
                        //convert the price in numbers
                        String finalPrice = laptopPrice.text();

                        //Get the laptop image url
                        Elements laptopImgUrl = prods.get(i).select("img.s-image");
                        String laptopImg = laptopImgUrl.attr("src");

                        //Get specific product link
                        Elements productUrl = prods.get(i).select("a.a-link-normal ");
                        String productLink = "https://www.amazon.com/" + productUrl.attr("href");

                        //Get the product description
                        Elements laptopDescription = prods.get(i).select("span.a-size-medium");
                        String laptopBrand;
                        String laptopModel;
                        String[] title = laptopDescription.text().split(" ");

                        //Amazon logo
                        //String logoUrl = "https://m.media-amazon.com/images/I/31hIyqA%2BktL.jpg";

                        if (title.length > 4) {
                            laptopBrand = title[0];
                            laptopModel = title [1];
                        } else {
                            laptopBrand = " ";
                            laptopModel = " ";
                        }

                        //Adding to database
                        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
                        HibernateXml hibernate = (HibernateXml) context.getBean("hibernate");
                        hibernate.addLaptop(laptopBrand, laptopModel, laptopDescription.text(), laptopImg, productLink, finalPrice);
                        hibernate.shutDown();
                    }
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            //Sleep for the crawl delay, which is in seconds
            try{
                sleep(1000 * crawlDelay);//Sleep is in milliseconds, so we need to multiply the crawl delay by 1000
            }
            catch(InterruptedException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
}
