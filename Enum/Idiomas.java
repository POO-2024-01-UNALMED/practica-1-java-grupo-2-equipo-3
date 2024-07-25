package Enum;

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
    
    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

	public int getPosicion() {
		return posicion;
	}
	public static Idiomas buscarIdioma(int posicion) {
		for(Idiomas idioma:Idiomas.values()) {
			if(posicion==idioma.getPosicion()) {
				return idioma;
			}
		}
		return null;
	}
}
