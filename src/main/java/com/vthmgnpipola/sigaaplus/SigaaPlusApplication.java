package com.vthmgnpipola.sigaaplus;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
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

        // Instala o info dos temas FlatLaf
        FlatLightLaf.installLafInfo();
        FlatDarkLaf.installLafInfo();
        FlatIntelliJLaf.installLafInfo();
        FlatDarculaLaf.installLafInfo();

        // Instala o tema
        String tema = Configuracao.getProperty(Configuracao.PROPRIEDADE_TEMA);
        if (tema == null) {
            FlatIntelliJLaf.install();
        } else {
            UIManager.setLookAndFeel(tema);
        }

        // Define a URL do Sigaapifsc
        if (Configuracao.getProperty(Configuracao.PROPRIEDADE_URL_SIGAAPIFSC) == null) {
            String urlSigaapifsc = JOptionPane.showInputDialog(
                    "Essa parece ser a primeira vez que o SIGAAPlus é iniciado. Por isso, " +
                    "preciso saber qual é a URL para o Sigaapifsc que quer usar. Se não sabe o que isso é ou " +
                    "significa, deixe exatamente da forma que está:", "https://api.sigaapifsc.org/");
            Configuracao.setProperty(Configuracao.PROPRIEDADE_URL_SIGAAPIFSC, urlSigaapifsc);
        }
    }

    public void iniciar() {
        LoginSigaapifscFrame loginSigaapifscFrame = new LoginSigaapifscFrame(true);
        loginSigaapifscFrame.iniciar();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException {
        SigaaPlusApplication sigaaPlusApplication = new SigaaPlusApplication();
        sigaaPlusApplication.configurar();
        sigaaPlusApplication.iniciar();
    }
}
