package gestorAplicacion.manejoReserva;

import java.util.ArrayList;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Guia;
import gestorAplicacion.hospedaje.Hotel;

import java.io.Serializable;

public class Destino implements Serializable{
	private static final long serialVersionUID = 1L; // Agregado para la compatibilidad de serialización
    private ArrayList<Guia> guias;
    private ArrayList<Actividad> actividades;
    private String nombre;
    private static ArrayList<Destino> destinos = new ArrayList<>();

    public Destino(String nombre) {
        super();
        this.nombre = nombre;
        this.guias = new ArrayList<>();
        this.actividades = new ArrayList<>();
        destinos.add(this);
    }

    

    /**
     * Elige los destinos para un guía basado en la cantidad de actividades que puede realizar. Verifica cuantas actividades puede realizar
     * si hay mas de un destino con la misma cantidad mayor, se elige el destino con menos guias.
     *
     * @param guia El guía para el cual se eligen los destinos.
     * @return Una lista de destinos elegidos.
     */
    public static ArrayList<Destino> elegirDestinoGuia(Guia guia) {
        int mayorCantidad = 0;
        ArrayList<Destino> listaDestinos = new ArrayList<>();
        for (Destino destino : destinos) {
            ArrayList<Actividad> listaActividades = destino. mostrarActividadesTipo(guia.getTipoActividades());
            if (listaActividades.size() == mayorCantidad) {
                listaDestinos.add(destino);
            } else if (listaActividades.size() > mayorCantidad) {
                mayorCantidad = listaActividades.size();
                listaDestinos.clear();
                listaDestinos.add(destino);
            }
        }
        if (listaDestinos.size() > 1) {
            int menorCantidad = listaDestinos.get(0).guias.size();
            ArrayList<Destino> listaFinal = new ArrayList<>();
            for (Destino destino : listaDestinos) {
                if (destino.guias.size() == menorCantidad) {
                    listaFinal.add(destino);
                } else if (destino.guias.size() < menorCantidad) {
                    menorCantidad = destino.guias.size();
                    listaFinal.clear();
                    listaFinal.add(destino);
                }
            }
            listaDestinos = listaFinal;
        }
        if (listaDestinos.size() != 1) {
            Destino.ingresarGuia(guia, listaDestinos, 1);
        }
        return listaDestinos;
    }

    /**
     * Muestra las actividades disponibles en un destino.
     *
     * @param clasificacion     La clasificación de las actividades a mostrar.
     * @param cantidadPersonas  La cantidad de personas para las actividades.
     * @return Una lista de actividades disponibles en el destino.
     */
    public ArrayList<Actividad> actividadesDisponiblesDestino(int clasificacion, int cantidadPersonas) {
        ArrayList<Actividad> actividadesPosibles = new ArrayList<>();
        for (Actividad actividad : actividades) {
            if (actividad.getClasificacion() <= clasificacion && actividad.getCapacidad() >= cantidadPersonas) {
                actividadesPosibles.add(actividad);
            }
        }
        return actividadesPosibles;
    }

    /**
     * Muestra las actividades que un guía puede realizar en este destino.
     *
     * @param guia El guía para el cual se muestran las actividades.
     * @return Una lista de actividades que el guía puede realizar.
     */
    public ArrayList<Actividad> mostrarActividadesTipo(Guia guia) {
        ArrayList<TiposActividad> lista = guia.getTipoActividades();
        ArrayList<Actividad> listaActividades = new ArrayList<>();
        for (TiposActividad tipoGuia : lista) {
            for (Actividad actividad : actividades) {
                ArrayList<TiposActividad> tipoActividad = actividad.getTipo();
                if (tipoActividad.contains(tipoGuia) && !listaActividades.contains(actividad)) {
                    listaActividades.add(actividad);
                }
            }
        }
        return listaActividades;
    }
    /**
     * Muestra las actividades que un guía puede realizar en este destino.
     *
     * @param guia El guía para el cual se muestran las actividades.
     * @return Una lista de actividades que el guía puede realizar.
     */
    public ArrayList<Actividad> mostrarActividadesTipo(ArrayList<TiposActividad> lista) {
        ArrayList<Actividad> listaActividades = new ArrayList<>();
        for (TiposActividad tipoGuia : lista) {
            for (Actividad actividad : actividades) {
                ArrayList<TiposActividad> tipoActividad = actividad.getTipo();
                if (tipoActividad.contains(tipoGuia) && !listaActividades.contains(actividad)) {
                    listaActividades.add(actividad);
                }
            }
        }
        return listaActividades;
    }

    /**
     * Ingresa un guía a uno de los destinos de una lista específica.
     *
     * @param guia  El guía a ingresar.
     * @param lista La lista de destinos donde se puede ingresar al guía.
     * @param n     La posición en la lista del destino seleccionado.
     */
    public static void ingresarGuia(Guia guia, ArrayList<Destino> lista, int n) {
        n -= 1;
        Destino destino = lista.get(n);
        destino.guias.add(guia);
        guia.setDestino(destino);
    }

    /**
     * Busca el destino más común en una lista de destinos.
     *
     * @param listaDestinos La lista de destinos a evaluar.
     * @return Una lista de cadenas que representa el destino más común y su frecuencia.
     */
    public static ArrayList<String> buscarDestinoComun(ArrayList<Destino> listaDestinos) {
        int contador = 0;
        int cantidadMaxima = 0;
        ArrayList<Destino> destinoFinal = new ArrayList<>();

        for (Destino destino : destinos) {
            contador = 0;
            for (Destino x : listaDestinos) {
                if (x.equals(destino)) {
                    contador++;
                }
            }

            if (contador == cantidadMaxima) {
                destinoFinal.add(destino);
            } else if (contador > cantidadMaxima) {
                destinoFinal.clear();
                destinoFinal.add(destino);
                cantidadMaxima = contador;
            }
        }

        ArrayList<String> listaDestino = new ArrayList<>();

        for (int i = 0; i < destinoFinal.size(); i++) {
            String idioma = destinoFinal.get(i) + "=" + cantidadMaxima + "/" + listaDestinos.size();
            listaDestino.add(idioma);
        }

        return listaDestino;
    }

    /**
     * Calcula el precio extra por temporada basado en la fecha y cantidad de clientes.
     *
     * @param fecha La fecha para calcular el precio extra.
     * @return El porcentaje de precio extra por temporada.
     */
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
        int cantidadClientes = Grupo.cantidadClientesDestino(this, fecha);
        if (cantidadClientes > this.guias.size() * 10) {
            porcentajePrecioExtra += 0.3;
        } else if (cantidadClientes < this.guias.size() * 4) {
            porcentajePrecioExtra -= 0.3;
        }
        return porcentajePrecioExtra;
    }

    /**
     * Calcula el precio extra basado en la popularidad y cantidad de actividades del destino.
     *
     * @return El porcentaje de precio extra por destino.
     */
    public double precioExtraPorDestino() {
        double porcentajeExtra = 1;

        int destinosConMasActividades = 0;
        int destinosConMenosActividades = 0;
        int destinosConMasClientes = 0;
        int destinosConMenosClientes = 0;
        for (Destino destino : destinos) {
            if (destino.actividades.size() > this.actividades.size()) {
                destinosConMasActividades++;
            } else {
                destinosConMenosActividades++;
            }

            if (Grupo.cantidadClientesDestino(destino,null) > Grupo.cantidadClientesDestino(this,null)) {
                destinosConMasClientes++;
            } else {
                destinosConMenosClientes++;
            }
        }
        if (((destinosConMasActividades + destinosConMenosActividades) / 4) > destinosConMasActividades) {
            porcentajeExtra += 0.5; // Tiene más actividades que el 75% de destinos
        } else if (((destinosConMasActividades + destinosConMenosActividades) / 2) > destinosConMasActividades) {
            porcentajeExtra += 0.3; // Tiene más actividades que el 50% de destinos
        } else if (((destinosConMasActividades + destinosConMenosActividades) / 4) > destinosConMenosActividades) {
            porcentajeExtra -= 0.3; // Está entre el 25% de destinos con menos actividades
        }

        if (((destinosConMasClientes + destinosConMenosClientes) / 4) > destinosConMasClientes) {
            porcentajeExtra += 0.5; // Tiene más popularidad que el 75% de destinos
        } else if (((destinosConMasClientes + destinosConMenosClientes) / 4) > destinosConMenosClientes) {
            porcentajeExtra -= 0.3; // Está entre el 25% de destinos con menos popularidad
        }

        return porcentajeExtra;
    }



    /**
     * Metodos para encontrar que detinos tiene cada idioma
     *
     * @param destinos destinos existentes.
     * @return El porcentaje de precio extra por idioma.
     */

     public static ArrayList<ArrayList<Destino>> destinosPorIdioma(ArrayList<Destino> destinos) {
        // Crear una lista para cada idioma
        ArrayList<Destino> destinosIngles = new ArrayList<>();
        ArrayList<Destino> destinosPortugues = new ArrayList<>();
        ArrayList<Destino> destinosEspanol = new ArrayList<>();
        ArrayList<Destino> destinosFrances = new ArrayList<>();
        ArrayList<Destino> destinosItaliano = new ArrayList<>();
    
        // Recorrer todos los destinos y verificar los idiomas disponibles en cada uno
        for (Destino destino : destinos) {
            for (Guia guia : destino.getGuias()) {
                for (Idiomas idioma : guia.getIdiomas()) {
                    switch (idioma) {
                        case INGLES:
                            if (!destinosIngles.contains(destino)) {
                                destinosIngles.add(destino);
                            }
                            break;
                        case PORTUGUES:
                            if (!destinosPortugues.contains(destino)) {
                                destinosPortugues.add(destino);
                            }
                            break;
                        case ESPANOL:
                            if (!destinosEspanol.contains(destino)) {
                                destinosEspanol.add(destino);
                            }
                            break;
                        case FRANCES:
                            if (!destinosFrances.contains(destino)) {
                                destinosFrances.add(destino);
                            }
                            break;
                        case ITALIANO:
                            if (!destinosItaliano.contains(destino)) {
                                destinosItaliano.add(destino);
                            }
                            break;
                    }
                }
            }
        }
    
        // Crear una lista de listas para almacenar todas las listas de destinos
        ArrayList<ArrayList<Destino>> destinosIdiomas = new ArrayList<>();
        destinosIdiomas.add(destinosIngles);
        destinosIdiomas.add(destinosPortugues);
        destinosIdiomas.add(destinosEspanol);
        destinosIdiomas.add(destinosFrances);
        destinosIdiomas.add(destinosItaliano);
    
        return destinosIdiomas;
    }

     /**
      * Devuelve la lista de los nombres de todos los destinos existentes
      * 
      * @return Un ArrayList<String> con los nombres 
      */
     public static ArrayList<String> listaNombres(){
		 ArrayList<String> ListaDestinos=new ArrayList<>();
		 for(Destino destino:destinos) {ListaDestinos.add(destino.getNombre());}
		 return ListaDestinos;
	 }
     /**
      * Busca un destino por su nombre.
      *
      * @param nombre El nombre del destino a buscar.
      * @return El destino encontrado, o null si no se encuentra.
      */
     public static Destino buscarNombre(String nombre) {
         for (Destino destino : destinos) {
             if (nombre.equals(destino.nombre)) {
                 return destino;
             }
         }
         return null;
     }
     public ArrayList<Object> mostrarPlaneacionDestino(String opcBusqueda,int clasificacion, TiposActividad tipo,ArrayList<ArrayList<Integer>> fecha,Idiomas idioma){
     public static ArrayList<Object> mostrarPlaneacionDestino(String opcBusqueda,Destino destino,int clasificacion, TiposActividad tipo,ArrayList<ArrayList<Integer>> fecha,Idiomas idioma){
    	 ArrayList<Object> tabla=new ArrayList<Object>();
    	 
    	 ArrayList<Object> posicion1=new ArrayList<>();//"1"&"3"=cantidad de actividades;"2"=actividades culturales;
    	 ArrayList<Object> posicion2=new ArrayList<>();;//"1"&"3"=promedio precios actividades;"2"=actividades familiares;
    	 ArrayList<Object> posicion3=new ArrayList<>();;//"1"=guias disponibles;"2"=actividades ecologicas;"3"hoteles disponibles
    	 ArrayList<Object> posicion4=new ArrayList<>();;//"1"=hoteles disponibles;"2"=actividades extremas;"3"promedio de precios hoteles
    	 ArrayList<Object> posicion5=new ArrayList<>();;//"1"=idioma mas comun;"2"=actividades acuaticas;"3" cantidad de personas
    	 ArrayList<Object> posicion6=new ArrayList<>();;//"1"=clasificacion mas solicitada;"2"=actividades deportivas;"3"principales actividades
    	 ArrayList<Object> posicion7=new ArrayList<>();;//"1"&"3"=oferta;"2"=total de actividades;
    	 
    	 
    	 ArrayList<Actividad> actividadesFiltradas=new ArrayList<>();
    	 for(Actividad actividad:actividades) {
 			if(actividad.verificarFiltrosActividad(clasificacion, tipo, fecha, idioma)) {actividadesFiltradas.add(actividad);}
 		 }
    	 if(opcBusqueda.equals("1")) {
    		 posicion1.add(actividadesFiltradas.size());
    		 posicion2.add(Actividad.promedioPreciosActividades(actividadesFiltradas));
    		 posicion3.add(Actividad.cantidadGuiasDisponiblesLista(actividadesFiltradas));
    		 posicion4.add(Hotel.cantidadHotelesDestino(this));
    		 posicion5.add(buscarIdiomaComun());
    		 posicion6.add(Reserva.mostrarClasificacionComun(this));
    		 posicion7.add(definirOferta(actividadesFiltradas));
    	 }else {
    		 posicion1.add(actividadesFiltradas.size());
    		 posicion2.add(Actividad.promedioPreciosActividades(actividadesFiltradas));
    		 posicion3.add(Hotel.cantidadHotelesDestino(this));
    		 posicion4.add(Hotel.promedioPreciosActividades(this));
    		 posicion5.add(Reserva.mostrarCantidadPersonasDestino(this));
    		 posicion6.add(Reserva.actividadPrincipalDestino(this));
    		 posicion7.add(definirOferta(actividadesFiltradas));
    	 }
    	 
    	 tabla.add(0, nombre); //0.nombre del destino
         tabla.add(1, posicion1); // 1."1"&"3"=promedio precios actividades;"2"=actividades familiares;
         tabla.add(2, posicion2); // 2."1"=guias disponibles;"2"=actividades ecologicas;"3"hoteles disponibles
         tabla.add(3, posicion3); // 3."1"=guias disponibles;"2"=actividades ecologicas;"3"hoteles disponibles
         tabla.add(4, posicion4); // 4."1"=hoteles disponibles;"2"=actividades extremas;"3"promedio de precios hoteles
         tabla.add(5, posicion5); // 5."1"=idioma mas comun;"2"=actividades acuaticas;"3" cantidad de personas
         tabla.add(6, posicion6); // 6."1"=clasificacion mas solicitada;"2"=actividades deportivas;"3"principales actividades
         tabla.add(7, posicion7); // 7."1"&"3"=oferta;"2"=total de actividades;
       
    	return tabla; 
     }
     /**
      * Busca el idioma más común entre los guías disponibles.
      * 
      * @return  El idioma más común entre los guías.Si no hay guías o idiomas comunes, devolverá null.
      */
     public Idiomas buscarIdiomaComun() {
    	 int cantidadMayor=0;
    	 Idiomas idiomaComun=null;
    	 for(Idiomas idioma:Idiomas.values()) {
    		 int cantidadIdioma=0;
    		 for(Guia guia:guias) {
    			if(guia.getIdiomas().contains(idioma)) {cantidadIdioma++;}
    		 }
    		 if(cantidadIdioma>cantidadMayor) {
    			 cantidadMayor=cantidadIdioma;
    			 idiomaComun=idioma;
    		 }
    	 }
    	 return idiomaComun;
     }
     /**
      * Define el nivel de oferta basado en el tamaño de una lista de actividades en comparación con el total de actividades disponibles.
      * 
      * @param actividadesLista  Lista de actividades actuales para comparar.
      * @return                 Una cadena que indica el nivel de oferta: "Alta", "Normal" o "Baja". Si la lista de actividades está vacía, devolverá "Baja".
      */
     public String definirOferta(ArrayList<Actividad> actividadesLista) {
    	 String oferta=null;
    	 if(actividadesLista.size()>actividades.size()*0.75) {oferta="Alta";}
    	 else if(actividadesLista.size()>actividades.size()*0.45) {oferta="Normal";}
    	 else {oferta="Baja";}
    	 return oferta;
     }
     /**
      * Cuenta la cantidad de actividades que corresponden a un tipo específico.
      * 
      * @param tipoFiltro  El tipo de actividad que se quiere filtrar.
      * @return            La cantidad de actividades que coinciden con el tipo especificado.
      */
     public static int cantidadActividadesTipo(TiposActividad tipoFiltro,ArrayList<Actividad>actividadesFiltradas){
    	 int cantidad=0;
    	 for(Actividad actividad:actividadesFiltradas) {
    		 if(actividad.getTipo().contains(tipoFiltro)) {cantidad++;}
    	 }
    	 return cantidad;
     }
     
//////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////Métodos de acceso//////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////
    
	public ArrayList<Actividad> getActividades() {
		return actividades;
	}
	
	public void setActividades(ArrayList<Actividad> actividades) {
		this.actividades = actividades;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public static ArrayList<Destino> getDestinos() {
		return destinos;
	}
	
	public static void setDestinos(ArrayList<Destino> destinos) {
		Destino.destinos = destinos;
	}
	
	public ArrayList<Guia> getGuias() {
		return guias;
	}
	
	public void setGuias(ArrayList<Guia> guias) {
		this.guias = guias;
	}


}
