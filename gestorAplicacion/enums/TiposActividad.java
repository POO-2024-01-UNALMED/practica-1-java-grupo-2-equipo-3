package gestorAplicacion.enums;

public enum TiposActividad {
	CULTURALES("Cultural", "Baja",1),
    FAMILIARES("Familiar","Baja",2),
    ECOLOGICAS("Ecológica", "Media",3),
    EXTREMAS("Extrema", "Extrema",4),
    ACUATICAS("Acuática", "Alta",5),
    DEPORTIVAS("Deportiva", "Alta",6);

    private final String nombre;
    private final String dificultad;
    private final int posicion;

    TiposActividad(String nombre, String dificultad, int posicion) {
        this.nombre = nombre;
        this.dificultad = dificultad;
		this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDificultad() {
        return dificultad;
    }

	public int getPosicion() {
		return posicion;
	}
	
}
