package controllers;

import data.FileHandler;
import models.entities.Account;
import models.datastructures.MyLinkedList;


public class AuthController {
    private MyLinkedList<Account> accounts;
    private FileHandler fileHandler;
    private Account currentUser;

    public AuthController(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.accounts = fileHandler.loadAccounts();
        if (accounts.isEmpty() || !isUsernameTaken("admin")) {
            Account defaultAdmin = new Account("admin", "System Administrator", "0999999999", "admin@netflix.com", "admin123", "ADMIN");
            accounts.add(defaultAdmin);
            fileHandler.saveAccounts(accounts);
        }
    }

    public boolean isUsernameTaken(String username) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean registerUser(String username, String name, String phone, String email, String password) {
        if (isUsernameTaken(username)) {
            return false;
        }
        Account newUser = new Account(username, name, phone, email, password, "USER");
        accounts.add(newUser);
        fileHandler.saveAccounts(accounts);
        return true;
    }

    public Account login(String username, String password) {
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getUsername().equals(username) && acc.getPassword().equals(password)) {
                currentUser = acc;
                return acc;
            }
        }
        return null;
    }

    public Account getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }
}