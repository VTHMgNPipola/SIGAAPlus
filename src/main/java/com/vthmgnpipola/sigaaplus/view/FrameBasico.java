package com.vthmgnpipola.sigaaplus.view;

import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public abstract class FrameBasico extends JFrame {
    private static Image icone;

    static {
        try {
            icone = ImageIO.read(Objects.requireNonNull(FrameBasico.class.getClassLoader()
                            .getResourceAsStream("icone.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FrameBasico(String titulo) {
        super(titulo);
        setIconImage(icone);
    }
}
