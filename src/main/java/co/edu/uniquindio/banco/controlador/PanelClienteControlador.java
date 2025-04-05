package co.edu.uniquindio.banco.controlador;


import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.banco.modelo.entidades.Transaccion;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PanelClienteControlador implements Initializable {

    @FXML
    private TableColumn<Transaccion, String> colCategoria;

    @FXML
    private TableColumn<Transaccion, String > colFecha;

    @FXML
    private TableColumn<Transaccion, String> colTipo;

    @FXML
    private TableColumn<Transaccion, String> colUusario;

    @FXML
    private TableColumn<Transaccion, String> colValor;

    @FXML
    private TableView<?> tablaTransacciones;

    private Banco banco;


    public PanelClienteControlador(){
        banco = new Banco();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo().toString()));
        colValor.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));
        colUusario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBilleteraDestino().getUsuario().toString()));
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().toString()));
    }

    public void irPanelTransferencia() {
        navegarVentana("/Transferencia.fxml", "Banco - Transferencia");
    }

    public void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
        try {

            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(tituloVentana);

            // Mostrar la nueva ventana
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void consultarSaldoBilletera(Usuario usuario) {
        Optional<BilleteraVirtual> billetera = banco.getBilleteras().stream()
                .filter(b -> b.getUsuario().equals(usuario))
                .findFirst();

        if (billetera.isPresent()) {
            mostrarAlerta("Su saldo es: $" + billetera.get().consultarSaldo(), Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("No se encontró una billetera.", Alert.AlertType.WARNING);
        }
    }


    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

}

