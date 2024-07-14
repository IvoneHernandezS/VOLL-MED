//package med.voll.api.dominio.medico;
//
//import med.voll.api.dominio.consulta.Consulta;
//import med.voll.api.dominio.direccion.DatosDireccion;
//import med.voll.api.dominio.paciente.DatosRegistroPaciente;
//import med.voll.api.dominio.paciente.Paciente;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.temporal.TemporalAdjusters;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//
//@DataJpaTest //aqui le indico que está trabajando con una base de datos
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //aqui le digo que uso una base de datos externa y no voy a reemplazar mi base actual
//@ActiveProfiles("test")
//class MedicoRepositorioTest {
//
//    @Autowired
//    private MedicoRepositorio medicoRepositorio;
//
//    @Autowired
//    private TestEntityManager em;
//
//    @Test
//    @DisplayName("deberia renornar nulo cuando el medico se encuentre en consulta con otro paciente en ese horario")
//    void seleccionarMedicoConEspecialidadEnFechaEscenario1() {
//
//        var proximoLunes10H = LocalDate.now()
//                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
//                .atTime(10,0);
//
//        var medico = registrarMedico("Jose","j@gmail.com","12346", Especialidad.CARDIOLOGIA);
//        var paciente = registrarPaciente("Antonio", "a@gmail.com","456789");
//        registrarConsulta(medico, paciente, proximoLunes10H);
//
//        var medicoLibre = medicoRepositorio.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);
//
//        assertThat(medicoLibre).isNull();
//    }
//
//    @Test
//    @DisplayName("deberia retornar un medico cuando realice la consulta en la base de datos  en ese horario")
//    void seleccionarMedicoConEspecialidadEnFechaEscenario2() {
//
//        //given
//        var proximoLunes10H = LocalDate.now()
//                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
//                .atTime(10,0);
//
//        var medico=registrarMedico("Jose","j@mail.com","123456",Especialidad.CARDIOLOGIA);
//
//        //when
//        var medicoLibre = medicoRepositorio.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA,proximoLunes10H);
//
//        //then
//        assertThat(medicoLibre).isEqualTo(medico);
//    }
//
//
//    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
//        em.persist(new Consulta( medico, paciente, fecha));
//    }
//
//    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
//        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
//        em.persist(medico);
//        return medico;
//    }
//
//    private Paciente registrarPaciente(String nombre, String email, String documento) {
//        var paciente = new Paciente(datosPaciente(nombre, email, documento));
//        em.persist(paciente);
//        return paciente;
//    }
//
//    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
//        return new DatosRegistroMedico(
//                nombre,
//                email,
//                "61999999999",
//                documento,
//                especialidad,
//                datosDireccion()
//        );
//    }
//
//    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
//        return new DatosRegistroPaciente(
//                nombre,
//                email,
//                "61999999999",
//                documento,
//                datosDireccion()
//        );
//    }
//
//    private DatosDireccion datosDireccion() {
//        return new DatosDireccion(
//                " loca",
//                "azul",
//                "acapulpo",
//                "321",
//                "12"
//        );
//    }
//}