package com.vthmgnpipola.sigaaplus.gui;

import com.vthmgnpipola.sigaaplus.Configuracao;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class HomeFrame extends CustomFrame {
    public HomeFrame(boolean centered) {
        super("SIGAAPlus - Home", centered);

        /*
        Barra de menu
         */
        JMenuBar menuBar = new JMenuBar();

        // Menu 'editar'
        JMenu editarMenu = new JMenu("Editar");

        // Item de menu 'tema'
        JMenu temaMenu = new JMenu("Tema");

        UIManager.LookAndFeelInfo[] temas = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo tema : temas) {
            JMenuItem temaItem = new JMenuItem(tema.getName());
            temaItem.addActionListener(event -> {
                Configuracao.setProperty(Configuracao.PROPERTY_TEMA, tema.getClassName());
                try {
                    UIManager.setLookAndFeel(tema.getClassName());
                    SwingUtilities.updateComponentTreeUI(HomeFrame.this);
                } catch (Exception e) {
                    e.printStackTrace();
                    DialogHelper.mostrarErro(HomeFrame.this, "Não foi possível alterar o tema em tempo " +
                            "real! Será necessário reiniciar o SIGAAPlus para verificar as mudanças.");
                }
            });
            temaMenu.add(temaItem);
        }

        editarMenu.add(temaMenu);

        menuBar.add(editarMenu);

        setJMenuBar(menuBar);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
