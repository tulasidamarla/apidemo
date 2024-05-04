package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class BankAccount {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private static int sequence_num = 123;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private double balance;

    private double avgQuarterlyBalance;
    private String ifscCode;


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAvgQuarterlyBalance() {
        return avgQuarterlyBalance;
    }

    public void setAvgQuarterlyBalance(double avgQuarterlyBalance) {
        this.avgQuarterlyBalance = avgQuarterlyBalance;
    }

    // transfer, update details, debit, credit
    public void transfer(Long accountId, Long amount){
        final var balance = this.getBalance() - amount;
        this.setBalance(balance);
        bankAccountRepository.save(this);
    }

    public void calculateInterest(){
        //write logic to calculate interest
        final var avgBalance  = this.getAvgQuarterlyBalance();
        final var interestAmount = (avgBalance * 8/100)/4;
        final var newBalance = interestAmount + this.getBalance();
        this.setBalance(newBalance);
        bankAccountRepository.save(this);
    }

}
