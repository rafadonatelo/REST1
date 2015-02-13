package com.rest.dao;

import java.util.Date;

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

	@Override
	public void insert(Cliente obj) {
		obj.setDataCadastro(new Date());
		super.insert(obj);
	}
	
	@Override
	public Cliente update(Cliente obj) {
		obj.setDataUltimaAtualizacao(new Date());
		return super.update(obj);
	}
}
