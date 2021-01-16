package com.vthmgnpipola.sigaaplus;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;
import okhttp3.OkHttpClient;

public class Configuracao {
    public static final String PROPERTY_TEMA = "tema";

    public static final String PROPERTY_URL_SIGAAPIFSC = "sigaapifsc.url";

    public static final String PROPERTY_JWT_PERSISTENTE = "jwt";

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

    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void setTokenJwt(String tokenJwt) {
        Configuracao.tokenJwt = tokenJwt;
    }

    public static String getTokenJwt() {
        return tokenJwt;
    }

    public static URL getSigaapifscUrl() throws MalformedURLException {
        return new URL(properties.getProperty(PROPERTY_URL_SIGAAPIFSC));
    }

    public static OkHttpClient getHttpClient() {
        return httpClient;
    }
}
