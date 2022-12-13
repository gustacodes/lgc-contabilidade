package com.lgc.contabilidade.lgcontabilidade.controller;

import com.lgc.contabilidade.lgcontabilidade.model.Funcionario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Locale;

public class ExcluirController {

    @FXML
    private Text cargoEx;

    @FXML
    private Text codigoEx;

    @FXML
    private TextField excluirCodigo;

    @FXML
    private Text nomeEx;


    @FXML
    void buscar(ActionEvent event) {
        Funcionario busca = new Funcionario();
        busca.busca(excluirCodigo.getText());

        nomeEx.setText(Funcionario.nomeFuncionario.toUpperCase(Locale.ROOT));
        cargoEx.setText(Funcionario.cargoFuncionario.toUpperCase(Locale.ROOT));
        codigoEx.setText(Funcionario.codigoFuncionario);
    }

    @FXML
    void excluir(ActionEvent event) {
        Funcionario excluir = new Funcionario();
        excluir.excluir(excluirCodigo.getText());
    }

}
