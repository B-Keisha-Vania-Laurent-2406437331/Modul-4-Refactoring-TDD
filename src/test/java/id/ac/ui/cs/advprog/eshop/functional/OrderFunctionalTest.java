package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.EshopApplication;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        classes = EshopApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ExtendWith(SeleniumJupiter.class)
class OrderFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupUrl() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createOrderPage_titleIsCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/order/create");
        assertEquals("Create Order", driver.getTitle());
    }

    @Test
    void orderHistoryPage_titleIsCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/order/history");
        assertEquals("Order History", driver.getTitle());
    }

    @Test
    void orderHistoryPost_staysOnHistoryPage(ChromeDriver driver) {
        driver.get(baseUrl + "/order/history");
        driver.findElement(By.name("author")).sendKeys("Safira Sudrajat");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        assertTrue(driver.getCurrentUrl().contains("/order/history"));
    }
}
