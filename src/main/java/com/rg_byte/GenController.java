package com.rg_byte;

import com.google.zxing.WriterException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GenController {

    private final GenModel model;
    private final GenView view;

    GenController(GenModel model, GenView view){
        this.model = model;
        this.view = view;

        view.addGenerateAction(e -> generateAction());

        view.addSaveAction(e->{
            String url = this.view.getURL();
            if(url.isEmpty()){
                JOptionPane.showMessageDialog(this.view, "Please type in a value!","Input Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }else {
                this.model.setURL(url);
            }

            File file;
            try {
                file = this.model.saveAction();
                if(file == null)
                    return;

                Desktop.getDesktop().open(new File(file.getAbsolutePath().replace(File.pathSeparatorChar + file.getName(), "")));
                Desktop.getDesktop().open(file);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this.view, "File Error Message: " + ex.getMessage(),"File Error", JOptionPane.ERROR_MESSAGE);
            } catch (WriterException ex) {
                JOptionPane.showMessageDialog(this.view, "Encoder Error Message: " + ex.getMessage(),"Encoder Error", JOptionPane.ERROR_MESSAGE);
            }


        });
    }

    private void generateAction(){
        String url = this.view.getURL();
        if(url.isEmpty()){
            JOptionPane.showMessageDialog(this.view, "Please type in a value!","Input Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }else {
            this.model.setURL(url);
        }

        try {
            this.model.generateAction();

        } catch (WriterException ex) {
            JOptionPane.showMessageDialog(this.view, "Error while encoding the QR-Code.\nStack : " + ex.getMessage(),"Encoding Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void showView() {
        this.view.showView();
    }
}
