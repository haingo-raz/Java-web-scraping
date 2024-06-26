package web.coursework;


import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Hibernate example that uses XML mapping to specify the mapping between a laptop
 * object and the laptops table in the cw1 database
 */
public class HibernateXml {

    private static SessionFactory sessionFactory;
    public static boolean laptopDeletionCompleted = false;
    public static boolean comparisonDeletionCompleted = false;

    /** Empty constructor */
    public HibernateXml(){

    }

    //Adding a new Laptop in the database
    public ArrayList<Integer> addLaptop(String laptopBrand, String laptopModel, String laptopDescription, String laptopImgUrl, String sourceUrl, String price, String logoUrl) {
        //create a new session instance
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        if (!checkLaptopDuplicates("laptopDescription", laptopDescription, "laptopImgUrl", laptopImgUrl)) {

            Laptops laptop = new Laptops();
            Comparison comparison = new Comparison();

            //Set the values
            laptop.setLaptopBrand(laptopBrand);
            laptop.setLaptopModel(laptopModel);
            laptop.setLaptopDescription(laptopDescription);
            laptop.setLaptopImgUrl(laptopImgUrl);

            //Add laptop to the database
            session.save(laptop);

            // Populate comparison table
            comparison.setLaptopId(laptop.getLaptopId());
            comparison.setPrice(price);
            comparison.setSourceUrl(sourceUrl);
            comparison.setLogoUrl(logoUrl);
            //Add to database
            session.save(comparison);

            //Commit transaction to save to database
            session.getTransaction().commit();

            ArrayList<Integer> ids = new ArrayList<>();

            ids.add(laptop.getLaptopId());
            ids.add(comparison.getComparisonId());

            //close the session
            session.close();
            System.out.println("Laptop added to database with ID: " + laptop.getLaptopId() +
                    "\nLaptop Brand: " + laptopBrand +
                    "\nLaptop Model: " + laptopModel +
                    "\nLaptop Description: " + laptopDescription +
                    "\nLaptop Image Url: " + laptopImgUrl);
            return ids;
        }
        else if (checkComparisonDuplicate("sourceUrl", sourceUrl)){
            session.close();
            System.out.println("Comparison already in the database");
            return new ArrayList<>();
        }
        else {
            Laptops existingLaptop = matchLaptop("laptopModel", laptopModel);
            Comparison comparison = new Comparison();

            //Add comparison to database
            comparison.setLaptopId(existingLaptop.getLaptopId());
            comparison.setPrice(price);
            comparison.setSourceUrl(sourceUrl);

            //Save
            session.getTransaction().commit();
            session.close();
            System.out.println("New comparison added");
            return new ArrayList<>();
        }
    }


    //Checking for laptop duplicates
    public boolean checkLaptopDuplicates(String column1, String data1, String column2, String data2) {
        //Set a new session factory
        Session session = sessionFactory.getCurrentSession();

        List<Laptops> laptopList = session.createQuery("from Laptops where " + column1 + " = '" + data1 + "' AND " + column2 + " = '" + data2 + "'").getResultList();
        return laptopList.size() > 0;
    }

    //Checking for comparison duplicates
    public boolean checkComparisonDuplicate(String column1, String data1){
        //Set a new session factory
        Session session = sessionFactory.getCurrentSession();

        List<Comparison> comparisonList = session.createQuery(" from Comparison where " + column1 + " = '" + data1 + "'").getResultList();
        return comparisonList.size() > 0;
    }

    //Matching laptops
    public Laptops matchLaptop(String column1, String data1){
        //Set a new session factory
        Session session = sessionFactory.getCurrentSession();
        List<Laptops> laptopList = session.createQuery("from Laptops where "+ column1 + " = " + data1).getResultList();
        return laptopList.get(0);
    }


    //Deleting a laptop by id
    public void deleteLaptop(int laptopId){
    Laptops laptops;

    //Create instance of Laptops class
        try(Session session = sessionFactory.getCurrentSession()) {

            //New instance of Laptop class
            laptops = new Laptops();
            laptops.setLaptopId(laptopId);

            //Start transaction
            session.beginTransaction();

            //Search for a laptop in the database having the same id
            Object persistentInstance = session.load(Laptops.class, laptopId);

            //Delete in case of match
            if (persistentInstance != null) {
                session.delete(persistentInstance);
            } else {
                System.out.println("Laptop with id " + laptopId + " does not exist");
            }
            //save to database
            session.getTransaction().commit();
        }
        System.out.println("Laptop deleted with ID " + laptopId);
        laptopDeletionCompleted = true;
    }

    //Deleting a comparison by id
    public void deleteComparison(int comparisonId){
        Comparison comparison;

        try(Session session = sessionFactory.getCurrentSession()){
            comparison = new Comparison();
            comparison.setComparisonId(comparisonId);

            //Start transaction
            session.beginTransaction();

            //Search for a laptop in the database having the same id
            Object persistentInstance = session.load(Comparison.class, comparisonId);

            //Delete in case of match
            if (persistentInstance != null) {
                session.delete(persistentInstance);
            } else {
                System.out.println("Comparison with id " + comparisonId + " does not exist");
            }
            //save to database
            session.getTransaction().commit();
        }
        System.out.println("Comparison deleted with ID " + comparisonId);
        comparisonDeletionCompleted = true;
    }

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