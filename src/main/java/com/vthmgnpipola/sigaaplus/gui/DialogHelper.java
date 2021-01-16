package com.vthmgnpipola.sigaaplus.gui;

import java.awt.Component;
import javax.swing.JOptionPane;

public class DialogHelper {
    public static void mostrarErro(Component pai, String mensagem) {
        JOptionPane.showMessageDialog(pai, mensagem, "Erro!", JOptionPane.ERROR_MESSAGE);
    }
}
