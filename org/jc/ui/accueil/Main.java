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

import javax.swing.*;

/**
 * Classe contenant la méthode <b>main</b>, méthode principale du programme.
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 * @version 1.0
 * @since jeditor 1.0
 */
public class Main {

    public static void main(String[] args) {
        /* Le code suivant permet d'utiliser le LaF du systême sur lequel JEditor est lancé. */

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            /* On ignore cette exception. */
        }

        /* Lancement de l'accueil de JEditor. */

        Accueil acc = new Accueil();
    }
}