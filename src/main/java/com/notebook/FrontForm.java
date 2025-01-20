package com.notebook;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

public class FrontForm extends JFrame implements ActionListener, ListSelectionListener {
    private ManagerForm manager;

     JButton editButton, delButton, bacButton;
    Font myFont = new Font("Ink Free", Font.BOLD, 30);
    JList list;
    DefaultListModel model = new DefaultListModel();
    String directory = "notingSaw";

    FrontForm(final ManagerForm manager) {
        this.manager = manager;
        tempMetod(manager);

    }

    void tempMetod(ManagerForm manager) {
        this.manager = manager;

        setTitle("nootebook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 780);
        setLayout(null);

        updataNot();

        //set Jlist
        list = new JList(model);
        list.setBounds(25, 50, 750, 600);
        list.setFont(myFont);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener((ListSelectionListener) this);

        editButton = new JButton("Edit");
        editButton.setBounds(25, 670, 130, 50);
        editButton.setFont(myFont);
        editButton.addActionListener(this);
        editButton.setEnabled(false);

        delButton = new JButton("Del");
        delButton.setBounds(180, 670, 100, 50);
        delButton.setFont(myFont);
        delButton.addActionListener(this);

        bacButton = new JButton("Back");
        bacButton.setBounds(625, 670, 150, 50);
        bacButton.setFont(myFont);
        bacButton.addActionListener(this);

        add(list);
        add(editButton);
        add(bacButton);
        add(delButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editButton) {
            manager.setSelectNote((String)list.getSelectedValue());

            manager.toggleForms();
            updataNot();
        }

        if (e.getSource() == delButton) {

            try {
                String nameFile=(String)list.getSelectedValue();
                File file= new File( directory ,nameFile);
                Path path= Paths.get (String.valueOf(file));
                Files.delete(path);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            updataNot();

        }
        if (e.getSource() == bacButton) {
            // кнопка повертання
            manager.toggleForms();
            updataNot();

        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        //System.out.println( (String) list.getSelectedValue());
        editButton.setEnabled(true);
    }

    ArrayList<String> updataNot() {

        ArrayList<String> data = new ArrayList<>();

        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(Paths.get(directory))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    data.add(path.getFileName().toString());
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        if (data.isEmpty()) {
            //правда якщо пустий
            model.add(0, "There is nothing here yet... ");
        } else {
            // фолз якщо нє
            model.removeAllElements();
            for (int i = 0; i < data.size(); i++) {
                model.add(i, data.get(i));
            }
        }
        return data;
    }




}