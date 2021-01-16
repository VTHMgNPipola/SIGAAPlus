package com.vthmgnpipola.sigaaplus;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.vthmgnpipola.sigaaplus.gui.DialogHelper;
import com.vthmgnpipola.sigaaplus.gui.LoginSigaapifscFrame;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SigaaPlusApplication {
    public void configurar() throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException {
        // Os menus Java são colocados na barra de título no Windows 10
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        Configuracao.carregarPropriedades();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Configuracao.salvarPropriedades();
            } catch (IOException e) {
                e.printStackTrace();
                DialogHelper.mostrarErro(null, "Não foi possível salvar o arquivo de propriedades do " +
                        "SIGAAPlus! O cliente continuará funcionando, mas não será possível salvar nenhuma " +
                        "configuração.");
            }
        }));

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

        // Define a URL do Sigaapifsc
        if (Configuracao.getProperty(Configuracao.PROPERTY_URL_SIGAAPIFSC) == null) {
            String urlSigaapifsc = JOptionPane.showInputDialog(
                    "Essa parece ser a primeira vez que o SIGAAPlus é iniciado. Por isso, " +
                    "preciso saber qual é a URL para o Sigaapifsc que quer usar. Se não sabe o que isso é ou " +
                    "significa, deixe exatamente da forma que está:", "https://api.sigaapifsc.org/");
            Configuracao.setProperty(Configuracao.PROPERTY_URL_SIGAAPIFSC, urlSigaapifsc);
        }
    }

    public void iniciar() {
        LoginSigaapifscFrame loginSigaapifscFrame = new LoginSigaapifscFrame(true);
        loginSigaapifscFrame.setVisible(true);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException {
        SigaaPlusApplication sigaaPlusApplication = new SigaaPlusApplication();
        sigaaPlusApplication.configurar();
        sigaaPlusApplication.iniciar();
    }
}
