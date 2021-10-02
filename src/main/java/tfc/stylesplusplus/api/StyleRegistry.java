package tfc.stylesplusplus.api;

import com.google.common.collect.ImmutableMap;
import net.minecraft.util.Identifier;
import tfc.stylesplusplus.builtin.PlaceholderStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class StyleRegistry {
	private static Map<Identifier, Supplier<ExtraStyle>> styles = new HashMap<>();
	
	private static final List<Runnable> styleFillers = new ArrayList<>();
	
	public static void register(Identifier name, Supplier<ExtraStyle> style) {
		styles.put(name, style);
	}
	
	protected static void lock() {
		styles = ImmutableMap.copyOf(styles);
	}
	
	protected static void reload() {
		styles = new HashMap<>();
		styleFillers.forEach(Runnable::run);
		lock();
	}
	
	public static void addStyleLoader(Runnable filler) {
		styleFillers.add(filler);
	}
	
	public static ExtraStyle get(Identifier registryName) {
		if (styles.containsKey(registryName)) return styles.get(registryName).get();
		return new PlaceholderStyle();
	}
}
