package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private PaymentService paymentService;

    @Test
    void createOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateOrder"));
    }

    @Test
    void orderHistoryPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("OrderHistory"));
    }

    @Test
    void orderHistoryPost() throws Exception {
        List<Order> orders = new ArrayList<>();
        when(orderService.findAllByAuthor("Safira")).thenReturn(orders);

        mockMvc.perform(post("/order/history")
                        .param("author", "Safira"))
                .andExpect(status().isOk())
                .andExpect(view().name("OrderHistory"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    void payOrderPage() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        Order order = new Order("o-001", products, 1000L, "Safira");
        when(orderService.findById("o-001")).thenReturn(order);

        mockMvc.perform(get("/order/pay/o-001"))
                .andExpect(status().isOk())
                .andExpect(view().name("PayOrder"));
    }

    @Test
    void payOrderPost() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        Order order = new Order("o-001", products, 1000L, "Safira");
        Payment payment = new Payment("p-001", "VOUCHER_CODE", new HashMap<>());
        when(orderService.findById("o-001")).thenReturn(order);
        when(paymentService.addPayment(any(), anyString(), any())).thenReturn(payment);

        mockMvc.perform(post("/order/pay/o-001")
                        .param("method", "VOUCHER_CODE"))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentResult"));
    }
}