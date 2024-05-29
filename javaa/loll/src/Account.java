public class Account {
    private String accountNumber;
    private float balance;
    private Customer customer;

    public Account(String accountNumber, Customer customer) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = 0.0f;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public float getBalance() {
        return balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void deposit(float amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(float amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: " + amount);
        } else {
            System.out.println("Invalid or insufficient funds.");
        }
    }
}
