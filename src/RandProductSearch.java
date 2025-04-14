import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class RandProductSearch extends JFrame {
    private final JTextField searchField;
    private final JTextArea resultsArea;
    private static final String FILE_NAME = "products.dat";

    public RandProductSearch() {
        super("Search Products");

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        topPanel.add(new JLabel("Search Name:"));
        searchField = new JTextField(20);
        topPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        topPanel.add(searchButton);

        JButton quitButton = new JButton("Quit");
        topPanel.add(quitButton);

        add(topPanel, BorderLayout.NORTH);

        resultsArea = new JTextArea(15, 40);
        resultsArea.setEditable(false);
        add(new JScrollPane(resultsArea), BorderLayout.CENTER);

        searchButton.addActionListener(e -> searchProducts());
        quitButton.addActionListener(e -> System.exit(0));

        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void searchProducts() {
        String keyword = searchField.getText().trim().toLowerCase();
        resultsArea.setText("");

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a name to search.");
            return;
        }

        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            long fileLength = file.length();
            int recordSize = Product.getRecordSize();

            while (file.getFilePointer() < fileLength) {
                String name = readFixedString(file, 35).trim();
                String desc = readFixedString(file, 75).trim();
                String id = readFixedString(file, 6).trim();
                double cost = file.readDouble();

                if (name.toLowerCase().contains(keyword)) {
                    resultsArea.append("Name: " + name + "\n");
                    resultsArea.append("Description: " + desc + "\n");
                    resultsArea.append("ID: " + id + "\n");
                    resultsArea.append("Cost: $" + cost + "\n");
                    resultsArea.append("-------------------------------------\n");
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "File error: " + e.getMessage());
        }
    }

    private String readFixedString(RandomAccessFile file, int length) throws IOException {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(file.readChar());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandProductSearch::new);
    }
}
