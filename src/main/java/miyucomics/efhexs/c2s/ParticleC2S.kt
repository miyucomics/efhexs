package miyucomics.efhexs.c2s

import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.PacketByteBuf
import net.minecraft.particle.ParticleType
import net.minecraft.registry.Registries
import net.minecraft.util.math.Vec3d

data class ParticleC2S(val type: ParticleType<*>, val position: Vec3d, val velocity: Vec3d, val age: Int, val id: Long) {
	fun write(buf: PacketByteBuf) {
		buf.writeInt(Registries.PARTICLE_TYPE.getRawId(type))
		buf.writeDouble(position.x)
		buf.writeDouble(position.y)
		buf.writeDouble(position.z)
		buf.writeDouble(velocity.x)
		buf.writeDouble(velocity.y)
		buf.writeDouble(velocity.z)
		buf.writeVarInt(age)
		buf.writeLong(id)
	}

	companion object {
		val CODEC: Codec<ParticleC2S> = RecordCodecBuilder.create { instance ->
			instance.group(
				Codec.INT.comapFlatMap({ DataResult.success(Registries.PARTICLE_TYPE.getOrThrow(it)) }, Registries.PARTICLE_TYPE::getRawId).fieldOf("type").forGetter(ParticleC2S::type),
				Vec3d.CODEC.fieldOf("position").forGetter(ParticleC2S::position),
				Vec3d.CODEC.fieldOf("velocity").forGetter(ParticleC2S::velocity),
				Codec.INT.fieldOf("age").forGetter(ParticleC2S::age),
				Codec.LONG.fieldOf("id").forGetter(ParticleC2S::id)
			).apply(instance, ::ParticleC2S)
		}

		fun read(buf: PacketByteBuf): ParticleC2S = ParticleC2S(
			type = Registries.PARTICLE_TYPE.getOrThrow(buf.readInt()),
			position = Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble()),
			velocity = Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble()),
			age = buf.readVarInt(),
			id = buf.readLong()
		)
	}
}