package de.backend;

import java.util.List;

import org.antlrfun.Expression;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DummyBackend implements Backend {

	private static final Logger LOG = LogManager.getLogger(DummyBackend.class);

	@Override
	public void ifStatement(final List<Expression> condition, final List<Expression> statementBody) {
		LOG.info("Condition: " + condition);
		LOG.info("StatementBody: " + statementBody);
	}

}
