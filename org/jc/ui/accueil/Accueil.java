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
package org.jc.ui.accueil;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * Classe représentant la fenêtre d'accueil de JEditor.
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 * @version 1.0
 * @since jeditor 1.0
 */
public class Accueil extends JFrame {

    /* Eléments graphiques. */
    private final JTextPane container_centre = new JTextPane();
    private final JPanel container_bas = new JPanel();
    private final JButton bouton_editeur_normal = new JButton();
    private final JButton bouton_editeur_style = new JButton();
    private final JButton bouton_editeur_programmeur = new JButton();

    /* Autre. */
    private ActionsListener actionslistener = new ActionsListener();

    /**
     * Constructeur par défaut de la fenêtre.
     *
     * @since Accueil 1.0
     */
    public Accueil() {
        this.setTitle("Accueil");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 350);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/content/images/icone.png")));
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());
        this.add(container_centre, BorderLayout.NORTH);
        this.add(container_bas, BorderLayout.CENTER);

        initContainers();
        initLogo();

        setListener();

        this.setVisible(true);
        this.setResizable(false);
    }

    /**
     * Méthode permettant d'initialiser le contenu de la fenêtre.
     *
     * @since Accueil 1.0
     */
    private void initContainers() {
        container_centre.setEditable(false);

        container_bas.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 10, 20, 0);

        container_bas.add(bouton_editeur_normal, constraints);
        bouton_editeur_normal.setText("Normal");
        bouton_editeur_normal.setIcon(new ImageIcon(getClass().getResource("/content/images/accueil/editeur_normal.png")));

        constraints.gridy = 1;

        container_bas.add(bouton_editeur_style, constraints);
        bouton_editeur_style.setText("Style");
        bouton_editeur_style.setIcon(new ImageIcon(getClass().getResource("/content/images/accueil/editeur_style.png")));

        constraints.gridy = 2;

        container_bas.add(bouton_editeur_programmeur, constraints);
        bouton_editeur_programmeur.setText("Programmeur");
        bouton_editeur_programmeur.setIcon(new ImageIcon(getClass().getResource("/content/images/accueil/editeur_programmeur.png")));
    }

    /**
     * Méthode permettant de dessiner le logo de JEditor sur l'accueil.
     *
     * @return <b>0</b> si le logo a bien été ajouté.<br /><b>false</b>
     * sinon.
     *
     * @since Accueil 1.0
     */
    private int initLogo() {
        Style style_image = container_centre.addStyle("image", null);
        StyleConstants.setIcon(style_image, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/content/images/accueil/logo.png"))));

        try {
            StyledDocument document = (StyledDocument) container_centre.getStyledDocument();
            document.insertString(new Integer(1), "ignored", style_image);
        } catch (BadLocationException e1) {
            return -1;
        }

        return 0;
    }

    /**
     * Méthode permettant de mettre en place les listeners ("écouteurs")
     * nécéssaires.
     *
     * @since Accueil 1.0
     */
    private void setListener() {
        bouton_editeur_normal.addActionListener(actionslistener);
        bouton_editeur_style.addActionListener(actionslistener);
        bouton_editeur_programmeur.addActionListener(actionslistener);
    }

    /**
     * Classe interne représentant un action listener.<br />
     * Ici, le listener permet de détecter un click sur un objet.
     *
     * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
     * @version 1.0
     * @since jeditor 1.0
     */
    private class ActionsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if ((e.getSource() == bouton_editeur_normal)) {
                org.jc.ui.editeur.normal.Editeur ed = new org.jc.ui.editeur.normal.Editeur();
            } else if ((e.getSource() == bouton_editeur_style)) {
                org.jc.ui.editeur.standard.Editeur ed = new org.jc.ui.editeur.standard.Editeur();
            } else if ((e.getSource() == bouton_editeur_programmeur)) {
                org.jc.ui.editeur.programmeur.Editeur ed = new org.jc.ui.editeur.programmeur.Editeur();
            }
        }
    }
}
