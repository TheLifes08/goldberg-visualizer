package leti.practice.view;

import leti.practice.structures.graph.EdgeProperties;
import leti.practice.structures.graph.Node;
import leti.practice.structures.graph.ResidualNetwork;

import java.util.HashSet;

public class NetworkParametersFormatter {
    public static String getNetworkParameters(ResidualNetwork<Double> network) {
        if (network == null) {
            return """
                    Nodes (<name> <surplus> <height>):

                    Edges (<source> <destination> <flow> <capacity>):

                    Reverse edges (<source> <destination> <flow> <capacity>):
                    """;
        }

        StringBuilder result = new StringBuilder();
        HashSet<Node> nodes = new HashSet<>(network.getNetworkNodes());

        nodes.addAll(network.getReverseNetworkNodes());
        result.append("Nodes (<name> <surplus> <height>):\n");

        for (Node node : nodes) {
            result.append(node.getName());
            result.append(" ");

            Double surplus = network.getSurpluses().get(node);
            if (surplus != null) {
                result.append(surplus);
            } else {
                result.append("-");
            }

            result.append(" ");

            Integer height = network.getHeights().get(node);
            if (height != null) {
                result.append(height);
            } else {
                result.append("-");
            }

            result.append("\n");
        }

        result.append("\nEdges (<source> <destination> <flow> <capacity>):\n");

        for (Node source : network.getNetworkNodes()) {
            for (Node destination: network.getNetworkEdges(source).keySet()) {
                EdgeProperties<Double> edgeParams = network.getNetworkEdges(source).get(destination);

                result.append(source.getName());
                result.append(" ");
                result.append(destination.getName());
                result.append(" ");
                result.append(edgeParams.getFlow());
                result.append(" ");
                result.append(edgeParams.getCapacity());
                result.append("\n");
            }
        }

        result.append("\nReverse edges (<source> <destination> <flow> <capacity>):\n");

        for (Node source : network.getReverseNetworkNodes()) {
            for (Node destination: network.getReverseNetworkEdges(source).keySet()) {
                EdgeProperties<Double> edgeParams = network.getReverseNetworkEdges(source).get(destination);

                result.append(source.getName());
                result.append(" ");
                result.append(destination.getName());
                result.append(" ");
                result.append(edgeParams.getFlow());
                result.append(" ");
                result.append(edgeParams.getCapacity());
                result.append("\n");
            }
        }

        return result.toString();
    }
}
