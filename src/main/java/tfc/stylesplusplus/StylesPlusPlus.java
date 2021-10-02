package tfc.stylesplusplus;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import tfc.stylesplusplus.api.ReloadListener;
import tfc.stylesplusplus.api.StyleRegistry;
import tfc.stylesplusplus.builtin.WavyStyle;

public class StylesPlusPlus implements ModInitializer {
	public StylesPlusPlus() {
	}
	
	@Override
	public void onInitialize() {
		StyleRegistry.addStyleLoader(StylesPlusPlus::registerBuiltins);
		ResourceManagerHelperImpl.get(ResourceType.SERVER_DATA).registerReloadListener(new ReloadListener());
	}
	
	public static void registerBuiltins() {
		StyleRegistry.register(new Identifier("stylesplusplus:wavy"), WavyStyle::new);
	}
}
