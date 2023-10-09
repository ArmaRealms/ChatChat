package at.helpch.chatchat.hooks.simpleclans;

import at.helpch.chatchat.api.holder.FormatsHolder;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class SimpleClansAllyChannel extends AbstractSimpleClansChannel {

    public SimpleClansAllyChannel(@NotNull final String name,
                                  @NotNull final String messagePrefix,
                                  @NotNull final List<String> toggleCommands,
                                  @NotNull final String channelPrefix,
                                  @NotNull final FormatsHolder formats,
                                  final int radius) {
        super(name, messagePrefix, toggleCommands, channelPrefix, formats, radius);
    }

    @Override
    protected @NotNull Set<ClanPlayer> clanPlayerList(@NotNull final ClanPlayer clanPlayer) {
        final Clan clan = clanPlayer.getClan();
        if (clan == null) return Set.of();
        Set<ClanPlayer> clanPlayerSet = new HashSet<>(clan.getAllAllyMembers());
        clanPlayerSet.addAll(clan.getOnlineMembers());
        return clanPlayerSet.stream().filter(cp -> cp.toPlayer() != null).collect(Collectors.toSet());
    }

}
