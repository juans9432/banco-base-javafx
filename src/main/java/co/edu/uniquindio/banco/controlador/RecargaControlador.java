package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.banco.modelo.entidades.Sesion;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class RecargaControlador {

    @FXML
    private TextField txtMonto;

    Banco banco = Banco.getInstancia();

    Sesion sesion = Sesion.getInstancia();

    public RecargaControlador(){}

    public void recargarBilletera(){
        Usuario usuario = sesion.getUsuario();
        BilleteraVirtual billetera = banco.buscarBilleteraUsuario(usuario.getId());

        try{
            String numero = billetera.getNumero();
            float monto = Float.parseFloat(txtMonto.getText());
            banco.recargarBilletera(numero, monto);
            mostrarAlerta("Recarga realizada con exito", Alert.AlertType.INFORMATION);
            cerrarVentana();
        } catch (Exception e){
            mostrarAlerta("No se pudo realizar la recarga", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    public void cerrarVentana(){
        Stage stage = (Stage) txtMonto.getScene().getWindow();
        stage.close();
    }
}
