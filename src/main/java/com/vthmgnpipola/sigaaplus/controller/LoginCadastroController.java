package com.vthmgnpipola.sigaaplus.controller;

import com.vthmgnpipola.sigaaplus.Configuracao;
import com.vthmgnpipola.sigaaplus.SigaapifscHelper;
import com.vthmgnpipola.sigaaplus.view.DialogHelper;
import com.vthmgnpipola.sigaaplus.view.HomeFrame;
import com.vthmgnpipola.sigaaplus.view.LoginCadastroFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginCadastroController {
    private static final LoginCadastroController instance = new LoginCadastroController();

    private LoginCadastroController() {}

    public static LoginCadastroController getInstance() {
        return instance;
    }

    public void iniciar() {
        String jwt = Configuracao.getProperty(Configuracao.PROPRIEDADE_JWT_PERSISTENTE);
        if (jwt != null) {
            Configuracao.setTokenJwt(jwt);
            if (checarSessaoSigaapifsc()) {
                if (checarSessaoSigaa()) {
                    iniciarTelaHome();
                } else if (Configuracao.getProperty(Configuracao.PROPRIEDADE_SENHA_SIGAA) != null) {
                    logarUsuarioSigaa(Configuracao.getProperty(Configuracao.PROPRIEDADE_SENHA_SIGAA),
                            false, null);
                } else {
                    iniciarTelaLoginSigaa();
                }
            } else {
                Configuracao.setTokenJwt(null);
                Configuracao.removeProperty(Configuracao.PROPRIEDADE_JWT_PERSISTENTE);
                iniciarTelaLoginSigaapifsc();
            }
        } else {
            iniciarTelaLoginSigaapifsc();
        }
    }

    private boolean checarSessaoSigaapifsc() {
        Request request = SigaapifscHelper.construirRequestAutorizada(Configuracao.URL_NOME).build();
        Response response = SigaapifscHelper.executarRequestSincrona(request);
        assert response != null;
        return response.code() == 200;
    }

    private boolean checarSessaoSigaa() {
        Request request = SigaapifscHelper.construirRequestAutorizada(Configuracao.URL_PING).build();
        Response response = SigaapifscHelper.executarRequestSincrona(request);
        assert response != null;
        return response.code() == 200;
    }

    private void iniciarTelaLoginSigaapifsc() {
        SwingUtilities.invokeLater(() -> {
            LoginCadastroFrame frame = new LoginCadastroFrame(LoginCadastroFrame.MODO_LOGIN_SIGAAPIFSC);
            frame.setVisible(true);
        });
    }

    private void iniciarTelaLoginSigaa() {
        SwingUtilities.invokeLater(() -> {
            LoginCadastroFrame frame = new LoginCadastroFrame(LoginCadastroFrame.MODO_LOGIN_SIGAA);
            frame.setVisible(true);
        });
    }

    private void iniciarTelaHome() {
        HomeFrame homeFrame = new HomeFrame();
        homeFrame.setVisible(true);
    }

    public String getNomeUsuario() {
        Request request = SigaapifscHelper.construirRequestAutorizada(Configuracao.URL_NOME).build();
        Response response = SigaapifscHelper.executarRequestSincrona(request);
        assert response != null;
        if (response.code() == 200) {
            return SigaapifscHelper.getBodyString(response);
        } else {
            return null;
        }
    }

    public void abrirTelaCadastro(LoginCadastroFrame frame) {
        frame.dispose();
        frame = new LoginCadastroFrame(LoginCadastroFrame.MODO_CADASTRO_SIGAAPIFSC);
        frame.setVisible(true);
    }

    public void abrirTelaLoginSigaapifsc(LoginCadastroFrame frame) {
        frame.dispose();
        frame = new LoginCadastroFrame(LoginCadastroFrame.MODO_LOGIN_SIGAAPIFSC);
        frame.setVisible(true);
        Configuracao.setTokenJwt(null);
    }

    public void abrirTelaLoginSigaa(LoginCadastroFrame frame) {
        frame.dispose();
        frame = new LoginCadastroFrame(LoginCadastroFrame.MODO_LOGIN_SIGAA);
        frame.setVisible(true);
    }

    public void cadastrarUsuario(String usuario, String senha, LoginCadastroFrame frame) {
        Request request = SigaapifscHelper.construirRequest(Configuracao.URL_CADASTRAR)
                .addHeader("X-Usuario", usuario)
                .addHeader("X-Senha", senha)
                .post(RequestBody.create(new byte[0]))
                .build();
        Response response = SigaapifscHelper.executarRequestSincrona(request);

        assert response != null;
        if (response.code() == 200) {
            abrirTelaLoginSigaapifsc(frame);
        } else {
            DialogHelper.mostrarErro(frame, "Não foi possível realizar o cadastro deste usuário no " +
                    "Sigaapifsc!");
        }
    }

    public void logarUsuarioSigaapifsc(String usuario, String senha, boolean lembrar, LoginCadastroFrame frame) {
        Request request = SigaapifscHelper.construirRequest(Configuracao.URL_AUTENTICAR)
                .addHeader("X-Usuario", usuario)
                .addHeader("X-Senha", senha)
                .post(RequestBody.create(new byte[0]))
                .build();
        Response response = SigaapifscHelper.executarRequestSincrona(request);

        assert response != null;
        if (response.code() == 200) {
            String jwt = SigaapifscHelper.getBodyString(response);
            Configuracao.setTokenJwt(jwt);
            if (lembrar) {
                Configuracao.setProperty(Configuracao.PROPRIEDADE_JWT_PERSISTENTE, jwt);
            }
            abrirTelaLoginSigaa(frame);
        } else {
            DialogHelper.mostrarErro(frame, "Não foi possível realizar a autenticação no Sigaapifsc! " +
                    "Verifique suas credenciais, e tenha certeza de que possui uma conta no Sigaapifsc (não é a " +
                    "mesma do SIGAA)!");
        }
    }

    public void logarUsuarioSigaa(String senha, boolean lembrar, LoginCadastroFrame frame) {
        Request request = SigaapifscHelper.construirRequestAutorizada(Configuracao.URL_LOGIN)
                .addHeader("X-Senha-Sigaa", senha)
                .post(RequestBody.create(new byte[0]))
                .build();
        Response response = SigaapifscHelper.executarRequestSincrona(request);

        assert response != null;
        if (response.code() == 200) {
            if (lembrar) {
                int confirmacaoLembrar = DialogHelper.mostrarConfirmacao(frame, "Tem certeza que deseja " +
                        "lembrar a sessão SIGAA? Para fazer isso, o SIGAAPlus armazenará sua senha sem qualquer " +
                        "criptografia no arquivo de configurações!", JOptionPane.YES_NO_OPTION);

                if (confirmacaoLembrar == JOptionPane.YES_OPTION) {
                    Configuracao.setProperty(Configuracao.PROPRIEDADE_SENHA_SIGAA, senha);
                }
            }

            if (frame != null) {
                frame.dispose();
            }
            iniciarTelaHome();
        } else if (response.code() == 400) {
            DialogHelper.mostrarErro(frame, "As credenciais para o SIGAA estão inválidas!");
        } else if (response.code() == 401) {
            DialogHelper.mostrarErro(frame, "Sessão Sigaapifsc inválida! Faça o login novamente.");
            if (frame != null) {
                abrirTelaLoginSigaapifsc(frame);
            } else {
                iniciarTelaLoginSigaapifsc();
            }
        }
    }
}
