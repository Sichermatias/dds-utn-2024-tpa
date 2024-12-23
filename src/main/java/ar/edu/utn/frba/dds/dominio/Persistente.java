package ar.edu.utn.frba.dds.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
public abstract class Persistente {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;

    @Column(name = "activo", columnDefinition = "BIT(1)")
    protected Boolean activo = true;

    @Column(name = "fechaHoraAlta", columnDefinition = "DATETIME")
    protected LocalDateTime fechaHoraAlta;

    @Column(name = "fechaHoraBaja", columnDefinition = "DATETIME")
    protected LocalDateTime fechaHoraBaja;
}
