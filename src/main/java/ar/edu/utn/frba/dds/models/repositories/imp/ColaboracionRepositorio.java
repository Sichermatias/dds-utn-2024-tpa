package ar.edu.utn.frba.dds.models.repositories.imp;

import ar.edu.utn.frba.dds.dominio.colaboracion.*;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ColaboracionRepositorio extends BaseRepositorio implements WithSimplePersistenceUnit {
        private static ColaboracionRepositorio instancia;
        public static ColaboracionRepositorio getInstancia(){
            if (instancia == null){
            instancia = new ColaboracionRepositorio();
            }
            return instancia;
        }
        @Override
        protected EntityManager getEntityManager() {
        return entityManager();
    }

    public List<Colaboracion> obtenerColaboracionesPorColaboradorId(Long colaboradorId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Colaboracion> cq = cb.createQuery(Colaboracion.class);
        Root<Colaboracion> root = cq.from(Colaboracion.class);

        Predicate condicion = cb.equal(root.get("colaborador").get("id"), colaboradorId);
        cq.where(condicion);
        cq.select(root);

        return getEntityManager().createQuery(cq).getResultList();
    }
    public Object obtenerColaboracionPorTipo(Colaboracion colaboracion) {
        String tipoColaboracion = colaboracion.getTipo(); // Suponiendo que hay un método getTipo() en Colaboracion

        return switch (tipoColaboracion) {
            case "DINERO" -> buscarColaboracion(DonacionDinero.class, colaboracion);
            case "DONACION_VIANDAS" -> buscarColaboracion(DonacionVianda.class, colaboracion);
            case "HOSTEAR_HELADERA" -> buscarColaboracion(HostearHeladera.class, colaboracion);
            case "OFRECER_PREMIOS" -> buscarColaboracion(OfrecerPremio.class, colaboracion);
            case "REDISTRIBUCION_VIANDAS" -> buscarColaboracion(RedistribucionViandas.class, colaboracion);
            case "ENTREGA_TARJETAS" -> buscarColaboracion(RegistrarPersonasVulnerables.class, colaboracion);
            default -> null;
        };
    }

    private <T> T buscarColaboracion(Class<T> tipoClase, Colaboracion colaboracion) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tipoClase);
        Root<T> root = criteriaQuery.from(tipoClase);

        Predicate condicion = criteriaBuilder.equal(root.get("colaboracion"), colaboracion);
        criteriaQuery.select(root).where(condicion);

        return getEntityManager().createQuery(criteriaQuery).getResultStream().findFirst().orElse(null);
    }

    public List<HostearHeladera> buscarHosteosNoContadosParaElPuntajeEnMasDeUnDia() {
        // Calculamos la fecha límite
        LocalDate fechaLimite = LocalDate.now().minus(1, ChronoUnit.DAYS);

        // Creamos la consulta
        TypedQuery<HostearHeladera> query = this.getEntityManager().createQuery(
                "SELECT hh FROM HostearHeladera hh " +
                        "JOIN hh.heladera h " +
                        "WHERE h.ultimaFechaContadaParaPuntaje <= :fechaLimite" +
                        "AND h.desperfecto = false" +
                        "AND hh.enVigencia = true",
                HostearHeladera.class);

        // Asignamos el parámetro de la fecha límite
        query.setParameter("fechaLimite", fechaLimite);

        return query.getResultList();
    }

    public List<HostearHeladera> buscarHosteosNoContadosParaElPuntajeEn(Integer dias) {
        // Creamos la consulta
        TypedQuery<HostearHeladera> query = this.getEntityManager().createQuery(
                "SELECT hh FROM HostearHeladera hh " +
                        "JOIN hh.heladera h " +
                        "WHERE h.cantDiasSinContarParaPuntaje >= :dias" +
                        "AND hh.enVigencia = true",
                HostearHeladera.class);

        // Asignamos el parámetro de la fecha límite
        query.setParameter("dias", dias);

        return query.getResultList();
    }
}