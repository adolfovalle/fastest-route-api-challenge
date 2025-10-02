package com.challenge.fastest_route_api.service;

import com.challenge.fastest_route_api.domain.Edge;
import com.challenge.fastest_route_api.domain.PathResult;
import com.challenge.fastest_route_api.graph.GraphService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RouteService {

    private final GraphService graphService;

    public RouteService(GraphService graphService) {
        this.graphService = graphService;
    }

    public void loadRoutesFromCsv(InputStream inputStream) throws IOException, CsvValidationException {

        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream))
                .withSkipLines(1) // Omitir la primera línea (cabecera)
                .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator(';').build())
                .build()) {

            List<Map.Entry<String, Edge>> routes = new ArrayList<>();
            String[] line;

            while ((line = reader.readNext()) != null) {
                if (line.length >= 3) {
                    String start = line[0].trim();
                    String end = line[1].trim();
                    int time = Integer.parseInt(line[2].trim());
                    routes.add(new AbstractMap.SimpleEntry<>(start, new Edge(end, time)));
                }
            }
            graphService.buildGraph(routes);
        }
    }

    public PathResult findFastestRoute(String start, String end) {
        long startTime = System.currentTimeMillis();
        PathResult result = graphService.findShortestPath(start, end);
        long endTime = System.currentTimeMillis();

        System.out.println("Cálculo de ruta tomó: " + (endTime - startTime) + "ms");

        return result;
    }
}