package com.vthmgnpipola.sigaaplus.gui;

import com.vthmgnpipola.sigaaplus.Configuracao;
import com.vthmgnpipola.sigaaplus.SigaapifscHelper;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginSigaaFrame extends CustomFrame {
    public LoginSigaaFrame(boolean centered) {
        super("SIGAAPlus - Login SIGAA", centered);
    }

    @Override
    public void iniciar() {
        // Verifica se o usuário ainda tem uma sessão ativa
        Request requestPing = SigaapifscHelper.construirRequestAutorizada(Configuracao.URL_PING).build();
        Optional<Response> responseOptional = SigaapifscHelper.executarRequestSincrona(requestPing);
        if (responseOptional.isEmpty()) {
            return;
        }
        Response responsePing = responseOptional.get();

        if (responsePing.code() == 200) { // Caso a sessão ainda seja válida
            proximaJanela();
            return;
        } else if (Configuracao.getProperty(Configuracao.PROPRIEDADE_SENHA_SIGAA) != null) { // Caso a sessão seja
            // inválida, mas a senha do SIGAA esteja salva
            logar(Configuracao.getProperty(Configuracao.PROPRIEDADE_SENHA_SIGAA), false);
        }

        // Inicialização da janela
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets insets = new Insets(0, 5, 5, 5);
        c.insets = insets;
        c.weighty = 1;

        // Header
        JLabel headerLabel = new JLabel("Login - SIGAA");
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
        usuarioField.setEnabled(false);
        // Insere o nome de usuário no field
        Request requestNome = SigaapifscHelper.construirRequestAutorizada(Configuracao.URL_NOME).build();
        @SuppressWarnings("OptionalGetWithoutIsPresent") // Não é necessário checar se existe ou não, pois a checagem já
        // foi feita anteriormente
        Response responseNome = SigaapifscHelper.executarRequestSincrona(requestNome).get();
        try {
            usuarioField.setText(Objects.requireNonNull(responseNome.body()).string());
            Objects.requireNonNull(responseNome.body()).close();
        } catch (IOException e) {
            e.printStackTrace();
            DialogHelper.mostrarErro(this, "Não foi possível ler a resposta do Sigaapifsc " +
                    "(requisição: nome do usuário)!");
        }

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(usuarioField, c);

        // Campo de senha
        JLabel senhaLabel = new JLabel("Senha SIGAA");
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

        // Checkbox 'lembrar usuário'
        JCheckBox lembrarUsuarioCheckBox = new JCheckBox("Lembrar-me");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weightx = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(lembrarUsuarioCheckBox, c);

        // Botão sair
        JButton sairButton = new JButton("Sair");
        sairButton.addActionListener(event -> {
            Configuracao.removeProperty(Configuracao.PROPRIEDADE_JWT_PERSISTENTE);
            Configuracao.setTokenJwt(null);
            LoginSigaapifscFrame loginSigaapifscFrame = new LoginSigaapifscFrame(true);
            loginSigaapifscFrame.iniciar();
            dispose();
        });
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(sairButton, c);

        // Botão de login
        JButton entrarButton = new JButton("Entrar");
        entrarButton.addActionListener(event -> {
            logar(new String(senhaField.getPassword()), lembrarUsuarioCheckBox.isSelected());
        });
        c.gridx = 1;
        c.gridy = 4;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        getRootPane().setDefaultButton(entrarButton);
        panel.add(entrarButton, c);

        setContentPane(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setSize(350, getHeight());
        setResizable(false);
        setVisible(true);
    }

    private void logar(String senha, boolean lembrarUsuario) {
        // Envia a requisição
        Request request = SigaapifscHelper.construirRequestAutorizada(Configuracao.URL_LOGIN)
                .addHeader("X-Senha-Sigaa", senha)
                .post(RequestBody.create(new byte[0]))
                .build();

        Optional<Response> responseOptional = SigaapifscHelper.executarRequestSincrona(request);
        if (responseOptional.isEmpty()) {
            return;
        }
        Response response = responseOptional.get();

        // Processa a resposta
        if (response.code() == 404) { // Credenciais inválidas
            DialogHelper.mostrarErro(LoginSigaaFrame.this, "As credenciais para o SIGAA são " +
                    "inválidas!");
        } else if (response.code() == 200) { // Sucesso
            if (lembrarUsuario) {
                int status = DialogHelper.mostrarConfirmacao(LoginSigaaFrame.this, "Você quer " +
                        "mesmo lembrar sua sessão do SIGAA? Você não vai precisar digitar a senha toda vez que se" +
                        " sessão expirar, mas para isso sua senha será guardada sem criptografia no arquivo de " +
                        "propriedades!", JOptionPane.YES_NO_OPTION);
                if (status == JOptionPane.YES_OPTION) {
                    Configuracao.setProperty(Configuracao.PROPRIEDADE_SENHA_SIGAA, senha);
                }
            }

            // Inicia o resto do SIGAAPlus
            proximaJanela();
        }
    }

    private void proximaJanela() {
        HomeFrame homeFrame = new HomeFrame(true);
        homeFrame.iniciar();
        dispose();
    }
}
