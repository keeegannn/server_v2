import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextFrame extends JFrame {
    private JTextArea textArea;
    private JTextField textField;
    private JButton submitButton;
    private JTextField usernameField;
    private JButton usernameSubmitButton;
    private JPanel mainPanel;
    private JPanel usernamePanel;
    private String username;

    // Constructor
    public TextFrame() {
        // Set up the JFrame
        setTitle("Text Display Frame");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        // Initialize panels
        mainPanel = createMainPanel();
        usernamePanel = createUsernamePanel();

        // Add panels to the frame
        add(usernamePanel, "UsernamePanel");
        add(mainPanel, "MainPanel");

        // Show the username input screen initially
        showUsernameInput();

        // Make the frame visible
        setVisible(true);
    }

    // Method to create the username input panel
    private JPanel createUsernamePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel usernameLabel = new JLabel("Enter Username:");
        usernameField = new JTextField();
        usernameSubmitButton = new JButton("Submit");

        // Action listener for the username submission
        usernameSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUsername = usernameField.getText().trim();
                if (!inputUsername.isEmpty()) {
                    username = inputUsername;
                    showMainPanel();
                }
            }
        });

        panel.add(usernameLabel, BorderLayout.NORTH);
        panel.add(usernameField, BorderLayout.CENTER);
        panel.add(usernameSubmitButton, BorderLayout.SOUTH);

        return panel;
    }

    // Method to create the main text display panel
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Initialize JTextArea
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Initialize text field and submit button
        textField = new JTextField();
        submitButton = new JButton("Submit");

        // Panel for input components
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(textField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);

        // Action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = getInputText();
                if (!inputText.isEmpty()) {
                    appendText(username + ": " + inputText);
                    textField.setText(""); // Clear the input field after submission
                }
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    // Method to show the username input panel
    private void showUsernameInput() {
        CardLayout layout = (CardLayout) getContentPane().getLayout();
        layout.show(getContentPane(), "UsernamePanel");
    }

    // Method to show the main panel
    private void showMainPanel() {
        CardLayout layout = (CardLayout) getContentPane().getLayout();
        layout.show(getContentPane(), "MainPanel");
        appendText("Welcome, " + username + "!");
    }

    // Method to update the text area
    public void appendText(String text) {
        textArea.append(text + "\n");
    }

    // Method to get text from the input field
    public String getInputText() {
        return textField.getText();
    }

    // Main method to test the class
    public static void main(String[] args) {
        new TextFrame();
    }
}
