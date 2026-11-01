package edu.camunda.labs;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Optional REST endpoints for Connector testing; workers use the same synthetic rules. */
@RestController @RequestMapping("/mock")
public class MockApiController {
  @PostMapping("/credit") public ResponseEntity<Map<String,Object>> credit(@RequestBody Map<String,Object> body) {
    String id=String.valueOf(body.getOrDefault("applicantId", "")); if ("CREDIT-FAIL".equals(id)) return ResponseEntity.status(503).body(Map.of("error","synthetic outage"));
    return ResponseEntity.ok(Map.of("creditScore",id.endsWith("999")?520:720,"creditStatus",id.endsWith("999")?"FAIL":"PASS")); }
  @PostMapping("/payment") public ResponseEntity<Map<String,Object>> payment(@RequestBody Map<String,Object> body) {
    String token=String.valueOf(body.getOrDefault("paymentToken", "")); if ("timeout".equals(token)) return ResponseEntity.status(504).body(Map.of("error","synthetic timeout"));
    return ResponseEntity.ok(Map.of("paymentStatus","decline".equals(token)?"DECLINED":"PAID")); }
  @PostMapping("/slots") public ResponseEntity<Map<String,Object>> slots(@RequestBody Map<String,Object> body) {
    String ref=String.valueOf(body.getOrDefault("patientRef", "")); if ("PAT-FAIL".equals(ref)) return ResponseEntity.status(503).body(Map.of("error","synthetic outage"));
    boolean available=!"none".equalsIgnoreCase(String.valueOf(body.getOrDefault("appointmentPreference", "")));
    return ResponseEntity.ok(Map.of("slotAvailable",available,"availableSlot",available?"2030-01-15T10:00:00Z":"")); }
}
