package ru.brikster.chatty.adventure;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;

@SuppressWarnings("rawtypes")
public final class NativeBukkitAudienceProvider implements BukkitAudiences {

    @Override
    public @NotNull Audience all() {
        //noinspection unchecked
        List<Audience> audienceList = new ArrayList<>(
                (Collection) Bukkit.getOnlinePlayers());
        audienceList.add((Audience) Bukkit.getConsoleSender());
        return Audience.audience(audienceList);
    }

    @Override
    public @NotNull Audience console() {
        return (Audience) Bukkit.getConsoleSender();
    }

    @Override
    public @NotNull Audience players() {
        //noinspection unchecked
        return Audience.audience(
                (Collection) Bukkit.getOnlinePlayers());
    }

    @Override
    public @NotNull Audience player(@NotNull UUID playerId) {
        return Audience.audience((Audience) Objects.requireNonNull(Bukkit.getPlayer(playerId),
                "Cannot find player"));
    }

    @Override
    public @NotNull Audience permission(@NotNull String permission) {
        List<CommandSender> audienceList = new ArrayList<>(
                Bukkit.getOnlinePlayers());
        audienceList.add(Bukkit.getConsoleSender());
        audienceList.removeIf(sender -> !sender.hasPermission(permission));
        //noinspection unchecked
        return Audience.audience((Collection) audienceList);
    }

    @Override
    public @NotNull Audience world(@NotNull Key world) {
        List<Player> audienceList = new ArrayList<>(
                Bukkit.getOnlinePlayers());
        audienceList.removeIf(player -> !player.getWorld().getName().equals(world.asString()));
        //noinspection unchecked
        return Audience.audience((Collection) audienceList);
    }

    @Override
    public @NotNull Audience server(@NotNull String serverName) {
        return Audience.empty();
    }

    @Override
    public @NotNull ComponentFlattener flattener() {
        return ComponentFlattener.basic();
    }

    @Override
    public void close() {

    }

    @Override
    public @NotNull Audience sender(@NotNull CommandSender sender) {
        return Audience.audience((Audience) sender);
    }

    @Override
    public @NotNull Audience player(@NotNull Player player) {
        return sender(player);
    }

    @Override
    public @NotNull Audience filter(@NotNull Predicate<CommandSender> filter) {
        List<CommandSender> audienceList = new ArrayList<>(
                Bukkit.getOnlinePlayers());
        audienceList.add(Bukkit.getConsoleSender());
        audienceList.removeIf(sender -> !filter.test(sender));
        //noinspection unchecked
        return Audience.audience((Collection) audienceList);
    }

}
