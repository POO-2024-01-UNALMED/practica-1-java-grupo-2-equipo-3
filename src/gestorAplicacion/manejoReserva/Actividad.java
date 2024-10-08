package gestorAplicacion.manejoReserva;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Cliente;
import gestorAplicacion.gestionHum.Guia;
import gestorAplicacion.interfaces.Registrable;
import gestorAplicacion.actividades.Plan;;

public class Actividad implements Registrable, Serializable {
    private static final long serialVersionUID = 6L;
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

    public Actividad(String nombre, Destino destino, TiposActividad tipos) {
        super();
        this.nombre = nombre;
        this.destino = destino;
        destino.getActividades().add(this);
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
     * Verifica si una actividad cumple con los filtros especificados.
     * 
     * @param clasificacionFiltro   Filtro de clasificación (0 = sin filtro).
     * @param tipoFiltro            Filtro de tipo de actividad (null = sin filtro).
     * @param fechaFiltro           Filtro de fecha (null = sin filtro).
     * @param idiomaFiltro          Filtro de idioma (null = sin filtro).
     * 
     * @return                      true si la actividad cumple con todos los filtros aplicados, de lo contrario, false.
     */
    public boolean verificarFiltrosActividad(int clasificacionFiltro, TiposActividad tipoFiltro,ArrayList<ArrayList<Integer>> fechaFiltro,Idiomas idiomaFiltro) {
   	 boolean isClasificacionMatch = (clasificacionFiltro==0) || clasificacion==clasificacionFiltro;
   	 boolean isTipoMatch=(tipoFiltro==null)||verificarTipoActividad(tipoFiltro);
   	 boolean isIdiomaMatch=(idiomaFiltro==null)||buscarGuia(idiomaFiltro).size()!=0;
   	 boolean isFechaDisponible=false;
   	 
   	 for(ArrayList<Integer> fecha:fechaFiltro) {
   		 if(this.verificaActividadDisponible(fecha,idiomaFiltro)) {isFechaDisponible=true;}
   	 }
   	 boolean isFechaMatch=(fechaFiltro==null)||isFechaDisponible;
   	 
   	 if(isClasificacionMatch&&isTipoMatch&&isIdiomaMatch&&isFechaMatch) {return true;}
   	 return false;
    }

    /**
     * Verifica si la actividad es de cierto tipo
     *
     * @param tipoFiltro el tipo a buscar.
     * @return true si la actividad es de ese tipo.
     */
    public boolean verificarTipoActividad(TiposActividad tipoFiltro) {
      	 for(TiposActividad tipoFor:tipo) {
      		 if(tipoFiltro.equals(tipoFor)) {return true;}
      	}return false;
    }

    /**
     * Muestra un lista de los nombres de una lista de actividades dada
     *
     * @param actividadesLista la ll+ista de actividades dadas
     * @return nombres un ArrayList<String> de los nombres
     */
    public static ArrayList<String> mostrarNombres(ArrayList<Actividad> actividadesLista){
    	 ArrayList<String> nombres=new ArrayList<>();
    	 if(actividadesLista==null||actividadesLista.isEmpty()) {return null;}
    	 for(Actividad actividad:actividadesLista) {
    		 nombres.add(actividad.nombre);
    	 }
    	 return nombres;
    }

    /**
     * Muestra si la actividad esta disponible en ese dia
     *
     * @param fecha fecha a verificar
     * @return true si esta disponible
     */
    public boolean verificaActividadDisponible(ArrayList<Integer> fecha,Idiomas idioma) {
    	ArrayList<Grupo> existenGrupos =new ArrayList<>();
    	if(idioma==null) {existenGrupos = Grupo.buscarGrupo(fecha,this);}
    	else {existenGrupos = Grupo.buscarGrupo(fecha,this,idioma);}
            if (existenGrupos.size() > 0) {
                return true;
            } else if (existenGrupos.isEmpty()) {
                ArrayList<Guia> guiasCapacitados = destino.getGuias();
                ArrayList<Guia> guiasConDisponibilidad = Guia.buscarDisponibilidad(guiasCapacitados, fecha);
                if (guiasConDisponibilidad.size() > 0) {
                    return true;
                }
            }
        return false;
    }

    /**
     * Genera una tabla con información de actividades basada en filtros como clasificación, tipo de actividad, fecha e idioma.
     * El contenido de la tabla depende de la opción de búsqueda proporcionada.
     *
     * @param opcBusqueda  Opción de búsqueda que define el contenido de la tabla. Valores posibles: "1" o "2".
     * @param clasificacion Clasificación a filtrar para las actividades.
     * @param tipo          Tipo de actividad a filtrar.
     * @param fecha         Lista de fechas para filtrar las actividades.
     * @param idioma        Idioma a filtrar.
     *
     * @return              Una tabla (lista de objetos) con la información solicitada según los filtros aplicados.
     */
    public ArrayList<Object> mostrarPlaneacionActividades(String opcBusqueda,int clasificacion, TiposActividad tipo,ArrayList<ArrayList<Integer>> fecha,Idiomas idioma){
         ArrayList<Object> tabla=new ArrayList<Object>();

         ArrayList<String> posicion1=new ArrayList<>();//"1"=actividades familiares;"2"=disponibilidad de actividades
         ArrayList<String> posicion2=new ArrayList<>();;//"1"=actividades ecologicas;"2"=promedio de precios actividades
         ArrayList<String> posicion3=new ArrayList<>();;//"1"=actividades ecologicas;"2"=cantidad de personas
         ArrayList<String> posicion4=new ArrayList<>();;//"1"=actividades extremas;"2"=cantidad de guias
         ArrayList<String> posicion5=new ArrayList<>();;//"1"=actividades acuaticas;"2"= clasificacion mas solicitada
         ArrayList<String> posicion6=new ArrayList<>();;//"1"=actividades deportivas;"2"=oferta
         ArrayList<String> posicion7=new ArrayList<>();;//"1"=total de actividades;"2"=[];

             posicion1.add(Integer.toString(capacidad));
             posicion2.add(mostrarClasificacion(this.clasificacion));
             posicion3.add(Integer.toString(Reserva.mostrarCantidadReservasActividad(destino,fecha,this)));
             posicion4.add(this.tipo.get(0).getNombre());
             posicion5.add(this.tipo.get(0).getDificultad());
             posicion6.add(buscarIdiomaComun().getNombre());
             posicion7.add(precio+"$");

            tabla.add(0, idioma.getNombre()); //0.nombre del idioma
            tabla.add(1, posicion1); // 1."1"=actividades familiares;"2"=disponibilidad de actividades
            tabla.add(2, posicion2); // 2."1"=actividades ecologicas;"2"=promedio de precios actividades
            tabla.add(3, posicion3); // 3."1"=actividades ecologicas;"2"=cantidad de personas
            tabla.add(4, posicion4); // 4."1"=actividades extremas;"2"=cantidad de guias
            tabla.add(5, posicion5); // 5."1"=actividades acuaticas;"2"= clasificacion mas solicitada
            tabla.add(6, posicion6); // 6."1"=actividades deportivas;"2"=oferta
            tabla.add(7, posicion7); // 7."1"=total de actividades;"2"=[];
      
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
     * Calcula el promedio de los precios de una lista de actividades.
     * 
     * @param actividadesLista  Lista de objetos Actividad para los que se calculará el promedio del precio.
     * 
     * @return                  El promedio de los precios de las actividades en la lista.
     */
    public static long promedioPreciosActividades(ArrayList<Actividad> actividadesLista) {
    	if(actividadesLista == null || actividadesLista.isEmpty()) {return 0;}
    	long promedio=0;
    	for(Actividad actividad:actividadesLista) {
    		promedio+=actividad.precio;
    	}
    	return promedio/actividadesLista.size();
    }

    /**
     * Calcula el promedio de los precios de una lista de actividades.
     * 
     * @param actividadesLista  Lista de objetos Actividad para los que se calculará el promedio del precio.
     * @param tipoFiltro        el tipo de actividad por el cual se quiere filtrar
     * @return                  El promedio de los precios de las actividades en la lista.
     */
    public static long promedioPreciosActividades(ArrayList<Actividad> actividadesLista,TiposActividad tipoFiltro) {
    	if(actividadesLista == null || actividadesLista.isEmpty()) {return 0;}
    	long promedio=0;
    	for(Actividad actividad:actividadesLista) {
    		if(actividad.tipo.contains(tipoFiltro)){promedio+=actividad.precio;}
    	}
    	return promedio/actividadesLista.size();
    }
    
    /**
     * Calcula la cantidad de guías únicas disponibles en una lista de actividades.
     * 
     * @param actividadesLista  Lista de objetos Actividad que contienen guías.
     * 
     * @return                  El número de guías únicas disponibles en la lista de actividades.
     */
    public static int cantidadGuiasDisponiblesLista(ArrayList<Actividad> actividadesLista) {
        if (actividadesLista == null) {return 0;}
        ArrayList<Guia> guias = new ArrayList<>();
        
        for (Actividad actividad : actividadesLista) {
            for (Guia guia : actividad.guias) {
                if (!guias.contains(guia)) {guias.add(guia);}
            }
        }
        return guias.size();
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
