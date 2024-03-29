package es.upm.dit.isst.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import es.upm.dit.isst.persistence.model.Circunscripcion2016;

public class Circunscripcion2016DAOImplementation implements Circunscripcion2016DAO{
	private static Circunscripcion2016DAOImplementation instance;
	private Circunscripcion2016DAOImplementation() {};
	
	
	public static Circunscripcion2016DAOImplementation getInstance() {
		if(null== instance) {
			instance = new Circunscripcion2016DAOImplementation();
		}
		return instance;
	}
	
	//Para cada operación CRUD se sigue el siguiente patrón:
	// 1. Se abre una sesión de hibernate
	// 2. Hacerlas operaciones protegidas con try catch
	// 3. Se incluyen las operaciones dentro de las transacciones
	// 4. Se cierra la sesión.
	
	@Override
	public void create(Circunscripcion2016 circunscripcion2016) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(circunscripcion2016);
			session.getTransaction().commit();
		} catch (Exception e) {
			// manejar excepciones
		} finally {
			session.close();
		}
		
	}

	@Override
	public Circunscripcion2016 read(int idCirc) {
		Circunscripcion2016 c = null;
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			c = session.get(Circunscripcion2016.class, idCirc);
			session.getTransaction().commit();
		} catch (Exception e) {
			// manejar excepciones
		} finally {
			session.close();
		}
		return c;
	}

	@Override
	public void update(Circunscripcion2016 c) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(c);
			session.getTransaction().commit();
		} catch (Exception e) {
			// manejar excepciones
		} finally {
			session.close();
		}
		
	}

	@Override
	public void delete(Circunscripcion2016 c) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(c);
			session.getTransaction().commit();
		} catch (Exception e) {
			// manejar excepciones
		} finally {
			session.close();
		}
		
	}

	

	@Override
	public List<Circunscripcion2016> readAll() {
		List<Circunscripcion2016> cs = new ArrayList<>();
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			cs.addAll(
					session.createQuery("from Circunscripcion2016").list()
					);
			session.getTransaction().commit();
		} catch (Exception e) {
			// manejar excepciones
		} finally {
			session.close();
		}
		return cs;
	}

	
}
