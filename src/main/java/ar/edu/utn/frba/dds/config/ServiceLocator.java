package ar.edu.utn.frba.dds.config;


import ar.edu.utn.frba.dds.controllers.ColaboracionController;
import ar.edu.utn.frba.dds.controllers.LoginController;
import ar.edu.utn.frba.dds.dominio.services.messageSender.adapters.MailSender;
import ar.edu.utn.frba.dds.models.repositories.imp.*;
import ar.edu.utn.frba.dds.services.ColaboracionService;
import ar.edu.utn.frba.dds.services.GestorDeIncidentesService;
import ar.edu.utn.frba.dds.services.TransaccionService;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static Map<String, Object> instances = new HashMap<>();


    @SuppressWarnings("unchecked")
    public static <T> T instanceOf(Class<T> componentClass) {
        String componentName = componentClass.getName();

        if (!instances.containsKey(componentName)) {
            if(componentName.equals(LoginController.class.getName())) {
                LoginController instance = new LoginController(instanceOf(UsuarioRepositorio.class));
                instances.put(componentName, instance);
            }
            else if (componentName.equals(UsuarioRepositorio.class.getName())) {
                UsuarioRepositorio instance = new UsuarioRepositorio();
                instances.put(componentName, instance);
            }
            else if(componentName.equals(ColaboracionController.class.getName())) {
                ColaboracionController instance = new ColaboracionController(new ColaboradorRepositorio(), new HeladerasRepositorio(), new PremioRepositorio(), new ColaboracionService(new ColaboracionRepositorio(), new DonacionDineroRepositorio(), new TransaccionService()), new ModeloRepositorio());
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ColaboradorRepositorio.class.getName())) {
                ColaboradorRepositorio instance = new ColaboradorRepositorio();
                instances.put(componentName, instance);
            }
            else if (componentName.equals(GestorDeIncidentesService.class.getName())) {
                GestorDeIncidentesService instance = new GestorDeIncidentesService(new IncidenteRepositorio(), new TecnicoRepositorio(), new MailSender());
                instances.put(componentName, instance);
            }
        }

        return (T) instances.get(componentName);
    }
}
