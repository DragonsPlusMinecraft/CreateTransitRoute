package plus.dragons.createcommutenetwork.content.logistics.network;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.fml.DistExecutor;
import org.apache.commons.lang3.mutable.MutableObject;
import plus.dragons.createcommutenetwork.DragonTransit;
import plus.dragons.createcommutenetwork.DragonTransitClient;

public class CommuteNetworkManager {
    //public DTNetwork network = new DTNetwork();
    CommuteNetworkSavedData savedData;

    public CommuteNetworkManager() {
        cleanUp();
    }

    @SuppressWarnings("ConstantConditions")
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        var manager = DragonTransit.COMMUTE_NETWORK_MANAGER;
        if (player instanceof ServerPlayer serverPlayer) {
            manager.loadRouteData(serverPlayer.getServer());
            //DTNetworkSyncPacket.Initialize.of(manager.network)
            //        .forEach(packet -> CcnPackets.channel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), packet));
        }
    }

    public static void onClientPlayerLeave(ClientPlayerNetworkEvent.LoggingOut event) {
        DragonTransitClient.COMMUTE_NETWORK_MANAGER.cleanUp();
    }

    public static void onLoadWorld(LevelEvent.Load event) {
        LevelAccessor level = event.getLevel();
        var manager = DragonTransit.COMMUTE_NETWORK_MANAGER;
        MinecraftServer server = level.getServer();
        if (server == null || server.overworld() != level)
            return;
        manager.cleanUp();
        manager.savedData = null;
        manager.loadRouteData(server);
    }

    private void loadRouteData(MinecraftServer server) {
        if (savedData != null)
            return;
        savedData = CommuteNetworkSavedData.load(server);
        //network = savedData.network;
    }

    public void cleanUp() {
        //network = new DTNetwork();
    }

    public void markDirty() {
        if (savedData != null)
            savedData.setDirty();
    }

    public CommuteNetworkManager sided(LevelAccessor level) {
        if (level != null && !level.isClientSide())
            return this;
        MutableObject<CommuteNetworkManager> m = new MutableObject<>();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> clientManager(m));
        return m.getValue();
    }

    @OnlyIn(Dist.CLIENT)
    private void clientManager(MutableObject<CommuteNetworkManager> m) {
        m.setValue(DragonTransitClient.COMMUTE_NETWORK_MANAGER);
    }
}
