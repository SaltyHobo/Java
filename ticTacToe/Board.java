package ticTacToe;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

/*
 *@author SaltyHobo
 *
 * My submission for part 13_13.TicTacToe of University of Helsinki’s MOOC on Java programming.
 */

public class Board {
    private BorderPane mainBoard;
    private Label infoLabel;
    private GridPane grid;
    private Button square1;
    private Button square2;
    private Button square3;
    private Button square4;
    private Button square5;
    private Button square6;
    private Button square7;
    private Button square8;
    private Button square9;
    
    public Board() {
        
        this.mainBoard = new BorderPane();
        
        this.infoLabel = new Label("Turn: X");
        
        this.grid = new GridPane();
        
        this.square1 = new Button(" ");
        this.square2 = new Button(" ");
        this.square3 = new Button(" ");
        this.square4 = new Button(" ");
        this.square5 = new Button(" ");
        this.square6 = new Button(" ");
        this.square7 = new Button(" ");
        this.square8 = new Button(" ");
        this.square9 = new Button(" ");
        
        this.square1.setFont(Font.font("Monospaced", 40));
        this.square2.setFont(Font.font("Monospaced", 40));
        this.square3.setFont(Font.font("Monospaced", 40));
        this.square4.setFont(Font.font("Monospaced", 40));
        this.square5.setFont(Font.font("Monospaced", 40));
        this.square6.setFont(Font.font("Monospaced", 40));
        this.square7.setFont(Font.font("Monospaced", 40));
        this.square8.setFont(Font.font("Monospaced", 40));
        this.square9.setFont(Font.font("Monospaced", 40));
        
        this.square1.setOnMouseClicked((event) -> {
            clicked(this.square1);
            checkForWin();
        });
        this.square2.setOnMouseClicked((event) -> {
            clicked(this.square2);
            checkForWin();
        });
        this.square3.setOnMouseClicked((event) -> {
            clicked(this.square3);
            checkForWin();
        });
        this.square4.setOnMouseClicked((event) -> {
            clicked(this.square4);
            checkForWin();
        });
        this.square5.setOnMouseClicked((event) -> {
            clicked(this.square5);
            checkForWin();
        });
        this.square6.setOnMouseClicked((event) -> {
            clicked(this.square6);
            checkForWin();
        });
        this.square7.setOnMouseClicked((event) -> {
            clicked(this.square7);
            checkForWin();
        });
        this.square8.setOnMouseClicked((event) -> {
            clicked(this.square8);
            checkForWin();
        });
        this.square9.setOnMouseClicked((event) -> {
            clicked(this.square9);
            checkForWin();
        });
    }
    
    public Parent getBoard() {
        this.grid.add(this.square1,0,0);
        this.grid.add(this.square2,1,0);
        this.grid.add(this.square3,2,0);
        this.grid.add(this.square4,0,1);
        this.grid.add(this.square5,1,1);
        this.grid.add(this.square6,2,1);
        this.grid.add(this.square7,0,2);
        this.grid.add(this.square8,1,2);
        this.grid.add(this.square9,2,2);
        
        this.mainBoard.setTop(this.infoLabel);
        this.mainBoard.setCenter(this.grid);
        
        return this.mainBoard;
    }
    
    public void clicked(Button button) {
        if (button.getText().equals(" ")) {
            if (this.infoLabel.getText().equals("Turn: X")) {
                button.setText("X");
                this.infoLabel.setText("Turn: O");
            }
            else if (this.infoLabel.getText().equals("Turn: O")) {
                button.setText("O");
                this.infoLabel.setText("Turn: X");
            }
        }
    }
    
    public void checkForWin() {
        if (this.square1.getText().matches("X|O")
                && this.square1.getText().equals(this.square2.getText()) 
                && this.square1.getText().equals(this.square3.getText())) {
            this.infoLabel.setText("The end!");
            return;
        }
        if (this.square1.getText().matches("X|O")
                && this.square1.getText().equals(this.square4.getText()) 
                && this.square1.getText().equals(this.square7.getText())) {
            this.infoLabel.setText("The end!");
            return;
        }
        if (this.square1.getText().matches("X|O")
                && this.square1.getText().equals(this.square5.getText()) 
                && this.square1.getText().equals(this.square9.getText())) {
            this.infoLabel.setText("The end!");
            return;
        }
        if (this.square4.getText().matches("X|O") 
                && this.square4.getText().equals(this.square5.getText()) 
                && this.square2.getText().equals(this.square6.getText())) {
            this.infoLabel.setText("The end!");
            return;
        }
        if (this.square7.getText().matches("X|O")
                && this.square7.getText().equals(this.square8.getText()) 
                && this.square7.getText().equals(this.square9.getText())) {
            this.infoLabel.setText("The end!");
            return;
        }
        if (this.square2.getText().matches("X|O")
                && this.square2.getText().equals(this.square5.getText()) 
                && this.square2.getText().equals(this.square8.getText())) {
            this.infoLabel.setText("The end!");
            return;
        }
        if (this.square3.getText().matches("X|O")
                && this.square3.getText().equals(this.square6.getText()) 
                && this.square3.getText().equals(this.square9.getText())) {
            this.infoLabel.setText("The end!");
            return;
        }
    }
}
