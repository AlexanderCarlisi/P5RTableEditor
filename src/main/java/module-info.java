module com.p5rte {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.base;

    exports com.p5rte;
    exports com.p5rte.GUI;
    exports com.p5rte.GUI.EnemyControllers;
    exports com.p5rte.GUI.PartyControllers;
    exports com.p5rte.GUI.PersonaControllers;

    opens com.p5rte to javafx.graphics, javafx.fxml;
    opens com.p5rte.GUI to javafx.graphics, javafx.fxml;
    opens com.p5rte.GUI.EnemyControllers to javafx.graphics,  javafx.fxml;
    opens com.p5rte.GUI.PartyControllers to javafx.graphics, javafx.fxml;
    opens com.p5rte.GUI.PersonaControllers to javafx.graphics, javafx.fxml;
}