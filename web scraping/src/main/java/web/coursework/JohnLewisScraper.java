package web.coursework;

import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * Web scraper for johnlewis.com
 */
public class JohnLewisScraper extends Thread{

    //Interval between HTTP requests to the server in seconds.
    private int crawlDelay = 10;

    //Allows clean application shutdown
    volatile private boolean runThread = false;

    @Override
    public void run(){
        runThread = true;

        //While loop will keep running until runThread is set to false;
        while(runThread){
            System.out.println("JohnLewisScraper thread is scraping data.");

            //Scrape data
            try{
                //Download HTML document from website
                Document doc = Jsoup.connect("https://www.johnlewis.com/search?search-term=laptop")
                        .timeout(10 * 1000)
                        .userAgent("Student for a web development coursework")
                        .get();

                //Get all the products on the page
                Elements prods = doc.select("div.ProductGrid_product-grid__product__oD7Jq");

                //Work through the products
                for(int i=0; i<prods.size(); ++i){

                    //Get laptop brands
                    Elements laptopBrand = prods.get(i).select("span.title_title__brand__UX8j9");

                    //Get the product description
                    Elements laptopDescription = prods.get(i).select("span.title_title__desc__ZCdyp");
                    String[] title = laptopDescription.text().split(" ");

                    String laptopModel;

                    //Get laptop brand and model
                    if (title.length > 4) {
                        laptopModel = title [0];
                    } else {
                        laptopModel = " ";
                    }

                    //Get the final product price
                    Elements laptopPrice = prods.get(i).select("span.price_price__now__3B4yM");
                    //convert the price in numbers
                    String finalPrice = laptopPrice.text();

                    //Get the laptop image url
                    Elements laptopImgUrl = prods.get(i).select(".image_image__jhaxk");
                    String laptopImg = "https:" + laptopImgUrl.attr("src");

                    //Get the specific laptop product url
                    Element productUrl = prods.get(i).select("div.product-card_c-product-card__image-container__5DecW").first();
                    Element constLink = productUrl.select("a").first();
                    String productLink = "https://www.johnlewis.com/" + constLink.attr("href");

                    //Logo url
                    String logoUrl = "https://www.johnlewis.com/header-ui-assets/static/images/john-lewis-partners-f6c40704.svg";

                    //Adding laptop to database
                    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
                    HibernateXml hibernate = (HibernateXml) context.getBean("hibernate");
                    hibernate.addLaptop(laptopBrand.text(), laptopModel, laptopDescription.text(), laptopImg, productLink, finalPrice, logoUrl);
                    hibernate.shutDown();
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
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
