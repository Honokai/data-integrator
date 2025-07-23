package dev.honokai.data_integrator_backend.infrastructure.integrators;

import dev.honokai.data_integrator_backend.domain.interfaces.IntegratorProcessor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RestApiIntegrator implements IntegratorProcessor {
    @Override
    public void executeIntegration() {
        var httpClient = HttpClient.newHttpClient();
        try {
            var httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/scripts/integrator-check")).GET().build();
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {

        }
    }
}
