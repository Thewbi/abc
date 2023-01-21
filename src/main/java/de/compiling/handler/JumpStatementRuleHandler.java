package de.compiling.handler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.antlrfun.ASTWalker;
import org.antlrfun.Argument;
import org.cgrammar.CParser;
import org.cgrammar.CParser.JumpStatementContext;

public class JumpStatementRuleHandler extends AbstractRuleHandler<CParser.JumpStatementContext> {

	final BufferedWriter bufferedWriter;

	public JumpStatementRuleHandler(final BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}

	@Override
	public void processEnter(final JumpStatementContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processExit(final JumpStatementContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

		final String jumpType = (String) properties.get("type");

		@SuppressWarnings("unchecked")
		final List<Argument> arguments = (List<Argument>) properties.get("args");
		final String returnValue = arguments.get(0).getValue();

		if (jumpType.equals("return")) {
			bufferedWriter.write("\n\n");
			bufferedWriter.write("    ; return\n");
			bufferedWriter.write("    push    dword " + returnValue + "\n");
			bufferedWriter.write("    mov     eax, 1\n");
			bufferedWriter.write("    sub     esp, 12\n");
			bufferedWriter.write("    int     0x80");
		}
	}

}
