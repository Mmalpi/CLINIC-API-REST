package med.mmalpi.api.medico;

import jakarta.validation.constraints.NotNull;
import med.mmalpi.api.direccion.DatosDireccion;

public record DatosActualizarMedico(@NotNull Long id, String nombre, String documento, DatosDireccion direccion) {

}
