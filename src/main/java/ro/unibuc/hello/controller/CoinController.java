package ro.unibuc.hello.controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.Service.CoinService;
import ro.unibuc.hello.data.CoinRepository;
import ro.unibuc.hello.dto.CoinDto;

@Controller

public class CoinController {
    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private CoinService coinService;

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/refresh-price")
    @ResponseBody
    public CoinDto dataRefresh () {
        JSONArray refreshedData = coinService.getPricesandNames();
        ArrayList<CoinDto> coinList = new ArrayList <> ();
        for (int i = 0; i < refreshedData.size(); i++){
            JSONObject x = (JSONObject) refreshedData.get(i);
            CoinDto coin = new CoinDto(counter.incrementAndGet(),
                                       x.get("name").toString(),
                                       Double.parseDouble(x.get("price").toString()));
            coinList.add(coin);
        }
        return coinList.get(0);
    }






}
