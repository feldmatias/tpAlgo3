package vista.controlador;

import java.util.List;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import modelo.juego.DragonBall;
import modelo.personajes.Personaje;
import vista.BotonInvisible;
import vista.VistaJuego;

public class BotonAtacarEventHandler implements EventHandler<MouseEvent> {

	private Label labelAcciones;
	private Map<Personaje, BotonInvisible> personajes;
	private List<BotonInvisible> casilleros;
	private DragonBall juego;
	private VistaJuego vista;

	public BotonAtacarEventHandler(Label labelAcciones, List<BotonInvisible> botonesCasilleros,
			Map<Personaje, BotonInvisible> botonesPersonajes, DragonBall juego, VistaJuego vista) {
		
		this.labelAcciones = labelAcciones;
		this.casilleros = botonesCasilleros;
		this.personajes = botonesPersonajes;
		this.juego = juego;
		this.vista = vista;
	}




	@Override
	public void handle(MouseEvent event) {
		for (BotonInvisible boton: casilleros){
			boton.deshabilitar();
		}
		for (Personaje personaje: personajes.keySet()){
			BotonInvisible boton = personajes.get(personaje);
			boton.habilitar();
			boton.setOnAction(new BotonPersonajeAtacableEventHandler(juego,personaje, labelAcciones, vista, boton));
		}
		labelAcciones.setText("Seleccione al enemigo a atacar");
	}

}
