module com.examenia.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.examenia.demo to javafx.fxml;
    exports com.examenia.demo;
}