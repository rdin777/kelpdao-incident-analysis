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
