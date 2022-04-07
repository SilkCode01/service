package ro.unibuc.hello.service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.unibuc.hello.data.CoinEntity;
import ro.unibuc.hello.data.CoinRepository;
import ro.unibuc.hello.dto.CoinDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)

public class CoinApplicationTTests {
    @Mock
    CoinRepository mockCoinRepository;

    @InjectMocks
    CoinApplicationT coinApplicationT = new CoinApplicationT();

    @Test
    void test_hello_returnsNewcoin () {
        // Arrange
        String name = "Sake";
        double price = 4864;

        CoinDto coinDto = coinApplicationT.newCoin(name, price);

        Assertions.assertEquals(4864, coinDto.getPrice());
        Assertions.assertSame("Sake", coinDto.getName());
    }

    @Test
    void test_hello_returnsNewcoin_whenNameNullandPriceNegative(){
        // Arrange

        // Act
        CoinDto coinDto = coinApplicationT.newCoin(null, -12);

        // Assert
        Assertions.assertEquals(-12, coinDto.getPrice());
        Assertions.assertSame(null, coinDto.getName());
    }

    @Test
    void test_hello_buildCoinFromInfo_returnCoinWithInfo() {
        // Arrange
        long id = 1;
        String name = "Sake";
        long price = 4864;
        CoinEntity coinEntity = new CoinEntity(id, name, price);
        when(mockCoinRepository.findByid(id)).thenReturn(coinEntity);
        // Act
        CoinDto coinDto = coinApplicationT.buildCoinFromInfo(id);
        // Assert
        Assertions.assertEquals(1, coinDto.getId());
        Assertions.assertEquals("Sake", coinDto.getName());
    }

    @Test
    void test_buildCoinFromInfo_throwsEntityNotFoundException_whenInformationNull() {
        // Arrange
        long id = 1;
        when(mockCoinRepository.findByid(id)).thenReturn(null);
        try {
            // Act
            CoinDto coinDto = coinApplicationT.buildCoinFromInfo(id);
        } catch (Exception ex) {
            // Assert
            Assertions.assertEquals(ex.getClass(), EntityNotFoundException.class);
            Assertions.assertEquals(ex.getMessage(), "Entity: 1 was not found");
        }
    }

}


