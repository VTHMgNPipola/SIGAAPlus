package com.vthmgnpipola.sigaaplus;

import com.vthmgnpipola.sigaaplus.gui.DialogHelper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import javax.swing.JOptionPane;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;
import okhttp3.OkHttpClient;

public class Configuracao {
    public static final String PROPRIEDADE_TEMA = "tema";
    public static final String PROPRIEDADE_URL_SIGAAPIFSC = "sigaapifsc.url";
    public static final String PROPRIEDADE_JWT_PERSISTENTE = "jwt";
    public static final String PROPRIEDADE_HOME_DIMENSAO = "home.dimensao";
    public static final String PROPRIEDADE_HOME_MAXIMIZADA = "home.maximizada";
    public static final String PROPRIEDADE_SENHA_SIGAA = "sigaa.senha";

    public static URL URL_AUTENTICACAO;
    public static URL URL_LOGIN;
    public static URL URL_PING;

    private static Properties properties;
    private static final Path dataDirectory;
    private static final Path arquivoPropriedades;

    private static final OkHttpClient httpClient;

    private static String tokenJwt;

    static {
        AppDirs appDirs = AppDirsFactory.getInstance();
        dataDirectory = Paths.get(appDirs.getUserDataDir("SIGAAPlus", "0.0.1", "João H"));
        System.out.println("Pasta de dados sendo utilizada: " + dataDirectory.toString());
        if (!Files.exists(dataDirectory)) {
            try {
                Files.createDirectories(dataDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        arquivoPropriedades = dataDirectory.resolve("sigaaplus.properties");

        httpClient = new OkHttpClient();
    }

    public static void carregarPropriedades() throws IOException {
        if (!Files.exists(arquivoPropriedades)) {
            Files.createFile(dataDirectory.resolve("sigaaplus.properties"));
        }

        properties = new Properties();
        properties.load(Files.newInputStream(arquivoPropriedades));
    }

    public static void salvarPropriedades() throws IOException {
        properties.store(Files.newOutputStream(arquivoPropriedades), "Este é o arquivo de propriedades do " +
                "SIGAAPlus. Não compartilhe esse arquivo com ninguém, já que ele pode conter seus dados de acesso de " +
                "sua conta do Sigaapifsc e SIGAA.");
    }

    public static void inicializarUrls() {
        try {
            URL_LOGIN = new URL(Configuracao.getSigaapifscUrl(), "/acesso/logar");
            URL_AUTENTICACAO = new URL(Configuracao.getSigaapifscUrl(), "/acesso/autenticar");
            URL_PING = new URL(Configuracao.getSigaapifscUrl(), "/acesso/ping");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            DialogHelper.mostrarErroFatal(null, "Ocorreu um " +
                    "erro ao formar a URL para acessar o Sigaapifsc!");

            String urlSigaapifsc = JOptionPane.showInputDialog(
                    "A URL para o Sigaapifsc pode estar errada, portanto insira a URL correta:",
                    getProperty(PROPRIEDADE_URL_SIGAAPIFSC));
            setProperty(PROPRIEDADE_URL_SIGAAPIFSC, urlSigaapifsc);

            System.exit(-1);
        }
    }

    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void removeProperty(String key) {
        properties.remove(key);
    }

    public static void setTokenJwt(String tokenJwt) {
        Configuracao.tokenJwt = tokenJwt;
    }

    public static String getTokenJwt() {
        return tokenJwt;
    }

    public static URL getSigaapifscUrl() {
        try {
            return new URL(properties.getProperty(PROPRIEDADE_URL_SIGAAPIFSC));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            DialogHelper.mostrarErroFatal(null, "A URL para o Sigaapifsc configurada é inválida!");
            return null;
        }
    }

    public static OkHttpClient getHttpClient() {
        return httpClient;
    }
}
