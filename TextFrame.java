import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextFrame extends JFrame {
    private JTextArea textArea;
    private JTextField textField;
    private JButton submitButton;

    // Constructor
    public TextFrame() {
        // Set up the JFrame
        setTitle("Text Display Frame");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize JTextArea
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize text field and button
        textField = new JTextField();
        submitButton = new JButton("Submit");

        // Panel for input components
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(textField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // Button action listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = getInputText();
                if (!inputText.isEmpty()) {
                    textField.setText(""); // Clear the input field after submission
                }
            }
        });

        // Make the frame visible
        setVisible(true);
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
