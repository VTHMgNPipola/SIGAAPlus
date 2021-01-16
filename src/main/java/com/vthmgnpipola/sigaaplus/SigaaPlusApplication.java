package com.vthmgnpipola.sigaaplus;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.vthmgnpipola.sigaaplus.gui.LoginSigaapifscFrame;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SigaaPlusApplication {
    public void configurar() throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException {
        // Os menus Java são colocados na barra de título no Windows 10
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        Configuracao.carregarPropriedades();

        // Instala o tema
        String tema = Configuracao.getProperty(Configuracao.PROPERTY_TEMA);
        if (tema == null) {
            FlatIntelliJLaf.install();
        } else {
            String laf;
            switch (tema) {
                case Configuracao.TEMA_LIGHT -> laf = "com.formdev.flatlaf.FlatLightLaf";
                case Configuracao.TEMA_DARK -> laf = "com.formdev.flatlaf.FlatDarkLaf";
                case Configuracao.TEMA_INTELLIJ -> laf = "com.formdev.flatlaf.FlatIntelliJLaf";
                case Configuracao.TEMA_DARCULA -> laf = "com.formdev.flatlaf.FlatDarculaLaf";
                case Configuracao.TEMA_NIMBUS -> laf = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
                case Configuracao.TEMA_MOTIF -> laf = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                case Configuracao.TEMA_GTK -> laf = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
                case Configuracao.TEMA_WINDOWS -> laf = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                default -> laf = "javax.swing.plaf.metal.MetalLookAndFeel";
            }

            UIManager.setLookAndFeel(laf);
        }
    }

    public void logar() {
        LoginSigaapifscFrame loginSigaapifscFrame = new LoginSigaapifscFrame(true);
        loginSigaapifscFrame.setVisible(true);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException {
        SigaaPlusApplication sigaaPlusApplication = new SigaaPlusApplication();
        sigaaPlusApplication.configurar();
        sigaaPlusApplication.logar();
    }
}
