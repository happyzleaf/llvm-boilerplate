package org.voidlang.compiler.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@UtilityClass
public class Resources {
    @SneakyThrows
    public static @NotNull String read(@NotNull String path) {
        StringBuilder builder = new StringBuilder();
        try (
            InputStream stream = Resources.class.getClassLoader().getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream))
        ) {
            String line;
            while ((line = reader.readLine()) != null)
                builder.append(line).append('\n');
        }
        return builder.toString();
    }
}
