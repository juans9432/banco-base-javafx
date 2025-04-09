package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.Sesion;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarDatosControlador {

    @FXML
    private TextField txtIdentificacion;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtDireccion;
    @FXML
    private PasswordField txtPassword;

    Banco banco = Banco.getInstancia();

    Sesion sesion = Sesion.getInstancia();



    public void initialize() {
        // Obtener el usuario de la sesión
        Usuario usuario = Sesion.getInstancia().getUsuario();

        // Mostrar los datos actuales
        txtIdentificacion.setText(usuario.getId());
        txtNombre.setText(usuario.getNombre());
        txtCorreo.setText(usuario.getEmail());
        txtDireccion.setText(usuario.getDireccion());
        txtPassword.setText(usuario.getPassword());
    }

    /**
     * metodo para guardar los cambios
     * @param e
     */
    public void guardarCambios(ActionEvent e) {
        Usuario usuario = Sesion.getInstancia().getUsuario();

        usuario.setId(txtIdentificacion.getText());
        usuario.setNombre(txtNombre.getText());
        usuario.setEmail(txtCorreo.getText());
        usuario.setDireccion(txtDireccion.getText());
        usuario.setPassword(txtPassword.getText());

        mostrarAlerta("Perfil actualizado con éxito", Alert.AlertType.INFORMATION);

        cerrarVentana();
    }

    /**
     * metodo para mostrar una alerta
     * @param mensaje
     * @param tipo
     */
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    /**
     * metodo para cerrar la ventana
     */
    public void cerrarVentana(){
        Stage stage = (Stage) txtIdentificacion.getScene().getWindow();
        stage.close();
    }

}
