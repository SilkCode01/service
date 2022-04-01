package ro.unibuc.hello.data;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

public class CoinEntity {
    @Id
    public long id;

    public String name;
    public long price;

    public CoinEntity() {}

    public CoinEntity(long id, String name, long price) {
        this.name = name;
        this.id = id;
        this.price = price;

    }

}
