package med.mmalpi.api.medico;

public record DatosListadoMedico(Long id, String nombre, String especialidad, String documento, String email) {

	
	public DatosListadoMedico(Medico medico) {
		this(medico.getId(), medico.getNombre(), medico.getEspecialidad(), medico.getEmail(), medico.getDocumento());
	}
}
