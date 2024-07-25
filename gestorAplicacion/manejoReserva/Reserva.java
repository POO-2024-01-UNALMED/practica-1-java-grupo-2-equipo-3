package gestorAplicacion.manejoReserva;

import java.util.ArrayList;
import java.util.Arrays;

public class Reserva {

	public static ArrayList<ArrayList<Integer>> mostrarDias(int cantidadDias, ArrayList<Integer> fecha) {
        int dia = fecha.get(0);
        int mes = fecha.get(1);
        int año = fecha.get(2);

        ArrayList<ArrayList<Integer>> listaFechas = new ArrayList<>();
        int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        while (cantidadDias > 0) {
            int diasMes = diasPorMes[mes - 1];
            int cantidadRestante=cantidadDias;
            for (int i = 1; i < cantidadDias+1; i++) {
                if (dia > diasMes) {
                    break;
                }
                ArrayList<Integer> fechas = new ArrayList<>();
                fechas.add(dia);
                fechas.add(mes);
                fechas.add(año);
                listaFechas.add(fechas);
                dia++;
                cantidadRestante --;
            }

            if (cantidadRestante==0) {
                break;
            }

            cantidadDias =cantidadRestante;
            dia = 1;
            mes++;
            if (mes > 12) {
                mes = 1;
                año++;
            }
        }
        return listaFechas;
    }
	public static boolean verificarLista(int i, String i_idiomas) {
		if(i_idiomas==null||i_idiomas.isEmpty()) {
			return false;
		}
		else {
		String[] numeros=i_idiomas.split(" ");
		for (String str : numeros) {
	        try {
	            int num = Integer.parseInt(str);
	            if (num < 1 || num > i) {
	                return false;
	            }
	        } catch (NumberFormatException e) {
	            return false;
	        }
	    }
	    return true;	
		
	    }
	}
	public static boolean verificarNumero(int max, String str) {
	    try {
	        int num = Integer.parseInt(str);
	        return num >= 1 && num <= max;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	public static ArrayList<Integer> listaFecha(String fechaInicio) {
		String[] R_listaString=fechaInicio.split("/");
		ArrayList<Integer> fecha=new ArrayList<>();
		for(String x:R_listaString) {
			int numero=Integer.parseInt(x);
			fecha.add(numero);
		}
		return fecha;
		
	}
	public static boolean verificarFecha(String fecha) {	
		if(fecha==null) {return false;}
		String[] listaString=fecha.split("/");
		if(listaString.length==2) {
			if(!Reserva.verificarNumero(12,listaString[0] )) {return false;}
		}
		else if(listaString.length==3) {
			if(!Reserva.verificarNumero(12,listaString[1])) {return false;}
		}
		else{return false;}
		return true;
	}
	public static ArrayList<ArrayList<Integer>> mostrarListaFechas(String opcFecha,String fecha) {
		ArrayList<ArrayList<Integer>> listaFechas=new ArrayList<>();
		String[] listaString=fecha.split("/");
		
		if(opcFecha.equals("1")) {
			int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
			
			int mes=Integer.parseInt(listaString[0]);
			int año=Integer.parseInt(listaString[1]);
			
			int cantidadDias = diasPorMes[mes - 1];
			ArrayList<Integer> fechaInicio=new ArrayList<>(Arrays.asList(1,mes,año));
			
			listaFechas=Reserva.mostrarDias(cantidadDias, fechaInicio);
		}
		else {
			int dia=Integer.parseInt(listaString[0]);
			int mes=Integer.parseInt(listaString[1]);
			int año=Integer.parseInt(listaString[2]);
			ArrayList<Integer> fechaDia=new ArrayList<>(Arrays.asList(dia,mes,año));
			
			listaFechas.add(fechaDia);
		}
		return listaFechas;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> convertirTipo(Object lista) {
		if (lista instanceof ArrayList<?>) {
            ArrayList<?> listaGenerica = (ArrayList<?>) lista;

            for (Object elemento : listaGenerica) {
                if (!(elemento instanceof String)) {
                   return null;
                   }
            }
            ArrayList<String> listaString = (ArrayList<String>) lista;
            return listaString;
        }
		return null;
	}
	public static String mostrarMes(int fecha) {
		String[] meses = {
	            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
	            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	return meses[fecha-1];
	}
}

