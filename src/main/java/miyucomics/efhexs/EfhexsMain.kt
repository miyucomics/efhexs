package miyucomics.efhexs

import miyucomics.efhexs.c2s.ParticleC2S
import miyucomics.efhexs.c2s.SoundC2S
import miyucomics.efhexs.misc.PlayerEntityMinterface
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.util.Identifier

class EfhexsMain : ModInitializer {
	override fun onInitialize() {
		EfhexsActions.init()

		ServerPlayNetworking.registerGlobalReceiver(C2S_CHANNEL) { server, player, _, buf, _ ->
			val particleCount = buf.readVarInt()
			val outerParticles = ArrayList<ParticleC2S>(particleCount)
			repeat(particleCount) { outerParticles.add(ParticleC2S.read(buf)) }

			val soundCount = buf.readVarInt()
			val outerSounds = ArrayList<SoundC2S>(soundCount)
			repeat(soundCount) { outerSounds.add(SoundC2S.read(buf)) }

			server.execute {
				val particles = (player as PlayerEntityMinterface).getParticles()
				particles.clear()
				outerParticles.forEach(particles::add)

				val sounds = (player as PlayerEntityMinterface).getSounds()
				sounds.clear()
				outerSounds.forEach(sounds::add)
			}
		}
	}

	companion object {
		fun id(string: String) = Identifier("efhexs", string)
		val C2S_CHANNEL = id("c2s")
	}
}