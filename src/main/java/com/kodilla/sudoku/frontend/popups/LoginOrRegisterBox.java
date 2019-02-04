package com.kodilla.sudoku.frontend.popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginOrRegisterBox {
    private static boolean isLogin;

    public static boolean getDecision(){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //below windows can't be entered before dealing with this one
        window.setTitle("Welcome");
        window.initStyle(StageStyle.UTILITY);
        window.setWidth(600);

        Button loginButton = new Button();
        loginButton.setText("LOGIN");
        loginButton.setTextFill(Color.web("#b299e6"));
        loginButton.setStyle("-fx-background-color: #403F40, linear-gradient(#403F40, #331D45)");
        loginButton.setOnMouseClicked(e->{
            isLogin = true;
            window.close();
        });

        Button registerButton = new Button();
        registerButton.setText("REGISTER");
        registerButton.setTextFill(Color.web("#b299e6"));
        registerButton.setStyle("-fx-background-color: #403F40, linear-gradient(#403F40, #331D45)");
        registerButton.setOnMouseClicked(e->{
            isLogin = false;
            window.close();
        });

        HBox windowLayout = new HBox(loginButton, registerButton);
        windowLayout.setSpacing(10);
        windowLayout.setPadding(new Insets(5,5,5,5));
        windowLayout.setAlignment(Pos.CENTER);
        windowLayout.setStyle("-fx-background-color: #29293d, linear-gradient(#29293d, #211C24)");

        Scene scene = new Scene(windowLayout);
        window.setScene(scene);
        window.setHeight(100);
        window.setWidth(200);
        window.showAndWait();

        return isLogin;

    }
}
