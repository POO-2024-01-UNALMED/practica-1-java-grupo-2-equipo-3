package gestorAplicacion.manejoReserva;

import java.util.ArrayList;

import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Guia;

public class Destino {
	private ArrayList<Guia> guias;
	private ArrayList<Actividad> actividades;
	private String nombre;
	private static ArrayList<Destino> destinos=new ArrayList<>();


	public Destino(String nombre) {
		super();
		this.nombre=nombre;
		this.guias=new ArrayList<>();
		this.actividades=new ArrayList<>();
		destinos.add(this);
	}
	
	public ArrayList<Actividad> getActividades() {
		return actividades;
	}

	void setActividades(ArrayList<Actividad> actividades) {
		this.actividades = actividades;
	}

	public String getNombre() {
		return nombre;
	}

	void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static ArrayList<Destino> getDestinos() {
		return destinos;
	}

	static void setDestinos(ArrayList<Destino> destinos) {
		Destino.destinos = destinos;
	}

	public ArrayList<Guia> getGuias() {
		return guias;
	}

	void setGuias(ArrayList<Guia> guias) {
		this.guias = guias;
	}

	public static ArrayList<Destino> elegirDestinoGuia(Guia guia) {
		int mayorCantidad=0;
		ArrayList<Destino> listaDestinos=new ArrayList<>();
		for(Destino destino:destinos) {
			ArrayList<Actividad> listaActividades = destino.mostrarActividadesTipo(guia);
			if (listaActividades.size()==mayorCantidad) {
				listaDestinos.add(destino);
			}
			else if(listaActividades.size()>mayorCantidad) {
				mayorCantidad=listaActividades.size();
				listaDestinos.clear();
				listaDestinos.add(destino);
			}
		}
		if(listaDestinos.size()>1) {
			int menorCantidad=listaDestinos.get(0).guias.size();
			ArrayList<Destino> listaFinal=new ArrayList<>();
			for(Destino destino:listaDestinos) {
				if(destino.guias.size()==menorCantidad) {
					listaFinal.add(destino);
				}
				else if(destino.guias.size()<menorCantidad) {
					menorCantidad=destino.guias.size();
					listaFinal.clear();
					listaFinal.add(destino);
				}
			}
			listaDestinos=listaFinal;
		}
		if (listaDestinos.size() != 1) {Destino.ingresarGuia(guia,listaDestinos,1);}
		return listaDestinos;		
	}
	
	public ArrayList<Actividad> mostrarActividadesTipo(Guia guia) {
		ArrayList<TiposActividad> lista = guia.getTipoActividades();
		ArrayList<Actividad> listaActividades = new ArrayList<>();
		for(TiposActividad tipoGuia:lista) {
			for(Actividad actividad:actividades) {
				ArrayList<TiposActividad> tipoActividad=actividad.getTipo();
				if(tipoActividad.contains(tipoGuia) && !listaActividades.contains(actividad)) {
					listaActividades.add(actividad);
				}
			}
		}
		return listaActividades;
	}

	public static void ingresarGuia(Guia guia,ArrayList<Destino> lista, int n) {
		n-=1;
		Destino destino = lista.get(n);
		destino.guias.add(guia);
		guia.setDestino(destino);
	}

	public static Destino buscarDestino(String nombre) {
		for (Destino destino : destinos) {
            if (nombre.equals(destino.nombre)) {
                return destino;
            }
        }
        return null;
	}

	public static ArrayList<String> buscarDestinoComun(ArrayList<Destino> listaDestinos) {
		int contador=0;
		int cantidadMaxima=0;
		ArrayList<Destino> destinoFinal=new ArrayList<>();
		
		for(Destino destino: destinos) {
			contador=0;
			for(Destino x:listaDestinos) {
				if(x.equals(destino)) {contador++;}	
			}
			
			if(contador==cantidadMaxima) {
				destinoFinal.add(destino);
				}
			else if(contador>cantidadMaxima) {
				destinoFinal.clear();
				destinoFinal.add(destino);
				cantidadMaxima=contador;
			}
		}
		
		ArrayList<String> listaDestino=new ArrayList<>();
		
		for(int i=0;i<destinoFinal.size();i++) {
			String idioma=destinoFinal.get(i)+"="+cantidadMaxima+"/"+listaDestinos.size();
			listaDestino.add(idioma);
		}
		
		return listaDestino;
	}
	
	public double precioExtraPorTemporada(ArrayList<Integer> fecha) {
	    double porcentajePrecioExtra = 0.0;
	    int mes = fecha.get(1); 

	    switch (mes) {
	        case 12: // Diciembre
	        case 1:  // Enero
	        case 6:  // Junio
	        case 7:  // Julio
	            porcentajePrecioExtra = 1.4;
	            break;
	        case 2:  // Febrero
	        case 3:  // Marzo
	        case 11: // Noviembre
	            porcentajePrecioExtra = 0.8;
	            break;
	        default:
	            porcentajePrecioExtra = 1.0; // Temporada normal
	            break;
	    }
	    int cantidadClientes=Grupo.cantidadClientesDestino(this);
		if(cantidadClientes>this.guias.size()*10) {porcentajePrecioExtra=+0.3;}
		else if(cantidadClientes<this.guias.size()*4) {porcentajePrecioExtra=-0.3;}
	    return porcentajePrecioExtra;
	}
	
	public double precioExtraPorDestino() {
		double porcentajeExtra=1;
		
		int destinosConMasActividades=0;
		int destinosConMenosActividades=0;
		int destinosConMasClientes=0;
		int destinosConMenosClientes=0;
		for(Destino destino:destinos) {
			if(destino.actividades.size()>this.actividades.size()) {destinosConMasActividades++;}
			else {destinosConMenosActividades++;}
			
			if(Grupo.cantidadClientesDestino(destino)>Grupo.cantidadClientesDestino(this)) {destinosConMasClientes++;}
			else {destinosConMenosClientes++;}
		}
		if(((destinosConMasActividades+destinosConMenosActividades)/4)>destinosConMasActividades) {porcentajeExtra=+0.5;}//Tiene mas actividades que el 75% de destinos
		else if(((destinosConMasActividades+destinosConMenosActividades)/2)>destinosConMasActividades) {porcentajeExtra=+0.3;}//Tiene mas actividades que el 50% de destinos
		else if(((destinosConMasActividades+destinosConMenosActividades)/4)>destinosConMenosActividades) {porcentajeExtra=-0.3;}//Esta entre el 25% de destinos con menos actividades
		
		if(((destinosConMasClientes+destinosConMenosClientes)/4)>destinosConMasClientes) {porcentajeExtra=+0.5;}//Tiene mas popularidad que el 75% de destinos
		else if(((destinosConMasClientes+destinosConMenosClientes)/4)>destinosConMenosClientes) {porcentajeExtra=-0.3;}//Esta entre el 25% de destinos con menos popularidad
		
		return porcentajeExtra;
	}
}



