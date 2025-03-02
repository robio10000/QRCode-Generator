package com.rg_byte;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Setup UIManager look and feel.
        FlatLightLaf.setup();

        // Init QRCode Application
        SwingUtilities.invokeLater(()->{
            // Build mvc components
            GenView genView = new GenView();
            GenModel genModel = new GenModel();
            GenController genController = new GenController(genModel, genView);

            // Set view as observer listener
            genModel.addPropertyChangeListener(genView);

            // Open the view
            genController.showView();

        });
    }
}