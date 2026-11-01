// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

/// C3: Bank account of a customer - deposit money, withdraw money, show balance.
contract Bank {
    mapping(address => uint256) private balances;   // balance of each customer, in wei

    event Deposited(address indexed customer, uint256 amount);
    event Withdrawn(address indexed customer, uint256 amount);

    function deposit() public payable {
        require(msg.value > 0, "Deposit must be greater than 0");
        balances[msg.sender] += msg.value;
        emit Deposited(msg.sender, msg.value);
    }

    function withdraw(uint256 amount) public {
        require(amount > 0, "Amount must be greater than 0");        // NEW
        require(balances[msg.sender] >= amount, "Insufficient balance");
        balances[msg.sender] -= amount;               // update state before sending (no re-entrancy)
        (bool ok, ) = payable(msg.sender).call{value: amount}("");
        require(ok, "Transfer failed");
        emit Withdrawn(msg.sender, amount);
    }

    function getBalance() public view returns (uint256) {
        return balances[msg.sender];
    }
}