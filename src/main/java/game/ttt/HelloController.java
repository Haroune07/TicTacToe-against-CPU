package game.ttt;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.*;

public class HelloController {

    @FXML
    private Label label;

    @FXML
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
    List<Button> listOfButtons = new ArrayList<>();
    private Button[] corners;
    private Button[][] wins;

    Random random = new Random();

    boolean isPlayerTurn = new Random().nextInt(2) == 1;
    boolean isOver = false;


    @FXML
    public void initialize() {
        Collections.addAll(listOfButtons, b1, b2, b3, b4, b5, b6, b7, b8, b9);
        corners = new Button[]{b1, b3, b7, b9};
        wins = new Button[][]{{b1, b2, b3}, {b4, b5, b6}, {b7, b8, b9}, {b1, b4, b7}, {b2, b5, b8}, {b3, b6, b9}, {b1, b5, b9}, {b3, b5, b7}};

        if (!isPlayerTurn && b5.getText().isEmpty()) {
            System.out.println("CPU");
            cpuMove(b5);
        }

        for (Button b : listOfButtons) {
            b.setOnAction(e -> playerMove(b));
        }
    }

    void playerMove(Button button) {

        if (isPlayerTurn) {
            button.setText("X");
            listOfButtons.remove(button);
            button.setDisable(true);
            checkWin();
        }

        isPlayerTurn = false;

        if (!listOfButtons.isEmpty()) {
            cpuMove();
        }

    }

    void cpuMove(Button button) {
        if (!isPlayerTurn) {
            button.setText("O");
            listOfButtons.remove(button);
            button.setDisable(true);
            isPlayerTurn = true;
            checkWin();
        }

    }

    public void cpuMove() {

        if (attemptWinningMove())return;
        if (attemptBlockMove())return;

        if (b5.getText().isEmpty() && !b5.isDisabled()){
            cpuMove(b5);
        }

        for (Button b : corners){
            if (b.getText().isEmpty()){
                cpuMove(b);
            }
        }

        if (!listOfButtons.isEmpty()){
            cpuMove(listOfButtons.get(random.nextInt(listOfButtons.size())));
        }

    }

    public boolean attemptWinningMove(){
        for (Button[] winCase : wins){
            if (winningMoveIf(winCase, "O")){
                return true;
            }
        }
        return false;
    }

    public boolean attemptBlockMove(){
        for (Button[] winCase : wins){
            if (winningMoveIf(winCase, "X")){
                return true;
            }
        }
        return false;
    }

    boolean winningMoveIf(Button[] winCase, String symbol){

        int sCount = 0;
        Button emptyButton = null;

        for (Button b : winCase){
            if (b.getText().equalsIgnoreCase(symbol)){
                sCount++;
            }else {
                emptyButton = b;
            }
        }

        if (sCount == 2 && emptyButton != null && !emptyButton.isDisabled()){
            cpuMove(emptyButton);
            return true;
        }
        return false;
    }

    public void reset() {
        isOver = false;

        listOfButtons.clear();
        Collections.addAll(listOfButtons, b1, b2, b3, b4, b5, b6, b7, b8, b9);

        label.setText("");

        for (Button b : listOfButtons) {
            b.setDisable(false);
            b.setText("");
            b.setStyle("-fx-background-color: gray");
        }

        isPlayerTurn = random.nextInt(2) == 1;

        if (!isPlayerTurn && b5.getText().isEmpty()) {
            cpuMove(b5);
        }
    }

    public void checkWin() {

        if (!isOver) {
            for (Button[] buttons : wins) {
                if (buttons[0].getText().equals(buttons[1].getText()) && buttons[1].getText().equals(buttons[2].getText()) && !buttons[0].getText().isEmpty()) {
                    String color = buttons[1].getText().equals("X") ? "-fx-text-fill: red" : "-fx-text-fill: green";

                    for (Button b : buttons) {
                        b.setStyle(color);
                    }

                    declareWinner(buttons[0].getText());
                    isOver = true;
                    return;
                }
            }
        }

        else {
            for (Button b : listOfButtons){
                b.setDisable(true);
            }
            listOfButtons.clear();
        }

    }

    public void declareWinner(String symbol){

        if (symbol.equalsIgnoreCase("x")){
            label.setText("You Win!");
        }

        else if (symbol.equalsIgnoreCase("o")){
            label.setText("CPU Wins!");
        }

    }

}