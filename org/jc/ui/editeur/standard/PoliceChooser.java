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
 * Classe repr�sentant la fen�tre de choix de la police pour l'�diteur standard.
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 */
public class PoliceChooser extends JFrame {
    /* El�ments graphiques. */

    private JList liste_polices;
    private JScrollPane scroll_liste;

    /* Polices install�es sur le syst�me. */
    private final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final String[] polices = ge.getAvailableFontFamilyNames();

    /* Autres. */
    private String police_choisie;
    private boolean isClosingRight = false;

    public PoliceChooser() {
        this.setTitle("Police");
        this.setSize(250, 120);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/content/images/icone.png")));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initContainer();

        this.getContentPane().add(scroll_liste);
        this.setResizable(false);
        this.setVisible(true);
    }

    /* M�thode graphique. */
    
    /**
     * M�thode permettant d'initialiser le contenu de la fen�tre.
     */
    private void initContainer() {
        liste_polices = new JList(polices);
        liste_polices.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        liste_polices.setLayoutOrientation(JList.VERTICAL);
        liste_polices.setVisibleRowCount(-1);
        liste_polices.setToolTipText("Double-cliquez sur une police pour la choisir.");

        liste_polices.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    police_choisie = (String) liste_polices.getSelectedValue();
                    isClosingRight = true;
                    dispose();
                }
            }
        });

        scroll_liste = new JScrollPane(liste_polices);
    }

    /* Accesseur. */
    
    /**
     * M�thode permettant de r�cup�rer la police choisie par l'utilisateur.
     *
     * @return La police choisie par l'utilisateur.
     */
    public String getPoliceChoisie() {
        return police_choisie;
    }

    /**
     * M�thode permettant de savoir si une police a �t� choisie ou si la fen�tre
     * a �t� ferm�e sans choix.
     *
     * @return <b>true</b> si une police a �t� choisie.<br /><b>false</b> sinon.
     */
    public boolean isClosingRight() {
        return this.isClosingRight;
    }
}
