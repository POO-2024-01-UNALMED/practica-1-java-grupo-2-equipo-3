package uiMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Guia;
import gestorAplicacion.manejoReserva.Actividad;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.manejoReserva.Reserva;

public class Main {

    /**
     * Imprime una tabla con la disponibilidad de guías según los criterios especificados.
     * 
     * @param listaFechas   Lista de fechas para verificar la disponibilidad.
     * @param opcFiltro     Opción de filtro para la tabla.
     * @param opcBusqueda   Opción de búsqueda para la tabla.
     * @param opcFecha      Opción de fecha para la tabla.
     * @param guia          El guía a buscar.
     * @param destino       El destino para buscar guías.
     * @param idioma        El idioma para buscar guías.
     */
    public static void imprimirTablaDisponibilidadGuias(ArrayList<ArrayList<Integer>> listaFechas, String opcFiltro, String opcBusqueda, String opcFecha, Guia guia, Destino destino, Idiomas idioma) {
		String D_lineaTabla="|---------------------------------------------------------------------------------------------------------|";
		String D_lineaTablaI=" --------------------------------------------------------------------------------------------------------- ";
		ArrayList<Object> primerTabla = Guia.mostrarDisponibilidadGuias(listaFechas.get(0),destino,idioma);
		ArrayList<String>tablaDestino=Reserva.convertirTipo(primerTabla.get(6));
		ArrayList<String>tablaIdioma=Reserva.convertirTipo(primerTabla.get(7));
		int maxSize = Math.max(tablaDestino.size(), tablaIdioma.size());
		
		//PRIMERA PARTE
		System.out.println(D_lineaTablaI);
		if (opcFecha.equals("1")||opcBusqueda.equals("4")) {
		    if (opcBusqueda.equals("1") || opcBusqueda.equals("4")) {
		        String primeraLinea1 = opcBusqueda.equals("1") ? "Mes: " + primerTabla.get(1) : "Guia: " + guia.getNombre();
		        String primeraLinea2 = opcBusqueda.equals("1") ? "Año: " + primerTabla.get(0) : "Destino: " + guia.getDestino();
		        
		        System.out.printf("|%-63s|%-41s|%n",primeraLinea1 + ", " + primeraLinea2);
		    } 
		    else if (opcBusqueda.equals("2") || opcBusqueda.equals("3")) {
		        String primeraLinea1 = opcBusqueda.equals("2") ? destino.getGuias().size()+""  : primerTabla.get(9)+"" ;
		        String segundaLinea1 = opcBusqueda.equals("2") ? ("Cantidad de actividades: " + destino.getActividades().size()) : "Total cantidad de personas: " + Grupo.cantidadClientesIdioma(listaFechas, idioma);
		        
		        System.out.printf("|%-63s|%-41s|%n","Cantidad de guías: " + primeraLinea1 + ", Mes: " + primerTabla.get(1));
		        System.out.printf("|%-63s|%-41s|%n",segundaLinea1 + ", Año: " + primerTabla.get(0));
		    }
		} else {
			System.out.printf("|%-41s|%-41s|%-21s|%n","Dia: " + primerTabla.get(2) + ", Mes: " + primerTabla.get(1) + ", Año: " + primerTabla.get(0));
		}
		
		System.out.println(D_lineaTabla);
		System.out.println(D_lineaTabla);
		//SEGUNDA PARTE
		
		if(opcFecha.equals("1")||opcBusqueda.equals("4")) {
			String primerLinea1 = opcBusqueda.equals("4") ? "" : "Guias";
		    String primerLinea2 = opcBusqueda.equals("4") ? "" : "Guias";
		    String primerLinea3 = opcBusqueda.equals("4") ? "Tipo de Actividad/" : "Actividades";
		    String primerLinea4 = opcBusqueda.equals("2") ? "Idioma" : (opcBusqueda.equals("4") ? "" : "Destino");
		    String primerLinea5 = opcBusqueda.equals("1") ? "Idioma" : "Cantidad";

		    String segundaLinea1 = opcBusqueda.equals("4") ? "Estado: " : "disponibles: ";
		    String segundaLinea2 = opcBusqueda.equals("4") ? "Actividad:" : "ocupados:";
		    String segundaLinea3 = opcBusqueda.equals("3") ? "reservadas: " : (opcBusqueda.equals("4") ? "Clasificacion: " : "con guia:");
		    String segundaLinea4 = opcBusqueda.equals("4") ? "Idioma:" : "mas usado:";
		    String segundaLinea5 = opcBusqueda.equals("1") ? "mas usado:" : "de personas:";

			System.out.printf("|%-10s|%-15s|%-15s|%-20s|%-20s|%-20s|%n","",primerLinea1,primerLinea2,primerLinea3,primerLinea4,primerLinea5);
			System.out.printf("|%-10s|%-15s|%-15s|%-20s|%-20s|%-20s|%n","Dia:",segundaLinea1,segundaLinea2,segundaLinea3,segundaLinea4,segundaLinea5);
			
		}else {
			String primerLinea1 = opcBusqueda.equals("2") ? "Total cantidad" : "Destino";
		    String primerLinea2 = opcBusqueda.equals("3") ? "Total cantidad: " : "Idioma";
		    
		    String segundaLinea1 = opcBusqueda.equals("3") ? "reservadas: " : "con guia: ";
		    String segundaLinea2 = opcBusqueda.equals("2") ? "de clientes: " : "mas usado: ";
		    String segundaLinea3 =opcBusqueda.equals("3") ? "de clientes: " : "mas usado: ";
		    
		    String terceraLinea1 = opcBusqueda.equals("2") ? primerTabla.get(8)+"" : tablaDestino.get(0);
		    String terceraLinea2 = opcBusqueda.equals("3") ? primerTabla.get(8)+"" : tablaIdioma.get(0);
		    
			System.out.printf("|%-20s|%-20s|%-20s|%-20s|%-21s|%n","Guias","Guias","Actividades ",primerLinea1 ,primerLinea2 );
			System.out.printf("|%-20s|%-20s|%-20s|%-20s|%-21s|%n","disponibles: ","ocupados: ",segundaLinea1 ,segundaLinea2,segundaLinea3);
			System.out.printf("|%-20s|%-20s|%-20s|%-20s|%-21s|%n",primerTabla.get(3),primerTabla.get(4),primerTabla.get(5),terceraLinea1,terceraLinea2);		
			
			for (int i = 1; i < maxSize; i++) {
		    	String D_listaDestino = (i < tablaDestino.size()) ? tablaDestino.get(i) : "";
		        String D_listaIdioma = (i < tablaIdioma.size()) ? tablaIdioma.get(i) : "";
		        System.out.printf("|%-20s|%-20s|%-20s|%-20s|%-21s|%n","","","",D_listaDestino,D_listaIdioma);	
		    }
	
			System.out.println(D_lineaTabla);
			System.out.println(D_lineaTabla);
			String cuartaLinea1 = opcBusqueda.equals("2") ? "Cantidad de clientes:" : "Destino:";
		    String cuartaLinea2 = opcBusqueda.equals("3") ? "Cantidad de clientes:" : "Idioma:";
		    
			System.out.printf("|%-20s|%-20s|%-20s|%-20s|%-21s|%n","Guia:","Estado:","Actividad:",cuartaLinea1,cuartaLinea2);
			System.out.println(D_lineaTabla);
		
		}
		System.out.println(D_lineaTabla);
		
		//IMPRIMIR CUERPO
		if(opcFecha.equals("1")||opcBusqueda.equals("4")) {
			for(ArrayList<Integer> fecha:listaFechas) {
				ArrayList<Object> tabla=Guia.mostrarDisponibilidadGuias(fecha,destino,idioma);
				tablaDestino=Reserva.convertirTipo(tabla.get(6));
				tablaIdioma=Reserva.convertirTipo(tabla.get(7));
				if(opcBusqueda.equals("4")){
					System.out.printf("|%-10s|%-15s|%-15s|%-20s|%-20s|%-20s|%n",tabla.get(2),tabla.get(5),tabla.get(6),tabla.get(2),tabla.get(7),tabla.get(8),tabla.get(9));
					System.out.printf("|%-10s|%-15s|%-15s|%-20s|%-20s|%-20s|%n","","","",tabla.get(10),"","");		
				}
				else {
					System.out.println(D_lineaTabla);
					maxSize = Math.max(tablaDestino.size(), tablaIdioma.size());
					for (int i = maxSize - 1; i >= 1; i--) {
				    	String D_destino = (i+1 <= tablaDestino.size()) ? tablaDestino.get(i) : "";
				        String D_idioma = (i+1 <= tablaIdioma.size()) ? tablaIdioma.get(i) : "";
				        		       
				        System.out.printf("|%-10s|%-15s|%-15s|%-20s|%-20s|%-20s|%n", "", "", "", "", D_destino, D_idioma);
				    }
					
					String primeraLinea1 = opcBusqueda.equals("2") ? tabla.get(9)+"" : tablaDestino.get(0);
					String primeraLinea2 = opcBusqueda.equals("3") ? tabla.get(9)+"" : tablaIdioma.get(0);
					
				    System.out.printf("|%-10s|%-15s|%-15s|%-20s|%-20s|%-20s|%n",tabla.get(2),tabla.get(3),tabla.get(4),tabla.get(5),primeraLinea1,primeraLinea2);
				}	
			}
		}else{
			for (Guia x : Guia.getGuias()) {
				boolean isDestinoMatch = (destino == null )|| x.getDestino().equals(destino);
				boolean isIdiomaMatch= (idioma==null)||x.getIdiomas().contains(idioma);
				
				if(isDestinoMatch &&isIdiomaMatch) {
			    	boolean D_mostrarItinerario = false;
			    	if (opcFiltro.equals("2")) {
				        if (!guia.getDiasOcupados().contains(listaFechas.get(0)) && !guia.getDiasNoDisponibles().contains(listaFechas.get(0))) { D_mostrarItinerario = true;}
				    } 
				    else if (opcFiltro.equals("3")) {
				        if (guia.getDiasOcupados().contains(listaFechas.get(0))) { D_mostrarItinerario = true;}
				    } 
				    else {D_mostrarItinerario = true;}

				    if (D_mostrarItinerario) {
				    	System.out.println(D_lineaTabla);
				        ArrayList<Object> tabla = guia.mostrarIntinerario(listaFechas.get(0));
				        
				        String primeraLinea1 = opcBusqueda.equals("2") ? tabla.get(9)+"" : tabla.get(4)+"";
						String primeraLinea2 = opcBusqueda.equals("3") ? tabla.get(9)+"" : tabla.get(8)+"";
						
				        System.out.printf("|%-20s|%-20s|%-20s|%-20s|%-21s|%n", tabla.get(3), tabla.get(5), tabla.get(6), primeraLinea1, primeraLinea2);
				    }
				}
			}
		}
		System.out.println(D_lineaTablaI);
	}

    /**
     * Permite al usuario ingresar una opción basada en una pregunta dada y una lista de opciones.
     * 
     * @param pregunta          La pregunta a mostrar al usuario.
     * @param opcionPregunta    El tipo de opción de pregunta (única, múltiple, doble).
     * @param listaString       Lista de opciones disponibles.
     * @return                  La opción seleccionada por el usuario.
     */
    public static String ingresarOpcion(String pregunta, int opcionPregunta, ArrayList<String> listaString) {
        Scanner entrada = new Scanner(System.in);
        
        List<String> opcionesPregunta = new ArrayList<>(Arrays.asList(
                "(Digite el numero de una unica opcion)",
                "(Si es mas de uno digite los numeros separados por espacio)",
                "(Elija maximo 2 opciones,Si es mas de una digite los numeros separados por espacio)"));
        
        System.out.println(pregunta + " " + opcionesPregunta.get(opcionPregunta));
        for (int i = 0; i < listaString.size(); i++) {
            System.out.println(i + 1 + ". " + listaString.get(i));
        }
        String opcionEscogida = null;
        while (true) {
            opcionEscogida = entrada.nextLine();
            
            String[] numeros = opcionEscogida.split(" ");
            boolean longitud = true;
            if (opcionPregunta == 2) {
                if (numeros.length > 2) { longitud = false;}
            }
            if(opcionPregunta==0) {
            	if(numeros.length != 1) {longitud=false;}
            }
            
            if (numeros.length == 1 && Reserva.verificarNumero(listaString.size(), opcionEscogida) && longitud) break;
            else if (Reserva.verificarLista(listaString.size(), opcionEscogida) && longitud) break;
            System.out.println("La opcion ingresada es incorrecta, por favor lea bien las instrucciones e intentelo de nuevo");
        }
        entrada.close();
        return opcionEscogida;
    }

    /**
     * Permite al usuario ingresar una fecha.
     * -Si se escogio la opcion de mes devuelve una lista de todos los dias del mes
     * -Si se escogio la opcion de dia devulve una lista con un solo elemnto que es la fecha ingresada
     * 
     * @param opcionFecha   Tipo de fecha a ingresar (mes/año o día/mes/año).
     * @return              Una lista de fechas
     */
    public static ArrayList<ArrayList<Integer>> ingresarFecha(String opcionFecha) {
        Scanner entrada = new Scanner(System.in);
        if (opcionFecha.equals("1")) {
            System.out.println("Ingrese el mes: \nDigite el numero del mes y año, sin ceros adelante y separando cada numero por '/' (mes/año)");    
        } else {
            System.out.println("Ingrese la fecha: \nDigite el numero del dia,mes y año, sin ceros adelante y separando cada numero por '/' (dia/mes/año)");
        }
        
        ArrayList<ArrayList<Integer>> listaFechas=new ArrayList<>();
        while (true) {
        	String fecha = entrada.nextLine();
            if (!Reserva.verificarFecha(fecha)) {
            	listaFechas=Reserva.mostrarListaFechas(opcionFecha, fecha);
            	break;
            }
            System.out.println("Se ingreso incorrectamente la fecha, por favor lea bien las instrucciones e intentelo de nuevo");
        }
        
        entrada.close();
        return listaFechas;
    }

    /**
     * Permite al usuario ingresar un periodo de fechas.
     * 
     * @return  Lista de fechas representando el periodo ingresado.
     */
    public static ArrayList<ArrayList<Integer>> ingresarPeriodoFechas() {
        Scanner entrada = new Scanner(System.in);
        ArrayList<ArrayList<Integer>> listaFechas = new ArrayList<>();

        while (true) {
            try {
                System.out.println("Ingrese la cantidad de dias: (Solo ingrese números enteros)");
                int cdias = entrada.nextInt();
                entrada.nextLine(); // Limpiar el buffer del scanner

                System.out.println("Ingrese la fecha de inicio: \nDigite el número del día, mes y año, sin ceros adelante y separando cada número por '/' (dia/mes/año)");
                String fechaInicio = entrada.nextLine();

                if (!Reserva.verificarFecha(fechaInicio)) {
                    ArrayList<Integer> fecha = Reserva.listaFecha(fechaInicio);
                    listaFechas = Reserva.mostrarDias(cdias, fecha);
                    break;
                }

                System.out.println("Se ingresó incorrectamente un dato, por favor lea bien las instrucciones e inténtelo de nuevo.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingresó un número no válido. Por favor, intente nuevamente.");
                entrada.nextLine(); // Limpiar el buffer del scanner en caso de error
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
                entrada.nextLine(); // Limpiar el buffer del scanner en caso de error
            }
        }
        entrada.close();
        return listaFechas;
    }

    /**
     * Permite al usuario ingresar el nombre de un guía y busca el guía en la lista de guías activas.
     * 
     * @return  El guía ingresado por el usuario.
     */
    public static Guia ingresarGuia() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Ingrese el nombre del guia: ");
        
        Guia guia = null;
        while (true) {
            String nombre = entrada.nextLine();
            guia = Guia.buscarGuia(nombre);
            if (guia != null) break;
            System.out.println("Se ingreso incorrectamente el nombre, intentelo de nuevo y asegurese de ingresar el nombre de un guia activo");
        }
        entrada.close();
        return guia;
    }

    /**
     * Permite al usuario ingresar el nombre de una actividad y el destino, y busca la actividad en la lista de actividades activas.
     * 
     * @return  La actividad ingresada por el usuario.
     */
    public static Actividad ingresarActividad() {
        Scanner entrada = new Scanner(System.in);
        Actividad actividad = null;
        while (true) {
            System.out.println("Ingrese el destino de la actividad: ");
            String destino = entrada.nextLine();
                                
            System.out.println("Ingrese el nombre de la actividad: ");
            String nombre = entrada.nextLine();
            
            actividad = Actividad.buscarActividad(nombre, destino);
            if (actividad != null) break;
            System.out.println("Se ingreso incorrectamente el nombre o el destino, intentelo de nuevo y asegurese de ingresar la informacion de una actividad activa");
        }
        entrada.close();
        return actividad;
    }
    /**
     *  Permite al usuario ingresar un número entero desde la consola.
     * 
     * @param pregunta Un mensaje que se mostrará al usuario para solicitar el número entero.
     * @return El número entero ingresado por el usuario.
     */
    public static int ingresarEntero(String pregunta) {
		Scanner entrada = new Scanner(System.in);
		System.out.println(pregunta);
		int numero=0;
		while(true) {
			String numeroString=entrada.nextLine();
			boolean vrfNumero=Reserva.verificarNumero(0,numeroString );
			if(vrfNumero) {
				numero=Integer.parseInt(numeroString);
				break;
			}
			System.out.println("Se ingreso incorrectamente el dato, intentelo de nuevo y asegurese de ingresar un número entero");
		}
		entrada.close();
        return numero;
	}
    /**
     * Pregunta al usuario si desea cerrar el ciclo administrativo.
     * 
     * @return  {@code true} si desea ir al menú, {@code false} si desea volver al inicio.
     */
    public static boolean terminarCicloAdmin() {
        ArrayList<String> cerrarCiclo = new ArrayList<>(Arrays.asList("Ir al menu", "Volver al inicio"));
        String opcionCerrarCiclo = ingresarOpcion("¿Que desea hacer?", 0, cerrarCiclo);
        return !opcionCerrarCiclo.equals("2");
    }

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		//LISTAS DE NOMBRES DE LAS CONSTANTES 
		ArrayList<String> ListaIdiomas=Idiomas.listaNombres();
		ArrayList<String> ListaTipos=TiposActividad.listaNombres();
		ArrayList<String> ListaDestinos=Destino.listaNombres();
		
		//INICIO FUNCIONALIDADES
		ArrayList<String> menuPrincipal=new ArrayList<>(Arrays.asList(
				"Reservar un plan de actividades turisticas",
				"Reservar un hospedaje",
				"Planear tu viaje",
				"Modificar reserva",
				"Ver opciones de administrador","Salir"));
		
		boolean terminarCicloPrincipal=true;
		while(terminarCicloPrincipal) {
			
			String opcionMenuPrincipal=ingresarOpcion("¿Que desea hacer?",0,menuPrincipal);
			
			switch(opcionMenuPrincipal) {
			case "1"://FUNCIONALIDAD: Reservar un plan de actividades turisticas
			break;
			
			case"2"://FUNCIONALIDAD: Reservar un hospedaje
			break;
			
			case"3"://FUNCIONALIDAD: Planear tu viaje
			break;
			
			case"4"://FUNCIONALIDAD: Modificar reserva
			break;
			
			case"5"://FUNCIONALIDAD: Ver opciones de administrador
				boolean terminarCicloAdmin=true;
				while(terminarCicloAdmin) {
					ArrayList<String> opcionesCiclo = new ArrayList<>(Arrays.asList(
				            "Ingresar guia","Retirar guia","Ver disponibilidad guias",
				            "Ingresar actividad","Retirar actividad","Volver al inicio"));
					String opcionCicloEscogida=ingresarOpcion("¿Que desea hacer?",0,opcionesCiclo);
					
					switch(opcionCicloEscogida) {
					case "1":
						// 1.OPCION INGRESAR GUIA=I
						System.out.println("Ingrese el nombre del guia: ");
						String I_nombre = entrada.nextLine();

						Guia I_guia = new Guia(I_nombre);
						
						String I_idiomas=ingresarOpcion("¿Que idiomas habla?",1,ListaIdiomas);
						I_guia.ingresarIdiomas(I_idiomas);
						
						String I_tipoActividades =ingresarOpcion("¿Para cuales actividades esta capacitado?",1,ListaTipos);
						I_guia.ingresarTipoActividades(I_tipoActividades);
						

						ArrayList<Destino> I_lista = Destino.elegirDestinoGuia(I_guia);
						
						if (I_lista.size() != 1) {
							ArrayList<String> I_ListaDestinos=new ArrayList<>();
							for(Destino destino:I_lista) {I_ListaDestinos.add(destino.getNombre());}
							
							String I_destinoFinal=ingresarOpcion("¿Cual destino prefiere?",0,I_ListaDestinos);
							Destino.ingresarGuia(I_guia,I_lista,Integer.parseInt(I_destinoFinal));	
						}
						
						I_guia.ingresarGuia();
						I_guia.asignarParametros();
						
						System.out.println("El guia se ha ingresado correctamente\n_______________Resumen_______________\n"+I_guia);
						terminarCicloAdmin=terminarCicloAdmin();
					break;
					
					case"2":
						// 2.OPCION RETIRAR GUIA=R
						ArrayList<String> R_menu = new ArrayList<>(Arrays.asList(
					            "Despedir guia","Dar de baja a un guia por un tiempo"));
						String R_opc1 =ingresarOpcion("¿Que desea hacer?",0,R_menu);
						
						Guia R_guia=ingresarGuia();
						if(R_opc1.equals("1")) {
							Guia.retirarGuia(R_guia);
						}
						else {
							ArrayList<ArrayList<Integer>> R_listaFechas=ingresarPeriodoFechas();
							
							Guia.retirarGuia(R_guia,R_listaFechas);
							System.out.println("Se ha retirado correctamente a "+R_guia.getNombre()+" en los siguientes dias: "+ R_listaFechas);
						}	
						terminarCicloAdmin=terminarCicloAdmin();
					break;
					case"3":
						//3.OPCION VER DISPONIBILIDAD GUIAS=D
						ArrayList<String> D_menu = new ArrayList<>(Arrays.asList(
							    "Ver la disponibilidad de todos los guías según la fecha",
							    "Ver la disponibilidad de todos los guías según el destino",
							    "Ver la disponibilidad de todos los guías según el idioma",
							    "Ver el itinerario de un guía en específico"));

							String D_opcBusqueda = ingresarOpcion("¿Qué desea buscar?", 0, D_menu);

							ArrayList<String> D_MesDia = new ArrayList<>(Arrays.asList("Mes", "Día"));
							String D_opcFecha = ingresarOpcion("Desea buscar según:", 0, D_MesDia);

							ArrayList<ArrayList<Integer>> D_listaFechas =ingresarFecha(D_opcFecha);

							ArrayList<String> D_filtro = new ArrayList<>(Arrays.asList(
							    "Disponibilidad de todos los guías", "Solo los guías disponibles", "Solo los guías ocupados"));

							String D_opcFiltro = null;
							if (!D_opcBusqueda.equals("4") && !D_opcFecha.equals("1")) {
							    D_opcFiltro = ingresarOpcion("¿Qué desea buscar?", 0, D_filtro);
							}

							ArrayList<String> D_disponibilidadOpciones = new ArrayList<>(Arrays.asList(
							    "Buscar la información por día", "Buscar la información por mes", "Buscar la información de un destino en específico",
							    "Buscar la información de un idioma en específico", "Ver el itinerario de un guía en específico",
							    "Buscar la información de una fecha en específico", "Volver al inicio"));

							boolean D_romperCiclo = true;
							Destino D_destino = null;
							Idiomas D_idioma = null;
							Guia D_guia = null;

							while (D_romperCiclo) {
							    switch (D_opcBusqueda) {
							        case "1":
							            imprimirTablaDisponibilidadGuias(D_listaFechas, D_opcFiltro, D_opcBusqueda, D_opcFecha, D_guia, D_destino, D_idioma);
							            D_disponibilidadOpciones.remove("Buscar la información de una fecha en específico");
							            break;
							        case "2":
							            String D_opcDestino = ingresarOpcion("Elija el destino", 0, ListaDestinos);
							            D_destino = Destino.getDestinos().get(Integer.parseInt(D_opcDestino));
							            imprimirTablaDisponibilidadGuias(D_listaFechas, D_opcFiltro, D_opcBusqueda, D_opcFecha, D_guia, D_destino, D_idioma);
							            D_disponibilidadOpciones.remove("Buscar la información de un destino en específico");
							            break;
							        case "3":
							            String D_indiceIdiomas = ingresarOpcion("Elija el idioma", 0, ListaIdiomas);
							            D_idioma = Idiomas.buscarIdioma(Integer.parseInt(D_indiceIdiomas));
							            imprimirTablaDisponibilidadGuias(D_listaFechas, D_opcFiltro, D_opcBusqueda, D_opcFecha, D_guia, D_destino, D_idioma);
							            D_disponibilidadOpciones.remove("Buscar la información de un idioma en específico");
							            break;
							        case "4":
							            D_guia = ingresarGuia();
							            imprimirTablaDisponibilidadGuias(D_listaFechas, D_opcFiltro, D_opcBusqueda, D_opcFecha, D_guia, D_destino, D_idioma);
							            D_disponibilidadOpciones.remove("Ver el itinerario de un guía en específico");
							            break;
							    }

							    if (D_opcFecha.equals("1")) {
							        D_disponibilidadOpciones.remove("Buscar la información por mes");
							    } else {
							        D_disponibilidadOpciones.remove("Buscar la información por día");
							    }

							    String D_vrfCiclo = ingresarOpcion("¿Qué desea buscar?", 0, D_disponibilidadOpciones);
							    String D_opcionCiclo = D_disponibilidadOpciones.get(Integer.parseInt(D_vrfCiclo) - 1);

							    switch (D_opcionCiclo) {
							        case "Buscar la información por día":
							            D_disponibilidadOpciones.add("Buscar la información por mes");
							            D_opcFecha = "2";
							            D_listaFechas =ingresarFecha(D_opcFecha);
							            break;
							        case "Buscar la información por mes":
							            D_disponibilidadOpciones.add("Buscar la información por día");
							            D_opcFecha = "1";
							            D_listaFechas =ingresarFecha(D_opcFecha);
							            break;
							        case "Buscar la información de un destino en específico":
							            D_opcBusqueda = "2";
							            D_destino = null;
							            break;
							        case "Buscar la información de un idioma en específico":
							            D_opcBusqueda = "3";
							            D_idioma = null;
							            break;
							        case "Ver el itinerario de un guía en específico":
							            D_opcBusqueda = "4";
							            break;
							        case "Buscar la información de una fecha en específico":
							            D_opcBusqueda = "1";
							            break;
							        case "Volver al inicio":
							            D_romperCiclo = false;
							            break;
							    }

							    String D_filtroLinea = D_opcBusqueda.equals("4") ? "" : (D_opcFiltro.equals("1") ? "de todos los guías," : "de " + D_filtro.get(Integer.parseInt(D_opcFiltro) - 1).toLowerCase() + ",");
							    String D_destinoLinea = D_opcBusqueda.equals("4") ? "" : (D_destino == null ? "" : "del destino: " + D_destino + ",");
							    String D_idiomaLinea = D_opcBusqueda.equals("4") ? "" : (D_idioma == null ? "" : "con el idioma: " + D_idioma + ",");
							    String D_fechaLinea = D_listaFechas.size() > 1 ? "en el mes: " + Reserva.mostrarMes(D_listaFechas.get(0).get(1)) : "en el día: " + D_listaFechas.get(0).get(1);
							    String D_texto = (D_filtroLinea + D_destinoLinea + D_idiomaLinea + D_fechaLinea);

							    System.out.println("Actualmente va a " + D_opcionCiclo + " " + D_texto);
							    ArrayList<String> D_cerrarCiclo = new ArrayList<>(Arrays.asList("Continuar", "Volver al inicio"));
							    String D_cierre = ingresarOpcion("¿Desea continuar o restaurar los filtros?", 0, D_cerrarCiclo);

							    if (D_cierre.equals("2")) { D_romperCiclo = false; }
							}
					break;
					case"4":
						//4.OPCION INGRESAR ACTIVIDAD=A
						System.out.println("Ingrese el nombre de la actividad: ");
						String A_nombre = entrada.nextLine();
						
						String A_opcDestino=ingresarOpcion("¿Donde esta ubicada?",0,ListaDestinos);
						Destino A_destino=Destino.getDestinos().get(Integer.parseInt(A_opcDestino)-1);
						
						Actividad A_actividad = new Actividad(A_nombre,A_destino);
						
						String A_tipoActividades=ingresarOpcion("¿De que tipo es la actividad?",2,ListaTipos);
						A_actividad.ingresarTipoActividades(A_tipoActividades);
						A_actividad.ingresarGuia();
						A_actividad.asignarParametros();
						
						System.out.println("La actividad se ha ingresado correctamente\n_______________Resumen_______________\n"+A_actividad);	
						terminarCicloAdmin=terminarCicloAdmin();
					break;
					case"5":
						//5.OPCION CANCELAR ACTIVIDAD=C
						ArrayList<String> C_menu = new ArrayList<>(Arrays.asList(
					            "Eliminar actividad","Suspender actividad por un tiempo"));
						String C_opcMenu =ingresarOpcion("¿Que desea hacer?",0,C_menu);
						
						Actividad C_actividad=ingresarActividad();
						
						if(C_opcMenu.equals("1")) {
							Actividad.retirarActividad(C_actividad);
							System.out.println("Se ha cancelado la actividad: "+C_actividad.getNombre()+", correctamente");
						}else {
							ArrayList<ArrayList<Integer>> C_listaFechas=ingresarPeriodoFechas();
							Grupo.retirarActividad(C_actividad, C_listaFechas);
							System.out.println("Se ha suspendido correctamente la actividad: "+C_actividad.getNombre()+", en los siguientes dias: "+C_listaFechas);
						}
						terminarCicloAdmin=terminarCicloAdmin();
					break;
					case"6":
						terminarCicloAdmin=false;
					break;
					
					}
				}
			break;
			case "6"://Salir
				System.out.println("Vuelva pronto :)");
				terminarCicloPrincipal=false;
			break;
			}
			
		}
		entrada.close();
	}

	public static void ingresarOpcion(String string, int i, String string2, String string3, String string4) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'ingresarOpcion'");
	}

}

