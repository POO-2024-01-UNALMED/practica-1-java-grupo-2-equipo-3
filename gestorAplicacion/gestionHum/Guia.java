package gestorAplicacion.gestionHum;
import java.util.ArrayList;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.manejoReserva.Actividad;
import gestorAplicacion.manejoReserva.Destino;
import gestorAplicacion.manejoReserva.Grupo;
import gestorAplicacion.manejoReserva.Reserva;

public class Guia extends Persona {
		private String nombre;
		private ArrayList<Idiomas> idiomas;
		private ArrayList<TiposActividad> tipoActividades;
		private Destino destino;
		private int precio;
		private ArrayList<ArrayList<Integer>> diasOcupados;
		private ArrayList<ArrayList<Integer>> diasNoDisponibles;
		private static ArrayList<Guia>guias=new ArrayList<>();
		
		public Guia(String nombre) {
			super();
			this.nombre = nombre;
			this.idiomas=new ArrayList<>();
			this.tipoActividades=new ArrayList<>();
			this.diasOcupados=new ArrayList<>();
			this.diasNoDisponibles=new ArrayList<>();
			guias.add(this);
		}
		
		public ArrayList<Idiomas> getIdiomas() {
			return idiomas;
		}


		void setIdiomas(ArrayList<Idiomas> idiomas) {
			this.idiomas = idiomas;
		}


		public ArrayList<TiposActividad> getTipoActividades() {
			return tipoActividades;
		}


		void setTipoActividades(ArrayList<TiposActividad> tipoActividades) {
			this.tipoActividades = tipoActividades;
		}


		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}


		public Destino getDestino() {
			return destino;
		}

		public void setDestino(Destino destino) {
			this.destino = destino;
		}


		public int getPrecio() {
			return precio;
		}

		void setPrecio(int precio) {
			this.precio = precio;
		}
		
		public ArrayList<ArrayList<Integer>> getDiasOcupados() {
			return diasOcupados;
		}

		public void setDiasOcupados(ArrayList<ArrayList<Integer>> diasOcupados) {
			this.diasOcupados=diasOcupados;
		}

		public ArrayList<ArrayList<Integer>> getDiasNoDisponibles() {
			return diasNoDisponibles;
		}

		void setDiasNoDisponibles(ArrayList<ArrayList<Integer>> diasNoDisponibles) {
			this.diasNoDisponibles=diasNoDisponibles;
		}

			
		public static ArrayList<Guia> getGuias() {
			return guias;
		}

		static void setGuias(ArrayList<Guia> guias) {
			Guia.guias = guias;
		}
		
		
		@Override
		public String toString() {
			return "Nombre del guia: " + nombre + ",\nIdiomas que domina: " + idiomas + ",\nTipo de actividades: " + tipoActividades + ",\nDestino asignado: "
					+ destino.getNombre();
		}

		public void ingresarIdiomas(String idiomas) {
			for(String posicion : idiomas.split(" ")) {
				int numero = Integer.parseInt(posicion);
				for(Idiomas idioma:Idiomas.values()) {
					if(numero==idioma.getPosicion()) {
						this.idiomas.add(idioma);}
				}
			}
		}

		public void ingresarTipoActividades(String tipoActividades) {
			for(String posicion : tipoActividades.split(" ")) {
				int numero = Integer.parseInt(posicion);
				for(TiposActividad tipo:TiposActividad.values()) {
					if(numero==tipo.getPosicion()) {
						this.tipoActividades.add(tipo);}
				}
			}
		}

			public void asignarPrecio() {
				//SE PUEDE MEJORAR
				int precioBase=30000;
				int precioExtra=0;
				for (Idiomas idioma : this.idiomas) {
		            precioExtra += idioma.getPrecio();
		        }
				//FALTA PRECIO EXTRA POR DESTINO
				int precioFinal=precioBase+precioExtra;
				this.precio=precioFinal;
			}
			
			public void ingresarGuia() {
				ArrayList<Actividad> listaActividades = this.destino.mostrarActividadesTipo(this);
				for(Actividad actividad:listaActividades) {
					if(!actividad.getGuias().contains(this)) {
						actividad.setGuias(this);
					}
				}
				
			}

		public static Guia buscarGuia(String nombre) {
			for (Guia guia : guias) {
		        if (nombre.equals(guia.nombre)) {
		            return guia;
		        }
		    }
		    return null;
			
		}

		public static ArrayList<Guia> buscarDisponibilidad(ArrayList<Guia> guiasCapacitados, ArrayList<Integer> fecha) {
			ArrayList<Guia> guiasDisponibles=new ArrayList<>();
			for(Guia guia:guiasCapacitados) {
				if(!guia.diasOcupados.contains(fecha)) {
					guiasDisponibles.add(guia);
				}
			}
			return guiasDisponibles;
		}

		public static void retirarGuia(Guia retirado,ArrayList<ArrayList<Integer>> listaFechas) {

			for(ArrayList<Integer> dia:retirado.diasOcupados) {
				if(!retirado.diasNoDisponibles.contains(dia)&&(listaFechas==null||listaFechas.contains(dia))) {
					Grupo.retirarGuia(retirado,dia);
				}
			}
			
			for(ArrayList<Integer>dia:listaFechas) {
				if(!retirado.diasOcupados.contains(dia)) {
					retirado.diasOcupados.add(dia);
				}
				if(!retirado.diasNoDisponibles.contains(dia)) {
					retirado.diasNoDisponibles.add(dia);
				}
			}
			
			
		}
		
		public static void retirarGuia(Guia despedido) {
			Guia.retirarGuia(despedido,null);

			Actividad.retirarGuia(despedido);
			Guia.guias.remove(despedido);
			despedido=null;

		}

		public static ArrayList<Object> mostrarDisponibilidadGuias(ArrayList<Integer> fecha,Destino destino,Idiomas idioma) {
			ArrayList<Object> tabla=new ArrayList<>();
			
			int guiasOcupados=0;
			int guiasDisponibles=0;
			ArrayList<Destino> destinos=new ArrayList<>();
			ArrayList<Idiomas> idiomas=new ArrayList<>();
			ArrayList<Actividad> actividades=new ArrayList<>();
			int contadorActividades=0;
			int contadorClientes=0;
			int contadorGuiasIdioma=0;
			
			for(Guia guia:guias) {
				Grupo grupoGuia=Grupo.buscarGrupo(fecha,guia);
				boolean isDestinoMatch = (destino == null )|| guia.destino.equals(destino);
				boolean isIdiomaMatch= (idioma==null)||grupoGuia.getIdioma().equals(idioma);
				
				if(isDestinoMatch &&guia.diasOcupados.contains(fecha)&&isIdiomaMatch) {
					guiasOcupados++;
					
					destinos.add(guia.destino);
					idiomas.add(grupoGuia.getIdioma());
					for(ArrayList<Cliente> Reserva:grupoGuia.getListaReservas()) {
						contadorClientes+=Reserva.size();
					}
					
					if(!actividades.contains(grupoGuia.getActividad())) {
						actividades.add(grupoGuia.getActividad());
						contadorActividades++;
					}
				}else {guiasDisponibles++;}
				
				if(idioma!=null&&guia.idiomas.contains(idioma)) {
					contadorGuiasIdioma++;
				}
			}
			
			String actividad=(contadorActividades+"/"+actividades.size());
			ArrayList<String> destinoComun = Destino.buscarDestinoComun(destinos);
			ArrayList<String> listIdioma=Grupo.buscarIdiomaMasUsado(idiomas);
			
			tabla.add(0, fecha.get(2));//año
			tabla.add(1, Reserva.mostrarMes(fecha.get(1)));//mes
			tabla.add(2, fecha.get(0));//dia
			tabla.add(3,guiasDisponibles );//guias Disponibles
			tabla.add(4,guiasOcupados );//guias Ocupados
			tabla.add(5,actividad );//contador Actividades
			
			if(destino==null) {tabla.add(6,destino );}//destino
			else {tabla.add(6,destinoComun );}//destino comun
			
			if(idioma==null) {tabla.add(7,idioma );}//idioma 
			else {tabla.add(7,listIdioma );}//idioma mas usado
			
			tabla.add(8,contadorClientes );//contador clientes
			tabla.add(9,contadorGuiasIdioma );//contador guias por idioma
			
			return tabla;
		}
		
		
		public ArrayList<Object> mostrarIntinerario(ArrayList<Integer> fecha) {
			ArrayList<Object> tabla=new ArrayList<>();
		
			String estado="Disponible";
			ArrayList<String> tipo=new ArrayList<>();
			Grupo grupo=null;
			int cantidadPersonas=0;
			
			if(this.diasOcupados.contains(fecha)) {
				estado="Ocupado";
				grupo=Grupo.buscarGrupo(fecha, this);
			}
			else if(this.diasNoDisponibles.contains(fecha)) {estado="No disponible";}
			
			for(ArrayList<Cliente> Reserva:grupo.getListaReservas()) {
				cantidadPersonas+=Reserva.size();
			}
			
			for(TiposActividad x:grupo.getActividad().getTipo()) {
				tipo.add(x.getNombre()+",");
			}
			tabla.add(0, fecha.get(2));//año
			tabla.add(1, Reserva.mostrarMes(fecha.get(1)));//mes
			tabla.add(2, fecha.get(0));//dia
			tabla.add(3,this.nombre );//nombre
			tabla.add(4,this.destino );//destino
			tabla.add(5,estado );//estado
			tabla.add(6,grupo.getActividad().getNombre());//actividad
			tabla.add(7,tipo);//tipo actividad
			tabla.add(8,grupo.getIdioma() );//idioma
			tabla.add(9,cantidadPersonas );//cantidadPersonas
			tabla.add(10,grupo.getClasificacion() );//clasificacion
			
			return tabla;
		}
		
		public static void imprimirTabla(ArrayList<ArrayList<Integer>> listaFechas,String opcFiltro,  String opcBusqueda, String opcFecha, Guia guia,Destino destino,Idiomas idioma) {
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
					boolean isDestinoMatch = (destino == null )|| x.destino.equals(destino);
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

		public static String mostrarBusquedaDisponibilidad(ArrayList<ArrayList<Integer>> listaFechas, String filtro,String opcBusqueda, Destino destino, Idiomas idioma) {
			String filtroLinea=opcBusqueda.equals("4")?"":(filtro==null?"de todos los guias,":"de "+ filtro.toLowerCase()+",");
			String destinoLinea=opcBusqueda.equals("4")?"":(destino==null?"":"del destino: "+destino+",");
			String idiomaLinea=opcBusqueda.equals("4")?"":(idioma==null?"":"con el idioma: "+idioma+",");
			String fechaLinea=listaFechas.size()>1?"en el mes: "+Reserva.mostrarMes(listaFechas.get(0).get(1)):"en el dia: "+listaFechas.get(0).get(1);
			String texto=(filtroLinea+destinoLinea+idiomaLinea+fechaLinea+"\n¿desea continuar o reestaurar los filtros?(Digite solo el numero de una unica opcion)\n1.Continuar\n2.Volver al inicio");
			return texto;
		}

		
		
		
			
	}

