package com.iotech.qualitrack.platform.inventory.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class InventoryConfiguration {
    @Bean
    public Clock inventoryClock() { return Clock.system(ZoneId.of("America/Lima")); }
}
