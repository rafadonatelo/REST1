package com.rest.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.rest.util.SimpleValidate;

@Stateless
public class GenericsSearchDAO<T> {

	@PersistenceContext(unitName = "restPoll")
	private EntityManager em;

	public List<T> carregarPesquisaLazy(int startingAt,
			int maxPerPage, String fieldOrder, String order,
			Map<String, Object> parametros, IGenericEntity entity) {

		String jpql = "SELECT entity FROM " + entity.getClass().getName()
				+ " entity where entity.id > 0";
		
		Set<String> listParametros = null;
		int indice = 0;
		if (parametros != null) {
			listParametros = parametros.keySet();

			indice = 0;
			for (Iterator<String> parametro = listParametros.iterator(); parametro
					.hasNext();) {
				String campo = parametro.next();
				String args = "args_" + indice;
				Object valueArgs = parametros.get(campo);
				if (valueArgs.getClass().equals(String.class)) {
					jpql += " AND entity." + campo + " LIKE :" + args;
				} else {
					jpql += " AND entity." + campo + " = :" + args;
				}

				indice++;
			}
		}
		jpql = orderParametroHQL(fieldOrder, order, jpql);

		Query q = em.createQuery(jpql, entity.getClass());

		q.setFirstResult(startingAt);
		q.setMaxResults(maxPerPage);
		if (parametros != null) {
			indice = 0;
			for (Iterator<String> parametro = listParametros.iterator(); parametro
					.hasNext();) {
				String campo = parametro.next();
				String args = "args_" + indice;
				Object valueArgs = parametros.get(campo);
				if (valueArgs.getClass().equals(String.class)) {
					q.setParameter(args, "%" + valueArgs.toString() + "%");
				} else {
					q.setParameter(args, valueArgs);
				}

			}
		}
		@SuppressWarnings("unchecked")
		List<T> entities = q.getResultList();
		if (SimpleValidate.isNullOrEmpty(entities)) {
			return new ArrayList<T>();
		}
		return entities;

	}

	@SuppressWarnings("unchecked")
	public List<IGenericEntity> carregarPesquisaUsuarioLazy(int startingAt,
			int maxPerPage, String fieldOrder, String order,
			Map<String, Object> parametros) {

		String jpql = "SELECT entity FROM Usuario entity where entity.login <> 'master' ";

		// Filtros
		Set<String> listParametros = parametros.keySet();
		jpql = setarParametroHQL(jpql, listParametros);

		// Ordenacao
		jpql = orderParametroHQL(fieldOrder, order, jpql);

		Query q = em.createQuery(jpql);

		q.setFirstResult(startingAt);
		q.setMaxResults(maxPerPage);

		iterarArgumentoHQL(parametros, listParametros, q);

		List<IGenericEntity> entities = q.getResultList();
		if (SimpleValidate.isNullOrEmpty(entities)) {
			return new ArrayList<IGenericEntity>();
		}
		return entities;

	}

	private static void iterarArgumentoHQL(Map<String, Object> parametros,
			Set<String> listParametros, Query q) {
		int indice = 0;
		for (Iterator<String> parametro = listParametros.iterator(); parametro
				.hasNext();) {
			String campo = parametro.next();
			String args = "args_" + indice;
			q.setParameter(args, "%" + parametros.get(campo) + "%");
			indice++;
		}
	}

	private static String orderParametroHQL(String fieldOrder, String order,
			String jpql) {
		if (!fieldOrder.isEmpty())
			jpql += " ORDER BY " + fieldOrder + " " + order;
		return jpql;
	}

	private static String setarParametroHQL(String jpql,
			Set<String> listParametros) {
		int indice = 0;
		for (Iterator<String> parametro = listParametros.iterator(); parametro
				.hasNext();) {
			String campo = parametro.next();
			String args = "args_" + indice;
			jpql += " AND entity." + campo + " LIKE :" + args;
			indice++;
		}
		return jpql;
	}

}
