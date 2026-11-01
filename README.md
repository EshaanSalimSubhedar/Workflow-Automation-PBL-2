# Camunda 8 practical assignment - Labs 1 to 3

This submission contains three deployable BPMN/DMN model pairs, a Spring Boot worker and mock REST API, test data, and a submission report. All data is synthetic.

## Prerequisites

- Java 21 and Maven 3.9+
- A Camunda 8 cluster (SaaS or self-managed) and Modeler 5+

## Configure and run

1. Copy `.env.example` values into environment variables (do not commit secrets).
2. Set `CAMUNDA_MODE=simple` for a local unsecured cluster, or leave `saas` and provide cluster credentials.
3. Start the worker and mock REST API: `mvn spring-boot:run`.
4. Deploy each matching BPMN and DMN pair from `src/main/resources/models/` in Modeler. Deploy the DMN before, or in the same deployment as, its BPMN.
5. Start instances using the payloads in `test-data/start-events.json`.

The mock API is available at `http://localhost:8080/mock`. It deliberately returns failures for values containing `FAIL` (for example `productId: FAIL-42`) or a payment token of `decline`.

## Assignment evidence to capture after running

The included models and code are ready to run, but Operate and Tasklist screenshots must come from the student's own Camunda environment. Follow `docs/evidence-checklist.md` after deployment; do not replace those screenshots with placeholders.
