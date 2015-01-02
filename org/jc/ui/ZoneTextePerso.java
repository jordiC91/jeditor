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
package org.jc.ui;

import javax.swing.*;
import javax.swing.text.*;

/**
 * Classe représantant un JTextField personnalisé : il ne pourra pas contenir
 * qu'un nombre donné de caractère(s).
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 * @version 1.0
 * @since jeditor 1.0
 */
public class ZoneTextePerso extends JTextField {

    /**
     * La taille limite de la zone de texte.
     *
     * @since ZoneTextePerso 1.0
     */
    private int limite;

    public ZoneTextePerso(int limite) {
        this.limite = limite;
        setDocument(new LimiteDigitDocument(this.limite));
    }

    private class LimiteDigitDocument extends PlainDocument {

        /**
         * La taille limite de la zone de texte.
         *
         * @since LimiteDigitDocument 1.0
         */
        private int limite;

        public LimiteDigitDocument(int limite) {
            super();
            this.limite = limite;
        }

        @Override
        public void insertString(int off, String s, AttributeSet a) throws BadLocationException {
            if ((s.length() + getLength() > limite) || (s == null)) {
                return;
            }

            super.insertString(off, s, a);
        }
    }
}