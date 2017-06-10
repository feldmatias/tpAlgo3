package modelo.personajes;

import modelo.personajes.modos.Modo;
import modelo.personajes.modos.ModoNormal;
import modelo.personajes.modos.PrimeraTransformacion;
import modelo.personajes.modos.SegundaTransformacion;
import modelo.tablero.Tablero;

public class Piccolo extends Personaje {

	public Piccolo(Tablero tablero) {
		super("Piccolo", 500, new ModoNormal(20,2,2), new AtaquePotenciador(10,1.25), tablero, 20 ,0);
	}
	
	public Modo realizarPrimeraTransformacion(){
		super.restarKiPrimeraTransformacion();
		return new PrimeraTransformacion(40,4,3);
	}

	@Override
	public Modo realizarSegundaTransformacion() {
		super.restarKiSegundaTransformacion();
		return new SegundaTransformacion(60,6,4);
	}
	
	@Override
	public boolean puedeRealizarSegundaTransformacion() {

		for (Personaje personaje: this.getEquipo().getMiembros()){
			if (personaje.getNombre() == "Gohan"){
				return personaje.getPorcentajeVida() < 20;
			}
		}
		return true; //Gohan esta muerto
	}

}
