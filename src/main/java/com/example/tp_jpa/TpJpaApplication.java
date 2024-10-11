package com.example.tp_jpa;

import com.example.tp_jpa.entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TpJpaApplication {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		System.out.println("Estoy funcionando");

		try {
			entityManager.getTransaction().begin();

			// Crear y persistir entidades
			Factura factura1 = new Factura();
			factura1.setNumero(12);
			factura1.setFecha("26/08/2024");

			Domicilio domicilio1 = new Domicilio("Tucuman", 420);
			Cliente cliente1 = new Cliente("Lorenzo", "Molina", 12345);

			cliente1.setDomicilio(domicilio1);
			domicilio1.setCliente(cliente1);
			factura1.setCliente(cliente1);

			Categoria perecederos = new Categoria("Perecederos");
			Categoria lacteos = new Categoria("Lacteos");
			Categoria limpieza = new Categoria("Limpieza");

			Articulo articulo1 = new Articulo(200, "Yogurt", 20);
			Articulo articulo2 = new Articulo(200, "Detergente", 80);

			articulo1.getCategorias().add(perecederos);
			articulo1.getCategorias().add(lacteos);
			lacteos.getArticulos().add(articulo1);
			perecederos.getArticulos().add(articulo1);

			articulo2.getCategorias().add(limpieza);
			limpieza.getArticulos().add(articulo2);

			DetalleFactura detalle1 = new DetalleFactura();
			detalle1.setArticulo(articulo1);
			detalle1.setCantidad(2);
			detalle1.setSubtotal(40);

			articulo1.getDetallesFacturas().add(detalle1);
			factura1.getDetalles().add(detalle1);
			detalle1.setFactura(factura1);

			DetalleFactura detalle2 = new DetalleFactura();
			detalle2.setArticulo(articulo2);
			detalle2.setCantidad(1);
			detalle2.setSubtotal(80);

			articulo2.getDetallesFacturas().add(detalle2);
			factura1.getDetalles().add(detalle2);
			detalle2.setFactura(factura1);

			factura1.setTotal(120);

			entityManager.persist(factura1);

			entityManager.flush();
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();  // Para ver la excepción en detalle
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}
}

