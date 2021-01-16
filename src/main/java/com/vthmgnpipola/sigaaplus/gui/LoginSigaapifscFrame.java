package com.vthmgnpipola.sigaaplus.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginSigaapifscFrame extends CustomFrame {
    private static final String TITULO = "SIGAAPlus - Login Sigaapifsc";

    public LoginSigaapifscFrame(boolean centered) {
        super(TITULO, centered);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets insets = new Insets(0, 5, 5, 5);
        c.insets = insets;
        c.weighty = 1;

        // Header
        JLabel headerLabel = new JLabel("Login - Sigaapifsc");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 5, 15, 5);
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(headerLabel, c);

        // Campo de nome de usuário
        JLabel usuarioLabel = new JLabel("Nome de Usuário");
        c.gridx = 0;
        c.gridy = 1;
        c.insets = insets;
        c.gridwidth = 1;
        c.weightx = 0;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(usuarioLabel, c);

        JTextField usuarioField = new JTextField();
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(usuarioField, c);

        // Campo de senha
        JLabel senhaLabel = new JLabel("Nome de Usuário");
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(senhaLabel, c);

        JPasswordField senhaField = new JPasswordField();
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(senhaField, c);

        // Botão de cadastro
        JButton cadastrarButton = new JButton("Cadastrar-se");
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(cadastrarButton, c);

        // Botão de login
        JButton entrarButton = new JButton("Entrar");
        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(entrarButton, c);

        setContentPane(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setSize(350, getHeight());
        setResizable(false);
    }
}
