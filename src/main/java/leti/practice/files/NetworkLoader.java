package leti.practice.files;

import leti.practice.structures.graph.ResidualNetwork;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class NetworkLoader {
    public static ResidualNetwork<Double> loadNetwork(File file) {
        ResidualNetwork<Double> loadNetwork = new ResidualNetwork<Double>();

        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            String line;

            while ((line = reader.readLine()) != null) {

            }

        } catch (IOException e) {
            return null;
        }

        return loadNetwork;
    }
}
