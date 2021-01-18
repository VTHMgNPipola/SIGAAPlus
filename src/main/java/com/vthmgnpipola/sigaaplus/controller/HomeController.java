package com.vthmgnpipola.sigaaplus.controller;

import com.vthmgnpipola.sigaaplus.Configuracao;
import com.vthmgnpipola.sigaaplus.view.DialogHelper;
import com.vthmgnpipola.sigaaplus.view.HomeFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class HomeController {
    private static final HomeController instance = new HomeController();

    private HomeController() {}

    public static HomeController getInstance() {
        return instance;
    }

    public void modificarTema(UIManager.LookAndFeelInfo tema, HomeFrame frame) {
        Configuracao.setProperty(Configuracao.PROPRIEDADE_TEMA, tema.getName());
        try {
            UIManager.setLookAndFeel(tema.getClassName());
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.mostrarErro(frame, "Não foi possível alterar o tema em tempo " +
                    "real! Será necessário reiniciar o SIGAAPlus para verificar as mudanças.");
        }
    }

    public void modificarUrlSigaapifsc(HomeFrame frame) {
        String urlSigaapifsc = JOptionPane.showInputDialog(frame, "Insira a URL " +
                "que será usada para acessar o Sigaapifsc:", Configuracao.getSigaapifscUrl());
        Configuracao.setProperty(Configuracao.PROPRIEDADE_URL_SIGAAPIFSC, urlSigaapifsc);
    }

    public void removerContaSigaapifsc(HomeFrame frame) {
        int status = DialogHelper.mostrarConfirmacao(frame, "Tem certeza que deseja remover sua conta " +
                "Sigaapifsc deste computador? O SIGAAPlus será fechado após isso.", JOptionPane.YES_NO_OPTION);
        if (status == JOptionPane.YES_OPTION) {
            Configuracao.removeProperty(Configuracao.PROPRIEDADE_JWT_PERSISTENTE);
            System.exit(0);
        }
    }
}
