package ro.unibuc.hello.dto;

public class CoinDto {

    private long id;
    private String name;
    private double price;

    public CoinDto(long id, String name, double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(double price){
        this.price = price;
    }


    public long getId(){return id;}
    public String getName(){return name;}
    public double getPrice(){return price;}

}
