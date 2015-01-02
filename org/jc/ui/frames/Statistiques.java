/*
 * Copyright 2012 Jordi CHARPENTIER jordi.charpentier@gmail.com
 * 
 * This file is part of JEditor.

 * JEditor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

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
import java.util.regex.*;
import javax.swing.*;

/**
 * Classe représentant la fenÃ¨tre de statistiques des Ã©diteurs.
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 * @version 1.0
 * @since jeditor 1.0
 */
public class Statistiques extends JFrame {

    /* Eléments graphiques. */
    private final JPanel container_centre = new JPanel();
    private final JLabel lab_nb_lignes = new JLabel();
    private final JLabel lab_caracteres = new JLabel();
    private final JLabel lab_caracteres_sans_espace = new JLabel();
    private final JPanel container_bas = new JPanel();
    private final JButton bout_ok = new JButton("OK");

    /* Autres. */
    private String langage;
    private String texte;

    public Statistiques(String langue, String texte) {
        this.langage = langue;
        this.texte = texte;

        this.setTitle("Statistiques");
        this.setSize(250, 140);
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/content/images/icone.png")));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setLayout(new BorderLayout());
        this.add(container_centre, BorderLayout.CENTER);
        this.add(container_bas, BorderLayout.SOUTH);

        initContainers();

        this.setVisible(true);
        this.setResizable(false);
    }

    /**
     * MÃ©thode permettant d'initialiser le contenu de la fenï¿½tre.<br />
     * Chaque conteneur est initialisï¿½.
     */
    private void initContainers() {
        /* Conteneur central. */

        container_centre.setLayout(new BoxLayout(container_centre, BoxLayout.Y_AXIS));
        container_centre.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        container_centre.add(lab_nb_lignes);
        lab_nb_lignes.setAlignmentX(Component.LEFT_ALIGNMENT);

        container_centre.add(Box.createVerticalStrut(10));

        switch (this.langage) {
            case "Francais":
                lab_nb_lignes.setText("Nombre de lignes : " + (1 + compterLettre("\n")));
                break;
            case "Anglais":
                lab_nb_lignes.setText("Number of lines : " + (1 + compterLettre("\n")));
                break;
        }

        container_centre.add(lab_caracteres);
        lab_caracteres.setAlignmentX(Component.LEFT_ALIGNMENT);

        container_centre.add(Box.createVerticalStrut(10));

        if (this.langage.equals("Francais")) {
            if (texte.length() - (2 * compterLettre("\n")) > 0) {
                lab_caracteres.setText("Caractères : " + (texte.length() - (2 * compterLettre("\n"))));
            } else {
                lab_caracteres.setText("Caractères : 0");
            }
        } else if (this.langage.equals("Anglais")) {
            if (texte.length() - (2 * compterLettre("\n")) > 0) {
                lab_caracteres.setText("Characters : " + (texte.length() - (2 * compterLettre("\n"))));
            } else {
                lab_caracteres.setText("Words : 0");
            }
        }

        container_centre.add(lab_caracteres_sans_espace);
        lab_caracteres_sans_espace.setAlignmentX(Component.LEFT_ALIGNMENT);

        switch (this.langage) {
            case "Francais":
                if (texte.length() - compterLettre(" ") - (2 * compterLettre("\n")) > 0) {
                    lab_caracteres_sans_espace.setText("Caractères sans les espaces : " + (texte.length() - compterLettre(" ") - (2 * compterLettre("\n"))));
                } else {
                    lab_caracteres_sans_espace.setText("Caractères sans les espaces : 0");
                }   break;
            case "Anglais":
                if (texte.length() - compterLettre(" ") - (2 * compterLettre("\n")) > 0) {
                    lab_caracteres_sans_espace.setText("Characters without spaces : " + (texte.length() - compterLettre(" ") - (2 * compterLettre("\n"))));
                } else {
                    lab_caracteres_sans_espace.setText("Characters without spaces : 0");
            }   break;
        }

        /* Conteneur bas. */
        container_bas.setLayout(new BoxLayout(container_bas, BoxLayout.Y_AXIS));

        container_bas.add(bout_ok);
        bout_ok.setAlignmentX(Component.CENTER_ALIGNMENT);

        bout_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        container_bas.add(Box.createVerticalStrut(5));
    }

    /**
     * Méthode permettant de compter le nombre d'occurence d'une lettre dans le
     * texte.
     *
     * @param regex La lettre à chercher.
     *
     * @return Le nombre d'occurence de la lettre.
     */
    private int compterLettre(String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(this.texte);
        int occur = 0;

        while (matcher.find()) {
            occur++;
        }

        return occur;
    }
}
