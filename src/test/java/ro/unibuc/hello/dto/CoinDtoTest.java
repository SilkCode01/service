package ro.unibuc.hello.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.dto.CoinDto;

@SpringBootTest
class CoinDtoTest {
    CoinDto coin = new CoinDto(1, "Cardano", 0.9);

    @Test
    void test_name(){
        Assertions.assertSame("Cardano", coin.getName());
    }
    @Test
    void test_price(){
        Assertions.assertEquals(0.9, coin.getPrice());
    }
    @Test
    void test_id(){
        Assertions.assertEquals(1, coin.getId());
    }

}

