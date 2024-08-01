package gestorAplicacion.gestionHum;
import java.util.ArrayList;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.manejoReserva.Actividad;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.manejoReserva.Reserva;

/**
 * La clase Guia representa a un guía turístico.
 * Extiende de la clase Persona y contiene información sobre el nombre,
 * idiomas, tipos de actividades que puede realizar, destino asignado,
 * precio, y días ocupados y no disponibles.
 */
public class Guia extends Persona {
    private ArrayList<TiposActividad> tipoActividades;
    private double precio;
    private ArrayList<ArrayList<Integer>> diasOcupados;
    private ArrayList<ArrayList<Integer>> diasNoDisponibles;
    private static ArrayList<Guia> guias = new ArrayList<>();

    /**
     * Constructor de la clase Guia.
     *
     * @param nombre El nombre del guía.
     */
    public Guia(String nombre, int edad, Destino destino, String[] idioma, String[] seguro, Grupo grupo, ArrayList<TiposActividad> tipoActividades, double precio, ArrayList<ArrayList<Integer>> diasOcupados, ArrayList<ArrayList<Integer>> diasNoDisponibles) {
        super(nombre, edad, destino, idioma, seguro, grupo);
        this.tipoActividades = tipoActividades;
        this.precio = precio;
        this.diasOcupados = diasOcupados;
        this.diasNoDisponibles = diasNoDisponibles;
        guias.add(this);
    }



    public String[] getIdiomas() {
        return idioma;
    }

    void setIdiomas(String[] idioma) {
        this.idioma = idioma;
    }

    

    public ArrayList<TiposActividad> getTipoActividades() {
        return tipoActividades;
    }

    void setTipoActividades(ArrayList<TiposActividad> tipoActividades) {
        this.tipoActividades = tipoActividades;
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

    public double getPrecio() {
        return precio;
    }

    void setPrecio(int precio) {
        this.precio = precio;
    }

    
    public ArrayList<ArrayList<Integer>> getDiasOcupados() {
        return diasOcupados;
    }

    public void setDiasOcupados(ArrayList<ArrayList<Integer>> diasOcupados) {
        this.diasOcupados = diasOcupados;
    }

    public ArrayList<ArrayList<Integer>> getDiasNoDisponibles() {
        return diasNoDisponibles;
    }

    void setDiasNoDisponibles(ArrayList<ArrayList<Integer>> diasNoDisponibles) {
        this.diasNoDisponibles = diasNoDisponibles;
    }

    public static ArrayList<Guia> getGuias() {
        return guias;
    }

    static void setGuias(ArrayList<Guia> guias) {
        Guia.guias = guias;
    }

    /**
     * Devuelve una representación en cadena del objeto Guia.
     *
     * @return Una cadena que representa al guía.
     */
    @Override
    public String toString() {
        return "Nombre del guia: " + nombre + ",\nIdiomas que domina: " + idiomas + ",\nTipo de actividades: " + tipoActividades + ",\nDestino asignado: "
                + destino.getNombre();
    }

    /**
     * Ingresa los idiomas que domina el guía a partir de una cadena.
     *
     * @param idiomas Una cadena que representa los idiomas.
     */
    public void ingresarIdiomas(String idiomas) {
        for (String posicion : idiomas.split(" ")) {
            int numero = Integer.parseInt(posicion);
            for (Idiomas idioma : Idiomas.values()) {
                if (numero == idioma.getPosicion()) {
                    this.idiomas.add(idioma);
                }
            }
        }
    }

    /**
     * Ingresa los tipos de actividades que puede realizar el guía a partir de una cadena.
     *
     * @param tipoActividades Una cadena que representa los tipos de actividades.
     */
    public void ingresarTipoActividades(String tipoActividades) {
        for (String posicion : tipoActividades.split(" ")) {
            int numero = Integer.parseInt(posicion);
            for (TiposActividad tipo : TiposActividad.values()) {
                if (numero == tipo.getPosicion()) {
                    this.tipoActividades.add(tipo);
                }
            }
        }
    }

    /**
     * Asigna un precio al guía basado en los idiomas que domina y el destino asignado.
     */
    public void asignarPrecioBase() {
        int precioBase = 30000;
        int precioExtra = 0;
        double porcentajeExtra=destino.precioExtraPorDestino();
        for (Idiomas idioma : this.idiomas) {
            precioExtra += idioma.getPrecio();
        }
        
        double precioFinal = (precioBase*porcentajeExtra)+ precioExtra;
        this.precio = precioFinal;
    }

    /**
     * Ingresa el guía a las actividades que puede realizar en su destino.
     */
    public void ingresarGuia() {
        ArrayList<Actividad> listaActividades = this.destino.mostrarActividadesTipo(this);
        for (Actividad actividad : listaActividades) {
            if (!actividad.getGuias().contains(this)) {
                actividad.setGuias(this);
            }
        }
    }

    /**
     * Busca un guía por su nombre.
     *
     * @param nombre El nombre del guía a buscar.
     * @return El guía encontrado o null si no se encuentra.
     */
    public static Guia buscarGuia(String nombre) {
        for (Guia guia : guias) {
            if (nombre.equals(guia.nombre)) {
                return guia;
            }
        }
        return null;
    }

    /**
     * Busca guías disponibles en una fecha específica entre los guías capacitados.
     *
     * @param guiasCapacitados Una lista de guías capacitados.
     * @param fecha            La fecha a verificar.
     * @return Una lista de guías disponibles.
     */
    public static ArrayList<Guia> buscarDisponibilidad(ArrayList<Guia> guiasCapacitados, ArrayList<Integer> fecha) {
        ArrayList<Guia> guiasDisponibles = new ArrayList<>();
        for (Guia guia : guiasCapacitados) {
            if (!guia.diasOcupados.contains(fecha)) {
                guiasDisponibles.add(guia);
            }
        }
        return guiasDisponibles;
    }

    /**
     * Retira un guía en una fecha especifica y actualiza sus días ocupados y no disponibles.
     *
     * @param retirado    El guía a retirar.
     * @param listaFechas Una lista de fechas a actualizar.
     */
    public static void retirarGuia(Guia retirado, ArrayList<ArrayList<Integer>> listaFechas) {
        for (ArrayList<Integer> dia : retirado.diasOcupados) {
            if (!retirado.diasNoDisponibles.contains(dia) && (listaFechas == null || listaFechas.contains(dia))) {
                Grupo.retirarGuia(retirado, dia);
            }
        }

        for (ArrayList<Integer> dia : listaFechas) {
            if (!retirado.diasOcupados.contains(dia)) {
                retirado.diasOcupados.add(dia);
            }
            if (!retirado.diasNoDisponibles.contains(dia)) {
                retirado.diasNoDisponibles.add(dia);
            }
        }
    }

    /**
     * Retira un guía despedido.
     *
     * @param despedido El guía a despedir.
     */
    public static void retirarGuia(Guia despedido) {
        Guia.retirarGuia(despedido, null);

        Actividad.retirarGuia(despedido);
        Guia.guias.remove(despedido);
        despedido = null;
    }

    /**
     * Muestra la tabla de disponibilidad de los guías en una fecha específica.
     *
     * @param fecha   La fecha a verificar.
     * @param destino El destino a verificar (puede ser null).
     * @param idioma  El idioma a verificar (puede ser null).
     * @return Una lista de objetos con la disponibilidad de los guías.
     */
    public static ArrayList<Object> mostrarDisponibilidadGuias(ArrayList<Integer> fecha, Destino destino, Idiomas idioma) {
        ArrayList<Object> tabla = new ArrayList<>();

        int guiasOcupados = 0;
        int guiasDisponibles = 0;
        ArrayList<Destino> destinos = new ArrayList<>();
        ArrayList<Idiomas> idiomas = new ArrayList<>();
        ArrayList<Actividad> actividades = new ArrayList<>();
        int contadorActividades = 0;
        int contadorClientes = 0;
        int contadorGuiasIdioma = 0;

        for (Guia guia : guias) {
            Grupo grupoGuia = Grupo.buscarGrupo(fecha, guia);
            boolean isDestinoMatch = (destino == null) || guia.destino.equals(destino);
            boolean isIdiomaMatch = (idioma == null) || grupoGuia.getIdioma().equals(idioma);

            if (isDestinoMatch && guia.diasOcupados.contains(fecha) && isIdiomaMatch) {
                guiasOcupados++;

                destinos.add(guia.destino);
                idiomas.add(grupoGuia.getIdioma());
                for (ArrayList<Cliente> Reserva : grupoGuia.getListaReservas()) {
                    contadorClientes += Reserva.size();
                }

                if (!actividades.contains(grupoGuia.getActividad())) {
                    actividades.add(grupoGuia.getActividad());
                    contadorActividades++;
                }
            } else {
                guiasDisponibles++;
            }

            if (idioma != null && guia.idiomas.contains(idioma)) {
                contadorGuiasIdioma++;
            }
        }

        String actividad = (contadorActividades + "/" + actividades.size());
        ArrayList<String> destinoComun = Destino.buscarDestinoComun(destinos);
        ArrayList<String> listIdioma = Grupo.buscarIdiomaMasUsado(idiomas);

        tabla.add(0, fecha.get(2)); // año
        tabla.add(1, Reserva.mostrarMes(fecha.get(1))); // mes
        tabla.add(2, fecha.get(0)); // día
        tabla.add(3, guiasDisponibles); // guías disponibles
        tabla.add(4, guiasOcupados); // guías ocupados
        tabla.add(5, actividad); // contador actividades

        if (destino == null) {
            tabla.add(6, destino);
        } // destino
        else {
            tabla.add(6, destinoComun);
        } // destino común

        if (idioma == null) {
            tabla.add(7, idioma);
        } // idioma
        else {
            tabla.add(7, listIdioma);
        } // idioma más usado

        tabla.add(8, contadorClientes); // contador clientes
        tabla.add(9, contadorGuiasIdioma); // contador guías por idioma

        return tabla;
    }

    /**
     * Muestra la tabla del itinerario del guía en una fecha específica.
     *
     * @param fecha La fecha a verificar.
     * @return Una lista de objetos con el itinerario del guía.
     */
    public ArrayList<Object> mostrarIntinerario(ArrayList<Integer> fecha) {
        ArrayList<Object> tabla = new ArrayList<>();

        String estado = "Disponible";
        ArrayList<String> tipo = new ArrayList<>();
        Grupo grupo = null;
        int cantidadPersonas = 0;

        if (this.diasOcupados.contains(fecha)) {
            estado = "Ocupado";
            grupo = Grupo.buscarGrupo(fecha, this);
        } else if (this.diasNoDisponibles.contains(fecha)) {
            estado = "No disponible";
        }

        for (ArrayList<Cliente> Reserva : grupo.getListaReservas()) {
            cantidadPersonas += Reserva.size();
        }

        for (TiposActividad x : grupo.getActividad().getTipo()) {
            tipo.add(x.getNombre() + ",");
        }
        tabla.add(0, fecha.get(2)); // año
        tabla.add(1, Reserva.mostrarMes(fecha.get(1))); // mes
        tabla.add(2, fecha.get(0)); // día
        tabla.add(3, this.nombre); // nombre
        tabla.add(4, this.destino); // destino
        tabla.add(5, estado); // estado
        tabla.add(6, grupo.getActividad().getNombre()); // actividad
        tabla.add(7, tipo); // tipo actividad
        tabla.add(8, grupo.getIdioma()); // idioma
        tabla.add(9, cantidadPersonas); // cantidad de personas
        tabla.add(10, grupo.getClasificacion()); // clasificación

        return tabla;
    }

    /**
     * Asigna un guía a un grupo.
     *
     * @param grupo El grupo al que se asignará el guía.
     */
    public void asignarGuia(Grupo grupo) {
        this.diasOcupados.add(grupo.getFecha());
        grupo.setGuia(this);
    }
    
    /**
     * Muestra el precio del guia segun la fecha de la reserva.
     *
     * @param fecha La fecha del grupo al cual el guia esta asignado.
     */
    public double mostrarPrecioGuia(ArrayList<Integer> fecha) {
    	double porcentajeExtra=this.destino.precioExtraPorTemporada(fecha);
    	return (this.precio*porcentajeExtra);
    }
    
}
