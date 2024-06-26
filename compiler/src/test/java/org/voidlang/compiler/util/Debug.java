package org.voidlang.compiler.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.token.Token;
import org.voidlang.compiler.util.console.ConsoleFormat;

import java.util.List;

@UtilityClass
public class Debug {
    public void debugTokens(@NotNull List<@NotNull Token> tokens) {
        System.out.println();
        System.out.println(ConsoleFormat.RED + "           " + ConsoleFormat.BOLD + "PARSED TOKENS");
        System.out.println(ConsoleFormat.DEFAULT);

        int longestType = tokens.stream()
            .mapToInt(x -> x.type().name().length())
            .max()
            .orElse(0);

        int longestValue = tokens.stream()
            .mapToInt(x -> x.value().length())
            .max()
            .orElse(0);

        int longestRange = tokens.stream()
            .mapToInt(x -> x.meta().range().length())
            .max()
            .orElse(0);

        for (Token element : tokens) {
            int typeLength = element.type().name().length();
            for (int i = 0; i < longestType - typeLength; i++)
                System.out.print(' ');
            System.out.print(element.print());

            if (element.meta().beginIndex() < 0) {
                System.out.println();
                continue;
            }

            int valueLength = element.value().length();
            for (int i = 0; i < (longestValue - valueLength) + 1; i++)
                System.out.print(' ');

            int rangeLength = element.meta().range().length();
            for (int i = 0; i < longestRange - rangeLength; i++)
                System.out.print(' ');

            System.out.print(element.meta().range());

            for (int i = 0; i < 5; i++)
                System.out.print(' ');

            System.out.println(element.meta().index());
        }
    }
}
