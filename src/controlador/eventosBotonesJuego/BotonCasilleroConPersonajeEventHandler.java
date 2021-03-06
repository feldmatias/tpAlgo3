package controlador.eventosBotonesJuego;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modelo.excepciones.PersonajeNoSeleccionable;
import modelo.juego.DragonBall;
import modelo.personajes.Personaje;
import vista.VistaJuego;
import vista.botones.BotonInvisible;

public class BotonCasilleroConPersonajeEventHandler implements EventHandler<MouseEvent> {

	private DragonBall juego;
	private Personaje personaje;
	private Text acciones;
	private VistaJuego vista;
	private BotonInvisible boton;
	
	public BotonCasilleroConPersonajeEventHandler(DragonBall juego,  Personaje personaje, Text informacionAcciones, VistaJuego vista, BotonInvisible boton) {
		this.juego = juego;
		this.personaje = personaje;
		this.acciones = informacionAcciones;
		this.vista = vista;
		this.boton = boton;
	}



	@Override
	public void handle(MouseEvent event) {
		try {
			juego.jugadorActualSeleccionarPersonaje(personaje);
			boton.reproducirSonidoSeleccion();
			vista.actualizarVista();
		} catch (PersonajeNoSeleccionable e) {
			acciones.setText("No puede seleccionar a ese personaje");
			boton.reproducirSonidoError();
		}
	}

}
