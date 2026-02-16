package com.example.taskboard.config;

// ============================================================================
// CORS Configuration for Spring Boot Backend
// ============================================================================
//
// PROBLEM THIS SOLVES:
// Your React frontend (localhost:5173) and Spring backend (localhost:8080) are on
// DIFFERENT PORTS. When the frontend calls fetch() to your backend API, the browser
// BLOCKS the request and shows a CORS error in the console. This happens because of
// the "Same-Origin Policy"—a browser security feature that prevents one website from
// secretly making requests to another website (to prevent cross-site attacks).
//
// THE SOLUTION:
// Tell Spring Boot: "Hey, I own BOTH the frontend and backend. Please add CORS headers
// to your responses that tell the browser this cross-origin request is safe." Spring
// will then send response headers like:
//   Access-Control-Allow-Origin: http://localhost:5173
//   Access-Control-Allow-Methods: GET, POST, PUT, DELETE, ...
//   Access-Control-Allow-Headers: *
// When the browser sees these headers, it allows the request.
//
// KEY LEARNING NOTES FOR FRONTEND ENGINEERS:
//
// 1. HTTP HEADERS: A request/response includes metadata called "headers" (like Content-Type,
//    Authorization). CORS is just browser validation of specific response headers. The browser
//    checks if the server said "yes, I allow requests from your origin."
//
// 2. SAME-ORIGIN POLICY: Two URLs are the "same origin" if they have the same protocol + domain + port.
//    http://localhost:5173 ≠ http://localhost:8080 → Different origins → CORS kicks in.
//    (In production, it's https://myapp.com ≠ https://api.myapp.com → Also different origins.)
//
// 3. WHY THIS CLASS EXISTS: Spring Boot doesn't allow cross-origin requests by default. This
//    config class is where you EXPLICITLY grant permission for specific frontends to call
//    your backend.
//
// 4. WEBMVCCONFIGURER INTERFACE: Spring provides many "Configurer" interfaces for customizing
//    different parts of the framework. WebMvcConfigurer is for web/HTTP behavior. By implementing
//    it, you get access to "hook" methods like addCorsMappings(). Think of it like a plugin system.
//
// 5. @CONFIGURATION ANNOTATION: This tells Spring "this class has setup logic." On startup,
//    Spring scans all classes with @Configuration and reads them. Without this annotation,
//    Spring ignores the class—even if it implements WebMvcConfigurer.
//
// 6. LOCALHOST PORTS: In development:
//    - localhost:5173 = Vite dev server (your React frontend)
//    - localhost:3000 = Alternative port (maybe for a deployed version or another app)
//    - localhost:8080 = Spring Boot backend (default)
//    In production, these would be real domains (e.g., myapp.com, api.myapp.com).
//
// 7. SECURITY TRADEOFF: Using "*" for methods/headers is convenient for dev but risky in
//    production. In production, specify exactly which methods (GET, POST) and headers you need.
//
// ============================================================================

// @Configuration: Spring annotation that marks this class as a configuration class.
// Think of it like a setup file—Spring reads this class once on startup and applies all the settings defined here.
import org.springframework.context.annotation.Configuration;

// WebMvcConfigurer: An interface that lets you customize Spring's web/HTTP behavior.
// By implementing this interface, we can hook into Spring's configuration and add our own rules (like CORS).
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// CorsRegistry: A utility provided by Spring that manages Cross-Origin Resource Sharing (CORS) rules.
// CORS is a browser security feature that normally blocks frontend requests to a different domain/port.
// We use CorsRegistry to tell Spring: "Allow my frontend at localhost:5173 to call my backend."
import org.springframework.web.servlet.config.annotation.CorsRegistry;

// @Configuration: This tells Spring Boot: "This class contains settings for the application. Read it on startup."
// Without this annotation, Spring would ignore this class.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // @Override: This method comes from the WebMvcConfigurer interface.
    // We're overriding it to provide our own CORS configuration instead of using Spring's defaults.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // registry.addMapping("/**"): Create a CORS rule that applies to ALL endpoints (/** means every path).
        // So every REST endpoint (/api/health, /greeting, etc.) will follow this rule.
        registry.addMapping("/**")

        // .allowedOrigins(...): Specify which domains/ports can make requests to your backend.
        // In frontend terms: this is like saying "only requests from these two addresses will succeed."
        // localhost:5173 is your Vite dev server (React frontend).
        // localhost:3000 is an alternate port (maybe for another app or production).
        .allowedOrigins("http://localhost:5173", "http://localhost:3000")

        // .allowCredentials(true): Allow cookies/authentication headers in cross-origin requests.
        // If set to false (default), the browser won't include cookies when calling your API.
        // Set to true if your frontend needs to send auth tokens or session cookies.
        .allowCredentials(true)

        // .allowedMethods("*"): Allow all HTTP methods (GET, POST, PUT, DELETE, PATCH, etc.).
        // The "*" is a wildcard—it's the backend equivalent of saying "no restrictions."
        // You could also specify specific methods: .allowedMethods("GET", "POST") for tighter security.
        .allowedMethods("*")

        // .allowedHeaders("*"): Allow any HTTP header the frontend sends.
        // Headers are metadata in HTTP requests (e.g., Content-Type, Authorization, custom headers).
        // Using "*" means no restrictions. In production, you might restrict this for security.
        .allowedHeaders("*");
    }
}
