package de.compiling.handler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.antlrfun.ASTWalker;
import org.antlrfun.Argument;
import org.antlrfun.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.cgrammar.CParser;
import org.cgrammar.CParser.StatementContext;

public class StatementRuleHandler implements RuleHandler<CParser.StatementContext> {

	final BufferedWriter bufferedWriter;

	public StatementRuleHandler(final BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}

	@Override
	public void processEnter(final StatementContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processExit(final StatementContext ctx, final Map<String, Object> properties, final ASTWalker astWalker)
			throws IOException {

		@SuppressWarnings("unchecked")
		final List<Argument> arguments = (List<Argument>) properties.get("args");
		if (CollectionUtils.isEmpty(arguments)) {
			return;
		}

		final String firstArgument = arguments.get(0).getValue();

		if (firstArgument.equals("printf")) {

			// prepare data so it can be output into the data section later
			final Data data = new Data();
			astWalker.getDataList().add(data);

			final String varName = "arg" + astWalker.getDataList().size();
			data.setVarName(varName);

			final String secondArgument = arguments.get(1).getValue();
			data.setValue(secondArgument);

			System.out.println("printf");

			bufferedWriter.write("\n\n");

			bufferedWriter.write("    ; printf\n");
			bufferedWriter.write("    push    dword " + varName + ".len\n");
			bufferedWriter.write("    push    dword " + varName + "\n");
			bufferedWriter.write("    push    dword 1\n");
			bufferedWriter.write("    mov     eax, 4\n");
			bufferedWriter.write("    sub     esp, 4\n");
			bufferedWriter.write("    int     0x80\n");
			bufferedWriter.write("    add     esp, 16\n");
		}
	}

}
