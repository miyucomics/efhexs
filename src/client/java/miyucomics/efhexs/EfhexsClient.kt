package miyucomics.efhexs

import miyucomics.efhexs.c2s.SoundC2S
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.text.Text
import net.minecraft.util.math.Vec3d

class EfhexsClient : ClientModInitializer {
	override fun onInitializeClient() {
		ClientLifecycleEvents.CLIENT_STARTED.register { client ->
			client.soundManager.registerListener { sound, subtitleProvider ->
				val name = (subtitleProvider.subtitle ?: Text.literal(sound.id.toString())).copy()
				val category = Text.translatable("soundCategory.${sound.category.name}")
				ClientState.sounds.add(SoundC2S(sound.id, sound.category, Vec3d(sound.x, sound.y, sound.z), sound.volume.toDouble(), sound.volume.toDouble(), Text.translatable("subtitle.efhex.sound_iota", name, sound.volume, sound.pitch, category)))
			}
		}

		ClientTickEvents.END_WORLD_TICK.register {
			ClientState.tick()
		}
	}
}