package models.entities;

public class Account {
    private String username;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String role;

    public Account(String username, String name, String phone, String email, String password, String role) {
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    @Override
    public String toString() {
        return username + "|" + name + "|" + phone + "|" + email + "|" + password + "|" + role;
    }
}
