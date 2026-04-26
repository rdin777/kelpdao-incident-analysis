// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "forge-std/Test.sol";

/**
 * @title KelpDAO Invariant PoC
 * @author rdin777
* @notice Simulation of the bridge solvency check (Solvency Check).
 */
contract InvariantTest is Test {
// Simulation of balances
    uint256 public constant TOTAL_DEPOSITED_L1 = 100_000 ether;
    uint256 public circulatingSupplyL2 = 100_000 ether;

    /**
* @dev INVARIANT: L1 assets (collateral) must be >= L2 supply.
* If this condition is violated, the system contains "phantom" money.
*/
    function check_SolvencyInvariant() public view {
// In a real contract, there would be a call to bridge.totalLocked() here
        uint256 lockedOnL1 = TOTAL_DEPOSITED_L1; 
        uint256 supplyOnL2 = circulatingSupplyL2;

        assertGe(lockedOnL1, supplyOnL2, "SOLVENCY_VIOLATION: L2 supply exceeded L1 backing");
    }

    /**
     * @dev Successful completion of the test with honest work
     */
    function test_NormalWithdrawal() public {
        uint256 withdrawAmount = 10 ether;
        
        // When withdrawing honestly, tokens are burned on L2
        circulatingSupplyL2 -= withdrawAmount;
        
        // The invariant remains intact (100k >= 99.99k)
        check_SolvencyInvariant();
    }

    /**
     * @dev Simulating the KelpDAO attack (Phantom Burn)
     * The hacker tricks the verifier by not burning tokens on L2, 
    * but by forcing the L1 bridge to release funds.
     */
    function test_Attack_PhantomBurn() public {
        uint256 hackAmount = 50_000 ether;

        // DVN hack: The bridge on L1 unlocks the funds, thinking they were lost on L2.
        // But in fact, the totalSupply on L2 did NOT decrease (the tokens remained with the hacker).
        
        // Simulating the situation AFTER the hacker's withdrawal:
        // There are 50k left on L1, but there are still 100k in circulation on L2.
        uint256 fakeLockedOnL1 = TOTAL_DEPOSITED_L1 - hackAmount;
        
        // This should cause the test to fail
        assertGe(fakeLockedOnL1, circulatingSupplyL2, "CRITICAL: Phantom assets detected!");
    }
}
