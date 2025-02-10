package me.choicore.samples.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.Session;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SessionEventListener {
    @EventListener
    public void processSessionCreatedEvent(SessionCreatedEvent event) {
        Session session = event.getSession();
        log.info("[Session Created] ID: {}, Creation Time: {}",
                session.getId(),
                session.getCreationTime());
    }

    @EventListener
    public void processSessionDeletedEvent(SessionDeletedEvent event) {
        log.info("[Session Deleted] ID: {}", event.getSessionId());
        SecurityContextHolder.clearContext();
    }

    @EventListener
    public void processSessionDestroyedEvent(SessionDestroyedEvent event) {
        Session session = event.getSession();
        SecurityContext securityContext = session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if (securityContext != null && securityContext.getAuthentication() != null) {
            log.info("[Session Destroyed] ID: {}, User: {}, Last Access: {}",
                    session.getId(),
                    securityContext.getAuthentication().getName(),
                    session.getLastAccessedTime());
        }
    }

    @EventListener
    public void processSessionExpiredEvent(SessionExpiredEvent event) {
        Session session = event.getSession();
        SecurityContext securityContext = session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if (securityContext != null && securityContext.getAuthentication() != null) {
            log.info("[Session Expired] ID: {}, User: {}, Last Access: {}, MaxInactiveInterval: {}s",
                    session.getId(),
                    securityContext.getAuthentication().getName(),
                    session.getLastAccessedTime(),
                    session.getMaxInactiveInterval().getSeconds());
        }
    }
}