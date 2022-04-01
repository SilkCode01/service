package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.CoinEntity;
import ro.unibuc.hello.data.CoinRepository;
import ro.unibuc.hello.dto.CoinDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

import javax.swing.text.html.parser.Entity;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class CoinApplicationT {
    @Autowired
    private CoinRepository coinRepository;

    private final AtomicLong counter = new AtomicLong();

    public CoinDto newCoin(String name, double price){
        return new CoinDto(counter.incrementAndGet(), name, price);
    }
    public CoinDto buildCoinFromInfo(long id) throws EntityNotFoundException {
        CoinEntity coinEntity = coinRepository.findByid(id);
        if (coinEntity == null) {
            throw new EntityNotFoundException(id);
        }
        return new CoinDto(counter.incrementAndGet(), coinEntity.name, coinEntity.price);
    }



}
