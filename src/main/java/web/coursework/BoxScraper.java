package web.coursework;

import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BoxScraper extends Thread {
    //Interval between HTTP requests to the server in seconds.
    private int crawlDelay = 2;

    //Allows clean application shutdown
    volatile private boolean runThread = false;

    @Override
    public void run() {
        runThread = true;

        //While loop will keep running until runThread is set to false;
        while (runThread) {
            System.out.println("BoxScraper thread is scraping data.");

            //Scrape data from Amazon
            for (int pageNumber = 1; pageNumber < 10; pageNumber++) {

                try {
                    //Download HTML document from website
                    Document doc = Jsoup.connect("https://www.box.co.uk/laptops/page/" + pageNumber)
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .userAgent("Student for a web development coursework")
                            .timeout(10 * 1000)
                            .get();

                    //Get all the products on the page
                    Elements prods = doc.select("div.product-list-item ");

                    //Work through the products
                    for (int i = 0; i < prods.size(); ++i) {

                        //Get the product description = name
                        Elements laptopDescription = prods.get(i).select("div.p-list-title-wrapper");
                        String[] title = laptopDescription.text().split(" ");
                        String laptopBrand;
                        String laptopModel;

                        if (title.length > 4) {
                            laptopBrand = title[0];
                            laptopModel = title[1];
                        } else {
                            laptopBrand = " ";
                            laptopModel = " ";
                        }

                        //Get the FINAL product price
                        Elements laptopPrice = prods.get(i).select("p.p-list-sell");
                        //convert the price in numbers
                        String finalPrice = laptopPrice.text();

                        //Get the laptop image url => good
                        Elements laptopImgUrl = prods.get(i).select("img.lazyimage");
                        String laptopImg = "https://www.box.co.uk" + laptopImgUrl.attr("data-src");

                        //Get the specific laptop product url
                        Element productUrl = prods.get(i).select("div.p-list-image-wrapper").first();
                        Element constLink = productUrl.select("a").first();
                        String productLink = constLink.attr("href");

                        //Get the website logo url => WE ARE NOT GETTING THE LOGO URL
                        Elements websiteLogoUrl = prods.get(i).select("div.wrapper");
                        Elements logoLink = websiteLogoUrl.select("img");
                        String websiteLogo = "https://www.box.co.uk" + logoLink.attr("data-src");

                        //Box uk logo
                        //String logoUrl = "https://www.box.co.uk/Images/box-logo2-FP_2110111013.svg";

                        //Adding to database
                        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
                        HibernateXml hibernate = (HibernateXml) context.getBean("hibernate");
                        hibernate.addLaptop(laptopBrand, laptopModel, laptopDescription.text(), laptopImg, productLink, finalPrice);
                        hibernate.shutDown();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //Sleep for the crawl delay, which is in seconds
                try {
                    sleep(1000 * crawlDelay);//Sleep is in milliseconds, so we need to multiply the crawl delay by 1000
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }
}
