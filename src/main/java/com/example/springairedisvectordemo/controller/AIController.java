package com.example.springairedisvectordemo.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@RestController
public class AIController {

    private static final double SIMILARITY_THRESHOLD = 0.5;
    @Value("classpath:input.txt")
    private Resource resourceFile;

    VectorStore vectorStore;

    public AIController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @GetMapping("/load")
    public String load() throws IOException {
        List<Document> documents = Files.lines(resourceFile.getFile().toPath())
                .map(line -> new Document(line, Map.of("meta1", "meta2")))
                .toList();
        TextSplitter textSplitter = new TokenTextSplitter();
        for (Document document : documents) {
            List<Document> splitDocs = textSplitter.split(document);
            vectorStore.add(splitDocs);
            System.out.println("Document " + document.getContent() + " added to vector store");
        }
        return resourceFile.getFilename();
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "message", defaultValue = "Soothe Electric bike") String message) {

        List<Document> documents = vectorStore.similaritySearch(SearchRequest.query(message).withTopK(2).withSimilarityThreshold(SIMILARITY_THRESHOLD));

        return documents.toString();
    }
}
