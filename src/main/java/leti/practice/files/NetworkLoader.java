package leti.practice.files;

import leti.practice.structures.graph.EdgeProperties;
import leti.practice.structures.graph.Node;
import leti.practice.structures.graph.ResidualNetwork;

import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class NetworkLoader {
    public static ResidualNetwork<Double> loadNetwork(File file) {
        if (file == null) {
            return null;
        }

        ResidualNetwork<Double> loadNetwork = new ResidualNetwork<Double>();

        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            String line = reader.readLine();

            if (line != null) {
                loadNetwork.setSource(new Node(line));
            } else {
                throw new Exception("Network source not found");
            }

            line = reader.readLine();

            if (line != null) {
                loadNetwork.setDestination(new Node(line));
            } else {
                throw new Exception("Network destination not found");
            }

            while ((line = reader.readLine()) != null) {
                int firstSpaceIndex = line.indexOf(' ');
                int secondSpaceIndex = line.lastIndexOf(' ');

                if (firstSpaceIndex != -1 && secondSpaceIndex != -1) {
                    Node from = new Node(line.substring(0, firstSpaceIndex));
                    Node to = new Node(line.substring(firstSpaceIndex + 1, secondSpaceIndex));
                    Double capacity = Double.valueOf(line.substring(secondSpaceIndex + 1));
                    loadNetwork.addEdge(from, to, new EdgeProperties<>(capacity, 0.0));
                } else {
                    throw new Exception("Wrong edge format");
                }
            }

        } catch (Exception e) {
            return null;
        }

        return loadNetwork;
    }
}
