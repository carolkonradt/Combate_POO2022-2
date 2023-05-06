module ufpelpoo.combate_poo {
    requires javafx.controls;
    requires javafx.fxml;


    opens ufpelpoo.combate_poo to javafx.fxml;
    exports ufpelpoo.combate_poo;
    //exports classesProjeto;
    //opens classesProjeto to javafx.fxml;
}