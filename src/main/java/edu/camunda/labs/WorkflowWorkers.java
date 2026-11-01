package edu.camunda.labs;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/** Synthetic workers only: no payment credentials or patient data are stored. */
@Component
public class WorkflowWorkers {
  @JobWorker(type = "validate-loan")
  public Map<String,Object> validateLoan(Map<String,Object> v) {
    boolean valid = text(v, "applicantId").matches("APP-[0-9]{3,}") && number(v,"monthlyIncome").compareTo(BigDecimal.ZERO) > 0;
    return Map.of("applicationValid", valid, "validationMessage", valid ? "validated" : "invalid applicantId or income");
  }

  @JobWorker(type = "assess-credit")
  public Map<String,Object> assessCredit(Map<String,Object> v) {
    if ("CREDIT-FAIL".equals(text(v,"applicantId"))) throw new IllegalStateException("Synthetic credit API outage: retry after correction");
    int score = text(v,"applicantId").endsWith("999") ? 520 : 720;
    return Map.of("creditScore", score, "creditStatus", score >= 650 ? "PASS" : "FAIL");
  }

  @JobWorker(type = "validate-order")
  public Map<String,Object> validateOrder(Map<String,Object> v) {
    boolean valid = !text(v,"customerRef").isBlank() && !text(v,"productId").isBlank() && number(v,"quantity").compareTo(BigDecimal.ZERO) > 0;
    BigDecimal total = number(v,"quantity").multiply(new BigDecimal("100"));
    return Map.of("orderValid", valid, "orderTotal", total, "customerCategory", text(v,"customerCategory"));
  }

  @JobWorker(type = "check-inventory")
  public Map<String,Object> checkInventory(Map<String,Object> v) {
    String product = text(v,"productId");
    if (product.startsWith("FAIL")) throw new IllegalStateException("Synthetic inventory API outage");
    boolean available = !product.startsWith("OOS");
    return Map.of("stockAvailable", available, "stockMessage", available ? "reserved" : "out of stock");
  }

  @JobWorker(type = "process-payment")
  public Map<String,Object> processPayment(Map<String,Object> v) {
    String token = text(v,"paymentToken");
    if ("timeout".equals(token)) throw new IllegalStateException("Synthetic payment timeout");
    return Map.of("paymentStatus", "decline".equals(token) ? "DECLINED" : "PAID", "paymentReference", "PAY-SYN-001");
  }

  @JobWorker(type = "validate-appointment")
  public Map<String,Object> validateAppointment(Map<String,Object> v) {
    boolean valid = text(v,"patientRef").matches("PAT-[0-9]{3,}") && !text(v,"appointmentPreference").isBlank();
    return Map.of("registrationValid", valid);
  }

  @JobWorker(type = "find-slot")
  public Map<String,Object> findSlot(Map<String,Object> v) {
    if (text(v,"patientRef").equals("PAT-FAIL")) throw new IllegalStateException("Synthetic scheduling API outage");
    boolean available = !text(v,"appointmentPreference").equalsIgnoreCase("none");
    Map<String,Object> result = new HashMap<>();
    result.put("slotAvailable", available);
    result.put("availableSlot", available ? "2030-01-15T10:00:00Z" : null);
    result.put("bookingStatus", available ? "HELD" : "NO_SLOT");
    return result;
  }

  private static String text(Map<String,Object> v, String key) { return String.valueOf(v.getOrDefault(key, "")); }
  private static BigDecimal number(Map<String,Object> v, String key) { return new BigDecimal(String.valueOf(v.getOrDefault(key, "0"))); }
}
