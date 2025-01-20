package com.notebook;

import javax.swing.*;
import java.io.IOException;


public class Main implements ManagerForm {

    private FrontForm listForm;
    private BackForm noteForm;

    public String selectNote;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(( new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        }));
    }
    public Main() {
        listForm = new FrontForm( this );
        try {
            noteForm = new BackForm(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listForm.setVisible(true);
    }

    @Override
    public void toggleForms(){
        listForm.setVisible(!listForm.isVisible());
        noteForm.setVisible(!noteForm.isVisible());

        if(selectNote!= null) {
            noteForm.setSelectNote(selectNote);
        }
    }

    @Override
    public void setSelectNote(String s){
        selectNote=s;
    }
}



