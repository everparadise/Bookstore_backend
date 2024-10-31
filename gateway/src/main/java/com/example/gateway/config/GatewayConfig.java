package com.example.gateway.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "auth")
public class GatewayConfig {
    @Getter
    private List<String> excludedPaths;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public boolean isExcluded(String path) {
        for (String excluded: excludedPaths) {
            if (pathMatcher.match(excluded, path)) return true;
        }
        return false;
    }
}
