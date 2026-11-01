// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.0;

contract StudentData {
    // Structure
    struct Student {
        string name;
        uint256 rollno;
    }

    // Array
    Student[] public studentarr;

    function addStudent(string memory name, uint256 rollno) public {
        for (uint i = 0; i < studentarr.length; i++) {
            if (studentarr[i].rollno == rollno) {
                revert("Student with this roll no already exists");
            }
        }

        studentarr.push(Student(name, rollno));
    }

    function getStudentsLength() public view returns (uint) {
        return studentarr.length;
    }

    function displayAllStudents() public view returns (Student[] memory) {
        return studentarr;
    }

    function getStudentByIndex(uint idx) public view returns (Student memory) {
        require(idx < studentarr.length, "index out of bound!");
        return studentarr[idx];
    }

    // Fallback
    fallback() external payable {
        // Handles calls that do not match a function in this contract.
    }

    // Receive
    receive() external payable {
        // Handles Ether sent without calldata.
    }
}