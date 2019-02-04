package com.kodilla.sudoku.frontend.popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MessageBox {

    public static void displayMessage(String windowTitle, String message){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //below windows can't be entered before dealing with this one
        window.setTitle(windowTitle);
        window.initStyle(StageStyle.UNDECORATED);
        window.setWidth(300);

        Label messageLabel = new Label();
        messageLabel.setText(message);
        messageLabel.setTextFill(Color.web("#b299e6"));
        messageLabel.setStyle("-fx-background-color: #29293d;");

        Button closeButton = new Button();
        closeButton.setText("Ok");
        closeButton.setTextFill(Color.web("#b299e6"));
        closeButton.setStyle("-fx-background-color: #403F40, linear-gradient(#403F40, #331D45)");
        closeButton.setOnMouseClicked(e->window.close());

        VBox windowLayout = new VBox(messageLabel, closeButton);
        windowLayout.setSpacing(10);
        windowLayout.setPadding(new Insets(5,5,5,5));
        windowLayout.setAlignment(Pos.CENTER);
        windowLayout.setStyle("-fx-background-color: #29293d, linear-gradient(#29293d, #211C24)");


        Scene scene = new Scene(windowLayout);
        window.setScene(scene);
        window.showAndWait();

    }
}
