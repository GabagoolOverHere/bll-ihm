package eu.unareil.ihm;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class CreateForm extends Dialog {
    CreateForm() {
        StackPane root = new StackPane();
        root.setPadding(new Insets(20));

        Label labelTitle = new Label("Creer un nouveau produit.");
        Label labelLibelle = new Label("Libelle");
        TextField fieldLibelle = new TextField();
        Label labelMarque = new Label("Marque");
        TextField fieldMarque = new TextField();
        Label labelPrixUnitaire = new Label("Prix Unitaire");
        TextField fieldPrixUnitaire = new TextField();
        Label labelQteStock = new Label("Qte Stock");
        TextField fieldQteStock = new TextField();
        Label labelDateLimiteConso = new Label("Date Limite Conso");
        TextField fieldDateLimiteConso = new TextField();
        Label labelPoids = new Label("Poids");
        TextField fieldPoids = new TextField();
        Label labelParfum = new Label("Parfum");
        TextField fieldParfum = new TextField();
        Label labelTemperatureConso = new Label("Temperature Conso");
        TextField fieldTemperatureConso = new TextField();
        Label labelCouleur = new Label("Couleur");
        TextField fieldCouleur = new TextField();
        Label labelTypeMine = new Label("Type Mine");
        TextField fieldTypeMine = new TextField();
        Label labelTypeCartePostale = new Label("Type Carte Postale");
        TextField fieldTypeCartePostale = new TextField();

        Button validateButton = new Button("Valider");

        root.getChildren().addAll(labelTitle,
            labelLibelle,
            fieldLibelle,
            labelMarque,
            fieldMarque,
            labelPrixUnitaire,
            fieldPrixUnitaire,
            labelQteStock,
            fieldQteStock,
            labelDateLimiteConso,
            fieldDateLimiteConso,
            labelPoids,
            fieldPoids,
            labelParfum,
            fieldParfum,
            labelTemperatureConso,
            fieldTemperatureConso,
            labelCouleur,
            fieldCouleur,
            labelTypeMine,
            fieldTypeMine,
            labelTypeCartePostale,
            fieldTypeCartePostale,
            validateButton
        );

        Scene scene = new Scene(root, 600, 800);
    }
}
