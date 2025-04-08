package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.*;
import co.edu.uniquindio.banco.modelo.enums.Categoria;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TransferenciaControlador implements Initializable {

    @FXML
    private ComboBox<Categoria> txtCategoria;

    @FXML
    private TextField txtMonto;

    @FXML
    private TextField txtNumeroCuenta;

    private final Banco banco = Banco.getInstancia();

    private final Sesion sesion = Sesion.getInstancia();


    public TransferenciaControlador() {
    }


    public void transferir(ActionEvent e) {
        Usuario usuario = sesion.getUsuario();
        BilleteraVirtual billetera = banco.buscarBilleteraUsuario(usuario.getId());
        try {
            String origen = billetera.getNumero();
            String destino = txtNumeroCuenta.getText();
            float monto = Float.parseFloat(txtMonto.getText());
            Categoria categoria = txtCategoria.getValue();

            if (categoria == Categoria.RECARGA) {
                banco.recargarBilletera(origen, monto);
            } else {
                banco.realizarTransferencia(origen, destino, monto, categoria);
            }

            mostrarAlerta("transaccion realizada con exito", Alert.AlertType.INFORMATION);
            cerrarVentana();

        } catch (Exception ex) {
            mostrarAlerta("Error: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtCategoria.getItems().addAll(Categoria.values());
    }

    public void cerrarVentana(){
        Stage stage = (Stage) txtMonto.getScene().getWindow();
        stage.close();
    }
}

