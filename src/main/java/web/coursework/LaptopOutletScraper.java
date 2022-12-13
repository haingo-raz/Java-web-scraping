package web.coursework;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LaptopOutletScraper extends Thread{

    //Interval between HTTP requests to the server in seconds.
    private int crawlDelay = 2;

    //Allows clean application shutdown
    volatile private boolean runThread = false;

    @Override
    public void run(){
        runThread = true;

        //While loop will keep running until runThread is set to false;
        while(runThread){
            System.out.println("LaptopOutletScraper thread is scraping data.");

            //Scrape data
            for (int pageNumber = 1; pageNumber < 40; pageNumber++) {
                try{
                    //Download HTML document from website
                    Document doc = Jsoup.connect("https://www.laptopoutlet.co.uk/laptops-and-notebooks.html?p=" + pageNumber)
                            .userAgent("Student for a web development coursework")
                            .timeout(10 * 1000)
                            .get();

                    //Get all the products on the page
                    Elements prods = doc.select("li.product-item");

                    //Work through the products
                    for(int i=0; i<prods.size(); ++i){

                        //Get the product description
                        Elements laptopDescription = prods.get(i).select("a.product-item-link");
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


                        //Get the final product price
                        Elements laptopPrice = prods.get(i).select("span.including_vat_price_box"); //price description
                        //get the string value
                        String finalPrice = laptopPrice.text();


                        //Get the laptop image url
                        Elements laptopImgUrl = prods.get(i).select("img.product-image-photo");
                        String laptopImg = laptopImgUrl.attr("src");

                        //Get a specific product url
                        Element productUrl = prods.get(i).select("div.product-item-photo").first();
                        Element constLink = productUrl.select("a").first();
                        String productLink = constLink.attr("href");

                        //Logo url
                        String logoUrl = "https://static-media.laptopoutlet.co.uk/logo/stores/1/LaptopOutlet_newlogo-01.png";

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
                sleep(1000 * crawlDelay); //sleep 1s
            }
            catch(InterruptedException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
}
