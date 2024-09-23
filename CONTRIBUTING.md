// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract LendingBorrowing {
    struct Loan {
        address borrower;
        uint amount;
        bool isActive;
    }

    mapping(address => uint) public balances;
    mapping(uint => Loan) public loans;
    uint public loanCount;

    event Deposited(address indexed user, uint amount);
    event LoanRequested(uint indexed loanId, address indexed borrower, uint amount);
    event LoanPaid(uint indexed loanId);

    // Deposit funds into the contract
    function deposit() public payable {
        require(msg.value > 0, "Deposit amount must be greater than 0");
        balances[msg.sender] += msg.value;
        emit Deposited(msg.sender, msg.value);
    }

    // Request a loan
    function requestLoan(uint amount) public {
        require(balances[msg.sender] >= amount, "Insufficient balance");
        loans[loanCount] = Loan(msg.sender, amount, true);
        loanCount++;
        emit LoanRequested(loanCount - 1, msg.sender, amount);
    }

    // Pay back the loan
    function payLoan(uint loanId) public {
        Loan storage loan = loans[loanId];
        require(loan.isActive, "Loan is not active");
        require(msg.sender == loan.borrower, "Only borrower can pay back the loan");

        balances[msg.sender] -= loan.amount;
        loan.isActive = false;
        emit LoanPaid(loanId);
    }

    // Withdraw funds
    function withdraw(uint amount) public {
        require(balances[msg.sender] >= amount, "Insufficient balance");
        balances[msg.sender] -= amount;
        payable(msg.sender).transfer(amount);
    }
}
