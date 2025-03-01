package com.rg_byte;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;

public class GenModel {
    private String url;
    private BufferedImage qrCodeImage;
    private int size;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);


    public void setQRCode(BufferedImage qrCodeImage){
        BufferedImage old = this.qrCodeImage;
        this.qrCodeImage = qrCodeImage;
        support.firePropertyChange("qrcode", old, qrCodeImage);

    }

    void setSize(int size){
        this.size = size;
    }
    public int getSize() {
        return this.size;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getURL() {
        return this.url;
    }

    public void generateAction() throws WriterException {
        setSize(1500);
        BitMatrix bitMatrix = generateQRCodeMatrix();
        BufferedImage image = GenModel.convertMatrixToImage(bitMatrix);
        setQRCode(image);
    }

    public File saveAction() throws IOException, WriterException {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int retVal = chooser.showOpenDialog(null);
        if(retVal != JFileChooser.APPROVE_OPTION){
            return null;
        }
        File file = chooser.getSelectedFile();

        int cSize;
        try{
            cSize  = Integer.parseInt(JOptionPane.showInputDialog(null, "Size: ", "1500"));

        }catch (NumberFormatException e){
            cSize = 1500;
        }

        return generateQRCodeToFile(file.getAbsolutePath() + "/qrcode_" + System.currentTimeMillis() + ".png", cSize);

    }

    protected File generateQRCodeToFile(String path, int size) throws IllegalArgumentException, WriterException, IOException {
        // Make file of the path
        File file = new File(path);

        // Set size
        this.setSize(size);
        // Generate QRCode to the file
        BitMatrix bitMatrix = generateQRCodeMatrix();

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG",file.toPath());

        return file;
    }

    protected BitMatrix generateQRCodeMatrix() throws WriterException {
        // Get temp file
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        bitMatrix = qrCodeWriter.encode(this.getURL(), BarcodeFormat.QR_CODE, this.getSize(), this.getSize());
        return bitMatrix;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }


    public static BufferedImage convertMatrixToImage(BitMatrix bitMatrix) {
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
