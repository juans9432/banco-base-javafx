package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.*;
import co.edu.uniquindio.banco.modelo.enums.Categoria;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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

    private BilleteraVirtual billeteraActual;


    public TransferenciaControlador() {
    }


    public void transferir(ActionEvent e) {
        try {
            String origen = billeteraActual.getNumero();
            String destino = txtNumeroCuenta.getText();
            float monto = Float.parseFloat(txtMonto.getText());
            Categoria categoria = txtCategoria.getValue();

            banco.realizarTransferencia(origen, destino, monto, categoria);
        } catch (Exception ex) {
            mostrarAlerta("Error: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void setBilleteraActual(BilleteraVirtual billeteraActual) {
        this.billeteraActual = billeteraActual;
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
}

