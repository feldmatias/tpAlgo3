package pruebas;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import modelo.juego.Casillero;
import modelo.juego.Posicion;
import modelo.personajes.Goku;
import modelo.personajes.Personaje;

public class CasilleroTest {
	
	private Casillero casillero1;
	private Personaje personaje;
	
	@Before
	public void setUp() {
		personaje = new Goku(null);
		casillero1 = new Casillero(5,0);
	}
	
	@Test
	public void testAlCrearCasilleroEstaVacio(){
		Assert.assertTrue( casillero1.estaVacio() );
	}
	
	@Test
	public void testLuegoDePosicionarPersonajeElCasilleroNoEstaVacio(){
		casillero1.ocupar(personaje);
		Assert.assertFalse( casillero1.estaVacio() );
	}
	
	@Test
	public void testLuegoDeDesocuparCasilleroOcupadoEstaVacio(){
		casillero1.ocupar(personaje);
		casillero1.desocupar();
		Assert.assertTrue(casillero1.estaVacio());
	}
	
	@Test
	public void testCasillerogetPosicionDevuelveLaPosicionCorrecta(){
		Casillero casillero2 = new Casillero(1,2);
		Posicion posEsperada= new Posicion(1,2);
		Posicion posCasillero= casillero2.getPosicion();
		Assert.assertTrue( posEsperada.equals(posCasillero) );
		}
	
	@Test
	public void testDistanciaAOtroCasilleroEsLaCorrecta(){
		Casillero casillero2 = new Casillero(2,4);
		Assert.assertEquals(4, casillero2.distanciaA(casillero1));
	}
	
}