package pruebas;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import modelo.excepciones.AtaqueNoPosible;
import modelo.excepciones.EquipoNoDisponible;
import modelo.excepciones.MovimientoNoPosible;
import modelo.excepciones.PersonajeNoSeleccionable;
import modelo.excepciones.TransformacionNoPosible;
import modelo.juego.DragonBall;
import modelo.juego.Posicion;
import modelo.personajes.Personaje;
import modelo.utilidades.Constantes;

public class TestIntegracion {
	
	@Test
	public void integracionNuevoJuegoConDosJugadoresYPersonajesDistribuidos() {
		
		try{
			DragonBall dragonBall = new DragonBall();
			dragonBall.establecerEquipoJugador1(Constantes.GUERREROS);
			dragonBall.establecerEquipoJugador2(Constantes.ENEMIGOS);
			dragonBall.iniciar();
			
			String unNombre = dragonBall.getJugador1().getEquipo().getNombre();
			String otroNombre = dragonBall.getJugador2().getEquipo().getNombre();
			Assert.assertEquals("La prueba pas�: el jugador 1 es del equipo Guerreros", Constantes.GUERREROS, unNombre);
			Assert.assertEquals("La prueba pas�: el jugador 2 es del equipo Enemigos", Constantes.ENEMIGOS, otroNombre);
			
			List<Personaje> miembrosGuerreros = dragonBall.getJugador1().getEquipo().getMiembros();
			for (Personaje personaje : miembrosGuerreros){
				boolean flag = personaje.getNombre() == "Goku";
				flag = flag || personaje.getNombre() == "Gohan";
				flag = flag || personaje.getNombre() == "Piccolo";
				Assert.assertTrue(flag);
			}
			
			List<Personaje> miembrosEnemigos = dragonBall.getJugador2().getEquipo().getMiembros();
			for (Personaje personaje : miembrosEnemigos){
				boolean flag = personaje.getNombre() == "Cell";
				flag = flag || personaje.getNombre() == "Freezer";
				flag = flag || personaje.getNombre() == "Majin Boo";
				Assert.assertTrue(flag);
			}
			
			Posicion posicionEsperadaGuerreros1 = Constantes.POS_INICIAL1;
			Posicion posicionEsperadaGuerreros2 = posicionEsperadaGuerreros1.sumarPosicion( new Posicion (0, 1) );
			Posicion posicionEsperadaGuerreros3 = posicionEsperadaGuerreros1.sumarPosicion( new Posicion (0, -1) );
			for (Personaje personaje : miembrosGuerreros){
				Posicion posicionReal = dragonBall.getTablero().getPosicionPersonaje(personaje);
				boolean flag = posicionReal.equals(posicionEsperadaGuerreros1);
				flag = flag || posicionReal.equals(posicionEsperadaGuerreros2);
				flag = flag || posicionReal.equals(posicionEsperadaGuerreros3);
				Assert.assertTrue(flag);
			}
			Posicion posicionEsperadaEnemigos1 = Constantes.POS_INICIAL2;
			Posicion posicionEsperadaEnemigos2 = posicionEsperadaEnemigos1.sumarPosicion( new Posicion (0, 1) );
			Posicion posicionEsperadaEnemigos3 = posicionEsperadaEnemigos1.sumarPosicion( new Posicion (0, -1) );
			for (Personaje personaje : miembrosEnemigos){
				Posicion posicionReal = dragonBall.getTablero().getPosicionPersonaje(personaje);
				boolean flag = posicionReal.equals(posicionEsperadaEnemigos1);
				flag = flag || posicionReal.equals(posicionEsperadaEnemigos2);
				flag = flag || posicionReal.equals(posicionEsperadaEnemigos3);
				Assert.assertTrue(flag);
			}
			
			
		}catch(EquipoNoDisponible e){
			Assert.fail("La prueba fallo, los equipos deberian estar disponibles");
		}
	}
	
	@Test
	public void testIntegracionJugarJuego() throws  MovimientoNoPosible, PersonajeNoSeleccionable, AtaqueNoPosible, TransformacionNoPosible{
		
		//La prueba falla si lanza una excepcion
		
		DragonBall juego = new DragonBall();
		
		juego.establecerEquipoJugador1(Constantes.GUERREROS);
		juego.establecerEquipoJugador2(Constantes.ENEMIGOS);
		juego.iniciar();
		
		if (juego.getJugadorActual() == juego.getJugador2()){
			juego.jugadorActualTerminarTurno();
		}
		
		Personaje goku = juego.getJugador1().getEquipo().getMiembros().get(0);
		Personaje gohan = juego.getJugador1().getEquipo().getMiembros().get(1);
		Personaje cell = juego.getJugador2().getEquipo().getMiembros().get(0);
		Personaje freezer = juego.getJugador2().getEquipo().getMiembros().get(1);
		Personaje majinBoo = juego.getJugador2().getEquipo().getMiembros().get(2);
		
		//Turno guerreros
		juego.jugadorActualSeleccionarPersonaje(goku);
		juego.jugadorActualMoverAPosicion(new Posicion (2,3));
		juego.jugadorActualTerminarTurno();
		
		//Turno enemigos
		juego.jugadorActualSeleccionarPersonaje(freezer);
		juego.jugadorActualMoverAPosicion(new Posicion (4,6));
		juego.jugadorActualTerminarTurno();
		
		//Turno guerreros
		juego.jugadorActualMoverAPosicion(new Posicion(3,1));
		juego.jugadorActualTerminarTurno();
		
		//Turno enemigos
		juego.jugadorActualMoverAPosicion(new Posicion (5,3));
		juego.jugadorActualTerminarTurno();
		
		//Turno guerreros
		juego.jugadorActualAtacarAEnemigo(freezer);
		juego.jugadorActualTerminarTurno();
		
		//Turno enemigos
		juego.jugadorActualAtacarAEnemigo(goku);
		juego.jugadorActualTerminarTurno();
		
		//Turno guerreros
		juego.jugadorActualMoverAPosicion(new Posicion (5,1));
		juego.jugadorActualAtacarAEnemigo(freezer);
		
		//Turno enemigos
		Assert.assertEquals(juego.getJugador2(), juego.getJugadorActual());
		juego.jugadorActualAtacarAEnemigo(goku);
		juego.jugadorActualMoverAPosicion(new Posicion (5,2));
		
		//Turno guerreros
		juego.jugadorActualTransformar();
		juego.jugadorActualAtacarAEnemigo(freezer);
		juego.jugadorActualMoverAPosicion(new Posicion (2,4));
		
		//Turno enemigos
		juego.jugadorActualTerminarTurno();
		
		//Turno guerreros
		juego.jugadorActualAtacarAEnemigo(freezer);
		juego.jugadorActualSeleccionarPersonaje(gohan);
		juego.jugadorActualMoverAPosicion(new Posicion(2,5));
		
		//Turno Enemigos
		juego.jugadorActualSeleccionarPersonaje(cell);
		juego.jugadorActualMoverAPosicion(new Posicion(6,4));
		juego.jugadorActualTerminarTurno();
		
		//Turno Guerreros
		juego.jugadorActualTransformar();
		juego.jugadorActualMoverAPosicion(new Posicion (4,5));
		juego.jugadorActualTerminarTurno();
		
		//Turno Enemigos
		juego.jugadorActualRealizarAtaqueEspecial(gohan);
		juego.jugadorActualSeleccionarPersonaje(majinBoo);
		juego.jugadorActualMoverAPosicion(new Posicion(6,3));
		
		//Turno guerreros
		juego.jugadorActualTerminarTurno();
		
		//Turno Enemigos
		juego.jugadorActualRealizarAtaqueEspecial(gohan);
		juego.jugadorActualTerminarTurno();
		
		//Turno Guerreros
		try{
			juego.jugadorActualAtacarAEnemigo(majinBoo);
			Assert.fail("No deberia atacar, esta inmovilizado");
		} catch (AtaqueNoPosible e){
		}
		juego.jugadorActualSeleccionarPersonaje(goku);
		juego.jugadorActualRealizarAtaqueEspecial(majinBoo);
		
	}
	
}
