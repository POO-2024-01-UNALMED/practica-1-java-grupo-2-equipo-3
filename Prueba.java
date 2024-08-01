// Esto es una solo para hacer purebas, ignorenlo, despues lo borramos


import gestorAplicacion.*;
import gestorAplicacion.manejoReserva.Reserva;
import uiMain.*;
import java.util.ArrayList;

public class Prueba {

    public static void main(String[] args) {
        ArrayList<Integer> fecha = new ArrayList<Integer>();

        fecha.add(30);
        fecha.add(12);
        fecha.add(2020);

        System.out.println(mostrarDias(5, fecha));

        
    }
    



    public static ArrayList<ArrayList<Integer>> mostrarDias(int cantidadDias, ArrayList<Integer> fecha) {
        int dia = fecha.get(0);
        int mes = fecha.get(1);
        int año = fecha.get(2);

        ArrayList<ArrayList<Integer>> listaFechas = new ArrayList<>();
        int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        while (cantidadDias > 0) {
            int diasMes = diasPorMes[mes - 1];
            int cantidadRestante = cantidadDias;
            for (int i = 1; i < cantidadDias + 1; i++) {
                if (dia > diasMes) {
                    break;
                }
                ArrayList<Integer> fechas = new ArrayList<>();
                fechas.add(dia);
                fechas.add(mes);
                fechas.add(año);
                listaFechas.add(fechas);
                dia++;
                cantidadRestante--;
            }

            if (cantidadRestante == 0) {
                break;
            }

            cantidadDias = cantidadRestante;
            dia = 1;
            mes++;
            if (mes > 12) {
                mes = 1;
                año++;
            }
        }
        return listaFechas;
    }

    

}

