package med.mmalpi.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.mmalpi.api.direccion.DatosDireccion;

//here we made the validations //aqui se hacen las validaciones
public record DatosRegistroMedico(
		
		@NotBlank
		String nombre,
		@NotBlank
		@Email
		String email,
		@NotBlank
		@Pattern(regexp = "^[0-9]{7,15}$")
		String telefono,
		@NotBlank
		@Pattern(regexp = "\\d{4,6}")
		String documento,
		@NotNull
		Especialidad especialidad,
		@NotNull
		@Valid
		DatosDireccion direccion) {

	
}
