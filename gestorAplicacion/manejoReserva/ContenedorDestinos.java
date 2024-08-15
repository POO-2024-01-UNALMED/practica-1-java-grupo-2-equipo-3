package gestorAplicacion.manejoReserva;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContenedorDestinos implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Destino> destinos;

    public ContenedorDestinos() {
        this.destinos = new ArrayList<>();
    }

    public void addDestino(Destino destino) {
        this.destinos.add(destino);
    }

    public List<Destino> getDestinos() {
        return destinos;
    }
}