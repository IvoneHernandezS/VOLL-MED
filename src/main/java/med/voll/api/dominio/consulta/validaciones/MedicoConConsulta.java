package med.voll.api.dominio.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.dominio.consulta.ConsultaRepositorio;
import med.voll.api.dominio.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepositorio consultaRepositorio;

    public void validar(DatosAgendarConsulta datos){
        if (datos.idMedico()==null){
            return;
        }
        var medicoConConsulta = consultaRepositorio.existsByMedicoIdAndFecha(datos.idMedico(), datos.fecha());
        if (medicoConConsulta){
            throw new ValidationException("Este medico ya tiene una consulta en ese horario");
        }
    }
}
