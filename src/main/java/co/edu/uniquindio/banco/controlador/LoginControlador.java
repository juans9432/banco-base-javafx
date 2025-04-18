package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.Sesion;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el controlador de la vista de login
 * @author caflorezvi
 */
public class LoginControlador {

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private PasswordField txtContrasena;

    private final Banco banco;

    private final Sesion sesion = Sesion.getInstancia();

    public LoginControlador(){
        this.banco = Banco.getInstancia(); // Usa el Singleton
    }

    /**
     * metodo para iniciar sesion
     * @param e
     */
    public void iniciarSesion(ActionEvent e){
        String id= txtIdentificacion.getText();
        String contraseña= txtContrasena.getText();

        Usuario usuarioEncontrado = banco.buscarUsuario(id);

        if(usuarioEncontrado != null){
            mostrarAlerta("Éxito", "inicio de sesión exitoso", Alert.AlertType.INFORMATION);

            Sesion sesion = Sesion.getInstancia();
            sesion.setUsuario(usuarioEncontrado);

            irPanelUsuario();
            cerrarVentana();
        } else {
            mostrarAlerta("Error", "numero de identificacion o contraseña incorrectos", Alert.AlertType.ERROR);
        }
    }


    /**
     * metodo para mostrar una alerta
     * @param titulo
     * @param mensaje
     * @param tipo
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * metodo para ir al panel de usuario
     */
    public void irPanelUsuario() {
        navegarVentana("/panelCliente.fxml", "Banco - Panel Cliente");

    }

    /**
     * metodo para navegar entre ventanas
     * @param nombreArchivoFxml
     * @param tituloVentana
     */
    public void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
        try {

            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            PanelClienteControlador controlador = loader.getController();
            controlador.inicializarDatos();

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

    /**
     * metodo para cerrar la ventana
     */
    public void cerrarVentana(){
        Stage stage = (Stage) txtIdentificacion.getScene().getWindow();
        stage.close();
    }


}
