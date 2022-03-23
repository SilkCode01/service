package ro.unibuc.hello.data;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

public class CoinEntity {
    @Id
    public String id;

    public String title;
    public String description;

    public CoinEntity(String title, String description) {
        this.title = title;
        this.description = description;

    }

    @Override
    public String toString() {
        return String.format(
                "Information[id= '%s', title='%s', description='%s']",
                id, title, description);
    }

}
