"""C3 / C4: compile the Solidity contracts and run them on a local test blockchain (eth-tester),
printing each transaction's gas used and fee. Usage: python C_run_contracts.py C3|C4"""
import sys

import solcx
from eth_tester.exceptions import TransactionFailed
from web3 import Web3
from web3.exceptions import ContractLogicError

SOLC = "0.8.24"
solcx.install_solc(SOLC)
w3 = Web3(Web3.EthereumTesterProvider())
acct, other = w3.eth.accounts[0], w3.eth.accounts[1]
eth = lambda wei: f"{Web3.from_wei(wei, 'ether'):.6f} ETH"


def compile_contract(path, name):
    out = solcx.compile_files([path], output_values=["abi", "bin"], solc_version=SOLC)
    c = out[f"{path}:{name}"]
    return w3.eth.contract(abi=c["abi"], bytecode=c["bin"])


def receipt_line(label, tx_hash):
    r = w3.eth.wait_for_transaction_receipt(tx_hash)
    fee = r.gasUsed * r.effectiveGasPrice
    print(f"  {label:<38} status={r.status} gasUsed={r.gasUsed:>7,} "
          f"gasPrice={r.effectiveGasPrice / 1e9:.3f} gwei fee={eth(fee)}")
    return r


def expect_revert(label, fn):
    try:
        fn()
        print(f"  {label:<38} (no revert)")
    except (ContractLogicError, TransactionFailed) as e:
        print(f"  {label:<38} REVERTED: {str(e).split('execution reverted: ')[-1].strip(chr(39))}")


def run_bank():
    print(f"Compiling C3_Bank.sol with solc {SOLC}; local chain id {w3.eth.chain_id}")
    Bank = compile_contract("C3_Bank.sol", "Bank")
    r = receipt_line("Deploy Bank", Bank.constructor().transact({"from": acct}))
    bank = w3.eth.contract(address=r.contractAddress, abi=Bank.abi)
    print(f"  Contract address: {r.contractAddress}\n")

    receipt_line("deposit() 0.01 ETH", bank.functions.deposit().transact({"from": acct, "value": Web3.to_wei(0.01, "ether")}))
    print(f"  getBalance() = {bank.functions.getBalance().call({'from': acct})} wei")
    receipt_line("withdraw(0.004 ETH)", bank.functions.withdraw(Web3.to_wei(0.004, "ether")).transact({"from": acct}))
    print(f"  getBalance() = {bank.functions.getBalance().call({'from': acct})} wei (0.006 ETH)")
    expect_revert("withdraw(1 ETH)", lambda: bank.functions.withdraw(Web3.to_wei(1, "ether")).transact({"from": acct}))
    expect_revert("deposit() 0 ETH", lambda: bank.functions.deposit().transact({"from": acct, "value": 0}))
    print(f"  getBalance() from another account = {bank.functions.getBalance().call({'from': other})} wei")
    print(f"  Contract holds {eth(w3.eth.get_balance(bank.address))}")


def run_students():
    print(f"Compiling C4_StudentData.sol with solc {SOLC}; local chain id {w3.eth.chain_id}")
    C = compile_contract("C4_StudentData.sol", "StudentData")
    r = receipt_line("Deploy StudentData", C.constructor().transact({"from": acct}))
    sd = w3.eth.contract(address=r.contractAddress, abi=C.abi)
    print(f"  Contract address: {r.contractAddress}\n")

    for args in [(1, "Asha", "Computer", 85), (2, "Rohan", "Computer", 78), (3, "Meera", "IT", 91)]:
        receipt_line(f"addStudent{args}", sd.functions.addStudent(*args).transact({"from": acct}))
    print(f"  getStudentCount() = {sd.functions.getStudentCount().call()}  (view call: no transaction, no fee)")
    for i in range(3):
        print(f"  getStudent({i}) = {sd.functions.getStudent(i).call()}")
    expect_revert("getStudent(5)", lambda: sd.functions.getStudent(5).call())
    expect_revert("addStudent(4, 'X', 'IT', 150)", lambda: sd.functions.addStudent(4, "X", "IT", 150).transact({"from": acct}))

    r = receipt_line("Send 0.001 ETH, empty calldata",
                     w3.eth.send_transaction({"from": acct, "to": sd.address, "value": Web3.to_wei(0.001, "ether")}))
    print(f"     event: {sd.events.EtherReceived().process_receipt(r)[0]['args']['via']}() ran")
    r = receipt_line("Send 0.001 ETH, unknown calldata 0x1234",
                     w3.eth.send_transaction({"from": acct, "to": sd.address, "value": Web3.to_wei(0.001, "ether"), "data": "0x1234"}))
    print(f"     event: {sd.events.EtherReceived().process_receipt(r)[0]['args']['via']}() ran")
    print(f"  Contract balance = {eth(w3.eth.get_balance(sd.address))}")


if __name__ == "__main__":
    {"C3": run_bank, "C4": run_students}[sys.argv[1] if len(sys.argv) > 1 else "C3"]()
    
    
"""
pip install web3 eth-tester py-evm py-solc-x
python C_run_contracts.py C3
python C_run_contracts.py C4
"""