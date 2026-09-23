package miyucomics.efhexs.c2s

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
import miyucomics.efhexs.EfhexsMain
import miyucomics.efhexs.misc.RingBuffer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.client.particle.Particle
import net.minecraft.client.sound.SoundInstance
import net.minecraft.particle.ParticleEffect
import net.minecraft.registry.Registries
import net.minecraft.util.math.Vec3d

object ClientState {
	private var incrementingID = 0L
	private val particleLookup = Long2ObjectOpenHashMap<Particle>()
	private val particles = RingBuffer<ParticleC2S>(128)
	private val sounds = RingBuffer<SoundC2S>(128)

	@JvmStatic
	fun pushParticle(type: ParticleEffect, particle: Particle) {
		particles.add(ParticleC2S(Registries.PARTICLE_TYPE.getId(type.type)!!, Vec3d(particle.x, particle.y, particle.z), Vec3d(particle.velocityX, particle.velocityY, particle.velocityZ), particle.age, incrementingID))
		particleLookup[incrementingID] = particle
		incrementingID += 1
	}

	@JvmStatic
	fun pushSound(sound: SoundInstance) {
		sounds.add(SoundC2S(sound.id, sound.category, Vec3d(sound.x, sound.y, sound.z), sound.volume.toDouble(), sound.volume.toDouble()))
	}

	fun tick() {
		val buf = PacketByteBufs.create()

		buf.writeInt(particles.size)
		for (particle in particles)
			particle.write(buf)

		buf.writeInt(sounds.size)
		for (sound in sounds)
			sound.write(buf)

		ClientPlayNetworking.send(EfhexsMain.C2S_CHANNEL, buf)

		particleLookup.values.removeIf { !it.isAlive }
	}
}