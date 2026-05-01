Root Cause Analysis: KelpDAO Infrastructure Compromise (April 2026)
Executive Summary
The $292M KelpDAO hack was not caused by a bug in the smart contract logic. It was an attack on the transport layer (LayerZero v2) by exploiting an insecure verifier configuration (1-of-1 DVN).

Technical Breakdown
Infrastructure Weakness: Using a single verifier created a "Single Point of Failure." The hacker isolated the RPC nodes of this verifier.

Verification Logic: The bridge accepted LzReceive messages without a secondary balance check (Solvency Check).

The Exploit: A hacker faked a burn event on the source network. The verifier, seeing the fake RPC state, confirmed the transaction.

Proposed Mitigation
Transition to an X-of-Y DVN configuration (minimum 2-of-3).

Implementation of cross-chain invariants at the smart contract level (Total Supply vs. Locked Assets check).

Markdown
## Recovery & Governance (Post-Incident)
As of April 25, 2026, a joint proposal [AIP] was submitted by Aave Labs, KelpDAO, and LayerZero to the Arbitrum DAO. 
* **Key Action:** Release of 30,765.66 ETH frozen by the Arbitrum Security Council.
* **Objective:** Remediation of rsETH impairment for affected users via the "DeFi United" coordinated effort.

* Root Cause Analysis: KelpDAO Infrastructure Compromise (April 2026)
Executive Summary
The $292M KelpDAO hack was not caused by a bug in the smart contract logic. It was an attack on the transport layer (LayerZero v2) by exploiting an insecure verifier configuration (1-of-1 DVN).

Technical Breakdown: Infrastructure Weakness
Single Point of Failure: Using a single verifier created a "Single Point of Failure".

RPC Isolation: The hacker (identified as Lazarus Group/TraderTraitor) isolated the RPC nodes of this verifier.

Fake State Confirmation: The verifier, seeing the fake RPC state, confirmed a fraudulent burn event on the source network, allowing the hacker to mint tokens on the destination chain.

Market Impact & Contagion (As of May 1, 2026)
Aave Liquidity Crisis: The protocol has frozen rsETH markets due to "bad debt" estimated between $123M and $230M.

Recovery Fund: A "DeFi United" initiative has been formed, with LayerZero Labs and Consensys contributing over 40,000 ETH combined to restore the rsETH peg.

DAO Intervention: Arbitrum DAO is currently voting on a 30,766 ETH grant to support affected users.

Proposed Mitigation & Industry Shift
Mandatory Multi-DVN: LayerZero has deprecated support for 1-of-1 DVN configurations.

Transition to X-of-Y: Transition to an X-of-Y DVN configuration (minimum 2-of-3) is now the required standard.

Cross-chain Invariants: Implementation of monitoring tools (like the Clojure-based monitor in this repo) to verify Total Supply vs. Locked Assets in real-time.

