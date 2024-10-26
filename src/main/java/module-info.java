module game.ttt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens game.ttt to javafx.fxml;
    exports game.ttt;
}