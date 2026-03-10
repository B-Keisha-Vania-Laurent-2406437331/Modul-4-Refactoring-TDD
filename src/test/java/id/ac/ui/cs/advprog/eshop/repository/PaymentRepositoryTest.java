package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testSaveNewPayment() {
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);
        Payment result = paymentRepository.save(payment);

        Payment found = paymentRepository.findById("p-001");
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), found.getId());
    }

    @Test
    void testSaveUpdatePayment() {
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);
        paymentRepository.save(payment);

        Map<String, String> newData = new HashMap<>();
        newData.put("voucherCode", "ESHOP0000ABC1234");
        Payment updated = new Payment("p-001", "VOUCHER_CODE", newData);
        paymentRepository.save(updated);

        Payment found = paymentRepository.findById("p-001");
        assertEquals("p-001", found.getId());
        assertEquals(updated.getPaymentData(), found.getPaymentData());
    }

    @Test
    void testFindByIdFound() {
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);
        paymentRepository.save(payment);

        Payment found = paymentRepository.findById("p-001");
        assertNotNull(found);
        assertEquals("p-001", found.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Payment found = paymentRepository.findById("p-999");
        assertNull(found);
    }

    @Test
    void testFindAllPayments() {
        Payment payment1 = new Payment("p-001", "VOUCHER_CODE", paymentData);
        Map<String, String> codData = new HashMap<>();
        codData.put("address", "Jl. Merdeka");
        codData.put("deliveryFee", "10000");
        Payment payment2 = new Payment("p-002", "CASH_ON_DELIVERY", codData);

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> all = paymentRepository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testSaveNewPaymentWhenRepositoryNotEmpty() {
        Payment payment1 = new Payment("p-001", "VOUCHER_CODE", paymentData);
        paymentRepository.save(payment1);

        Map<String, String> codData = new HashMap<>();
        codData.put("address", "Jl. Merdeka");
        codData.put("deliveryFee", "10000");
        Payment payment2 = new Payment("p-002", "CASH_ON_DELIVERY", codData);
        Payment result = paymentRepository.save(payment2);

        assertEquals("p-002", result.getId());
        assertEquals(2, paymentRepository.findAll().size());
    }

    @Test
    void testFindByIdNotFoundWhenRepositoryNotEmpty() {
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);
        paymentRepository.save(payment);

        Payment found = paymentRepository.findById("p-999");
        assertNull(found);
    }
}
