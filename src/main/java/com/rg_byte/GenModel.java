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


    /**
     * Set QRCode image and trigger the observer.
     * @param qrCodeImage Image to set.
     */
    public void setQRCode(BufferedImage qrCodeImage){
        BufferedImage old = this.qrCodeImage;
        this.qrCodeImage = qrCodeImage;
        support.firePropertyChange("qrcode", old, qrCodeImage);

    }

    /**
     * Set the Size for the next QRCode generation.
     * @param size Size to set.
     */
    void setSize(int size){
        this.size = size;
    }

    /**
     * Get the Size for generate a QRCode.
     * @return The size.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Set the data that will use for the next QRCode generation.
     * @param url Data to set.
     */
    public void setURL(String url) {
        this.url = url;
    }

    /**
     * Get the data for QRCode generation.
     * @return The data.
     */
    public String getURL() {
        return this.url;
    }

    /**
     * Action for the button click.
     * @throws WriterException Error while encoding BitMatrix.
     */
    public void generateAction() throws WriterException {
        setSize(1500);
        BitMatrix bitMatrix = generateQRCodeMatrix();
        BufferedImage image = GenModel.convertMatrixToImage(bitMatrix);
        setQRCode(image);
    }

    /**
     * Open JFileChooser to choose a dir.<br>
     * Open Size request. Default Value is 1500.<br>
     * Save the QRCode as png in the dir as qrcode_LONGVALUE.png.<br>
     * @return the QRCode File.
     * @throws IOException Error while write the file.
     * @throws WriterException Error while encoding BitMatrix.
     */
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

    /**
     * Generate a QRCode with data saved in the model and save the QRCode to a file.
     * @param path path to save the QRCode.
     * @param size size of QRCode File.
     * @return QRCode file.
     * @throws IllegalArgumentException If data is empty.
     * @throws WriterException Error while generate BitMatrix.
     * @throws IOException Error while writing the file.
     */
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

    /**
     * Generate QRCode Matrix object.
     * @return BitMatrix object.
     * @throws WriterException error while encoding.
     */
    protected BitMatrix generateQRCodeMatrix() throws WriterException {
        // Get temp file
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        bitMatrix = qrCodeWriter.encode(this.getURL(), BarcodeFormat.QR_CODE, this.getSize(), this.getSize());
        return bitMatrix;
    }

    /**
     * Add a listener to the observer.
     * @param pcl The listener.
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    /**
     * Convert a BitMatrix to a BufferedImage
     * @param bitMatrix The BitMatrix source.
     * @return The QRCode/BitMatrix as BufferedImage.
     */
    public static BufferedImage convertMatrixToImage(BitMatrix bitMatrix) {
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
