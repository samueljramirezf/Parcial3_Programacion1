module co.edu.uniquindio.poo.parcial3 {

    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.poo.parcial3 to javafx.fxml;
    exports co.edu.uniquindio.poo.parcial3;
    exports co.edu.uniquindio.poo.parcial3.viewController;
    opens co.edu.uniquindio.poo.parcial3.viewController to javafx.fxml;

}
