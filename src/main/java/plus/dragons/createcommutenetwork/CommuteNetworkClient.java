package plus.dragons.createcommutenetwork;

import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import plus.dragons.createcommutenetwork.content.commuteNetwork.NetworkManager;

public class CommuteNetworkClient {

    public static NetworkManager COMMUTE_NETWORK_MANAGER = new NetworkManager();

    public CommuteNetworkClient() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
    }

    @SubscribeEvent
    public static void onJoin(ClientPlayerNetworkEvent.LoggingIn event) {
        //CreateClient.checkGraphicsFanciness();
    }
}
