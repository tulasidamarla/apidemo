package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class BankController {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @GetMapping
    public String getAccounts() {
        final var users = bankAccountRepository.findAll();
        StringBuilder response = new StringBuilder("<html><body>" +
                "<table border=1px><th><tr><td>name</td><td>email</td></tr></th>");
        for(BankAccount user: users){
            response.append("<tr><td>");
            response.append(user.getName());
            response.append("</td><td>");
            response.append(user.getEmail());
            response.append("</td></tr>");
        }
        return response.toString();
    }

    @GetMapping("{id}")
    public BankAccount getBankAccountById(@PathVariable Long id) {
        return bankAccountRepository.findById(id).get();
    }

    @PostMapping
    public BankAccount createBankAccount(@RequestBody BankAccount bankAccount) {
        System.out.println(bankAccount.getEmail());
        return bankAccountRepository.save(bankAccount);
    }

    @PostMapping("/transfer/{accountId}/amount/{amount}")
    public String transferBankAccount(@PathVariable Long accountId, @PathVariable Long amount) {
        final BankAccount bankAccount = bankAccountRepository.getReferenceById(accountId);
        System.out.println("Email of the user " + bankAccount.getEmail());
        System.out.println("Current account balance is " + bankAccount.getBalance());
        if(bankAccount.getBalance() < amount){
            return "Insufficient balance. Current balance is " + bankAccount.getBalance();
        }
        bankAccount.transfer(accountId, amount);
        return "successfully transferred";
    }

    @PutMapping()
    public BankAccount updateBankAccount(@RequestBody BankAccount bankAccount) {
        System.out.println(bankAccount.getEmail());
        return bankAccountRepository.save(bankAccount);
    }

    @PostMapping("/updateInterest/{accountId}")
    public String calculateQuarterlyInterest(@PathVariable Long accountId){
        final BankAccount bankAccount = bankAccountRepository.getReferenceById(accountId);
        bankAccount.calculateInterest();
        return "successfully calculated interest";
    }



    // Other CRUD endpoints (GET /api/users/{id}, PUT, DELETE, etc.) can be added here
}
