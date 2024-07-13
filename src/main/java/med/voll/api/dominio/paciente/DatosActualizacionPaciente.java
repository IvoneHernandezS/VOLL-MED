package med.voll.api.dominio.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.dominio.direccion.DatosDireccion;

public record DatosActualizacionPaciente(
        @NotNull
        Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion
) {
}
