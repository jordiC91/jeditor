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

import java.awt.event.*;
import javax.swing.*;
import javax.swing.undo.*;

/**
 * Classe représentant les objets capable d'annuler et de refaire une action.
 *
 * @author Jordi CHARPENTIER jordi.charpentier@gmail.com
 * @version 1.0
 * @since jeditor 1.0
 */
public class UndoRedo {

    public static Action getUndoAction(UndoManager manager) {
        return new UndoAction(manager, "");
    }

    public static Action getRedoAction(UndoManager manager) {
        return new RedoAction(manager, "");
    }

    public abstract static class UndoRedoAction extends AbstractAction {

        UndoManager undoManager = new UndoManager();

        protected UndoRedoAction(UndoManager manager, String name) {
            super(name);
            undoManager = manager;
        }
    }

    public static class UndoAction extends UndoRedoAction {

        public UndoAction(UndoManager manager, String name) {
            super(manager, name);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                undoManager.undo();
            } catch (CannotUndoException cannotUndoException) {
                return;
            }
        }
    }

    public static class RedoAction extends UndoRedoAction {

        public RedoAction(UndoManager manager, String name) {
            super(manager, name);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                undoManager.redo();
            } catch (CannotRedoException cannotRedoException) {
                return;
            }
        }
    }
}