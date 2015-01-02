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
import javax.swing.*;

/**
 * Classe représentant la fenêtre de choix de la taille pour l'éditeur standard.
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 */
public class SizeChooser extends JFrame {
    /* Eléments graphiques. */

    private JList liste_tailles;
    private JScrollPane scroll_liste;

    /* Constante. */
    private final String[] tailles = {"4", "6", "8", "10", "12", "13", "14", "16", "18", "20", "22", "24", "26", "28", "30", "32", "34", "36", "40", "44", "48", "54", "60", "66", "72", "80", "88", "96"};

    /* Autres. */
    private String taille_choisie;
    private boolean isClosingRight = false;

    public SizeChooser() {
        this.setTitle("Taille");
        this.setSize(250, 120);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/content/images/icone.png")));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initContainer();

        this.getContentPane().add(scroll_liste);
        this.setResizable(false);
        this.setVisible(true);
    }

    /* Méthode graphique. */
    
    /**
     * Méthode permettant d'initialiser le contenu de la fenêtre.
     */
    private void initContainer() {
        liste_tailles = new JList(tailles);
        liste_tailles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        liste_tailles.setLayoutOrientation(JList.VERTICAL);
        liste_tailles.setVisibleRowCount(-1);
        liste_tailles.setToolTipText("Double-cliquez sur une taille pour la choisir.");

        liste_tailles.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    taille_choisie = (String) liste_tailles.getSelectedValue();
                    isClosingRight = true;
                    dispose();
                }
            }
        });

        scroll_liste = new JScrollPane(liste_tailles);
    }

    /* Accesseur. */
    
    /**
     * Méthode permettant de récupérer la taille choisie par l'utilisateur.
     *
     * @return La taille choisie par l'utilisateur.
     */
    public String getTailleChoisie() {
        return taille_choisie;
    }

    /**
     * Méthode permettant de savoir si une police a été choisie ou si la fenêtre
     * a été fermée sans choix.
     *
     * @return <b>true</b> si une police a été choisie.<br /><b>false</b> sinon.
     */
    public boolean isClosingRight() {
        return this.isClosingRight;
    }
}
