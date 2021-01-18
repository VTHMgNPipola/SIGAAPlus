package com.vthmgnpipola.sigaaplus.view;

import java.awt.Component;
import javax.swing.JOptionPane;

public class DialogHelper {
    public static void mostrarErro(Component pai, String mensagem) {
        JOptionPane.showMessageDialog(pai, mensagem, "SIGAAPlus - Erro!", JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrarErroFatal(Component pai, String mensagem) {
        mostrarErro(pai, mensagem);
        System.exit(-1);
    }

    public static int mostrarConfirmacao(Component pai, String mensagem, int tipo) {
        return JOptionPane.showConfirmDialog(pai, mensagem, "SIGAAPlus - Tem certeza?", tipo);
    }
}
