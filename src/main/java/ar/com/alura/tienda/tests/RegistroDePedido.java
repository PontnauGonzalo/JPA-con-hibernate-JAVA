package ar.com.alura.tienda.tests;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import ar.com.alura.tienda.model.ItemsPedido;
import ar.com.alura.tienda.model.Producto;
import ar.com.alura.tienda.model.Pedido;
import ar.com.alura.tienda.model.Categoria;
import ar.com.alura.tienda.model.Cliente;
import ar.com.alura.tienda.utils.JPAUtils;
import ar.com.alura.tienda.vo.RelatorioDeVenta;
import ar.com.alura.tienda.dao.ProductoDao;
import ar.com.alura.tienda.dao.CategoriaDao;
import ar.com.alura.tienda.dao.ClienteDao;
import ar.com.alura.tienda.dao.PedidoDao;

public class RegistroDePedido {

	public static void main(String[] args) throws FileNotFoundException {
		
		registrarProducto();

		EntityManager em = JPAUtils.getEntityManager();

		ProductoDao productoDao = new ProductoDao(em);
		Producto producto = productoDao.consultaPorId(1l);

		ClienteDao clienteDao = new ClienteDao(em);
		PedidoDao pedidoDao = new PedidoDao(em);

		Cliente cliente = new Cliente("Gonzalo", "45233290");
		Pedido pedido = new Pedido(cliente);
		pedido.agregarItems(new ItemsPedido(5, producto, pedido));

		em.getTransaction().begin();

		clienteDao.guardar(cliente);
		pedidoDao.guardar(pedido);

		em.getTransaction().commit();

		BigDecimal valorTotal = pedidoDao.valorTotalVendido();
		System.out.println("Valor Total: " + valorTotal);

		List<RelatorioDeVenta> relatorio = pedidoDao.relatorioDeVentasVO();

		relatorio.forEach(System.out::println);

	}

	private static void registrarProducto() {
		Categoria yerbas = new Categoria("YERBA");

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