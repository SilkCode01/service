package ro.unibuc.hello;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ro.unibuc.hello.data.CoinRepository;

@SpringBootTest
class HelloApplicationTests {

	@MockBean
	CoinRepository mockRepository;

	@Test
	void contextLoads() {
	}

}
