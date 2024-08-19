package uiMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import gestorAplicacion.actividades.Plan;
import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Cliente;
import gestorAplicacion.gestionHum.Guia;
import gestorAplicacion.hospedaje.Hotel;
import gestorAplicacion.manejoReserva.Actividad;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.manejoReserva.Reserva;
import gestorAplicacion.manejoReserva.Suscripcion;

public class Main {
//////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////METODOS IMPORTANTES DE LA CLASE MAIN/////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////  
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
                "(Elija maximo 2 opciones,Si es mas de una digite los numeros separados por espacio)" ,
				"Porfavor seleccione las habitaciones que desea de cada tipo, de la siguiente forma [Num sencilla, Num Doble, Num suites] \n" + "Tenga presente que no pude asiganr un numero Total de habitaciones mayor al numero de adultos en su reserva, "));
        
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
            
            if ( numeros.length == 1 && Reserva.verificarNumero(listaString.size(), opcionEscogida) && longitud) break;

			if (opcionPregunta == 3 ) break;
            else if (Reserva.verificarLista(listaString.size(), opcionEscogida) && longitud) break;
            System.out.println("La opcion ingresada es incorrecta, por favor lea bien las instrucciones e intentelo de nuevo");

        }
        entrada.close();
        return opcionEscogida;
    }

	/**
	 * Permite al usuario ingresar una opción basada en una pregunta dada sobre la seleccion de actividades y una lista de opciones.
	 *
	 * @param pregunta          La pregunta a mostrar al usuario.
	 * @param diasReserva       La cantidad de dias de la reserva.
	 * @param listaString       Lista de opciones disponibles.
	 *
	 * @return                  La lista de los nombres de las actividades seleccionadas por el usuario.
	 */
	public static ArrayList<String> ingresarOpcionActividad(String pregunta, int diasReserva, ArrayList<String> listaString) {
		Scanner entrada = new Scanner(System.in);
		ArrayList<String> nombresEscogidos = new ArrayList<>();

		String opcionesPregunta = "Su reserva es de "+diasReserva+" dias, por favor seleccione POR LO MENOS \n" +
						"la misma cantidad de actividades que le interesaría realizar en su estancia en el destino \n" +
						"con el fin de tener más opciones a escoger, ingrese el número de las actividades que le interesen separadas por un espacio." ;

		System.out.println(pregunta + "\n" + opcionesPregunta);
		for (int i = 0; i < listaString.size(); i++) {
			System.out.println(i + 1 + ". " + listaString.get(i));
		}
		String opcionEscogida;
		while (true) {
			opcionEscogida = entrada.nextLine();

			String[] numeros = opcionEscogida.split(" ");
			for (String index : numeros) {
				nombresEscogidos.add(listaString.get(Integer.parseInt(index) - 1));
			}
			boolean longitud = true;
				if (numeros.length < diasReserva) { longitud = false;}

			if (numeros.length == 1 && Reserva.verificarNumero(listaString.size(), opcionEscogida) && longitud) {
				break;
			}
			else if (Reserva.verificarLista(listaString.size(), opcionEscogida) && longitud){
				break;
				}
			System.out.println("La opcion ingresada es incorrecta, por favor lea bien las instrucciones e intentelo de nuevo");
		}
		entrada.close();
		return nombresEscogidos;
	}


	/**
     * Imprime los valores [Fechas, idiomas, Clientes] de la reserva que se ingresa.
     * 
     * @param Reserva:   Reserva la cual se desea imprimir sus atributos.
     * @return              String con los valores de la reserva.
     */
	public static String imprimirReserva(Reserva reserva) {

		ArrayList<ArrayList<Integer>> fechas = reserva.getFechas();
        ArrayList<Idiomas> idiomas = reserva.getIdiomas();
        ArrayList<Cliente> clientes = reserva.getClientes();  
        
        String menu = "Su reserva es la siguiente: \n" + 
        "Fechas de la reserva:" + fechas + "\n" +
        "Idiomas:" + idiomas + "\n" +
        "Clientes:" + clientes + "\n";
        
        return menu;
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
	 * Verifica si la edad del usuario ingresada es mayor a 18 años
	 *
	 * @return int, la edad del usuario
	 * */
	public static int IngresoEdadTitular() {
		Scanner entrada = new Scanner(System.in);
		int edad;
		do {
			try {
				System.out.println("Ingrese la edad del titular de la reserva: ");
				edad = entrada.nextInt();
				entrada.nextLine(); //Limpiar el buffer
				if (edad < 18) {
					System.out.println("El titular de la reserva debe ser mayor de edad");
				}
			} catch (Exception e) {
				System.out.println("###ERROR###");
				System.out.println("Ingrese un número entero para la edad.");
				System.out.println("Por favor, intente de nuevo.");
				System.out.println("//////////////////////////////////////////" + "\n");
				entrada.next();
				edad = -1;
			}
		}
		while (edad < 18);
		entrada.close();
		return edad;
	}

	/**
	 * Verifica si el usuario ingreso un texto valido.
	 *
	 * @param enunciado, la pregunta que se le quiere hacer al usuario
	 * @return String, el texto ingresado por el usuario
	 * */
	public static String ingresarString (String enunciado) {
		Scanner entrada = new Scanner(System.in);
		String texto;
		do {
			try {
				System.out.println(enunciado);
				texto = entrada.nextLine();
				if (texto.isEmpty()) {
					System.out.println("El texto ingresado es muy corto.");
				}
				//Verificar si el texto ingresado es un número
				if (texto.matches("[0-9]+")) {
					System.out.println("El texto ingresado no puede ser un número.");
					texto = "";
				}
				//Verificar si el texto ingresado es un número con decimales
				if (texto.matches("[0-9]+.[0-9]+")) {
					System.out.println("El texto ingresado no puede ser un número con decimales.");
					texto = "";
				}
				//Verificar si el texto ingresado es una combinación de números y letras, pero que no sea solo letras

				if (texto.matches("[a-zA-Z0-9]+") && !texto.matches("[a-zA-Z]+")) {
					System.out.println("El texto ingresado no puede ser una combinación de números y letras.");
					texto = "";
				}
				//Verificar si el texto ingresado es una combinación de números, letras y caracteres especiales, pero que no sea solo letras
				if (texto.matches("[a-zA-Z0-9!@#$%^&*()_+]+") && !texto.matches("[a-zA-Z]+")) {
					System.out.println("El texto ingresado no puede ser una combinación de números, letras y caracteres especiales.");
					texto = "";
				}

			} catch (Exception e) {
				System.out.println("###ERROR###");
				System.out.println("Ingrese un texto válido.");
				System.out.println("Por favor, intente de nuevo.");
				System.out.println("//////////////////////////////////////////" + "\n");
				entrada.next();
				texto= "";
			}
		}
		while (texto.isEmpty());
		entrada.close();
		return texto;
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
     * Muestra la lista de idiomas y devuelve el objeto seleccionado.
     *
     * @return Idioma seleccionado.
     */
    public static Idiomas ingresarIdioma() {
    	Scanner entrada = new Scanner(System.in);
        ArrayList<String> ListaIdiomas = Idiomas.listaNombres();
        int opcIdiomas = Integer.parseInt(ingresarOpcion("Elija el idioma: ", 0, ListaIdiomas));
        Idiomas idioma = Idiomas.buscarNombre(ListaIdiomas.get(opcIdiomas - 1));
        entrada.close();
        return idioma;
    }

    /**
     * Muestra la lista de tipos de actividad y devuelve el objeto seleccionado.
     *
     * @return Tipo de actividad seleccionado.
     */
    public static TiposActividad ingresarTipoActividad() {
    	Scanner entrada = new Scanner(System.in);
        ArrayList<String> ListaTipos = TiposActividad.listaNombres();
        int opcTipo = Integer.parseInt(ingresarOpcion("Elija el tipo de actividad: ", 0, ListaTipos));
        TiposActividad tipo = TiposActividad.buscarNombre(ListaTipos.get(opcTipo - 1));
        entrada.close();
        return tipo;
    }

    /**
     * Muestra la lista de destinos y devuelve el objeto seleccionado.
     *
     * @return Destino seleccionado.
     */
    public static Destino ingresarDestino() {
    	Scanner entrada = new Scanner(System.in);
        ArrayList<String> ListaDestinos = Destino.listaNombres();
        int opcDestinos = Integer.parseInt(ingresarOpcion("Elija el destino: ", 0, ListaDestinos));
        Destino destino = Destino.buscarNombre(ListaDestinos.get(opcDestinos - 1));
        entrada.close();
        return destino;
    }
    /**
     * Muestra los tipos de clasificaciones segun la edad y devuelve la opcion escogida
     *
     * @return Un int con el numero de la opcion escogida .
     */
    public static int ingresarClasificacion() {
    	Scanner entrada = new Scanner(System.in);
    	ArrayList<String> opcionesClasificacion=new ArrayList<>(Arrays.asList(
				"Menores de 7 años","Entre 7 y 15 años","Entre 15 y 18","Mayores de 18 años"));
		String clasificacion=ingresarOpcion("Elija una clasificación(tenga en cuenta la edad de la menor persona del grupo)",0,opcionesClasificacion);
		entrada.close();
		return Integer.parseInt(clasificacion);
    }
    /**
     *  Imprime una lista de fechas 
     * 
     * @param pregunta la pregunta que se le quiere hacer al usuario
     * @param  listaFechas<Integer>>, una lista de fechas
     */
    private static void imprimirListaFechas(String pregunta, ArrayList<ArrayList<Integer>> listaFechas) {
    	System.out.println(pregunta);
    	for(ArrayList<Integer> fecha:listaFechas) {
    		ArrayList<String> fechaString=new ArrayList<>();
    		fechaString.add(Integer.toString(fecha.get(0)));
    		fechaString.add(Reserva.mostrarMes(fecha.get(1)));
    		fechaString.add(Integer.toString(fecha.get(2)));
    		System.out.println(fechaString);
    	}
    	
    }


    /**
     * Pregunta al usuario si desea cerrar el ciclo de la funcionalidad.
     * 
     * @return  {@code true} si desea ir al menú, {@code false} si desea volver al inicio.
     */
    public static boolean terminarCicloFuncionalidad() {
        ArrayList<String> cerrarCiclo = new ArrayList<>(Arrays.asList("Ir al menu", "Volver al inicio"));
        String opcionCerrarCiclo = ingresarOpcion("¿Que desea hacer?", 0, cerrarCiclo);
        return !opcionCerrarCiclo.equals("2");
    }

	public static Cliente ingresarCliente(){

		String nombre = ingresarString("Ingrese el nombre del nuevo cliente: ");
		Integer edad = ingresarEntero("Ingrese la edad del nuevo cliente: ");
		Cliente cliente = new Cliente(nombre, edad);
		return cliente;


	}







//////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////METODO MAIN////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////    
	
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
				boolean terminarReservaActividades = true;
				while(terminarReservaActividades) {
					ArrayList<String> opcionesCiclo = new ArrayList<>(Arrays.asList(
							"Realizar una nueva reserva",
							"Buscar reserva existente para agregar las actividades"));
					String opcionCicloEscogida = ingresarOpcion("¿Que desea hacer?",0,opcionesCiclo);

					switch (opcionCicloEscogida) {
                        case "1":
                            // 1.OPCION REALIZAR UNA NUEVA RESERVA
                            System.out.println("Cuáles son los días en los cuales desea realizar la reserva?");
                            ArrayList<ArrayList<Integer>> listaFechas = ingresarPeriodoFechas();

                            int edad = IngresoEdadTitular();
							String nombre = ingresarString("Ingrese el nombre del titular de la reserva: ");
                            //Verificar si el cliente tiene una suscripcion activa

                            Cliente titular = Suscripcion.verificarSuscripcion(nombre, edad, listaFechas);
                            if (titular == null) {
                                ArrayList<String> opcionesClienteNoExiste = new ArrayList<>(Arrays.asList(
                                        "Sí", "No"));
                                String opcionCicloEscogida = ingresarOpcion("No cuenta con una suscripción con nosotros," +
                                        "¿desea comprar una para recibir descuentos impresionantes para su reserva?", 0, opcionesClienteNoExiste);
                                switch (opcionCicloEscogida) {
                                    case "1":
                                        System.out.println("Ingrese el tipo de suscripción que desea adquirir: ");
                                        String tipo = ingresarOpcion("¿Qué tipo de suscripción desea adquirir?", 0, ListaTiposSuscripcion);
                                        System.out.println("Ingrese la fecha de vencimiento de la suscripción: ");
                                        ArrayList<Integer> vencimiento = ingresarFecha("2");
                                        System.out.println("Ingrese la capacidad de la suscripción: ");
                                        int capacidad = entrada.nextInt();
                                        System.out.println("Ingrese el precio de la suscripción: ");
                                        double precio = entrada.nextDouble();
                                        Suscripcion nuevaSuscripcion = new Suscripcion(tipo, vencimiento, capacidad, precio);
                                        titular.setSuscripcion(nuevaSuscripcion);
                                        System.out.println("La suscripción se ha ingresado correctamente\n_______________Resumen_______________\n" + nuevaSuscripcion);
                                        break;

                                    titular = new Cliente(nombre, edad);
                                }
                                Reserva nuevaReserva = new Reserva(cliente, listaFechas, listaActividades);
                                nuevaReserva.asignarPrecioTotal();
                                nuevaReserva.ingresarReserva();
                                System.out.println("La reserva se ha realizado correctamente\n_______________Resumen____________ */");
                                terminarReservaActividades = terminarCicloAdmin();
                                break;
                            }
                    }
					//Parte actividades
					ArrayList<String> tiposDePlan = new ArrayList<>(Arrays.asList(
							"Plan personalizado\n" +
									"(Se escogen las actividades desde 0 de manera manual)",
							"Paquete turistico\n" +
									"(Se escoge un plan turistico predefinido, con actividades generales ya establecidas)"));
					String tipoPlan = ingresarOpcion("¿Qué desea escoger?",0,tiposDePlan);
					Plan planCreado = null;
					while (planCreado == null) {
						switch (tipoPlan) {
							case "1":
								//Plan personalizado
								String tipoEscogido = Plan.asignarTipo(Integer.parseInt(tipoPlan));
								ArrayList<Actividad> actividadesDisponibles = reservaCreada.escogerPlan(tipoEscogido);
								planCreado = reservaCreada.plan;
								ArrayList<String> opcionesActividades = Plan.mostrarNombreActividad(actividadesDisponibles);
								ArrayList<String> nombresActividadesEscogidas = ingresarOpcionActividad("Elija las actividades que desea realizar", reservaCreada.getFechas().size(), opcionesActividades);
								planCreado.escogerActividadesIniciales(actividadesDisponibles, nombresActividadesEscogidas);

								//Método de verificación de actividades
								

								break;
							case "2":
								//Paquete turistico
								ArrayList<String> paquetes = new ArrayList<>(Arrays.asList(
										"Paquete turistico 1",
										"Paquete turistico 2",
										"Paquete turistico 3"));
								String paquete = ingresarOpcion("¿Qué paquete turistico desea escoger?", 0, paquetes);
								planCreado = new Plan(paquete);
								break;
					}
					}
				}
				}
			
			break;
			
			case"3"://FUNCIONALIDAD: Planear tu viaje
				boolean terminarCicloPlan=true;
				while(terminarCicloPlan) {
					Reserva reservaFicticia=new Reserva();
					
					//ESCOGER DESTINO=D
					String D_opcionMenuDestino=ingresarOpcion("¿Que desea hacer?",0,new ArrayList<>(Arrays.asList("Ingresar destino","Buscar las mejores opciones de destinos")));
					
					Destino D_destino=null;
					switch(D_opcionMenuDestino) {
					case "1"://Ingresar destino
						String D_opcDestino=ingresarOpcion("Elija el destino",0,ListaDestinos);
						D_destino=Destino.buscarNombre(ListaDestinos.get(Integer.parseInt(D_opcDestino)));
						reservaFicticia.setDestino(D_destino);
					break;
					
					case "2"://Escoger destino
						ArrayList<Object> D_filtros=escogerOpcionBusqueda("Destino",reservaFicticia);
						
						boolean terminarCicloEscogerDestino=true;
						while(terminarCicloEscogerDestino) {
							imprimirTablaPlanearDestino((String)D_filtros.get(8),(int)D_filtros.get(1),(TiposActividad)D_filtros.get(2),Reserva.convertirListaFechas(D_filtros.get(3)),(Idiomas)D_filtros.get(4));
							D_filtros=elegirFiltros("Destino",(String)D_filtros.get(8),(int)D_filtros.get(1),(TiposActividad)D_filtros.get(2),Reserva.convertirListaFechas(D_filtros.get(3)),(Idiomas)D_filtros.get(4),(Destino)D_filtros.get(5));
							
							//Verificar si se termina el ciclo
							if((Boolean)D_filtros.get(6)==true) {
								reservaFicticia.setDestino((Destino)D_filtros.get(5));//Escoger destino final
								terminarCicloEscogerDestino=false;
							}
							//Verificar si se borraron los filtros
							if((Boolean)D_filtros.get(0)==true) {
								D_filtros=escogerOpcionBusqueda("Destino",reservaFicticia);
							}		
						}
					break;
					}
					//ESCOGER FECHA=F
					String F_opcionMenuFecha=ingresarOpcion("¿Que desea hacer?",0,new ArrayList<>(Arrays.asList("Ingresar fecha","Buscar las mejores fechas para viajar")));
					
					ArrayList<ArrayList<Integer>> F_fecha=null;
					switch(F_opcionMenuFecha) {
					case "1"://Ingresar fecha
						F_fecha=ingresarPeriodoFechas();
						reservaFicticia.setFechas(F_fecha);
					break;
					
					case "2"://Escoger fecha
						ArrayList<Object> F_filtros=escogerOpcionBusqueda("Fecha",reservaFicticia);
						
						boolean terminarCicloEscogerFecha=true;
						while(terminarCicloEscogerFecha) {
							imprimirTablaPlanearFecha((String)F_filtros.get(8),(int)F_filtros.get(1),(TiposActividad)F_filtros.get(2),Reserva.convertirListaFechas(F_filtros.get(3)),(Idiomas)F_filtros.get(4));
							F_filtros=elegirFiltros("Fecha",(String)F_filtros.get(8),(int)F_filtros.get(1),(TiposActividad)F_filtros.get(2),Reserva.convertirListaFechas(F_filtros.get(3)),(Idiomas)F_filtros.get(4),(Destino)F_filtros.get(5));
							
							//Verificar si se termina el ciclo
							if((Boolean)F_filtros.get(6)==true) {
								F_fecha=Reserva.convertirListaFechas(F_filtros.get(3));
								
								//Verificar si se cambio el destino
								verificarCambioDestino(reservaFicticia,(Destino)F_filtros.get(5),false);
								
								//Verificar si la fecha escogida es valida
								boolean F_terminarCicloFecha=true;
								while(F_terminarCicloFecha) {
									if(F_fecha.get(0).get(0)!=100) {
										if(Reserva.comprobarEsMes(F_fecha)) {System.out.println("Actualmente estan seleccionados todos los dias del mes de "+Reserva.mostrarMes(F_fecha.get(0).get(1)));} 
										else {Main.imprimirListaFechas("Estas son las fechas actualmente seleccionadas: ",F_fecha);}
										
										String F_opcMes=ingresarOpcion("¿Que desea hacer'", 0, new ArrayList<>(Arrays.asList("Continuar","Volver a ingresar la fecha")));
										if(F_opcMes.equals("1")) {F_terminarCicloFecha=false;}
										F_fecha=Main.ingresarPeriodoFechas();
									}
									else {System.out.println("Actualmente solo tiene seleccionado un año, vuelva a ingresar una fecha valida");}
									F_fecha=Main.ingresarPeriodoFechas();
								}
								reservaFicticia.setFechas(F_fecha);//Escoger fecha final
								terminarCicloEscogerFecha=false;	
							}
							//Verificar si se borraron los filtros
							if((Boolean)F_filtros.get(0)==true) {
								F_filtros=escogerOpcionBusqueda("Fecha",reservaFicticia);
							}
						}
						
					break;
					}	
					//ESCOGER IDIOMA=I
					String I_opcionMenuIdioma=ingresarOpcion("¿Que desea hacer?",0,new ArrayList<>(Arrays.asList("Ingresar idioma","Buscar los idiomas con mayor disponibilidad")));
					
					Idiomas I_idioma=null;
					switch(I_opcionMenuIdioma) {
					case "1"://Ingresar idioma
						String I_opcIdioma=ingresarOpcion("Elija el idioma",0,ListaIdiomas);
						I_idioma=Idiomas.buscarNombre(ListaIdiomas.get(Integer.parseInt(I_opcIdioma)));
						ArrayList<Idiomas> I_listaIdioma=new ArrayList<>(Arrays.asList(I_idioma));
						reservaFicticia.setIdiomas(I_listaIdioma);
					break;
					
					case "2"://Escoger idioma
						ArrayList<Object> I_filtros=escogerOpcionBusqueda("Fecha",reservaFicticia);
						
						boolean terminarCicloEscogerIdioma=true;
						while(terminarCicloEscogerIdioma) {
							imprimirTablaPlanearIdioma((String)I_filtros.get(8),(int)I_filtros.get(1),(TiposActividad)I_filtros.get(2),Reserva.convertirListaFechas(I_filtros.get(3)),(Idiomas)I_filtros.get(4));
							I_filtros=elegirFiltros("Idioma",(String)I_filtros.get(8),(int)I_filtros.get(1),(TiposActividad)I_filtros.get(2),Reserva.convertirListaFechas(I_filtros.get(3)),(Idiomas)I_filtros.get(4),(Destino)I_filtros.get(5));
							
							//Verificar si se termina el ciclo
							if((Boolean)I_filtros.get(6)==true) {
								
								//Verificar si se cambio el destino
								verificarCambioDestino(reservaFicticia,(Destino)I_filtros.get(5),false);
								
								//Verificar si se cambio la fecha
								verificarCambioFecha(reservaFicticia, Reserva.convertirListaFechas(I_filtros.get(3)),false);
								
								I_listaIdioma=new ArrayList<>(Arrays.asList((Idiomas)I_filtros.get(4)));
								reservaFicticia.setIdiomas(I_listaIdioma);//Escoger idioma final
								terminarCicloEscogerIdioma=false;
							}
							//Verificar si se borraron los filtros
							if((Boolean)I_filtros.get(0)==true) {
								I_filtros=escogerOpcionBusqueda("Fecha",reservaFicticia);
							}
						}
					break;
					}
					
					int cantidadPersonas=ingresarEntero("Ingrese la cantidad de personas: ");
					ArrayList<Cliente> listaClientes=new ArrayList<>();
	    			for(int i=0;i<cantidadPersonas;i++) {listaClientes.add(new Cliente());}
	    			reservaFicticia.setClientes(listaClientes);
					
					boolean ciclo_planHotel=true;
					while(ciclo_planHotel)	{
						//PLANEAR PLAN TURISTICO=P
						String P_opcionMenuPlan=ingresarOpcion("¿Que desea hacer?",0,new ArrayList<>(Arrays.asList("Escoger un plan personalizado","Escoger un paquete turistico")));
						
						Plan P_plan=new Plan();
						boolean P_terminarCicloPlanTuristico=true;
						while(P_terminarCicloPlanTuristico) {
							switch(P_opcionMenuPlan) {
							case "1"://Paquete turistico
								String P_opcionMenuPaquete=ingresarOpcion("¿Que desea hacer?",0,new ArrayList<>(Arrays.asList("Ingresar paquete","Buscar las mejores opciones de paquetes disponibles")));
								
								//Advertencia fecha
								imprimirListaFechas("Le recordamos que actualmente tiene estas fechas elegidas: ", reservaFicticia.getFechas());
								System.out.println("Tenga en cuenta que un paquete turistico tiene una cantidad de dias predeterminada,\n al definir una fecha fija en la busqueda puede retringir mucho la cantidad de paquetes disponibles.");
								String P_opcPaqueteFecha=ingresarOpcion("¿Que desea hacer?",0,new ArrayList<>(Arrays.asList("Continuar con las fechas escogidas","Ver la informacion de cualquier cantidad de dias respetando la fecha de inicio")));
										
								switch(P_opcionMenuPaquete) {
								case "1"://Ingresar paquete
									ArrayList<Plan> P_paquetesDisponibles=null;//Llamar al metodo que muestre las opciones de paquetes segun los parametros
									ArrayList<String> P_listaPaquetes=null;//LLamar al metodo que devuelve el to string de los paquetes
									String P_opcPaquete=ingresarOpcion("¿Cual paquete desea?",0,P_listaPaquetes);
									reservaFicticia.setPlan(P_paquetesDisponibles.get((Integer.parseInt(P_opcPaquete))-1));
									
								break;
								case "2"://Planear paquete
									ArrayList<Object> P_filtrosPaquete=escogerOpcionBusqueda("Paquete",reservaFicticia);
									
									boolean terminarCicloPlanearPaquete=true;
									while(terminarCicloPlanearPaquete) {  
										ArrayList<Object> P_listaPaquetesMostrados=imprimirTablaPlanearPlan((String)P_filtrosPaquete.get(8),P_opcPaqueteFecha,(int)P_filtrosPaquete.get(1),(TiposActividad)P_filtrosPaquete.get(2),Reserva.convertirListaFechas(P_filtrosPaquete.get(3)),(Idiomas)P_filtrosPaquete.get(4),false);
										P_filtrosPaquete=elegirFiltros("Paquete",(String)P_filtrosPaquete.get(8),(int)P_filtrosPaquete.get(1),(TiposActividad)P_filtrosPaquete.get(2),Reserva.convertirListaFechas(P_filtrosPaquete.get(3)),(Idiomas)P_filtrosPaquete.get(4),(Destino)P_filtrosPaquete.get(5));
										
										//Verificar si se cambio de plan
										if((Boolean)P_filtrosPaquete.get(7)==true) {
											P_opcionMenuPlan="2";
											terminarCicloPlanearPaquete=false;
										}
										
										//Verificar si se termina el ciclo
										if((Boolean)P_filtrosPaquete.get(6)==true) {
											
											//Verificar si se cambio el destino
											verificarCambioDestino(reservaFicticia,(Destino)P_filtrosPaquete.get(5),false);
											
											//Verificar si se cambio la fecha
											verificarCambioFecha(reservaFicticia, Reserva.convertirListaFechas(P_filtrosPaquete.get(3)),false);
											
											//Verificar si se cambio de idioma
											verificarCambioIdioma(reservaFicticia, (Idiomas)P_filtrosPaquete.get(4),false);
											
											//Elegir paquete
											P_paquetesDisponibles=null;//Llamar al metodo que muestre las opciones de paquetes segun los parametros
											P_listaPaquetes=null;//LLamar al metodo que devuelve el to string de los paquetes
											P_opcPaquete=ingresarOpcion("¿Cual paquete desea?",0,P_listaPaquetes);
											reservaFicticia.setPlan(P_paquetesDisponibles.get((Integer.parseInt(P_opcPaquete))-1));
											
											
											P_terminarCicloPlanTuristico=false;
										}
										//Verificar si se borraron los filtros
										if((Boolean)P_filtrosPaquete.get(0)==true) {
											P_filtrosPaquete=escogerOpcionBusqueda("Paquete",reservaFicticia);
										}
									}
								break;
								}
						    break;
							
							case "2"://Plan personalizado
								ArrayList<Object> P_filtrosPlan=escogerOpcionBusqueda("Plan",reservaFicticia);
								
								boolean P_terminarCicloElegirActividad=true;
								boolean P_terminarCicloPlanearPlan=true;
								boolean P_terminarCicloFor=true;
								while(P_terminarCicloPlanearPlan) {
									while(P_terminarCicloFor) {
										for(ArrayList<Integer> fecha:reservaFicticia.getFechas()) {
											while(P_terminarCicloElegirActividad) {  
												imprimirTablaPlanearPlan((String)P_filtrosPlan.get(8),P_opcPaqueteFecha,(int)P_filtrosPlan.get(1),(TiposActividad)P_filtrosPlan.get(2),Reserva.convertirListaFechas(P_filtrosPlan.get(3)),(Idiomas)P_filtrosPlan.get(4),false);
												P_filtrosPlan=elegirFiltros("Plan",(String)P_filtrosPlan.get(8),(int)P_filtrosPlan.get(1),(TiposActividad)P_filtrosPlan.get(2),Reserva.convertirListaFechas(P_filtrosPlan.get(3)),(Idiomas)P_filtrosPlan.get(4),(Destino)P_filtrosPlan.get(5));
												
												//Verificar si se cambio de plan
												if((Boolean)P_filtrosPlan.get(7)==true) {
													P_opcionMenuPlan="1";
													P_terminarCicloElegirActividad=false;
												}
												//Verificar si se va a ingresar una actividad
												if((Boolean)P_filtrosPlan.get(6)==true) {
													//Verificar si se cambio el destino
													if(verificarCambioDestino(reservaFicticia,(Destino)P_filtrosPlan.get(5),true)) {P_terminarCicloFor=false;}
													
													//Verificar si se cambio la fecha
													if(verificarCambioFecha(reservaFicticia, Reserva.convertirListaFechas(P_filtrosPlan.get(3)),true)) {P_terminarCicloFor=false;}
													
													//Verificar si se cambio de idioma
													if(verificarCambioIdioma(reservaFicticia, (Idiomas)P_filtrosPlan.get(4),true)) {P_terminarCicloFor=false;}
													
													//Ingresar actividad al plan
													ArrayList<Actividad> P_listaActividades=null;
													ArrayList<String> P_actividadesNombre=new ArrayList<>();
													for(Actividad actividad:P_listaActividades) {P_actividadesNombre.add(actividad.getNombre());}
													String P_opcActividad=ingresarOpcion("Elija una actividad: ", 0, P_actividadesNombre);
													P_plan.añadirActividad(P_listaActividades.get(Integer.parseInt(P_opcActividad)-1));
													P_terminarCicloElegirActividad=false;
												}	
												//Verificar si se borraron los filtros
												if((Boolean)P_filtrosPlan.get(0)==true) {
													P_filtrosPlan=escogerOpcionBusqueda("Plan",reservaFicticia);
												}
											}
										}
										//Mostrar plan final
										String P_verificarPlanFinal=ingresarOpcion("Desea quedarse con ese plan o volver a empezar?", 0, new ArrayList<String>(Arrays.asList("Continuar con este plan","Elegir otro plan")));
										if(P_verificarPlanFinal.equals("1")) {P_terminarCicloPlanearPlan=false;}
									}
									
								}
								
							break;
							}
						}
						//PLANEAR HOTEL
						String H_opcionMenuHotel=ingresarOpcion("¿Que desea hacer?",0,new ArrayList<>(Arrays.asList("Ingresar hotel","Buscar las mejores opciones de hoteles")));
						
						switch(H_opcionMenuHotel) {
						case "1"://Ingresar hotel
							ArrayList<String>H_hotelesDisponibles=Hotel.mostrarHotelesDisponibles(reservaFicticia);
							String H_opcHotel=ingresarOpcion("Elija un hotel: ",0,H_hotelesDisponibles);
							Hotel H_hotel=Hotel.buscarHotelLista(reservaFicticia, H_opcHotel);
							for(Cliente cliente:reservaFicticia.getClientes()) {cliente.setHotel(H_hotel);}
							
						break;
						
						case "2"://Escoger hotel
							ArrayList<Object> H_filtros=escogerOpcionBusqueda("Fecha",reservaFicticia);
							
							boolean terminarCicloEscogerHotel=true;
							while(terminarCicloEscogerHotel) {
								imprimirTablaPlanearHotel((String)H_filtros.get(8),(int)H_filtros.get(1),(TiposActividad)H_filtros.get(2),Reserva.convertirListaFechas(H_filtros.get(3)),(Idiomas)H_filtros.get(4));
								H_filtros=elegirFiltros("Idioma",(String)H_filtros.get(8),(int)H_filtros.get(1),(TiposActividad)H_filtros.get(2),Reserva.convertirListaFechas(H_filtros.get(3)),(Idiomas)H_filtros.get(4),(Destino)H_filtros.get(5));
								
								//Verificar si se termino el ciclo
								if((Boolean)H_filtros.get(6)==true) {
									//Verificar si se cambio el destino
									if(verificarCambioDestino(reservaFicticia,(Destino)H_filtros.get(5),true)) {terminarCicloEscogerHotel=false;}
									
									//Verificar si se cambio la fecha
									if(verificarCambioFecha(reservaFicticia, Reserva.convertirListaFechas(H_filtros.get(3)),true)) {terminarCicloEscogerHotel=false;}
									
									//Verificar si se cambio de idioma
									if(verificarCambioIdioma(reservaFicticia, (Idiomas)H_filtros.get(4),true)) {terminarCicloEscogerHotel=false;}
									
									//Verificar si se cambio la cantidad de personas
									verificarCantidadPersonas(reservaFicticia, (int)H_filtros.get(1));
									
									//Ingresar hotel
									H_hotelesDisponibles=null;
									H_opcHotel=ingresarOpcion("Elija un hotel: ",0,H_hotelesDisponibles);
									H_hotel=Hotel.buscarHotelLista(reservaFicticia, H_opcHotel);
									for(Cliente cliente:reservaFicticia.getClientes()) {cliente.setHotel(H_hotel);}
									ciclo_planHotel=false;
								}	
									
								}
								//Verificar si se borraron los filtros
								if((Boolean)H_filtros.get(0)==true) {
									H_filtros=escogerOpcionBusqueda("Fecha",reservaFicticia);
								}
							}
						break;
						}
					//AÑADIR OTROS METODOS DE HOTEL Y MOSTRAR PRESUPUESTO	
	
				}//Fin ciclo plan
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
						terminarCicloAdmin=terminarCicloFuncionalidad();
					break;
					
					case"2":
						// 2.OPCION RETIRAR GUIA=R
						String R_opc1 =ingresarOpcion("¿Qué desea hacer?", 0,new ArrayList<>(Arrays.asList("Despedir guia","Dar de baja a un guia por un tiempo")));
						
						Guia R_guia=ingresarGuia();
						if(R_opc1.equals("1")) {
							Guia.retirarGuia(R_guia);
						}
						else {
							ArrayList<ArrayList<Integer>> R_listaFechas=ingresarPeriodoFechas();
							
							Guia.retirarGuia(R_guia,R_listaFechas);
							System.out.println("Se ha retirado correctamente a "+R_guia.getNombre()+" en los siguientes dias: "+ R_listaFechas);
						}	
						terminarCicloAdmin=terminarCicloFuncionalidad();
					break;
					case"3":
						//3.OPCION VER DISPONIBILIDAD GUIAS=D
						ArrayList<String> D_menu = new ArrayList<>(Arrays.asList(
							    "Ver la disponibilidad de todos los guías según la fecha",
							    "Ver la disponibilidad de todos los guías según el destino",
							    "Ver la disponibilidad de todos los guías según el idioma",
							    "Ver el itinerario de un guía en específico"));

							String D_opcBusqueda = ingresarOpcion("¿Qué desea buscar?", 0, D_menu);

							String D_opcFecha = ingresarOpcion("Desea buscar según:", 0, new ArrayList<>(Arrays.asList("Mes", "Día")));

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
							            D_destino = ingresarDestino();
							            imprimirTablaDisponibilidadGuias(D_listaFechas, D_opcFiltro, D_opcBusqueda, D_opcFecha, D_guia, D_destino, D_idioma);
							            D_disponibilidadOpciones.remove("Buscar la información de un destino en específico");
							            break;
							        case "3":
							            D_idioma = ingresarIdioma();
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
						terminarCicloAdmin=terminarCicloFuncionalidad();
					break;
					case"5":
						//5.OPCION CANCELAR ACTIVIDAD=C
						String C_opcMenu =ingresarOpcion("¿Qué desea hacer?", 0,new ArrayList<>(Arrays.asList("Eliminar actividad", "Suspender actividad por un tiempo")));
						
						Actividad C_actividad=ingresarActividad();
						
						if(C_opcMenu.equals("1")) {
							Actividad.retirarActividad(C_actividad);
							System.out.println("Se ha cancelado la actividad: "+C_actividad.getNombre()+", correctamente");
						}else {
							ArrayList<ArrayList<Integer>> C_listaFechas=ingresarPeriodoFechas();
							Grupo.retirarActividad(C_actividad, C_listaFechas);
							System.out.println("Se ha suspendido correctamente la actividad: "+C_actividad.getNombre()+", en los siguientes dias: "+C_listaFechas);
						}
						terminarCicloAdmin=terminarCicloFuncionalidad();
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
	}//Cierre del main


//////////////////////////////////////////////////////////////////////////////////////////////
////////////////////METODOS FUNCIONALIDAD OPCIONES DE ADMINISTRADOR///////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////
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
    
//////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////METODOS FUNCIONALIDAD PLANEAR VIAJE///////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////
    
    public static ArrayList<Destino> imprimirTablaPlanearDestino(String opcBusqueda,int clasificacion,TiposActividad tipo,ArrayList<ArrayList<Integer>> fecha,Idiomas idioma) {
    	String D_lineaTablaI = " ------------------------------------------------------------------------------------------------------------------------------- ";
    	String D_lineaTabla = "|-------------------------------------------------------------------------------------------------------------------------------|";

    	//PRIMERA PARTE
		System.out.println(D_lineaTablaI);
		String PrimeraLinea1 = opcBusqueda.equals("1")? "Tipo de actividad: "+tipo.getNombre():opcBusqueda.equals("2")?"Idioma: " +idioma.getNombre():"Fecha de inicio: "+Reserva.mostrarFechaString(fecha.get(0));
        String PrimeraLinea2 = opcBusqueda.equals("1") ? "Dificultad: " + tipo.getDificultad():opcBusqueda.equals("3")?"Cantidad de dias: "+fecha.size():"" ;
         
		System.out.printf("|%-47s%-47s%-32s|%n","",PrimeraLinea1,PrimeraLinea2);
		System.out.println(D_lineaTabla);
		System.out.println(D_lineaTabla);
		
		//SEGUNDA PARTE
		if(opcBusqueda.equals("1")) {
			System.out.printf("|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%n","","Cantidad de","Actividades","Actividades","Actividades","Actividades","Actividades","Total de");
		}else if(opcBusqueda.equals("2")) {
			System.out.printf("|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%n","","Actividades","Actividades","Actividades","Actividades","Actividades","Actividades","Total de");
		}else {
			System.out.printf("|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%n","","Actividades","Actividades","Actividades","Actividades","Actividades","Actividades","Total de");
				
		}
		

		
		
	}
    
    public static void imprimirTablaPlanearFecha(String opcBusqueda,int clasificacion,TiposActividad tipo,ArrayList<ArrayList<Integer>> fecha,Idiomas idioma) {
		
	}
	
    public static void imprimirTablaPlanearIdioma(String opcBusqueda,int clasificacion,TiposActividad tipo,ArrayList<ArrayList<Integer>> fecha,Idiomas idioma) {
		
	}
    
    public static void imprimirTablaPlanearHotel(String opcBusqueda,int clasificacion,TiposActividad tipo,ArrayList<ArrayList<Integer>> fecha,Idiomas idioma) {
		
	}
    
    public static ArrayList<Object> imprimirTablaPlanearPlan(String opcBusqueda,String opcFecha,int clasificacion,TiposActividad tipo,ArrayList<ArrayList<Integer>> fecha,Idiomas idioma,boolean planPersonalizado) {
		return null;
	}

	public static ArrayList<Object> elegirFiltros(String busqueda,String opcPrimerFiltro,int clasificacion, TiposActividad tipo, ArrayList<ArrayList<Integer>> fecha,Idiomas idioma,Destino destino) {
		ArrayList<String> opcionesFiltro = new ArrayList<>(7);

		//CREAR MENU DE OPCIONES DE FILTRO SEGUN LOS PARAMETROS INGRESADOS
		opcionesFiltro.add(0,"Borrar Filtros");//1.borrar filtros
		opcionesFiltro.add(1,clasificacion == 0 ? "Filtrar según una clasificación de edad" : "Cambiar la clasificación de edad");//2.Filtrar por clasificacion
		opcionesFiltro.add(2,tipo == null ? "Filtrar según un tipo de actividad" : "Cambiar el tipo de actividad");//3.Filtrar por tipo de actividad
		opcionesFiltro.add(3,fecha==null ? "Filtrar por fecha" : "Cambiar fecha");//4.Filtrar por fecha
		opcionesFiltro.add(4,busqueda.equals("Idioma")?"Escoger idioma":idioma==null?"Filtrar por un idioma en específico":"Cambiar idioma");//5.Filtrar por idioma
		opcionesFiltro.add(5,!busqueda.equals("Destino")?"Cambiar destino":"Escoger Destino");//6.Filtrar por destino
		if(busqueda.equals("Fecha")) {opcionesFiltro.add(6,"Escoger fechas");};//7.Elegir opcion Fechas
		if(busqueda.equals("Plan")||busqueda.equals("Paquete")) {opcionesFiltro.add(6,busqueda.equals("Plan")?"Escoger actividad":"Escoger plan turistico");};//7.Elegir opcion Fechas
		if(busqueda.equals("Hotel")) {opcionesFiltro.add(6,"Escoger hotel");};//7.Elegir opcion Hotel
		if(busqueda.equals("Plan")||busqueda.equals("Paquete")) {opcionesFiltro.add(7,busqueda.equals("Plan")?"Cambiar a planear paquete turistico":"Cambiar a planear plan personalizado");}//8.Cambiar opcion plan turistico
				
		String filtro=ingresarOpcion("¿Que desea hacer?",0,opcionesFiltro);
		ArrayList<Object> parametrosFiltrados=new ArrayList<>(7);
		
		//ACTUALIZAR LOS PARAMETROS SEGUN LA OPCION INGRESADA
		boolean parametro0=filtro.equals("1")?true:false;
		parametrosFiltrados.add(0,parametro0);//Borrar filtros
		
		int parametro1=filtro.equals("2")?ingresarClasificacion():clasificacion;
		parametrosFiltrados.add(1,parametro1);//Clasificacion
		
		TiposActividad parametro2=filtro.equals("3")?ingresarTipoActividad():tipo;
		parametrosFiltrados.add(2,parametro2);//Tipo actividad
		
		ArrayList<ArrayList<Integer>> parametro3=filtro.equals("4")?ingresarFiltroFecha(busqueda):filtro.equals("6")?ingresarPeriodoFechas():fecha;
		parametrosFiltrados.add(3,parametro3);//Fecha
		
		Idiomas parametro4=filtro.equals("5")?ingresarIdioma():idioma;
		parametrosFiltrados.add(4,parametro4);//Idioma
		
		Destino parametro5=filtro.equals("6")?ingresarDestino():destino;
		parametrosFiltrados.add(5,parametro5);//Destino
		
		Boolean parametro6=filtro.equals("5")&&busqueda.equals("Idioma")||filtro.equals("6")&&busqueda.equals("Destino")||filtro.equals("7")?true:false;
		parametrosFiltrados.add(6,parametro6);//Elegir opcion
		
		Boolean parametro7=filtro.equals("8")?true:false;
		parametrosFiltrados.add(7,parametro7);//Cambiar opcion de tipo de plan
		
		parametrosFiltrados.add(8,opcPrimerFiltro);//Opcion filtro
		
		//BORRAR FILTROS
		if(filtro.equals("0")||parametro6) {
			for(int i=0;i<7;i++) {parametrosFiltrados.set(i,null);}
		}
		
		return parametrosFiltrados;
		}

    public static ArrayList<ArrayList<Integer>> ingresarFiltroFecha(String opcionFiltro) {
    	
    	ArrayList<ArrayList<Integer>> fecha=new ArrayList<>();
    	
    	if(opcionFiltro.equals("Fecha")) {fecha=ingresarFecha("1");}
    	else if(opcionFiltro.equals("Año")) {
    		int año=ingresarEntero("Ingrese el año: ");
			ArrayList<Integer> añoFecha = new ArrayList<>(Arrays.asList(100, 100, año));
			fecha.add(añoFecha);
    	}else {
    		ArrayList<String> opcionesFecha=new ArrayList<>(Arrays.asList(
    				"Buscar por año","Buscar por mes","Buscar por dias en especifico"));
    		String opcionFecha=ingresarOpcion("¿Que desea hacer?",0,opcionesFecha);
    		
    		if(opcionFecha.equals("1")) {
    			int año=ingresarEntero("Ingrese el año: ");
    			ArrayList<Integer> añoFecha = new ArrayList<>(Arrays.asList(100, 100, año));
    			fecha.add(añoFecha);
    		}else {
    			fecha=opcionFecha.equals("2")?ingresarFecha("1"):ingresarPeriodoFechas();
    		}
    	}
		return fecha;
    }
    
    public static boolean verificarCambioDestino(Reserva reserva, Destino destino,boolean plan) {
    	if(!reserva.getDestino().equals(destino)) {
    		System.out.println("Se cambio el destino guardado anteriormente ("+reserva.getDestino()+") por un nuevo destino ("+destino);
    		if(plan) {System.out.println("Tenga en cuenta que si cambia el destino perdera el progreso y debera volver a empezar a planear el plan desde cero.");}
    		String opcDestino=ingresarOpcion("¿Que desea hacer'", 0, new ArrayList<>(Arrays.asList("Continuar con el nuevo destino","Volver a seleccionar el destino escogido anteriormente")));
    		if(opcDestino.equals("1")) {
    			reserva.setDestino(destino);
    			return true;}
    	}
    	return false;
    }

    public static boolean verificarCambioIdioma(Reserva reserva, Idiomas idioma,boolean plan) {
    	if(!reserva.getIdiomas().get(0).equals(idioma)) {
    		System.out.println("Se cambio el idioma guardado anteriormente ("+reserva.getIdiomas().get(0)+") por un nuevo idioma ("+idioma);
    		if(plan) {System.out.println("Tenga en cuenta que si cambia el idioma perdera el progreso y debera volver a empezar a planear el plan desde cero.");}
    		String opcIdioma=ingresarOpcion("¿Que desea hacer'", 0, new ArrayList<>(Arrays.asList("Continuar con el nuevo idioma","Volver a seleccionar el idioma escogido anteriormente")));
    		if(opcIdioma.equals("1")) {
    			reserva.setIdiomas(new ArrayList<Idiomas>(Arrays.asList(idioma)));
    			return true;}
    	}
    	return false;
    }
    
    public static boolean verificarCantidadPersonas(Reserva reserva, int cantidad) {
    	if(reserva.getClientes().size()!=cantidad) {
    		System.out.println("Se cambio la cantidad de personas guardada anteriormente ("+reserva.getClientes().size()+") por una nueva cantidad ("+cantidad);
    		System.out.println("Tenga en cuenta que si cambia la cantidad de personas se le hara el presupuesto con la nueva cantidad y no se tendra en cuenta la anterior cantidad de personas.");
    		String opcIdioma=ingresarOpcion("¿Que desea hacer'", 0, new ArrayList<>(Arrays.asList("Continuar con la nueva cantidad de personas","Volver a seleccionar la cantidad de personas escogida anteriormente")));
    		if(opcIdioma.equals("1")) {
    			ArrayList<Cliente> listaClientes=new ArrayList<>();
    			for(int i=0;i<cantidad;i++) {
    				listaClientes.add(new Cliente());
    			}
    			reserva.setClientes(listaClientes);
    			return true;}
    	}
    	return false;
    }
  
    public static boolean verificarCambioFecha(Reserva reserva, ArrayList<ArrayList<Integer>> fecha,boolean plan) {
	if(!reserva.getFechas().equals(fecha)) {
		String I_opcFecha=null;

		if(fecha.get(0).get(0)==100||Reserva.comprobarEsMes(fecha)) {
			I_opcFecha=ingresarOpcion("Se modifico el filtro de fechas\n¿Que desea hacer?", 0, new ArrayList<>(Arrays.asList("Ingresar nuevas fechas","Volver a seleccionar las fechas guardadas anteriormente")));
			if(plan) {System.out.println("Tenga en cuenta que si cambia las fechas perdera el progreso y debera volver a empezar a planear el plan desde cero.");}
			if(I_opcFecha.equals("1")) {
				fecha=ingresarPeriodoFechas();
				reserva.setFechas(fecha);
				return true;}
		}else {
			imprimirListaFechas("Se cambiaron las fechas guardadas anteriormente\nEstas son las fechas actualmente seleccionadas: ",fecha);
			if(plan) {System.out.println("Tenga en cuenta que si cambia las fechas perdera el progreso y debera volver a empezar a planear el plan desde cero.");}
			I_opcFecha=ingresarOpcion("¿Que desea hacer'", 0, new ArrayList<>(Arrays.asList("Continuar con las fechas actualmente seleccionadas","Volver a seleccionar las fechas guardadas anteriormente")));
			if(I_opcFecha.equals("1")) {
				reserva.setFechas(fecha);
				return true;}
		}
	}
	return false;
    }
    
    public static ArrayList<Object> escogerOpcionBusqueda(String busqueda,Reserva reserva) {
    	ArrayList<Object> filtros=new ArrayList<>();
    	ArrayList<String> menu=new ArrayList<>();
    	
    	//CREAR MENU DE OPCIONES SEGUN LOS PARAMETROS INGRESADOS
    	menu.add(0,busqueda.equals("Hotel")?"Segun sus actividades":"Segun un tipo de actividad");//Buscar segun tipo de actividad
    	menu.add(1,busqueda.equals("Idioma")||busqueda.equals("Hotel")?"Segun disponibilidad":(busqueda.equals("Paquete")||busqueda.equals("Plan"))?"Segun una clasificacion de edad":"Segun un idioma especifico");//Buscar segun idioma o disponibilidad en idioma o clasificacion en plan
    	if(!busqueda.equals("Idioma")) {menu.add(2,busqueda.equals("Destino")?"Segun disponibilidad en una fecha":(busqueda.equals("Paquete")||busqueda.equals("Plan"))?"Ver todas las opciones disponibles":busqueda.equals("Hotel")?"Segun sus restaurantes":"Segun un mes");}//Buscar segun fecha o mes en fecha o todas las opciones en plan o restaurantes en hotel
    	if(busqueda.equals("Fecha")) {menu.add(3,"Segun un año");}//buscar segun año en fecha
    	
    	String pregunta=busqueda.equals("Paquete")?"¿Como desea buscar su paquete?":busqueda.equals("Plan")?"¿Como desea buscar sus actividades?":"¿Como desea buscar?";
    	String opcBusqueda=ingresarOpcion(pregunta,0,menu);
    	
    	//AÑADIR PARAMETROS AL FILTRO
    	int clasificacion=(opcBusqueda.equals("2")&&(busqueda.equals("Paquete")||busqueda.equals("Plan")))?ingresarClasificacion():0;
    	TiposActividad tipoActividad=opcBusqueda.equals("1")?ingresarTipoActividad():null;
    	Idiomas idioma=opcBusqueda.equals("2")&&(!busqueda.equals("Idioma"))?(busqueda.equals("Paquete")||busqueda.equals("Plan")||busqueda.equals("Hotel"))?reserva.getIdiomas().get(0):ingresarIdioma():null;
    	Destino destino=busqueda.equals("Destino")?null:reserva.getDestino();
    	ArrayList<ArrayList<Integer>> fecha = null;
    	if (!busqueda.equals("Fecha")) {fecha = opcBusqueda.equals("3") ? ingresarPeriodoFechas() :!busqueda.equals("Destino")?reserva.getFechas():null;} 
    	else {fecha = opcBusqueda.equals("3") ? ingresarFiltroFecha("Fecha") : ingresarFiltroFecha("Año");}
    	
    	
    	filtros=new ArrayList<>(Arrays.asList(false,clasificacion,tipoActividad,fecha,idioma,destino,false,false,opcBusqueda));
		
    	return filtros;
    }
    
}
