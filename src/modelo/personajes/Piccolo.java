package modelo.personajes;

import modelo.excepciones.TransformacionNoPosible;
import modelo.juego.Tablero;
import modelo.personajes.modos.Modo;
import modelo.personajes.modos.ModoNormal;
import modelo.personajes.modos.PrimeraTransformacion;
import modelo.personajes.modos.SegundaTransformacion;
import modelo.utilidades.Constantes;

public class Piccolo extends Personaje {
	
	public static final int vidaInicial = 500;
	public static final int poderPeleaNormal = 20;
	public static final int poderPeleaPrimerTransformacion = 40;
	public static final int poderPeleaSegundaTransformacion = 60;
	public static final int distanciaAtaqueNormal=2;
	public static final int distanciaAtaquePrimerTransformacion=4;
	public static final int distanciaAtaqueSegundaTransformacion=6;
	public static final int velocidadNormal = 2;
	public static final int velocidadPrimerTranformacion = 3;
	public static final int velocidadSegundaTranformacion = 4;
	public static final int porcentajeVidaGohanParaSegundaTransformacion = 20;

	public Piccolo(Tablero tablero) {
		super(vidaInicial, new ModoNormal(poderPeleaNormal,distanciaAtaqueNormal,velocidadNormal, "Piccolo"), new AtaquePotenciador(10,1.25), tablero, 20 ,0);
	}
	
	public Modo realizarPrimeraTransformacion(){
		super.restarKiPrimeraTransformacion();
		return new PrimeraTransformacion(poderPeleaPrimerTransformacion,distanciaAtaquePrimerTransformacion,velocidadPrimerTranformacion, "Piccolo Fortalecido");
	}

	@Override
	public Modo realizarSegundaTransformacion() {
		super.restarKiSegundaTransformacion();
		return new SegundaTransformacion(poderPeleaSegundaTransformacion,distanciaAtaqueSegundaTransformacion,velocidadSegundaTranformacion, "Piccolo Protector");
	}
	
	@Override
	public void puedeRealizarSegundaTransformacion() throws TransformacionNoPosible {

		for (Personaje personaje: this.getEquipo().getMiembros()){
			if (personaje.getNombre() == "Gohan"){
				if (personaje.getPorcentajeVida() >= porcentajeVidaGohanParaSegundaTransformacion){
					throw new TransformacionNoPosible(Constantes.ErrorTransformacionPiccolo);
				}
			}
		}
	}

}
