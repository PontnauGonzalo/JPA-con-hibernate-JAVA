package ar.com.alura.tienda.dao;

import javax.persistence.EntityManager;

import ar.com.alura.tienda.model.Categoria;

public class CategoriaDao {

	private EntityManager em;

	public CategoriaDao(EntityManager em) {
		this.em = em;
	}

	public void guardar(Categoria categoria) {
		this.em.persist(categoria);
	}

	public void actualizar(Categoria categoria) {
		this.em.merge(categoria);
	}

	public void remover(Categoria categoria) {
		categoria = this.em.merge(categoria);
		this.em.remove(categoria);
	}

	public Categoria consultaPorNombre(String nombre) {
		String jpql = " SELECT C FROM Categoria AS C WHERE C.nombre=:nombre ";
		return em.createQuery(jpql, Categoria.class).setParameter("nombre", nombre).getSingleResult();
	}

}