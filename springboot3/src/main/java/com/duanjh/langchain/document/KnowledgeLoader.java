package com.duanjh.langchain.document;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-05-21 周四 10:09
 * @Version: v1.0
 * @Description: 知识库加载（启动加载指定目录下的文件）
 */
@lombok.extern.slf4j.Slf4j
@Slf4j
@Component
public class KnowledgeLoader {

    @Autowired
    @Qualifier("localOpenAiEmbeddingModel")
    EmbeddingModel  embeddingModel;

    @Autowired
    @Qualifier("memoryEmbeddingStore")
    EmbeddingStore  embeddingStore;

//    @PostConstruct
    public void loadDocument(){
        DocumentSplitter splitter= DocumentSplitters.recursive(300,50);
        try(Stream<Path> files = Files.walk(Path.of("./"))) {
            files.filter(p -> p.toString().endsWith(".md")).forEach(p -> {
                    Document doc = FileSystemDocumentLoader.loadDocument(p);
                    List<TextSegment> segs = splitter.split(doc);
                    List<Embedding> embs = embeddingModel.embedAll(segs).content();
                    embeddingStore.addAll(embs, segs);
                }
            );
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
