module com.example.crud {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires mysql.connector.j;

    opens com.example.crud to javafx.fxml;
    opens com.example.crud.controller to javafx.fxml;
    opens com.example.crud.model to javafx.base; // 🔥 ESSA LINHA RESOLVE

    exports com.example.crud;
    exports com.example.crud.controller;
    exports com.example.crud.model;
}