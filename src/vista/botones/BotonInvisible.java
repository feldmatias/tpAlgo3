package vista.botones;


import java.util.Timer;
import java.util.TimerTask;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vista.ConstantesPantalla;
import vista.LibreriaSonidos;

public abstract class BotonInvisible extends StackPane{

	private Rectangle boton;
	private Node imagenFondo;
	private LibreriaSonidos sonidos;
	
	
	public BotonInvisible(Node imagenFondo,LibreriaSonidos sonidos){
		this.imagenFondo = imagenFondo;
		this.getChildren().add(imagenFondo);
		this.sonidos = sonidos;
		this.crearBoton();
	}


	private void crearBoton() {
		boton = new Rectangle(ConstantesPantalla.anchoBotonCasillero,ConstantesPantalla.altoBotonCasillero);
		boton.setFill(Color.TRANSPARENT);
		boton.setStroke(Color.BLACK);
		this.getChildren().add(boton);
		
		this.setEfectoMouseEntered();
		
	}
	
	protected void setEfectoMouseEntered() {
		DropShadow sombra = new DropShadow(50,Color.WHITE);
		sombra.setInput(new Glow());
		
		this.setOnMouseEntered(evento -> {
			boton.setFill(this.getColorMouseEntered());
			boton.setOpacity(0.5);
			boton.setEffect(sombra);
		});
		
		this.setOnMouseExited(evento2 -> {
			boton.setFill(Color.TRANSPARENT);
			boton.setEffect(null);
			
		});
		
	}


	protected abstract Color getColorMouseEntered() ;


	public void habilitar(){
		this.setDisable(false);
		
	}
	
	public void deshabilitar(){
		this.setDisable(true);
		
	}
	
	public void setOnAction(EventHandler<MouseEvent> evento){
		this.setOnMouseClicked(evento);
	}
	
	protected void seleccionar(){
		 DropShadow sombra= new DropShadow( 1, Color.AQUA );
		 sombra.setWidth(ConstantesPantalla.anchoBotonCasillero / 2);
		 sombra.setHeight(ConstantesPantalla.altoBotonCasillero);
		 sombra.setSpread(0.5);
		 imagenFondo.setEffect(sombra);
	}
	
	public void parpadear(Color color){
		boton.setOpacity(0.5);
		for (int i = 0; i < 100; i++){
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask(){
            public void run() {
                if (boton.getFill() == color){
                	boton.setFill(Color.TRANSPARENT);
                }else {
                	boton.setFill(color);
                }
            }
		};
		timer.scheduleAtFixedRate(timerTask, 0,100);
			
		}
	}

	public void reproducirSonidoError(){
		this.sonidos.reproducirSonidoError();
	}
	
	public void reproducirSonidoSeleccion() {
		this.sonidos.reproducirSonidoSeleccion();
	}
	
	public void reproducirSonidoTransformacion() {
		this.sonidos.reproducirSonidoTransformacion();
	}
	
	public void reproducirSonidoMovimiento(){
		this.sonidos.reproducirSonidoMovimiento();
	}

	public void reproducirSonidoAtaque(){
		this.sonidos.reproducirSonidoAtaque();
	}
	
	public void reproducirSonidoAtaqueEspecial(){
		this.sonidos.reproducirSonidoAtaqueEspecial();
	}

	public void reproducirSonidoAgarrarConsumible() {
		this.sonidos.reproducirSonidoAgarrarConsumible();
	}

	
}
