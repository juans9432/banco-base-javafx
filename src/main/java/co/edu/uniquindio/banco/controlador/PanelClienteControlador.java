package co.edu.uniquindio.banco.controlador;


import co.edu.uniquindio.banco.modelo.entidades.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import lombok.Setter;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PanelClienteControlador implements Initializable, IActualizacion {

    @FXML
    private TableColumn<Transaccion, String> colCategoria;

    @FXML
    private TableColumn<Transaccion, String > colFecha;

    @FXML
    private TableColumn<Transaccion, String> colTipo;

    @FXML
    private TableColumn<Transaccion, String> colUsuario;

    @FXML
    private TableColumn<Transaccion, String> colValor;

    @FXML
    private TableView<Transaccion> tablaTransacciones;

    @FXML
    private Label lblBienvenida;

    @FXML
    private Label lblNumeroCuenta;

    private final Banco banco = Banco.getInstancia();

    private final Sesion sesion = Sesion.getInstancia();

    private ObservableList<Transaccion> transaccionObservableList;


    public PanelClienteControlador(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo().toString()));
        colValor.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMonto())));
        colUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBilleteraDestino().getUsuario().getNombre()));
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        colCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().toString()));

        transaccionObservableList = FXCollections.observableArrayList();
        cargarTransacciones();
    }

    /**
     * metodo para cargar las transacciones
     */
    private void cargarTransacciones() {
        BilleteraVirtual billetera = banco.buscarBilleteraUsuario(sesion.getUsuario().getId());
        transaccionObservableList.setAll(billetera.obtenerTransacciones());
        tablaTransacciones.setItems(transaccionObservableList);
    }

    /**
     * metodo para ir al panel de transferencia
     */
    public void irPanelTransferencia() {
        FXMLLoader loader = navegarVentana("/Transferencia.fxml", "Banco - Transferencia");
        TransferenciaControlador controlador = loader.getController();
        controlador.setInterfazActualizacion(this);

    }

    /**
     * metodo para ir al panel de recarga
     * @param e
     */
    public void irPanelRecarga(ActionEvent e) {
        FXMLLoader loader = navegarVentana("/recarga.fxml", "Banco - Recarga");
        RecargaControlador controlador = loader.getController();
        controlador.setInterfazActualizacion(this);
    }

    public void irPanelEditar() {
        navegarVentana("/editarDatos.fxml", "Banco - Editar datos");

    }

    /**
     * metodo para navegar entre ventanas
     * @param nombreArchivoFxml
     * @param tituloVentana
     * @return
     */
    public FXMLLoader navegarVentana(String nombreArchivoFxml, String tituloVentana) {
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

            return loader;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * metodo para consultar el saldo de la billetera
     * @param e
     */
    public void consultarSaldo(ActionEvent e) {
        Usuario usuario = sesion.getUsuario();
        for(BilleteraVirtual billetera : banco.getBilleteras()) {
            if(billetera.getUsuario().equals(usuario)){
                double saldo = billetera.consultarSaldo();
                mostrarAlerta("su saldo actual es: $"+ saldo, Alert.AlertType.INFORMATION);
                return;
            }
        }
        mostrarAlerta("No se encontró una billetera asociada al usuario.", Alert.AlertType.ERROR);
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
     * metodo para cerrar sesion
     * @param event
     */
    public void cerrarSesion(ActionEvent event) {
        sesion.cerrarSesion();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * metodo que inicializa datos para los textos del panel
     */
    public void inicializarDatos() {
        Usuario usuario = sesion.getUsuario();

        lblBienvenida.setText(usuario.getNombre() + " bienvenido a su banco. Aquí podrá ver sus transacciones.");

        BilleteraVirtual billetera = banco.buscarBilleteraUsuario(usuario.getId());

        if (billetera != null) {
            lblNumeroCuenta.setText("Nro. Cuenta: " + billetera.getNumero());
        } else {
            lblNumeroCuenta.setText("No se encontró billetera para este usuario.");
        }
    }

    @Override
    public void actualizarTabla() {
        cargarTransacciones();
    }



    /**
    public void editarNota(ActionEvent e) {

        if(sesion.getUsuario() != null) {
            try {
                banco.actualizar(
                        notaSeleccionada.getId(),
                        txtTitulo.getText(),
                        txtNota.getText(),
                        txtCategoria.getValue(),
                        txtRecordatorio.getValue()
                );

                limpiarCampos();
                actualizarNotas();
                mostrarAlerta("Nota actualizada correctamente", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }else{
            mostrarAlerta("Debe seleccionar una nota de la tabla", Alert.AlertType.WARNING);
        }
    }
     */

}

