package ar.edu.utn.frba.dds.models.repositories.imp;

import dominio.colaboracion.Colaboracion;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ColaboracionRepositorio {
        @Getter
        private List<Colaboracion> colaboraciones;
        private static ar.edu.utn.frba.dds.models.repositories.imp.ColaboracionRepositorio instancia;
        public static synchronized ColaboracionRepositorio obtenerInstancia() {
            if (instancia == null) {
                instancia = new ar.edu.utn.frba.dds.models.repositories.imp.ColaboracionRepositorio();
            }
            return instancia;
        }
        public ColaboracionRepositorio() {
            this.colaboraciones=new ArrayList<>() {
            }; {
            };
        }

        public void agregar(Colaboracion colaboracion) {
            colaboraciones.add(colaboracion);
        }

        public void eliminar(Colaboracion colaborador) {
            colaboraciones.remove(colaborador);
        }
}