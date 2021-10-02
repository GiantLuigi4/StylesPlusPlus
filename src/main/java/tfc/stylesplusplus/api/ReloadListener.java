package tfc.stylesplusplus.api;

import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

// https://gist.github.com/quat1024/2645637708b9577a57671df0eab953e2
public class ReloadListener implements SimpleResourceReloadListener<Object> {
	@Override
	public Identifier getFabricId() {
		return new Identifier("stylesplusplus:idk_why_fapi_requires_this");
	}
	
	@Override
	public CompletableFuture<Object> load(ResourceManager manager, Profiler profiler, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			StyleRegistry.reload();
			return true;
		}, executor);
	}
	
	@Override
	public CompletableFuture<Void> apply(Object data, ResourceManager manager, Profiler profiler, Executor executor) {
		return CompletableFuture.runAsync(() -> {
		}, executor);
	}
}
