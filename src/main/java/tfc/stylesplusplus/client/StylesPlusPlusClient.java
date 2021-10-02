package tfc.stylesplusplus.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.minecraft.item.ToolItem;
import net.minecraft.resource.ResourceType;
import tfc.stylesplusplus.api.ReloadListener;

@Environment(net.fabricmc.api.EnvType.CLIENT)
public class StylesPlusPlusClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ResourceManagerHelperImpl.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new ReloadListener());
	}
}
