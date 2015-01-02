/*
 * Copyright 2012 Jordi CHARPENTIER jordi.charpentier@gmail.com
 * 
 * This file is part of JEditor.

 * JEditor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.

 * JEditor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with JEditor.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jc.ui.editeur.programmeur;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.text.*;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.undo.*;
import org.jc.Constantes;
import org.jc.ui.UndoRedo;
import org.jc.ui.frames.*;

/**
 * Classe représentant la fenêtre principale de l'éditeur pour programmeur.
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 */
public class Editeur extends JFrame {
    /* Tool barre. */

    private final JToolBar tool_barre = new JToolBar();
    private final JButton bout_new = new JButton();
    private final JButton bout_ouvrir = new JButton();
    private final JButton bout_enregistrer = new JButton();
    private final JButton bout_couper = new JButton();
    private final JButton bout_copier = new JButton();
    private final JButton bout_coller = new JButton();
    private final JButton bout_chercher = new JButton();
    private final JButton bout_imprimer = new JButton();

    /* Barre de menu. */
    private final JMenuBar barre_menu = new JMenuBar();
    private final JMenu menu_fichier = new JMenu();
    private final JMenuItem sous_menu_nouveau = new JMenuItem();
    private final JMenuItem sous_menu_ouvrir = new JMenuItem();
    private final JMenuItem sous_menu_fermer_editeur = new JMenuItem();
    private final JMenuItem sous_menu_enregistrer = new JMenuItem();
    private final JMenuItem sous_menu_enregistrer_sous = new JMenuItem();
    private final JMenuItem sous_menu_imprimer = new JMenuItem();
    private final JMenuItem sous_menu_quitter = new JMenuItem();
    private final JMenu menu_edition = new JMenu();
    private final JMenuItem sous_menu_copier = new JMenuItem();
    private final JMenuItem sous_menu_couper = new JMenuItem();
    private final JMenuItem sous_menu_coller = new JMenuItem();
    private final JMenuItem sous_menu_supprimer = new JMenuItem();
    private final JMenuItem sous_menu_tout_selectionner = new JMenuItem();
    private final JMenuItem sous_menu_recherche = new JMenuItem();
    private final JMenuItem sous_menu_plein_ecran = new JMenuItem();
    private final JMenu menu_insertion = new JMenu();
    private final JMenuItem sous_menu_en_tete = new JMenuItem();
    private final JMenuItem sous_menu_commentaire = new JMenuItem("Commentaire");
    private final JMenu menu_langage = new JMenu("Langage");
    private final JRadioButtonMenuItem sous_menu_item_bash = new JRadioButtonMenuItem("Bash");
    private final JRadioButtonMenuItem sous_menu_item_c = new JRadioButtonMenuItem("C");
    private final JRadioButtonMenuItem sous_menu_item_cpp = new JRadioButtonMenuItem("C++");
    private final JRadioButtonMenuItem sous_menu_item_html = new JRadioButtonMenuItem("HTML");
    private final JRadioButtonMenuItem sous_menu_item_java = new JRadioButtonMenuItem("Java");
    private final JRadioButtonMenuItem sous_menu_item_javascript = new JRadioButtonMenuItem("JavaScript");
    private final JRadioButtonMenuItem sous_menu_item_php = new JRadioButtonMenuItem("PHP");
    private final JRadioButtonMenuItem sous_menu_item_python = new JRadioButtonMenuItem("Python");
    private final JRadioButtonMenuItem sous_menu_item_ruby = new JRadioButtonMenuItem("Ruby");
    private final JRadioButtonMenuItem sous_menu_item_scala = new JRadioButtonMenuItem("Scala");
    private final JRadioButtonMenuItem sous_menu_item_sql = new JRadioButtonMenuItem("SQL");
    private final JMenuItem sous_menu_personnaliser_langage = new JMenuItem();
    private final JMenu menu_manuel_langage = new JMenu();
    private final JMenuItem sous_menu_manuel_bash = new JMenuItem("Bash");
    private final JMenuItem sous_menu_manuel_c = new JMenuItem("C");
    private final JMenuItem sous_menu_manuel_cpp = new JMenuItem("C++");
    private final JMenuItem sous_menu_manuel_html = new JMenuItem("HTML");
    private final JMenuItem sous_menu_manuel_java = new JMenuItem("Java");
    private final JMenuItem sous_menu_manuel_javascript = new JMenuItem("Javascript");
    private final JMenuItem sous_menu_manuel_php = new JMenuItem("PHP");
    private final JMenuItem sous_menu_manuel_python = new JMenuItem("Python");
    private final JMenuItem sous_menu_manuel_ruby = new JMenuItem("Ruby");
    private final JMenuItem sous_menu_manuel_scala = new JMenuItem("Scala");
    private final JMenuItem sous_menu_manuel_sql = new JMenuItem("SQL");
    private final JMenuItem sous_menu_custom_recherche = new JMenuItem();
    private final JMenu menu_fenetre = new JMenu();
    private final JMenu menu_langages = new JMenu();
    private final JRadioButtonMenuItem sous_menu_francais = new JRadioButtonMenuItem();
    private final JRadioButtonMenuItem sous_menu_anglais = new JRadioButtonMenuItem();
    private final JMenuItem sous_menu_statistiques = new JMenuItem();
    private final JMenuItem sous_menu_new_fenetre = new JMenuItem();
    private final JMenuItem sous_menu_fermer_fenetre = new JMenuItem();
    private final JMenu menu_aide = new JMenu();
    private final JMenuItem sous_menu_aide = new JMenuItem();
    private final JMenuItem sous_menu_site = new JMenuItem();
    private final JMenuItem sous_menu_jeditor = new JMenuItem();

    /* Popup menu. */
    private JPopupMenu pop_up_menu;
    private final JMenuItem pop_up_couper = new JMenuItem();
    private final JMenuItem pop_up_copier = new JMenuItem();
    private final JMenuItem pop_up_coller = new JMenuItem();
    private final JMenuItem pop_up_supprimer = new JMenuItem();
    private final JMenuItem pop_up_tout_selectionner = new JMenuItem();
    private final JMenuItem pop_up_commenter = new JMenuItem();

    /* Autres éléments graphiques. */
    private final JTextPane zone_texte = new JTextPane();
    private final JScrollPane barre_scroll = new JScrollPane(zone_texte);
    private final JFileChooser filechooser = new JFileChooser();
    private final JLabel lab_plein_ecran = new JLabel("<html><font size=\"6\"><strong>Appuyez sur ECHAP pour quitter le mode plein écran.</strong></font></html>");

    /* Liens des manuels. */
    private final String lien_bash = "http://www.gnu.org/software/bash/manual/bashref.html";
    private final String lien_c = "http://www.acm.uiuc.edu/webmonkeys/book/c_guide/";
    private final String lien_cpp = "http://www.cplusplus.com/doc/tutorial/";
    private final String lien_html = "http://fr.html.net/tutorials/html/";
    private final String lien_java = "http://download.oracle.com/javase/6/docs/api/";
    private final String lien_javascript = "http://www.webreference.com/js/";
    private final String lien_php = "http://php.net/manual/fr/index.php";
    private final String lien_ruby = "http://www.ruby-doc.org/";
    private final String lien_python = "http://docs.python.org/";
    private final String lien_scala = "http://www.scala-lang.org/api/current/index.html";
    private final String lien_sql = "http://sgbd.developpez.com/cours/";

    /* Listeners. */
    private final ActionsListener actionslistener = new ActionsListener();
    private final UndoManager undo_redo_listener = new UndoManager();

    /* Mot-clés des langages. */
    private String[] keywords_bash;
    private String[] keywords_c;
    private String[] keywords_cpp;
    private String[] keywords_html;
    private String[] keywords_java;
    private String[] keywords_javascript;
    private String[] keywords_php;
    private String[] keywords_python;
    private String[] keywords_ruby;
    private String[] keywords_scala;
    private String[] keywords_sql;

    /* Langues. */
    private ResourceBundle traducteur;

    private final Locale francais = new Locale("fr", "Francais");
    private final Locale anglais = new Locale("en", "Anglais");

    /* Autres. */
    private String[] langage_choisi;
    private String file_open = null;
    private String extension_current_file = null;
    private String texte_doc;

    private final HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.CYAN);
    private final GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public Editeur() {
        this.setTitle("JEditor - Programmeur");
        this.setJMenuBar(barre_menu);
        this.setSize((int) this.getToolkit().getScreenSize().getWidth() - 60, ((int) this.getToolkit().getScreenSize().getHeight() - 60));
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
        this.requestFocus();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/content/images/icone.png")));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                testEnregistrerFichier("fermer");
            }
        });

        this.setLayout(new BorderLayout());
        this.add(tool_barre, BorderLayout.NORTH);
        this.add(barre_scroll, BorderLayout.CENTER);

        zone_texte.setFocusable(true);
        zone_texte.requestFocus();

        initBarreMenu();
        initBouttonGroup();
        initFileChooser();
        initToolBarre();
        initStyle();
        initKeyWords();
        initLangage();

        enableOptionsBarreMenu();

        setListener();
        setAccelerator();
        setIconBarreMenu();
        setIconeToolBarre();
        setIconPopuMenu();

        this.setVisible(true);
    }

    /* Méthodes graphiques. */
    /**
     * Méthode permettant d'initialiser la barre de menu de la fenêtre.
     */
    private void initBarreMenu() {
        barre_menu.add(menu_fichier);
        menu_fichier.setMnemonic('F');
        menu_fichier.add(sous_menu_nouveau);
        menu_fichier.add(sous_menu_ouvrir);
        menu_fichier.addSeparator();
        menu_fichier.add(sous_menu_fermer_editeur);
        menu_fichier.add(sous_menu_enregistrer);
        menu_fichier.add(sous_menu_enregistrer_sous);
        menu_fichier.addSeparator();
        menu_fichier.add(sous_menu_imprimer);
        menu_fichier.addSeparator();
        menu_fichier.add(sous_menu_quitter);
        barre_menu.add(menu_edition);
        menu_edition.setMnemonic('E');

        Action undoaction = UndoRedo.getUndoAction(undo_redo_listener);
        menu_edition.add(undoaction).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
        ((AbstractButton) menu_edition.getMenuComponent(0)).setIcon(new ImageIcon(getClass().getResource("/content/images/autres/annuler.png")));
        Action redoaction = UndoRedo.getRedoAction(undo_redo_listener);
        menu_edition.add(redoaction).setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_MASK));
        ((AbstractButton) menu_edition.getMenuComponent(1)).setIcon(new ImageIcon(getClass().getResource("/content/images/autres/refaire.png")));

        menu_edition.addSeparator();
        menu_edition.add(sous_menu_couper);
        menu_edition.add(sous_menu_copier);
        menu_edition.add(sous_menu_coller);
        menu_edition.add(sous_menu_supprimer);
        menu_edition.add(sous_menu_tout_selectionner);
        menu_edition.addSeparator();
        menu_edition.add(sous_menu_recherche);
        menu_edition.addSeparator();
        menu_edition.add(sous_menu_plein_ecran);
        barre_menu.add(menu_insertion);
        menu_insertion.setMnemonic('I');
        menu_insertion.add(sous_menu_en_tete);
        menu_insertion.add(sous_menu_commentaire);
        barre_menu.add(menu_langage);
        menu_langage.setMnemonic('L');
        menu_langage.add(sous_menu_item_bash);
        menu_langage.add(sous_menu_item_c);
        menu_langage.add(sous_menu_item_cpp);
        menu_langage.add(sous_menu_item_html);
        menu_langage.add(sous_menu_item_java);
        menu_langage.add(sous_menu_item_javascript);
        menu_langage.add(sous_menu_item_php);
        menu_langage.add(sous_menu_item_python);
        menu_langage.add(sous_menu_item_ruby);
        menu_langage.add(sous_menu_item_scala);
        menu_langage.add(sous_menu_item_sql);
        menu_langage.addSeparator();
        menu_langage.add(sous_menu_personnaliser_langage);
        barre_menu.add(menu_manuel_langage);
        menu_manuel_langage.setMnemonic('M');
        menu_manuel_langage.add(sous_menu_manuel_bash);
        sous_menu_manuel_bash.setToolTipText(lien_bash);
        menu_manuel_langage.add(sous_menu_manuel_c);
        sous_menu_manuel_c.setToolTipText(lien_c);
        menu_manuel_langage.add(sous_menu_manuel_cpp);
        sous_menu_manuel_cpp.setToolTipText(lien_cpp);
        menu_manuel_langage.add(sous_menu_manuel_html);
        sous_menu_manuel_html.setToolTipText(lien_html);
        menu_manuel_langage.add(sous_menu_manuel_java);
        sous_menu_manuel_java.setToolTipText(lien_java);
        menu_manuel_langage.add(sous_menu_manuel_javascript);
        sous_menu_manuel_javascript.setToolTipText(lien_javascript);
        menu_manuel_langage.add(sous_menu_manuel_php);
        sous_menu_manuel_php.setToolTipText(lien_php);
        menu_manuel_langage.add(sous_menu_manuel_python);
        sous_menu_manuel_python.setToolTipText(lien_python);
        menu_manuel_langage.add(sous_menu_manuel_ruby);
        sous_menu_manuel_ruby.setToolTipText(lien_ruby);
        menu_manuel_langage.add(sous_menu_manuel_scala);
        sous_menu_manuel_scala.setToolTipText(lien_scala);
        menu_manuel_langage.add(sous_menu_manuel_sql);
        sous_menu_manuel_sql.setToolTipText(lien_sql);
        menu_manuel_langage.addSeparator();
        menu_manuel_langage.add(sous_menu_custom_recherche);
        barre_menu.add(menu_fenetre);
        menu_fenetre.setMnemonic(KeyEvent.VK_N);
        menu_fenetre.add(menu_langages);
        menu_langages.add(sous_menu_francais);
        menu_langages.add(sous_menu_anglais);
        menu_fenetre.add(sous_menu_statistiques);
        menu_fenetre.addSeparator();
        menu_fenetre.add(sous_menu_new_fenetre);
        menu_fenetre.add(sous_menu_fermer_fenetre);
        barre_menu.add(menu_aide);
        menu_aide.setMnemonic('A');
        menu_aide.add(sous_menu_aide);
        menu_aide.add(sous_menu_site);
        menu_aide.add(sous_menu_jeditor);
    }

    /**
     * Méthode permettant d'initialiser la tool barre de la fenêtre.
     */
    private void initToolBarre() {
        tool_barre.setFloatable(false);

        tool_barre.add(Box.createHorizontalStrut(5));
        tool_barre.add(bout_new);
        tool_barre.add(Box.createHorizontalStrut(10));
        tool_barre.add(bout_ouvrir);
        tool_barre.add(bout_enregistrer);
        tool_barre.add(Box.createHorizontalStrut(10));
        tool_barre.add(bout_imprimer);
        tool_barre.add(bout_chercher);
        tool_barre.add(Box.createHorizontalStrut(10));
        tool_barre.add(bout_couper);
        tool_barre.add(bout_copier);
        tool_barre.add(bout_coller);
    }

    /**
     * Méthode permettant d'initialiser le style par défaut du texte.
     */
    private void initStyle() {
        Style style = zone_texte.addStyle(null, null);
        StyleConstants.setFontFamily(style, "Monospaced");
        StyleConstants.setFontSize(style, 13);

        zone_texte.setLogicalStyle(style);
    }

    /**
     * Méthode permettant d'initialiser le menu pop-up (activé par un clic
     * droit) de la fenêtre.
     */
    private void initPopUpMenu() {
        pop_up_menu.add(pop_up_couper);
        pop_up_menu.add(pop_up_copier);
        pop_up_menu.add(pop_up_coller);
        pop_up_menu.add(pop_up_supprimer);
        pop_up_menu.add(pop_up_tout_selectionner);
        pop_up_menu.addSeparator();
        pop_up_menu.add(pop_up_commenter);
    }

    /**
     * Méthode permettant d'initialiser le sélecteur de fichier utilisé lors de
     * l'enregistrement et de l'ouverture d'un fichier.
     */
    private void initFileChooser() {
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        filechooser.setAcceptAllFileFilterUsed(false);

        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichiers bash", "sh"));
        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichiers c", "c"));
        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichiers cpp", "cpp"));
        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichiers html", "html"));
        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichiers java", "java"));
        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichiers javascript", "js"));
        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichiers php", "php"));
        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichiers python", "py"));
        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichiers ruby", "rb"));
        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichiers scala", "scl"));
        filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichiers sql", "sql"));
    }

    /**
     * Méthode permettant d'initialiser la fenêtre avec la langue par défaut du
     * logiciel.
     */
    private void initLangage() {
        traducteur = ResourceBundle.getBundle("content.textes.interface.texte", francais);
        initStringWithRightLanguage();

        ButtonGroup groupe_langage = new ButtonGroup();
        groupe_langage.add(sous_menu_francais);
        groupe_langage.add(sous_menu_anglais);

        sous_menu_francais.setSelected(true);
    }

    private void initBouttonGroup() {
        ButtonGroup groupe_langage = new ButtonGroup();
        groupe_langage.add(sous_menu_item_bash);
        groupe_langage.add(sous_menu_item_c);
        groupe_langage.add(sous_menu_item_cpp);
        groupe_langage.add(sous_menu_item_html);
        groupe_langage.add(sous_menu_item_java);
        groupe_langage.add(sous_menu_item_javascript);
        groupe_langage.add(sous_menu_item_php);
        groupe_langage.add(sous_menu_item_python);
        groupe_langage.add(sous_menu_item_ruby);
        groupe_langage.add(sous_menu_item_scala);
        groupe_langage.add(sous_menu_item_sql);

        sous_menu_item_bash.setSelected(true);
    }

    /**
     * Méthode permettant de mettre en place les listeners ("écouteurs")
     * nécéssaires.<br />
     * Plusieurs types de listeners sont mit en place (clavier/souris/clic).
     */
    private void setListener() {
        /* Zone de texte. */

        zone_texte.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Editeur.this.remove(lab_plein_ecran);
                    device.setFullScreenWindow(null);
                } else {
                    enableOptionsBarreMenu();
                }
            }
        });

        zone_texte.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    pop_up_menu = new JPopupMenu("Menu");
                    initPopUpMenu();
                    pop_up_menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        zone_texte.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                removeHighlights(zone_texte);
                enableOptionsBarreMenu();

                if (sous_menu_item_bash.isSelected()) {
                    LanguageHighlight(zone_texte, keywords_bash);
                } else if (sous_menu_item_c.isSelected()) {
                    LanguageHighlight(zone_texte, keywords_c);
                } else if (sous_menu_item_cpp.isSelected()) {
                    LanguageHighlight(zone_texte, keywords_cpp);
                } else if (sous_menu_item_html.isSelected()) {
                    LanguageHighlight(zone_texte, keywords_html);
                } else if (sous_menu_item_java.isSelected()) {
                    LanguageHighlight(zone_texte, keywords_java);
                } else if (sous_menu_item_javascript.isSelected()) {
                    LanguageHighlight(zone_texte, keywords_javascript);
                } else if (sous_menu_item_php.isSelected()) {
                    String[] keywords_php_html;

                    keywords_php_html = new String[keywords_php.length + keywords_html.length];
                    System.arraycopy(keywords_php, 0, keywords_php_html, 0, keywords_php.length);
                    System.arraycopy(keywords_html, 0, keywords_php_html, keywords_php.length, keywords_html.length);

                    LanguageHighlight(zone_texte, keywords_php_html);
                } else if (sous_menu_item_python.isSelected()) {
                    LanguageHighlight(zone_texte, keywords_python);
                } else if (sous_menu_item_ruby.isSelected()) {
                    LanguageHighlight(zone_texte, keywords_ruby);
                } else if (sous_menu_item_scala.isSelected()) {
                    LanguageHighlight(zone_texte, keywords_scala);
                } else if (sous_menu_item_sql.isSelected()) {
                    LanguageHighlight(zone_texte, keywords_sql);
                }
            }
        });

        zone_texte.getDocument().addUndoableEditListener(undo_redo_listener);

        /* Barre de menu. */
        sous_menu_nouveau.addActionListener(actionslistener);
        sous_menu_ouvrir.addActionListener(actionslistener);
        sous_menu_fermer_editeur.addActionListener(actionslistener);
        sous_menu_enregistrer.addActionListener(actionslistener);
        sous_menu_enregistrer_sous.addActionListener(actionslistener);
        sous_menu_imprimer.addActionListener(actionslistener);
        sous_menu_quitter.addActionListener(actionslistener);
        sous_menu_copier.addActionListener(actionslistener);
        sous_menu_couper.addActionListener(actionslistener);
        sous_menu_coller.addActionListener(actionslistener);
        sous_menu_supprimer.addActionListener(actionslistener);
        sous_menu_recherche.addActionListener(actionslistener);
        sous_menu_plein_ecran.addActionListener(actionslistener);
        sous_menu_en_tete.addActionListener(actionslistener);
        sous_menu_commentaire.addActionListener(actionslistener);
        sous_menu_item_bash.addActionListener(actionslistener);
        sous_menu_item_c.addActionListener(actionslistener);
        sous_menu_item_cpp.addActionListener(actionslistener);
        sous_menu_item_html.addActionListener(actionslistener);
        sous_menu_item_java.addActionListener(actionslistener);
        sous_menu_item_javascript.addActionListener(actionslistener);
        sous_menu_item_php.addActionListener(actionslistener);
        sous_menu_item_python.addActionListener(actionslistener);
        sous_menu_item_ruby.addActionListener(actionslistener);
        sous_menu_item_sql.addActionListener(actionslistener);
        sous_menu_item_scala.addActionListener(actionslistener);
        sous_menu_personnaliser_langage.addActionListener(actionslistener);
        sous_menu_manuel_bash.addActionListener(actionslistener);
        sous_menu_manuel_c.addActionListener(actionslistener);
        sous_menu_manuel_cpp.addActionListener(actionslistener);
        sous_menu_manuel_html.addActionListener(actionslistener);
        sous_menu_manuel_java.addActionListener(actionslistener);
        sous_menu_manuel_javascript.addActionListener(actionslistener);
        sous_menu_manuel_php.addActionListener(actionslistener);
        sous_menu_manuel_python.addActionListener(actionslistener);
        sous_menu_manuel_ruby.addActionListener(actionslistener);
        sous_menu_manuel_scala.addActionListener(actionslistener);
        sous_menu_manuel_sql.addActionListener(actionslistener);
        sous_menu_custom_recherche.addActionListener(actionslistener);
        sous_menu_francais.addActionListener(actionslistener);
        sous_menu_anglais.addActionListener(actionslistener);
        sous_menu_statistiques.addActionListener(actionslistener);
        sous_menu_new_fenetre.addActionListener(actionslistener);
        sous_menu_fermer_fenetre.addActionListener(actionslistener);
        sous_menu_aide.addActionListener(actionslistener);
        sous_menu_site.addActionListener(actionslistener);
        sous_menu_jeditor.addActionListener(actionslistener);

        /* Tool Barre. */
        bout_ouvrir.addActionListener(actionslistener);
        bout_enregistrer.addActionListener(actionslistener);
        bout_new.addActionListener(actionslistener);
        bout_couper.addActionListener(actionslistener);
        bout_copier.addActionListener(actionslistener);
        bout_coller.addActionListener(actionslistener);
        bout_chercher.addActionListener(actionslistener);
        bout_imprimer.addActionListener(actionslistener);

        /* Menu popup. */
        pop_up_couper.addActionListener(actionslistener);
        pop_up_copier.addActionListener(actionslistener);
        pop_up_coller.addActionListener(actionslistener);
        pop_up_supprimer.addActionListener(actionslistener);
        pop_up_tout_selectionner.addActionListener(actionslistener);
        pop_up_commenter.addActionListener(actionslistener);
    }

    /**
     * Méthode permettant d'associer à certains éléments une combinaison de
     * touches "raccourcie".<br />
     * Effectuer cette combinaison permettra d'effectuer l'action associée à
     * cette combinaison.
     */
    private void setAccelerator() {
        /* ctrl + touche. */

        sous_menu_nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        sous_menu_ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        sous_menu_enregistrer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        sous_menu_enregistrer_sous.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_MASK));
        sous_menu_quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK));
        sous_menu_tout_selectionner.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));
        sous_menu_recherche.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK));
        sous_menu_copier.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
        sous_menu_couper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
        sous_menu_coller.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));

        /* alt + touche. */
        sous_menu_fermer_fenetre.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.ALT_MASK));
        sous_menu_aide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.ALT_MASK));
        sous_menu_jeditor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, KeyEvent.ALT_MASK));
    }

    /**
     * Méthode permettant d'associer à certains éléments de la barre de menu une
     * icône.
     */
    private void setIconBarreMenu() {
        sous_menu_nouveau.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/nouveau.png")));
        sous_menu_ouvrir.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/ouvrir.png")));
        sous_menu_fermer_editeur.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/fermer.png")));
        sous_menu_enregistrer.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/enregistrer.png")));
        sous_menu_enregistrer_sous.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/enregistrer_sous.png")));
        sous_menu_quitter.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/quitter.png")));
        sous_menu_imprimer.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/imprimer.png")));
        sous_menu_couper.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/couper.png")));
        sous_menu_copier.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/copier.png")));
        sous_menu_coller.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/coller.png")));
        sous_menu_tout_selectionner.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/tout_selectionner.png")));
        sous_menu_plein_ecran.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/plein_ecran.png")));
        sous_menu_recherche.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/rechercher.png")));
        sous_menu_en_tete.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/presentation.png")));
        sous_menu_commentaire.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/commentaire.png")));
        sous_menu_manuel_bash.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/carre.png")));
        sous_menu_manuel_c.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/carre.png")));
        sous_menu_manuel_cpp.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/carre.png")));
        sous_menu_manuel_html.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/carre.png")));
        sous_menu_manuel_java.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/carre.png")));
        sous_menu_manuel_javascript.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/carre.png")));
        sous_menu_manuel_php.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/carre.png")));
        sous_menu_manuel_python.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/carre.png")));
        sous_menu_manuel_ruby.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/carre.png")));
        sous_menu_manuel_sql.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/carre.png")));
        sous_menu_manuel_scala.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/carre.png")));
        sous_menu_fermer_fenetre.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/fermer.png")));
        sous_menu_aide.setIcon(new ImageIcon(getClass().getResource("/content/images/menu_aide/aide.png")));
        sous_menu_jeditor.setIcon(new ImageIcon(getClass().getResource("/content/images/menu_aide/informations.png")));
    }

    /**
     * Méthode permettant d'associer à certains éléments de la tool barre une
     * icône.
     */
    private void setIconeToolBarre() {
        bout_new.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/nouveau.png")));
        bout_ouvrir.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/ouvrir.png")));
        bout_enregistrer.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/enregistrer.png")));
        bout_couper.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/couper.png")));
        bout_copier.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/copier.png")));
        bout_coller.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/coller.png")));
        bout_chercher.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/rechercher.png")));
        bout_imprimer.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/imprimer.png")));
    }

    /**
     * Méthode permettant d'associer à certains éléments du menu pop-up une
     * icône.
     */
    private void setIconPopuMenu() {
        pop_up_couper.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/couper.png")));
        pop_up_copier.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/copier.png")));
        pop_up_coller.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/coller.png")));
        pop_up_tout_selectionner.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/tout_selectionner.png")));
        pop_up_commenter.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/commentaire.png")));
    }

    /**
     * Méthode permettant d'activer ou de désactiver certains éléments selon ce
     * qui est contenu dans la zone de texte.<br />
     * Par exemple, si la zone de texte ne contient rien, il est inutile
     * d'activer la recherche de texte.
     */
    private void enableOptionsBarreMenu() {
        updateTexteInZoneTexte();

        if (texte_doc.length() == 0) {
            sous_menu_enregistrer.setEnabled(false);
            sous_menu_enregistrer_sous.setEnabled(false);
            sous_menu_copier.setEnabled(false);
            sous_menu_couper.setEnabled(false);
            sous_menu_supprimer.setEnabled(false);
            sous_menu_tout_selectionner.setEnabled(false);
            sous_menu_recherche.setEnabled(false);

            pop_up_couper.setEnabled(false);
            pop_up_copier.setEnabled(false);
            pop_up_commenter.setEnabled(false);
            pop_up_supprimer.setEnabled(false);
            pop_up_tout_selectionner.setEnabled(false);
        } else {
            sous_menu_enregistrer.setEnabled(true);
            sous_menu_enregistrer_sous.setEnabled(true);
            sous_menu_copier.setEnabled(true);
            sous_menu_couper.setEnabled(true);
            sous_menu_supprimer.setEnabled(true);
            sous_menu_tout_selectionner.setEnabled(true);
            sous_menu_recherche.setEnabled(true);

            pop_up_couper.setEnabled(true);
            pop_up_copier.setEnabled(true);
            pop_up_commenter.setEnabled(true);
            pop_up_supprimer.setEnabled(true);
            pop_up_tout_selectionner.setEnabled(true);
        }
    }

    /* Méthodes servant à enregistrer et ouvrir un fichier. */
    /**
     * Méthode permettant d'ouvrir un fichier et de faire les actions
     * nécessaires selon l'extension du fichier.<br />
     * <u><b>Exemple</b></u> : Si le fichier termine par ".c", le programme va
     * changer le langage coloré par le C et rendre cet item sélectionné.
     *
     * @return <b>true</b> si le fichier a bien été ouvert.<br /><b>false</b>
     * sinon.
     */
    private boolean ouvrirFichier() {
        if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            if (filechooser.getSelectedFile().toString().endsWith(".sh")) {
                LanguageHighlight(zone_texte, keywords_bash);
                sous_menu_item_bash.setSelected(true);
            } else if (filechooser.getSelectedFile().toString().endsWith(".c")) {
                LanguageHighlight(zone_texte, keywords_c);
                sous_menu_item_c.setSelected(true);
            } else if (filechooser.getSelectedFile().toString().endsWith(".cpp")) {
                LanguageHighlight(zone_texte, keywords_cpp);
                sous_menu_item_cpp.setSelected(true);
            } else if (filechooser.getSelectedFile().toString().endsWith(".html")) {
                LanguageHighlight(zone_texte, keywords_html);
                sous_menu_item_html.setSelected(true);
            } else if (filechooser.getSelectedFile().toString().endsWith(".java")) {
                LanguageHighlight(zone_texte, keywords_java);
                sous_menu_item_java.setSelected(true);
            } else if (filechooser.getSelectedFile().toString().endsWith(".js")) {
                LanguageHighlight(zone_texte, keywords_javascript);
                sous_menu_item_javascript.setSelected(true);
            } else if (filechooser.getSelectedFile().toString().endsWith(".php")) {
                String[] keywords_php_html;

                keywords_php_html = new String[keywords_php.length + keywords_html.length];
                System.arraycopy(keywords_php, 0, keywords_php_html, 0, keywords_php.length);
                System.arraycopy(keywords_html, 0, keywords_php_html, keywords_php.length, keywords_html.length);

                LanguageHighlight(zone_texte, keywords_php_html);
                sous_menu_item_php.setSelected(true);
            } else if (filechooser.getSelectedFile().toString().endsWith(".py")) {
                LanguageHighlight(zone_texte, keywords_python);
                sous_menu_item_python.setSelected(true);
            } else if (filechooser.getSelectedFile().toString().endsWith(".ru")) {
                LanguageHighlight(zone_texte, keywords_ruby);
                sous_menu_item_ruby.setSelected(true);
            } else if (filechooser.getSelectedFile().toString().endsWith(".scl")) {
                LanguageHighlight(zone_texte, keywords_scala);
                sous_menu_item_scala.setSelected(true);
            } else if (filechooser.getSelectedFile().toString().endsWith(".sql")) {
                LanguageHighlight(zone_texte, keywords_sql);
                sous_menu_item_sql.setSelected(true);
            } else {
                JOptionPane.showMessageDialog(null, "Extension non gérée !", "Erreur", JOptionPane.ERROR_MESSAGE);

                return false;
            }

            file_open = filechooser.getSelectedFile().toString();
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(file_open);
            } catch (FileNotFoundException e) {
                return false;
            }

            BufferedReader br = null;

            try {
                br = new BufferedReader(new InputStreamReader(fis, "UTF-16"));
            } catch (UnsupportedEncodingException e) {
                return false;
            }

            try {
                int n;

                while ((n = fis.available()) > 0) {
                    char[] b = new char[n];
                    int result = br.read(b);
                    if (result == -1) {
                        break;
                    }
                    zone_texte.setText(new String(b));
                }

            } catch (Exception err) {
                return false;
            }
        }

        return true;
    }

    /**
     * Méthode permettant d'enregistrer un fichier selon un encodage voulu.
     *
     * @param encodage L'encodage voulu pour enregistrer le fichier.
     * @return <b>true</b> si le fichier a bien été
     * enregistré.<br /><b>false</b> sinon.
     */
    private boolean enregistrerFichier(String encodage) {
        if (filechooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            file_open = filechooser.getSelectedFile().toString();
            extension_current_file = "." + filechooser.getFileFilter().getDescription().substring(9);

            try {
                PrintWriter print = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file_open + extension_current_file), encodage)));
                print.write(texte_doc);
                print.close();
            } catch (IOException er) {
                return false;
            }

            return true;
        }

        return false;
    }

    /**
     * Méthode permettant d'enregistrer un fichier selon un encodage voulu et
     * une adresse, si elle est déjà connue.
     *
     * @param encodage L'encodage voulu pour enregistrer le fichier.
     * @param adresse L'adresse du fichier.
     *
     * @return <b>true</b> si le fichier a bien été
     * enregistré.<br /><b>false</b> sinon.
     */
    private boolean enregistrerFichier(String encodage, String adresse) {
        try {
            PrintWriter print = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(adresse + extension_current_file), encodage)));
            print.write(texte_doc);
            print.close();
        } catch (IOException er) {
            return false;
        }

        return true;
    }

    /**
     * Méthode permettant de tester si le document doit être enregistré ou non.
     *
     * @param action L'action à faire une fois les tests effectués.
     */
    private void testEnregistrerFichier(String action) {
        if ((texte_doc.length() == 0) && (file_open == null)) {
            if (action.equals("fermer")) {
                dispose();
            } else if (action.equals("quitter")) {
                System.exit(0);
            }
        } else {
            int reponse = JOptionPane.showConfirmDialog(null, "Enregistrer les modifications ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (reponse == JOptionPane.NO_OPTION) {
                if (action.equals("fermer")) {
                    dispose();
                } else if (action.equals("quitter")) {
                    System.exit(0);
                }
            } else if (reponse == JOptionPane.YES_OPTION) {
                enregistrerFichier("UTF-16");
            }
        }
    }

    /**
     * Méthode permettant de tester si un document doit être enregistré
     * lorsqu'un utilisateur veut ouvrir un nouveau fichier.
     */
    private void ouvrirFichierEtTestEnregistrerFichier() {
        if ((texte_doc.length() > 0) && (file_open == null)) {
            int choix = JOptionPane.showConfirmDialog(null, "Enregistrer les modifications ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choix == JOptionPane.OK_OPTION) {
                enregistrerFichier("UTF-16");
                ouvrirFichier();
            } else if (choix == JOptionPane.NO_OPTION) {
                ouvrirFichier();
            }
        } else if ((texte_doc.length() == 0) && (file_open == null)) {
            ouvrirFichier();
        } else if ((texte_doc.length() > 0) && (file_open != null)) {
            int choix = JOptionPane.showConfirmDialog(null, "Enregistrer les modifications ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choix == JOptionPane.OK_OPTION) {
                enregistrerFichier("UTF-16", file_open);
                ouvrirFichier();
            } else if (choix == JOptionPane.NO_OPTION) {
                ouvrirFichier();
            }
        }
    }

    /* Méthodes permettant de surligner un texte. */
    /**
     * Méthode permettant de surligner un texte dans un composant textuel.
     *
     * @param textComp Le composant où l'on doit ajouter des éléments surlignés.
     * @param pattern Le texte à surligner.
     */
    private boolean highlight(JTextComponent textComp, String pattern) {
        boolean hasHighLight = false;
        removeHighlights(textComp);

        try {
            Highlighter hilite = textComp.getHighlighter();
            Document doc = textComp.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;

            while ((pos = text.indexOf(pattern, pos)) >= 0) {
                hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
                pos += pattern.length();
                hasHighLight = true;
            }

            return hasHighLight;
        } catch (BadLocationException e) {
            return hasHighLight;
        }
    }

    /**
     * Méthode permettant de supprimer les éléments surlignés dans un composant
     * textuel.
     *
     * @param textComp Le composant où l'on doit supprimer les éléments
     * surlignés.
     */
    private void removeHighlights(JTextComponent textComp) {
        Highlighter hilite = textComp.getHighlighter();
        Highlighter.Highlight[] hilites = hilite.getHighlights();

        for (int i = 0; i < hilites.length; i++) {
            if (hilites[i].getPainter() instanceof MyHighlightPainter) {
                hilite.removeHighlight(hilites[i]);
            }
        }
    }

    private class MyHighlightPainter extends DefaultHighlightPainter {

        public MyHighlightPainter(Color color) {
            super(color);
        }
    }

    /* Méthodes de coloration syntaxique. */
    /**
     * Méthode permettant de récupérer la liste des mots-clés d'un langage sous
     * la forme d'un tableau de chaines de caractères.
     *
     * @param language Le language choisi.
     * @return La liste des mots-clés du langage choisi.
     */
    private String[] initKeyWords(String language) {
        try {
            String[] resultat;

            InputStream ips = Editeur.this.getClass().getResourceAsStream("/content/keywords/" + language);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            int indice = 0;

            while ((ligne = br.readLine()) != null) {
                indice++;
            }

            br.close();

            InputStream ips2 = Editeur.this.getClass().getResourceAsStream("/content/keywords/" + language);
            InputStreamReader ipsr2 = new InputStreamReader(ips2);
            BufferedReader br2 = new BufferedReader(ipsr2);

            resultat = new String[indice];
            indice = 0;

            while ((ligne = br2.readLine()) != null) {
                resultat[indice++] = ligne;
            }

            br2.close();

            return resultat;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Méthode permettant d'initialiser les tableaux de mot-clés des langages.
     */
    private void initKeyWords() {
        keywords_bash = initKeyWords("bash");
        keywords_c = initKeyWords("c");
        keywords_cpp = initKeyWords("cpp");
        keywords_html = initKeyWords("html");
        keywords_java = initKeyWords("java");
        keywords_javascript = initKeyWords("javascript");
        keywords_php = initKeyWords("php");
        keywords_python = initKeyWords("python");
        keywords_ruby = initKeyWords("ruby");
        keywords_scala = initKeyWords("scala");
        keywords_sql = initKeyWords("sql");
    }

    /**
     * Méthode permettant de colorer des mots-clés dans une zone de texte.
     *
     * @param c La zone de texte où les mots-clés doivent être colorés.
     * @param keywords_language Les mots-clés à colorer.
     */
    private void LanguageHighlight(JTextPane c, String[] keywords_language) {
        String text = zone_texte.getText().replaceAll("\n", "");
        final StyledDocument doc = c.getStyledDocument();

        final MutableAttributeSet normal = new SimpleAttributeSet();
        StyleConstants.setForeground(normal, Color.BLACK);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                doc.setCharacterAttributes(0, doc.getLength(), normal, true);
            }
        });

        for (String strToHL : keywords_language) {
            Pattern p = Pattern.compile("\\b*" + strToHL + "\\b*", Pattern.MULTILINE); // KeyWords
            Pattern p2 = Pattern.compile("\".*\"", Pattern.MULTILINE); // Guillemets
            Pattern p3 = Pattern.compile("\'.*\'", Pattern.MULTILINE); // Quotes

            Pattern p4 = null; // Comments

            if (sous_menu_item_c.isSelected() || sous_menu_item_cpp.isSelected() || sous_menu_item_java.isSelected() || sous_menu_item_javascript.isSelected() || sous_menu_item_scala.isSelected() || sous_menu_item_php.isSelected()) {
                p4 = Pattern.compile("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)", Pattern.MULTILINE);
            } else if (sous_menu_item_bash.isSelected() || sous_menu_item_python.isSelected() || sous_menu_item_ruby.isSelected()) {
                p4 = Pattern.compile("^#.*", Pattern.MULTILINE);
            } else if (sous_menu_item_sql.isSelected()) {
                p4 = Pattern.compile("^--.*", Pattern.MULTILINE);
            } else if (sous_menu_item_html.isSelected()) {
                p4 = Pattern.compile("^<!--.*-->", Pattern.MULTILINE);
            }

            Matcher m = p.matcher(text);
            Matcher m2 = p2.matcher(text);
            Matcher m3 = p3.matcher(text);
            Matcher m4 = p4.matcher(text);

            while (m.find() == true) {
                MutableAttributeSet attri = new SimpleAttributeSet();
                StyleConstants.setForeground(attri, Color.decode("#002FA7"));
                StyleConstants.setBold(attri, true);

                final int start = m.start();
                final int end = m.end();
                final int length = end - start;
                final MutableAttributeSet style = attri;

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        doc.setCharacterAttributes(start, length, style, true);
                    }
                });
            }

            while (m2.find() == true) {
                MutableAttributeSet attri = new SimpleAttributeSet();
                StyleConstants.setForeground(attri, Color.GRAY);

                final int start = m2.start();
                final int end = m2.end();
                final int length = end - start;
                final MutableAttributeSet style = attri;

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        doc.setCharacterAttributes(start, length, style, true);
                    }
                });
            }

            while (m3.find() == true) {
                MutableAttributeSet attri = new SimpleAttributeSet();
                StyleConstants.setForeground(attri, Color.GRAY);

                final int start = m3.start();
                final int end = m3.end();
                final int length = end - start;
                final MutableAttributeSet style = attri;

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        doc.setCharacterAttributes(start, length, style, true);
                    }
                });
            }

            while (m4.find() == true) {
                MutableAttributeSet attri = new SimpleAttributeSet();
                StyleConstants.setForeground(attri, Color.decode("#7BA05B"));

                final int start = m4.start();
                final int end = m4.end();
                final int length = end - start;
                final MutableAttributeSet style = attri;

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        doc.setCharacterAttributes(start, length, style, true);
                    }
                });
            }
        }
    }

    /* Autres. */
    /**
     * Méthode permettant d'initialiser les différents menu/tool barre/... avec
     * la langue choisie par l'utilisateur.
     */
    private void initStringWithRightLanguage() {
        /* Barre de menu. */

        menu_fichier.setText(traducteur.getString("menu.fichier"));
        sous_menu_nouveau.setText(traducteur.getString("sous_menu_nouveau"));
        sous_menu_ouvrir.setText(traducteur.getString("sous_menu_ouvrir"));
        sous_menu_fermer_editeur.setText(traducteur.getString("sous_menu_fermer_editeur"));
        sous_menu_enregistrer.setText(traducteur.getString("sous_menu_enregistrer"));
        sous_menu_enregistrer_sous.setText(traducteur.getString("sous_menu_enregistrer_sous"));
        sous_menu_imprimer.setText(traducteur.getString("sous_menu_imprimer"));
        sous_menu_quitter.setText(traducteur.getString("sous_menu_quitter"));
        menu_edition.setText(traducteur.getString("menu.edition"));

        ((AbstractButton) menu_edition.getMenuComponent(0)).setText(traducteur.getString("annuler"));
        ((AbstractButton) menu_edition.getMenuComponent(1)).setText(traducteur.getString("refaire"));

        sous_menu_copier.setText(traducteur.getString("sous_menu_copier"));
        sous_menu_couper.setText(traducteur.getString("sous_menu_couper"));
        sous_menu_coller.setText(traducteur.getString("sous_menu_coller"));
        sous_menu_supprimer.setText(traducteur.getString("sous_menu_supprimer"));
        sous_menu_tout_selectionner.setText(traducteur.getString("sous_menu_tout_selectionner"));
        sous_menu_recherche.setText(traducteur.getString("sous_menu_rechercher"));
        sous_menu_plein_ecran.setText(traducteur.getString("sous_menu_plein_ecran"));
        menu_insertion.setText(traducteur.getString("menu.insertion"));
        sous_menu_en_tete.setText(traducteur.getString("sous_menu_en_tete"));
        sous_menu_commentaire.setText(traducteur.getString("sous_menu_commentaire"));
        menu_langage.setText(traducteur.getString("menu_langage"));
        sous_menu_personnaliser_langage.setText(traducteur.getString("sous_menu_personnaliser_langage"));
        menu_manuel_langage.setText(traducteur.getString("menu_manuel_langage"));
        sous_menu_custom_recherche.setText(traducteur.getString("sous_menu_custom_recherche"));
        menu_fenetre.setText(traducteur.getString("menu.fenetre"));
        sous_menu_new_fenetre.setText(traducteur.getString("sous_menu_new_fenetre"));
        sous_menu_fermer_fenetre.setText(traducteur.getString("sous_menu_fermer_fenetre"));
        menu_langages.setText(traducteur.getString("menu.langages"));
        sous_menu_francais.setText(traducteur.getString("sous_menu_francais"));
        sous_menu_anglais.setText(traducteur.getString("sous_menu_anglais"));
        sous_menu_statistiques.setText(traducteur.getString("sous_menu_statistiques"));
        menu_aide.setText(traducteur.getString("menu.aide"));
        sous_menu_aide.setText(traducteur.getString("sous_menu_aide"));
        sous_menu_site.setText(traducteur.getString("sous_menu_site"));
        sous_menu_jeditor.setText(traducteur.getString("sous_menu_jeditor"));

        /* Popup menu. */
        pop_up_couper.setText(traducteur.getString("pop_up_couper"));
        pop_up_copier.setText(traducteur.getString("pop_up_copier"));
        pop_up_coller.setText(traducteur.getString("pop_up_coller"));
        pop_up_supprimer.setText(traducteur.getString("pop_up_supprimer"));
        pop_up_tout_selectionner.setText(traducteur.getString("pop_up_tout_selectionner"));
        pop_up_commenter.setText(traducteur.getString("pop_up_commenter"));

        /* Tool barre. */
        bout_new.setToolTipText(traducteur.getString("bout_new"));
        bout_ouvrir.setToolTipText(traducteur.getString("bout_ouvrir"));
        bout_enregistrer.setToolTipText(traducteur.getString("bout_enregistrer"));
        bout_chercher.setToolTipText(traducteur.getString("bout_chercher"));
        bout_imprimer.setToolTipText(traducteur.getString("bout_imprimer"));
        bout_copier.setToolTipText(traducteur.getString("bout_copier"));
        bout_coller.setToolTipText(traducteur.getString("bout_coller"));
        bout_couper.setToolTipText(traducteur.getString("bout_couper"));
    }

    /**
     * Méthode permettant de créer un nouveau document.<br />
     * Si le document actuel n'est pas vide, il est proposé de l'enregistrer.
     */
    private void nouveauDocument() {
        if (texte_doc.length() > 0) {
            int choix = JOptionPane.showConfirmDialog(null, "Enregistrer les modifications ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choix == JOptionPane.OK_OPTION) {
                enregistrerFichier("UTF-16");
                zone_texte.setText("");
                file_open = null;
            } else if (choix == JOptionPane.NO_OPTION) {
                zone_texte.setText("");
            }
        }
    }

    /**
     * Méthode permettant de mettre à jour la variable représentant le texte du
     * document.
     *
     * @return <b>true</b> si le texte a bien été mis à jour.<br /><b>false</b>
     * sinon.
     */
    private boolean updateTexteInZoneTexte() {
        try {
            texte_doc = zone_texte.getText(0, zone_texte.getDocument().getLength());
        } catch (BadLocationException e) {
            return false;
        }

        return true;
    }

    /**
     * Méthode permettant d'imprimer le document actuel.
     */
    private void imprimerDocument() {
        if (JOptionPane.showConfirmDialog(null, "Imprimer le fichier ?", "Impression", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                zone_texte.print();
            } catch (PrinterException e1) {
                return;
            }
        }
    }

    /**
     * Méthode permettant de rechercher ou remplacer un texte dans le
     * document.<br />
     * Si le texte à rechercher est trouvé, il est surligné.<br />
     * Et si le texte à remplacer est trouvé, il est aussi surligné.
     */
    private void rechercherEtRemplacerTexte() {
        updateTexteInZoneTexte();

        final RechercheTexte recherche_texte;

        if (sous_menu_francais.isSelected()) {
            recherche_texte = new RechercheTexte("francais");
        } else {
            recherche_texte = new RechercheTexte("anglais");
        }

        recherche_texte.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (recherche_texte.isRechercherSelected()) {
                    if (!highlight(zone_texte, recherche_texte.getTexteARechercher())) {
                        JOptionPane.showMessageDialog(null, "Texte non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        rechercherEtRemplacerTexte();
                    }
                } else if (recherche_texte.isRemplacerSelected()) {
                    String texte_a_chercher = recherche_texte.getTexteARechercher();
                    String remplacer_par = recherche_texte.getTexteARemplacer();

                    zone_texte.setText(zone_texte.getText().replaceAll(texte_a_chercher, remplacer_par));

                    if (!highlight(zone_texte, remplacer_par)) {
                        JOptionPane.showMessageDialog(null, "Texte non trouvé. Rien n'a été remplacé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        rechercherEtRemplacerTexte();
                    }
                }
            }
        });
    }

    /**
     * Méthode permettant de compter le nombre de retour à la ligne dans un
     * texte.
     *
     * @param text Le texte à étudier.
     * @return Le nombre de retour à la ligne dans "text".
     */
    private int compterRetourLigne(String text) {
        Matcher matcher = Pattern.compile("\n").matcher(text);
        int occur = 0;

        while (matcher.find()) {
            occur++;
        }

        return (occur + 1);
    }

    /**
     * Méthode permettant d'ouvrir une URL via le navigateur par défaut de
     * l'utilisateur.
     *
     * @param url L'URL à visiter.
     * @return <b>true</b> si l'URL a correctement été ouverte dans le
     * navigateur par défaut.<br /><b>false</b> sinon.
     */
    private boolean ouvrirUrl(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));

            return true;
        } catch (URISyntaxException | IOException e1) {
            return false;
        }
    }

    /**
     * Méthode permettant de modifier le texte du document actuel.
     *
     * @param texte Le nouveau texte du document.
     */
    public void setText(String texte) {
        zone_texte.setText(texte);
    }

    /**
     * Méthode permettant de lire un fichier qui se situe dans le ".jar" du
     * logiciel.
     *
     * @param path_file Le chemin du fichier à lire. Le fichier doit se situer
     * dans le ".jar".
     * @return Le contenu du fichier si la lecture s'est bien
     * passée.<br /><b>-1</b> sinon.
     */
    private String lireFichier(String path_file) {
        String contenu_fichier = "";

        try {
            int n;
            InputStream fis = Editeur.this.getClass().getResourceAsStream(path_file);

            while ((n = fis.available()) > 0) {
                byte[] b = new byte[n];
                int result = fis.read(b);
                if (result == -1) {
                    break;
                }
                contenu_fichier = new String(b);
            }
        } catch (Exception err) {
            return "-1";
        }

        return contenu_fichier;
    }

    /* Listener. */
    
    /**
     * Classe interne représentant un action listener.<br />
     * Ici, le listener permet de détecter un click sur un objet.
     *
     * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
     */
    private class ActionsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            zone_texte.requestFocus();

            if ((e.getSource() == sous_menu_nouveau) || (e.getSource() == bout_new)) {
                nouveauDocument();
            } else if ((e.getSource() == sous_menu_ouvrir) || (e.getSource() == bout_ouvrir)) {
                ouvrirFichierEtTestEnregistrerFichier();
            } else if ((e.getSource() == bout_enregistrer || e.getSource() == sous_menu_enregistrer) && (file_open != null)) {
                enregistrerFichier("UTF-16", file_open);
            } else if ((e.getSource() == bout_enregistrer || e.getSource() == sous_menu_enregistrer || e.getSource() == sous_menu_enregistrer_sous) && (file_open == null)) {
                enregistrerFichier("UTF-16");
            } else if (e.getSource() == sous_menu_fermer_editeur) {
                testEnregistrerFichier("fermer");
            } else if ((e.getSource() == sous_menu_imprimer) || (e.getSource() == bout_imprimer)) {
                imprimerDocument();
            } else if (e.getSource() == sous_menu_quitter) {
                testEnregistrerFichier("quitter");
            } else if ((e.getSource() == sous_menu_tout_selectionner) || (e.getSource() == pop_up_tout_selectionner)) {
                zone_texte.selectAll();
            } else if ((e.getSource() == pop_up_couper) || (e.getSource() == bout_couper) || (e.getSource() == sous_menu_couper)) {
                if (zone_texte.getSelectedText() == null); else {
                    zone_texte.cut();
                }
            } else if ((e.getSource() == pop_up_copier) || (e.getSource() == bout_copier) || (e.getSource() == sous_menu_copier)) {
                if (zone_texte.getSelectedText() == null); else {
                    zone_texte.copy();
                }
            } else if ((e.getSource() == pop_up_coller) || (e.getSource() == bout_coller) || (e.getSource() == sous_menu_coller)) {
                zone_texte.paste();
            } else if ((e.getSource() == sous_menu_supprimer) || (e.getSource() == pop_up_supprimer)) {
                if (zone_texte.getSelectedText() == null); else {
                    zone_texte.replaceSelection("");
                }
            } else if ((e.getSource() == bout_chercher) || (e.getSource() == sous_menu_recherche)) {
                rechercherEtRemplacerTexte();
            } else if (e.getSource() == sous_menu_plein_ecran) {
                if (device.isFullScreenSupported()) {
                    device.setFullScreenWindow(Editeur.this);
                    Editeur.this.add(lab_plein_ecran, BorderLayout.SOUTH);
                } else {
                    JOptionPane.showMessageDialog(null, "Le mode plein ecran n'est pas disponible", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == sous_menu_en_tete) {
                String texte = null;

                if (sous_menu_item_c.isSelected() || sous_menu_item_cpp.isSelected() || sous_menu_item_javascript.isSelected() || sous_menu_item_java.isSelected() || sous_menu_item_scala.isSelected() || sous_menu_item_php.isSelected()) {
                    texte = "/* - page.extension" + "\n" + "-> Par X" + "\n" + "-> Description" + "\n" + "-> Dernière modification */" + "\n";
                } else if (sous_menu_item_bash.isSelected() || sous_menu_item_python.isSelected() || sous_menu_item_ruby.isSelected()) {
                    texte = "# - page.extension" + "\n" + "# -> Par X" + "\n" + "# -> Description" + "\n" + "# -> Dernière modification" + "\n";
                } else if (sous_menu_item_sql.isSelected()) {
                    texte = "-- - page.extension" + "\n" + "-- -> Par X" + "\n" + "-- -> Description" + "\n" + "-- -> Dernière modification" + "\n";
                } else if (sous_menu_item_html.isSelected()) {
                    texte = "<!-- - page.extension -->" + "\n" + "<!-- -> Par X -->" + "\n" + "<!-- -> Description -->" + "\n" + "<!-- -> Dernière modification -->" + "\n";
                }

                try {
                    Document document = zone_texte.getDocument();
                    document.insertString(0, texte, null);
                } catch (BadLocationException a) {
                    return;
                }
            } else if ((e.getSource() == sous_menu_commentaire) || (e.getSource() == pop_up_commenter)) {
                String commentaire = zone_texte.getSelectedText();

                if (commentaire == null) {
                    JOptionPane.showMessageDialog(null, "Aucun texte selectionné !", "Erreur", JOptionPane.ERROR_MESSAGE);

                    return;
                }

                String temp = commentaire;

                if (commentaire.length() > 0) {
                    if (sous_menu_item_c.isSelected() || sous_menu_item_cpp.isSelected() || sous_menu_item_java.isSelected() || sous_menu_item_javascript.isSelected() || sous_menu_item_scala.isSelected() || sous_menu_item_php.isSelected()) {
                        commentaire = "/* " + commentaire + " */";
                    } else if (sous_menu_item_bash.isSelected() || sous_menu_item_python.isSelected() || sous_menu_item_ruby.isSelected()) {
                        if (compterRetourLigne(commentaire) > 1) {
                            StringTokenizer commentaire_eclate = new StringTokenizer(commentaire, "\n");
                            String[] tab = new String[compterRetourLigne(commentaire)];
                            commentaire = "";
                            while (commentaire_eclate.hasMoreTokens()) {
                                for (int i = 0; i < compterRetourLigne(temp); i++) {
                                    tab[i] = "# " + commentaire_eclate.nextToken() + "\n";
                                    commentaire = commentaire + tab[i];
                                }
                            }
                        } else {
                            commentaire = "# " + commentaire;
                        }
                    } else if (sous_menu_item_sql.isSelected()) {
                        commentaire = "-- " + commentaire;
                    } else if (sous_menu_item_html.isSelected()) {
                        commentaire = "<!-- " + commentaire + " -->";
                    }

                    zone_texte.replaceSelection(commentaire);
                }
            } else if (e.getSource() == sous_menu_item_bash) {
                LanguageHighlight(zone_texte, keywords_bash);
            } else if (e.getSource() == sous_menu_item_c) {
                LanguageHighlight(zone_texte, keywords_c);
            } else if (e.getSource() == sous_menu_item_cpp) {
                LanguageHighlight(zone_texte, keywords_cpp);
            } else if (e.getSource() == sous_menu_item_html) {
                LanguageHighlight(zone_texte, keywords_html);
            } else if (e.getSource() == sous_menu_item_java) {
                LanguageHighlight(zone_texte, keywords_java);
            } else if (e.getSource() == sous_menu_item_javascript) {
                LanguageHighlight(zone_texte, keywords_javascript);
            } else if (e.getSource() == sous_menu_item_php) {
                LanguageHighlight(zone_texte, keywords_php);
            } else if (e.getSource() == sous_menu_item_python) {
                LanguageHighlight(zone_texte, keywords_python);
            } else if (e.getSource() == sous_menu_item_ruby) {
                LanguageHighlight(zone_texte, keywords_ruby);
            } else if (e.getSource() == sous_menu_item_scala) {
                LanguageHighlight(zone_texte, keywords_scala);
            } else if (e.getSource() == sous_menu_item_sql) {
                LanguageHighlight(zone_texte, keywords_sql);
            } else if (e.getSource() == sous_menu_personnaliser_langage) {
                final LangageChooser langage_chooser = new LangageChooser();

                langage_chooser.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent arg0) {
                        if (langage_chooser.isOKSelected()) {
                            langage_choisi = langage_chooser.getSelectedItem();

                            sous_menu_item_bash.setVisible(false);
                            sous_menu_item_c.setVisible(false);
                            sous_menu_item_cpp.setVisible(false);
                            sous_menu_item_html.setVisible(false);
                            sous_menu_item_java.setVisible(false);
                            sous_menu_item_javascript.setVisible(false);
                            sous_menu_item_php.setVisible(false);
                            sous_menu_item_python.setVisible(false);
                            sous_menu_item_ruby.setVisible(false);
                            sous_menu_item_sql.setVisible(false);
                            sous_menu_item_scala.setVisible(false);

                            for (int j = 0; j < langage_choisi.length; j++) {
                                if (langage_choisi[j].equals("bash")) {
                                    sous_menu_item_bash.setVisible(true);
                                } else if (langage_choisi[j].equals("c")) {
                                    sous_menu_item_c.setVisible(true);
                                } else if (langage_choisi[j].equals("cpp")) {
                                    sous_menu_item_cpp.setVisible(true);
                                } else if (langage_choisi[j].equals("html")) {
                                    sous_menu_item_html.setVisible(true);
                                } else if (langage_choisi[j].equals("java")) {
                                    sous_menu_item_java.setVisible(true);
                                } else if (langage_choisi[j].equals("javascript")) {
                                    sous_menu_item_javascript.setVisible(true);
                                } else if (langage_choisi[j].equals("php")) {
                                    sous_menu_item_php.setVisible(true);
                                } else if (langage_choisi[j].equals("python")) {
                                    sous_menu_item_python.setVisible(true);
                                } else if (langage_choisi[j].equals("ruby")) {
                                    sous_menu_item_ruby.setVisible(true);
                                } else if (langage_choisi[j].equals("sql")) {
                                    sous_menu_item_sql.setVisible(true);
                                } else if (langage_choisi[j].equals("scala")) {
                                    sous_menu_item_scala.setVisible(true);
                                }
                            }
                        }
                    }
                });

                String[] langages_visibles = new String[11];
                int compteur = 0;

                if (sous_menu_item_bash.isVisible()) {
                    langages_visibles[compteur++] = "Bash";
                }
                if (sous_menu_item_c.isVisible()) {
                    langages_visibles[compteur++] = "C";
                }
                if (sous_menu_item_cpp.isVisible()) {
                    langages_visibles[compteur++] = "C++";
                }
                if (sous_menu_item_html.isVisible()) {
                    langages_visibles[compteur++] = "HTML";
                }
                if (sous_menu_item_java.isVisible()) {
                    langages_visibles[compteur++] = "Java";
                }
                if (sous_menu_item_javascript.isVisible()) {
                    langages_visibles[compteur++] = "Javascript";
                }
                if (sous_menu_item_php.isVisible()) {
                    langages_visibles[compteur++] = "PHP";
                }
                if (sous_menu_item_python.isVisible()) {
                    langages_visibles[compteur++] = "Python";
                }
                if (sous_menu_item_ruby.isVisible()) {
                    langages_visibles[compteur++] = "Ruby";
                }
                if (sous_menu_item_sql.isVisible()) {
                    langages_visibles[compteur++] = "SQL";
                }
                if (sous_menu_item_scala.isVisible()) {
                    langages_visibles[compteur++] = "Scala";
                }

                langage_chooser.setSelectedLangages(langages_visibles);
            } else if (e.getSource() == sous_menu_manuel_bash) {
                ouvrirUrl(lien_bash);
            } else if (e.getSource() == sous_menu_manuel_c) {
                ouvrirUrl(lien_c);
            } else if (e.getSource() == sous_menu_manuel_cpp) {
                ouvrirUrl(lien_cpp);
            } else if (e.getSource() == sous_menu_manuel_html) {
                ouvrirUrl(lien_html);
            } else if (e.getSource() == sous_menu_manuel_java) {
                ouvrirUrl(lien_java);
            } else if (e.getSource() == sous_menu_manuel_javascript) {
                ouvrirUrl(lien_javascript);
            } else if (e.getSource() == sous_menu_manuel_php) {
                ouvrirUrl(lien_php);
            } else if (e.getSource() == sous_menu_manuel_python) {
                ouvrirUrl(lien_python);
            } else if (e.getSource() == sous_menu_manuel_ruby) {
                ouvrirUrl(lien_ruby);
            } else if (e.getSource() == sous_menu_manuel_scala) {
                ouvrirUrl(lien_scala);
            } else if (e.getSource() == sous_menu_manuel_sql) {
                ouvrirUrl(lien_sql);
            } else if (e.getSource() == sous_menu_custom_recherche) {
                new CustomRecherche();
            } else if (e.getSource() == sous_menu_francais) {
                traducteur = ResourceBundle.getBundle("textes/interface/texte", francais);
                initStringWithRightLanguage();
            } else if (e.getSource() == sous_menu_anglais) {
                traducteur = ResourceBundle.getBundle("textes/interface/texte", anglais);
                initStringWithRightLanguage();
            } else if (e.getSource() == sous_menu_statistiques) {
                if (sous_menu_francais.isSelected()) {
                    new Statistiques("Francais", zone_texte.getText());
                } else {
                    new Statistiques("Anglais", zone_texte.getText());
                }
            } else if (e.getSource() == sous_menu_new_fenetre) {
                new Editeur();
            } else if (e.getSource() == sous_menu_fermer_fenetre) {
                testEnregistrerFichier("fermer");
            } else if (e.getSource() == sous_menu_aide) {
                if (sous_menu_francais.isSelected()) {
                    new Aide(lireFichier("/textes/raccourcis/editeur_p/raccourcis"));
                } else {
                    new Aide(lireFichier("/textes/raccourcis/editeur_p/raccourcis_en"));
                }
            } else if (e.getSource() == sous_menu_site) {
                ouvrirUrl(Constantes.URL_SITE);
            } else if (e.getSource() == sous_menu_jeditor) {
                if (sous_menu_francais.isSelected()) {
                    new JEditor("francais");
                } else {
                    new JEditor("anglais");
                }
            }
        }
    }
}
