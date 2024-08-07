package gestorAplicacion.manejoReserva;

import java.util.ArrayList;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Cliente;
import gestorAplicacion.gestionHum.Guia;

public class Grupo {
    private static ArrayList<Grupo> grupos = new ArrayList<>();
    private ArrayList<Integer> fecha;
    private Guia guia;
    private Actividad actividad;
    private Idiomas idioma;
    private ArrayList<ArrayList<Cliente>> listaReservas;
    private int capacidad;
    private int clasificacion;

    public Grupo(ArrayList<Integer> fecha, Guia guia, Actividad actividad, Idiomas idioma,
                 ArrayList<ArrayList<Cliente>> listaReservas) {
        super();
        this.fecha = fecha;
        this.guia = guia;
        this.actividad = actividad;
        this.idioma = idioma;
        this.listaReservas = listaReservas;
        this.capacidad = actividad.getCapacidad();
        this.clasificacion = actividad.getClasificacion();
        grupos.add(this);
    }

    /**
     * Retira un guía de un grupo en una fecha específica, primero busca si se puede reemplazar al guia 
     * si no se puede se busca si se puede reubicar a los clientes en otros grupos, si no se puede se le 
     * da una cortesia al cliente y se elimina el grupo
     *
     * @param guia  El guía a retirar.
     * @param fecha La fecha del grupo del cual retirar al guía.
     */
    public static void retirarGuia(Guia guia, ArrayList<Integer> fecha) {
        Grupo grupo = null;
        for (Grupo x : grupos) {
            if (x.guia.equals(guia) && x.fecha.equals(fecha)) {
                grupo = x;
                break;
            }
        }
        Guia reemplazo = grupo.elegirGuia();
        if (reemplazo == null) {
            ArrayList<Grupo> gruposSustitutos = Grupo.buscarGrupo(grupo.fecha, grupo.actividad, grupo.idioma);
            ArrayList<ArrayList<Cliente>> listaReservasPendientes = grupo.listaReservas;
            for (Grupo x : gruposSustitutos) {
                ArrayList<ArrayList<Cliente>> reservasPendientes = x.reubicarReservas(listaReservasPendientes);
                if (reservasPendientes.isEmpty()) {
                    break;
                } else {
                    listaReservasPendientes = reservasPendientes;
                }
            }
            if (!listaReservasPendientes.isEmpty()) {
                for (ArrayList<Cliente> reservas : listaReservasPendientes) {
                    for (Cliente cliente : reservas) {
                        cliente.cancelarActividad(grupo.actividad, grupo.fecha);
                    }
                }
            }
            grupos.remove(grupo);
            grupo = null;
        } else {
            grupo.guia = reemplazo;
            ArrayList<ArrayList<Integer>> diasOcupados = reemplazo.getDiasOcupados();
            diasOcupados.add(fecha);
            reemplazo.setDiasOcupados(diasOcupados);
        }
    }

    /**
     * Reubica las reservas de clientes a este grupo si hay capacidad disponible.
     *
     * @param listaReservas La lista de reservas de clientes a reubicar.
     * @return Una lista de reservas que no pudieron ser reubicadas.
     */
    public ArrayList<ArrayList<Cliente>> reubicarReservas(ArrayList<ArrayList<Cliente>> listaReservas) {
        int cantidadClientesActuales = 0;
        ArrayList<ArrayList<Cliente>> listaFinal = listaReservas;
        for (ArrayList<Cliente> lista : listaReservas) {
            cantidadClientesActuales += lista.size();
        }
        for (ArrayList<Cliente> lista : listaReservas) {
            if (cantidadClientesActuales + lista.size() <= this.capacidad) {
                this.listaReservas.add(lista);
                cantidadClientesActuales += lista.size();
                listaFinal.remove(lista);
            }
        }
        return listaFinal;
    }

    /**
     * Busca grupos que coincidan con la fecha, actividad e idioma especificados.
     *
     * @param fecha     La fecha del grupo.
     * @param actividad La actividad del grupo.
     * @param idioma    El idioma del grupo.
     * @return Una lista de grupos que coinciden con los criterios especificados.
     */
    public static ArrayList<Grupo> buscarGrupo(ArrayList<Integer> fecha, Actividad actividad, Idiomas idioma) {
        ArrayList<Grupo> gruposEncontrados = new ArrayList<>();
        for (Grupo grupo : grupos) {
            if (grupo.fecha.equals(fecha) && grupo.actividad.equals(actividad) && grupo.idioma.equals(idioma)) {
                gruposEncontrados.add(grupo);
            }
        }
        return gruposEncontrados;
    }

    /**
     * Busca un grupo que coincida con la fecha y guía especificados.
     *
     * @param fecha La fecha del grupo.
     * @param guia  El guía del grupo.
     * @return El grupo que coincide con los criterios especificados o null si no se encuentra.
     */
    public static Grupo buscarGrupo(ArrayList<Integer> fecha, Guia guia) {
        for (Grupo grupo : grupos) {
            if (grupo.fecha.equals(fecha) && grupo.guia.equals(guia)) {
                return grupo;
            }
        }
        return null;
    }

    /**
     * Elige un guía disponible y capacitado para los parametros del grupo. Primero verifica si el guia esta disponible luego verifica
     * si coinciden los tipos de actividades para los cuales esta capacitado el guia y los tipos de la actividad, por ultimo si hay mas
     * de un guia que cumple los parametros se elige al de menor precio
     *
     * @return El guía elegido o null si no hay guías disponibles.
     */
    public Guia elegirGuia() {
        ArrayList<Guia> guiasCapacitados = this.actividad.buscarGuia(this.idioma);
        ArrayList<Guia> guiasDisponibles = Guia.buscarDisponibilidad(guiasCapacitados, this.fecha);
        ArrayList<TiposActividad> tipoActividad = this.actividad.getTipo();
        ArrayList<Guia> guiasFinal = new ArrayList<>();
        int mayorConcidencia = 0;

        for (Guia guia : guiasDisponibles) {
            int concidencia = 0;
            for (TiposActividad tipo : guia.getTipoActividades()) {
                if (tipoActividad.contains(tipo)) {
                    concidencia++;
                }
            }
            if (concidencia == mayorConcidencia) {
                guiasFinal.add(guia);
            } else if (concidencia > mayorConcidencia) {
                mayorConcidencia = concidencia;
                guiasFinal.clear();
                guiasFinal.add(guia);
            }
        }
        if (guiasFinal.size() == 1) {
            return guiasFinal.get(0);
        } else if (guiasFinal.size() > 1) {
            double menorPrecio = guiasFinal.get(0).getPrecio();
            Guia guiaElegido = guiasFinal.get(0);
            for (Guia guia : guiasFinal) {
                double precio = guia.getPrecio();
                if (precio < menorPrecio) {
                    guiaElegido = guia;
                    menorPrecio = precio;
                }
            }
            return guiaElegido;
        } else {
            return null;
        }
    }

    /**
     * Retira una actividad y sus reservas en las fechas especificadas. Se le da un bono a cada cliente que haya reservado la actividad
     * se cambia la disponibilidad de los guias y se eliminan los grupos de reservas
     *
     * @param actividad La actividad a retirar.
     * @param fecha     Las fechas en las que se debe retirar la actividad.
     */
    public static void retirarActividad(Actividad actividad, ArrayList<ArrayList<Integer>> fecha) {
        for (Grupo grupo : grupos) {
            if (grupo.actividad.equals(actividad) && (fecha.isEmpty() || fecha.contains(grupo.fecha))) {
                for (ArrayList<Cliente> reservas : grupo.listaReservas) {
                    for (Cliente cliente : reservas) {
                        cliente.cancelarActividad(grupo.actividad, grupo.fecha);
                    }
                }
                ArrayList<ArrayList<Integer>> diasOcupados = grupo.guia.getDiasOcupados();
                diasOcupados.remove(grupo.fecha);
                grupo.guia.setDiasOcupados(diasOcupados);
                grupo = null;
            }
        }
    }

    /**
     * Busca el idioma más usado en una lista de idiomas.
     *
     * @param listaIidiomas La lista de idiomas a evaluar.
     * @return Una lista de cadenas que representa el idioma más usado y su frecuencia.
     */
    public static ArrayList<String> buscarIdiomaMasUsado(ArrayList<Idiomas> listaIidiomas) {
        int contador = 0;
        int cantidadMaxima = 0;
        ArrayList<Idiomas> idiomaMasUsado = new ArrayList<>();

        for (Idiomas idioma : Idiomas.values()) {
            contador = 0;
            for (Idiomas x : listaIidiomas) {
                if (x.equals(idioma)) {
                    contador++;
                }
            }

            if (contador == cantidadMaxima) {
                idiomaMasUsado.add(idioma);
            } else if (contador > cantidadMaxima) {
                idiomaMasUsado.clear();
                idiomaMasUsado.add(idioma);
                cantidadMaxima = contador;
            }
        }

        ArrayList<String> listaIdioma = new ArrayList<>();

        for (int i = 0; i < idiomaMasUsado.size(); i++) {
            String idioma = idiomaMasUsado.get(i) + "=" + cantidadMaxima + "/" + listaIidiomas.size();
            listaIdioma.add(idioma);
        }

        return listaIdioma;
    }

    /**
     * Calcula la cantidad de clientes en grupos que coinciden con las fechas e idioma especificados.
     *
     * @param listaFechas Las fechas de los grupos.
     * @param idioma      El idioma de los grupos.
     * @return La cantidad de clientes en los grupos que coinciden con los criterios especificados.
     */
    public static int cantidadClientesIdioma(ArrayList<ArrayList<Integer>> listaFechas, Idiomas idioma) {
        int cantidad = 0;
        for (Grupo grupo : grupos) {
            for (ArrayList<Integer> fecha : listaFechas) {
                if (grupo.idioma.equals(idioma) && grupo.fecha.equals(fecha)) {
                    for (ArrayList<Cliente> reserva : grupo.listaReservas) {
                        cantidad += reserva.size();
                    }
                }
            }
        }
        return cantidad;
    }

    /**
     * Calcula la cantidad de clientes en grupos que reservaron actividades en un destino específico.
     *
     * @param destino El destino de las actividades de los grupos.
     * @param fecha La fecha por la cual se quiere buscar(puede ser null)
     * @return La cantidad de clientes en los grupos que tienen actividades en el destino especificado.
     */
    public static int cantidadClientesDestino(Destino destino, ArrayList<Integer> fecha) {
        int cantidadClientes = 0;
        
        for (Grupo grupo : grupos) {
        	boolean istFecha=fecha==null?true:grupo.fecha.get(1)==fecha.get(1);
            if (grupo.actividad.getDestino().equals(destino)&&istFecha) {
                for (ArrayList<Cliente> reserva : grupo.listaReservas) {
                    cantidadClientes += reserva.size();
                }
            }
        }
        return cantidadClientes;
    }
//////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////Métodos de acceso//////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<Integer> getFecha() {
		return fecha;
	}
	
	public void setFecha(ArrayList<Integer> fecha) {
		this.fecha = fecha;
	}
	
	public Guia getGuia() {
		return guia;
	}
	
	public void setGuia(Guia guia) {
	this.guia = guia;
	}
	
	public Actividad getActividad() {
		return actividad;
	}
	
	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	
	public Idiomas getIdioma() {
		return idioma;
	}
	
	public void setIdioma(Idiomas idioma) {
		this.idioma = idioma;
	}
	
	public ArrayList<ArrayList<Cliente>> getListaReservas() {
		return listaReservas;
	}
	
	public void setListaReservas(ArrayList<ArrayList<Cliente>> listaReservas) {
		this.listaReservas = listaReservas;
	}
	
	public int getCapacidad() {
		return capacidad;
	}
	
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
	public int getClasificacion() {
		return clasificacion;
	}
	
	public void setClasificacion(int clasificacion) {
		this.clasificacion = clasificacion;
	}

}
