# Test cases and expected results

| Lab | Test input | Expected route | Actual result after run | Status |
|---|---|---|---|---|
| Loan | APP-101, 6000, 30000 | Approved | Record after execution | Pending execution |
| Loan | BAD, 0, 30000 | Rejected at validation | Record after execution | Pending execution |
| Loan | APP-999, 3000, 25000 | Manual loan review | Record after Tasklist completion | Pending execution |
| Loan | CREDIT-FAIL | Incident at credit assessment; fix input/worker then retry | Record after recovery | Pending execution |
| Order | SKU-1, qty 2, token ok | Fulfilled | Record after execution | Pending execution |
| Order | OOS-1 | Stopped: out of stock | Record after execution | Pending execution |
| Order | token decline | Stopped: payment declined | Record after execution | Pending execution |
| Order | VIP / qty 15 | Manual order verification | Record after Tasklist completion | Pending execution |
| Order | FAIL-42 | Inventory incident; correct cause and retry | Record after recovery | Pending execution |
| Triage | PAT-101, LOW, morning | Booked | Record after execution | Pending execution |
| Triage | PAT-102, HIGH | Authorized staff review | Record after Tasklist completion | Pending execution |
| Triage | PAT-103, CRITICAL | Immediate staff assessment | Record after Tasklist completion | Pending execution |
| Triage | PAT-104, preference none | Staff review: no slot | Record after Tasklist completion | Pending execution |
| Triage | PAT-FAIL | Scheduling incident; correct cause and retry | Record after recovery | Pending execution |
