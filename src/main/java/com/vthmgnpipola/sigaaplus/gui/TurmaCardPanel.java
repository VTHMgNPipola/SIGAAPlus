package com.vthmgnpipola.sigaaplus.gui;

import com.vthmgnpipola.sigaaplus.model.Turma;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TurmaCardPanel extends JPanel {
    public TurmaCardPanel(Turma turma) {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel nomeLabel = new JLabel(turma.getNome());
        nomeLabel.setFont(new Font("Fira Code Retina", Font.PLAIN, 16));
        add(nomeLabel, BorderLayout.NORTH);

        JButton acessarDetalhesButton = new JButton("Acessar Detalhes");
        add(acessarDetalhesButton, BorderLayout.SOUTH);
    }
}
