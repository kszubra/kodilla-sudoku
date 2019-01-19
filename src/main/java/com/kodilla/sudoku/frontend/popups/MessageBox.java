package com.kodilla.sudoku.frontend.popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MessageBox {

    public static void displayMessage(String windowTitle, String message){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //below windows can't be entered before dealing with this one
        window.setTitle(windowTitle);
        window.setWidth(600);

        Label messageLabel = new Label();
        messageLabel.setText(message);

        Button closeButton = new Button();
        closeButton.setText("Ok");
        closeButton.setOnMouseClicked(e->window.close());

        VBox windowLayout = new VBox(messageLabel, closeButton);
        windowLayout.setSpacing(10);
        windowLayout.setPadding(new Insets(5,5,5,5));
        windowLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(windowLayout);
        window.setScene(scene);
        window.showAndWait();

    }
}
