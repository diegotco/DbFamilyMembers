package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FamilyInfoApp extends JFrame {
    private JTextField nameField, lastNameField, ageField, relationshipField;

    public FamilyInfoApp() {
        super("Family Information App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);

        createUI();

        setVisible(true);
    }

    private void createUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        panel.add(lastNameField);

        panel.add(new JLabel("Age:"));
        ageField = new JTextField();
        panel.add(ageField);

        panel.add(new JLabel("Relationship:"));
        relationshipField = new JTextField();
        panel.add(relationshipField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFamilyMember();
            }
        });
        panel.add(saveButton);

        add(panel);
    }

    private void saveFamilyMember() {
        String name = nameField.getText();
        String lastName = lastNameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String relationship = relationshipField.getText();

        try {
            // Replace "jdbc:postgresql://localhost:5432/your_database" with your actual database URL
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/myfamily", "postgres", "");

            String sql = "INSERT INTO myfamily (name, last_name, age, relationship) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.setString(4, relationship);

            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Family member saved successfully!");
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving family member. Please try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FamilyInfoApp());
    }
}
