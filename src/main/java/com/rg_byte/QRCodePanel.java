package com.rg_byte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
public class QRCodePanel extends JPanel {

    private transient BufferedImage image;
    private transient BufferedImage scaledImage;

    /**
     * Init the QRCodePanel.<br>
     * Init resize listener.
     */
    public QRCodePanel(){

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeImage();
            }
        });

        scaledImage = null;
        image = null;
    }

    /**
     * Resize the image to the size of the panel itself.
     */
    private void resizeImage() {
        if (image != null) {
            int width = getWidth();
            int height = getHeight();
            if (width > 0 && height > 0) {
                scaledImage = new BufferedImage(width, height, image.getType());
                Graphics2D g = scaledImage.createGraphics();
                g.drawImage(image, 0, 0, width, height, null);
                g.dispose();
                repaint();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(this.scaledImage, null, 0,0);
    }

    /**
     * Set the image of the panel.
     * @param image The image.
     */
    public void setImage(BufferedImage image) {
        this.image = image;
        resizeImage();
        repaint();
    }
}
