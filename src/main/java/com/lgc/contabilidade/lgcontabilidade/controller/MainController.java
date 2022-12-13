package com.lgc.contabilidade.lgcontabilidade.controller;

import com.itextpdf.text.pdf.PdfPTable;
import com.lowagie.text.*;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.toolbox.Toolbox;
import com.lgc.contabilidade.lgcontabilidade.Main;
import com.lgc.contabilidade.lgcontabilidade.dao.ConexaoBD;
import com.lgc.contabilidade.lgcontabilidade.model.Registros;
import com.lowagie.text.Table;
import com.lowagie.text.Cell;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TextField codigo;

    @FXML
    private DatePicker date;

    @FXML
    private TableColumn<Registros, String> data;

    @FXML
    private TableColumn<Registros, Integer> entrada;

    @FXML
    private TextField entradaHora;

    @FXML
    private TextField entradaMinuto;

    @FXML
    private TableColumn<Registros, Float> extras;

    @FXML
    private Text funcionario;

    @FXML
    private Text cargo;

    @FXML
    private TableColumn<Registros, Integer> saida;

    @FXML
    private TextField saidaAlmocoHora;

    @FXML
    private TextField saidaAlmocoMinuto;

    @FXML
    private TableColumn<Registros, Integer> saidaCasa;

    @FXML
    private TextField saidaHora;

    @FXML
    private TextField saidaMinuto;

    @FXML
    private TableView<Registros> tabelaRegistro;

    @FXML
    private TableColumn<Registros, Float> total;

    @FXML
    private TableColumn<Registros, Integer> volta;

    @FXML
    private TextField voltaAlmocoHora;

    @FXML
    private TextField voltaAlmocoMinuto;

    @FXML
    void cliqueInsta(ActionEvent event) {
        Desktop desktop = Desktop.getDesktop();

        try {
            desktop.browse(new URI("https://www.instagram.com/_gustalencar/"));
        } catch (IOException | URISyntaxException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possível abrir o site!");
            alert.showAndWait();
        }
    }

    @FXML
    void alterar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("alterar.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Alterar cadastro");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private Connection conexao = ConexaoBD.getConnection();
    public static String nome = null, funcao = null;

    @FXML
    void buscar(ActionEvent event) {
        String sql = "SELECT * FROM cadastro WHERE codigo = (?)";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            stm.setString(1, codigo.getText());
            ResultSet rs = stm.executeQuery();

                while(rs.next()){
                    nome = rs.getString("nome");
                    funcao = rs.getString("cargo");
                }

                    if(nome == null){
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setTitle("Erro");
                        a.setHeaderText("Falha na consulta");
                        a.setContentText("Funcionário não cadastrado ou código inválido.");
                        a.showAndWait();
                    } else {
                        funcionario.setText(nome.toUpperCase(Locale.ROOT));
                        cargo.setText(funcao.toUpperCase(Locale.ROOT));
                    }

        } catch (SQLException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setContentText("Houve um erro na consulta do Banco de dados");
            a.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    void cadastro(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("cadastro.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Cadastro");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void comousar(ActionEvent event) {

    }

    @FXML
    void sobre(ActionEvent event) {

    }

    @FXML
    void excluir(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("excluir.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Excluir");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private static String dataFuncionario = null;
    private static String entradas = null;
    private static String almocoSaida = null;
    private static String voltaAlmoco = null;
    private static String casaSaida = null;
    private static String a = null;
    private static String b = null;
    private static long aux = 0, aux2 = 0;
    private static int sequence = 0;

    @FXML
    void registrar(ActionEvent event) {

        LocalDate dat = date.getValue();
        dataFuncionario = dat.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        int he = Integer.parseInt(entradaHora.getText());
        int me = Integer.parseInt(entradaMinuto.getText());

        LocalTime horasEntrada = LocalTime.of(he, me);

        int sa = Integer.parseInt(saidaAlmocoHora.getText());
        int smm = Integer.parseInt(saidaAlmocoMinuto.getText());
        LocalTime saidaAlmoco = LocalTime.of(sa, smm);

        Duration intervalo = Duration.between(horasEntrada, saidaAlmoco);

        int va = Integer.parseInt(voltaAlmocoHora.getText());
        int vam = Integer.parseInt(voltaAlmocoMinuto.getText());

        LocalTime voltaAl = LocalTime.of(va, vam);
        voltaAl.minusHours(va).minusMinutes(vam);

        int sh = Integer.parseInt(saidaHora.getText());
        int sm = Integer.parseInt(saidaMinuto.getText());

        LocalTime saiCasa = LocalTime.of(sh, sm);
        saiCasa.minusHours(sh).minusMinutes(sm);

        Duration intervalo2 = Duration.between(voltaAl, saiCasa);

        Duration horasTotais = intervalo.plus(intervalo2);

        entradas = entradaHora.getText() + ":" + entradaMinuto.getText();
        almocoSaida = saidaAlmocoHora.getText() + ":" + saidaAlmocoMinuto.getText();
        voltaAlmoco = voltaAlmocoHora.getText() + ":" + voltaAlmocoMinuto.getText();
        casaSaida = saidaHora.getText() + ":" + saidaMinuto.getText();

        Duration d = null;

        String consultaCargo = "SELECT * FROM Cadastro WHERE codigo = (?)";
        String cargo = "";

            try {
                PreparedStatement stm = conexao.prepareStatement(consultaCargo);
                stm.setInt(1, Integer.parseInt(codigo.getText()));
                ResultSet rs = stm.executeQuery();

                    while(rs.next()){
                        cargo = rs.getString("cargo");
                    }

                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

                if(cargo.equalsIgnoreCase("balconista")){
                    d = horasTotais.minusHours(7).minusMinutes(20);
                } else {
                    d = horasTotais.minusHours(8);
                }

            long hT = horasTotais.getSeconds();
            long hTE = d.getSeconds();

            aux = hT / 3600;
            aux2 = (hTE % 3600) / 60;

            a = String.format("%d:%02d", hT / 3600, (hT % 3600) / 60);
            b = String.format("%d:%02d", hTE / 3600, (hTE % 3600) / 60);

            if(d.toMinutes() == 60){
                Duration t = d.minusMinutes(60);

                Registros funcionario = new Registros(dataFuncionario, entradas, almocoSaida, voltaAlmoco, casaSaida, a, b);
                ObservableList<Registros> registros = tabelaRegistro.getItems();
                registros.add(funcionario);
                tabelaRegistro.setItems(registros);

            } else {

                Registros funcionario = new Registros(dataFuncionario, entradas, almocoSaida, voltaAlmoco, casaSaida, a, b);
                ObservableList<Registros> registros = tabelaRegistro.getItems();
                registros.add(funcionario);
                tabelaRegistro.setItems(registros);
            }

        String sql = "INSERT INTO Registros VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stm = null;

        long somaHoras = aux2;

        try {
            stm = conexao.prepareStatement(sql);

            stm.setString(1, dataFuncionario.toString());
            stm.setString(2, entradaHora.getText() + ":" + entradaMinuto.getText());
            stm.setString(3, saidaAlmocoHora.getText() + ":" + saidaAlmocoMinuto.getText());
            stm.setString(4, voltaAlmocoHora.getText() + ":" + voltaAlmocoMinuto.getText());
            stm.setString(5, saidaHora.getText() + ":" + saidaMinuto.getText());
            stm.setString(6, a);
            stm.setString(7, b);
            stm.setLong(8, somaHoras);
            stm.setInt(9, sequence++);
            stm.execute();
            stm.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Document documento = new Document(PageSize.A4);

        documento.setMargins(40f, 40f, 40f, 40f);

        try {

            PdfWriter.getInstance(documento, new FileOutputStream("Relatório - " + funcionario.getText() +".pdf"));
            documento.open();

            Paragraph tituloDoRelatorio = new Paragraph(new Phrase(22F, "RELATÓRIO DE HORAS EXTRAS", FontFactory.getFont(FontFactory.COURIER_BOLD, 16F)));
            tituloDoRelatorio.setAlignment(Element.ALIGN_CENTER);
            documento.add(tituloDoRelatorio);

            Paragraph linhaEmBranco = new Paragraph(new Phrase(20F, "                       ", FontFactory.getFont(FontFactory.COURIER_BOLD, 16F)));
            documento.add(linhaEmBranco);

            Paragraph texto = new Paragraph(new Phrase(11F, "Registro de horas extras do(a) funcionário(a) " + MainController.nome.toUpperCase(Locale.ROOT) + "\n que ocupa atualmente o cargo de " + MainController.funcao.toUpperCase(Locale.ROOT) + ".", FontFactory.getFont(FontFactory.COURIER, 8F)));
            texto.setAlignment(Element.ALIGN_CENTER);
            documento.add(texto);

            Table tabela = new Table(7);

            tabela.setWidth(70f);
            tabela.setPadding(2);
            tabela.setBorderWidth(1);
            tabela.setBorder(10);
            tabela.setWidths(new float[]{8f, 8f, 8f, 8f, 8f, 8f, 8f});

            //COLUNAS
            Paragraph dataTabela = new Paragraph(new Phrase( "DATA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8F)));
            dataTabela.setAlignment(Element.ALIGN_CENTER);

            Paragraph horaTabela = new Paragraph(new Phrase("ENTRADA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8F)));
            horaTabela.setAlignment(Element.ALIGN_CENTER);

            Paragraph saidaAlmocoTabela = new Paragraph(new Phrase("SAÍDA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8F)));
            saidaAlmocoTabela.setAlignment(Element.ALIGN_CENTER);

            Paragraph voltaAlmocoTabela = new Paragraph(new Phrase("VOLTA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8F)));
            voltaAlmocoTabela.setAlignment(Element.ALIGN_CENTER);

            Paragraph saidaCasaTabela = new Paragraph(new Phrase("SAÍDA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8F)));
            saidaAlmocoTabela.setAlignment(Element.ALIGN_CENTER);

            Paragraph totalTabela = new Paragraph(new Phrase("TOTAL", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8F)));
            totalTabela.setAlignment(Element.ALIGN_CENTER);

            Paragraph extraTabela = new Paragraph(new Phrase("EXTRAS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8F)));
            extraTabela.setAlignment(Element.ALIGN_CENTER);

            //TABELAS
            Cell cellData = new Cell(dataTabela);
            Cell cellHora = new Cell(horaTabela);
            Cell cellSaidaAl = new Cell(saidaAlmocoTabela);
            Cell cellVoltaAlmoco = new Cell(voltaAlmocoTabela);
            Cell cellSaiCasa = new Cell(saidaCasaTabela);
            Cell cellTotal = new Cell(totalTabela);
            Cell cellExtra = new Cell(extraTabela);

            cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellExtra.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellHora.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellSaiCasa.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellSaidaAl.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellVoltaAlmoco.setHorizontalAlignment(Element.ALIGN_CENTER);

            cellData.setBackgroundColor(new Color(225, 225, 208));
            cellExtra.setBackgroundColor(new Color(225, 225, 208));
            cellHora.setBackgroundColor(new Color(225, 225, 208));
            cellSaiCasa.setBackgroundColor(new Color(225, 225, 208));
            cellTotal.setBackgroundColor(new Color(225, 225, 208));
            cellSaidaAl.setBackgroundColor(new Color(225, 225, 208));
            cellVoltaAlmoco.setBackgroundColor(new Color(225, 225, 208));

            tabela.addCell(cellData);
            tabela.addCell(cellHora);
            tabela.addCell(cellSaidaAl);
            tabela.addCell(cellVoltaAlmoco);
            tabela.addCell(cellSaiCasa);
            tabela.addCell(cellTotal);
            tabela.addCell(cellExtra);

            String sqll = "SELECT * FROM Registros";
            int horaBanco = 0, soma = 0, hora = 0;

            try {
                PreparedStatement stmm = conexao.prepareStatement(sqll);
                ResultSet rs = stmm.executeQuery();

                while(rs.next()){

                    horaBanco = rs.getInt("somaHoras");

                    Paragraph dateCell = new Paragraph(new Phrase(rs.getString("dat"), FontFactory.getFont(FontFactory.HELVETICA, 8F)));
                    dateCell.setAlignment(Element.ALIGN_CENTER);

                    Paragraph entradaCell = new Paragraph(new Phrase(rs.getString("entrada"), FontFactory.getFont(FontFactory.HELVETICA, 8F)));
                    entradaCell.setAlignment(Element.ALIGN_CENTER);

                    Paragraph saidaAlmocoCell = new Paragraph(new Phrase( rs.getString("saidaAlmoco"), FontFactory.getFont(FontFactory.HELVETICA, 8F)));
                    saidaAlmocoCell.setAlignment(Element.ALIGN_CENTER);

                    Paragraph voltaAlmocoCell = new Paragraph(new Phrase( rs.getString("voltaAlmoco"), FontFactory.getFont(FontFactory.HELVETICA, 8F)));
                    voltaAlmocoCell.setAlignment(Element.ALIGN_CENTER);

                    Paragraph saiCasaCell = new Paragraph(new Phrase(rs.getString("saidaCasa"), FontFactory.getFont(FontFactory.HELVETICA, 8F)));
                    saiCasaCell.setAlignment(Element.ALIGN_CENTER);

                    Paragraph totalCell = new Paragraph(new Phrase(rs.getString("total"), FontFactory.getFont(FontFactory.HELVETICA, 8F)));
                    totalCell.setAlignment(Element.ALIGN_CENTER);

                    Paragraph totalExtraCell = new Paragraph(new Phrase(rs.getString("extras"), FontFactory.getFont(FontFactory.HELVETICA, 8F)));
                    totalExtraCell.setAlignment(Element.ALIGN_CENTER);

                    Cell cellDate = new Cell(dateCell);
                    Cell cellEntrada = new Cell(entradaCell);
                    Cell cellSaidaAlmoco = new Cell(saidaAlmocoCell);
                    Cell cellVoltaAl = new Cell(voltaAlmocoCell);
                    Cell cellSai = new Cell(saiCasaCell);
                    Cell cellTotalHoras = new Cell(totalCell);
                    Cell cellTotalExtras = new Cell(totalExtraCell);

                    cellDate.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellEntrada.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellSaidaAlmoco.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellVoltaAl.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellSai.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellTotalHoras.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellTotalExtras.setHorizontalAlignment(Element.ALIGN_CENTER);

                    tabela.addCell(cellDate);
                    tabela.addCell(cellEntrada);
                    tabela.addCell(cellSaidaAlmoco);
                    tabela.addCell(cellVoltaAl);
                    tabela.addCell(cellSai);
                    tabela.addCell(cellTotalHoras);

                        if(totalExtraCell.toString().contains("-")){
                            cellTotalExtras.setBackgroundColor(new Color(255, 0, 0));
                            tabela.addCell(cellTotalExtras);

                        } else if(totalExtraCell.toString().contains("0:00")){
                            tabela.addCell(cellTotalExtras);
                            cellTotalExtras.setBackgroundColor(new Color(29, 151, 254));

                        } else {
                            cellTotalExtras.setBackgroundColor(new Color(0, 245, 0));
                            tabela.addCell(cellTotalExtras);
                        }

                        soma += horaBanco;

                            if(soma >= 60){
                                hora++;
                                soma -= 60;
                            } else if(soma < 0){
                                    if(soma <= -60){
                                        hora--;
                                        soma += 60;
                                    } else if(hora == 0 && soma == 0){
                                        hora = 00;
                                        soma = 00;
                                    }
                            }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            documento.add(tabela);
            documento.add(linhaEmBranco);

        Paragraph funciTxt = new Paragraph(new Phrase("                FUNCIONÁRIO: " + funcionario.getText() + "\n                 MÊS: " + dat.getMonth().getDisplayName(TextStyle.FULL, new Locale(String.format("pt", "BR"))) + "\n                 EXTRAS: " + hora + "h " + soma + "m", FontFactory.getFont(FontFactory.COURIER_BOLD, 8F)));
            funciTxt.setAlignment(Element.ALIGN_JUSTIFIED);

            documento.add(funciTxt);
            documento.close();

            /*try {
                Runtime.getRuntime().exec(new String[] {"cmd.exe", "/c", "start", "Relatório " + funcionario.getText() +".pdf"});
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void removerRegistro(ActionEvent event) {
        int selectID = tabelaRegistro.getSelectionModel().getSelectedIndex();

            if(selectID == -1){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setContentText("Nenhum registro selecionado.");
                a.showAndWait();

            } else {
                tabelaRegistro.getItems().remove(selectID);

                String delete = "DELETE FROM Registros WHERE id = (?)";

                try {
                    PreparedStatement statement = conexao.prepareStatement(delete);
                    statement.setInt(1, selectID);
                    statement.execute();
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    @FXML
    void limpar(ActionEvent event) {
        tabelaRegistro.getItems().clear();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data.setCellValueFactory(new PropertyValueFactory<>("data"));
        entrada.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        total.setCellValueFactory(new PropertyValueFactory<>("totalHoras"));
        saida.setCellValueFactory(new PropertyValueFactory<>("horaSaidaAlmoco"));
        volta.setCellValueFactory(new PropertyValueFactory<>("horaVoltaAlmoco"));
        saidaCasa.setCellValueFactory(new PropertyValueFactory<>("horaSaidaCasa"));
        extras.setCellValueFactory(new PropertyValueFactory<>("totalExtras"));

    }
}
