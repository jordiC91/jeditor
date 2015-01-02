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
import java.io.*;
import java.net.*;
import javax.swing.*;

/**
 * Classe représentant la fenêtre de recherche personnalisée dans la
 * documentation des langages de l'éditeur pour programmeur.
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 */
public class CustomRecherche extends JFrame {
    /* Eléments graphiques. */

    private final JPanel container_haut = new JPanel();
    private final Box box_haut = Box.createHorizontalBox();
    private final JTextField texte_recherche = new JTextField();
    private final JComboBox liste_langage = new JComboBox();

    private final JPanel container_bas = new JPanel();
    private final JButton bout_rechercher = new JButton("Rechercher");

    /* Constante. */
    private final static String[] langage = {"bash", "c", "c++", "html", "java", "javascript", "php", "python", "ruby", "scala", "sql"};

    public CustomRecherche() {
        this.setTitle("Recherche");
        this.setSize(300, 95);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/content/images/icone.png")));

        this.setLayout(new BorderLayout());
        this.add(container_haut, BorderLayout.NORTH);
        this.add(container_bas, BorderLayout.SOUTH);

        initContainer();

        this.setVisible(true);
        this.setResizable(false);
    }

    /* Méthode graphique. */
    /**
     * Méthode permettant d'initialiser le contenu de la fenêtre.
     */
    private void initContainer() {
        container_haut.setLayout(new BoxLayout(container_haut, BoxLayout.Y_AXIS));

        container_haut.add(Box.createVerticalStrut(10));
        container_haut.add(box_haut);
        box_haut.add(Box.createHorizontalStrut(5));
        box_haut.add(texte_recherche);
        texte_recherche.setMaximumSize(new Dimension(160, 20));
        texte_recherche.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        box_haut.add(Box.createHorizontalStrut(5));
        box_haut.add(liste_langage);

        for (String langage1 : langage) {
            liste_langage.addItem(langage1);
        }

        liste_langage.setMaximumSize(liste_langage.getPreferredSize());

        container_bas.add(bout_rechercher);
        bout_rechercher.setAlignmentX(Component.CENTER_ALIGNMENT);
        bout_rechercher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String extension = (String) liste_langage.getSelectedItem();
                String texte = texte_recherche.getText(), url = "";

                switch (extension) {
                    case "bash":
                        url = "http://www.google.fr/search?q=site:" + "http://www.gnu.org/software/bash/manual/bashref.html+" + texte;
                        break;
                    case "c":
                        url = "http://www.google.fr/search?q=site:" + "http://www.acm.uiuc.edu/webmonkeys/book/c_guide/+" + texte;
                        break;
                    case "c++":
                        url = "http://www.google.fr/search?q=site:" + "http://www.cplusplus.com/doc/tutorial/+" + texte;
                        break;
                    case "html":
                        url = "http://www.google.fr/search?q=site:" + "http://fr.html.net/tutorials/html/+" + texte;
                        break;
                    case "java":
                        url = "http://www.google.fr/search?q=site:" + "http://download.oracle.com/javase/6/docs/api/+" + texte;
                        break;
                    case "javascript":
                        url = "http://www.google.fr/search?q=site:" + "http://www.webreference.com/js/+" + texte;
                        break;
                    case "php":
                        url = "http://www.google.fr/search?q=site:" + "http://php.net/manual/fr/index.php+" + texte;
                        break;
                    case "python":
                        url = "http://www.google.fr/search?q=site:" + "http://docs.python.org/+" + texte;
                        break;
                    case "ruby":
                        url = "http://www.google.fr/search?q=site:" + "http://www.ruby-doc.org/+" + texte;
                        break;
                    case "scala":
                        url = "http://www.google.fr/search?q=site:" + "http://www.scala-lang.org/api/current/index.html+" + texte;
                        break;
                    case "sql":
                        url = "http://www.google.fr/search?q=site:" + "http://sgbd.developpez.com/cours/+" + texte;
                        break;
                }

                ouvrirUrl(url);
                dispose();
            }
        });
    }

    /* Autre. */
    
    /**
     * Méthode permettant d'ouvrir une URL via le navigateur par défaut de
     * l'utilisateur.
     *
     * @param url L'URL à visiter.
     * @return <b>true</b> si l'URL a correctement été ouverte dans le
     * navigateur par défaut.<br /><b>false</b> sinon.
     */
    public boolean ouvrirUrl(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));

            return true;
        } catch (URISyntaxException | IOException e1) {
            return false;
        }
    }
}
