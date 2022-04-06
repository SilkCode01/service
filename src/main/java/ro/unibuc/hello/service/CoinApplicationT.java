package ro.unibuc.hello.service;

import org.apache.el.parser.AstFalse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.comparator.Comparators;
import ro.unibuc.hello.data.CoinEntity;
import ro.unibuc.hello.data.CoinRepository;
import ro.unibuc.hello.dto.CoinDto;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.controller.CoinController;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;



@Component
public class CoinApplicationT {
    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private CoinController coinController;

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

    public static boolean isSorted(List<Long> data){
        for(int i = data.size() - 1; i > 0; i--){
            if(data.get(i) > data.get(i - 1)){
                return false;
            }
        }
        return true;
    }


    public Boolean checkIfordered () {
        String allCoinsString = coinController.showAll();
        String parts[] = allCoinsString.split("[:\\n]");
        List<Long> coinPrices = new ArrayList<>();

        for (int i = 3; i < parts.length; i += 4){
            String temp = parts[i].substring(1);
            coinPrices.add(Long.parseLong(temp));

        }

        return isSorted(coinPrices);
    }

}
