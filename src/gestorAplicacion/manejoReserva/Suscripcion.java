package gestorAplicacion.manejoReserva;

import java.util.ArrayList;
import java.io.Serializable;
import gestorAplicacion.gestionHum.Cliente;

public class Suscripcion implements Serializable {
    private static final long serialVersionUID = 1L; // Agregado para la compatibilidad de serialización
    private static ArrayList<Cliente> listaClientes = new ArrayList<>();
    private String tipo;
    private ArrayList<Integer> fechaVencimiento;       //Otra instancia de una fecha
    private int capacidad;
    private double precio;
    private float descTransporte;
    private float descTour;
    private float descHotel;


    // Analizar el temas de listaClientes
    public Suscripcion(String tipo, ArrayList<Integer> vencimiento, int capacidad, double precio, float descTransporte, float descTour, float descHotel) {
        this.tipo = tipo;
        this.fechaVencimiento = vencimiento;
        this.capacidad = capacidad;
        this.precio = precio;
        this.descTransporte = descTransporte;
        this.descTour = descTour;
        this.descHotel = descHotel;
    }

    public Suscripcion(String tipo, ArrayList<Integer> vencimiento, int capacidad, double precio) {
        this.tipo = tipo;
        this.fechaVencimiento = vencimiento;
        this.capacidad = capacidad;
        this.precio = precio;
    }

    public static ArrayList<Integer> ultimaFechaReserva(ArrayList<ArrayList<Integer>> fechas) {
        return fechas.get(fechas.size() - 1);
    }

    public static Cliente verificarSuscripcion(String nombre, int edad, ArrayList<ArrayList<Integer>> listaFechas) {
        for (int i = 0; i < listaClientes.size(); i++) {
            if (listaClientes.get(i).getNombre().equals(nombre)) {
                if (listaClientes.get(i).getSuscripcion().verificarFechaVencimiento(Suscripcion.ultimaFechaReserva(listaFechas))) { //verifica el vencimiento de la suscripcion
                    listaClientes.get(i).setEdad(edad);
                    return listaClientes.get(i);        //retornar el objeto cliente existente en el array que tiene la suscripcion

                }
                else {
                    listaClientes.remove(i);
                    return null; //retornar el objeto cliente que ingreso el usuario
                }
            }
        }
        return null;
    }


    public boolean verificarFechaVencimiento(ArrayList<Integer> ultimaFecha) {
        if (fechaVencimiento.get(2) < ultimaFecha.get(2)) {
            return false;
        }
        else if (fechaVencimiento.get(1) < ultimaFecha.get(1)) {
            return false;
        }
        else if (fechaVencimiento.get(0) < ultimaFecha.get(0)) {
            return false;
        }
        else {
            return true;
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////Métodos de acceso//////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    public void setTipos(String tipo) {
        this.tipo = tipo;
    }

    public void setVencimiento(ArrayList<Integer> vencimiento) {
        this.fechaVencimiento = vencimiento;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDescTransporte(float descTransporte) {
        this.descTransporte = descTransporte;
    }

    public void setDescTour(float descTour) {
        this.descTour = descTour;
    }

    public void setDescHotel(float descHotel) {
        this.descHotel = descHotel;
    }

    public static void setListaClientes(ArrayList<Cliente> listaClientes) {
        Suscripcion.listaClientes = listaClientes;
    }

    public String getTipo() {
        return tipo;
    }

    public ArrayList<Integer> getVencimiento() {
        return fechaVencimiento;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public double getPrecio() {
        return precio;
    }

    public float getDescTransporte() {
        return descTransporte;
    }

    public float getDescTour() {
        return descTour;
    }

    public float getDescHotel() {
        return descHotel;
    }

    public static ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }




}
