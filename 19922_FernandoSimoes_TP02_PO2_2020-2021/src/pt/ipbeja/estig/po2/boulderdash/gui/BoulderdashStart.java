package pt.ipbeja.estig.po2.boulderdash.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipbeja.estig.po2.boulderdash.model.Board;

/**
 * @author Fernando Simões nº 19922
 */

public class BoulderdashStart extends Application{
    private String mapFile = "src/resources/map_1.txt";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Board board = new Board(mapFile);
        BoulderdashBoard boulderdashBoard  = new BoulderdashBoard(board);
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
