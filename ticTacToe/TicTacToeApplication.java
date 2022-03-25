package ticTacToe;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/*
 *@author SaltyHobo
 *
 * My submission for part 13_13.TicTacToe of University of Helsinki’s MOOC on Java programming.
 */

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
