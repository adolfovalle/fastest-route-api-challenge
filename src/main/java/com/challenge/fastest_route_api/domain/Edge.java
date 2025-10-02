package com.challenge.fastest_route_api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Edge {
    private String destination;
    private int weight;
}