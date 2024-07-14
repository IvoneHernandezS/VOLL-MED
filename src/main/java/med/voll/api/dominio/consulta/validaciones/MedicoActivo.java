package med.voll.api.dominio.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.dominio.consulta.DatosAgendarConsulta;
import med.voll.api.dominio.medico.MedicoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoActivo implements ValidadorDeConsultas{

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    public void validar (DatosAgendarConsulta datos) {
        if (datos.idMedico()==null){
            return;
        }
        var medicoActivo = medicoRepositorio.findActivoById(datos.idMedico());

        if (!medicoActivo){
            throw new ValidationException("No se permite agendar citas con medicos inactivos en el sistema");
        }
    }
}
