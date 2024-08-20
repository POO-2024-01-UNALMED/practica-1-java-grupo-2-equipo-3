package gestorAplicacion.manejoReserva;

import java.io.Serializable;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.HashMap;
=======
import java.util.Arrays;
>>>>>>> refs/remotes/origin/master

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Cliente;
import gestorAplicacion.gestionHum.Guia;
import gestorAplicacion.interfaces.Registrable;
import gestorAplicacion.actividades.Plan;;

public class Actividad implements Registrable, Serializable {
    private String nombre;
    private Destino destino;
    private ArrayList<TiposActividad> tipo;
    private ArrayList<Guia> guias;
    private int capacidad;
    private int clasificacion;
    private double precio;
    private Plan plan;
    private String tipoPlan;

    /**
     * Constructor para crear una actividad con un nombre y un destino.
     *
     * @param nombre El nombre de la actividad.
     * @param destino El destino asociado a la actividad.
     */
    public Actividad(String nombre, Destino destino) {
        super();
        this.nombre = nombre;
        this.destino = destino;
        this.guias = new ArrayList<>();
        this.tipo = new ArrayList<>();
    }

    /**
     * Constructor para crear una actividad con un nombre y un tipo de actividad.
     *
     * @param nombre El nombre de la actividad.
     * @param tipo El tipo de actividad.
     */
    public Actividad(String nombre, TiposActividad tipo) {
        super();
        this.nombre = nombre;
        this.tipo = new ArrayList<>();
        this.tipo.add(tipo);
        this.guias = new ArrayList<>();
    }
    @Override
    public String toString() {
        return "Nombre de la actividad: " + nombre + ",\nSe encuentra en: " + destino.getNombre() + ",\nTipo de actividad: " + tipo + ",\nCapacidad por grupo: " + capacidad + ",\nClasificacion por edad: +" + clasificacion + ",\nPrecio por persona: " + precio;
    }

    
	/**
     * Ingresa la lista de guías a la actividad basándose en los tipos de actividad para los cuales esta capacitado el guia y el destino.
     */
    @Override
    public void ingresarGuia() {
        for (TiposActividad tipo : this.tipo) {
            for (Guia guia : Guia.getGuias()) {
                if (guia.getTipoActividades().contains(tipo) && guia.getDestino().equals(destino) && !this.guias.contains(guia)) {
                    this.guias.add(guia);
                }
            }
        }
    }
    /**
     * Ingresa tipos de actividades a la actividad basada en una cadena de posiciones.
     *
     * @param tipoActividades Una cadena que contiene las posiciones de los tipos de actividades.
     */
    @Override
    public void ingresarTipoActividades(String tipoActividades) {
    	String[] listaString = tipoActividades.split(" ");
	    for (String numero : listaString) {
	        int indice = Integer.parseInt(numero);
	        this.tipo.add(TiposActividad.values()[indice - 1]);
	    }
        if (this.tipo.size() == 1) {
            this.tipo.add(this.tipo.get(0));
        }
    }

    /**
     * Asigna parámetros como capacidad, clasificación y precio a la actividad en base a sus tipos de actividades.
     */
    @Override
    public void asignarParametros() {
        int capacidad = 0;
        int clasificacion = 0;
        int precioB = 0;
        for (TiposActividad tipo : this.tipo) {
            switch (tipo.getDificultad()) {//Asignar una capacidad base segun su dificultad
                case "Baja":
                    capacidad += 15;
                    clasificacion += 30;
                    break;
                case "Media":
                    capacidad += 10;
                    clasificacion += 15;
                    precioB += 10;
                    break;
                case "Alta":
                    capacidad += 8;
                    clasificacion += 10;
                    precioB += 100;
                    break;
                case "Extrema":
                    capacidad += 5;
                    clasificacion += 5;
                    precioB += 1000;
                    break;
            }
        }
        //Asigna un extra de capacidad o resta la capacidad segun la cantidad de guias y la rareza de la actividad
        int actividadesConMasGuias = 0;
        int actividadesConMenosGuias = 0;
        int cantidadActividadesConMismoTipo = 0;
        for (Actividad actividad : this.destino.getActividades()) {
            if (actividad.guias.size() > this.guias.size()) {
                actividadesConMasGuias++;
            } else {
                actividadesConMenosGuias++;
            }
            for (TiposActividad tipo : this.tipo) {
                if (actividad.tipo.contains(tipo)) {
                    cantidadActividadesConMismoTipo++;
                }

                if ((this.destino.getActividades().size() / 4) > cantidadActividadesConMismoTipo) {capacidad += 3;}//Menos del 25% de actividades en el destino son del mismo tipo
                else if ((this.destino.getActividades().size() / 2) > cantidadActividadesConMismoTipo) {capacidad += 2;}//Menos del 50% de actividades en el destino son del mismo tipo
                else if ((this.destino.getActividades().size() *0.75) < cantidadActividadesConMismoTipo) {capacidad -= 1;}//Mas del 75% de actividades en el destino son del mismo tipo
            }
        }
        if (((actividadesConMasGuias + actividadesConMenosGuias) / 4) > actividadesConMasGuias) {capacidad -= 5;} //Tiene mas guias que el 75% de actividades en el destino 
        else if (((actividadesConMasGuias + actividadesConMenosGuias) / 2) > actividadesConMasGuias) {capacidad -= 3;} //Tiene mas guias que el 50% de actividades en el destino 
        else if (((actividadesConMasGuias + actividadesConMenosGuias) / 4) > actividadesConMenosGuias) {capacidad += 3; }//Esta entre el 25% de actividades con menos guias

        this.capacidad = capacidad;

        //Asigna una clasificacion segun la dificultad      
        if (clasificacion <=10) {
            this.clasificacion = 4;
        } else if (clasificacion <=20) {
            this.clasificacion = 3;
        } else if(clasificacion<=30){
            this.clasificacion = 2;
        } else {
        	this.clasificacion = 1;
        }
        
        //Asigna un precio segun la dificultad y la capacidad
        double precio = 0;
        if (precioB > 1000) {
            precio = (60000 * (capacidad) + 930000) / (capacidad);
        } else if (precioB > 100) {
            precio = (30000 * (capacidad) + 1150000) / (capacidad);
        } else if (precioB > 10) {
            precio = (4000 * (capacidad) + 1400000) / (capacidad);
        } else {
            precio = 1200000 / (capacidad);
        }

        this.precio = Math.round(precio / 100) * 100;
    }

    /**
     * Busca en los guias capacitados para la actividad los guías que hablen un idioma específico.
     *
     * @param idioma El idioma requerido.
     * @return Una lista de guías que hablan el idioma especificado.
     */
    public ArrayList<Guia> buscarGuia(Idiomas idioma) {
        ArrayList<Guia> guiasCapacitados = new ArrayList<>();
        for (Guia guia : guias) {
            ArrayList<Idiomas> idiomas = guia.getIdiomas();
            if (idiomas.contains(idioma)) {
                guiasCapacitados.add(guia);
            }
        }
        return guiasCapacitados;
    }

    /**
     * Retira un guía de todas las actividades en su destino.
     *
     * @param guia El guía a retirar.
     */
    public static void retirarGuia(Guia guia) {
        Destino destino = guia.getDestino();
        ArrayList<Actividad> listaActividades = destino.mostrarActividadesTipo(guia);
        for (Actividad actividad : listaActividades) {
            if (actividad.guias.contains(guia)) {
                actividad.guias.remove(guia);
            }
        }
    }


    /**
     * Busca una actividad por su nombre y destino.
     *
     * @param nombre El nombre de la actividad.
     * @param destino El nombre del destino.
     * @return La actividad encontrada, o null si no se encuentra.
     */
    public static Actividad buscarActividad(String nombre, String destino) {
        Destino destinoActividad = Destino.buscarNombre(destino);
        if (destinoActividad != null) {
            for (Actividad actividad : destinoActividad.getActividades()) {
                if (nombre.equals(actividad.nombre)) {
                    return actividad;
                }
            }
        }
        return null;
    }

    /**
     * Retira una actividad de su destino y la elimina.
     *
     * @param actividad La actividad a retirar.
     * @return true si la actividad fue retirada exitosamente, false en caso contrario.
     */
    public static boolean retirarActividad(Actividad actividad) {
        Grupo.retirarActividad(actividad, null);

        ArrayList<Actividad> actividades = actividad.destino.getActividades();
        actividades.remove(actividad);
        actividad.destino.setActividades(actividades);
        actividad = null;
        return true;
    }

    /**
     * Muestra un string del rango de edad de cada clasificacion
     *
     * @param indice o numero de la clasificacion
     * @return string del rango
     */
    public static String mostrarClasificacion(int indice) {
    	ArrayList<String> opcionesClasificacion=new ArrayList<>(Arrays.asList(
				"[0<edad<7]","[7<edad<15]","[15<edad<18]","[18<edad]"));
		return opcionesClasificacion.get(indice-1);
    }

    /**
     * Verifica si la capacidad de la actividad es mayor o igual a la cantidad de clientes
     *
     * @param Reserva o numero de la clasificacion
     * @return Boolenam si la capacidad de la actividad es mayor o igual a la cantidad de clientes
     */
    public static boolean verificarCapacidadActividad(Reserva reserva){


        if (reserva.getActividades().getCapacidad() >= reserva.getClientes().size()){
            return true;
        }
    
        return true;
    }

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////Métodos de acceso//////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////

    public void setGuias(Guia guia) {
        this.guias.add(guia);
    }
    
    public String getNombre() {
		return nombre;
	}

    public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Destino getDestino() {
		return destino;
	}

	public void setDestino(Destino destino) {
		this.destino = destino;
	}

	public ArrayList<TiposActividad> getTipo() {
		return tipo;
	}

	public void setTipo(ArrayList<TiposActividad> tipo) {
		this.tipo = tipo;
	}

	public ArrayList<Guia> getGuias() {
		return guias;
	}

	public void setGuias(ArrayList<Guia> guias) {
		this.guias = guias;
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

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public String getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(String tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

}
