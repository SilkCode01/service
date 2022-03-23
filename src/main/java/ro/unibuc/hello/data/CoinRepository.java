package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CoinRepository extends MongoRepository<CoinEntity, String> {

    public CoinRepository findByTitle(String title);
    public List<CoinEntity> findByDescription(String description);

}

