package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.CoinEntity;
import ro.unibuc.hello.data.CoinRepository;


import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = CoinRepository.class)
public class CoinApplication {

    @Autowired
    private CoinRepository coinRepository;

    public static void main(String[] args) {
        SpringApplication.run(CoinApplication.class, args);
    }

    @PostConstruct
    public void runAfterObjectCreated() {
        coinRepository.deleteAll();
        coinRepository.save(new CoinEntity("Overview",
                "This is an example of using a data storage engine running separately from our applications server"));
    }

}