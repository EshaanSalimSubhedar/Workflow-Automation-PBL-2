# Deployment and evidence checklist

1. Run `mvn spring-boot:run` and verify `GET /mock` endpoints return the expected synthetic responses by using the API examples in the report.
2. In Modeler, deploy the matching `.dmn` and `.bpmn` files. Capture a deployment screenshot for each pair.
3. Start every payload in `test-data/start-events.json`; replace the **Actual result** and **Status** cells in `docs/test-cases.md` with observed values.
4. In Tasklist, claim and complete one task for every candidate group. Capture the task form/details and completion state.
5. For each `FAIL` case, open its Operate incident, capture the failed activity and variables, correct the mock/worker condition, retry, and capture the completed instance.

Keep screenshots separate from this repository's source directory and name them `labN-scenario-description.png`. They must be captured from your own running environment.
