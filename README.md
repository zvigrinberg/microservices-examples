# microservices-examples
examples of distributed microservices architecture - demonstrating unit test + integration test, as well as logging and tracing, and documentation by OpenAPI/Swagger

## Spinning up a wiremock mock server instance in container(for testing locally).
1. Run a wiremock server instance that will be accessible from machinethat runs the container on port 8080, but from host will be accessible via port 8081
```shell
 podman run -d --name wiremock-container   -p 8081:8080  wiremock/wiremock
```
2. Upload stub mappings to server using REST API admin mappings import endpoint in wiremock server
```shell
curl -X POST http://localhost:8081/__admin/mappings/import -T ./mappings/mappings.json
```

3. Verify that all stub mappings uploaded correctly to wiremock server
```shell
 curl -X GET http://localhost:8081/__admin
```
4. Runs the microservice that access all mocked rest API endpoints and validate expected behavior.
