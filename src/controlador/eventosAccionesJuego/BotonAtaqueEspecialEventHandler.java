package controlador.eventosAccionesJuego;

import java.util.List;
import java.util.Map;

import controlador.eventosBotonesJuego.BotonPersonajeAtacableEspecialEventHandler;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import modelo.juego.DragonBall;
import modelo.personajes.Personaje;
import vista.VistaJuego;
import vista.botones.BotonInvisible;

public class BotonAtaqueEspecialEventHandler implements EventHandler<MouseEvent> {

	private Text acciones;
	private List<BotonInvisible> casilleros;
	private Map<Personaje, BotonInvisible> personajes;
	private DragonBall juego;
	private VistaJuego vista;

	public BotonAtaqueEspecialEventHandler(Text informacionAcciones, List<BotonInvisible> botonesCasilleros,
			Map<Personaje, BotonInvisible> botonesPersonajes, DragonBall juego, VistaJuego vista) {

		this.acciones = informacionAcciones;
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
			boton.setOnAction(new BotonPersonajeAtacableEspecialEventHandler(juego,personaje, acciones, vista, boton));
		}
		acciones.setText("Seleccione al enemigo a atacar");
	}

}
