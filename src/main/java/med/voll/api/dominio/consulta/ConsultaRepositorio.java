package med.voll.api.dominio.consulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepositorio  extends JpaRepository<Consulta, Long> {
}
