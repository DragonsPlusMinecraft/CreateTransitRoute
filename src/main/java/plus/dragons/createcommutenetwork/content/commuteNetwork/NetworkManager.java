package plus.dragons.createcommutenetwork.content.commuteNetwork;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import org.apache.commons.lang3.mutable.MutableObject;
import plus.dragons.createcommutenetwork.CommuteNetwork;
import plus.dragons.createcommutenetwork.CommuteNetworkClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NetworkManager {
    public Map<UUID,Route> allRoutes;
    public Map<UUID,Station> allStations;
    public NetworkSync sync;
    NetworkSavedData savedData;

    public NetworkManager() {
        cleanUp();
    }

    public void onPlayerLogin(Player player) { //TODO
        var manager = CommuteNetwork.COMMUTE_NETWORK_MANAGER;
        if (player instanceof ServerPlayer serverPlayer) {
            manager.loadNetworkData(serverPlayer.getServer());
            this.sync.sendAllRouteTo(allRoutes.values(),serverPlayer);
            this.sync.sendAllStationTo(allStations.values(),serverPlayer);
        }
    }

    public void onPlayerLogout(Player player) {
        CommuteNetworkClient.COMMUTE_NETWORK_MANAGER.cleanUp();
    }

    public void onWorldLoad(LevelAccessor level) {
        var manager = CommuteNetwork.COMMUTE_NETWORK_MANAGER;
        MinecraftServer server = level.getServer();
        if (server == null || server.overworld() != level)
            return;
        manager.cleanUp();
        manager.savedData = null;
        manager.loadNetworkData(server);
    }

    private void loadNetworkData(MinecraftServer server) {
        if (savedData != null)
            return;
        this.savedData = NetworkSavedData.load(server);
        this.allRoutes = savedData.getRoutes();
        this.allStations = savedData.getStations();
    }

    public void cleanUp() {
        this.allRoutes = new HashMap<>();
        this.allStations = new HashMap<>();
        this.sync = new NetworkSync();
    }

    public void markDirty() {
        if (this.savedData != null)
            this.savedData.setDirty();
    }

    public NetworkManager sided(LevelAccessor level) {
        if (level != null && !level.isClientSide())
            return this;
        MutableObject<NetworkManager> m = new MutableObject<>();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> clientManager(m));
        return m.getValue();
    }

    @OnlyIn(Dist.CLIENT)
    private void clientManager(MutableObject<NetworkManager> m) {
        m.setValue(CommuteNetworkClient.COMMUTE_NETWORK_MANAGER);
    }
}
