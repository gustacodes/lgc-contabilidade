package com.lgc.contabilidade.lgcontabilidade.controller;
import com.lgc.contabilidade.lgcontabilidade.dao.ConexaoBD;
import com.lgc.contabilidade.lgcontabilidade.model.Funcionario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class FuncionarioController {

    @FXML
    private Text cargo;

    @FXML
    private TextField cargoCampo;

    @FXML
    private TextField codigoText;

    @FXML
    private Text codigo;

    @FXML
    private TextField codigoCampo;

    @FXML
    private Text nome;

    @FXML
    private TextField nomeCampo;

    private Connection conexao = ConexaoBD.getConnection();

    @FXML
    void buscar(ActionEvent event) {
        Funcionario busca = new Funcionario();
        busca.busca(codigoText.getText());

        nome.setText(Funcionario.nomeFuncionario.toUpperCase(Locale.ROOT));
        cargo.setText(Funcionario.cargoFuncionario.toUpperCase(Locale.ROOT));
        codigo.setText(Funcionario.codigoFuncionario.toUpperCase(Locale.ROOT));
    }

    @FXML
    void atualizar(ActionEvent event) {
        Funcionario altera = new Funcionario();
        altera.alterar(nomeCampo.getText(), cargoCampo.getText(), codigoText.getText());
    }
}
