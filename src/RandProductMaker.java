import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductMaker extends JFrame {
    private final JTextField nameField;
    private final JTextField descField;
    private final JTextField idField;
    private final JTextField costField;
    private final JTextField countField;
    private int recordCount = 0;
    private static final String FILE_NAME = "products.dat";

    public RandProductMaker() {
        super("Product Entry");

        setLayout(new GridLayout(7, 2));

        add(new JLabel("Name:"));
        nameField = new JTextField(35);
        add(nameField);

        add(new JLabel("Description:"));
        descField = new JTextField(75);
        add(descField);

        add(new JLabel("ID:"));
        idField = new JTextField(6);
        add(idField);

        add(new JLabel("Cost:"));
        costField = new JTextField();
        add(costField);

        JButton addButton = new JButton("Add");
        add(addButton);

        JButton quitButton = new JButton("Quit");
        add(quitButton);

        add(new JLabel("Records Entered:"));
        countField = new JTextField("0");
        countField.setEditable(false);
        add(countField);

        addButton.addActionListener(e -> addProduct());
        quitButton.addActionListener(e -> System.exit(0));

        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addProduct() {
        try {
            String name = nameField.getText().trim();
            String desc = descField.getText().trim();
            String id = idField.getText().trim();
            double cost = Double.parseDouble(costField.getText().trim());

            if (name.isEmpty() || desc.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.");
                return;
            }

            Product p = new Product(name, desc, id, cost);
            try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
                file.seek(file.length()); // Append mode
                p.writeToFile(file);
            }

            recordCount++;
            countField.setText(String.valueOf(recordCount));
            nameField.setText("");
            descField.setText("");
            idField.setText("");
            costField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cost must be a valid number.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "File error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandProductMaker::new);
    }
}
