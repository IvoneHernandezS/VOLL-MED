package med.voll.api.dominio.consulta;

import med.voll.api.dominio.medico.Medico;
import med.voll.api.dominio.medico.MedicoRepositorio;
import med.voll.api.dominio.paciente.PacienteRepositorio;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private ConsultaRepositorio consultaRepositorio;

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    public void agendar(DatosAgendarConsulta datos) {
        if (pacienteRepositorio.findById(datos.idPaciente()).isPresent()) {
            throw new ValidacionDeIntegridad("El id para el paciente no fue encontrado");
        }
        if (datos.idMedico() == null && medicoRepositorio.existsById(datos.idMedico())) {
            throw new ValidacionDeIntegridad("El id para el medico no fue encontrado");
        }
        var paciente = pacienteRepositorio.findById(datos.idPaciente()).get();
        var medico = seleccionarMedico(datos);
        var consulta = new Consulta(null, medico, paciente, datos.fecha());
        consultaRepositorio.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if (datos.idMedico() != null) {
            return medicoRepositorio.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad() == null){
            throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad para el medico");
        }
        return medicoRepositorio.seleccionarMedicoConEspecialidadEnFecha (datos.especialidad(), datos.fecha());
    }
}
