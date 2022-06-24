package eu.unareil.ihm;

import eu.unareil.bo.Produit;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class ProduitTableView extends TableView<Produit> {
    private TableColumn<Produit, Integer> refProdCol;
    private TableColumn<Produit, String> libelleCol;
    private TableColumn<Produit, String> marqueCol;
    private TableColumn<Produit, Float> prixCol;
    private TableColumn<Produit, Long> qteCol;
    private TableColumn<Produit, String> typeCol;
    private TableColumn<Produit, LocalDate> dateLimiteConsoCol;
    private TableColumn<Produit, Integer> poidsCol;
    private TableColumn<Produit, String> parfumCol;
    private TableColumn<Produit, String> temperatureCol;
    private TableColumn<Produit, String> typeMineCol;
    private TableColumn<Produit, String> typeCartePostaleCol;

    public ProduitTableView(ObservableList<Produit> observableList) {
        refProdCol = new TableColumn<>("Ref Prod");
        refProdCol.setCellValueFactory(new PropertyValueFactory<>("refProd"));
        libelleCol = new TableColumn<>("Libelle");
        libelleCol.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        marqueCol = new TableColumn<>("Marque");
        marqueCol.setCellValueFactory(new PropertyValueFactory<>("marque"));
        prixCol = new TableColumn<>("Prix Unitaire");
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        qteCol = new TableColumn<>("Quantite Stock");
        qteCol.setCellValueFactory(new PropertyValueFactory<>("qteStock"));
        typeCol = new TableColumn<>("Type");
        dateLimiteConsoCol = new TableColumn<>("Date Limite Consommation");
        dateLimiteConsoCol.setCellValueFactory(new PropertyValueFactory<>("dateLimiteConso"));
        poidsCol = new TableColumn<>("Poids");
        poidsCol.setCellValueFactory(new PropertyValueFactory<>("poids"));
        parfumCol = new TableColumn<>("Parfum");
        parfumCol.setCellValueFactory(new PropertyValueFactory<>("parfum"));
        temperatureCol = new TableColumn<>("Temperature Conservation");
        temperatureCol.setCellValueFactory(new PropertyValueFactory<>("temperatureConservation"));
        typeMineCol = new TableColumn<>("Type Mine");
        typeMineCol.setCellValueFactory(new PropertyValueFactory<>("typeMine"));
        typeCartePostaleCol = new TableColumn<>("Type Carte Postale");
        typeCartePostaleCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        getColumns().addAll(refProdCol, libelleCol, marqueCol, prixCol, qteCol, typeCol, dateLimiteConsoCol, poidsCol, parfumCol, temperatureCol, typeMineCol, typeCartePostaleCol);
        setItems(observableList);
    }
}
