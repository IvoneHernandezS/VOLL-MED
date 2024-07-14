package med.voll.api.dominio.medico;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.dominio.direccion.Direccion;

@Table (name = "medicos")
@Entity(name = "Medico")
@Getter// getters para todos mis atributos
@NoArgsConstructor // un constructor default
@AllArgsConstructor //un contructor con todos los atributos
@EqualsAndHashCode(of = "id") //va a comparar en razon del id para no sobreescribir
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;

    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.activo = true;
        this.nombre = datosRegistroMedico.nombre();
        this.email = datosRegistroMedico.email();
        this.documento = datosRegistroMedico.documento();
        this.telefono = datosRegistroMedico.telefono();
        this.especialidad = datosRegistroMedico.especialidad();
        this.direccion = new Direccion(datosRegistroMedico.direccion());
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public String getDocumento() {
        return documento;
    }

    public long getId() {
        return id;
    }

    public String getTelefono() {
        return telefono;
    }

    public Boolean getActivo() {
        return activo;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico) {
        if (datosActualizarMedico.nombre() != null) {
            this.nombre = datosActualizarMedico.nombre();
        }
        if (datosActualizarMedico.documento() != null) {
            this.documento = datosActualizarMedico.documento();
        }
        if (datosActualizarMedico.direccion() != null) {
            this.direccion = direccion.actualizarDireccion(datosActualizarMedico.direccion());
        }
    }

    public void desactivarMedico() {
        this.activo = false;
    }
}
