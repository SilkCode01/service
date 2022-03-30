package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.controller.CoinController;
import ro.unibuc.hello.data.CoinEntity;
import ro.unibuc.hello.data.CoinRepository;


import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = CoinRepository.class)
public class CoinApplication {

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private CoinController coinController;

    public static void main(String[] args) {
        SpringApplication.run(CoinApplication.class, args);
    }

    @PostConstruct
    public void runAfterObjectCreated() {
        coinRepository.deleteAll();
        List<CoinEntity> insertIndatabase = coinController.dataRefresh();
        for (CoinEntity coinEntity : insertIndatabase) {
            coinRepository.save(coinEntity);
        }

    }
}