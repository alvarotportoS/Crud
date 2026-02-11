package com.example.crud.controller;

import com.example.crud.dao.ProdutoDao;
import com.example.crud.model.Produto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProdutoController {
    @FXML private TextField txtNome;
    @FXML private TextField txtPreco;
    @FXML private TableView<Produto> tabelaProduto;
    @FXML private TableColumn<Produto, Integer> colId;
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TableColumn<Produto, Double> colPreco;

    private ProdutoDao dao = new ProdutoDao();
    private Produto produtoSelecionado;

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        atualizarTabela();
    }

    private void atualizarTabela() {
        try {
            tabelaProduto.setItems(FXCollections.observableArrayList(dao.listarTodos()));
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    public void salvarProduto() {
        try {
            if (produtoSelecionado == null) {
                dao.salvar(new Produto(txtNome.getText(), Double.parseDouble(txtPreco.getText())));
            } else {
                produtoSelecionado.setNome(txtNome.getText());
                produtoSelecionado.setPreco(Double.parseDouble(txtPreco.getText()));
                dao.atualizar(produtoSelecionado);
            }
            atualizarTabela();
            limparCampos();
        } catch (Exception e) { exibirAlerta("Erro", e.getMessage()); }
    }

    @FXML
    public void excluirProduto() {
        if (produtoSelecionado != null) {
            try {
                dao.deletar(produtoSelecionado.getId());
                atualizarTabela();
                limparCampos();
            } catch (Exception e) { exibirAlerta("Erro", e.getMessage()); }
        }
    }

    @FXML
    public void selecionarItem() {
        if (tabelaProduto == null) {
            return;
        }

        produtoSelecionado = tabelaProduto.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null) {
            txtNome.setText(produtoSelecionado.getNome());
            txtPreco.setText(String.valueOf(produtoSelecionado.getPreco()));
        }
    }

    @FXML
    public void limparCampos() {
        txtNome.clear();
        txtPreco.clear();
        produtoSelecionado = null;
        tabelaProduto.getSelectionModel().clearSelection();
    }
    private void exibirAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.show();
    }
}