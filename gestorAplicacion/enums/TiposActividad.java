package gestorAplicacion.enums;

import java.util.ArrayList;

import clases.TiposActividad;

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
    
    /**
     * Devuelve la lista de los nombres de todos los tipos existentes
     * 
     * @return Un ArrayList<String> con los nombres 
     */
    public static ArrayList<String> listaNombres(){
		 ArrayList<String> ListaTipos=new ArrayList<>();
		 for(TiposActividad tipos:TiposActividad.values()) {ListaTipos.add(tipos.getNombre());}
		 return ListaTipos;
	 }
    /**
     * Devuelve el tipo que esta en la posicion indicada
     * 
     * @param posicion El numero de la posicion a buscar.
     * @return el objeto TiposActividad.
     */
    public static TiposActividad buscarTipo(int posicion) {
		for(TiposActividad tipo:TiposActividad.values()) {
			if(posicion==tipo.getPosicion()) {
				return tipo;
			}
		}
		return null;
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
