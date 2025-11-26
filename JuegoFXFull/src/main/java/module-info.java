module com.example.juegofxfull.juegofxfull {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.juegofxfull.juegofxfull to javafx.fxml;
    exports com.example.juegofxfull.juegofxfull;
}