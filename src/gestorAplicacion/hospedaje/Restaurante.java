package gestorAplicacion.hospedaje;

import gestorAplicacion.manejoReserva.Grupo;

import java.io.Serializable;
import java.util.ArrayList;


public class Restaurante implements Serializable{
    private String nombre;
    private Restaurante restaurante;
    private int capacidad;
    private ArrayList<Grupo> mesasExistentes;

}
