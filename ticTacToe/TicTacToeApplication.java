package ticTacToe;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class TicTacToeApplication extends Application {
    
    public void start(Stage window) {
        Board layout = new Board();
        Scene scene = new Scene(layout.getBoard());
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch(TicTacToeApplication.class);
    }
}
