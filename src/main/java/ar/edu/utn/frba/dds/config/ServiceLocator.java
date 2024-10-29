package ar.edu.utn.frba.dds.config;


import ar.edu.utn.frba.dds.controllers.ColaboracionController;
import ar.edu.utn.frba.dds.controllers.LoginController;
import ar.edu.utn.frba.dds.models.repositories.imp.*;
import ar.edu.utn.frba.dds.services.ColaboracionService;
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
            if(componentName.equals(ColaboracionController.class.getName())) {
                ColaboracionController instance = new ColaboracionController(new ColaboracionRepositorio(), new ColaboradorRepositorio(), new TransaccionRepositorio(), new HeladerasRepositorio(), new DonacionDineroRepositorio(), new PremioRepositorio(), new ColaboracionService(new ColaboracionRepositorio(), new DonacionDineroRepositorio(), new TransaccionService()), new TransaccionService());
                instances.put(componentName, instance);
            }
            else if (componentName.equals(ColaboradorRepositorio.class.getName())) {
                ColaboradorRepositorio instance = new ColaboradorRepositorio();
                instances.put(componentName, instance);
            }
        }

        return (T) instances.get(componentName);
    }
}
