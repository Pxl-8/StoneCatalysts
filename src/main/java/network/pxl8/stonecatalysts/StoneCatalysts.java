package network.pxl8.stonecatalysts;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import network.pxl8.stonecatalysts.event.StoneGen;
import network.pxl8.stonecatalysts.lib.LibMeta;
import network.pxl8.stonecatalysts.proxy.Proxy;

import java.util.Arrays;

@Mod(modid = LibMeta.MOD_ID, version = LibMeta.VERSION, acceptableRemoteVersions = "*")
public class StoneCatalysts {
    @Mod.Instance
    public static StoneCatalysts instance;

    @SidedProxy(clientSide = LibMeta.CLIENT_PROXY, serverSide = LibMeta.SERVER_PROXY, modId = LibMeta.MOD_ID)
    private static Proxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        StoneGen.getCustomCatalysts();
        if (Loader.isModLoaded("jei")) { StoneGen.registerJEIRecipes(); }
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }
}
