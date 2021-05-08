package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BoulderdashStart extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        BoulderdashBoard boulderdashBoard  = new BoulderdashBoard();
        Scene scene = new Scene(boulderdashBoard);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Boulder Dash");
        primaryStage.setOnCloseRequest((e) -> {
            System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String args)
    {
        Application.launch(args);
    }
}
