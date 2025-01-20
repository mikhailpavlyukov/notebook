package com.notebook;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class BackForm extends JFrame implements ActionListener {
    private ManagerForm manager;


    JTextField textField;
    JButton saveButton, clrButton, retButton, okButton;
    JTextArea textarea;
    JLabel labelFirst, labelSecond, labelTree;
    Border myborder;
    JDialog dialogW;
    String dir = "notingSaw";


    Font myFont = new Font("Ink Free", Font.BOLD, 30);

    BackForm(final ManagerForm manager) throws IOException {
        this.manager = manager;
        tetsting(manager);

    }

    void tetsting(final ManagerForm manager) throws IOException {
        this.manager = manager;
        setTitle("nootebook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 780);
        setLayout(null);

        labelFirst = new JLabel();
        labelFirst.setFont(myFont);
        labelFirst.setBounds(320, 15, 750, 25);
        labelFirst.setText("Name note ");

        textField = new JTextField();
        textField.setFont(myFont);
        textField.setBounds(25, 55, 750, 50);
        myborder = BorderFactory.createLineBorder(Color.WHITE);
        textField.setBorder(myborder);

        labelSecond = new JLabel();
        labelSecond.setFont(myFont);
        labelSecond.setBounds(320, 120, 750, 25);
        labelSecond.setText("Enter note ");

        textarea = new JTextArea();
        textarea.setFont(myFont);
        textarea.setBounds(25, 160, 750, 500);

        saveButton = new JButton("Save");
        saveButton.setBounds(25, 670, 100, 50);
        saveButton.setFont(myFont);
        saveButton.addActionListener(this);

        clrButton = new JButton("Clr");
        clrButton.setBounds(150, 670, 100, 50);
        clrButton.setFont(myFont);
        clrButton.addActionListener(this);

        retButton = new JButton("Back");
        retButton.setBounds(625, 670, 150, 50);
        retButton.setFont(myFont);
        retButton.addActionListener(this);

        add(textField);
        add(labelFirst);
        add(labelSecond);
        add(textarea);
        add(saveButton);
        add(clrButton);
        add(retButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            textField.getText();
            File file = new File(dir, textField.getText());
            Path path = Paths.get(String.valueOf(file));
            byte[] strToBytes = textarea.getText().getBytes();
            try {
                Files.write(path, strToBytes);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            labelTree = new JLabel();
            labelTree.setFont(myFont);
            labelTree.setText("saved");
            labelTree.setBounds(45, 1, 80, 50);

            okButton = new JButton("OK");
            okButton.addActionListener(this);

            okButton.setBounds(45, 45, 80, 50);
            okButton.setFont(myFont);

            dialogW = new JDialog(this, "Messege");
            dialogW.setSize(200, 150);
            dialogW.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            //dialogW.add(lebelTree);

            dialogW.add(labelTree);
            dialogW.add(okButton);
            dialogW.setLayout(null);
            dialogW.setVisible(true);

        }

        if (e.getSource() == clrButton) {
            // коли стетри
            textarea.setText(" ");

        }

        if (e.getSource() == retButton) {
            // кнопка повертання
            manager.toggleForms();
        }

        if (e.getSource() == okButton) {
            manager.toggleForms();
        }
    }

    void setSelectNote(String nameSelestNote) {
        textField.setText(nameSelestNote);

        try {
            File file = new File(dir, nameSelestNote);
            Path path = Paths.get(String.valueOf(file));
            String readNote = Files.readAllLines(path).get(0);
            textarea.setText(readNote);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
