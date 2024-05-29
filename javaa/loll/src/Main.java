// import bank.Bank;
// import gui.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        // Create and display the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame(bank);
                mainFrame.setVisible(true);
            }
        });
    }
}