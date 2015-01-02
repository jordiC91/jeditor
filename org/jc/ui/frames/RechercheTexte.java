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
package org.jc.ui.frames;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.jc.ui.ZoneTextePerso;

/**
 * Classe représentant la fenètre de recherche et de remplacement de texte pour
 * les deux éditeurs.
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 * @version 1.0
 * @since jeditor 1.0
 */
public class RechercheTexte extends JFrame {
    
    /* Eléments graphiques. */

    private final JPanel container = new JPanel();
    private final JLabel lab_rechercher = new JLabel("Rechercher");
    private final Box box1 = Box.createHorizontalBox();
    private final ZoneTextePerso ta_rechercher = new ZoneTextePerso(50);
    private final JButton bout_rechercher = new JButton("Rechercher");
    private final JLabel lab_remplacer = new JLabel("Remplacer par");
    private final Box box2 = Box.createHorizontalBox();
    private final ZoneTextePerso ta_remplacer = new ZoneTextePerso(50);
    private final JButton bout_remplacer = new JButton("Remplacer");

    /* Autres. */
    
    private boolean isRechercherSelected = false;
    private boolean isRemplacerSelected = false;
    
    private String texte_a_rechercher;
    private String texte_a_remplacer;
    private ResourceBundle traducteur;

    public RechercheTexte(String langue) {
        if (langue.equals("francais")) {
            this.traducteur = ResourceBundle.getBundle("content.textes.interface.texte", new Locale("fr", "Francais"));
        } else {
            this.traducteur = ResourceBundle.getBundle("content.textes.interface.texte", new Locale("en", "Anglais"));
        }

        this.setTitle(traducteur.getString("titre_fenetre_rechercher"));
        this.setSize(410, 170);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/content/images/icone.png")));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().add(container);

        initContainers();
        initStrings();
        setListener();

        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * Méthode permettant d'initialiser le contenu de la fenètre.
     * 
     * @since RechercheTexte 1.0
     */
    private void initContainers() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

        container.add(lab_rechercher);
        lab_rechercher.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        container.add(box1);
        box1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box1.add(ta_rechercher);
        ta_rechercher.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ta_rechercher.setMaximumSize(new Dimension(250, 20));
        ta_rechercher.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        box1.add(Box.createHorizontalStrut(30));
        box1.add(bout_rechercher);
        bout_rechercher.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        container.add(Box.createVerticalStrut(10));

        container.add(lab_remplacer);
        lab_remplacer.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        container.add(box2);
        box2.add(ta_remplacer);
        ta_remplacer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ta_remplacer.setMaximumSize(new Dimension(250, 20));
        ta_remplacer.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        box2.add(Box.createHorizontalStrut(30));
        box2.add(bout_remplacer);
        bout_remplacer.setAlignmentX(JComponent.LEFT_ALIGNMENT);
    }

    /**
     * Méthode permettant de mettre en place les listeners ("écouteurs")
     * nécéssaires.<br />
     */
    private void setListener() {
        bout_rechercher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                texte_a_rechercher = ta_rechercher.getText();

                if (texte_a_rechercher.length() > 0) {
                    isRechercherSelected = true;
                    dispose();
                }
            }
        });

        bout_remplacer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                texte_a_rechercher = ta_rechercher.getText();
                texte_a_remplacer = ta_remplacer.getText();

                if ((texte_a_rechercher.length() > 0) && (texte_a_remplacer.length() > 0)) {
                    isRemplacerSelected = true;
                    dispose();
                }
            }
        });

        ta_rechercher.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (ta_rechercher.getText().length() > 0)) {
                    bout_rechercher.doClick();
                }
            }
        });

        ta_remplacer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (ta_rechercher.getText().length() > 0) && (ta_remplacer.getText().length() > 0)) {
                    bout_rechercher.doClick();
                }
            }
        });
    }
    
    /**
     * Méthode permettant de savoir si l'utilisateur veut rechercher un texte.
     *
     * @return <b>true</b> si l'utilisateur appuie sur le bouton "Rechercher".<br />
     * <b>false</b> sinon.
     */
    public boolean isRechercherSelected() {
        return this.isRechercherSelected;
    }

    /**
     * MÃ©thode permettant de savoir si l'utilisateur veut remplacer un texte.
     *
     * @return <b>true</b> si l'utilisateur appuie sur le bouton "Remplacer par".<br />
     * <b>false</b> sinon.
     */
    public boolean isRemplacerSelected() {
        return this.isRemplacerSelected;
    }

    /**
     * Méthode permettant de récupérer le texte recherché par l'utilisateur.
     *
     * @return Le texte cherché par l'utilisateur.
     */
    public String getTexteARechercher() {
        return this.texte_a_rechercher;
    }

    /**
     * Méthode permettant de récupérer le texte qui remplacera le texte
     * recherché par l'utilisateur.
     *
     * @return Le texte qui remplacera le texte recherché par l'utilisateur.
     */
    public String getTexteARemplacer() {
        return this.texte_a_remplacer;
    }

    /**
     * Méthode permettant d'initialiser les différents textes de la fenètre avec
     * la langue choisie par l'utilisateur.
     */
    private void initStrings() {
        lab_rechercher.setText(traducteur.getString("lab_rechercher"));
        bout_rechercher.setText(traducteur.getString("bout_rechercher"));
        lab_remplacer.setText(traducteur.getString("lab_remplacer"));
        bout_remplacer.setText(traducteur.getString("bout_remplacer"));
    }
}