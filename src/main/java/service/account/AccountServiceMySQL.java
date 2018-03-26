package service.account;

import com.sun.org.apache.xpath.internal.operations.Bool;
import model.Account;
import model.SavingsAccount;
import model.builder.AccountBuilder;
import model.builder.SavingsAccountBuilder;
import model.validation.AccountValidator;
import model.validation.Notification;
import model.validation.SavingsAccountValidator;
import repository.account.AccountRepository;

import java.util.List;

public class AccountServiceMySQL implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceMySQL(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public String getAccountData() {
        List<Account> accountList = accountRepository.findAll();
        String data = "";
        for(Account account: accountList){
            data+= account.toString() + " " + accountRepository.getClientId(account.getId()) +"\n";
        }

        return data;
    }

    @Override
    public Notification<Boolean> insertAccount(int amount, long clientID, boolean saving, double interest) {
        if(saving){
            SavingsAccount savingsAccount =(SavingsAccount) new SavingsAccountBuilder()
                    .setInterest(interest)
                    .setAmount(amount)
                    .build();

            SavingsAccountValidator savingsAccountValidator = new SavingsAccountValidator(savingsAccount);
            boolean accountValid = savingsAccountValidator.validate();
            Notification<Boolean> notification = new Notification<>();

            if(!accountValid){
                savingsAccountValidator.getErrors().forEach(notification::addError);
                notification.setResult(Boolean.FALSE);
                return notification;
            } else {
                notification.setResult(accountRepository.saveAccount(savingsAccount, clientID, accountRepository.getTypeId("Saving")));
                return notification;
            }
        }
        else {
            Account account = new AccountBuilder()
                    .setAmount(amount)
                    .build();

            AccountValidator accountValidator = new AccountValidator(account);
            boolean accountValid = accountValidator.validate();
            Notification<Boolean> notification = new Notification<>();

            if(!accountValid){
                accountValidator.getErrors().forEach(notification::addError);
                notification.setResult(Boolean.FALSE);
                return notification;
            } else {
                notification.setResult(accountRepository.saveAccount(account, clientID, accountRepository.getTypeId("Spending")));
                return notification;
            }
        }
    }

    @Override
    public Notification<Boolean> updateAccount(long id, int amount, long clientID, boolean saving, double interest) {
        if(saving){
            SavingsAccount savingsAccount =(SavingsAccount) new SavingsAccountBuilder()
                    .setInterest(interest)
                    .setID(id)
                    .setAmount(amount)
                    .build();

            SavingsAccountValidator savingsAccountValidator = new SavingsAccountValidator(savingsAccount);
            boolean accountValid = savingsAccountValidator.validate();
            Notification<Boolean> notification = new Notification<>();

            if(!accountValid){
                savingsAccountValidator.getErrors().forEach(notification::addError);
                notification.setResult(Boolean.FALSE);
                return notification;
            } else {
                notification.setResult(accountRepository.updateAccount(savingsAccount, clientID, accountRepository.getTypeId("Saving")));
                return notification;
            }
        }
        else {
            Account account = new AccountBuilder()
                    .setID(id)
                    .setAmount(amount)
                    .build();

            AccountValidator accountValidator = new AccountValidator(account);
            boolean accountValid = accountValidator.validate();
            Notification<Boolean> notification = new Notification<>();

            if(!accountValid){
                accountValidator.getErrors().forEach(notification::addError);
                notification.setResult(Boolean.FALSE);
                return notification;
            } else {
                notification.setResult(accountRepository.updateAccount(account, clientID, accountRepository.getTypeId("Spending")));
                return notification;
            }
        }
    }

    @Override
    public Notification<Boolean> delete(long id) {
        Account account = new AccountBuilder()
                .setID(id)
                .build();

        Notification<Boolean> notification = new Notification<>();
        notification.setResult(accountRepository.delete(account));
        return notification;
    }
}
