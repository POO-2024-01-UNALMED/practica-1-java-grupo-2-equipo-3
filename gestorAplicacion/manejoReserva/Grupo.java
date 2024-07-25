package gestorAplicacion.manejoReserva;

import java.util.ArrayList;

import gestorAplicacion.enums.Idiomas;
import gestorAplicacion.enums.TiposActividad;
import gestorAplicacion.gestionHum.Cliente;
import gestorAplicacion.gestionHum.Guia;

public class Grupo {
	
		private static ArrayList<Grupo> grupos=new ArrayList<>();
		private ArrayList<Integer> fecha;
		private Guia guia;
		private Actividad actividad;
		private Idiomas idioma;
		private ArrayList<ArrayList<Cliente>> listaReservas;
		private int capacidad;
		private int clasificacion;

		
		ArrayList<Integer> getFecha() {
			return fecha;
		}

		void setFecha(ArrayList<Integer> fecha) {
			this.fecha = fecha;
		}

		Guia getGuia() {
			return guia;
		}

		void setGuia(Guia guia) {
			this.guia = guia;
		}

		public Actividad getActividad() {
			return actividad;
		}

		void setActividad(Actividad actividad) {
			this.actividad = actividad;
		}

		public Idiomas getIdioma() {
			return idioma;
		}

		void setIdioma(Idiomas idioma) {
			this.idioma = idioma;
		}

		public ArrayList<ArrayList<Cliente>> getListaReservas() {
			return listaReservas;
		}

		void setListaReservas(ArrayList<ArrayList<Cliente>> listaReservas) {
			this.listaReservas = listaReservas;
		}

		int getCapacidad() {
			return capacidad;
		}

		void setCapacidad(int capacidad) {
			this.capacidad = capacidad;
		}

		public int getClasificacion() {
			return clasificacion;
		}

		void setClasificacion(int clasificacion) {
			this.clasificacion = clasificacion;
		}

		public Grupo(ArrayList<Integer> fecha, Guia guia, Actividad actividad, Idiomas idioma,
				ArrayList<ArrayList<Cliente>> listaReservas) {
			super();
			this.fecha = fecha;
			this.guia = guia;
			this.actividad = actividad;
			this.idioma = idioma;
			this.listaReservas = listaReservas;
			this.capacidad = actividad.getCapacidad();
			this.clasificacion = actividad.getClasificacion();
			grupos.add(this);
		}

		public static void retirarGuia(Guia guia, ArrayList<Integer> fecha) {
			Grupo grupo=null;
			for(Grupo x:grupos) {
				if(x.guia.equals(guia)&&x.fecha.equals(fecha)) {
					grupo=x;
					break;
				}
			}
			Guia reemplazo=grupo.elegirGuia();
			if (reemplazo==null) {
				ArrayList<Grupo> gruposSustitutos=Grupo.buscarGrupo(grupo.fecha,grupo.actividad,grupo.idioma);
				ArrayList<ArrayList<Cliente>> listaReservasPendientes=grupo.listaReservas;
				for(Grupo x: gruposSustitutos) {
					ArrayList<ArrayList<Cliente>> reservasPendientes=x.reubicarReservas(listaReservasPendientes);
					if (reservasPendientes.isEmpty()) {
						break;
					}else {
						listaReservasPendientes=reservasPendientes;
					}									
				}
				if(!listaReservasPendientes.isEmpty()) {
					for(ArrayList<Cliente> reservas:listaReservasPendientes) {
						for(Cliente cliente:reservas) {
							cliente.cancelarActividad(grupo.actividad,grupo.fecha);
						}
					}
				}
				grupos.remove(grupo);
				grupo=null;
			}
			else {
				grupo.guia=reemplazo;
				ArrayList<ArrayList<Integer>> diasOcupados = reemplazo.getDiasOcupados();
				diasOcupados.add(fecha);
				reemplazo.setDiasOcupados(diasOcupados);
			}
			
		}

		public ArrayList<ArrayList<Cliente>> reubicarReservas(ArrayList<ArrayList<Cliente>> listaReservas) {
			int cantidadClientesActuales=0;
			ArrayList<ArrayList<Cliente>> listaFinal=listaReservas;
			for(ArrayList<Cliente> lista:listaReservas) {
				cantidadClientesActuales+=lista.size();
			}
			for(ArrayList<Cliente> lista:listaReservas) {
				if(cantidadClientesActuales+lista.size()<=this.capacidad) {
					this.listaReservas.add(lista);
					cantidadClientesActuales+=lista.size();
					listaFinal.remove(lista);
				}
			}
			return listaFinal;
		}


		public static ArrayList<Grupo> buscarGrupo(ArrayList<Integer> fecha, Actividad actividad, Idiomas idioma) {
			ArrayList<Grupo> gruposEncontrados=new ArrayList<>();
			for(Grupo grupo:grupos) {
				if(grupo.fecha.equals(fecha)&&grupo.actividad.equals(actividad)&&grupo.idioma.equals(idioma)) {
					gruposEncontrados.add(grupo);
				}		
			}
			return gruposEncontrados;
		}
		
		public static Grupo buscarGrupo(ArrayList<Integer> fecha,Guia guia) {
			for(Grupo grupo:grupos) {
				if(grupo.fecha.equals(fecha)&&grupo.guia.equals(guia)) {
					return grupo;
				}		
			}
			return null;
		}

		public Guia elegirGuia() {
			ArrayList<Guia> guiasCapacitados=this.actividad.buscarGuia(this.idioma);
			ArrayList<Guia> guiasDisponibles=Guia.buscarDisponibilidad(guiasCapacitados,this.fecha);
			ArrayList<TiposActividad> tipoActividad=this.actividad.getTipo();
			ArrayList<Guia> guiasFinal=new ArrayList<>();
			int mayorConcidencia=0;
			
			for(Guia guia:guiasDisponibles) {
				int concidencia=0;
				for(TiposActividad tipo:guia.getTipoActividades()) {
					if(tipoActividad.contains(tipo)) {
						concidencia++;
					}
				}
				if(concidencia==mayorConcidencia) {
					guiasFinal.add(guia);
				}
				else if(concidencia>mayorConcidencia) {
					mayorConcidencia=concidencia;
					guiasFinal.clear();
					guiasFinal.add(guia);
				}
			}
			if(guiasFinal.size()==1) {
				return guiasFinal.get(0);
			}
			else if(guiasFinal.size()>1) {
				int menorPrecio =guiasFinal.get(0).getPrecio() ;
				Guia guiaElegido=guiasFinal.get(0);
				for(Guia guia:guiasFinal) {
					int precio=guia.getPrecio();		
					if(precio<menorPrecio) {
						guiaElegido=guia;
						menorPrecio=precio;
					}
				}
				return guiaElegido;		
			}
			else {
				return null;
			}
		}

		public static void retirarActividad(Actividad actividad,ArrayList<ArrayList<Integer>> fecha) {
			for(Grupo grupo: grupos) {
				if(grupo.actividad.equals(actividad)&&(fecha.isEmpty() || fecha.contains(grupo.fecha))) {
					for(ArrayList<Cliente> reservas:grupo.listaReservas) {
						for(Cliente cliente:reservas) {
							cliente.cancelarActividad(grupo.actividad,grupo.fecha);
						}
					}
					ArrayList<ArrayList<Integer>> diasOcupados = grupo.guia.getDiasOcupados();
					diasOcupados.remove(grupo.fecha);
					grupo.guia.setDiasOcupados(diasOcupados);
					grupo=null;
				}		
			}	
		}

		public static ArrayList<String> buscarIdiomaMasUsado(ArrayList<Idiomas> listaIidiomas) {
			int contador=0;
			int cantidadMaxima=0;
			ArrayList<Idiomas> idiomaMasUsado=new ArrayList<>();
			
			for(Idiomas idioma: Idiomas.values()) {
				contador=0;
				for(Idiomas x:listaIidiomas) {
					if(x.equals(idioma)) {contador++;}	
				}
				
				if(contador==cantidadMaxima) {
					idiomaMasUsado.add(idioma);
					}
				else if(contador>cantidadMaxima) {
					idiomaMasUsado.clear();
					idiomaMasUsado.add(idioma);
					cantidadMaxima=contador;
				}
			}
			
			ArrayList<String> listaIdioma=new ArrayList<>();
			
			for(int i=0;i<idiomaMasUsado.size();i++) {
				String idioma=idiomaMasUsado.get(i)+"="+cantidadMaxima+"/"+listaIidiomas.size();
				listaIdioma.add(idioma);
			}
			
			return listaIdioma;
		}

		public static int cantidadClientesIdioma(ArrayList<ArrayList<Integer>> listaFechas, Idiomas idioma) {
			int cantidad=0;
			for(Grupo grupo:grupos) {
				for(ArrayList<Integer> fecha:listaFechas) {
					if(grupo.idioma.equals(idioma)&&grupo.fecha.equals(fecha)) {
						for(ArrayList<Cliente> reserva:grupo.listaReservas) {
							cantidad+=reserva.size();
						}
					}
				}
			}
			return cantidad;
		}

	}


