/*
 * Copyright (C) 2017 Kasirgalabs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.kasirgalabs.etumulator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class DocumentMenuController implements Initializable {
    private static final Logger LOG = Logger.getLogger(DocumentMenuController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void newOnAction(ActionEvent event) {
        DocumentChooser documentChooser = Registry.get(DocumentChooser.class);
        documentChooser.createDocument();
    }

    @FXML
    private void openOnAction(ActionEvent event) throws IOException {
        DocumentChooser documentChooser = Registry.get(DocumentChooser.class);
        documentChooser.openDocument();
    }

    @FXML
    private void saveOnAction(ActionEvent event) {
        Document document = Registry.get(Document.class);
        try {
            document.saveDocument();
        } catch(IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void saveAsOnaction(ActionEvent event) throws IOException {
        DocumentChooser documentChooser = Registry.get(DocumentChooser.class);
        Document document = Registry.get(Document.class);
        File file = documentChooser.chooseDocument();
        if(file == null) {
            return;
        }
        document.setTargetFile(file);
        document.saveDocument();
    }
}
