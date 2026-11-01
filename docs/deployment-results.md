# Local deployment results

Deployed on the local Zeebe gateway at `localhost:26500` on 2026-11-01.

| Lab | Deployment key | Process definition key | Decision key |
|---|---:|---:|---:|
| Loan approval | 2251799813736795 | 2251799813736798 | 2251799813736797 |
| Order fulfillment | 2251799813736800 | 2251799813736803 | 2251799813736802 |
| Patient triage | 2251799813736804 | 2251799813736807 | 2251799813736806 |

All three BPMN processes and their paired DMN decision resources were accepted by the engine at version 1. Starting an instance requires a worker for the service-task types listed in `src/main/java/edu/camunda/labs/WorkflowWorkers.java`.
