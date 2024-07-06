package ru.brikster.chatty.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.concurrent.CompletableFuture;

@UtilityClass
public class EventUtil {

    @SneakyThrows
    public void callAsynchronously(Event event) {
        if (Bukkit.isPrimaryThread()) {
            Throwable throwable = CompletableFuture
                    .supplyAsync(() -> {
                        Bukkit.getPluginManager().callEvent(event);
                        return (Throwable) null;
                    })
                    .exceptionally(t -> t)
                    .join();
            if (throwable != null) {
                throw throwable;
            }
            return;
        }
        Bukkit.getPluginManager().callEvent(event);
    }

}
