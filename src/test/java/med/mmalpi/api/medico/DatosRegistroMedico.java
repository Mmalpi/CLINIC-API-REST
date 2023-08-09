package med.mmalpi.api.medico;

import med.mmalpi.api.direccion.DatosDireccion;

public record DatosRegistroMedico(String nombre, String email, String documento, Especialidad especialidad, DatosDireccion direccion) {

}
