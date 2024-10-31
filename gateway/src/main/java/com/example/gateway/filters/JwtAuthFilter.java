package com.example.gateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final GatewayConfig config;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest fluxRequest = exchange.getRequest();
        log.debug("Request Method: {}", fluxRequest.getMethod());
        log.debug("Request URL: {}", fluxRequest.getPath());

        /* check if excluded */
        if (fluxRequest.getMethod() == HttpMethod.OPTIONS || config.isExcluded(fluxRequest.getPath().toString())) {
            /* ignore authorization */
            return chain.filter(exchange);
        }

        /* check authorization token */
        try {
            HttpHeaders headers = fluxRequest.getHeaders();
            List<String> auths = headers.get(Auth.AuthFilterHeader);
            if (auths == null || auths.isEmpty()) {
                throw new InvalidJwt(Auth.NoAuthHeader);
            }
            String authHeader = auths.get(0);

            /* Get the content of JWT (compact) */
            final String[] tokens = authHeader.split(" ");
            if (tokens.length < 2) {
                /* Invalid token */
                throw new InvalidJwt(Auth.InvalidAuthHeader);
            }
            final String authToken = authHeader.split(" ")[1];
            if (!JwtUtils.isAuthTokenValid(authToken)) {
                /* Invalid token */
                throw new InvalidJwt(Auth.InvalidAuthHeader);
            }

            /* Auth success */
            JWT curJwt = JwtUtils.parseToken(authToken);
            final long tokenUid = Long.parseLong(JwtUtils.getAuthTokenPayload(curJwt, Auth.JwtUidFieldName));
            final String tokenUsername = JwtUtils.getAuthTokenPayload(curJwt, Auth.JwtUsernameFieldName);

            /* check token expire */
            /* refresh exemption (check it later at entrypoint) */
            final int tokenIsRefresh = Integer.parseInt(JwtUtils.getAuthTokenPayload(curJwt, Auth.JwtIsRefreshFieldName));
            final long tokenExpire = Long.parseLong(JwtUtils.getAuthTokenPayload(curJwt, Auth.JwtExpireFieldName));

            if (tokenIsRefresh == 0 && System.currentTimeMillis() > tokenExpire) {
                throw new InvalidJwt(Auth.ExpiredJwtMsg);
            }

            /* Pass userInfo to microservice */
            exchange.mutate()
                    .request(builder -> builder.header(Auth.JwtUidFieldName, Long.toString(tokenUid)))
                    .request(builder -> builder.header(Auth.JwtUsernameFieldName, tokenUsername))
                    .build();

            return chain.filter(exchange);

        } catch (Exception exception) {
            String msg = exception.getMessage();
            log.warn(msg);
            /* encode message into bytes */
            ResponseDto<Boolean> resp = new ResponseDto<>(false, msg, false);
            byte[] bytes;
            try {
                bytes = objectMapper.writeValueAsBytes(resp);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            /* set status code */
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            /* set content type */
            exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            return exchange.getResponse().writeWith(Flux.just(buffer));
        }
    }

    @Override
    public int getOrder() {
        return Auth.JwtAuthFilterPrior;
    }
}
