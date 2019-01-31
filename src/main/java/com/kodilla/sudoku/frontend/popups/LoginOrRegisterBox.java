package com.kodilla.sudoku.frontend.popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginOrRegisterBox {
    private static boolean isLogin;

    public static boolean getDecision(){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //below windows can't be entered before dealing with this one
        window.setTitle("Welcome");
        window.setWidth(600);

        Button loginButton = new Button();
        loginButton.setText("LOGIN");
        loginButton.setOnMouseClicked(e->{
            isLogin = true;
            window.close();
        });

        Button registerButton = new Button();
        registerButton.setText("REGISTER");
        registerButton.setOnMouseClicked(e->{
            isLogin = false;
            window.close();
        });

        VBox windowLayout = new VBox(loginButton, registerButton);
        windowLayout.setSpacing(10);
        windowLayout.setPadding(new Insets(5,5,5,5));
        windowLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(windowLayout);
        window.setScene(scene);
        window.showAndWait();

        return isLogin;

    }
}
