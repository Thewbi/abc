package de.compiling.handler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

import org.antlrfun.ASTWalker;
import org.apache.commons.lang3.StringUtils;
import org.cgrammar.CParser;

public class FunctionDefinitionRuleHandler extends AbstractRuleHandler<CParser.FunctionDefinitionContext> {

	final BufferedWriter bufferedWriter;

	public FunctionDefinitionRuleHandler(final BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}

	@Override
	public void processEnter(final CParser.FunctionDefinitionContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

//		System.out.println(ctx.getText());

//		for (int i = 0; i < ctx.getChildCount(); i++) {
//
//			System.out.println(ctx.getChild(i).getText());
//		}

//		dumpContext(ctx, 0);

		final String functionName = (String) properties.get("func_name");

		if (StringUtils.isNotBlank(functionName)) {

//			if (functionName.equals("main")) {
//				bufferedWriter.write("global start\n" + "\n" + "section .text\n" + "start:");
//			}
		}

	}

	@Override
	public void processExit(final CParser.FunctionDefinitionContext ctx, final Map<String, Object> properties,
			final ASTWalker astWalker) throws IOException {

//		System.out.println(ctx.getText());

//		for (int i = 0; i < ctx.getChildCount(); i++) {
//
//			System.out.println(ctx.getChild(i).getText());
//		}

//		dumpContext(ctx, 0);

	}

}
