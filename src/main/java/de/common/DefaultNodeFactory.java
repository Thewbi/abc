package de.common;

public class DefaultNodeFactory implements NodeFactory<DefaultNode> {

	private IdService idService;

	@Override
	public DefaultNode createObject(final Object... args) throws NodeException {

		final DefaultNode result = new DefaultNode();

		if (idService != null) {
			result.setId(idService.getNextId());
		}

		return result;
	}

	@Override
	public void reset() {
		idService.reset();
	}

	public IdService getIdService() {
		return idService;
	}

	public void setIdService(final IdService idService) {
		this.idService = idService;
	}

}
