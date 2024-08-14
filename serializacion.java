import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.manejoReserva.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


public class serializacion {

    public static void main(String[] args) throws IOException {

        Destino cartagena = new Destino("Cartagena");
        ArrayList <Actividad> Actividades_cartagena = new ArrayList <Actividad>();
        Actividad senderismo = new Actividad("Senderismo",cartagena);        
        ArrayList<TiposActividad> tipos = new ArrayList<>();
        tipos.add(TiposActividad.ACUATICAS);
        senderismo.setTipo(tipos);
        cartagena.setActividades(Actividades_cartagena);
        





        FileOutputStream fileOutputStream = new FileOutputStream("Cartagena.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(cartagena);
        objectOutputStream.close();

        
        
    }

    

    
}
