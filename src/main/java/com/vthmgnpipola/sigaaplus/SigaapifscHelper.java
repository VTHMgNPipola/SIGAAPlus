package com.vthmgnpipola.sigaaplus;

import com.vthmgnpipola.sigaaplus.view.DialogHelper;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import okhttp3.Request;
import okhttp3.Response;

public class SigaapifscHelper {
    public static Request.Builder construirRequest(URL url) {
        return new Request.Builder().url(url);
    }

    public static Request.Builder construirRequestAutorizada(URL url) {
        return new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + Configuracao.getTokenJwt());
    }

    public static Response executarRequestSincrona(Request request) {
        try {
            return Configuracao.getHttpClient().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            DialogHelper.mostrarErroFatal(null, "Ocorreu um " +
                    "erro ao tentar acessar o Sigaapifsc! Verifique sua conexão com a internet e URL " +
                    "para o Sigaapifsc!");
            return null;
        }
    }

    public static String getBodyString(Response response) {
        try {
            if (response == null) {
                return null;
            }

            return Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            e.printStackTrace();
            DialogHelper.mostrarErroFatal(null, "Ocorreu um erro ao ler a resposta enviada pelo Sigaapifsc!");
            return null;
        }
    }
}
