package org.voidlang.compiler.util;

import lombok.experimental.UtilityClass;
import org.voidlang.compiler.token.Token;
import org.voidlang.compiler.token.TokenTransformer;
import org.voidlang.compiler.token.TokenType;
import org.voidlang.compiler.token.Tokenizer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Tokenizers {
    public List<Token> tokenizeSource(String source) {
        Tokenizer tokenizer = new Tokenizer(new File("example.void"), source);
        List<Token> tokens = new ArrayList<>();
        Token token;

        do {
            tokens.add(token = tokenizer.next());
            if (token.is(TokenType.UNEXPECTED))
                throw new RuntimeException(token.value());
        } while (token.hasNext());

        return new TokenTransformer(tokens).transform();
    }
}
