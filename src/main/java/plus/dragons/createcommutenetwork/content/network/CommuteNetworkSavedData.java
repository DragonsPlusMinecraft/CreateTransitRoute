package plus.dragons.createcommutenetwork.content.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

public class CommuteNetworkSavedData extends SavedData {
    CommuteNetwork network = new CommuteNetwork();

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        // TODO
        return compoundTag;
    }

    private static CommuteNetworkSavedData load(CompoundTag compoundTag) {
        CommuteNetworkSavedData ret = new CommuteNetworkSavedData();
        // TODO
        return ret;
    }

    private CommuteNetworkSavedData() {
    }

    public static CommuteNetworkSavedData load(MinecraftServer server) {
        return server.overworld()
                .getDataStorage()
                .computeIfAbsent(CommuteNetworkSavedData::load, CommuteNetworkSavedData::new, "train_network");
    }
}
