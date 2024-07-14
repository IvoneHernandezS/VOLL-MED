package med.voll.api.dominio.consulta;

import jakarta.validation.ValidationException;
import med.voll.api.dominio.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.dominio.medico.Medico;
import med.voll.api.dominio.medico.MedicoRepositorio;
import med.voll.api.dominio.paciente.PacienteRepositorio;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private ConsultaRepositorio consultaRepositorio;

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired // implementarlo así, me permite que todas las clases que utilizan el validador de Consultas, se agreguen
    List<ValidadorDeConsultas> validadores;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos) {
        if (!pacienteRepositorio.findById(datos.idPaciente()).isPresent()) {
            throw new ValidacionDeIntegridad("El id para el paciente no fue encontrado");
        }
        if (datos.idMedico() != null && !medicoRepositorio.existsById(datos.idMedico())) {
            throw new ValidacionDeIntegridad("El id para el medico no fue encontrado");
        }
        validadores.forEach(v-> v.validar(datos));
        var paciente = pacienteRepositorio.findById(datos.idPaciente()).get();
        var medico = seleccionarMedico(datos);
        if(medico==null){
            throw new ValidacionDeIntegridad("no existen medicos disponibles para este horario y especialidad");
        }
        var consulta = new Consulta(medico,paciente,datos.fecha());
        consultaRepositorio.save(consulta);
        return new DatosDetalleConsulta(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if(datos.idMedico()!=null){
            return medicoRepositorio.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()==null){
            throw new ValidacionDeIntegridad("debe seleccionarse una especialidad para el medico");
        }
        return medicoRepositorio.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }

//    public void cancelar(DatosCancelamientoConsulta datos) {
//        if (!consultaRepositorio.existsById(datos.idConsulta())){
//            throw new ValidacionDeIntegridad("El Id de la consulta realizada no existe");
//        }
//        validadoresCancelamiento.forEach(v -> v.validar(datos));
//
//        var consulta = consultaRepositorio.getReferenceById(datos.idConsulta());
//        consulta.cancelar(datos.motivo());
//    }
}
