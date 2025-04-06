module stockmanagementsystem.stockman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens stockmanagementsystem.stockman to javafx.fxml;
    exports stockmanagementsystem.stockman;
}