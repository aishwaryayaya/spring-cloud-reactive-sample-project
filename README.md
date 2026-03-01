# Spring Cloud Gateway Reactive Sample Project

A sample project demonstrating Spring Cloud Gateway with reactive programming, featuring custom filters for request/response modification and global logging.

## Overview

This project showcases a reactive API Gateway built with Spring Cloud Gateway. It routes requests to backend services and provides custom filter implementations for modifying request and response bodies.

## Features

- **Spring Cloud Gateway**: Reactive API Gateway for routing requests
- **Custom Filters**:
  - `Prefilter`: Modifies incoming request body 
  - `PostFilter`: Modifies outgoing response body 
  - `GlobalLoggingFilter`: Logs all incoming requests globally
- **Actuator Endpoints**: Exposed for monitoring and health checks
- **Reactive Programming**: Built on Spring WebFlux and Project Reactor

## Technology Stack

- Java 21
- Spring Boot 3.2.1
- Spring Cloud 2023.0.1
- Spring Cloud Gateway
- Spring Boot Actuator
- Maven

## Project Structure

```
├── src/
│   ├── main/java/com/example/gateway/
│   │   ├── filters/
│   │   │   ├── GlobalLoggingFilter.java    # Global request logging
│   │   │   ├── PostFilter.java             # Response modification filter
│   │   │   └── Prefilter.java              # Request modification filter
│   │   └── GatewayApplication.java         # Main application entry point
│   └── resources/
│       └── application.yml                 # Gateway configuration
├── pom.xml
└── README.md
```

## Configuration

The gateway is configured in `application.yml`:

- **Port**: 8081
- **Route**: `/test/**` → `http://localhost:8082`
- **Filters Applied**: StripPrefix, Prefilter, PostFilter
- **Timeouts**: 1s connect, 5s response

## Running the Application

### Prerequisites

- Java 21
- Maven

### Build

```bash
mvn clean package
```

### Run

```bash
mvn spring-boot:run
```

Or run the JAR:

```bash
java -jar target/gateway-0.0.1-SNAPSHOT.jar
```

## Usage

Once running, the gateway will be available at `http://localhost:8081`.

### Example Request

```bash
curl -X POST http://localhost:8081/test/endpoint \
  -H "Content-Type: text/plain" \
  -d "hello world"
```

The request body will be converted to uppercase before being forwarded to the backend service at `http://localhost:8082`. The response will also be modified (uppercase + "Modified Response" appended).

### Actuator Endpoints

All actuator endpoints are exposed at `http://localhost:8081/actuator/`:

- `/actuator/health` - Health status
- `/actuator/gateway/routes` - List of configured routes

## Filter Details

### GlobalLoggingFilter

- **Order**: 1
- **Function**: Logs HTTP method and URI for all incoming requests
- **Modification**: Removes Content-Length header from requests

### Prefilter

- **Order**: Highest Precedence
- **Function**: Transforms request body to uppercase
- **Applied to**: Route `/test/**`

### PostFilter

- **Function**: Transforms response body to uppercase and appends "Modified Response"
- **Applied to**: Route `/test/**`

## License

This project is for demonstration purposes.