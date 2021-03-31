package by.ralovets.qportal.controller.util;

import by.ralovets.qportal.exception.WebsocketConnectionException;
import by.ralovets.qportal.sequrity.jwt.JwtUtils;
import by.ralovets.qportal.sequrity.service.UserDetailsServiceImpl;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.MultiValueMap;

import static java.util.Objects.isNull;

/**
 * Used to authorize users connected via websocket.
 */
@NoArgsConstructor
public class WebsocketInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final String MSG_INVALID_TOKEN = "Json web token is invalid! Connection wasn't established.";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        if (!getMessageType(message).equals(SimpMessageType.CONNECT)) {
            return message;
        }

        String jwt = parseJwt(message);

        if (jwtUtils.validateJwtToken(jwt)) {
            String email = jwtUtils.getEmailFromJwtToken(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new WebsocketConnectionException(MSG_INVALID_TOKEN);
        }

        return message;
    }

    private SimpMessageType getMessageType(Message<?> message) {
        Object messageTypeHeader = message.getHeaders().get("simpMessageType");
        if (isNull(messageTypeHeader)) return null;

        return SimpMessageType.valueOf(messageTypeHeader.toString());
    }

    private String parseJwt(Message<?> message) {
        MultiValueMap headers = message.getHeaders().get(StompHeaderAccessor.NATIVE_HEADERS, MultiValueMap.class);
        if (isNull(headers)) return null;

        Object rawJwt = headers.get("jwt-token");
        if (isNull(rawJwt)) return null;

        return rawJwt.toString()
                .replaceAll("[\\[\\]]", "");
    }
}