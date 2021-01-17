package com.vthmgnpipola.sigaaplus.gui;

import com.vthmgnpipola.sigaaplus.Configuracao;
import com.vthmgnpipola.sigaaplus.SigaapifscHelper;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CadastrarSigaapifscFrame extends CustomFrame {
    CadastrarSigaapifscFrame(boolean centered) {
        super("SIGAAPlus - Cadastrar Sigaapifsc", centered);
    }

    @Override
    public void iniciar() {
        // Inicialização da janela
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets insets = new Insets(0, 5, 5, 5);
        c.insets = insets;
        c.weighty = 1;

        // Header
        JLabel headerLabel = new JLabel("Cadastrar - Sigaapifsc");
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
        JLabel senhaLabel = new JLabel("Senha Sigaapifsc");
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
        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(event -> {
            // Envia a requisição
            Request request = SigaapifscHelper.construirRequest(Configuracao.URL_CADASTRAR)
                    .addHeader("X-Usuario", usuarioField.getText())
                    .addHeader("X-Senha", new String(senhaField.getPassword()))
                    .post(RequestBody.create(new byte[0]))
                    .build();

            Response response;
            try {
                response = Configuracao.getHttpClient().newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                DialogHelper.mostrarErroFatal(CadastrarSigaapifscFrame.this, "Ocorreu um " +
                        "erro ao tentar acessar o Sigaapifsc! Verifique sua conexão com a internet e URL " +
                        "para o Sigaapifsc!");
                return;
            }

            // Processa a resposta
            if (response.code() == 400) { // Credenciais inválidas
                DialogHelper.mostrarErro(CadastrarSigaapifscFrame.this, "Este usuário já existe no " +
                        "Sigaapifsc!");
            } else if (response.code() == 200) {
                LoginSigaapifscFrame loginSigaapifscFrame = new LoginSigaapifscFrame(true);
                loginSigaapifscFrame.iniciar();
                dispose();
            }
        });
        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        getRootPane().setDefaultButton(cadastrarButton);
        panel.add(cadastrarButton, c);

        setContentPane(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setSize(350, getHeight());
        setResizable(false);
        setVisible(true);
    }
}
