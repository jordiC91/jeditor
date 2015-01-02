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
import javax.swing.*;

/**
 * Classe repr�sentant la fen�tre de choix des langages color�s par l'�diteur
 * pour programmeur.
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 */
public class LangageChooser extends JFrame {
    /* El�ments graphiques. */

    private final JPanel container_centre = new JPanel();
    private final Box box_choix = Box.createVerticalBox();
    private final JCheckBox[] choix = {new JCheckBox("Bash"), new JCheckBox("C"), new JCheckBox("C++"), new JCheckBox("HTML"), new JCheckBox("Java"), new JCheckBox("Javascript"), new JCheckBox("PHP"), new JCheckBox("Python"), new JCheckBox("Ruby"), new JCheckBox("Scala"), new JCheckBox("SQL")};

    private final JPanel container_bas = new JPanel();
    private final JToggleButton bout_ok = new JToggleButton("Valider");
    private final JToggleButton bout_annuler = new JToggleButton("Annuler");

    public LangageChooser() {
        this.setTitle("Langages");
        this.setSize(190, 310);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/content/images/icone.png")));

        this.setLayout(new BorderLayout());
        this.add(container_centre, BorderLayout.CENTER);
        this.add(container_bas, BorderLayout.SOUTH);

        initContainers();
        setListener();

        this.setVisible(true);
        this.setResizable(false);
    }

    /* M�thodes graphiques. */
    
    /**
     * M�thode permettant d'initialiser le contenu de la fen�tre.
     */
    private void initContainers() {
        container_centre.setLayout(new BoxLayout(container_centre, BoxLayout.Y_AXIS));
        container_centre.add(box_choix, BorderLayout.NORTH);

        box_choix.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (JCheckBox choix1 : choix) {
            box_choix.add(choix1);
        }

        container_bas.add(bout_ok);
        container_bas.add(bout_annuler);
    }

    /**
     * M�thode permettant de mettre en place les listeners ("�couteurs")
     * n�c�ssaires.<br />
     */
    private void setListener() {
        bout_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                bout_ok.setSelected(true);
                dispose();
            }
        });

        bout_annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                bout_annuler.setSelected(true);
                dispose();
            }
        });
    }

    /* Autres. */
    /**
     * M�thode permettant de cocher une liste de langages lors de l'ouverture de
     * la fen�tre de choix des langages.<br />
     * Les langages � cocher correspondent aux langages "d�j� choisis".
     *
     * @param langages La liste des langages � cocher (String[]).
     */
    public void setSelectedLangages(String[] langages) {
        for (JCheckBox choix1 : this.choix) {
            for (String langage : langages) {
                if (choix1.getText().equals(langage)) {
                    choix1.setSelected(true);
                }
            }
        }
    }

    /**
     * M�thode permettant de savoir quels langages ont �t� choisis.<br />
     * Les langages coch�s correspondent aux langages qui ont �t� choisis.
     *
     * @return La liste des langages coch�s (String[]).
     */
    public String[] getSelectedItem() {
        String[] langage = {"bash", "c", "cpp", "html", "java", "javascript", "php", "python", "ruby", "scala", "sql"};
        int compteur_selectionne = 0;

        for (JCheckBox choix1 : choix) {
            if (choix1.isSelected() == true) {
                compteur_selectionne++;
            }
        }

        String[] langage_choisi = new String[compteur_selectionne];
        compteur_selectionne = 0;

        for (int i = 0; i < choix.length; i++) {
            if (choix[i].isSelected() == true) {
                langage_choisi[compteur_selectionne++] = langage[i];
            }
        }

        return langage_choisi;
    }

    /**
     * M�thode permettant de savoir si la fen�tre de choix des langages a �t�
     * ferm�es avec la croix rouge ou en appuyant sur le bouton "OK".<br />
     * Dans un des cas, on actualise la liste des langages, et pas ans l'autre.
     *
     * @return <b>true</b> si la fen�tre a �t� ferm�e en appuyant sur le bouton
     * "OK".<br/><b>false</b> sinon.
     */
    public boolean isOKSelected() {
        return bout_ok.isSelected();
    }
}
