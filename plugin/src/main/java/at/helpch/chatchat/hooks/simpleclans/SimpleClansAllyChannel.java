package at.helpch.chatchat.hooks.simpleclans;

import at.helpch.chatchat.api.holder.FormatsHolder;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.ResidentList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
    protected @Nullable ResidentList residentList(@NotNull final Resident resident) {
        return resident.getNationOrNull();
    }
}
