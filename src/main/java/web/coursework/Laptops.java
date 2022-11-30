package web.coursework;

public class Laptops {
    private int laptopId;
    private int brandId;
    private String laptopModel;
    private String laptopDescription;
    private String laptopLink;
    private String laptopImgUrl;

    public Laptops(){

    }

    //Getters
    public int getLaptopId() {
        return laptopId;
    }
    public String getLaptopModel() { return laptopModel; }
    public int getBrandId() { return brandId;}
    public String getLaptopDescription() { return laptopDescription;}
    public String getLaptopLink() { return laptopLink;}
    public String getLaptopImgUrl () {return laptopImgUrl;}


    //Setters
    public void setLaptopId(int laptopId) { this.laptopId = laptopId;}
    public void setBrandId(int brandId) { this.brandId = brandId;}
    public void setLaptopModel(String laptopModel) { this.laptopModel = laptopModel;}
    public void setLaptopDescription(String laptopDescription) { this.laptopDescription = laptopDescription;}
    public void setLaptopLink(String laptopLink){ this.laptopLink = laptopLink; }
    public void setLaptopImgUrl(String laptopImgUrl) { this.laptopImgUrl = laptopImgUrl;}


    /** Returns a String description of the class */
    @Override
    public String toString(){

        return "Laptop id: " + laptopId +
                "\n Laptop name: " + laptopModel +
                "\n Brand id: " + brandId +
                "\n Laptop description: " + laptopDescription +
                "\n Image url: " + laptopImgUrl;
    }
}
