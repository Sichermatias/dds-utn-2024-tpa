package dominio.colaboracion;

import dominio.infraestructura.Heladera;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import java.time.LocalDateTime;

@Entity
@Table(name = "usoHeladera")
@Getter @Setter
public class UsoDeHeladera {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "heladera_id")
    private Heladera heladera;

    @Column(name = "fechaHoraUso", columnDefinition = "DATETIME")
    private LocalDateTime fechaHora;

    @OneToMany
    @JoinColumn(name = "usoHeladera_id")//
    private Vianda vianda;

    @Column(name = "nroTarjeta", columnDefinition = "VARCHAR(14)")
    private String codigoTarjeta;

    public void removerViandasDeHeladera(Integer cant){
        heladera.setViandasActuales(heladera.getViandasActuales()-cant);
    }
}
