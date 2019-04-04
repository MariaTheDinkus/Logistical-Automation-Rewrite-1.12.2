package com.zundrel.logisticalautomation;

import com.zundrel.logisticalautomation.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = LogisticalAutomation.MOD_ID, name = LogisticalAutomation.MOD_NAME, version = LogisticalAutomation.MOD_VERSION, dependencies = "required-after:patchouli", useMetadata = true)
public class LogisticalAutomation
{
    public static final String MOD_ID = "logisticalautomation";
    public static final String MOD_NAME = "Logistical Automation";
    public static final String MOD_VERSION = "0.2.0";

    @SidedProxy(clientSide = "com.zundrel.logisticalautomation.ClientProxy", serverSide = "com.zundrel.logisticalautomation.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static LogisticalAutomation instance;

    private static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
