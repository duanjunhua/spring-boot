package com.config.config.annotate;

import java.lang.annotation.Annotation;

import org.reflections.Reflections;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Michael
 * @Date: 2018年12月6日_下午4:32:06
 * @Version: v0.1
 */
@Component
public class ScanAnnotationRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		new Reflections("org.springframework")
        .getSubTypesOf(Annotation.class)
        .stream()
        .map(clazz->clazz.getName())
        .sorted()
        .forEach(System.out::println);
	}

}
