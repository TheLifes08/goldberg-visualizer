package leti.practice.algorithm;

import leti.practice.structures.graph.EdgeProperties;
import leti.practice.structures.graph.Node;
import leti.practice.structures.graph.ResidualNetwork;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class AlgorithmExecutorTest {
    @Test
    public void nextStep() {
        AlgorithmExecutor test_alg = new AlgorithmExecutor();
        ResidualNetwork<Double> test_network = new ResidualNetwork<>();

        test_network.setSource(new Node("a"));
        test_network.setDestination(new Node("d"));
        test_network.addEdge(new Node("a"),new Node("b"),new EdgeProperties<>(2.0,0.0));
        test_network.addEdge(new Node("b"),new Node("c"),new EdgeProperties<>(13.0,0.0));
        test_network.addEdge(new Node("c"),new Node("d"),new EdgeProperties<>(9.0,0.0));
        test_network.addEdge(new Node("h"),new Node("c"),new EdgeProperties<>(4.0,0.0));
        test_network.addEdge(new Node("e"),new Node("f"),new EdgeProperties<>(7.0,0.0));
        test_network.addEdge(new Node("g"),new Node("h"),new EdgeProperties<>(7.0,0.0));
        test_network.addEdge(new Node("b"),new Node("e"),new EdgeProperties<>(7.0,0.0));
        test_network.addEdge(new Node("a"),new Node("g"),new EdgeProperties<>(8.0,0.0));
        test_network.addEdge(new Node("f"),new Node("d"),new EdgeProperties<>(10.0,0.0));

        test_alg.setNetwork(test_network);
        Assertions.assertTrue(test_alg.nextStep());
    }

    @Test
    public void previousStep() {
        AlgorithmExecutor test_alg = new AlgorithmExecutor();
        ResidualNetwork<Double> test_network = new ResidualNetwork<>();

        test_network.setSource(new Node("a"));
        test_network.setDestination(new Node("d"));
        test_network.addEdge(new Node("a"),new Node("b"),new EdgeProperties<>(2.0,0.0));
        test_network.addEdge(new Node("b"),new Node("c"),new EdgeProperties<>(13.0,0.0));
        test_network.addEdge(new Node("c"),new Node("d"),new EdgeProperties<>(9.0,0.0));
        test_network.addEdge(new Node("h"),new Node("c"),new EdgeProperties<>(4.0,0.0));
        test_network.addEdge(new Node("e"),new Node("f"),new EdgeProperties<>(7.0,0.0));
        test_network.addEdge(new Node("g"),new Node("h"),new EdgeProperties<>(7.0,0.0));
        test_network.addEdge(new Node("b"),new Node("e"),new EdgeProperties<>(7.0,0.0));
        test_network.addEdge(new Node("a"),new Node("g"),new EdgeProperties<>(8.0,0.0));
        test_network.addEdge(new Node("f"),new Node("d"),new EdgeProperties<>(10.0,0.0));

        test_alg.setNetwork(test_network);
        test_alg.runAlgorithm(test_network);
        Assertions.assertTrue(test_alg.previousStep());
    }

    @Test
    public void runAlgorithm() {
        AlgorithmExecutor test_alg = new AlgorithmExecutor();

        // Тестовые случаи исходных сетей
        // 1
        ResidualNetwork<Double> test_network_1 = new ResidualNetwork<>();
        test_network_1.setSource(new Node("a"));
        test_network_1.setDestination(new Node("b"));
        test_network_1.addEdge(new Node("a"),new Node("b"),new EdgeProperties<>(1.0,0.0));

        // 2
        ResidualNetwork<Double> test_network_2 = new ResidualNetwork<>();
        test_network_2.setSource(new Node("a"));
        test_network_2.setDestination(new Node("d"));
        test_network_2.addEdge(new Node("a"),new Node("b"),new EdgeProperties<>(2.0,0.0));
        test_network_2.addEdge(new Node("b"),new Node("c"),new EdgeProperties<>(13.0,0.0));
        test_network_2.addEdge(new Node("c"),new Node("d"),new EdgeProperties<>(9.0,0.0));
        test_network_2.addEdge(new Node("h"),new Node("c"),new EdgeProperties<>(4.0,0.0));
        test_network_2.addEdge(new Node("e"),new Node("f"),new EdgeProperties<>(7.0,0.0));
        test_network_2.addEdge(new Node("g"),new Node("h"),new EdgeProperties<>(7.0,0.0));
        test_network_2.addEdge(new Node("b"),new Node("e"),new EdgeProperties<>(7.0,0.0));
        test_network_2.addEdge(new Node("a"),new Node("g"),new EdgeProperties<>(8.0,0.0));
        test_network_2.addEdge(new Node("f"),new Node("d"),new EdgeProperties<>(10.0,0.0));

        // 3
        ResidualNetwork<Double> test_network_3 = new ResidualNetwork<>();
        test_network_3.setSource(new Node("a"));
        test_network_3.setDestination(new Node("d"));
        test_network_3.addEdge(new Node("a"),new Node("b"),new EdgeProperties<>(1.0,0.0));
        test_network_3.addEdge(new Node("b"),new Node("c"),new EdgeProperties<>(1.0,0.0));
        test_network_3.addEdge(new Node("c"),new Node("d"),new EdgeProperties<>(1.0,0.0));
        test_network_3.addEdge(new Node("a"),new Node("c"),new EdgeProperties<>(100.0,0.0));
        test_network_3.addEdge(new Node("b"),new Node("d"),new EdgeProperties<>(100.0,0.0));

        // 4
        ResidualNetwork<Double> test_network_4=new ResidualNetwork<>();
        test_network_4.setSource(new Node("a"));
        test_network_4.setDestination(new Node("h"));
        test_network_4.addEdge(new Node("a"),new Node("c"),new EdgeProperties<>(12.0,0.0));
        test_network_4.addEdge(new Node("a"),new Node("d"),new EdgeProperties<>(22.0,0.0));
        test_network_4.addEdge(new Node("c"),new Node("d"),new EdgeProperties<>(14.0,0.0));
        test_network_4.addEdge(new Node("c"),new Node("f"),new EdgeProperties<>(21.0,0.0));
        test_network_4.addEdge(new Node("d"),new Node("g"),new EdgeProperties<>(10.0,0.0));
        test_network_4.addEdge(new Node("g"),new Node("f"),new EdgeProperties<>(10.0,0.0));
        test_network_4.addEdge(new Node("g"),new Node("h"),new EdgeProperties<>(7.0,0.0));
        test_network_4.addEdge(new Node("f"),new Node("h"),new EdgeProperties<>(7.0,0.0));

        // 5
        ResidualNetwork<Double> test_network_5 = new ResidualNetwork<>();
        test_network_5.setSource(new Node("a"));
        test_network_5.setDestination(new Node("f"));
        test_network_5.addEdge(new Node("a"),new Node("b"),new EdgeProperties<>(5.0,0.0));
        test_network_5.addEdge(new Node("a"),new Node("c"),new EdgeProperties<>(7.0,0.0));
        test_network_5.addEdge(new Node("b"),new Node("d"),new EdgeProperties<>(0.0,0.0));
        test_network_5.addEdge(new Node("c"),new Node("f"),new EdgeProperties<>(12.0,0.0));
        test_network_5.addEdge(new Node("d"),new Node("e"),new EdgeProperties<>(9.0,0.0));
        test_network_5.addEdge(new Node("d"),new Node("f"),new EdgeProperties<>(6.0,0.0));
        test_network_5.addEdge(new Node("e"),new Node("c"),new EdgeProperties<>(6.0,0.0));

        // 6
        ResidualNetwork<Double> test_network_6 = new ResidualNetwork<>();
        test_network_6.setSource(new Node("a"));
        test_network_6.setDestination(new Node("f"));
        test_network_6.addEdge(new Node("a"),new Node("b"),new EdgeProperties<>(0.0,0.0));
        test_network_6.addEdge(new Node("a"),new Node("c"),new EdgeProperties<>(0.0,0.0));
        test_network_6.addEdge(new Node("b"),new Node("d"),new EdgeProperties<>(0.0,0.0));
        test_network_6.addEdge(new Node("c"),new Node("f"),new EdgeProperties<>(0.0,0.0));
        test_network_6.addEdge(new Node("d"),new Node("e"),new EdgeProperties<>(0.0,0.0));
        test_network_6.addEdge(new Node("d"),new Node("f"),new EdgeProperties<>(0.0,0.0));
        test_network_6.addEdge(new Node("e"),new Node("c"),new EdgeProperties<>(0.0,0.0));
        //7
        ResidualNetwork<Double> test_network_7 = new ResidualNetwork<>();
        test_network_7.setSource(new Node("a"));
        test_network_7.setDestination(new Node("f"));
        test_network_7.addEdge(new Node("a"), new Node("b"), new EdgeProperties<>(5.0,0.0));
        test_network_7.addEdge(new Node("a"), new Node("c"), new EdgeProperties<>(7.0,0.0));
        test_network_7.addEdge(new Node("b"), new Node("a"), new EdgeProperties<>(5.0,0.0));
        test_network_7.addEdge(new Node("b"), new Node("d"), new EdgeProperties<>(0.0,0.0));
        test_network_7.addEdge(new Node("c"), new Node("a"), new EdgeProperties<>(7.0,0.0));
        test_network_7.addEdge(new Node("c"), new Node("f"), new EdgeProperties<>(12.0,0.0));
        test_network_7.addEdge(new Node("d"), new Node("e"), new EdgeProperties<>(9.0,0.0));
        test_network_7.addEdge(new Node("d"), new Node("f"), new EdgeProperties<>(6.0,0.0));
        test_network_7.addEdge(new Node("e"), new Node("c"), new EdgeProperties<>(6.0,0.0));
        // 8
        // 9
        ResidualNetwork<Double> test_network_9 = new ResidualNetwork<>();
        test_network_9.setSource(new Node("a"));
        test_network_9.setDestination(new Node("g"));
        test_network_9.addEdge(new Node("a"),new Node("b"),new EdgeProperties<>(4.0,0.0));
        test_network_9.addEdge(new Node("a"),new Node("c"),new EdgeProperties<>(4.0,0.0));
        test_network_9.addEdge(new Node("b"),new Node("d"),new EdgeProperties<>(3.0,0.0));
        test_network_9.addEdge(new Node("b"),new Node("e"),new EdgeProperties<>(6.0,0.0));
        test_network_9.addEdge(new Node("c"),new Node("d"),new EdgeProperties<>(3.0,0.0));
        test_network_9.addEdge(new Node("c"),new Node("f"),new EdgeProperties<>(6.0,0.0));
        test_network_9.addEdge(new Node("d"),new Node("e"),new EdgeProperties<>(3.0,0.0));
        test_network_9.addEdge(new Node("d"),new Node("f"),new EdgeProperties<>(3.0,0.0));
        test_network_9.addEdge(new Node("e"),new Node("g"),new EdgeProperties<>(5.0,0.0));
        test_network_9.addEdge(new Node("f"),new Node("g"),new EdgeProperties<>(5.0,0.0));

        // 10
        ResidualNetwork<Double> test_network_10=new ResidualNetwork<>();
        test_network_10.setSource(new Node("a"));
        test_network_10.setDestination(new Node("f"));
        test_network_10.addEdge(new Node("a"),new Node("b"),new EdgeProperties<>(5.0,0.0));
        test_network_10.addEdge(new Node("a"),new Node("c"),new EdgeProperties<>(5.0,0.0));
        test_network_10.addEdge(new Node("b"),new Node("d"),new EdgeProperties<>(5.0,0.0));
        test_network_10.addEdge(new Node("c"),new Node("f"),new EdgeProperties<>(5.0,0.0));
        test_network_10.addEdge(new Node("d"),new Node("e"),new EdgeProperties<>(5.0,0.0));
        test_network_10.addEdge(new Node("d"),new Node("f"),new EdgeProperties<>(5.0,0.0));
        test_network_10.addEdge(new Node("e"),new Node("c"),new EdgeProperties<>(5.0,0.0));

        // 11
        ResidualNetwork<Double> test_network_11=new ResidualNetwork<>();
        test_network_11.setSource(new Node("a"));
        test_network_11.setDestination(new Node("e"));
        test_network_11.addEdge(new Node("a"),new Node("b"),new EdgeProperties<>(20.0,0.0));
        test_network_11.addEdge(new Node("b"),new Node("a"),new EdgeProperties<>(20.0,0.0));
        test_network_11.addEdge(new Node("a"),new Node("d"),new EdgeProperties<>(10.0,0.0));
        test_network_11.addEdge(new Node("d"),new Node("a"),new EdgeProperties<>(10.0,0.0));
        test_network_11.addEdge(new Node("a"),new Node("c"),new EdgeProperties<>(30.0,0.0));
        test_network_11.addEdge(new Node("c"),new Node("a"),new EdgeProperties<>(30.0,0.0));
        test_network_11.addEdge(new Node("b"),new Node("c"),new EdgeProperties<>(40.0,0.0));
        test_network_11.addEdge(new Node("c"),new Node("b"),new EdgeProperties<>(40.0,0.0));
        test_network_11.addEdge(new Node("c"),new Node("d"),new EdgeProperties<>(10.0,0.0));
        test_network_11.addEdge(new Node("d"),new Node("c"),new EdgeProperties<>(10.0,0.0));
        test_network_11.addEdge(new Node("c"),new Node("e"),new EdgeProperties<>(20.0,0.0));
        test_network_11.addEdge(new Node("e"),new Node("c"),new EdgeProperties<>(20.0,0.0));
        test_network_11.addEdge(new Node("b"),new Node("e"),new EdgeProperties<>(30.0,0.0));
        test_network_11.addEdge(new Node("e"),new Node("b"),new EdgeProperties<>(30.0,0.0));
        test_network_11.addEdge(new Node("d"),new Node("e"),new EdgeProperties<>(10.0,0.0));
        test_network_11.addEdge(new Node("e"),new Node("d"),new EdgeProperties<>(10.0,0.0));

        // Проверка ответов
        Assertions.assertEquals(1,test_alg.runAlgorithm(test_network_1));
        Assertions.assertEquals(6,test_alg.runAlgorithm(test_network_2));
        Assertions.assertEquals(2,test_alg.runAlgorithm(test_network_3));
        Assertions.assertEquals(14,test_alg.runAlgorithm(test_network_4));
        Assertions.assertEquals(7,test_alg.runAlgorithm(test_network_5));
        Assertions.assertEquals(0,test_alg.runAlgorithm(test_network_6));
        Assertions.assertEquals(7,test_alg.runAlgorithm(test_network_7));
        //Assertions.assertEquals(7,test_alg.runAlgorithm(test_network_8));
        Assertions.assertEquals(8,test_alg.runAlgorithm(test_network_9));
        Assertions.assertEquals(10,test_alg.runAlgorithm(test_network_10));
        Assertions.assertEquals(60,test_alg.runAlgorithm(test_network_11));
    }
}