package com.kodilla.sudoku.frontend.popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginOrRegisterBox {
    private static boolean isLogin;
    private static final double WINDOW_HEIGHT = 100;
    private static final double WINDOW_WIDTH = 200;

    public static boolean getDecision(){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //below windows can't be entered before dealing with this one
        window.setTitle("Welcome");
        window.initStyle(StageStyle.UTILITY);
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

        HBox windowLayout = new HBox(loginButton, registerButton);
        windowLayout.setSpacing(10);
        windowLayout.setPadding(new Insets(5,5,5,5));
        windowLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(windowLayout);
        scene.getStylesheets().add("Sudoku.css");
        window.setScene(scene);
        window.setMinHeight(WINDOW_HEIGHT);
        window.setHeight(WINDOW_HEIGHT);
        window.setMinWidth(WINDOW_WIDTH);
        window.setWidth(WINDOW_WIDTH);
        window.showAndWait();

        return isLogin;

    }
}
