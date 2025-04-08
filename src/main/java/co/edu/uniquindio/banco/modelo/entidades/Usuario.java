package co.edu.uniquindio.banco.modelo.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que representa un usuario del banco
 * @version 1.0
 * @autor caflorezvi
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Usuario {

    private String id, nombre, direccion, email, password;

}
