package ar.com.alura.tienda.tests;

import java.io.File;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import ar.com.alura.tienda.dao.CategoriaDao;
import ar.com.alura.tienda.dao.ClienteDao;
import ar.com.alura.tienda.dao.PedidoDao;
import ar.com.alura.tienda.dao.ProductoDao;
import ar.com.alura.tienda.model.Categoria;
import ar.com.alura.tienda.model.Cliente;
import ar.com.alura.tienda.model.ItemsPedido;
import ar.com.alura.tienda.model.Pedido;
import ar.com.alura.tienda.model.Producto;
import ar.com.alura.tienda.utils.JPAUtils;

public class LoadRecords {

	public static void cargarRegistros() throws FileNotFoundException {

		EntityManager em = JPAUtils.getEntityManager();
		CategoriaDao categoriaDao = new CategoriaDao(em);
		ProductoDao productoDao = new ProductoDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		PedidoDao pedidoDao = new PedidoDao(em);
		em.getTransaction().begin();

		loadCategoria("categoria", categoriaDao, em);

		loadProducto("producto", productoDao, categoriaDao, em);

		loadCliente("cliente", clienteDao, em);

		List<Cliente> clientesList = clienteDao.consultarTodos();
		List<Pedido> pedidoList = new ArrayList<>();

		for (Cliente cl : clientesList) {
			pedidoList.add(new Pedido(cl));
		}

		for (int i = 0; i < pedidoList.size(); i++) {
			pedidoList.get(i)
					.agregarItems(new ItemsPedido(i + 1, productoDao.consultaPorId((long) (i + 1)), pedidoList.get(i)));
			pedidoDao.guardar(pedidoList.get(i));
		}

		em.getTransaction().commit();
		em.close();

	}

	private static void loadProducto(String type, ProductoDao productoDao, CategoriaDao categoriaDao, EntityManager em)
			throws FileNotFoundException {
		List<String> productosTxt = readFile(type);
		for (int i = 0; i < productosTxt.size(); i++) {
			String[] line = productosTxt.get(i).split(";");
			if (line.length > 1) {
				Categoria categoria = categoriaDao.consultaPorNombre(line[3]);
				Producto producto = new Producto(line[4], line[0], new BigDecimal(line[1]), categoria);
				productoDao.guardar(producto);
				em.flush();
			}
		}
	}

	private static void loadCategoria(String type, CategoriaDao categoriaDao, EntityManager em)
			throws FileNotFoundException {
		List<String> categoriasTxt = readFile(type);
		for (int i = 0; i < categoriasTxt.size(); i++) {
			String[] line = categoriasTxt.get(i).split(";");
			if (line.length == 1) {
				Categoria categoria = new Categoria(categoriasTxt.get(i));
				categoriaDao.guardar(categoria);
				em.flush();
			}
		}
	}

	private static void loadCliente(String type, ClienteDao clienteDao, EntityManager em) throws FileNotFoundException {
		List<String> clientesTxt = readFile(type);
		for (int i = 0; i < clientesTxt.size(); i++) {
			String[] line = clientesTxt.get(i).split("~");
			System.out.println(line[0] + line[1]);
			if (line.length > 1) {
				Cliente cliente = new Cliente(line[0], line[1]);
				clienteDao.guardar(cliente);
				em.flush();
			}
		}
	}

	private static List<String> readFile(String type) throws FileNotFoundException {
		File file = new File(
				"C:\\Users\\Andres Pontnau\\Desktop\\ORACLE ONE\\BACKEND\\Spring Boot\\tienda\\src\\main\\resources\\utils\\"
						+ type + ".txt");
		Scanner scan = new Scanner(file);
		List<String> pedido = new ArrayList<>();
		while (scan.hasNextLine()) {
			pedido.add(scan.nextLine());
		}
		scan.close();
		return pedido;
	}
}