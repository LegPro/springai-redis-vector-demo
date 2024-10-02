# SpringAI Redis Vector Demo

This project demonstrates a **Retrieval-Augmented Generation (RAG)** system using **Redis Vector** for storing and retrieving vectorized data. The demo is built with **Spring Boot** and leverages Redis for storing vectors and performing similarity searches on text data.

## Overview

In this demo, vector embeddings are generated using OpenAI and stored in **Redis Vector**. The application allows you to search for similar documents based on vectorized embeddings. Redis Vector provides fast and efficient similarity search using cosine distance on the stored vectors.

## Prerequisites

- **Java 21** installed on your system.
- **Maven** installed to build and run the project.
- A valid **OpenAI API Key** for vector generation.
- **Redis Vector** installed for storing and retrieving vector embeddings.

## OpenAI API Key Setup

The application requires an **OpenAI API key** to interact with the OpenAI API for vectorization. You can set the API key as an environment variable or add it to the `application.properties` file.

### Option 1: Set the API Key as an Environment Variable

On **Linux/macOS**:
```bash
export SPRING_AI_OPENAI_API_KEY=your-openai-api-key
```

On **Windows**:
```bash
set SPRING_AI_OPENAI_API_KEY=your-openai-api-key
```

### Option 2: Add to `application.properties`

Alternatively, you can add the API key directly to `src/main/resources/application.properties`:

```properties
spring.ai.openai.api-key=your-openai-api-key
```

Replace `your-openai-api-key` with your actual OpenAI API key.

## Redis Setup

To run Redis with the necessary vector capabilities, you can use **Redis Stack** which supports vector similarity search.

### Install Redis with Docker

Use the following Docker command to run **Redis Stack**:

```bash
docker run -it --name redis-stack-server -p 6379:6379 redis/redis-stack-server:latest
```

This command will run Redis Stack on port `6379` with vector search capabilities.

### Redis Document Creation for Vector Storage

After setting up Redis, create the document schema for storing vector embeddings:

```bash
FT.CREATE documents ON HASH PREFIX 1 docs: SCHEMA doc_embedding VECTOR FLAT 6 TYPE FLOAT32 DIM 1536 DISTANCE_METRIC COSINE
```

This command sets up a Redis vector index with 1536-dimensional embeddings using cosine distance for similarity searches.

## Setup and Installation

### Step 1: Clone the Repository

```bash
git clone https://github.com/LegPro/springai-redis-vector-demo.git
cd springai-redis-vector-demo
```

### Step 2: Build and Run the Application

Use Maven to build and run the Spring Boot application:

```bash
mvn clean install
mvn spring-boot:run
```

The application will start and run on `http://localhost:8080`.

## API Endpoints

### 1. `/load`

- **Method**: `GET`
- **Description**: Reads the data, generates vector embeddings using OpenAI, and stores the vectors in Redis.
- **Example**:
    ```bash
    curl "http://localhost:8080/load"
    ```

- **Response**: Loads the document data, vectorizes it, and stores it in Redis.

### 2. `/search`

- **Method**: `GET`
- **Description**: Performs a similarity search based on vector embeddings stored in Redis.
- **Query Parameter**:
    - `message`: The query used to perform similarity search.
- **Example**:
    ```bash
    curl "http://localhost:8080/search?message=Find%20similar%20documents"
    ```

- **Response**: Returns a list of documents most similar to the query based on cosine similarity of the vector embeddings.

Example Response:
```
"Document 1 content, Document 2 content"
```

## How It Works

1. **Loading Data**: The `/load` endpoint generates vector embeddings for the input data using OpenAI, then stores these embeddings in Redis.
2. **Searching for Similar Documents**: The `/search` endpoint retrieves the most similar documents by performing a vector similarity search in Redis, using cosine distance.

## Project Structure

```bash
springai-redis-vector-demo/
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── redisvectordemo
│   │   │               └── controller
│   │   │                   └── AIController.java
│   └── resources
│       └── application.properties
├── pom.xml
└── README.md
```

- **AIController.java**: Contains the REST API endpoints to load data into Redis and perform similarity searches.
- **application.properties**: Configuration file for connecting to Redis and setting up the OpenAI API key.

## Redis Vector Details

- **Vector Storage**: Embeddings are stored in Redis using `FT.CREATE` with vector search support.
- **Vector Search**: Similarity searches are conducted using cosine distance on vector embeddings stored in Redis.
