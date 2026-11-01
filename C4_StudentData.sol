// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

/// C4: Student data using structures, arrays and the receive/fallback functions.
contract StudentData {
    struct Student {
        uint256 rollNo;
        string name;
        string department;
        uint8 marks;
    }

    Student[] public students;                        // dynamic array of structures
    address public owner;

    event StudentAdded(uint256 rollNo, string name);
    event EtherReceived(address from, uint256 amount, string via);

    constructor() {
        owner = msg.sender;
    }

    function addStudent(uint256 rollNo, string calldata name, string calldata department, uint8 marks) external {
        require(msg.sender == owner, "Only owner can add students");   // NEW
        require(marks <= 100, "Marks must be 0-100");
        students.push(Student(rollNo, name, department, marks));
        emit StudentAdded(rollNo, name);
    }

    function getStudent(uint256 index) external view returns (uint256, string memory, string memory, uint8) {
        require(index < students.length, "Invalid index");
        Student storage s = students[index];
        return (s.rollNo, s.name, s.department, s.marks);
    }
 
    function getStudentCount() external view returns (uint256) {
        return students.length;
    }

    receive() external payable {                      // plain Ether transfer, empty calldata
        emit EtherReceived(msg.sender, msg.value, "receive");
    }

    fallback() external payable {                     // calldata matches no function
        emit EtherReceived(msg.sender, msg.value, "fallback");
    }
}