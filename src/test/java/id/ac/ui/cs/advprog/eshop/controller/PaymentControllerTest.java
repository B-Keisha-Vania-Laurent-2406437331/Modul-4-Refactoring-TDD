package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;

    @Test
    void paymentDetailForm() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentDetail"));
    }

    @Test
    void paymentDetail() throws Exception {
        Payment payment = new Payment("p-001", "VOUCHER_CODE", new HashMap<>());
        when(paymentService.getPayment("p-001")).thenReturn(payment);

        mockMvc.perform(get("/payment/detail/p-001"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentDetail"));
    }

    @Test
    void paymentAdminList() throws Exception {
        List<Payment> payments = new ArrayList<>();
        when(paymentService.getAllPayments()).thenReturn(payments);

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentAdminList"));
    }

    @Test
    void paymentAdminDetail() throws Exception {
        Payment payment = new Payment("p-001", "VOUCHER_CODE", new HashMap<>());
        when(paymentService.getPayment("p-001")).thenReturn(payment);

        mockMvc.perform(get("/payment/admin/detail/p-001"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentAdminDetail"));
    }

    @Test
    void setPaymentStatus() throws Exception {
        Payment payment = new Payment("p-001", "VOUCHER_CODE", new HashMap<>());
        when(paymentService.getPayment("p-001")).thenReturn(payment);
        when(paymentService.setStatus(any(), anyString())).thenReturn(payment);

        mockMvc.perform(post("/payment/admin/set-status/p-001")
                        .param("status", "SUCCESS"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentAdminDetail"));
    }
}