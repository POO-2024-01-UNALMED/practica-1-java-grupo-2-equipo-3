package gestorAplicacion.enums;

import java.util.ArrayList;


public enum Idiomas {
	INGLES("Inglés", 10000),
    PORTUGUES("Portugués", 10000),
    ESPANOL("Español", 5000),
    FRANCES("Francés", 15000),
    ITALIANO("Italiano", 15000);

    private final String nombre;
    private final double precio;

    Idiomas(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
    /**
     * Devuelve la lista de los nombres de todos los idiomas existentes
     * 
     * @return Un ArrayList<String> con los nombres 
     */
    public static ArrayList<String> listaNombres(){
		 ArrayList<String> ListaTipos=new ArrayList<>();
		 for(Idiomas idioma:Idiomas.values()) {ListaTipos.add(idioma.getNombre());}
		 return ListaTipos;
	 }
    /**
     * Devuelve el objeto idioma con el nombre indicado
     * 
     * @param nombre, un string del nombre a buscar
     * @return El objeto encontrado, o null si no se encuentra.
     */
    public static Idiomas buscarNombre(String nombre) {
		for(Idiomas idioma:Idiomas.values()) {
			if(nombre==idioma.getNombre()) {
				return idioma;
			}
		}
		return null;
	}
	public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

}
