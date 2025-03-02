package com.rg_byte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GenView extends JFrame implements PropertyChangeListener {
    private JPanel panel1;
    private JButton genBtn;
    private JButton saveBtn;
    private JPanel qrHolderPnl;
    private JTextPane dataTxt;

    private final QRCodePanel qrPnl;

    /**
     * Init the QRCode Generator view.
     */
    public GenView(){
        setTitle("QR-Code Generator");
        setSize(400,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.qrPnl = new QRCodePanel();
        this.qrHolderPnl.add(qrPnl);

        this.add(panel1);

        dataTxt.setBackground(new Color(247,248,250));


    }

    /**
     * Open the view.
     */
    public void showView(){
        SwingUtilities.invokeLater(()->setVisible(true));
    }

    /**
     * Get the input data of the view.
     * @return user input.
     */
    public String getURL(){
        return dataTxt.getText();
    }

    /**
     * Add a listener to the generate button.
     * @param listener The listener.
     */
    public void addGenerateAction(ActionListener listener){
        genBtn.addActionListener(listener);
    }

    /**
     * Add a listener to the save button.
     * @param listener The listener.
     */
    public void addSaveAction(ActionListener listener){
        saveBtn.addActionListener(listener);
    }

    

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if("qrcode".equals(evt.getPropertyName())){
            BufferedImage image = (BufferedImage) evt.getNewValue();
            qrPnl.setImage(image);
        }
    }
}
