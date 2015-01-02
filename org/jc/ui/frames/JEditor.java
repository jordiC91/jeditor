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
import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

/**
 * Classe représentant la fenêtre d'informations de JEditor.
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 * @version 1.0
 * @since jeditor 1.0
 */
public class JEditor extends JFrame {

    /* Eléments graphiques. */
    
    private final JPanel container = new JPanel();
    private final JPanel container_haut = new JPanel();
    private final JLabel lab_logo = new JLabel(new ImageIcon(getClass().getResource("/content/images/icone.png")));
    private final Box box = Box.createVerticalBox();
    private final JLabel lab_version = new JLabel();
    private final JLabel lab_auteur = new JLabel();
    private final JLabel lab_contact = new JLabel("Contact : contact@jordi-charpentier.com");
    private final JEditorPane zone_texte = new JEditorPane();
    private final JScrollPane scroll_texte = new JScrollPane(zone_texte);
    private final JButton bout_ok = new JButton("OK");
    
    /* Autre. */
    
    private ResourceBundle traducteur;

    /**
     * Constructeur paramétré de la fenêtre.
     * 
     * @param langue La langue d'affichage de la fenêtre.
     * 
     * @since JEditor 1.0
     */
    public JEditor(String langue) {
        if (langue.equals("francais")) {
            this.traducteur = ResourceBundle.getBundle("content.textes.interface.texte", new Locale("fr", "Francais"));
        } else {
            this.traducteur = ResourceBundle.getBundle("content.textes.interface.texte", new Locale("en", "Anglais"));
        }

        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/content/images/icone.png")));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("JEditor");
        this.getContentPane().add(container);

        initContainers();
        initStrings();

        this.setVisible(true);
        this.setResizable(false);
    }

    /**
     * Méthode permettant d'initialiser le contenu de la fenêtre.
     * 
     * @since JEditor 1.0
     */
    private void initContainers() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(Box.createVerticalStrut(5));

        container.add(container_haut);
        container_haut.add(lab_logo);
        lab_logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        container_haut.add(box);
        box.add(lab_version);

        lab_version.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(lab_auteur);
        lab_auteur.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(lab_contact);
        lab_contact.setAlignmentX(Component.LEFT_ALIGNMENT);

        container.add(scroll_texte);
        scroll_texte.setMaximumSize(new Dimension(250, 250));
        scroll_texte.setAlignmentX(Component.CENTER_ALIGNMENT);

        zone_texte.setContentType("text/html");
        zone_texte.setText("<font face=\"Arial\"<center>"
                + "<h3>GNU General Public Licence</h3></center></font>"
                + "<font face=\"Arial\" size=3>This program is free software; "
                + "you can redistribute it and/or modify it under the terms of "
                + "the GNU General Public License as published by the Free "
                + "Software Foundation; either version 3 of the License, or (at your "
                + "option) any later version. <br><br> This program is distributed "
                + "in the hope that it will be useful, but WITHOUT ANY WARRANTY; "
                + "without even the implied warranty of MERCHANTABILITY or FITNESS "
                + "FOR A PARTICULAR PURPOSE. See the GNU General Public License for "
                + "more details. <br><br> You should have received a copy of the GNU"
                + " General Public License along with this program; if not, write"
                + " to the Free Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.</font>");

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

    /**
     * Méthode permettant de lire un fichier qui se situe dans le ".jar" du
     * logiciel.
     *
     * @param path_file Le chemin du fichier � lire. Le fichier doit se situer
     * dans le ".jar".
     * 
     * @return Le contenu du fichier si la lecture s'est bien pass�e.<br />
     * <b>-1</b> sinon.
     * 
     * @since JEditor 1.0
     */
    private String lireFichier(String path_file) {
        String contenu_fichier = "";

        try {
            int n;
            InputStream fis = JEditor.this.getClass().getResourceAsStream(path_file);

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

    /**
     * Méthode permettant d'initialiser les différents textes de la fenêtre avec
     * la langue choisie par l'utilisateur.
     * 
     * @since JEditor 1.0
     */
    private void initStrings() {
        if (!lireFichier("/version").equals("-1")) {
            lab_version.setText("JEditor : " + lireFichier("/version"));
        } else {
            lab_version.setText(traducteur.getString("message_erreur_version"));
        }

        lab_auteur.setText(traducteur.getString("auteur") + " : Jordi CHARPENTIER");
    }
}