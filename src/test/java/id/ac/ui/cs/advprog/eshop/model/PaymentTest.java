package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
    }

    @Test
    void testCreatePaymentVoucherCodeValid() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeNot16Chars() {
        paymentData.put("voucherCode", "ESHOP123ABC");
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeNotStartWithESHOP() {
        paymentData.put("voucherCode", "TOKOP1234ABC5678");
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeNot8Numerics() {
        paymentData.put("voucherCode", "ESHOP123ABCDEFGH");
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherCodeNull() {
        paymentData.put("voucherCode", null);
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCODValid() {
        paymentData.put("address", "Jl. Merdeka No. 1");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("p-002", "CASH_ON_DELIVERY", paymentData);
        assertEquals("WAITING", payment.getStatus());
    }

    @Test
    void testCreatePaymentCODEmptyAddress() {
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("p-002", "CASH_ON_DELIVERY", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCODNullAddress() {
        paymentData.put("address", null);
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("p-002", "CASH_ON_DELIVERY", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCODEmptyDeliveryFee() {
        paymentData.put("address", "Jl. Merdeka No. 1");
        paymentData.put("deliveryFee", "");
        Payment payment = new Payment("p-002", "CASH_ON_DELIVERY", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCODNullDeliveryFee() {
        paymentData.put("address", "Jl. Merdeka No. 1");
        paymentData.put("deliveryFee", null);
        Payment payment = new Payment("p-002", "CASH_ON_DELIVERY", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testGetPaymentData() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testGetMethod() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);
        assertEquals("VOUCHER_CODE", payment.getMethod());
    }
}
