package gestorAplicacion.enums;

import java.util.ArrayList;


public enum TiposActividad {
	CULTURALES("Cultural", "Baja"),
    FAMILIARES("Familiar","Baja"),
    ECOLOGICAS("Ecológica", "Media"),
    EXTREMAS("Extrema", "Extrema"),
    ACUATICAS("Acuática", "Alta"),
    DEPORTIVAS("Deportiva", "Alta"),
    HOSPEDAJE("Hospedaje", "Baja"),
    RESTAURANTE("Restaurante", "Baja"),;

    

    private final String nombre;
    private final String dificultad;

    TiposActividad(String nombre, String dificultad) {
        this.nombre = nombre;
        this.dificultad = dificultad;
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
    * Devuelve el objeto tipo con el nombre indicado
    * 
    * @param nombre, un string del nombre a buscar
    * @return El objeto encontrado, o null si no se encuentra.
    */
   public static TiposActividad buscarNombre(String nombre) {
		for(TiposActividad tipo:TiposActividad.values()) {
			if(nombre==tipo.getNombre()) {
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

	
}
