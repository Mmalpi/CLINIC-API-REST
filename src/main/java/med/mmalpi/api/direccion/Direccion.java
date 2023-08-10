package med.mmalpi.api.direccion;

import jakarta.persistence.Embeddable;

@Embeddable
public class Direccion {

	

	private String calle;
	private Integer numero;
	private String complemento;
	private String distrito;
	private String ciudad;
	
	public Direccion(DatosDireccion direccion) {
		this.numero = direccion.numero();
		this.calle = direccion.calle();
		this.complemento = direccion.complemento();
		this.distrito = direccion.distrito();
		this.ciudad = direccion.ciudad();
	}
}
