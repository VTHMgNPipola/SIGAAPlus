package com.vthmgnpipola.sigaaplus.gui;

import com.vthmgnpipola.sigaaplus.model.Turma;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TurmaCardPanel extends JPanel {
    public TurmaCardPanel(Turma turma) {
        super(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.lightGray, 1, true));

        JPanel interno = new JPanel(new BorderLayout());
        interno.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel nomeLabel = new JLabel(turma.getNome());
        nomeLabel.setFont(new Font("Fira Code Retina", Font.PLAIN, 16));
        interno.add(nomeLabel, BorderLayout.NORTH);

        JPanel statusPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;

        String local = turma.getLocal() == null ? "Não definido" : turma.getLocal();
        JLabel localLabel = new JLabel("Local: " + local);
        c.gridy = 0;
        statusPanel.add(localLabel, c);

        interno.add(statusPanel, BorderLayout.WEST);

        JPanel painelAcessarDetalhes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton acessarDetalhesButton = new JButton("Acessar Detalhes");
        painelAcessarDetalhes.add(acessarDetalhesButton);
        interno.add(painelAcessarDetalhes, BorderLayout.SOUTH);

        add(interno);
    }
}
