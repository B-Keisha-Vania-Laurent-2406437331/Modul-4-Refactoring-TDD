package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    @Setter
    String status;
    Map<String, String> paymentData;

    @Setter
    private Order order;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = determineStatus(method, paymentData);
    }

    private String determineStatus(String method, Map<String, String> paymentData) {
        if (method.equals(PaymentMethod.VOUCHER_CODE.getValue())) {
            return validateVoucherCode(paymentData.get("voucherCode"));
        } else if (method.equals(PaymentMethod.CASH_ON_DELIVERY.getValue())) {
            return validateCOD(paymentData);
        }
        return PaymentStatus.REJECTED.getValue();
    }

    private String validateVoucherCode(String code) {
        if (code == null) return PaymentStatus.REJECTED.getValue();
        if (code.length() != 16) return PaymentStatus.REJECTED.getValue();
        if (!code.startsWith("ESHOP")) return PaymentStatus.REJECTED.getValue();

        long numericCount = code.chars()
                .filter(Character::isDigit)
                .count();
        if (numericCount != 8) return PaymentStatus.REJECTED.getValue();

        return PaymentStatus.SUCCESS.getValue();
    }

    private String validateCOD(Map<String, String> data) {
        String address = data.get("address");
        String deliveryFee = data.get("deliveryFee");

        if (address == null || address.isEmpty()) return PaymentStatus.REJECTED.getValue();
        if (deliveryFee == null || deliveryFee.isEmpty()) return PaymentStatus.REJECTED.getValue();

        return PaymentStatus.WAITING.getValue();
    }
}