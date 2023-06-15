module com.example.wordsfilter {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wordsfilter to javafx.fxml;
    exports com.example.wordsfilter.server;
    opens com.example.wordsfilter.server to javafx.fxml;
    exports com.example.wordsfilter;
}