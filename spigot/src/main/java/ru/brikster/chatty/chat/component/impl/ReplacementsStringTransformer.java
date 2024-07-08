package ru.brikster.chatty.chat.component.impl;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public interface ReplacementsStringTransformer {

    String transform(@NotNull OfflinePlayer sender, @NotNull String message);

}
