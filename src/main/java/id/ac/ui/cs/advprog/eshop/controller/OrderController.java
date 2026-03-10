package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create")
    public String createOrderPage() {
        return "CreateOrder";
    }

    @GetMapping("/history")
    public String orderHistoryPage() {
        return "OrderHistory";
    }

    @PostMapping("/history")
    public String orderHistoryResult(
            @RequestParam String author,
            Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        return "OrderHistory";
    }

    @GetMapping("/pay/{orderId}")
    public String payOrderPage(
            @PathVariable String orderId,
            Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "PayOrder";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrder(
            @PathVariable String orderId,
            @RequestParam String method,
            @RequestParam Map<String, String> allParams,
            Model model) {
        Order order = orderService.findById(orderId);
        allParams.remove("method");
        Payment payment = paymentService.addPayment(order, method, allParams);
        model.addAttribute("payment", payment);
        return "PaymentResult";
    }
}
