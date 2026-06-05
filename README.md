Markdown
# Analysis of KelpDAO Infrastructure Exploit ($292M)

*If this research helped you, please consider giving it a ⭐ Star.*


## 🚀 Stay Updated
Found this research useful?
* **Star ⭐** this repo to keep track of it.
* **Follow me** on GitHub for more DeFi security research.
* **Fork** it if you want to run your own experiments.

### ☕ Support the Research
If you appreciate the work and want to support further security research:

<img src="456.PNG" alt="Donate QR" width="200"/>

**Wallet Address (ETH/EVM):** 0xBDDD7973D0DE27B715A4A5cbdb87d0DF78757b3A 


## Overview
This repository contains a detailed technical analysis of the largest DeFi incident of the first half of 2026—the KelpDAO hack. The research focuses on vulnerabilities in the LayerZero v2 transport layer infrastructure and methods for mitigating similar attacks through invariant monitoring.

## Repository Structure
* **`incident-analysis.md`**: In-depth analysis of Root Cause, attack mechanics on RPC nodes, and DVN manipulation.
* **`monitoring/`**: A Clojure-based toolkit for independent bridge solvency verification (Cross-chain Solvency Watcher).
* **`proof-of-concept/`**: Solidity tests (Foundry) to demonstrate automated invariant checking.
* **`diagrams/`**: Visualization of attack vectors and comparison of 1-of-1 vs. Multi-DVN security models.

## Key Technical Findings
1. **Protocol Dependency:** Protocols using LayerZero v2 inherit the risks of the default DVN configuration. The 1-of-1 configuration is a single point of failure.
2. **Infrastructure Isolation:** The attack was implemented via an Eclipse attack on the verifier's RPC providers, which allowed for confirmation of the "phantom" token burn.
3. **Missing Guardrails:** The lack of cross-chain invariant checking at the smart contract level allowed for the execution of a malicious message.

## Remediation Concepts
The repository proposes two levels of protection:
* **On-chain:** Implementing `totalLocked >= totalSupply` checks before executing output.
* **Off-chain:** Using functional languages ​​(Clojure) to create highly reliable systems for real-time node health monitoring.

  ## Current Status
  Contextual Relevance: Following the $300 million exploit on April 18, Kelp DAO officially initiated the migration of rsETH from LayerZero to Chainlink CCIP to improve the security of cross-chain transactions.

The Sentinel Advantage: This event confirms my research thesis: standard protocols are not enough. My Sentinel Agent complements solutions like CCIP by providing invariant monitoring at the application logic level, not just the transport layer.

