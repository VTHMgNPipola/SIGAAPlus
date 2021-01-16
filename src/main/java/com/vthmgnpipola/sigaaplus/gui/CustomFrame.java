package com.vthmgnpipola.sigaaplus.gui;

import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public abstract class CustomFrame extends JFrame {
    private static Image icone;

    static {
        try {
            icone = ImageIO.read(Objects.requireNonNull(CustomFrame.class.getClassLoader()
                    .getResourceAsStream("icone.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    CustomFrame(String titulo, boolean centered) {
        super(titulo);
        pack();
        if (centered) {
            setLocationRelativeTo(null);
        }
        if (icone != null) {
            setIconImage(icone);
        }
    }

    public abstract void iniciar();
}
