package web.coursework;

//Hibernate imports
import java.lang.module.Configuration;
import java.util.ArrayList;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateXml {

    //Use this class to create new Sessions to interact with the database
    private static SessionFactory sessionFactory;
    static boolean laptopDeletionCompleted = false;
    static boolean comparisonDeletionCompleted = false;

    /** Empty constructor */
    HibernateXml(){

    }

    public ArrayList<Integer> addLaptop(String laptopModel, String laptopDescription, String laptopLink, String laptopImgUrl) {
        //create a new session instance
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        Laptops laptop = new Laptops();
        //Comparison comparison = new Comparison();

        laptop.setLaptopId(laptop.getLaptopId());
        laptop.setBrandId(1);
        laptop.setLaptopModel(laptopModel);
        laptop.setLaptopDescription(laptopDescription);
        laptop.setLaptopLink(laptopLink);
        laptop.setLaptopImgUrl(laptopImgUrl);
        //Add to the database
        session.save(laptop);


        //comparison.setLaptopId(laptop.getLaptopId());
        //comparison.setPrice(price);
        //comparison.setUrl(laptopLink);
        //Add to database
        //session.save(comparison);

        //Commit transaction to save to database
        session.getTransaction().commit();

        ArrayList<Integer> ids = new ArrayList<>();
//
          ids.add(laptop.getLaptopId());
//        ids.add(comparison.getComparisonId());

        //close the session
        session.close();
        System.out.println("Laptop added to database with ID: " + laptop.getLaptopId() +
                 "\nLaptop Model: " + laptopModel +
                "\nLaptop Description: " + laptopDescription +
                "\nLaptop Link: " + laptopLink +
                "\nLaptop Image Url: " + laptopImgUrl );
        return ids;
    }

    /*public boolean checkLaptopDuplicate(){
        //Set a new session factory
        Session session = sessionFactory.getCurrentSession();

    }*/


//    public boolean checkComparisonDuplicate(){
//
//    }

//    public Laptop matchLaptop(){
//
//    }

//    public void deleteLaptop(int id){
//
//    }

//    public void deleteComparison(int id){
//        Comparison comparison;
//
//    }

    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    /** Closes Hibernate down and stops its threads from running*/
    public void shutDown(){
        sessionFactory.close();
    }

}
