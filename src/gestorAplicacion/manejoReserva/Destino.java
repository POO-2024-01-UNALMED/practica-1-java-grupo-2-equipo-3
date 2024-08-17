package gestorAplicacion.manejoReserva;

import java.util.ArrayList;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Guia;
import gestorAplicacion.interfaces.BusquedaNombres;

import java.io.Serializable;

public class Destino implements Serializable,BusquedaNombres {
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
            ArrayList<Actividad> listaActividades = destino.mostrarActividadesTipo(guia);
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
     * @param idioma El idioma para calcular el precio extra.
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
     @Override
     public ArrayList<String> listaNombres(){
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
     @Override
     public Destino buscarNombre(String nombre) {
         for (Destino destino : destinos) {
             if (nombre.equals(destino.nombre)) {
                 return destino;
             }
         }
         return null;
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
