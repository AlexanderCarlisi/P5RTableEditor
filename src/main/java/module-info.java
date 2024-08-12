module com.p5rte {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.p5rte to javafx.fxml;
    exports com.p5rte;
    exports com.p5rte.GUI to javafx.graphics, javafx.fxml;
    opens com.p5rte.GUI to javafx.fxml;
}