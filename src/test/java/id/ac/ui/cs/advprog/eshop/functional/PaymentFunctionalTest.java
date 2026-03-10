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
class PaymentFunctionalTest {

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
    void paymentDetailPage_titleIsCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/detail");
        assertEquals("Payment Detail", driver.getTitle());
    }

    @Test
    void paymentAdminListPage_titleIsCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/list");
        assertEquals("Payment List", driver.getTitle());
    }

    @Test
    void paymentDetailSearch_staysOnDetailPage(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/detail");
        driver.findElement(By.name("paymentId")).sendKeys("test-id");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        assertTrue(driver.getCurrentUrl().contains("/payment/detail"));
    }
}