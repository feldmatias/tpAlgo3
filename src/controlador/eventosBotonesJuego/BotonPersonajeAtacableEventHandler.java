package controlador.eventosBotonesJuego;


import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import modelo.excepciones.AtaqueNoPosible;
import modelo.juego.DragonBall;
import modelo.personajes.Personaje;
import vista.VistaJuego;
import vista.botones.BotonInvisible;

public class BotonPersonajeAtacableEventHandler implements EventHandler<MouseEvent> {

	private DragonBall juego;
	private Personaje personaje;
	private Text acciones;
	private VistaJuego vista;
	private BotonInvisible boton;

	public BotonPersonajeAtacableEventHandler(DragonBall juego, Personaje personaje, Text acciones, VistaJuego vista, BotonInvisible boton) {
		this.juego = juego;
		this.personaje = personaje;
		this.acciones = acciones;
		this.vista = vista;
		this.boton = boton;
	}



	@Override
	public void handle(MouseEvent event) {
		try {
			
			
			juego.jugadorActualAtacarAEnemigo(personaje);
		
			boton.reproducirSonidoAtaque();
			boton.parpadear(Color.RED);
			PauseTransition pausa = new PauseTransition(Duration.seconds(1));
			pausa.setOnFinished(finPausa -> {
				vista.actualizarVista();
			});
			pausa.play();
		} catch (AtaqueNoPosible error) {
			acciones.setText("No puede atacar: " + error.getMensaje());
			boton.reproducirSonidoError();
		}
	}

}
