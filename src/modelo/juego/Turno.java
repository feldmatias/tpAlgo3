package modelo.juego;

import java.util.LinkedList;
import java.util.Queue;

import modelo.excepciones.AtaqueNoPosible;
import modelo.excepciones.MovimientoNoPosible;
import modelo.excepciones.PersonajeNoPerteneceAEquipo;
import modelo.excepciones.PersonajeNoSeleccionable;
import modelo.excepciones.TransformacionNoPosible;
import modelo.personajes.Personaje;
import modelo.utilidades.Constantes;


public class Turno {
	
	private Queue<Jugador> jugadores;
	private boolean movimientoRealizado = false;
	private boolean ataqueRealizado = false;
	private Tablero tablero;
	
	public Turno(Jugador jugadorInicial, Jugador otroJugador, Tablero tablero){
		this.jugadores = new LinkedList<Jugador>();
		jugadores.add(jugadorInicial);
		jugadores.add(otroJugador);
		this.tablero = tablero;
		this.empezarTurno();
	}
	

	private void empezarTurno(){
		this.jugadorActual().comprobarPersonajeSeleccionado();
		this.jugadorActual().empezarTurno();	
		this.movimientoRealizado = false;
		this.ataqueRealizado = false;
		this.tablero.generarConsumibles();
		this.comprobarTurnoTerminado();
	}
	
	public void seleccionarPersonaje (Personaje personaje) throws PersonajeNoSeleccionable{
		try{
			this.jugadorActual().seleccionarPersonaje(personaje);
		} catch (PersonajeNoPerteneceAEquipo e){
			throw new PersonajeNoSeleccionable();
		}
	}
	
	public void atacarEnemigo(Personaje enemigo) throws AtaqueNoPosible{
		if (ataqueRealizado){
			throw new AtaqueNoPosible(Constantes.ErrorAtaqueYaRealizado);
		}
		this.jugadorActual().atacar(enemigo);

		this.terminarAtaque();
	}
	
	public void realizarAtaqueEspecial(Personaje enemigo) throws AtaqueNoPosible {
		if (ataqueRealizado){
			throw new AtaqueNoPosible(Constantes.ErrorAtaqueYaRealizado);
		}
		this.jugadorActual().realizarAtaqueEspecial(enemigo);
		
		this.terminarAtaque();
	}

	public void moverPersonaje(Posicion destino) throws  MovimientoNoPosible{
		if (movimientoRealizado){
			throw new MovimientoNoPosible(Constantes.ErrorMovimientoYaRealizado);
		}
		this.jugadorActual().mover(destino);
		
		this.movimientoRealizado = true;
		this.comprobarTurnoTerminado();
	}
	
	public void transformar() throws TransformacionNoPosible {
		this.jugadorActual().transformarPersonaje();
	}
	
	public void terminarTurno() {
		jugadores.add(jugadores.remove()); // Cambia el jugador actual
		this.empezarTurno();
	}
	
	private void comprobarTurnoTerminado() {
		if ( ! this.puedeAtacar() && ! this.puedeMover()){
			this.terminarTurno();
		}
	}
	
	private boolean puedeAtacar() {
		if (this.ataqueRealizado){
			return false;
		}
		for (Jugador jugador: this.jugadores){
			if (jugador == this.jugadorActual()){
				continue;
			}
			for (Personaje personaje: this.jugadorActual().getEquipo().getMiembros().values()){
				for (Personaje enemigo: jugador.getEquipo().getMiembros().values()){
					try {
						personaje.puedeAtacarA(enemigo);
						return true;
					} catch (AtaqueNoPosible e) {
					}
				}
			}
		}
		return false;
	}


	private boolean puedeMover() {
		if (this.movimientoRealizado){
			return false;
		}
		for (Personaje personaje: this.jugadorActual().getEquipo().getMiembros().values()){
			if (this.tablero.tieneMovimientoPosible(personaje)){
				return true;
			}
		}
		return false;
	}
	
	public Jugador jugadorActual(){
		return jugadores.element();
	}
	
	private void terminarAtaque (){
		this.ataqueRealizado = true;
		this.comprobarTurnoTerminado();
	}

}
	
