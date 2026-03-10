package id.ac.ui.cs.advprog.eshop.enums;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMethodTest {

    @Test
    void contains_validMethod() {
        assertTrue(PaymentMethod.contains("VOUCHER_CODE"));
        assertTrue(PaymentMethod.contains("CASH_ON_DELIVERY"));
    }

    @Test
    void contains_invalidMethod() {
        assertFalse(PaymentMethod.contains("INVALID_METHOD"));
    }

    @Test
    void getValue() {
        assertEquals("VOUCHER_CODE", PaymentMethod.VOUCHER_CODE.getValue());
        assertEquals("CASH_ON_DELIVERY", PaymentMethod.CASH_ON_DELIVERY.getValue());
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("p-001", "INVALID_METHOD", data);
        assertEquals("REJECTED", payment.getStatus());
    }
}
