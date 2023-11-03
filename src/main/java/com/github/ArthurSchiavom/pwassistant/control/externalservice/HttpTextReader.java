package com.github.ArthurSchiavom.pwassistant.control.externalservice;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpTextReader {
    public static String readText(final String urlString) {
        final StringBuilder result = new StringBuilder();
        try {
            final URL url = new URL(urlString);
            final Scanner scanner = new Scanner(url.openStream(), StandardCharsets.UTF_8);

            result.append(scanner.nextLine());
            while (scanner.hasNext()) {
                result.append("\n").append(scanner.nextLine());
            }

            scanner.close();
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get codes", e);
        }
    }
}
