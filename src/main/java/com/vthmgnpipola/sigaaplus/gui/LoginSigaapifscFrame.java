package com.vthmgnpipola.sigaaplus.gui;

import com.vthmgnpipola.sigaaplus.Configuracao;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

        // Checkbox 'lembrar usuário'
        JCheckBox lembrarUsuarioCheckBox = new JCheckBox("Lembrar-me");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weightx = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(lembrarUsuarioCheckBox, c);

        // Botão de cadastro
        JButton cadastrarButton = new JButton("Cadastrar-se");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(cadastrarButton, c);

        // Botão de login
        JButton entrarButton = new JButton("Entrar");
        entrarButton.addActionListener(event -> {
            // Cria a URL
            URL urlAutenticacao;
            try {
                urlAutenticacao = new URL(Configuracao.getSigaapifscUrl(), "/acesso/autenticar");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                DialogHelper.mostrarErro(LoginSigaapifscFrame.this, "Ocorreu um " +
                        "erro ao formar a URL para acessar o Sigaapifsc!");
                return;
            }

            // Envia a requisição
            Request request = new Request.Builder()
                    .url(urlAutenticacao)
                    .addHeader("X-Usuario", usuarioField.getText())
                    .addHeader("X-Senha", new String(senhaField.getPassword()))
                    .post(RequestBody.create(new byte[0]))
                    .build();

            Response response;
            try {
                response = Configuracao.getHttpClient().newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                DialogHelper.mostrarErro(LoginSigaapifscFrame.this, "Ocorreu um " +
                                "erro ao tentar acessar o Sigaapifsc! Verifique sua conexão com a internet e URL " +
                        "para o Sigaapifsc!");
                return;
            }

            // Processa a resposta
            if (response.code() == 401) { // Credenciais inválidas
                DialogHelper.mostrarErro(LoginSigaapifscFrame.this, "Credenciais " +
                                "inválidas! Verifique suas credenciais, ou então crie uma conta no Sigaapifsc " +
                                "(não é a mesma do SIGAA).");
            } else if (response.code() == 200) { // Sucesso
                String tokenJwt;
                try {
                    tokenJwt = Objects.requireNonNull(response.body()).string();
                } catch (IOException e) {
                    e.printStackTrace();
                    DialogHelper.mostrarErro(LoginSigaapifscFrame.this, "A autenticação " +
                                    "foi bem sucedida, mas houve um erro ao analisar a resposta enviada!");
                    return;
                }

                Configuracao.setTokenJwt(tokenJwt);
                if (lembrarUsuarioCheckBox.isSelected()) {
                    Configuracao.setProperty(Configuracao.PROPERTY_JWT_PERSISTENTE, tokenJwt);
                }

                // Inicia o resto do SIGAAPlus
                dispose();
            }
        });
        c.gridx = 1;
        c.gridy = 4;
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
