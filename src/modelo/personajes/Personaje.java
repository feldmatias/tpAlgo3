package modelo.personajes;

import java.util.ArrayList;
import java.util.List;

import modelo.consumibles.Efecto;
import modelo.excepciones.AtaqueNoPosible;
import modelo.excepciones.MovimientoNoPosible;
import modelo.excepciones.PosicionFueraDeRango;
import modelo.excepciones.TransformacionNoPosible;
import modelo.juego.Equipo;
import modelo.personajes.modos.Modo;
import modelo.personajes.modos.ModoInmovilizado;
import modelo.tablero.Posicion;
import modelo.tablero.Tablero;

public abstract class Personaje {
	
	private String nombre;
	private int vidaInicial;
	private float vidaActual;
	private int ki;
	private Equipo equipo;
	private Modo modoActual;
	private Tablero tablero;
	private List<Efecto> listadoEfectos;
	
	public Personaje(String nombre,int vidaInicial, Modo modoInicial, Tablero tablero){
		this.nombre = nombre;
		this.vidaInicial = vidaInicial;
		this.vidaActual = vidaInicial;
		this.ki = 0;
		this.modoActual = modoInicial;
		this.tablero = tablero;
		this.listadoEfectos = new ArrayList<Efecto>();
	}
	
	public void setEquipo(Equipo equipo){
		this.equipo = equipo;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public int getPoderPelea() {
		return this.modoActual.getPoderPelea();
	}

	public int getDistanciaAtaque() {
		return this.modoActual.getDistanciaAtaque();
	}
	
	public int getVelocidad() {
		return this.modoActual.getVelocidad();
	}
	
	public float getPorcentajeVida(){
		return (100 * this.vidaActual / this.vidaInicial);
	}
	
	public int getKi() {
		return this.ki;
	}
	
	public Equipo getEquipo() {
		return this.equipo;
	}
	
	
	public void generarKi(){
		int kiGenerado = 5;
		this.ki += kiGenerado;
	}
	
	public void restarKi(int kiPerdido) {
		this.ki -= kiPerdido;
	}
	
	public void atacarAPersonaje(Personaje enemigo) throws AtaqueNoPosible{
		if (this.equipo.pertenece(enemigo)){
			throw new AtaqueNoPosible();
		}
		if (!this.alcanzaDistanciaAtaque(enemigo)){
			throw new AtaqueNoPosible();
		}
		enemigo.recibirAtaque(this.getPoderPelea());
	}

	private boolean alcanzaDistanciaAtaque(Personaje enemigo) {
		return (this.tablero.distanciaEntre(this,enemigo) <= this.getDistanciaAtaque());
	}

	public void recibirAtaque(int poderPeleaEnemigo) {
		if (this.getPoderPelea() > poderPeleaEnemigo){
			poderPeleaEnemigo *= 0.8; //Disminuye 20%
		}
		this.vidaActual -= poderPeleaEnemigo;
		this.comprobarSiEstaMuerto();
	}
	
	public void mover(Posicion nuevaPosicion) throws MovimientoNoPosible{
		if (! this.puedeMoverse(nuevaPosicion)){
			throw new MovimientoNoPosible();
		}
		try {
			this.tablero.reposicionarPersonaje(this , nuevaPosicion);
		}
		catch(PosicionFueraDeRango e){
			throw new MovimientoNoPosible();
		}
	}
	
	private boolean puedeMoverse(Posicion nuevaPosicion){
		
		return this.tablero.personajePuedeMoverse(this, nuevaPosicion);
		
	}
	
	public void transformar() throws TransformacionNoPosible{
		if (!this.modoActual.puedeTransformarse(this)){
			throw new TransformacionNoPosible();
		}
		this.modoActual = this.modoActual.transformar(this);
	}
	
	private void comprobarSiEstaMuerto(){
		if (this.vidaActual == 0){
			this.equipo.personajeMuerto(this);
			this.tablero.personajeMuerto(this);
		}
	}
	
	protected boolean comprobarKiNecesario(int kiNecesario){
		return this.ki >= kiNecesario;
	}
	
	public void empezarTurno(){
		
		this.modoActual.empezarTurno(this);
	}
	
	public void sumarEfecto(Efecto efecto){
		this.vidaActual= efecto.regenerarVida();
		this.listadoEfectos.add(efecto);
	}
	
	public void inmovilizar(){
		this.modoActual = new ModoInmovilizado(this.modoActual);
	}

	public abstract Modo transformarAModoTransformado();

	public abstract boolean puedeTransformarseAModoTransformado();

	public abstract Modo transformarAModoFinal();

	public abstract boolean puedeTransformarseAModoFinal();


}
