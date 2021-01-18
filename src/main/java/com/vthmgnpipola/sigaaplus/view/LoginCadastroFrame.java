package com.vthmgnpipola.sigaaplus.view;

import com.vthmgnpipola.sigaaplus.controller.LoginCadastroController;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginCadastroFrame extends FrameBasico {
    public static final int MODO_CADASTRO_SIGAAPIFSC = 0;
    public static final int MODO_LOGIN_SIGAAPIFSC = 1;
    public static final int MODO_LOGIN_SIGAA = 2;

    // Esse é o melhor método de fazer isso? Provavelmente não. Mas eu não tenho certeza de como fazer isso de uma
    // forma melhor sem criar 1000 classes diferentes desnecessariamente
    private static final String[] textoTitulo = new String[] {"SIGAAPlus - Cadastrar-se no Sigaapifsc",
            "SIGAAPlus - Logar no Sigaapifsc", "SIGAAPlus - Logar no SIGAA"};

    private static final String[] textoHeader = new String[] {"Cadastrar - Sigaapifsc", "Login - Sigaapifsc",
            "Login - SIGAA"};

    private static final String[] textSenhaLabel = new String[] {"Senha Sigaapifsc", "Senha Sigaapifsc", "Senha SIGAA"};

    private static final String[] textoAcaoSecundaria = new String[] {"Login", "Cadastrar", "Sair"};

    private static final String[] textoAcaoPrimaria = new String[] {"Cadastrar", "Entrar", "Entrar"};

    public LoginCadastroFrame(int modo) {
        super(textoTitulo[modo]);
        LoginCadastroController controller = new LoginCadastroController();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        Insets insetsPadrao = new Insets(0, 5, 5, 5);

        JLabel headerLabel = new JLabel(textoHeader[modo]);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 1;
        constraints.insets = new Insets(10, 5, 15, 5);
        panel.add(headerLabel, constraints);

        JLabel usuarioLabel = new JLabel("Nome de Usuário");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_END;
        constraints.weightx = 0;
        constraints.insets = insetsPadrao;
        panel.add(usuarioLabel, constraints);

        JTextField usuarioField = new JTextField();
        if (modo == MODO_LOGIN_SIGAA) {
            usuarioField.setText(controller.getNomeUsuario());
            usuarioField.setEnabled(false);
        }
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.weightx = 1;
        constraints.insets = insetsPadrao;
        panel.add(usuarioField, constraints);

        JLabel senhaLabel = new JLabel(textSenhaLabel[modo]);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_END;
        constraints.weightx = 0;
        constraints.insets = insetsPadrao;
        panel.add(senhaLabel, constraints);

        JPasswordField senhaField = new JPasswordField();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.weightx = 1;
        constraints.insets = insetsPadrao;
        panel.add(senhaField, constraints);

        JCheckBox lembrarCheckBox = new JCheckBox("Lembrar Usuário");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.weightx = 1;
        constraints.insets = insetsPadrao;
        if (modo != MODO_CADASTRO_SIGAAPIFSC) {
            panel.add(lembrarCheckBox, constraints);
        }

        JButton acaoSecundaria = new JButton(textoAcaoSecundaria[modo]);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.weightx = 0;
        constraints.insets = insetsPadrao;
        panel.add(acaoSecundaria, constraints);

        JButton acaoPrimaria = new JButton(textoAcaoPrimaria[modo]);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_END;
        constraints.weightx = 0;
        constraints.insets = insetsPadrao;
        panel.add(acaoPrimaria, constraints);

        switch (modo) {
            case MODO_CADASTRO_SIGAAPIFSC -> {
                acaoSecundaria.addActionListener(event -> controller.abrirTelaLoginSigaapifsc(this));
                acaoPrimaria.addActionListener(event -> controller.cadastrarUsuario(usuarioField.getText(),
                        new String(senhaField.getPassword()), this));
            } case MODO_LOGIN_SIGAAPIFSC -> {
                acaoSecundaria.addActionListener(event -> controller.abrirTelaCadastro(this));
                acaoPrimaria.addActionListener(event -> controller.logarUsuarioSigaapifsc(usuarioField.getText(),
                        new String(senhaField.getPassword()), lembrarCheckBox.isSelected(), this));
            } case MODO_LOGIN_SIGAA -> {
                acaoSecundaria.addActionListener(event -> controller.abrirTelaLoginSigaapifsc(this));
                acaoPrimaria.addActionListener(event -> controller.logarUsuarioSigaa(
                        new String(senhaField.getPassword()), lembrarCheckBox.isSelected(), this));
            }
        }

        setContentPane(panel);
        pack();
        setSize(350, getHeight());
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
