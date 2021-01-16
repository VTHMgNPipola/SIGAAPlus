package com.vthmgnpipola.sigaaplus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

public class Configuracao {
    public static final String PROPERTY_TEMA = "tema";
    public static final String TEMA_LIGHT = "light";
    public static final String TEMA_DARK = "dark";
    public static final String TEMA_INTELLIJ = "intellij";
    public static final String TEMA_DARCULA = "darcula";
    public static final String TEMA_METAL = "metal";
    public static final String TEMA_NIMBUS = "nimbus";
    public static final String TEMA_MOTIF = "motif";
    public static final String TEMA_GTK = "gtk";
    public static final String TEMA_WINDOWS = "windows";

    private static Properties properties;
    private static Path dataDirectory;

    static {
        AppDirs appDirs = AppDirsFactory.getInstance();
        dataDirectory = Paths.get(appDirs.getUserDataDir("SIGAAPlus", "0.0.1", "João H"));
        if (!Files.exists(dataDirectory)) {
            try {
                Files.createDirectories(dataDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void carregarPropriedades() throws IOException {
        Path arquivoPropriedades = dataDirectory.resolve("sigaaplus.properties");
        if (!Files.exists(arquivoPropriedades)) {
            Files.createFile(dataDirectory.resolve("sigaaplus.properties"));
        }

        properties = new Properties();
        properties.load(Files.newInputStream(arquivoPropriedades));
    }

    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
