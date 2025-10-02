package com.challenge.fastest_route_api.graph;

import com.challenge.fastest_route_api.domain.Edge;
import com.challenge.fastest_route_api.domain.PathResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GraphServiceTest {

    private GraphService graphService;

    @BeforeEach
    void setUp() {

        graphService = new GraphService();

        List<Map.Entry<String, Edge>> routes = List.of(
                new AbstractMap.SimpleEntry<>("R11", new Edge("R12", 20)),
                new AbstractMap.SimpleEntry<>("R12", new Edge("R13", 9)),
                new AbstractMap.SimpleEntry<>("R13", new Edge("R12", 11)),
                new AbstractMap.SimpleEntry<>("R13", new Edge("R20", 9)),
                new AbstractMap.SimpleEntry<>("R20", new Edge("R13", 11)),
                new AbstractMap.SimpleEntry<>("CP1", new Edge("R11", 84)),
                new AbstractMap.SimpleEntry<>("R11", new Edge("CP1", 92)),
                new AbstractMap.SimpleEntry<>("CP1", new Edge("CP2", 7)),
                new AbstractMap.SimpleEntry<>("CP2", new Edge("CP1", 10)),
                new AbstractMap.SimpleEntry<>("CP2", new Edge("R20", 67)),
                new AbstractMap.SimpleEntry<>("R20", new Edge("CP2", 60)));
        graphService.buildGraph(routes);
    }

    @Test
    void findShortestPath_ShouldReturnCorrectPathAnd_Time() {
        String start = "CP1";
        String end = "R20";

        PathResult result = graphService.findShortestPath(start, end);

        assertNotNull(result, "El resultado no debe ser nulo.");
        assertEquals(74, result.getTiempoTotal(), "El tiempo total de viaje no es el esperado.");
        assertEquals(List.of("CP1", "CP2", "R20"), result.getRuta(), "La ruta no es la esperada.");
    }

    @Test
    void findShortestPath_ShouldHandleReversePathCorrectly() {
        String start = "R20";
        String end = "CP1";

        PathResult result = graphService.findShortestPath(start, end);

        assertNotNull(result);
        assertEquals(70, result.getTiempoTotal());
        assertEquals(List.of("R20", "CP2", "CP1"), result.getRuta());
    }

    @Test
    void findShortestPath_ShouldThrowException_ForInvalidNodes() {
        String start = "NODO_INEXISTENTE";
        String end = "R20";

        assertThrows(IllegalArgumentException.class, () -> {
            graphService.findShortestPath(start, end);
        }, "Debe lanzarse una excepción para nodos de inicio inválidos.");
    }
}