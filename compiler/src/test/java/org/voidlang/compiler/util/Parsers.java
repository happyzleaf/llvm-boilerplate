package org.voidlang.compiler.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.parser.AstParser;
import org.voidlang.compiler.parser.ParserContext;
import org.voidlang.compiler.token.Token;

import java.util.List;

@UtilityClass
public class Parsers {
    public @NotNull AstParser of(@NotNull String source) {
        List<Token> tokens = Tokenizers.tokenizeSource(source);
        // Debug.debugTokens(tokens);
        ParserContext context = new ParserContext(tokens, source);

        return new AstParser(context);
    }
}
