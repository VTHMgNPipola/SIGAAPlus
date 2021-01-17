package com.vthmgnpipola.sigaaplus.gui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vthmgnpipola.sigaaplus.Configuracao;
import com.vthmgnpipola.sigaaplus.model.Turma;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import javax.swing.JPanel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public class TurmasPanel extends JPanel {
    public TurmasPanel() {
        super(new GridBagLayout());
        setBackground(Color.white);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 0, 10);
        c.gridx = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        URL urlTurmas;
        try {
            urlTurmas = new URL(Configuracao.getSigaapifscUrl(), "/turmas/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            DialogHelper.mostrarErroFatal(this, "Ocorreu um " +
                    "erro ao formar a URL para acessar o Sigaapifsc!");
            return;
        }
        Request requestTurmas = new Request.Builder()
                .url(urlTurmas)
                .addHeader("Authorization", "Bearer " + Configuracao.getTokenJwt())
                .build();

        Call call = Configuracao.getHttpClient().newCall(requestTurmas);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                DialogHelper.mostrarErro(TurmasPanel.this, "Ocorreu um erro acessando o Sigaapifsc!");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() == 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    String responseBody = Objects.requireNonNull(response.body()).string();

                    List<Turma> turmas = mapper.readValue(responseBody, new TypeReference<>() {});
                    for (int i = 0; i < turmas.size(); i++) {
                        c.gridy = i;
                        add(new TurmaCardPanel(turmas.get(i)), c);
                    }

                    updateUI();
                }
            }
        });
    }
}
