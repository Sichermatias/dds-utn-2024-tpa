package ar.edu.utn.frba.dds.dominio.utils;
import java.lang.reflect.Field;
public class TextoPlanoConverter {

    public static String convertirAtextoPlano(Object obj) {
        StringBuilder textoPlano = new StringBuilder();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);  // Permite acceder a atributos privados
            try {
                textoPlano.append(field.getName()).append(": ").append(field.get(obj)).append(", ");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // Eliminar la última coma y espacio, y devolver el resultado
        return textoPlano.length() > 0 ? textoPlano.substring(0, textoPlano.length() - 2) : "";
    }
}
