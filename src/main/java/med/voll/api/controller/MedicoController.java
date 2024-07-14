package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dominio.direccion.DatosDireccion;
import med.voll.api.dominio.medico.DatosActualizarMedico;
import med.voll.api.dominio.medico.DatosListadoMedico;
import med.voll.api.dominio.medico.DatosRegistroMedico;
import med.voll.api.dominio.medico.DatosRespuestaMedico;
import med.voll.api.dominio.medico.Medico;
import med.voll.api.dominio.medico.MedicoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo medico en la base de datos")
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = medicoRepositorio.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));

        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
        // return 201 Create
        //URL donde encontrar al médico (http://localhost:8080/medicos/xx
    }

//    @GetMapping
//    public Page<DatosListadoMedico> listadoMedicos (Pageable paginacion){
//        return medicoRepositorio.findAll(paginacion).map(DatosListadoMedico ::new);
//    }

    @GetMapping
    @Operation(summary = "Obtiene el listado de medicos")
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos (@PageableDefault(page = 0, size = 10, sort = {"nombre"}) Pageable paginacion){
//        return medicoRepositorio.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepositorio.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    @PutMapping
    @Transactional //le estoy diciendo que una vez que ejecute ya terminó la transacción
    @Operation(summary = "Actualiza los datos de un medico existente")
    public ResponseEntity actualizarMedico (@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepositorio.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(),medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())));
    }
//      ESTE METODO ES PARA BORRAR ALGO COMPLETAMENTE Y NOSOTROS SOLO QUEREMOS ALGO QUE BORRE DE MANERA LOGICA, ES DECIR QUE EXCLUYA PERO MANTENGA UN HISTORICO
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void eliminarMedico(@PathVariable Long id) {
//        Medico medico = medicoRepositorio.getReferenceById(id);
//        medicoRepositorio.delete(medico);
//    }

    // DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un medico registrado - inactivo")
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepositorio.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los registros del medico con ID")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico (@PathVariable Long id) {
        Medico medico = medicoRepositorio.getReferenceById(id);
        var datosMedicos = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(),medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedicos);
    }

}
