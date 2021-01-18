package com.vthmgnpipola.sigaaplus.view;

import com.vthmgnpipola.sigaaplus.Configuracao;
import com.vthmgnpipola.sigaaplus.controller.HomeController;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class HomeFrame extends FrameBasico {
    public HomeFrame() {
        super("SIGAAPlus - Home");
        HomeController controller = HomeController.getInstance();

        /*
        Menus
         */
        JMenuBar menuBar = new JMenuBar();

        JMenu editarMenu = new JMenu("Editar");
        JMenu temasMenu = new JMenu("Temas");
        for (UIManager.LookAndFeelInfo tema : UIManager.getInstalledLookAndFeels()) {
            JMenuItem menuItem = new JMenuItem(tema.getName());
            menuItem.addActionListener(event -> controller.modificarTema(tema, this));
            temasMenu.add(menuItem);
        }
        editarMenu.add(temasMenu);

        JMenuItem modificarUrlSigaapifscMenu = new JMenuItem("Modificar URL do Sigaapifsc");
        modificarUrlSigaapifscMenu.addActionListener(event -> controller.modificarUrlSigaapifsc(this));
        editarMenu.add(modificarUrlSigaapifscMenu);

        JMenuItem removerContaSigaapifscMenu = new JMenuItem("Remover Conta do Sigaapifsc");
        removerContaSigaapifscMenu.addActionListener(event -> controller.removerContaSigaapifsc(this));
        editarMenu.add(removerContaSigaapifscMenu);
        menuBar.add(editarMenu);

        setJMenuBar(menuBar);

        /*
        Componentes
         */
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

        tabbedPane.addTab("Turmas", new JPanel());
        tabbedPane.addTab("Tarefas", new JPanel());
        tabbedPane.addTab("Notícias", new JPanel());
        tabbedPane.addTab("Fóruns", new JPanel());
        tabbedPane.addTab("Mensagens", new JPanel());

        add(tabbedPane);

        /*
        "Resto"
         */
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Configuracao.setProperty(Configuracao.PROPRIEDADE_HOME_DIMENSAO, getWidth() + "x" + getHeight());
            }
        });

        int width = 1024;
        int height = 576;
        String dimensaoStr = Configuracao.getProperty(Configuracao.PROPRIEDADE_HOME_DIMENSAO);
        if (dimensaoStr != null) {
            String[] dimensaoStrParts = dimensaoStr.split("x");
            width = Integer.parseInt(dimensaoStrParts[0]);
            height = Integer.parseInt(dimensaoStrParts[1]);
        }
        setSize(width, height);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
