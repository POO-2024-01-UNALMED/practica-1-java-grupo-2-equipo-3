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


	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		 
		ArrayList<String> ListaIdiomas=new ArrayList<>();
		ArrayList<String> ListaTipos=new ArrayList<>();
		ArrayList<String> ListaDestinos=new ArrayList<>();
		
		for(Idiomas idioma:Idiomas.values()) {ListaIdiomas.add(idioma.getNombre());}
		for(TiposActividad tipos:TiposActividad.values()) {ListaIdiomas.add(tipos.getNombre());}
		for(Destino destino:Destino.getDestinos()) {ListaIdiomas.add(destino.getNombre());}
		
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
				I_guia.asignarPrecio();
				
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
					    "Ver la disponibilidad de todos los guías según el destino",
					    "Ver el itinerario de un guía en específico"));

					String D_opcBusqueda = ingresarOpcion("¿Qué desea buscar?", 0, D_menu);

					ArrayList<String> D_MesDia = new ArrayList<>(Arrays.asList("Mes", "Día"));
					String D_opcFecha = ingresarOpcion("Desea buscar según:", 0, D_MesDia);

					String D_fecha = ingresarFecha(D_opcFecha);
					ArrayList<ArrayList<Integer>> D_listaFechas = Reserva.mostrarListaFechas(D_opcFecha, D_fecha);

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
					            imprimirTabla(D_listaFechas, D_opcFiltro, D_opcBusqueda, D_opcFecha, D_guia, D_destino, D_idioma);
					            D_disponibilidadOpciones.remove("Buscar la información de una fecha en específico");
					            break;
					        case "2":
					            String D_opcDestino = ingresarOpcion("Elija el destino", 0, ListaDestinos);
					            D_destino = Destino.getDestinos().get(Integer.parseInt(D_opcDestino));
					            imprimirTabla(D_listaFechas, D_opcFiltro, D_opcBusqueda, D_opcFecha, D_guia, D_destino, D_idioma);
					            D_disponibilidadOpciones.remove("Buscar la información de un destino en específico");
					            break;
					        case "3":
					            String D_indiceIdiomas = ingresarOpcion("Elija el idioma", 0, ListaIdiomas);
					            D_idioma = Idiomas.buscarIdioma(Integer.parseInt(D_indiceIdiomas));
					            imprimirTabla(D_listaFechas, D_opcFiltro, D_opcBusqueda, D_opcFecha, D_guia, D_destino, D_idioma);
					            D_disponibilidadOpciones.remove("Buscar la información de un idioma en específico");
					            break;
					        case "4":
					            D_guia = ingresarGuia();
					            imprimirTabla(D_listaFechas, D_opcFiltro, D_opcBusqueda, D_opcFecha, D_guia, D_destino, D_idioma);
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
					            D_fecha = ingresarFecha(D_opcFecha);
					            D_listaFechas = Reserva.mostrarListaFechas(D_opcFecha, D_fecha);
					            break;
					        case "Buscar la información por mes":
					            D_disponibilidadOpciones.add("Buscar la información por día");
					            D_opcFecha = "1";
					            D_fecha = ingresarFecha(D_opcFecha);
					            D_listaFechas = Reserva.mostrarListaFechas(D_opcFecha, D_fecha);
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

		entrada.close();
	}

}

