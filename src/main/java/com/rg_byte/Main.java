package com.rg_byte;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        FlatLightLaf.setup();

        SwingUtilities.invokeLater(()->{
            GenView genView = new GenView();
            GenModel genModel = new GenModel();
            GenController genController = new GenController(genModel, genView);

            genModel.addPropertyChangeListener(genView);
            genController.showView();

        });
    }
}