package plus.dragons.createcommutenetwork;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.trains.graph.EdgePointType;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import plus.dragons.createcommutenetwork.content.commute.train.stationPlatform.Platform;
import plus.dragons.createcommutenetwork.content.commuteNetwork.NetworkManager;
import plus.dragons.createcommutenetwork.entry.*;
import plus.dragons.createcommutenetwork.foundation.config.CcnConfigs;
import plus.dragons.createdragonlib.init.SafeRegistrate;
import plus.dragons.createdragonlib.lang.Lang;

@Mod(CommuteNetwork.ID)
public class CommuteNetwork {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "create_commute_network";
    public static final String NAME = "Create Commute Network";
    public static final CreateRegistrate REGISTRATE = new SafeRegistrate(ID);
    public static final Lang LANG = new Lang(ID);
    public static NetworkManager COMMUTE_NETWORK_MANAGER = new NetworkManager();
    public static final EdgePointType<Platform> PLATFORM =
            EdgePointType.register(genRL("platform"), Platform::new);

    private static CreativeModeTab CREATIVE_MODE_TAB;

    public CommuteNetwork() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        CcnConfigs.register(ModLoadingContext.get());

        registerEntries(modEventBus);
        modEventBus.addListener(CommuteNetwork::setup);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CommuteNetworkClient::new);
    }

    private void registerEntries(IEventBus modEventBus) {
        REGISTRATE.registerEventListeners(modEventBus);
        CcnItems.register();
        CcnBlocks.register();
        CcnBlockEntities.register();
        CREATIVE_MODE_TAB = new CcnCreativeTab();
    }


    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CcnPackets.registerPackets();
        });
    }

    public static ResourceLocation genRL(String name) {
        return new ResourceLocation(ID, name);
    }
}
