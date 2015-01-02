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
package org.jc.ui.editeur.standard;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.text.*;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.rtf.*;
import javax.swing.undo.*;
import org.jc.Constantes;
import org.jc.ui.UndoRedo;
import org.jc.ui.frames.Aide;
import org.jc.ui.frames.JEditor;
import org.jc.ui.frames.RechercheTexte;
import org.jc.ui.frames.Statistiques;

/**
 * Classe représentant la fenêtre principale de l'éditeur standard.
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 */
public class Editeur extends JFrame {
    /* Tool barres. */

    private final JPanel panel_tool_barre = new JPanel();

    private final JToolBar tool_barre_haut = new JToolBar();
    private final JButton bout_new = new JButton();
    private final JButton bout_ouvrir = new JButton();
    private final JButton bout_enregistrer = new JButton();
    private final JButton bout_mail = new JButton();
    private final JButton bout_chercher = new JButton();
    private final JButton bout_imprimer = new JButton();
    private final JButton bout_couper = new JButton();
    private final JButton bout_copier = new JButton();
    private final JButton bout_coller = new JButton();

    private final JToolBar tool_barre_bas = new JToolBar();
    private final JButton bout_gauche = new JButton();
    private final JButton bout_centre = new JButton();
    private final JButton bout_droite = new JButton();
    private final JButton bout_justifie = new JButton();
    private final JButton bout_gras = new JButton();
    private final JButton bout_italique = new JButton();
    private final JButton bout_souligne = new JButton();
    private final JButton bout_couleur = new JButton();
    private final JButton bout_surlignage = new JButton();
    private final JComboBox box_taille = new JComboBox();
    private final JComboBox box_police = new JComboBox();

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
    private final JMenuItem sous_menu_recherche_globale = new JMenuItem();
    private final JMenuItem sous_menu_plein_ecran = new JMenuItem();
    private final JMenu menu_insertion = new JMenu();
    private final JMenuItem sous_menu_date = new JMenuItem();
    private final JMenuItem sous_menu_heure = new JMenuItem();
    private final JMenu menu_format = new JMenu();
    private final JMenuItem sous_menu_formatage_defaut = new JMenuItem();
    private final JMenu menu_modifier_casse = new JMenu();
    private final JMenuItem sous_menu_majuscule = new JMenuItem();
    private final JMenuItem sous_menu_minuscule = new JMenuItem();
    private final JMenu menu_alignement = new JMenu();
    private final JMenuItem sous_menu_alignement_gauche = new JMenuItem();
    private final JMenuItem sous_menu_alignement_centre = new JMenuItem();
    private final JMenuItem sous_menu_alignement_droite = new JMenuItem();
    private final JMenuItem sous_menu_alignement_justifie = new JMenuItem();
    private final JMenuItem sous_menu_police = new JMenuItem();
    private final JMenuItem sous_menu_taille = new JMenuItem();
    private final JMenu menu_style = new JMenu();
    private final JMenuItem sous_menu_normal = new JMenuItem();
    private final JMenuItem sous_menu_gras = new JMenuItem();
    private final JMenuItem sous_menu_italique = new JMenuItem();
    private final JMenuItem sous_menu_souligne = new JMenuItem();
    private final JMenuItem sous_menu_barre = new JMenuItem();
    private final JMenuItem sous_menu_indice = new JMenuItem();
    private final JMenuItem sous_menu_exposant = new JMenuItem();
    private final JMenuItem sous_menu_style_personnalise = new JMenuItem();
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
    private final JPopupMenu pop_up_menu = new JPopupMenu("Menu");
    private final JMenuItem pop_up_formatage_defaut = new JMenuItem();
    private final JMenu pop_up_style = new JMenu();
    private final JMenuItem pop_up_normal = new JMenuItem();
    private final JMenuItem pop_up_gras = new JMenuItem();
    private final JMenuItem pop_up_italique = new JMenuItem();
    private final JMenuItem pop_up_souligne = new JMenuItem();
    private final JMenuItem pop_up_barre = new JMenuItem();
    private final JMenuItem pop_up_exposant = new JMenuItem();
    private final JMenuItem pop_up_indice = new JMenuItem();
    private final JMenuItem pop_up_taille = new JMenuItem();
    private final JMenuItem pop_up_police = new JMenuItem();
    private final JMenu pop_up_alignement = new JMenu();
    private final JMenuItem pop_up_gauche = new JMenuItem();
    private final JMenuItem pop_up_centre = new JMenuItem();
    private final JMenuItem pop_up_droite = new JMenuItem();
    private final JMenuItem pop_up_justifie = new JMenuItem();
    private final JMenuItem pop_up_coller = new JMenuItem();

    /* Autres éléments graphiques. */
    private final JTextPane zone_texte = new JTextPane();
    private final JScrollPane barre_scroll = new JScrollPane(zone_texte);
    private final JLabel lab_plein_ecran = new JLabel("<html><font size=\"6\"><strong>Appuyez sur ECHAP pour quitter le mode plein écran.</strong></font></html>");

    /* Listeners. */
    private final ActionsListener actionslistener = new ActionsListener();
    private final UndoManager undo_redo_listener = new UndoManager();

    /* Constantes. */
    private final String[] liste_tailles = {"4", "6", "8", "10", "12", "13", "14", "16", "18", "20", "22", "24", "26", "28", "30", "32", "34", "36", "40", "44", "48", "54", "60", "66", "72", "80", "88", "96"};
    private final HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.CYAN);
    private final DefaultListCellRenderer center = new DefaultListCellRenderer();

    /* Sélecteurs de fichiers. */
    private final FileNameExtensionFilter ext_rtf = new FileNameExtensionFilter("Fichiers .rtf", "rtf");
    private final JFileChooser filechooser = new JFileChooser();

    /* Styles. */
    private StyledDocument document;
    private CustomStyle custom_style;
    private Style defaut;

    /* Polices installées sur le système. */
    private final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final String[] liste_polices = ge.getAvailableFontFamilyNames();

    /* Langues. */
    private ResourceBundle traducteur;

    private final Locale francais = new Locale("fr", "Francais");
    private final Locale anglais = new Locale("en", "Anglais");

    /* Autres. */
    private int taille_choisie = 0;
    private Color couleur_choisie = null;
    private final RTFEditorKit rtf = new RTFEditorKit();
    private final GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    private String police_choisie = null;
    private String style_choisi = null;
    private String file_open = null;
    private String extension_current_file = null;
    private String texte_doc;

    public Editeur() {
        this.setTitle("JEditor - Style");
        this.setJMenuBar(barre_menu);
        this.setSize((int) this.getToolkit().getScreenSize().getWidth() - 60, ((int) this.getToolkit().getScreenSize().getHeight() - 60));
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/content/images/icone.png")));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                testEnregistrerFichier("fermer");
            }
        });

        this.setLayout(new BorderLayout());
        this.add(panel_tool_barre, BorderLayout.NORTH);
        this.add(barre_scroll, BorderLayout.CENTER);

        zone_texte.setEditorKit(rtf);
        zone_texte.setFocusable(true);
        zone_texte.requestFocus();

        initBarreMenu();
        initToolBarres();
        initPopUpMenu();
        initFileChooser();
        initLangage();

        enableOptionsBarreMenu();

        setIconToolBarres();
        setIconBarreMenu();
        setIconPopuMenu();
        setAccelerator();
        setListener();

        this.setVisible(true);
    }

    /* Méthodes graphiques. */
    /**
     * Méthode permettant d'initialiser la barre de menu de la fenêtre.
     */
    private void initBarreMenu() {
        barre_menu.add(menu_fichier);
        menu_fichier.setMnemonic(KeyEvent.VK_F);
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
        menu_edition.setMnemonic(KeyEvent.VK_E);

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
        menu_edition.add(sous_menu_recherche_globale);
        menu_edition.addSeparator();
        menu_edition.add(sous_menu_plein_ecran);
        barre_menu.add(menu_insertion);
        menu_insertion.setMnemonic(KeyEvent.VK_I);
        menu_insertion.add(sous_menu_date);
        menu_insertion.add(sous_menu_heure);
        barre_menu.add(menu_format);
        menu_format.setMnemonic(KeyEvent.VK_O);
        menu_format.add(sous_menu_formatage_defaut);
        menu_format.add(menu_modifier_casse);
        menu_modifier_casse.add(sous_menu_majuscule);
        menu_modifier_casse.add(sous_menu_minuscule);
        menu_format.addSeparator();
        menu_format.add(sous_menu_police);
        menu_format.add(sous_menu_taille);
        menu_format.add(menu_style);
        menu_style.add(sous_menu_normal);
        menu_style.add(sous_menu_gras);
        menu_style.add(sous_menu_italique);
        menu_style.add(sous_menu_souligne);
        menu_style.add(sous_menu_barre);
        menu_style.addSeparator();
        menu_style.add(sous_menu_indice);
        menu_style.add(sous_menu_exposant);
        menu_format.add(menu_alignement);
        menu_alignement.add(sous_menu_alignement_gauche);
        menu_alignement.add(sous_menu_alignement_centre);
        menu_alignement.add(sous_menu_alignement_droite);
        menu_alignement.add(sous_menu_alignement_justifie);
        menu_format.addSeparator();
        menu_format.add(sous_menu_style_personnalise);
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
        menu_aide.setMnemonic(KeyEvent.VK_A);
        menu_aide.add(sous_menu_aide);
        menu_aide.add(sous_menu_site);
        menu_aide.add(sous_menu_jeditor);
    }

    /**
     * Méthode permettant d'initialiser les tool barres de la fenêtre.
     */
    private void initToolBarres() {
        center.setHorizontalAlignment(JLabel.CENTER);

        /* Initialisation du panel qui contiendra les 2 tool barres. */
        panel_tool_barre.setLayout(new BoxLayout(panel_tool_barre, BoxLayout.Y_AXIS));
        panel_tool_barre.add(tool_barre_haut);
        tool_barre_haut.setFloatable(false);
        tool_barre_haut.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        panel_tool_barre.add(tool_barre_bas);
        tool_barre_bas.setFloatable(false);
        tool_barre_bas.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        /* Haut. */
        tool_barre_haut.add(Box.createHorizontalStrut(5));
        tool_barre_haut.add(bout_new);
        tool_barre_haut.add(Box.createHorizontalStrut(10));
        tool_barre_haut.add(bout_ouvrir);
        tool_barre_haut.add(bout_enregistrer);
        tool_barre_haut.add(bout_mail);
        tool_barre_haut.add(Box.createHorizontalStrut(10));
        tool_barre_haut.add(bout_imprimer);
        tool_barre_haut.add(bout_chercher);
        tool_barre_haut.add(Box.createHorizontalStrut(10));
        tool_barre_haut.add(bout_couper);
        tool_barre_haut.add(bout_copier);
        tool_barre_haut.add(bout_coller);

        /* Bas. */
        for (int i = 0; i < liste_polices.length; i++) {
            box_police.addItem(liste_polices[i]);
        }

        tool_barre_bas.add(Box.createHorizontalStrut(5));
        tool_barre_bas.add(box_police);
        box_police.setMaximumSize(new Dimension(150, 30));

        tool_barre_bas.add(Box.createHorizontalStrut(10));

        for (int i = 0; i < liste_tailles.length; i++) {
            box_taille.addItem(liste_tailles[i]);
        }

        tool_barre_bas.add(box_taille);
        box_taille.setMaximumSize(new Dimension(50, 30));
        box_taille.setRenderer(center);

        tool_barre_bas.add(Box.createHorizontalStrut(10));
        tool_barre_bas.add(bout_gras);
        tool_barre_bas.add(bout_italique);
        tool_barre_bas.add(bout_souligne);
        tool_barre_bas.add(Box.createHorizontalStrut(10));
        tool_barre_bas.add(bout_gauche);
        tool_barre_bas.add(bout_centre);
        tool_barre_bas.add(bout_droite);
        tool_barre_bas.add(bout_justifie);
        tool_barre_bas.add(Box.createHorizontalStrut(10));
        tool_barre_bas.add(bout_couleur);
        tool_barre_bas.add(bout_surlignage);
        tool_barre_bas.add(Box.createHorizontalStrut(10));
    }

    /**
     * Méthode permettant d'initialiser le menu pop-up (activé par un clic
     * droit) de la fenêtre.
     */
    private void initPopUpMenu() {
        pop_up_menu.add(pop_up_formatage_defaut);
        pop_up_menu.addSeparator();
        pop_up_menu.add(pop_up_police);
        pop_up_menu.add(pop_up_taille);
        pop_up_menu.add(pop_up_style);
        pop_up_style.add(pop_up_normal);
        pop_up_style.add(pop_up_gras);
        pop_up_style.add(pop_up_italique);
        pop_up_style.add(pop_up_souligne);
        pop_up_style.add(pop_up_barre);
        pop_up_style.addSeparator();
        pop_up_style.add(pop_up_indice);
        pop_up_style.add(pop_up_exposant);
        pop_up_menu.add(pop_up_alignement);
        pop_up_alignement.add(pop_up_gauche);
        pop_up_alignement.add(pop_up_centre);
        pop_up_alignement.add(pop_up_droite);
        pop_up_alignement.add(pop_up_justifie);
        pop_up_menu.addSeparator();
        pop_up_menu.add(pop_up_coller);
    }

    /**
     * Méthode permettant d'initialiser le sélecteur de fichier utilisé lors de
     * l'enregistrement et de l'ouverture d'un fichier.
     */
    private void initFileChooser() {
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        filechooser.setAcceptAllFileFilterUsed(false);
        filechooser.addChoosableFileFilter(ext_rtf);
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

    /**
     * Méthode permettant d'associer à certains éléments de la barre de menu une
     * icône.
     */
    private void setIconBarreMenu() {
        sous_menu_nouveau.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/nouveau.png")));
        sous_menu_fermer_editeur.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/fermer.png")));
        sous_menu_ouvrir.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/ouvrir.png")));
        sous_menu_enregistrer.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/enregistrer.png")));
        sous_menu_enregistrer_sous.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/enregistrer_sous.png")));
        sous_menu_imprimer.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/imprimer.png")));
        sous_menu_quitter.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/quitter.png")));
        sous_menu_couper.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/couper.png")));
        sous_menu_copier.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/copier.png")));
        sous_menu_coller.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/coller.png")));
        sous_menu_gras.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/gras.png")));
        sous_menu_italique.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/italique.png")));
        sous_menu_souligne.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/souligne.png")));
        sous_menu_alignement_gauche.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/gauche.png")));
        sous_menu_alignement_centre.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/centre.png")));
        sous_menu_alignement_droite.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/droite.png")));
        sous_menu_alignement_justifie.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/justifie.png")));
        sous_menu_tout_selectionner.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/tout_selectionner.png")));
        sous_menu_recherche_globale.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/rechercher.png")));
        sous_menu_plein_ecran.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/plein_ecran.png")));
        sous_menu_date.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/date.png")));
        sous_menu_heure.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/heure.png")));
        sous_menu_police.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/police.png")));
        sous_menu_taille.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/taille.png")));
        sous_menu_fermer_fenetre.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/fermer.png")));
        sous_menu_style_personnalise.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/style_personnalise.png")));
        sous_menu_aide.setIcon(new ImageIcon(getClass().getResource("/content/images/menu_aide/aide.png")));
        sous_menu_jeditor.setIcon(new ImageIcon(getClass().getResource("/content/images/menu_aide/informations.png")));
    }

    /**
     * Méthode permettant d'associer à certains éléments des tool barres une
     * icône.
     */
    private void setIconToolBarres() {
        /* Haut. */

        bout_new.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/nouveau.png")));
        bout_ouvrir.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/ouvrir.png")));
        bout_enregistrer.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/enregistrer.png")));
        bout_mail.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/mail.png")));
        bout_imprimer.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/imprimer.png")));
        bout_chercher.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/rechercher.png")));
        bout_couper.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/couper.png")));
        bout_copier.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/copier.png")));
        bout_coller.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/coller.png")));

        /* Bas. */
        bout_gras.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/gras.png")));
        bout_italique.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/italique.png")));
        bout_souligne.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/souligne.png")));
        bout_gauche.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/gauche.png")));
        bout_centre.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/centre.png")));
        bout_droite.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/droite.png")));
        bout_justifie.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/justifie.png")));
        bout_couleur.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/couleur.png")));
        bout_surlignage.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/surligner.png")));

    }

    /**
     * Méthode permettant d'associer à certains éléments du menu pop-up une
     * icône.
     */
    private void setIconPopuMenu() {
        pop_up_coller.setIcon(new ImageIcon(getClass().getResource("/content/images/autres/coller.png")));
        pop_up_taille.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/taille.png")));
        pop_up_police.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/police.png")));
        pop_up_gauche.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/gauche.png")));
        pop_up_centre.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/centre.png")));
        pop_up_droite.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/droite.png")));
        pop_up_justifie.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/justifie.png")));
        pop_up_gras.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/gras.png")));
        pop_up_italique.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/italique.png")));
        pop_up_souligne.setIcon(new ImageIcon(getClass().getResource("/content/images/formatage_texte/souligne.png")));
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
            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Editeur.this.remove(lab_plein_ecran);
                    device.setFullScreenWindow(null);
                } else {
                    removeHighlights(zone_texte);
                }
            }
        });

        zone_texte.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (zone_texte.getSelectedText() == null) {
                    bout_gras.setSelected(false);
                    bout_italique.setSelected(false);
                    bout_souligne.setSelected(false);
                    bout_gauche.setSelected(false);
                    bout_centre.setSelected(false);
                    bout_droite.setSelected(false);
                    bout_justifie.setSelected(false);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    pop_up_menu.show(e.getComponent(), e.getX(), e.getY());
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    if (zone_texte.getSelectedText() == null) {
                        bout_gras.setSelected(false);
                        bout_italique.setSelected(false);
                        bout_souligne.setSelected(false);
                        bout_gauche.setSelected(false);
                        bout_centre.setSelected(false);
                        bout_droite.setSelected(false);
                        bout_justifie.setSelected(false);
                    } else {
                        MutableAttributeSet attr = rtf.getInputAttributes();

                        if (StyleConstants.isBold(attr)) {
                            bout_gras.setSelected(true);
                        }
                        if (StyleConstants.isItalic(attr)) {
                            bout_italique.setSelected(true);
                        }
                        if (StyleConstants.isUnderline(attr)) {
                            bout_souligne.setSelected(true);
                        }

                        if (StyleConstants.getAlignment(attr) == StyleConstants.ALIGN_LEFT) {
                            bout_gauche.setSelected(true);
                        }
                        if (StyleConstants.getAlignment(attr) == StyleConstants.ALIGN_CENTER) {
                            bout_centre.setSelected(true);
                        }
                        if (StyleConstants.getAlignment(attr) == StyleConstants.ALIGN_RIGHT) {
                            bout_droite.setSelected(true);
                        }
                        if (StyleConstants.getAlignment(attr) == StyleConstants.ALIGN_JUSTIFIED) {
                            bout_centre.setSelected(true);
                        }

                        for (int i = 0; i < liste_polices.length; i++) {
                            if (StyleConstants.getFontFamily(attr).equals(liste_polices[i])) {
                                box_police.setSelectedIndex(i);
                            }
                        }

                        for (int i = 0; i < liste_tailles.length; i++) {
                            if (StyleConstants.getFontSize(attr) == Integer.parseInt(liste_tailles[i])) {
                                box_taille.setSelectedIndex(i);
                            }
                        }
                    }
                }
            }
        });

        zone_texte.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                updateTexteInZoneTexte();
                removeHighlights(zone_texte);
                enableOptionsBarreMenu();
            }
        });

        zone_texte.getDocument().addUndoableEditListener(undo_redo_listener);

        /* Barre de menu. */
        sous_menu_nouveau.addActionListener(actionslistener);
        sous_menu_fermer_editeur.addActionListener(actionslistener);
        sous_menu_ouvrir.addActionListener(actionslistener);
        sous_menu_enregistrer.addActionListener(actionslistener);
        sous_menu_enregistrer_sous.addActionListener(actionslistener);
        sous_menu_imprimer.addActionListener(actionslistener);
        sous_menu_quitter.addActionListener(actionslistener);
        sous_menu_copier.addActionListener(actionslistener);
        sous_menu_couper.addActionListener(actionslistener);
        sous_menu_coller.addActionListener(actionslistener);
        sous_menu_supprimer.addActionListener(actionslistener);
        sous_menu_tout_selectionner.addActionListener(actionslistener);
        sous_menu_recherche_globale.addActionListener(actionslistener);
        sous_menu_plein_ecran.addActionListener(actionslistener);
        sous_menu_heure.addActionListener(actionslistener);
        sous_menu_date.addActionListener(actionslistener);
        sous_menu_formatage_defaut.addActionListener(actionslistener);
        sous_menu_alignement_gauche.addActionListener(actionslistener);
        sous_menu_alignement_centre.addActionListener(actionslistener);
        sous_menu_alignement_droite.addActionListener(actionslistener);
        sous_menu_alignement_justifie.addActionListener(actionslistener);
        sous_menu_gras.addActionListener(actionslistener);
        sous_menu_italique.addActionListener(actionslistener);
        sous_menu_souligne.addActionListener(actionslistener);
        sous_menu_barre.addActionListener(actionslistener);
        sous_menu_indice.addActionListener(actionslistener);
        sous_menu_exposant.addActionListener(actionslistener);
        sous_menu_police.addActionListener(actionslistener);
        sous_menu_taille.addActionListener(actionslistener);
        sous_menu_style_personnalise.addActionListener(actionslistener);
        sous_menu_majuscule.addActionListener(actionslistener);
        sous_menu_minuscule.addActionListener(actionslistener);
        sous_menu_francais.addActionListener(actionslistener);
        sous_menu_anglais.addActionListener(actionslistener);
        sous_menu_statistiques.addActionListener(actionslistener);
        sous_menu_new_fenetre.addActionListener(actionslistener);
        sous_menu_fermer_fenetre.addActionListener(actionslistener);
        sous_menu_aide.addActionListener(actionslistener);
        sous_menu_site.addActionListener(actionslistener);
        sous_menu_jeditor.addActionListener(actionslistener);

        /* Tool barres. */
        bout_ouvrir.addActionListener(actionslistener);
        bout_enregistrer.addActionListener(actionslistener);
        bout_mail.addActionListener(actionslistener);
        bout_new.addActionListener(actionslistener);
        bout_couper.addActionListener(actionslistener);
        bout_copier.addActionListener(actionslistener);
        bout_coller.addActionListener(actionslistener);
        bout_imprimer.addActionListener(actionslistener);
        bout_chercher.addActionListener(actionslistener);
        bout_gras.addActionListener(actionslistener);
        bout_italique.addActionListener(actionslistener);
        bout_souligne.addActionListener(actionslistener);
        bout_gauche.addActionListener(actionslistener);
        bout_centre.addActionListener(actionslistener);
        bout_droite.addActionListener(actionslistener);
        bout_justifie.addActionListener(actionslistener);
        bout_couleur.addActionListener(actionslistener);
        bout_surlignage.addActionListener(actionslistener);

        box_taille.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (zone_texte.getSelectedText() == null); else {
                    modifierTailleTexte(Integer.parseInt((String) box_taille.getSelectedItem()), zone_texte.getSelectedText());
                }
            }
        });

        box_police.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {
                if (zone_texte.getSelectedText() == null); else {
                    modifierPoliceTexte((String) box_police.getSelectedItem(), zone_texte.getSelectedText());
                }
            }
        });

        /* Menu popup. */
        pop_up_formatage_defaut.addActionListener(actionslistener);
        pop_up_coller.addActionListener(actionslistener);
        pop_up_normal.addActionListener(actionslistener);
        pop_up_gras.addActionListener(actionslistener);
        pop_up_italique.addActionListener(actionslistener);
        pop_up_souligne.addActionListener(actionslistener);
        pop_up_barre.addActionListener(actionslistener);
        pop_up_taille.addActionListener(actionslistener);
        pop_up_barre.addActionListener(actionslistener);
        pop_up_indice.addActionListener(actionslistener);
        pop_up_exposant.addActionListener(actionslistener);
        pop_up_police.addActionListener(actionslistener);
        pop_up_gauche.addActionListener(actionslistener);
        pop_up_centre.addActionListener(actionslistener);
        pop_up_droite.addActionListener(actionslistener);
        pop_up_justifie.addActionListener(actionslistener);
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
        sous_menu_copier.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
        sous_menu_couper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
        sous_menu_coller.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
        sous_menu_tout_selectionner.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));
        sous_menu_formatage_defaut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_MASK));
        sous_menu_gras.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK));
        sous_menu_italique.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK));
        sous_menu_souligne.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_MASK));
        pop_up_gras.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK));
        pop_up_italique.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK));
        pop_up_souligne.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_MASK));
        sous_menu_recherche_globale.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_MASK));
        sous_menu_quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK));

        /* alt + touche. */
        sous_menu_date.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.ALT_MASK));
        sous_menu_heure.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.ALT_MASK));
        sous_menu_fermer_fenetre.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.ALT_MASK));
        sous_menu_aide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.ALT_MASK));
        sous_menu_jeditor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, KeyEvent.ALT_MASK));
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
            sous_menu_imprimer.setEnabled(false);
            sous_menu_recherche_globale.setEnabled(false);
            sous_menu_formatage_defaut.setEnabled(false);
            menu_style.setEnabled(false);
            sous_menu_police.setEnabled(false);
            menu_modifier_casse.setEnabled(false);
            sous_menu_taille.setEnabled(false);
            sous_menu_style_personnalise.setEnabled(false);
            pop_up_formatage_defaut.setEnabled(false);
            menu_alignement.setEnabled(false);

            pop_up_style.setEnabled(false);
            pop_up_taille.setEnabled(false);
            pop_up_police.setEnabled(false);
            pop_up_alignement.setEnabled(false);
        } else {
            sous_menu_enregistrer.setEnabled(true);
            sous_menu_enregistrer_sous.setEnabled(true);
            sous_menu_copier.setEnabled(true);
            sous_menu_couper.setEnabled(true);
            sous_menu_supprimer.setEnabled(true);
            sous_menu_tout_selectionner.setEnabled(true);
            sous_menu_imprimer.setEnabled(true);
            sous_menu_recherche_globale.setEnabled(true);
            sous_menu_formatage_defaut.setEnabled(true);
            menu_style.setEnabled(true);
            sous_menu_police.setEnabled(true);
            menu_modifier_casse.setEnabled(true);
            sous_menu_taille.setEnabled(true);
            sous_menu_style_personnalise.setEnabled(true);
            pop_up_formatage_defaut.setEnabled(true);
            menu_alignement.setEnabled(true);

            pop_up_style.setEnabled(true);
            pop_up_taille.setEnabled(true);
            pop_up_police.setEnabled(true);
            pop_up_alignement.setEnabled(true);
        }
    }

    /* Méthodes servant à enregistrer et ouvrir un fichier. */
    /**
     * Méthode permettant d'enregistrer un fichier.<br />
     * La méthode va aussi tester si le nom du fichier entré existe déjà. Si
     * c'est le cas, une confirmation va être demandée.
     *
     * @return <b>true</b> si le fichier a bien été
     * enregistré.<br /><b>false</b> sinon.
     */
    private boolean enregistrerFichier() {
        updateTexteInZoneTexte();

        if (filechooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            extension_current_file = filechooser.getFileFilter().getDescription().substring(9);
            file_open = filechooser.getSelectedFile() + extension_current_file;

            if (!new File(file_open).exists()) {
                FileOutputStream writer = null;

                try {
                    writer = new FileOutputStream(file_open);
                } catch (FileNotFoundException e) {
                    return false;
                }

                if (filechooser.getFileFilter() == ext_rtf) {
                    try {
                        rtf.write(writer, zone_texte.getDocument(), 0, zone_texte.getDocument().getLength());
                        writer.close();
                    } catch (IOException | BadLocationException e) {
                        return false;
                    }

                    return true;
                }
            } else {
                if (JOptionPane.showConfirmDialog(null, "Le fichier existe déjà. Voulez-vous continuer ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
                    enregistrerFichier(file_open);
                } else {
                    enregistrerFichier();
                }
            }
        }

        return false;
    }

    /**
     * Méthode permettant d'enregistrer un fichier à une adresse déjà connue.
     *
     * @param adresse_fichier L'adresse du fichier.
     * 
     * @return <b>true</b> si le fichier a bien été
     * enregistré.<br /><b>false</b> sinon.
     */
    private boolean enregistrerFichier(String adresse_fichier) {
        updateTexteInZoneTexte();

        switch (extension_current_file) {
            case ".txt":
                try {
                    FileWriter lu = new FileWriter(adresse_fichier);
                    BufferedWriter out = new BufferedWriter(lu);
                    out.write(texte_doc);
                    out.close();
                } catch (IOException er) {
                    return false;
                }
                
                JOptionPane.showMessageDialog(null, "Fichier enregistré.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                
                return true;
            case ".rtf":
                FileOutputStream writer = null;
                
                try {
                    writer = new FileOutputStream(adresse_fichier);
                } catch (FileNotFoundException e) {
                    return false;
                }
                
                try {
                    rtf.write(writer, zone_texte.getDocument(), 0, zone_texte.getDocument().getLength());
                    writer.close();
                } catch (IOException | BadLocationException e) {
                    return false;
                }
                
                JOptionPane.showMessageDialog(null, "Fichier enregistré.", "Succès", JOptionPane.INFORMATION_MESSAGE);

            return true;
        }

        return false;
    }

    /**
     * Méthode permettant d'ouvrir un fichier si l'extension du fichier est
     * gérée.<br />
     *
     * @return <b>true</b> si le fichier a bien été ouvert.<br /><b>false</b>
     * sinon.
     */
    private boolean ouvrirFichier() {
        if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file_open = filechooser.getSelectedFile().toString();

            if (file_open.endsWith(".rtf") == true) {
                extension_current_file = ".rtf";

                try {
                    FileInputStream fis = new FileInputStream(file_open);
                    rtf.read(fis, zone_texte.getDocument(), 0);
                } catch (FileNotFoundException e) {
                    return false;
                } catch (IOException | BadLocationException e) {
                    return false;
                }

                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Extension non gérée.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }

        return false;
    }

    /**
     * Méthode permettant de tester si un document doit être enregistré
     * lorsqu'un utilisateur veut ouvrir un nouveau fichier.
     */
    private void ouvrirFichierEtTestSiEnregistrerFichier() {
        updateTexteInZoneTexte();

        if ((texte_doc.length() != 0) && (file_open == null)) {
            int choix = JOptionPane.showConfirmDialog(null, "Enregistrer les modifications ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choix == JOptionPane.OK_OPTION) {
                enregistrerFichier();
                ouvrirFichier();
            } else if (choix == JOptionPane.NO_OPTION) {
                ouvrirFichier();
            }
        } else if ((texte_doc.length() == 0) && (file_open == null)) {
            ouvrirFichier();
        } else if ((texte_doc.length() != 0) && (file_open != null)) {
            int choix = JOptionPane.showConfirmDialog(null, "Enregistrer les modifications ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choix == JOptionPane.OK_OPTION) {
                enregistrerFichier();
                ouvrirFichier();
            } else if (choix == JOptionPane.NO_OPTION) {
                ouvrirFichier();
            }
        }
    }

    /**
     * Méthode permettant de tester si le document doit être enregistré ou non.
     *
     * @param action L'action à faire une fois les tests effectués.
     */
    private void testEnregistrerFichier(String action) {
        updateTexteInZoneTexte();

        if ((texte_doc.length() == 0) && (file_open == null)) {
            switch (action) {
                case "fermer":
                    dispose();
                    break;
                case "quitter":
                    System.exit(0);
            }
        } else {
            int reponse = JOptionPane.showConfirmDialog(null, "Enregistrer les modifications ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (reponse == JOptionPane.NO_OPTION) {
                switch (action) {
                    case "fermer":
                        dispose();
                        break;
                    case "quitter":
                        System.exit(0);
                }
            } else if (reponse == JOptionPane.YES_OPTION) {
                enregistrerFichier();
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

        for (Highlighter.Highlight hilite1 : hilites) {
            if (hilite1.getPainter() instanceof MyHighlightPainter) {
                hilite.removeHighlight(hilite1);
            }
        }
    }

    private class MyHighlightPainter extends DefaultHighlightPainter {

        public MyHighlightPainter(Color color) {
            super(color);
        }
    }

    /* Méthodes de formatage du texte. */
    /**
     * Méthode permettant de modifier la couleur d'un texte dans le document.
     *
     * @param couleur La nouvelle couleur du texte choisie par l'utilisateur.
     * @param texte Le texte qui doit avoir sa couleur modifiée.
     */
    private void modifierCouleurTexte(Color couleur, String texte) {
        if (couleur == null || texte == null); else if (texte.length() > 0) {
            MutableAttributeSet attr = rtf.getInputAttributes();
            StyleConstants.setForeground(attr, couleur);

            zone_texte.setCharacterAttributes(attr, false);
        }
    }

    /**
     * Méthode permettant de modifier le style d'un texte dans le document.
     *
     * @param style Le nouveau style du texte choisi par l'utilisateur.
     * @param texte Le texte qui doit avoir son style modifié.
     */
    private void modifierStyleTexte(String style, String texte) {
        if (texte == null); else if (texte.length() > 0) {
            MutableAttributeSet attr = rtf.getInputAttributes();

            switch (style) {
                case "Gras":
                    StyleConstants.setBold(attr, Boolean.TRUE);
                    break;
                case "PasGras":
                    StyleConstants.setBold(attr, Boolean.FALSE);
                    break;
                case "Italic":
                    StyleConstants.setItalic(attr, Boolean.TRUE);
                    break;
                case "PasItalic":
                    StyleConstants.setItalic(attr, Boolean.FALSE);
                    break;
                case "Souligné":
                    StyleConstants.setUnderline(attr, Boolean.TRUE);
                    break;
                case "PasSouligné":
                    StyleConstants.setUnderline(attr, Boolean.FALSE);
                    break;
                case "Indice":
                    StyleConstants.setSubscript(attr, Boolean.TRUE);
                    break;
                case "Exposant":
                    StyleConstants.setSuperscript(attr, Boolean.TRUE);
                    break;
                case "Normal":
                    StyleConstants.setBold(attr, Boolean.FALSE);
                    StyleConstants.setItalic(attr, Boolean.FALSE);
                    StyleConstants.setUnderline(attr, Boolean.FALSE);
                    StyleConstants.setStrikeThrough(attr, Boolean.FALSE);
                    StyleConstants.setSuperscript(attr, Boolean.FALSE);
                    StyleConstants.setSubscript(attr, Boolean.FALSE);
                    break;
            }

            zone_texte.setCharacterAttributes(attr, false);
        }
    }

    /**
     * Méthode permettant de modifier l'alignement d'un texte dans le document.
     *
     * @param alignement Le nouvel alignement du texte choisi par l'utilisateur.
     * @param texte Le texte qui doit avoir son alignement modifié.
     */
    private void modifierAlignementTexte(String alignement, String texte) {
        if (texte == null); else if (texte.length() > 0) {
            MutableAttributeSet attr = rtf.getInputAttributes();

            switch (alignement) {
                case "gauche":
                    StyleConstants.setAlignment(attr, StyleConstants.ALIGN_LEFT);
                    break;
                case "centre":
                    StyleConstants.setAlignment(attr, StyleConstants.ALIGN_CENTER);
                    break;
                case "droite":
                    StyleConstants.setAlignment(attr, StyleConstants.ALIGN_RIGHT);
                    break;
                case "justifie":
                    StyleConstants.setAlignment(attr, StyleConstants.ALIGN_JUSTIFIED);
                    break;
            }

            zone_texte.setParagraphAttributes(attr, false);
        }
    }

    /**
     * Méthode permettant de modifier la taille d'un texte dans le document.
     *
     * @param taille La nouvelle taille du texte choisie par l'utilisateur.
     * @param texte Le texte qui doit avoir sa taille modifiée.
     */
    private void modifierTailleTexte(int taille, String texte) {
        if (texte == null); else if (texte.length() > 0) {
            MutableAttributeSet attr = rtf.getInputAttributes();
            StyleConstants.setFontSize(attr, taille);

            zone_texte.setCharacterAttributes(attr, false);
        }
    }

    /**
     * Méthode permettant de modifier la police d'un texte dans le document.
     *
     * @param police La nouvelle police du texte choisie par l'utilisateur.
     * @param texte Le texte qui doit avoir sa police modifiée.
     */
    private void modifierPoliceTexte(String police, String texte) {
        if (police == null || texte == null); else if (texte.length() > 0) {
            MutableAttributeSet attr = rtf.getInputAttributes();
            StyleConstants.setFontFamily(attr, police);

            zone_texte.setCharacterAttributes(attr, false);
        }
    }

    /**
     * Méthode permettant de modifier le surlignage d'un texte dans le document.
     *
     * @param couleur La nouvelle couleur de surlignage du texte choisie par
     * l'utilisateur.
     * @param texte Le texte qui doit avoir son surlignage modifié.
     */
    private void modifierSurlignageTexte(Color couleur, String texte) {
        if (couleur == null || texte == null); else if (texte.length() > 0) {
            MutableAttributeSet attr = rtf.getInputAttributes();
            StyleConstants.setBackground(attr, couleur);

            zone_texte.setCharacterAttributes(attr, false);
        }
    }

    /**
     * Méthode permettant de mettre le style d'un texte à celui par défaut.
     *
     * @param texte Le texte qui doit avoir son style mis à celui par défaut.
     */
    private void modifierTexteDefaultStyle(String texte) {
        if (texte == null); 
        
        else if (texte.length() > 0) {
            MutableAttributeSet attr = rtf.getInputAttributes();

            StyleConstants.setForeground(attr, Color.BLACK);
            StyleConstants.setAlignment(attr, StyleConstants.ALIGN_LEFT);
            StyleConstants.setFontSize(attr, 12);
            StyleConstants.setFontFamily(attr, "Arial");
            StyleConstants.setBold(attr, Boolean.FALSE);
            StyleConstants.setItalic(attr, Boolean.FALSE);
            StyleConstants.setUnderline(attr, Boolean.FALSE);
            StyleConstants.setStrikeThrough(attr, Boolean.FALSE);
            StyleConstants.setBackground(attr, Color.WHITE);

            zone_texte.setCharacterAttributes(attr, false);
            zone_texte.setParagraphAttributes(attr, false);
        }
    }

    /**
     * Méthode permettant d'utiliser la fenêtre de style personnalisé.<br />
     * Une fois un style choisi, la méthode va l'appliquer au texte sélectionné.
     */
    private void utiliserStylePersonnalise() {
        custom_style = new CustomStyle();
        custom_style.setSelectedColor(couleur_choisie);
        custom_style.setSelectedPolice(police_choisie);
        custom_style.setSelectedStyle(style_choisi);
        custom_style.setSelectedTaille(taille_choisie);

        custom_style.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                String texte = zone_texte.getSelectedText();

                if ((custom_style.isOKSelected()) && (texte.length() > 0)) {
                    police_choisie = custom_style.getPoliceChoisie();
                    style_choisi = custom_style.getStyleChoisi();
                    taille_choisie = Integer.parseInt(custom_style.getTailleChoisie());
                    couleur_choisie = custom_style.getColorChoisie();

                    modifierStyleTexte(style_choisi, texte);
                    modifierCouleurTexte(couleur_choisie, texte);
                    modifierTailleTexte(taille_choisie, texte);
                    modifierPoliceTexte(police_choisie, texte);

                    custom_style.setSelectedTaille(taille_choisie);
                    custom_style.setSelectedPolice(police_choisie);
                    custom_style.setSelectedColor(couleur_choisie);
                    custom_style.setSelectedStyle(style_choisi);
                }
            }
        });
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
        sous_menu_recherche_globale.setText(traducteur.getString("sous_menu_rechercher"));
        sous_menu_plein_ecran.setText(traducteur.getString("sous_menu_plein_ecran"));
        menu_insertion.setText(traducteur.getString("menu.insertion"));
        sous_menu_date.setText(traducteur.getString("sous_menu_date"));
        sous_menu_heure.setText(traducteur.getString("sous_menu_heure"));
        menu_format.setText(traducteur.getString("menu.format"));
        sous_menu_formatage_defaut.setText(traducteur.getString("sous_menu_formatage_defaut"));
        menu_alignement.setText(traducteur.getString("menu.alignement"));
        sous_menu_alignement_gauche.setText(traducteur.getString("sous_menu_alignement_gauche"));
        sous_menu_alignement_centre.setText(traducteur.getString("sous_menu_alignement_centre"));
        sous_menu_alignement_droite.setText(traducteur.getString("sous_menu_alignement_droite"));
        sous_menu_alignement_justifie.setText(traducteur.getString("sous_menu_alignement_justifie"));
        sous_menu_police.setText(traducteur.getString("sous_menu_police"));
        sous_menu_taille.setText(traducteur.getString("sous_menu_taille"));
        menu_style.setText(traducteur.getString("menu.style"));
        sous_menu_normal.setText(traducteur.getString("sous_menu_normal"));
        sous_menu_gras.setText(traducteur.getString("sous_menu_gras"));
        sous_menu_italique.setText(traducteur.getString("sous_menu_italique"));
        sous_menu_souligne.setText(traducteur.getString("sous_menu_souligne"));
        sous_menu_barre.setText(traducteur.getString("sous_menu_barre"));
        sous_menu_indice.setText(traducteur.getString("sous_menu_indice"));
        sous_menu_exposant.setText(traducteur.getString("sous_menu_exposant"));
        sous_menu_style_personnalise.setText(traducteur.getString("sous_menu_style_personnalise"));
        menu_modifier_casse.setText(traducteur.getString("menu.modifier_casse"));
        sous_menu_majuscule.setText(traducteur.getString("sous_menu_majuscule"));
        sous_menu_minuscule.setText(traducteur.getString("sous_menu_minuscule"));
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
        pop_up_formatage_defaut.setText(traducteur.getString("pop_up_formatage_defaut"));
        pop_up_coller.setText(traducteur.getString("pop_up_coller"));
        pop_up_style.setText(traducteur.getString("pop_up_style"));
        pop_up_normal.setText(traducteur.getString("pop_up_normal"));
        pop_up_gras.setText(traducteur.getString("pop_up_gras"));
        pop_up_italique.setText(traducteur.getString("pop_up_italique"));
        pop_up_souligne.setText(traducteur.getString("pop_up_souligne"));
        pop_up_barre.setText(traducteur.getString("pop_up_barre"));
        pop_up_indice.setText(traducteur.getString("pop_up_indice"));
        pop_up_exposant.setText(traducteur.getString("pop_up_exposant"));
        pop_up_taille.setText(traducteur.getString("pop_up_taille"));
        pop_up_police.setText(traducteur.getString("pop_up_police"));
        pop_up_alignement.setText(traducteur.getString("pop_up_alignement"));
        pop_up_gauche.setText(traducteur.getString("pop_up_gauche"));
        pop_up_centre.setText(traducteur.getString("pop_up_centre"));
        pop_up_droite.setText(traducteur.getString("pop_up_droite"));
        pop_up_justifie.setText(traducteur.getString("pop_up_justifie"));

        /* Tool barres. */
        bout_new.setToolTipText(traducteur.getString("bout_new"));
        bout_ouvrir.setToolTipText(traducteur.getString("bout_ouvrir"));
        bout_enregistrer.setToolTipText(traducteur.getString("bout_enregistrer"));
        bout_mail.setToolTipText(traducteur.getString("bout_mail"));
        bout_imprimer.setToolTipText(traducteur.getString("bout_imprimer"));
        bout_chercher.setToolTipText(traducteur.getString("bout_chercher"));
        bout_copier.setToolTipText(traducteur.getString("bout_copier"));
        bout_coller.setToolTipText(traducteur.getString("bout_coller"));
        bout_couper.setToolTipText(traducteur.getString("bout_couper"));
        bout_gras.setToolTipText(traducteur.getString("bout_gras"));
        bout_italique.setToolTipText(traducteur.getString("bout_italique"));
        bout_souligne.setToolTipText(traducteur.getString("bout_souligne"));
        bout_gauche.setToolTipText(traducteur.getString("bout_gauche"));
        bout_centre.setToolTipText(traducteur.getString("bout_centre"));
        bout_droite.setToolTipText(traducteur.getString("bout_droite"));
        bout_justifie.setToolTipText(traducteur.getString("bout_justifie"));
        bout_couleur.setToolTipText(traducteur.getString("bout_couleur"));
        bout_surlignage.setToolTipText(traducteur.getString("bout_surlignage"));
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
     * Méthode permettant de créer un nouveau document.<br />
     * Si le document actuel n'est pas vide, il est proposé de l'enregistrer.
     */
    private void nouveauDocument() {
        updateTexteInZoneTexte();

        if (texte_doc.length() != 0) {
            int choix = JOptionPane.showConfirmDialog(null, "Enregistrer les modifications ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choix == JOptionPane.OK_OPTION) {
                enregistrerFichier();
                zone_texte.setText("");
                file_open = null;
            } else if (choix == JOptionPane.NO_OPTION) {
                zone_texte.setText("");
            }
        }
    }

    /**
     * Méthode permettant de rechercher ou remplacer un texte dans le
     * document.<br />
     * Si le texte est trouvé, il est surligné.
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
                    JOptionPane.showMessageDialog(null, "A faire !", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Méthode permettant d'insérer la date actuelle dans le document.
     */
    private void insererDate() {
        DateFormat format_date = new SimpleDateFormat("dd-MM-yyyy");
        String date_to_string = format_date.format(new Date());

        try {
            document = (StyledDocument) zone_texte.getStyledDocument();
            document.insertString(zone_texte.getCaretPosition(), date_to_string, defaut);
        } catch (BadLocationException e1) {
            return;
        }
    }

    /**
     * Méthode permettant d'insérer l'heure actuelle dans le document.
     */
    private void insererHeure() {
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        String heure_to_string = sdf.format(new Date());

        try {
            document = (StyledDocument) zone_texte.getStyledDocument();
            document.insertString(zone_texte.getCaretPosition(), heure_to_string, defaut);
        } catch (BadLocationException e1) {
            return;
        }
    }

    /**
     * Méthode permettant d'ouvrir une URL via le navigateur par défaut de
     * l'utilisateur.
     *
     * @param url L'URL à visiter.
     * @return <b>true</b> si l'URL a correctement été ouverte dans le
     * navigateur par défaut.<br /><b>false</b> sinon.
     */
    public boolean ouvrirURL(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));

            return true;
        } catch (URISyntaxException | IOException e1) {
            return false;
        }
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
            } else if (e.getSource() == sous_menu_fermer_editeur) {
                testEnregistrerFichier("fermer");
            } else if ((e.getSource() == sous_menu_ouvrir) || (e.getSource() == bout_ouvrir)) {
                ouvrirFichierEtTestSiEnregistrerFichier();
            } else if ((e.getSource() == sous_menu_enregistrer || e.getSource() == bout_enregistrer) && (file_open != null)) {
                enregistrerFichier(file_open);
            } else if (((e.getSource() == sous_menu_enregistrer || e.getSource() == bout_enregistrer) && (file_open == null)) || (e.getSource() == sous_menu_enregistrer_sous)) {
                enregistrerFichier();
            } else if (e.getSource() == bout_mail) {
                try {
                    Desktop.getDesktop().mail();
                } catch (IOException a) {
                    JOptionPane.showMessageDialog(null, "Cette fonctionnalité n'est pas supportée par votre système.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else if ((e.getSource() == sous_menu_imprimer) || (e.getSource() == bout_imprimer)) {
                imprimerDocument();
            } else if (e.getSource() == sous_menu_quitter) {
                testEnregistrerFichier("quitter");
            } else if ((e.getSource() == sous_menu_couper) || (e.getSource() == bout_couper)) {
                if (zone_texte.getSelectedText() == null); else {
                    zone_texte.cut();
                }
            } else if ((e.getSource() == sous_menu_copier) || (e.getSource() == bout_copier)) {
                if (zone_texte.getSelectedText() == null); else {
                    zone_texte.copy();
                }
            } else if ((e.getSource() == sous_menu_coller) || (e.getSource() == bout_coller) || (e.getSource() == pop_up_coller)) {
                zone_texte.paste();
            } else if (e.getSource() == sous_menu_supprimer) {
                if (zone_texte.getSelectedText() == null); else {
                    zone_texte.replaceSelection("");
                }
            } else if (e.getSource() == sous_menu_tout_selectionner) {
                zone_texte.selectAll();
            } else if ((e.getSource() == sous_menu_recherche_globale) || (e.getSource() == bout_chercher)) {
                rechercherEtRemplacerTexte();
            } else if (e.getSource() == sous_menu_plein_ecran) {
                if (device.isFullScreenSupported()) {
                    device.setFullScreenWindow(Editeur.this);
                    Editeur.this.add(lab_plein_ecran, BorderLayout.SOUTH);
                } else {
                    JOptionPane.showMessageDialog(null, "Le mode plein ecran n'est pas disponible", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == sous_menu_heure) {
                insererHeure();
            } else if (e.getSource() == sous_menu_date) {
                insererDate();
            } else if ((e.getSource() == sous_menu_normal) || (e.getSource() == pop_up_normal)) {
                modifierStyleTexte("Normal", zone_texte.getSelectedText());
            } else if ((e.getSource() == bout_gras) && (bout_gras.isSelected())) {
                modifierStyleTexte("PasGras", zone_texte.getSelectedText());
                bout_gras.setSelected(false);
            } else if ((e.getSource() == pop_up_gras) || (e.getSource() == sous_menu_gras) || ((e.getSource() == bout_gras) && (!bout_gras.isSelected()))) {
                modifierStyleTexte("Gras", zone_texte.getSelectedText());
                bout_gras.setSelected(true);
            } else if ((e.getSource() == bout_italique) && (bout_italique.isSelected())) {
                modifierStyleTexte("PasItalic", zone_texte.getSelectedText());
                bout_italique.setSelected(false);
            } else if ((e.getSource() == pop_up_italique) || (e.getSource() == sous_menu_italique) || ((e.getSource() == bout_italique) && (!bout_italique.isSelected()))) {
                modifierStyleTexte("Italic", zone_texte.getSelectedText());
                bout_italique.setSelected(true);
            } else if ((e.getSource() == bout_souligne) && (bout_souligne.isSelected())) {
                modifierStyleTexte("PasSouligné", zone_texte.getSelectedText());
                bout_souligne.setSelected(false);
            } else if ((e.getSource() == pop_up_souligne) || (e.getSource() == sous_menu_souligne) || ((e.getSource() == bout_souligne) && (!bout_souligne.isSelected()))) {
                modifierStyleTexte("Souligné", zone_texte.getSelectedText());
                bout_souligne.setSelected(true);
            } else if ((e.getSource() == pop_up_barre) || (e.getSource() == sous_menu_barre)) {
                modifierStyleTexte("Barré", zone_texte.getSelectedText());
            } else if ((e.getSource() == pop_up_indice) || (e.getSource() == sous_menu_indice)) {
                modifierStyleTexte("Indice", zone_texte.getSelectedText());
            } else if ((e.getSource() == pop_up_exposant) || (e.getSource() == sous_menu_exposant)) {
                modifierStyleTexte("Exposant", zone_texte.getSelectedText());
            } else if ((e.getSource() == sous_menu_police) || (e.getSource() == pop_up_police)) {
                final PoliceChooser police_chooser = new PoliceChooser();

                police_chooser.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        if (police_chooser.isClosingRight()) {
                            modifierPoliceTexte(police_chooser.getPoliceChoisie(), zone_texte.getSelectedText());
                        }
                    }
                });
            } else if ((e.getSource() == sous_menu_taille) || (e.getSource() == pop_up_taille)) {
                final SizeChooser taille_chooser = new SizeChooser();

                taille_chooser.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        if (taille_chooser.isClosingRight()) {
                            modifierTailleTexte(Integer.parseInt(taille_chooser.getTailleChoisie()), zone_texte.getSelectedText());
                        }
                    }
                });
            } else if (e.getSource() == sous_menu_style_personnalise) {
                utiliserStylePersonnalise();
            } else if (e.getSource() == sous_menu_majuscule) {
                zone_texte.replaceSelection(zone_texte.getSelectedText().toUpperCase());
            } else if (e.getSource() == sous_menu_minuscule) {
                zone_texte.replaceSelection(zone_texte.getSelectedText().toLowerCase());
            } else if (e.getSource() == bout_couleur) {
                modifierCouleurTexte(JColorChooser.showDialog(null, "Couleur du texte", Color.WHITE), zone_texte.getSelectedText());
            } else if (e.getSource() == bout_surlignage) {
                modifierSurlignageTexte(JColorChooser.showDialog(null, "Couleur du surlignage", Color.WHITE), zone_texte.getSelectedText());
            } else if ((e.getSource() == pop_up_gauche) || (e.getSource() == bout_gauche) || (e.getSource() == sous_menu_alignement_gauche)) {
                modifierAlignementTexte("gauche", zone_texte.getSelectedText());
            } else if ((e.getSource() == pop_up_centre) || (e.getSource() == bout_centre) || (e.getSource() == sous_menu_alignement_centre)) {
                modifierAlignementTexte("centre", zone_texte.getSelectedText());
            } else if ((e.getSource() == pop_up_droite) || (e.getSource() == bout_droite) || (e.getSource() == sous_menu_alignement_droite)) {
                modifierAlignementTexte("droite", zone_texte.getSelectedText());
            } else if ((e.getSource() == pop_up_justifie) || (e.getSource() == bout_justifie) || (e.getSource() == sous_menu_alignement_justifie)) {
                modifierAlignementTexte("justifie", zone_texte.getSelectedText());
            } else if ((e.getSource() == sous_menu_formatage_defaut) || (e.getSource() == pop_up_formatage_defaut)) {
                modifierTexteDefaultStyle(zone_texte.getSelectedText());
            } else if (e.getSource() == sous_menu_francais) {
                traducteur = ResourceBundle.getBundle("content.textes.interface.texte", francais);
                initStringWithRightLanguage();
            } else if (e.getSource() == sous_menu_anglais) {
                traducteur = ResourceBundle.getBundle("content.textes.interface.texte", anglais);
                initStringWithRightLanguage();
            } else if (e.getSource() == sous_menu_statistiques) {
                updateTexteInZoneTexte();

                if (sous_menu_francais.isSelected()) {
                    new Statistiques("Francais", texte_doc);
                } else {
                    new Statistiques("Anglais", texte_doc);
                }
            } else if (e.getSource() == sous_menu_new_fenetre) {
                new Editeur();
            } else if (e.getSource() == sous_menu_fermer_fenetre) {
                testEnregistrerFichier("fermer");
            } else if (e.getSource() == sous_menu_aide) {
                if (sous_menu_francais.isSelected()) {
                    new Aide(lireFichier("/content/textes/raccourcis/editeur_s/raccourcis"));
                } else {
                    new Aide(lireFichier("/content/textes/raccourcis/editeur_s/raccourcis_en"));
                }
            } else if (e.getSource() == sous_menu_site) {
                ouvrirURL(Constantes.URL_SITE);
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
