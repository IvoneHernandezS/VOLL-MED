package med.voll.api.dominio.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepositorio extends JpaRepository <Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);

    //primer cambio en intellij


    @Query("""
            SELECT m FROM Medico m
            WHERE m.activo= 1 AND
            m.especialidad=:especialidad AND
            m.id NOT in(
            SELECT c.medico.id FROM Consulta c
            c.fecha=:fecha
            )
            ORDER BY RAND()
            LIMIT 1
            """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);
}
