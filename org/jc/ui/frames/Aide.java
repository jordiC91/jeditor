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
import javax.swing.*;

/**
 * Classe représentant la fenètre d'aide des éditeurs.
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 * @version 1.0
 * @since jeditor 1.0
 */
public class Aide extends JFrame {
    
    /* Eléments graphiques. */

    private final JPanel container = new JPanel();
    private final JEditorPane zone_texte = new JEditorPane();
    private final JScrollPane scroll_texte = new JScrollPane(zone_texte);
    private final JButton bout_ok = new JButton("OK");

    /* Autre. */
    
    /**
     * Le texte que vas afficher la fenètre.
     * 
     * @since Aide 1.0
     */
    private String texte;

    /**
     * Constructeur paramétré de la fenètre.
     * 
     * @param texte Le texte que vas afficher la fenètre.
     * 
     * @since Aide 1.0
     */
    public Aide(String texte) {
        this.texte = texte;

        this.setTitle("Aide");
        this.setSize(500, 250);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/content/images/icone.png")));
        this.getContentPane().add(container);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initContainer();

        this.setVisible(true);
        this.setResizable(false);
    }
    
    /**
     * MÃ©thode permettant d'initialiser le contenu de la fenètre.
     * 
     * @since Aide 1.0
     */
    private void initContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(Box.createVerticalStrut(5));

        container.add(scroll_texte);
        scroll_texte.setMaximumSize(new Dimension(450, 200));
        scroll_texte.setAlignmentX(Component.CENTER_ALIGNMENT);

        zone_texte.setContentType("text/html");
        zone_texte.setText(texte);

        zone_texte.setCaretPosition(0);
        zone_texte.setEditable(false);

        container.add(Box.createVerticalStrut(5));

        container.add(bout_ok);
        bout_ok.setAlignmentX(Component.CENTER_ALIGNMENT);
        bout_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        container.add(Box.createVerticalStrut(5));
    }
}