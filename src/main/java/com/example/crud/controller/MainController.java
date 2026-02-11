package com.example.crud.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class MainController {
    @FXML
    private BorderPane root;

    @FXML
    public void initialize(){
        carregarTela("Home.fxml");
    }
    @FXML
    public void abrirHome() {
        carregarTela("Home.fxml");
    }

    @FXML
    public void abrirProdutos() {
        carregarTela("Produtos.fxml");
    }

    @FXML
    public void abrirClientes() {
        carregarTela("Clientes.fxml");
    }
    @FXML
    public void abrirAjuda(ActionEvent event) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sobre o sistema");
        alert.setHeaderText("Informações do sistema");
        alert.setContentText(
                "Nome: Sistema de Reservas\n" +
                        "Versão: 1.0.0\n" +
                        "Desenvolvedor: Alvaro Porto\n" +
                        "Ano: 2026"
        );
        alert.showAndWait();
    }

    private void carregarTela(String fxml) {
        try {
            var url = getClass().getResource("/fxml/" + fxml);

            if (url == null) {
                System.out.println("FXML NÃO ENCONTRADO: " + fxml);
                return;
            }

            root.setCenter(FXMLLoader.load(url));

        } catch (Exception e) {
            System.out.println("ERRO AO CARREGAR FXML: " + fxml);
            e.printStackTrace();
        }
    }

}