package com.lgc.contabilidade.lgcontabilidade.controller;

import com.lgc.contabilidade.lgcontabilidade.dao.ConexaoBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroController {

    @FXML
    private TextField cargo;

    @FXML
    private TextField codigo;

    @FXML
    private TextField nome;

    private Connection conexao = ConexaoBD.getConnection();

    @FXML
    void cadastrado(ActionEvent event) {

        if(nome.getText().isEmpty() || cargo.getText().isEmpty() || codigo.getText().isEmpty()){

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Cadastro");
            a.setHeaderText("Campo vazio");
            a.setContentText("Por favor, preencha todos os campos.");
            a.showAndWait();
        } else {

            String sql = "INSERT INTO cadastro VALUES (?, ?, ?)";

            PreparedStatement cadastro = null;
            try {
                cadastro = conexao.prepareStatement(sql);
                cadastro.setString(1, nome.getText());
                cadastro.setString(2, cargo.getText());
                cadastro.setInt(3, Integer.parseInt(codigo.getText()));
                cadastro.execute();
                cadastro.close();

                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Cadastro");
                a.setContentText("Funcionário cadastro com sucesso.");
                a.showAndWait();

            } catch (SQLException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erro no cadastro");
                a.setContentText("Verifique as informações e tente novamente");
                a.showAndWait();

                e.printStackTrace();

            }
        }
    }

}