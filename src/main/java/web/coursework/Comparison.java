package web.coursework;

//represent the comparison table from the database

public class Comparison {

    private int comparisonId;
    private int laptopId;
    private String price;
    private String sourceUrl;
    private String logoUrl;

    public Comparison() {

    }

    //getters
    public int getComparisonId() {
        return comparisonId;
    }
    public int getLaptopId() { return laptopId; }
    public String getPrice() { return price;}
    public String getSourceUrl() { return sourceUrl;}
    public String getLogoUrl () {return logoUrl;}

    //Setters
    public void setComparisonId(int comparisonId) { this.comparisonId= comparisonId;}
    public void setLaptopId(int laptopId) { this.laptopId= laptopId;}
    public void setPrice(String price) { this.price = price;}
    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl;}
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl;}

}
