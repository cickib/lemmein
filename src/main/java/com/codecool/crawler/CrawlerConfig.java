//package com.codecool.crawler;
//
//import lombok.Data;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//
//@Configuration
////@Data
//@PropertySource(name="x",
//        value="classpath:com/javarticles/spring/annotations/x.properties")
//public class CrawlerConfig {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
//
//
//
//
//        public static void main(String[] args) {
//            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//            try {
//                ctx.register(SpringPropertySourceAnnotationExample.class);
//                ctx.refresh();
//                Environment env = ctx.getEnvironment();
//                System.out.println("Topic: " + env.getProperty("topic"));
//            } finally {
//                ctx.close();
//            }
//        }
//
//
//    private String URL;
//    @Value("${company}")
//    private String company;
//    private String hrefClass;
//
//    private String streetClass;
//    private String addressClass;
//    private String rentClass;
//    private String sizeClass;
//    private String districtClass;
//    private String districtRegex;
//    private String blockClass;
//    private String sizeRegex;
////
////    public CrawlerConfig() {
////        System.out.println("environment = " + environment);
////        System.out.println("company = " + company);
////        URL = environment.getProperty("URL");
////    }
//}
