package com.rest.dao;

import javax.ejb.Stateless;

import com.rest.model.Cliente;

@Stateless
public class ClienteDAO extends GenericDAOImpl<Cliente> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClienteDAO() {
		super(Cliente.class);
	}
}
