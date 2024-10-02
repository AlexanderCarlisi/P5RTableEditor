module com.p5rte {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.base;

    opens com.p5rte to javafx.fxml;
    exports com.p5rte;
    exports com.p5rte.GUI to javafx.graphics, javafx.fxml;
    opens com.p5rte.GUI to javafx.fxml;
    exports com.p5rte.GUI.EnemyControllers to javafx.graphics, javafx.fxml;
    opens com.p5rte.GUI.EnemyControllers to javafx.fxml;
    exports com.p5rte.GUI.PartyControllers to javafx.graphics, javafx.fxml;
    opens com.p5rte.GUI.PartyControllers to javafx.fxml;
    exports com.p5rte.GUI.PersonaControllers to javafx.graphics, javafx.fxml;
    opens com.p5rte.GUI.PersonaControllers to javafx.fxml;
}