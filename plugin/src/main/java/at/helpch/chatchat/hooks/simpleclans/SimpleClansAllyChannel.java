package at.helpch.chatchat.hooks.simpleclans;

import at.helpch.chatchat.api.holder.FormatsHolder;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.Rank;
import org.jetbrains.annotations.NotNull;

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
        Set<ClanPlayer> clanPlayerSet = clan.getAllAllyMembers().stream()
            .filter(cp -> cp.toPlayer() != null)
            .filter(cp -> hasAllyRankPermission(clan, cp))
            .collect(Collectors.toSet());
        clanPlayerSet.addAll(clan.getOnlineMembers().stream()
            .filter(cp -> cp.toPlayer() != null)
            .filter(cp -> hasAllyRankPermission(clan, cp))
            .collect(Collectors.toSet()));
        return clanPlayerSet;
    }

    private boolean hasAllyRankPermission(@NotNull final Clan clan, @NotNull final ClanPlayer clanPlayer) {
        if (clanPlayer.hasRank()) {
            final Rank rank = clan.getRank(clanPlayer.getRankId());
            return rank.getPermissions().contains("ally.chat");
        }
        return clanPlayer.isTrusted() || clanPlayer.isLeader();
    }

}
