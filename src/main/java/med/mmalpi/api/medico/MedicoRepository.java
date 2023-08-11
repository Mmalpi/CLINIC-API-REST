package med.mmalpi.api.medico;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long>{

	Page<Medico> findByStatusTrue(Pageable paginacion);


}
