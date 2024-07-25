package gestorAplicacion.manejoReserva;

import java.util.ArrayList;

public class Actividad {
	private String nombre;
	private Destino destino;
	private ArrayList<TiposActividad> tipo;
	private ArrayList<Guia> guias;
	private int capacidad;
	private int clasificacion;
	private double precio;

	public Actividad(String nombre, Destino destino) {
		super();
		this.nombre = nombre;
		this.destino = destino;
		this.guias = new ArrayList<>();
		this.tipo=new ArrayList<>();
	}

	
	String getNombre() {
		return nombre;
	}


	void setNombre(String nombre) {
		this.nombre = nombre;
	}


	@Override
	public String toString() {
		return "Nombre de la actividad: " + nombre + ",\nSe encuentra en: " + destino.getNombre() + ",\nTipo de actividad: " + tipo + ",\nCapacidad por grupo: " + capacidad + ",\nClasificacion por edad: +" + clasificacion + ",\nPrecio por persona: " + precio;
	}


	ArrayList<TiposActividad> getTipo() {
		return tipo;
	}


	void setTipo(ArrayList<TiposActividad> tipo) {
		this.tipo = tipo;
	}


	ArrayList<Guia> getGuias() {
		return guias;
	}


	void setGuias(Guia guia) {
		this.guias.add(guia);
	}


	int getCapacidad() {
		return capacidad;
	}

	void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	int getClasificacion() {
		return clasificacion;
	}

	void setClasificacion(int clasificacion) {
		this.clasificacion = clasificacion;
	}

	public void ingresarGuia() {
		for(TiposActividad tipo: this.tipo) {
			for(Guia guia:Guia.getGuias()) {
				if(guia.getTipoActividades().contains(tipo)&&guia.getDestino().equals(destino)&& !this.guias.contains(guia)) {
					this.guias.add(guia);
				}
			}
		}
	
	}

	public ArrayList<Guia> buscarGuia(Idiomas idioma) {
		ArrayList<Guia> guiasCapacitados=new ArrayList<>();
		for(Guia guia:guias) {
			ArrayList<Idiomas> idiomas=guia.getIdiomas();
			if(idiomas.contains(idioma)) {
				guiasCapacitados.add(guia);
			}
		}
		return guiasCapacitados;
	}

	public static void retirarGuia(Guia guia) {
		Destino destino=guia.getDestino();
		ArrayList<Actividad> listaActividades = destino.mostrarActividadesTipo(guia);
		for(Actividad actividad:listaActividades) {
			if(actividad.guias.contains(guia)) {
				actividad.guias.remove(guia);
			}
		}
		
	}

	public void ingresarTipoActividades(String tipoActividades) {	
		String[] numeros=tipoActividades.split(" ");
		
		for(String posicion : numeros) {
			int numero = Integer.parseInt(posicion);
			for(TiposActividad tipoA:TiposActividad.values()) {
				if(numero==tipoA.getPosicion()) {this.tipo.add(tipoA);}
			}
		}
		if(this.tipo.size()==1) {
			this.tipo.add(this.tipo.getFirst());
		}
	}


	public void asignarParametros() {
		int capacidad=0;
		int clasificacion=0;
		int precioB=0;
		for(TiposActividad tipo:this.tipo) {
			switch(tipo.getDificultad()) {
				case "Baja":
					capacidad+=15;
					clasificacion+=30;
				break;
				case "Media":
					capacidad+=10;
					clasificacion+=15;
					precioB+=10;
				break;
				case "Alta":
					capacidad+=8;	
					clasificacion+=10;
					precioB+=100;
				break;
				case "Extrema":
					capacidad+=5;	
					clasificacion+=5;
					precioB+=1000;
				break;
			}
		}
		
		int n=0;
		int m=0;
		int x=0;
		for(Actividad actividad: this.destino.getActividades()) {
			if(actividad.guias.size()>this.guias.size()) {n++;}
			else {m++;}
			for(TiposActividad tipo:this.tipo) {
				if(actividad.tipo.contains(tipo)) {x++;}
				
				if((this.destino.getActividades().size()/4)<x) {capacidad+=3;}
				else if((this.destino.getActividades().size()/2)<x) {capacidad+=2;}
				else if((this.destino.getActividades().size()/4)>x) {capacidad-=1;}
			}
		}
		if(((n+m)/4)>n) {capacidad-=5;}
		else if(((n+m)/4)<n) {capacidad-=3;}
		else if(((n+m)/4)<m) {capacidad+=3;}
		
		this.capacidad=capacidad;
		
		if(clasificacion<=15) {this.clasificacion=18;}
		else if(clasificacion<30) {this.clasificacion=15;}
		else {this.clasificacion=7;}
		
		double precio=0;
		if(precioB>1000) {precio=(60000*(capacidad)+930000)/(capacidad);}
		else if(precioB>100) {precio=(30000*(capacidad)+1150000)/(capacidad);}
		else if(precioB>10) {precio=(4000*(capacidad)+1400000)/(capacidad);}
		else {precio=1200000/(capacidad);}
		
		this.precio = Math.round(precio / 100) * 100;
	}

	public static Actividad buscarActividad(String nombre, String destino) {
		Destino destinoActividad=Destino.buscarDestino(destino);
		if(destinoActividad!=null) {
			for(Actividad actividad:destinoActividad.getActividades()) {
				if (nombre.equals(actividad.nombre)) {
					return actividad;				
				}
			}
		}
		return null;
	}
	
	public static boolean retirarActividad(Actividad actividad) {
		Grupo.retirarActividad(actividad, null);
		
		ArrayList<Actividad> actividades=actividad.destino.getActividades();
		actividades.remove(actividad);
		actividad.destino.setActividades(actividades);
		actividad=null;
		return true;
	}

	
}

