package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = determineStatus(method, paymentData);
    }

    private String determineStatus(String method, Map<String, String> paymentData) {
        if (method.equals("VOUCHER_CODE")) {
            return validateVoucherCode(paymentData.get("voucherCode"));
        } else if (method.equals("CASH_ON_DELIVERY")) {
            return validateCOD(paymentData);
        }
        return "REJECTED";
    }

    private String validateVoucherCode(String code) {
        if (code == null) return "REJECTED";
        if (code.length() != 16) return "REJECTED";
        if (!code.startsWith("ESHOP")) return "REJECTED";

        long numericCount = code.chars()
                .filter(Character::isDigit)
                .count();
        if (numericCount != 8) return "REJECTED";

        return "SUCCESS";
    }

    private String validateCOD(Map<String, String> data) {
        String address = data.get("address");
        String deliveryFee = data.get("deliveryFee");

        if (address == null || address.isEmpty()) return "REJECTED";
        if (deliveryFee == null || deliveryFee.isEmpty()) return "REJECTED";

        return "WAITING";
    }
}