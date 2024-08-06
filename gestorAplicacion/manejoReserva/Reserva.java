package gestorAplicacion.manejoReserva;

import java.util.ArrayList;
import java.util.Arrays;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.actividades.Plan;

public class Reserva {
    private static ArrayList<Reserva> reservasExistentes = new ArrayList<>();
    private long codigo;
    private Destino destino;
    private ArrayList<Idiomas> idiomas;
    private ArrayList<ArrayList<Integer>>  fechas;
    private int clasificacion;
    private String tipoPlan;
    private boolean existeSuscripcion;
    private Plan plan;

  
    public Reserva(long codigo, Destino destino, ArrayList<Idiomas> idiomas, ArrayList<ArrayList<Integer>> fechas, int clasificacion, String tipoPlan, boolean existeSuscripcion, Plan plan) {
        this.codigo = codigo;
        this.destino = destino;
        this.idiomas = idiomas;
        this.fechas = fechas;
        this.clasificacion = clasificacion;
        this.tipoPlan = tipoPlan;
        this.existeSuscripcion = existeSuscripcion;
        this.plan = plan;
    }




    public static Reserva buscarReserva(long codigo){  // por qué no me reconoce que le estoy regresando una reserva
        for (int i = 0; i < reservasExistentes.size(); i++){
            if (codigo == reservasExistentes.get(i).codigo){
                return reservasExistentes.get(i);
            }
        }
        return null;
    }

    public void añadirCliente(String nombre, int edad) {
        Cliente cliente = new Cliente(nombre, edad);


        

    }

    /**
     * Muestra una lista de días consecutivos a partir de una fecha dada.
     *
     * @param cantidadDias La cantidad de días a mostrar.
     * @param fecha La fecha de inicio en formato [día, mes, año].
     * @return Una lista de listas que representan las fechas en formato [día, mes, año].
     */
    public static ArrayList<ArrayList<Integer>> mostrarDias(int cantidadDias, ArrayList<Integer> fechaInicio) {
        int dia = fechaInicio.get(0);
        int mes = fechaInicio.get(1);
        int año = fechaInicio.get(2);

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

    /**VARIAS OPCIONES DE ENTRADA
     * Verifica si una lista de números en una cadena está dentro de un rango específico. 
     *
     * @param i El límite superior del rango.
     * @param entrada La cadena que contiene los números a verificar.
     * @return true si todos los números están dentro del rango, false en caso contrario.
     */
    public static boolean verificarLista(int i, String entrada) {
        if (entrada == null || entrada.isEmpty()) {
            return false;
        } 
        else {
            String[] numeros = entrada.split(" ");
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

    /**UNICA OPCION
     * Verifica si un número está dentro de un rango específico.
     *
     * @param max El límite superior del rango.
     * @param str La cadena que contiene el número a verificar.
     * @return true si el número está dentro del rango, false en caso contrario.
     */
    public static boolean verificarNumero(int max, String str) {
        try {
            int num = Integer.parseInt(str);
            return num >= 1 && num <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Convierte una cadena de fecha en una lista de enteros.
     *
     * @param fechaInicio La cadena de fecha en formato "dd/mm/yyyy".
     * @return Una lista de enteros que representa la fecha [día, mes, año].
     */
    public static ArrayList<Integer> listaFecha(String fechaInicio) {
        String[] R_listaString = fechaInicio.split("/");
        ArrayList<Integer> fecha = new ArrayList<>();
        for (String x : R_listaString) {
            int numero = Integer.parseInt(x);
            fecha.add(numero);
        }
        return fecha;
    }

    /**
     * Verifica si una cadena de fecha es válida.
     *
     * @param fecha La cadena de fecha a verificar.
     * @return true si la fecha es válida, false en caso contrario.
     */
    public static boolean verificarFecha(String fecha) {
        if (fecha == null) {
            return false;
        }
        String[] listaString = fecha.split("/");
        if (listaString.length == 2) {
            if (!Reserva.verificarNumero(12, listaString[0])) {
                return false;
            }
        } else if (listaString.length == 3) {
            if (!Reserva.verificarNumero(12, listaString[1])) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**LISTA DE TODOS LOS DIAS DEL MES (1,MES/AÑO/)
     * Muestra una lista de fechas en función de la opción de fecha y una fecha dada.
     *
     * @param opcFecha La opción de fecha ("1" para mostrar todos los días del mes, de lo contrario solo un día específico).
     * @param fecha La cadena de fecha en formato "dd/mm/yyyy" o "mm/yyyy".
     * @return Una lista de listas que representan las fechas en formato [día, mes, año].
     */
    public static ArrayList<ArrayList<Integer>> mostrarListaFechas(String opcFecha, String fecha) {
        ArrayList<ArrayList<Integer>> listaFechas = new ArrayList<>();
        String[] listaString = fecha.split("/");

        if (opcFecha.equals("1")) {
            int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

            int mes = Integer.parseInt(listaString[0]);
            int año = Integer.parseInt(listaString[1]);

            int cantidadDias = diasPorMes[mes - 1];
            ArrayList<Integer> fechaInicio = new ArrayList<>(Arrays.asList(1, mes, año));

            listaFechas = Reserva.mostrarDias(cantidadDias, fechaInicio);
        } else {
            int dia = Integer.parseInt(listaString[0]);
            int mes = Integer.parseInt(listaString[1]);
            int año = Integer.parseInt(listaString[2]);
            ArrayList<Integer> fechaDia = new ArrayList<>(Arrays.asList(dia, mes, año));

            listaFechas.add(fechaDia);
        }
        return listaFechas;
    }

    /**
     * Convierte una lista genérica en una lista de cadenas.
     *
     * @param lista La lista genérica a convertir.
     * @return La lista convertida de cadenas, o null si no se puede convertir.
     */
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

    /**
     * Muestra el nombre del mes correspondiente a un número de mes.
     *
     * @param fecha El número del mes (1-12).
     * @return El nombre del mes en español.
     */
    public static String mostrarMes(int fecha) {
        String[] meses = {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };
        return meses[fecha - 1];
    }
//////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////Métodos de acceso//////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////


    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public ArrayList<Idiomas> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(ArrayList<Idiomas> idiomas) {
        this.idiomas = idiomas;
    }

    public ArrayList<ArrayList<Integer>> getFechas() {
        return fechas;
    }

    public void setFechas(ArrayList<ArrayList<Integer>> fechas) {
        this.fechas = fechas;
    }

    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(String tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    public boolean getExisteSuscripcion() {
        return existeSuscripcion;
    }

    public void setExisteSuscripcion(boolean existeSuscripcion) {
        this.existeSuscripcion = existeSuscripcion;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

}


