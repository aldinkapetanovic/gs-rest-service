package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

// / endpoint returns system info
    @GetMapping("/")
    public String systemInfo() {
        String hostname = getHostname();
        String javaVersion = System.getProperty("java.version");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        
        // HTML response with a link to the /greeting page
        return String.format(
            "<html><body>" +
            "<h1>System Information</h1>" +
            "<p>Hostname: %s</p>" +
            "<p>Java Version: %s</p>" +
            "<p>OS: %s %s</p>" +
            "<p><a href='/greeting'>Go to Greeting Page</a></p>" +
            "</body></html>",
            hostname, javaVersion, osName, osVersion
        );
    }

    // Utility method to get the hostname
    private String getHostname() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostName();
        } catch (UnknownHostException e) {
            return "Unknown Host";
        }
    }

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}
