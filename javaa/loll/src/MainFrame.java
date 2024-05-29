import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

public class MainFrame extends JFrame {
    private Bank bank;

    // GUI Components
    private JTextField nameField;
    private JTextField customerIdField;
    private JTextField accountNumberField;
    private JFormattedTextField amountField;
    private JTextArea outputArea;

    public MainFrame(Bank bank) {
        this.bank = bank;
        initComponents();
    }

    private void initComponents() {
        setTitle("Bank Account Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Customizing Appearance
        Font font = new Font("Arial", Font.PLAIN, 14);
        Color bgColor = new Color(255, 255, 204); // Light yellow background

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.setBackground(bgColor);

        JLabel nameLabel = new JLabel("Customer Name:");
        nameLabel.setFont(font);
        inputPanel.add(nameLabel);
        nameField = new JTextField();
        inputPanel.add(nameField);

        JLabel customerIdLabel = new JLabel("Customer ID:");
        customerIdLabel.setFont(font);
        inputPanel.add(customerIdLabel);
        customerIdField = new JTextField();
        inputPanel.add(customerIdField);

        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setFont(font);
        inputPanel.add(accountNumberLabel);
        accountNumberField = new JTextField();
        inputPanel.add(accountNumberField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(font);
        inputPanel.add(amountLabel);

        // Set up the formatted text field for the amount
        NumberFormat floatFormat = NumberFormat.getNumberInstance();
        NumberFormatter numberFormatter = new NumberFormatter(floatFormat);
        numberFormatter.setValueClass(Float.class);
        numberFormatter.setAllowsInvalid(false); // Only allow valid float inputs
        amountField = new JFormattedTextField(numberFormatter);
        amountField.setFont(font);
        inputPanel.add(amountField);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setFont(font);
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });
        inputPanel.add(createAccountButton);

        JButton depositButton = new JButton("Deposit");
        depositButton.setFont(font);
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });
        inputPanel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(font);
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });
        inputPanel.add(withdrawButton);

        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.setFont(font);
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
        inputPanel.add(checkBalanceButton);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
    }

    private void createAccount() {
        String name = nameField.getText();
        String customerId = customerIdField.getText();
        String accountNumber = accountNumberField.getText();

        if (name.isEmpty() || customerId.isEmpty() || accountNumber.isEmpty()) {
            outputArea.append("Error: All fields must be filled to create an account.\n");
            return;
        }

        Customer customer = new Customer(name, customerId);
        Account account = new Account(accountNumber, customer);
        bank.addAccount(account);

        outputArea.append("Account created for " + name + " with Account Number: " + accountNumber + "\n");
    }

    private void deposit() {
        String accountNumber = accountNumberField.getText();
        float amount;

        try {
            amount = ((Number) amountField.getValue()).floatValue();
        } catch (Exception e) {
            outputArea.append("Error: Invalid amount. Please enter a valid float number.\n");
            return;
        }

        if (accountNumber.isEmpty()) {
            outputArea.append("Error: Account number must be provided to deposit funds.\n");
            return;
        }

        bank.deposit(accountNumber, amount);
        outputArea.append("Deposited " + amount + " to Account Number: " + accountNumber + "\n");
        playSound("C:\\Users\\Loay khaled\\Desktop\\javaa\\loll\\src\\sound\\deposit.wav");
    }

    private void withdraw() {
        String accountNumber = accountNumberField.getText();
        float amount;
    
        try {
            amount = ((Number) amountField.getValue()).floatValue();
        } catch (Exception e) {
            outputArea.append("Error: Invalid amount. Please enter a valid float number.\n");
            return;
        }
    
        if (accountNumber.isEmpty()) {
            outputArea.append("Error: Account number must be provided to withdraw funds.\n");
            return;
        }
    
        Account account = bank.getAccount(accountNumber);
        if (account != null) {
            if (account.getBalance() >= amount) {
                account.withdraw(amount);
                outputArea.append("Withdrew " + amount + " from Account Number: " + accountNumber + "\n");
                playSound("C:\\Users\\Loay khaled\\Desktop\\javaa\\loll\\src\\sound\\withdraw.wav");
            } else {
                outputArea.append("Insufficient funds for withdrawal.\n");
            }
        } else {
            outputArea.append("Error: Account not found.\n");
        }
    }
    

    private void checkBalance() {
        String accountNumber = accountNumberField.getText();

        if (accountNumber.isEmpty()) {
            outputArea.append("Error: Account number must be provided to check balance.\n");
            return;
        }

        Account account = bank.getAccount(accountNumber);
        if (account != null) {
            float balance = account.getBalance();
            outputArea.append("Balance for Account Number " + accountNumber + ": " + balance + "\n");
        } else {
            outputArea.append("Error: Account not found.\n");
        }
    }

    private void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}