module com.example.vm {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.vm to javafx.fxml;
    exports com.example.vm;
}