package ru.brikster.chatty.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@UtilityClass
public class EventUtil {

    private final static Cache<Class<?>, MethodHandle> EVENT_CLASS_METHOD_HANDLE_CACHE = CacheBuilder
            .newBuilder()
            .expireAfterAccess(Duration.ofMinutes(30))
            .build();

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

    public void unregisterListeners(Class<?> eventClass, Plugin plugin) {
        try {
            MethodHandle methodHandle = EVENT_CLASS_METHOD_HANDLE_CACHE.get(eventClass, () -> MethodHandles.publicLookup()
                    .findStatic(eventClass, "getHandlerList", MethodType.methodType(HandlerList.class)));

            HandlerList handlerList = (HandlerList) methodHandle.invoke();
            handlerList.unregister(plugin);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

}
