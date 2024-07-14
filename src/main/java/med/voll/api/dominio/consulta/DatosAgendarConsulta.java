package med.voll.api.dominio.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.dominio.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosAgendarConsulta(

        @NotNull
        Long idPaciente,
        Long idMedico,

        @NotNull
        @Future
        LocalDateTime fecha,

        Especialidad especialidad
) {
}
