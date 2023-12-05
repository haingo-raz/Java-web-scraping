package web.coursework;

/**
 * Represents the laptops table from the database
 */
public class Laptops {
    private int laptopId;
    private String laptopBrand;
    private String laptopModel;
    private String laptopDescription;
    private String laptopImgUrl;

    public Laptops(){

    }

    //Getters
    public int getLaptopId() {
        return laptopId;
    }
    public String getLaptopBrand() { return laptopBrand;}
    public String getLaptopModel() { return laptopModel; }

    public String getLaptopDescription() { return laptopDescription;}
    public String getLaptopImgUrl () {return laptopImgUrl;}


    //Setters
    public void setLaptopId(int laptopId) { this.laptopId = laptopId;}
    public void setLaptopBrand(String laptopBrand) { this.laptopBrand = laptopBrand;}
    public void setLaptopModel(String laptopModel) { this.laptopModel = laptopModel;}
    public void setLaptopDescription(String laptopDescription) { this.laptopDescription = laptopDescription;}
    //public void setLaptopLink(String laptopLink){ this.laptopLink = laptopLink; }
    public void setLaptopImgUrl(String laptopImgUrl) { this.laptopImgUrl = laptopImgUrl;}


    /** Returns a String description of the class */
    @Override
    public String toString(){

        return "Laptop id: " + laptopId +
                "\n Laptop name: " + laptopModel +
                "\n Brand id: " + laptopBrand +
                "\n Laptop description: " + laptopDescription +
                "\n Image url: " + laptopImgUrl;
    }
}
