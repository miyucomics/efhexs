package miyucomics.efhexs

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.hex.HexActions
import miyucomics.efhexs.actions.OpGetSounds
import net.minecraft.registry.Registry

object EfhexsActions {
	fun init() {
		register("get_sounds", "aeede", HexDir.WEST, OpGetSounds)
	}

	private fun register(name: String, signature: String, startDir: HexDir, action: Action) =
		Registry.register(
			HexActions.REGISTRY, EfhexsMain.id(name),
			ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action)
		)
}