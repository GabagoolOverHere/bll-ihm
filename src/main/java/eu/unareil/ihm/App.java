package eu.unareil.ihm;

import eu.unareil.bll.ProduitManager;
import eu.unareil.bo.Produit;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Button createButton = new Button("Creer");
        Button editButton = new Button("Modifier");
        Button deleteButton = new Button("Supprimer");

        HBox hbox = new HBox(createButton, editButton, deleteButton);

        ProduitManager produitManager = ProduitManager.getInstance();
        ObservableList<Produit> produits = null;
        try {
            produits = FXCollections.observableArrayList(produitManager.getLesElements());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ProduitTableView tableProduits = new ProduitTableView(produits);
        tableProduits.setEditable(true);

        Label label = new Label("Liste des produits");
        label.setStyle("-fx-font-weight: bold");

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(30, 30, 30, 30));
        vbox.getChildren().addAll(label, tableProduits);

        StackPane root = new StackPane();
        root.setPadding(new Insets(30, 30, 30, 30));
        root.getChildren().addAll(hbox, vbox);

        Scene scene = new Scene(root, 1600, 800);

        stage.setTitle("Gestionnaire de Produits");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}