package org.example.microservicesdemo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class MicroServiceEntry {
    private String urlWithPort;
    private Map<String,String> endpoints;
}
