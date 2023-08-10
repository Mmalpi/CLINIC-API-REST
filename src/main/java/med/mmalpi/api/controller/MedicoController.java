package med.mmalpi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.mmalpi.api.medico.DatosRegistroMedico;
import med.mmalpi.api.medico.Medico;
import med.mmalpi.api.medico.MedicoRepository;

@RestController
@RequestMapping("/medico") //endpoint
public class MedicoController {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@PostMapping
	public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico) {
		medicoRepository.save(new Medico(datosRegistroMedico));
	}
	 // Manejo de excepciones para datos de entrada no válidos personalizado //custom error handler
	//ejemplo // example 
	// "message": "nombre: must not be blank; email: must be a well-formed email address; telefono: must match \"^[0-9]{7,15}$\"; documento: must match \"\\d{4,6}\"; direccion.calle: must not be blank;"
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("; ");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
    }
}

