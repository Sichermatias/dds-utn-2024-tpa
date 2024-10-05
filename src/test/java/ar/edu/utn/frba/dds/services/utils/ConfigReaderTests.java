package ar.edu.utn.frba.dds.services.utils;

import ar.edu.utn.frba.dds.dominio.utils.ConfigReader;
import org.junit.jupiter.api.Test;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConfigReaderTests {

    @Test
    void leerPropertiesConfiguracionMail(){
        String filePath = "mailconfig.properties";
        ConfigReader config = new ConfigReader(filePath);
        assertDoesNotThrow(() -> {
            Properties prop = config.getProperties();
            System.out.println(prop);
            assertNotNull(prop);
        });
    }
}
