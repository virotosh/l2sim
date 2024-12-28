package free.l2j.simfactory.model.actor.ai.preference;

/*
 * Djikstra's algorithm
 * Finding the shortest path between cities in teleport graph
 * Author: Virotosh
 */

import java.util.*;

import net.sf.l2j.gameserver.data.xml.TeleportData;
import net.sf.l2j.gameserver.model.location.TeleportLocation;

public class TeleportGraph {
    private static final double INFINITY = Double.POSITIVE_INFINITY;
    private Set<String> nodes;
    private Map<String, Set<Pair<String, Double>>> adjacencyList;
    
    public static final String[] CityNames = {
        "Dwarven Village",
        "Orc Village",
        "Dark Elf Village",
        "Elven Village",
        "Talking Island Village",
        "Heine",
        "The Town of Dion",
        "The Town of Giran",
        "Town of Aden",
        "Town of Goddard",
        "The Town of Gludio",
        "The Village of Gludin",
        "Town of Schuttgart",
        "Rune Township",
        "Town of Oren",
        "hunter village"
    };

    // IDs of the Gatekeepers
    public static final int[] GateKeepers = {
        30540,
        30576,
        30134,
        30146,
        30006,
        30899,
        30059,
        30080,
        30848,
        31275,
        30256,
        30320,
        31964,
        31320,
        30177,
        30233
    };

    public TeleportGraph() {
    	List<Triple<String, String, Double>> graphEdges = new ArrayList<>();
    	for (int GKindex = 0; GKindex < CityNames.length; GKindex++) {
    		final List<TeleportLocation> teleports = TeleportData.getInstance().getTeleports(GateKeepers[GKindex]);
    		if (teleports == null)
    			return;
    		
    		for (int index = 0; index < teleports.size(); index++)
    		{
    			final TeleportLocation teleport = teleports.get(index);
    			if (teleport == null)
    				continue;
    			
    			String edgeFrom = CityNames[GKindex];
    			String edgeTo = teleport.getDesc();
    			double cost = teleport.getPriceCount();
    			graphEdges.add(new Triple<>(edgeFrom, edgeTo, cost));
    		}
    	}

        nodes = new HashSet<>();
        for (Triple<String, String, Double> edge : graphEdges) {
            nodes.add(edge.getFirst());
            nodes.add(edge.getSecond());
        }

        adjacencyList = new HashMap<>();
        for (String node : nodes) {
            adjacencyList.put(node, new HashSet<>());
        }
        for (Triple<String, String, Double> edge : graphEdges) {
            adjacencyList.get(edge.getFirst()).add(new Pair<>(edge.getSecond(), edge.getThird()));
        }
    }

    public Pair<List<String>, Double> shortestPath(String startNode, String endNode) {
        Set<String> unvisitedNodes = new HashSet<>(nodes);
        Map<String, Double> distanceFromStart = new HashMap<>();
        for (String node : nodes) {
            distanceFromStart.put(node, node.equals(startNode) ? 0.0 : INFINITY);
        }

        Map<String, String> previousNode = new HashMap<>();
        for (String node : nodes) {
            previousNode.put(node, null);
        }

        while (!unvisitedNodes.isEmpty()) {
            String currentNode = Collections.min(unvisitedNodes, Comparator.comparing(distanceFromStart::get));
            unvisitedNodes.remove(currentNode);

            if (distanceFromStart.get(currentNode).equals(INFINITY)) {
                break;
            }

            for (Pair<String, Double> neighbor : adjacencyList.get(currentNode)) {
                double newPath = distanceFromStart.get(currentNode) + neighbor.getSecond();
                if (newPath < distanceFromStart.get(neighbor.getFirst())) {
                    distanceFromStart.put(neighbor.getFirst(), newPath);
                    previousNode.put(neighbor.getFirst(), currentNode);
                }
            }

            if (currentNode.equals(endNode)) {
                break;
            }
        }

        LinkedList<String> path = new LinkedList<>();
        String currentNode = endNode;
        while (previousNode.get(currentNode) != null) {
            path.addFirst(currentNode);
            currentNode = previousNode.get(currentNode);
        }
        path.addFirst(startNode);

        return new Pair<>(path, distanceFromStart.get(endNode));
    }

    public static class Pair<K, V> {
        private final K first;
        private final V second;

        public Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }

        public K getFirst() {
            return first;
        }

        public V getSecond() {
            return second;
        }
    }

    public static class Triple<K, V, T> {
        private final K first;
        private final V second;
        private final T third;

        public Triple(K first, V second, T third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public K getFirst() {
            return first;
        }

        public V getSecond() {
            return second;
        }

        public T getThird() {
            return third;
        }
    }
}

