package com.example.microserviceb.config;

import com.netflix.discovery.DiscoveryClient;
import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
public class TomcatConfig {
    private final static Logger logger = LoggerFactory.getLogger(TomcatConfig.class);
    private final static AtomicBoolean finish = new AtomicBoolean(false);


    @Bean
    public GracefulShutdown get() {
        return new GracefulShutdown();
    }

    private static class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {


        private volatile Connector connector;
        private final int waitTime = 30;

        @Override
        public void customize(Connector connector) {
            this.connector = connector;
        }

        @Override
        public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
            if (finish.compareAndSet(false, true)) {
                DiscoveryClient discoveryClient = contextClosedEvent.getApplicationContext().getBean(DiscoveryClient.class);
                discoveryClient.shutdown();
                try {
                    Thread.sleep(17000);
                } catch (InterruptedException e) {
                }
                this.connector.pause();
//                try {
//                    Thread.sleep(6000);
//                } catch (InterruptedException e) {
//                }
                Executor executor = this.connector.getProtocolHandler().getExecutor();
                if (executor instanceof ThreadPoolExecutor) {
                    try {
                        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                        threadPoolExecutor.shutdown();
                        if (!threadPoolExecutor.awaitTermination(waitTime, TimeUnit.SECONDS)) {
                            logger.warn("Tomcat thread pool did not shut down gracefully within " + waitTime + " seconds. Proceeding with forceful shutdown");
                        }
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}
