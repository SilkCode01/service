package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.CoinRepository;
import ro.unibuc.hello.dto.CoinDto;

@SpringBootTest
@Tag("IT")

public class CoinApplicationTTestsIT {

    @Autowired
    CoinRepository coinRepository;

    @Autowired
    CoinApplicationT coinApplicationT;

    @Test
    void test_buildCoinFromInfo_returnsCoinWithInformation() {
        // Arrange
        long id = 1;

        // Act
        CoinDto coinDto = coinApplicationT.buildCoinFromInfo(id);

        // Assert
        Assertions.assertEquals(1, coinDto.getId());
        Assertions.assertEquals("Sake", coinDto.getName());
    }




}

