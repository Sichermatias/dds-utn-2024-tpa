package ar.edu.utn.frba.dds.dominio.archivos.carga_masiva;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.regex.Pattern;

public class ValidadorCampos {

    // Validar correos electrónicos
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    // Validar nombres y apellidos (solo letras)
    private static final Pattern NOMBRE_PATTERN = Pattern.compile("^[A-Za-z]+$");

    // Validar que el documento contiene solo números
    private static final Pattern NUMERO_PATTERN = Pattern.compile("^[0-9]+$");

    // Tipos de documentos permitidos
    private static final Set<String> TIPOS_DOCUMENTO_VALIDOS = Set.of("DNI", "PASAPORTE", "CEDULA");

    // Formas de colaboración permitidas
    private static final Set<String> FORMAS_COLABORACION_VALIDAS = Set.of("DINERO","DONACION_VIANDAS","REDISTRIBUCION_VIANDAS","ENTREGA_TARJETAS");

    // Formato de fecha esperado
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Longitud máxima de campos
    private static final int MAX_LONGITUD_TIPO_DOC = 3;
    private static final int MAX_LONGITUD_DOCUMENTO = 10;
    private static final int MAX_LONGITUD_NOMBRE = 50;
    private static final int MAX_LONGITUD_MAIL = 50;
    private static final int MAX_LONGITUD_FECHA_COLABORACION = 10;
    private static final int MAX_LONGITUD_FORMA_COLABORACION = 22;
    private static final int MAX_LONGITUD_CANTIDAD = 7;

    public static boolean validarTipoDocumento(String campo) {
        return campo != null && campo.length() <= MAX_LONGITUD_TIPO_DOC && TIPOS_DOCUMENTO_VALIDOS.contains(campo);
    }

    public static boolean validarDocumento(String campo) {
        return campo != null && campo.length() <= MAX_LONGITUD_DOCUMENTO && NUMERO_PATTERN.matcher(campo).matches() && campo.length() >= 5;
    }

    public static boolean validarNombre(String campo) {
        return campo != null && campo.length() <= MAX_LONGITUD_NOMBRE && NOMBRE_PATTERN.matcher(campo).matches();
    }

    public static boolean validarEmail(String campo) {
        return campo != null && campo.length() <= MAX_LONGITUD_MAIL && EMAIL_PATTERN.matcher(campo).matches();
    }

    public static boolean validarFecha(String campo) {
        return campo != null && campo.length() <= MAX_LONGITUD_FECHA_COLABORACION && validarFechaFormato(campo);
    }

    private static boolean validarFechaFormato(String campo) {
        try {
            LocalDate.parse(campo, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean validarFormaColaboracion(String campo) {
        return campo != null && campo.length() <= MAX_LONGITUD_FORMA_COLABORACION && FORMAS_COLABORACION_VALIDAS.contains(campo);
    }

    public static boolean validarCantidad(String campo) {
        return campo != null && campo.length() <= MAX_LONGITUD_CANTIDAD && validarCantidadNumero(campo);
    }

    private static boolean validarCantidadNumero(String campo) {
        try {
            int cantidad = Integer.parseInt(campo);
            return cantidad > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}