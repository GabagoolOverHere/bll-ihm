package eu.unareil.ihm;

import eu.unareil.bll.BLLException;
import eu.unareil.bll.ProduitManager;
import eu.unareil.bo.Produit;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        ProduitManager produitManager = ProduitManager.getInstance();
        ObservableList<Produit> produits = null;
        try {
            produits = FXCollections.observableArrayList(produitManager.getLesElements());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ProduitTableView tableProduits = new ProduitTableView(produits);
        var scene = new Scene(tableProduits, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}