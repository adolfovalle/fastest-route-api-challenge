package com.challenge.fastest_route_api.graph;

import com.challenge.fastest_route_api.domain.Edge;
import com.challenge.fastest_route_api.domain.PathResult;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GraphService {

    private final Map<String, List<Edge>> adjacencies = new HashMap<>();

    public void buildGraph(List<Map.Entry<String, Edge>> routes) {

        adjacencies.clear();
        Set<String> locations = new HashSet<>();
        for (Map.Entry<String, Edge> route : routes) {
            locations.add(route.getKey());
            locations.add(route.getValue().getDestination());
        }

        locations.forEach(location -> adjacencies.put(location, new ArrayList<>()));

        for (Map.Entry<String, Edge> route : routes) {
            adjacencies.get(route.getKey()).add(route.getValue());
        }
    }

    public PathResult findShortestPath(String start, String end) {
        if (!adjacencies.containsKey(start) || !adjacencies.containsKey(end)) {
            throw new IllegalArgumentException("Ubicación de origen o destino no encontrada en el grafo.");
        }

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Map.Entry.comparingByValue());

        for (String node : adjacencies.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.add(new AbstractMap.SimpleEntry<>(start, 0));

        while (!pq.isEmpty()) {
            String u = pq.poll().getKey();

            if (u.equals(end)) {
                break;
            }

            if (distances.get(u) == Integer.MAX_VALUE) {
                continue;
            }

            for (Edge edge : adjacencies.get(u)) {
                String v = edge.getDestination();
                int weight = edge.getWeight();
                if (distances.get(u) + weight < distances.get(v)) {
                    distances.put(v, distances.get(u) + weight);
                    predecessors.put(v, u);
                    pq.add(new AbstractMap.SimpleEntry<>(v, distances.get(v)));
                }
            }
        }

        LinkedList<String> path = new LinkedList<>();
        String current = end;
        if (distances.get(end) == Integer.MAX_VALUE) {
            return new PathResult(Collections.emptyList(), -1);
        }

        while (predecessors.containsKey(current)) {
            path.addFirst(current);
            current = predecessors.get(current);
        }
        path.addFirst(start);

        return new PathResult(path, distances.get(end));
    }
}