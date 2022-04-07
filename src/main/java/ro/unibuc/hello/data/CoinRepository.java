package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CoinRepository extends MongoRepository<CoinEntity, Long> {

    public CoinEntity findByid(long id);
    public List<String> findByprice();
    public List<CoinEntity> findByPriceBetween(long price1, long price2);

}

