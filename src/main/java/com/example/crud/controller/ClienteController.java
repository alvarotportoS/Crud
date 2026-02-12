package com.example.crud.controller;

import com.example.crud.dao.ClienteDao;
import com.example.crud.model.Cliente;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClienteController {
    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;
    @FXML private TextField txtCep;
    @FXML private TableView<Cliente> tabelaCliente;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colCpf;
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, String> colCep;

    private ClienteDao dao = new ClienteDao();
    private Cliente clienteSelecionado;

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        atualizarTabela();
    }

    private void atualizarTabela() {
        try {
            tabelaCliente.setItems(FXCollections.observableArrayList(dao.listarTodos()));
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    public void salvarCliente() {
        try {
            if (clienteSelecionado == null) {
                dao.salvar(new Cliente(txtNome.getText(), txtCpf.getText(), txtTelefone.getText(), txtEmail.getText(), txtCep.getText()));
            } else {
                clienteSelecionado.setNome(txtNome.getText());
                clienteSelecionado.setCpf(txtCpf.getText());
                clienteSelecionado.setTelefone(txtTelefone.getText());
                clienteSelecionado.setEmail(txtEmail.getText());
                clienteSelecionado.setCep(txtCep.getText());

                dao.atualizar(clienteSelecionado);
            }
            atualizarTabela();
            limparCampos();
        } catch (Exception e) {
            exibirAlerta("Erro", e.getMessage());
        }
    }

    @FXML
    public void excluirCliente() {
        if (clienteSelecionado != null) {
            try {
                dao.deletar(clienteSelecionado.getId());
                atualizarTabela();
                limparCampos();
            } catch (Exception e) { exibirAlerta("Erro", e.getMessage()); }
        }
    }

    @FXML
    public void selecionarItem() {
        if (tabelaCliente == null) {
            return;
        }

        clienteSelecionado = tabelaCliente.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {
            txtNome.setText(clienteSelecionado.getNome());
            txtCpf.setText(clienteSelecionado.getCpf());
            txtTelefone.setText(clienteSelecionado.getTelefone());
            txtEmail.setText(clienteSelecionado.getEmail());
            txtCep.setText(clienteSelecionado.getCep());
        }
    }

    @FXML
    public void limparCampos() {
        txtNome.clear();
        txtCpf.clear();
        clienteSelecionado = null;
        tabelaCliente.getSelectionModel().clearSelection();
    }
    private void exibirAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.show();
    }
}