package com.lgc.contabilidade.lgcontabilidade.model;

import com.lgc.contabilidade.lgcontabilidade.controller.FuncionarioController;
import com.lgc.contabilidade.lgcontabilidade.dao.ConexaoBD;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class Funcionario {

    private Connection conexao = ConexaoBD.getConnection();
    public static String nomeFuncionario = null, codigoFuncionario = null, cargoFuncionario = null;

    public void busca(String codigo){
        String sql = "SELECT * FROM cadastro WHERE codigo = (?)";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            stm.setString(1, codigo);
            ResultSet rs = stm.executeQuery();

            while(rs.next()){
                nomeFuncionario = rs.getString("nome");
                cargoFuncionario = rs.getString("cargo");
                codigoFuncionario = rs.getString("codigo");
            }

            if(codigoFuncionario == null){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setContentText("Funcionário não cadastrado ou código inválido.");
                a.showAndWait();

            }

        } catch (SQLException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setContentText("Houve um erro na consulta");
            a.showAndWait();
            e.printStackTrace();
        }

    }

    public void alterar(String nome, String cargo, String codigo){

        String update = "UPDATE cadastro SET nome = (?), cargo = (?) WHERE codigo = (?)";

        try {
            PreparedStatement stm = conexao.prepareStatement(update);
            stm.setString(1, nome);
            stm.setString(2, cargo);
            stm.setString(3, codigo);
            stm.execute();
            stm.close();

            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Cadastro atualizado");
            a.setContentText("O cadastro foi atualizado com sucesso.");
            a.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(String codigo){

        if(codigo == ""){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Funcionário não encontrado");
            a.setContentText("Verifique se o campo está vazio.");
            a.showAndWait();
        } else {
            String sql = "DELETE FROM cadastro WHERE codigo = (?)";

            try {
                PreparedStatement delete = conexao.prepareStatement(sql);
                delete.setString(1, codigo);
                delete.execute();
                delete.close();

                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Funcionário excluído");
                a.setContentText("O funcionário foi removido com sucesso.");
                a.showAndWait();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
