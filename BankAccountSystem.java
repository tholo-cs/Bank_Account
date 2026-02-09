import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class BankAccountSystem extends JFrame {

    private Map<String, Account> accounts;
    private JTabbedPane tabbedPane;
    private JTextField accountNumberField;
    private JTextField amountField;
    private JTextArea reportArea;
    private JButton depositButton;
    private JButton withdrawButton;

    public BankAccountSystem() {
        accounts = new HashMap<>();
        accounts.put("AA123", new Account("AA", "123", 1000.0));
        accounts.put("BB456", new Account("BB", "456", 2000.0));
        accounts.put("CC789", new Account("CC", "789", 3000.0));

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Menu", createMenuPanel());
        tabbedPane.addTab("Deposit/Withdraw", createDepositWithdrawPanel());
        tabbedPane.addTab("List of Accounts", createListOfAccountsPanel());

        add(tabbedPane);

        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton openAccountButton = new JButton("Open Account");
        openAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String initials = JOptionPane.showInputDialog("Enter account holder's initials:");
                String accountNumber = JOptionPane.showInputDialog("Enter account number:");
                double balance = Double.parseDouble(JOptionPane.showInputDialog("Enter initial balance:"));

                Account account = new Account(initials, accountNumber, balance);
                accounts.put(accountNumber, account);
                JOptionPane.showMessageDialog(null, "Account opened successfully!");
            }
        });

        JButton searchAccountButton = new JButton("Search Account");
        searchAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = JOptionPane.showInputDialog("Enter account number:");

                Account account = accounts.get(accountNumber);
                if (account != null) {
                    JOptionPane.showMessageDialog(null, account.toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Account not found!");
                }
            }
        });

        JButton listAccountsButton = new JButton("List of Accounts");
        listAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(2);
            }
        });

        panel.add(openAccountButton);
        panel.add(searchAccountButton);
        panel.add(listAccountsButton);

        return panel;
    }

    private JPanel createDepositWithdrawPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberField = new JTextField(10);

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);

        depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText();
                double amount = Double.parseDouble(amountField.getText());

                Account account = accounts.get(accountNumber);
                if (account != null) {
                    account.deposit(amount);
                    reportArea.setText("Deposit successful!\n" + account.toString());
                } else {
                    reportArea.setText("Account not found!");
                }
            }
        });

        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText();
                double amount = Double.parseDouble(amountField.getText());

                Account account = accounts.get(accountNumber);
                if (account != null) {
                    if (account.getBalance() >= amount) {
                        account.withdraw(amount);
                        reportArea.setText("Withdrawal successful!\n" + account.toString());
                    } else {
                        reportArea.setText("Insufficient balance!");
                    }
                } else {
                    reportArea.setText("Account not found!");
                }
            }
        });

        reportArea = new JTextArea(10, 20);

        panel.add(accountNumberLabel);
        panel.add(accountNumberField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(new JScrollPane(reportArea));

        return panel;
    }

    private JPanel createListOfAccountsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(20, 40);
        for (Account account : accounts.values()) {
            textArea.append(account.toString() + "\n");
        }

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        new BankAccountSystem();
    }
}

class Account {
private String initials;
private String accountNumber;
private double balance;

public Account(String initials, String accountNumber, double balance) {
    this.initials = initials;
    this.accountNumber = accountNumber;
    this.balance = balance;
}

public void deposit(double amount) {
    balance += amount;
}

public void withdraw(double amount) {
    balance -= amount;
}

public double getBalance() {
    return balance;
}

@Override
public String toString() {
    return "Initials: " + initials + "\nAccount Number: " + accountNumber + "\nBalance: " + balance;
}
}