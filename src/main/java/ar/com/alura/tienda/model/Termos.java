package ar.com.alura.tienda.model;

import javax.persistence.Entity;

@Entity
public class Termos extends Producto {

	private String tipos;
	private int material;

	public Termos() {

	}

	public Termos(String tipos, int material) {
		this.tipos = tipos;
		this.material = material;
	}
	
//	getters / setters
	
	public String getTipos() {
		return tipos;
	}

	public void setTipos(String tipos) {
		this.tipos = tipos;
	}

	public int getPaginas() {
		return material;
	}

	public void setPaginas(int material) {
		this.material = material;
	}

}