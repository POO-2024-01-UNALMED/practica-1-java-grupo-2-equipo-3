package gestorAplicacion.enums;

import java.util.ArrayList;

public enum Idiomas {
	INGLES("Inglés", 10000,1),
    PORTUGUES("Portugués", 10000,2),
    ESPANOL("Español", 5000,3),
    FRANCES("Francés", 15000,4),
    ITALIANO("Italiano", 15000,5);

    private final String nombre;
    private final double precio;
    private final int posicion;

    Idiomas(String nombre, double precio, int posicion) {
        this.nombre = nombre;
        this.precio = precio;
		this.posicion = posicion;
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
     * Devuelve el idioma que esta en la posicion indicada
     * 
     * @param posicion El numero de la posicion a buscar.
     * @return el objeto idioma.
     */
	public static Idiomas buscarIdioma(int posicion) {
		for(Idiomas idioma:Idiomas.values()) {
			if(posicion==idioma.getPosicion()) {
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

	public int getPosicion() {
		return posicion;
	}
}
