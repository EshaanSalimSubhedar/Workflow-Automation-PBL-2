# Implementation report

## Architecture

Each lab has an independent BPMN process and DMN decision table. The BPMN model invokes typed Zeebe jobs; `WorkflowWorkers` performs input validation and calls deterministic simulated services. `MockApiController` exposes equivalent REST endpoints for a REST Connector exercise. A DMN business-rule task produces a result variable used by BPMN gateways. Human intervention is modelled as a candidate-group user task for Tasklist.

| Lab | BPMN process | DMN decision | Workers | User-task group |
|---|---|---|---|---|
| 1 | `loan_approval` | `loan_decision` | `validate-loan`, `assess-credit` | `loan-officers` |
| 2 | `order_fulfillment` | `order_priority` | `validate-order`, `check-inventory`, `process-payment` | `order-reviewers` |
| 3 | `patient_triage` | `triage_decision` | `validate-appointment`, `find-slot` | `clinical-staff` |

## DMN educational rules

- Loan: income >= 5000, amount <= 50000, score >= 700 approves; a lower-risk middle band goes to manual review; all other cases reject.
- Order: high-value or VIP orders are high priority; express shipping is expedited; otherwise standard.
- Triage: `CRITICAL` is urgent assessment, `HIGH` is priority review, and all other synthetic requests are routine.

## Variables

| Variable | Type | Purpose |
|---|---|---|
| `applicationValid`, `orderValid`, `registrationValid` | boolean | Validation result |
| `creditScore`, `orderTotal` | number | Loan/order decisions |
| `stockAvailable`, `slotAvailable` | boolean | Routing condition |
| `paymentStatus`, `bookingStatus` | string | Integration result |
| `loanDecision`, `priorityDecision`, `triageDecision` | object | DMN result variable |

## REST Connector configuration

For a Connector exercise, configure an HTTP JSON POST to `http://host.docker.internal:8080/mock/credit`, `/payment`, or `/slots` (use `localhost` when Modeler and the API share the host). Set `Content-Type: application/json`; map the body from the relevant process variables and map the response into `creditResponse`, `paymentResponse`, or `slotResponse`. The BPMN version supplied uses workers so it runs without a Connector secret. Do not use real payment data.

## Limitations

The mock services are deterministic, deliberately minimal, and unsuitable for production. The models contain synthetic data only. Camunda deployment, Tasklist completion, incident resolution, and evidence capture must be performed in a real cluster by the submitter.
