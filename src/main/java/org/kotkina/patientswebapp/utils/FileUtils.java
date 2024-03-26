package org.kotkina.patientswebapp.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FileUtils {

    public static <T> List<T> parseFile(InputStream inputStream, Function<String, T> parseFunction) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            List<T> list = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                list.add(parseFunction.apply(line));
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
