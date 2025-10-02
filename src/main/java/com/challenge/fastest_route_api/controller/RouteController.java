package com.challenge.fastest_route_api.controller;

import com.challenge.fastest_route_api.domain.PathResult;
import com.challenge.fastest_route_api.service.RouteService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadRoutes(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Por favor, seleccione un archivo CSV para cargar.");
        }
        try {
            routeService.loadRoutesFromCsv(file.getInputStream());
            return ResponseEntity.ok("Rutas cargadas y grafo construido exitosamente.");
        } catch (IOException | CsvValidationException e) {
            return ResponseEntity.internalServerError().body("Error al procesar el archivo CSV: " + e.getMessage());
        }
    }

    @GetMapping("/fastest")
    public ResponseEntity<?> getFastestRoute(
            @RequestParam String start,
            @RequestParam String end) {
        try {
            PathResult result = routeService.findFastestRoute(start, end);
            if (result.getRuta().isEmpty()) {
                return ResponseEntity.status(404).body("No se encontró una ruta entre el origen y el destino.");
            }
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}