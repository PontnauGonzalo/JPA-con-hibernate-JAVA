package ar.com.alura.tienda.tests;

import ar.com.alura.tienda.utils.JPAUtils;
import ar.com.alura.tienda.dao.CategoriaDao;
import ar.com.alura.tienda.dao.ProductoDao;
import ar.com.alura.tienda.model.Categoria;
import ar.com.alura.tienda.model.CategoriaId;
import ar.com.alura.tienda.model.Producto;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

public class RegistroDeProducto {

	public static void main(String[] args) {
		registrarProducto();
		EntityManager em = JPAUtils.getEntityManager();
		ProductoDao productoDao = new ProductoDao(em);

		Producto producto = productoDao.consultaPorId(1l);
		System.out.println(producto.getNombre());

		BigDecimal precio = productoDao.consultarPrecioPorNombreDeProducto("Canarias Brasilera");
		System.out.println(precio);

		Categoria find = em.find(Categoria.class, new CategoriaId("YERBAS", "AluraH2"));

		System.out.println(find.getNombre());

	}

	private static void registrarProducto() {
		Categoria yerbas = new Categoria("YERBAS");

		Producto yerba = new Producto("Canarias Brasilera", "Molienda fina", new BigDecimal("600"), yerbas);

		EntityManager em = JPAUtils.getEntityManager();
		ProductoDao productoDao = new ProductoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);

		em.getTransaction().begin();

		categoriaDao.guardar(yerbas);
		productoDao.guardar(yerba);

		em.getTransaction().commit();
		em.close();
	}

}