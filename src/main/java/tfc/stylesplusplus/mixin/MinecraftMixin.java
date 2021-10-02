package tfc.stylesplusplus.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.stylesplusplus.api.util.GeneralUtils;

@Mixin(MinecraftClient.class)
public class MinecraftMixin {
	@Shadow
	@Nullable
	public ClientWorld world;
	
	@Unique
	private static final GeneralUtils utils = new GeneralUtils();
	
	@Inject(at = @At("HEAD"), method = "tick")
	public void preTick(CallbackInfo ci) {
		if (world == null) utils.setTime(0);
		else utils.setTime(GeneralUtils.getTime() + 1);
	}
}
