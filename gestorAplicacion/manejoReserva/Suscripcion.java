package gestorAplicacion.manejoReserva;

import java.util.ArrayList;  
import gestorAplicacion.gestionHum.Cliente;

public class Suscripcion {
    private static ArrayList<Cliente> listaClientes = new ArrayList<>();
    private String tipo;
    private ArrayList<Integer> vencimiento;       //Otra instancia de una fecha
    private int capacidad;
    private double precio;
    private float descTransporte;
    private float descTour;
    private float descHotel;


    // Analisar el temas de listaClientes
    public Suscripcion(String tipo, ArrayList<Integer> vencimiento, int capacidad, double precio, float descTransporte, float descTour, float descHotel) {
        this.tipo = tipo;
        this.vencimiento = vencimiento;
        this.capacidad = capacidad;
        this.precio = precio;
        this.descTransporte = descTransporte;
        this.descTour = descTour;
        this.descHotel = descHotel;
    }

    public Suscripcion(String tipo, ArrayList<Integer> vencimiento, int capacidad, double precio) {
        this.tipo = tipo;
        this.vencimiento = vencimiento;
        this.capacidad = capacidad;
        this.precio = precio;
    }



    public Cliente verificarSuscripcion(String nombre, int edad) {
        for (int i = 0; i < listaClientes.size(); i++) {
            if (listaClientes.get(i).getNombre().equals(nombre)) {
                if (listaClientes.get(i).getSuscripcion().getVencimiento().verificarFechaSuscripcion()) { //verifica el vencimiento de la suscripcion
                    listaClientes.get(i).setEdad(edad);
                    return listaClientes.get(i);
                    
                }
                else{
                    listaClientes.remove(i);
                    return entradaUsuario(); //retornar el objeto cliente que ingreso el usuario
                }

            }
            else{
                return entradaUsuario(); //retornar el objeto cliente que ingreso el usuario
            }
            
        }
        
       
    }









    public boolean verificarFechaSuscripcion(ArrayList<Integer> fechaVencicmiento){

        //Metodo que toma una fecha de vencimiento y verifica si esta fecha es valida y vigente
        

        //Formato Fecha [dia, mes, año]

        if (fechaVencicmiento.get(2) < fechaActual.get(2)) {
            return false;
        }
        else if (fechaVencicmiento.get(1) < fechaActual.get(1)) {
            return false;
        }
        else if (fechaVencicmiento.get(0) < fechaActual.get(0)) {
            return false;
        }
        else{
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
        this.vencimiento = vencimiento;
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

    public String getTipo() {
        return tipo;
    }

    public ArrayList<Integer> getVencimiento() {
        return vencimiento;
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






}
