package web.coursework;

import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class QuzoScraper extends Thread{


    //Interval between HTTP requests to the server in seconds.
    private int crawlDelay = 2;

    //Allows clean application shutdown
    volatile private boolean runThread = false;

    @Override
    public void run(){
        runThread = true;

        //While loop will keep running until runThread is set to false;
        while(runThread){
            System.out.println("QuzoScraper thread is scraping data.");

            //Scrape data
            for (int pageNumber = 1; pageNumber < 70; pageNumber++) {
                try{
                    //Download HTML document from website
                    Document doc = Jsoup.connect("https://www.quzo.net/products/laptops/?page=" + pageNumber)
                            .timeout(10 * 1000)
                            .userAgent("Student for a web development coursework")
                            .get();

                    //Get all the products on the page
                    Elements prods = doc.select("span.listingFormat");

                    //Work through the products
                    for(int i=0; i<prods.size(); ++i){

                        //Get the product description
                        Elements laptopDescription = prods.get(i).select("div.desc");
                        String laptopBrand;
                        String laptopModel;
                        String[] title = laptopDescription.text().split(" ");

                        if (title.length > 4) {
                            laptopBrand = title[0];
                            laptopModel = title [1];
                        } else {
                            laptopBrand = " ";
                            laptopModel = " ";
                        }

                        //Get the laptop brand

                        //Get the FINAL product price
                        Elements laptopPrice = prods.get(i).select("span.fixedPrice");
                        //get the string value
                        String finalPrice = laptopPrice.text();

                        //Get the laptop image URL
                        Elements laptopImgUrl = prods.get(i).select("div.ProductImageMedium");
                        Elements constImgUrl = laptopImgUrl.select("img");
                        String laptopImg = constImgUrl.attr("src");

                        //Get the specific laptop description link
                        Element productUrl = prods.get(i).select("div.ProductImageMedium").first();
                        Element constLink = productUrl.select("a").first();
                        String productLink = "https://www.quzo.net" + constLink.attr("href");

                        //Get the website logo url
                        String logoUrl = "https://assets.quzo.co.uk/site/structure/quzo.svg?r=1.11211";

                        //Adding to database
                        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
                        HibernateXml hibernate = (HibernateXml) context.getBean("hibernate");
                        hibernate.addLaptop(laptopBrand, laptopModel, laptopDescription.text(), laptopImg, productLink, finalPrice, logoUrl);
                        hibernate.shutDown();
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }

            try{
                sleep(1000 * crawlDelay);//sleep 1s
            }
            catch(InterruptedException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
}
