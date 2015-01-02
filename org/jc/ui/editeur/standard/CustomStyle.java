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
 * Classe repr�sentant la fen�tre de style personnalis� de l'�diteur standard.
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 */
public class CustomStyle extends JFrame {
    /* El�ments graphiques. */

    private final JPanel container = new JPanel();
    private final JLabel lab_taille = new JLabel("Taille du texte");
    private final JComboBox box_taille = new JComboBox();
    private final JLabel lab_police = new JLabel("Police du texte");
    private final JComboBox box_police = new JComboBox();
    private final JLabel lab_couleur = new JLabel("Couleur choisie");
    private final JButton bout_couleur = new JButton();
    private final JLabel lab_style = new JLabel("Style du texte");
    private final JComboBox box_style = new JComboBox();
    private final JButton bout_ok = new JButton("Appliquer");

    /* Polices install�es sur le syst�me. */
    private final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final String[] polices = ge.getAvailableFontFamilyNames();

    /* Constantes. */
    private final String[] tailles = {"4", "6", "8", "10", "12", "13", "14", "16", "18", "20", "22", "24", "26", "28", "30", "32", "34", "36", "40", "44", "48", "54", "60", "66", "72", "80", "88", "96"};
    private final String[] style = {"Gras", "Italique", "Soulign�", "Barr�", "Normal"};

    /* Autre. */
    private boolean isOKSelected = false;

    public CustomStyle() {
        this.setTitle("Style");
        this.setSize(210, 220);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/content/images/icone.png")));
        this.getContentPane().add(container);

        initContainer();

        this.setVisible(true);
        this.setResizable(false);
    }

    /* M�thode graphique. */
    /**
     * M�thode permettant d'initialiser le contenu de la fen�tre.
     */
    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(Box.createVerticalStrut(5));
        container.add(lab_taille);
        lab_taille.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(box_taille);

        for (int i = 0; i < tailles.length; i++) {
            box_taille.addItem(tailles[i]);
        }

        box_taille.setMaximumSize(box_taille.getPreferredSize());
        container.add(Box.createVerticalStrut(5));

        container.add(lab_police);
        lab_police.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(box_police);

        for (int i = 0; i < polices.length; i++) {
            box_police.addItem(polices[i]);
        }

        box_police.setMaximumSize(box_police.getPreferredSize());
        container.add(Box.createVerticalStrut(5));

        container.add(lab_couleur);
        lab_couleur.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(bout_couleur);
        bout_couleur.setBackground(Color.BLACK);
        bout_couleur.setAlignmentX(Component.CENTER_ALIGNMENT);
        bout_couleur.setMaximumSize(new Dimension(40, 40));
        bout_couleur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bout_couleur.setBackground(JColorChooser.showDialog(null, "Couleur du texte", Color.WHITE));
            }
        });

        container.add(Box.createVerticalStrut(5));

        container.add(lab_style);
        lab_style.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(box_style);
        box_style.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (String style1 : style) {
            box_style.addItem(style1);
        }

        box_style.setMaximumSize(box_style.getPreferredSize());
        container.add(Box.createVerticalStrut(5));

        container.add(bout_ok);
        bout_ok.setAlignmentX(Component.CENTER_ALIGNMENT);
        bout_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isOKSelected = true;
                dispose();
            }
        });

        container.add(Box.createVerticalStrut(5));
    }

    /* Accesseurs. */
    
    /**
     * M�thode permettant de r�cup�rer la taille choisie par l'utilisateur.
     *
     * @return La taille choisie par l'utilisateur.
     */
    public String getTailleChoisie() {
        return (String) box_taille.getSelectedItem();
    }

    /**
     * M�thode permettant de r�cup�rer la police choisie par l'utilisateur.
     *
     * @return La police choisie par l'utilisateur.
     */
    public String getPoliceChoisie() {
        return (String) box_police.getSelectedItem();
    }

    /**
     * M�thode permettant de r�cup�rer la couleur choisie par l'utilisateur.
     *
     * @return La couleur choisie par l'utilisateur.
     */
    public Color getColorChoisie() {
        return bout_couleur.getBackground();
    }

    /**
     * M�thode permettant de r�cup�rer le style choisi par l'utilisateur.
     *
     * @return Le style choisi par l'utilisateur.
     */
    public String getStyleChoisi() {
        return (String) box_style.getSelectedItem();
    }

    /**
     * M�thode permettant de savoir comment la fen�tre a �t� ferm�e.
     *
     * @return <b>true</b> si la fen�tre a �t� ferm�e en appuyant sur le bouton
     * "Appuyer".<br /><b>false</b> sinon.
     */
    public boolean isOKSelected() {
        return this.isOKSelected;
    }

    /* Mutateurs. */
    
    /**
     * M�thode permettant de changer la taille s�lectionn�e par l'utilisateur.
     *
     * @param taille La nouvelle taille voulue.
     */
    public void setSelectedTaille(int taille) {
        if (taille > 0) {
            box_taille.setSelectedItem(taille);
        }
    }

    /**
     * M�thode permettant de changer la police s�lectionn�e par l'utilisateur.
     *
     * @param police La nouvelle police voulue.
     */
    public void setSelectedPolice(String police) {
        if (police != null) {
            box_police.setSelectedItem(police);
        }
    }

    /**
     * M�thode permettant de changer la couleur s�lectionn�e par l'utilisateur.
     *
     * @param couleur La nouvelle couleur voulue.
     */
    public void setSelectedColor(Color couleur) {
        if (couleur != null) {
            bout_couleur.setBackground(couleur);
        }
    }

    /**
     * M�thode permettant de changer le style s�lectionn� par l'utilisateur.
     *
     * @param style Le nouveau style voulu.
     */
    public void setSelectedStyle(String style) {
        if (style != null) {
            box_style.setSelectedItem(style);
        }
    }
}
