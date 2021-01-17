package com.vthmgnpipola.sigaaplus.gui;

import com.vthmgnpipola.sigaaplus.Configuracao;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class HomeFrame extends CustomFrame {
    public HomeFrame(boolean centered) {
        super("SIGAAPlus - Home", centered);
    }

    @Override
    public void iniciar() {
        /*
        Barra de menu
         */
        JMenuBar menuBar = new JMenuBar();

        // Menu 'editar'
        JMenu editarMenu = new JMenu("Editar");

        // Menu item 'tema'
        JMenu temaMenu = new JMenu("Tema");

        UIManager.LookAndFeelInfo[] temas = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo tema : temas) {
            JMenuItem temaItem = new JMenuItem(tema.getName());
            temaItem.addActionListener(event -> {
                Configuracao.setProperty(Configuracao.PROPRIEDADE_TEMA, tema.getClassName());
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

        // Menu item 'modificar URL do Sigaapifsc'
        JMenuItem modificarUrlSigaapifscItem = new JMenuItem("Modificar a URL do Sigaapifsc");
        modificarUrlSigaapifscItem.addActionListener(event -> {
            String urlSigaapifsc = JOptionPane.showInputDialog(HomeFrame.this, "Insira a URL " +
                    "que será usada para acessar o Sigaapifsc:", Configuracao.getSigaapifscUrl());
            Configuracao.setProperty(Configuracao.PROPRIEDADE_URL_SIGAAPIFSC, urlSigaapifsc);
        });
        editarMenu.add(modificarUrlSigaapifscItem);

        // Menu item 'remover conta do Sigaapifsc'
        JMenuItem removerContaSigaapifscItem = new JMenuItem("Remover Conta do Sigaapifsc");
        removerContaSigaapifscItem.addActionListener(event -> {
            int status = DialogHelper.mostrarConfirmacao(this, "Tem certeza que deseja remover sua conta " +
                            "Sigaapifsc deste computador? O SIGAAPlus será fechado após isso.", JOptionPane.YES_OPTION);
            if (status == JOptionPane.YES_OPTION) {
                Configuracao.removeProperty(Configuracao.PROPRIEDADE_JWT_PERSISTENTE);
                dispose();
            }
        });
        editarMenu.add(removerContaSigaapifscItem);

        // Adiciona os menus para a barra
        menuBar.add(editarMenu);

        /*
        Componentes
         */
        JPanel contentPane = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

        // Aba de turmas
        tabbedPane.addTab("Turmas", new JScrollPane(new TurmasPanel()));

        // Aba de tarefas
        JPanel tarefas = new JPanel();
        tabbedPane.addTab("Tarefas", tarefas);

        // Aba de notícias
        JPanel noticias = new JPanel();
        tabbedPane.addTab("Notícias", noticias);

        // Aba de fóruns
        JPanel foruns = new JPanel();
        tabbedPane.addTab("Fóruns", foruns);

        // Aba de mensagens
        JPanel mensagens = new JPanel();
        tabbedPane.addTab("Mensagens", mensagens);

        contentPane.add(tabbedPane, BorderLayout.CENTER);

        setContentPane(contentPane);

        // TODO: Adicionar listener para o estado de maximização da janela
        // Listener para o tamanho da janela
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Configuracao.setProperty(Configuracao.PROPRIEDADE_HOME_DIMENSAO, getWidth() + "x" + getHeight());
            }
        });

        // Define o tamanho da janela
        int width = 1024;
        int height = 576;
        String dimensaoStr = Configuracao.getProperty(Configuracao.PROPRIEDADE_HOME_DIMENSAO);
        if (dimensaoStr != null) {
            String[] dimensaoPartes = dimensaoStr.split("x");
            width = Integer.parseInt(dimensaoPartes[0]);
            height = Integer.parseInt(dimensaoPartes[1]);
        }
        setSize(width, height);

        // Configura e torna visível a janela
        setJMenuBar(menuBar);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Define se a janela estará maximizada
        String maximizada = Configuracao.getProperty(Configuracao.PROPRIEDADE_HOME_MAXIMIZADA);
        if (Boolean.parseBoolean(maximizada)) {
            setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        }
    }
}
