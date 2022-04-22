package ro.unibuc.hello.controller;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.service.CoinService;
import ro.unibuc.hello.data.CoinRepository;
import ro.unibuc.hello.dto.CoinDto;
import ro.unibuc.hello.data.CoinEntity;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
@Controller
public class CoinController {

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private CoinService coinService;

    /*@Autowired
    MeterRegistry metricsRegistry;*/

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/refresh-price")
    @ResponseBody
    @Timed(value = "coin.refresh.time", description = "Time taken to return message")
    @Counted(value = "coin.refresh.count", description = "Times message was returned")
    public int dataRefresh() {
        JSONArray refreshedData = coinService.getPricesandNames();
        for (int i = 0; i < refreshedData.size(); i++) {
            JSONObject x = (JSONObject) refreshedData.get(i);
            CoinEntity coin = new CoinEntity(counter.incrementAndGet(),
                    x.get("name").toString(),
                    (long) Double.parseDouble(x.get("price").toString()));
            coinRepository.save(coin);
        }
//      return "Data update successful!";
        //metricsRegistry.counter("my_non_aop_metric", "endpoint", "coin").increment(counter.incrementAndGet());
        return 1;
    }

    @GetMapping("/show-list")
    @ResponseBody
    @Timed(value = "coin.list.time", description = "Time taken to return list")
    @Counted(value = "coin.list.count", description = "Times list was returned")
    public String showAll() {
        List<CoinEntity> coinEntities = coinRepository.findAll();
        String dataList = "";
        for(CoinEntity coinEntity : coinEntities) {
            dataList += String.format("Name: %s\nPrice: %d", coinEntity.name, coinEntity.price);
            dataList += "\n";
        }
        return dataList;
    }

    @GetMapping("/search-low-price")
    @ResponseBody
    public String showPrice(@RequestParam(name="price", required=true, defaultValue="0") long price) {
        List<CoinEntity> coins = new ArrayList<>();
        try {
            coins = coinRepository.findByPriceBetween(0l, price);
        }
        catch(NullPointerException e)
        {
            return String.format("Error: No CryptoCoins with a price lower than %d", price);
        }
        String dataList = "";
        for(CoinEntity coinEntity : coins) {
            dataList += String.format("Name: %s\nPrice: %d", coinEntity.name, coinEntity.price);
            dataList += "\n";
        }
        return dataList;
    }
}
