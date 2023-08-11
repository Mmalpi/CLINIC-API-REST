package med.mmalpi.api.medico;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.mmalpi.api.direccion.Direccion;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

	
	private Long id;
	private String nombre;
	private String email;
	private String telefono;
	private String documento;
	private boolean status;
	@Enumerated(EnumType.STRING)
	private Especialidad especialidad;
	@Embedded
	private Direccion direccion;
	
	
	public Medico(DatosRegistroMedico datosRegistroMedico) {
		this.status = true;
		this.nombre = datosRegistroMedico.nombre();
		this.email = datosRegistroMedico.email();
		this.telefono = datosRegistroMedico.telefono();
		this.documento = datosRegistroMedico.documento();
		this.especialidad = datosRegistroMedico.especialidad();
		this.direccion = new Direccion(datosRegistroMedico.direccion());
	}


	public void actualizarDatos(DatosActualizarMedico datosAcutalizarMedico) {
		if (datosAcutalizarMedico.nombre() != null) {
			this.nombre = datosAcutalizarMedico.nombre();
		}
		if (datosAcutalizarMedico.documento() != null) {
			this.documento = datosAcutalizarMedico.documento();
		}
		if (datosAcutalizarMedico.direccion() != null) {
			this.direccion = direccion.actualizarDatos(datosAcutalizarMedico.direccion());
		}
		

	}


	public void desactivarMedico() {
		this.status = false;
	}
}
