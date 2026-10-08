module com.examenia.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires io.github.cdimascio.dotenv.java;

    opens com.examenia.demo to javafx.fxml;
    exports com.examenia.demo;
    requires java.net.http;
}