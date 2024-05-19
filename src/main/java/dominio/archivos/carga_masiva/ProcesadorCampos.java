package dominio.archivos.carga_masiva;

import dominio.colaboracion.Colaboracion;
import dominio.colaboracion.TipoColaboracion;
import dominio.contacto.MedioDeContacto;
import dominio.contacto.NombreDeMedioDeContacto;
import dominio.persona.PersonaHumana;
import dominio.persona.TipoDocumento;

public class ProcesadorCampos {
    public static void procesarCampos(String[] campos){
        //Busco persona por tipo y num de doc
        //Si no la encuentro la cargo, si la encuentro la uso.
        //Como no hay persistencia todavia esto de buscar medio que no existe así que la voy a instanciar.
        PersonaHumana colaborador=new PersonaHumana();
        colaborador.setTipoDocumento(TipoDocumento.valueOf(campos[0]));
        colaborador.setDocumento(campos[1]);
        colaborador.setNombre(campos[2]);
        colaborador.setApellido((campos[3]));
        NombreDeMedioDeContacto contacto=new NombreDeMedioDeContacto();
        contacto.setNombre("mail");
        MedioDeContacto mail=new MedioDeContacto();
        mail.setNombreDeMedioDeContacto(contacto);
        mail.setValor(campos[4]);
        colaborador.agregarMedioContacto(mail);
        procesarColaboracion(campos[5],campos[6],campos[7]);
    }
    public static void procesarColaboracion(String fecha, String forma, String cantidad){
        for (Integer i=0;i<cantidad;i++) {
            Colaboracion colaboracion = new Colaboracion();
            TipoColaboracion tipo = new TipoColaboracion();
            tipo.setNombreTipo(forma);
            colaboracion.setFechaColaboracion(fecha);
            colaboracion.cambiarTipoColaboracion(tipo);
        }
    }
}

