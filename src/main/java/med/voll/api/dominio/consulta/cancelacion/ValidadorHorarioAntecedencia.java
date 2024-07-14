//package med.voll.api.dominio.consulta.cancelacion;
//
//import jakarta.validation.ValidationException;
//import med.voll.api.dominio.consulta.ConsultaRepositorio;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//
//@Component ("ValidadorHorarioAntecedenciaCancelamiento")
//public class ValidadorHorarioAntecedencia implements ValidadorCancelamientoDeConsulta {
//
//    @Autowired
//    private ConsultaRepositorio consultaRepositorio;
//
//    @Override
//    public void validar(DatosCancelamientoConsulta datos){
//        var consulta = consultaRepositorio.getReferenceById(datos.idConsulta());
//        var ahora = LocalDateTime.now();
//        var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();
//
//        if (diferenciaEnHoras <24){
//            throw new ValidationException("La Consulta solamente puede ser cancelada con minimo 24 horas de anticipacion");
//        }
//    }
//}
