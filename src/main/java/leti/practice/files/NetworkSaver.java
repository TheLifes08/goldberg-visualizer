package leti.practice.files;

import leti.practice.structures.graph.EdgeProperties;
import leti.practice.structures.graph.Node;
import leti.practice.structures.graph.ResidualNetwork;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class NetworkSaver {
    public static boolean saveNetwork(File file, ResidualNetwork<Double> network) {
        if (file == null || network == null) {
            return false;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            Node source = network.getSource();
            Node destination = network.getDestination();

            writer.write(source.getName());
            writer.newLine();

            writer.write(destination.getName());
            writer.newLine();

            for (Node from : network.getNetworkNodes()) {
                for (Node to : network.getNetworkEdges(from).keySet()) {
                    EdgeProperties<Double> edgeParams = network.getNetworkEdges(from).get(to);
                    writer.write(from.getName() + " " + to.getName() + " " + edgeParams.getCapacity());
                    writer.newLine();
                }
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
