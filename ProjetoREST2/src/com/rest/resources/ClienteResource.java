package com.rest.resources;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.rest.dao.ClienteDAO;
import com.rest.model.Cliente;
import com.rest.model.PaginatedListWrapper;

@Path("clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {
	@EJB
	private ClienteDAO dao;
	private Cliente c = new Cliente();

	@GET
	@Path("{id}")
	public Cliente obterClientePorID(@PathParam("id") Long id) {
		return dao.find(id);
	}

	@POST
	public Cliente salvarCliente(Cliente cliente) {
		if (c.getId() == null) {
			dao.insert(cliente);
		} else {
			this.c = dao.update(cliente);
		}
		return this.c;
	}

	@DELETE
	@Path("{id}")
	public void excluirCliente(@PathParam("id") Long id) {
		dao.remove(obterClientePorID(id));
	}
	
	@GET
	public PaginatedListWrapper<Cliente> listPersons(@DefaultValue("1") @QueryParam("page") Integer page, @DefaultValue("id") @QueryParam("sortFields") String sortFields,
			@DefaultValue("asc") @QueryParam("sortDirections") String sortDirections) {
		PaginatedListWrapper<Cliente> paginatedListWrapper = new PaginatedListWrapper<Cliente>();
		paginatedListWrapper.setCurrentPage(page);
		paginatedListWrapper.setSortFields(sortFields);
		paginatedListWrapper.setSortDirections(sortDirections);
		paginatedListWrapper.setPageSize(10);
		return ;
	}
	
	private PaginatedListWrapper<Cliente> findPersons(PaginatedListWrapper<Cliente> wrapper) {
		wrapper.setTotalResults(dao.count().intValue());
		int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
		wrapper.setList(service.findPersons(start, wrapper.getPageSize(), wrapper.getSortFields(), wrapper.getSortDirections()));
		return wrapper;
	}
	
}
