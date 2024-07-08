package ru.brikster.chatty.chat.component.impl;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class InternalPlaceholdersReplacementsStringTransformer implements ReplacementsStringTransformer {

    @Override
    public String transform(@NotNull OfflinePlayer sender, @NotNull String message) {
        return message.replace("{player}", sender instanceof Player
                ? ((Player) sender).getDisplayName()
                : Objects.requireNonNull(sender.getName(), "Player name cannot be null"));
    }

}
