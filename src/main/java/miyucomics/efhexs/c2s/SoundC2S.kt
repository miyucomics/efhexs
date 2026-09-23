package miyucomics.efhexs.c2s

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.netty.buffer.ByteBuf
import net.minecraft.network.PacketByteBuf
import net.minecraft.sound.SoundCategory
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d

data class SoundC2S(val type: Identifier, val category: SoundCategory, val position: Vec3d, val volume: Double, val pitch: Double) {
	fun write(buf: PacketByteBuf) {
		buf.writeIdentifier(type)
		buf.writeVarInt(category.ordinal)
		buf.writeDouble(position.x)
		buf.writeDouble(position.y)
		buf.writeDouble(position.z)
		buf.writeDouble(volume)
		buf.writeDouble(pitch)
	}

	companion object {
		val CODEC: Codec<SoundC2S> = RecordCodecBuilder.create { instance ->
			instance.group(
				Identifier.CODEC.fieldOf("type").forGetter(SoundC2S::type),
				Codec.STRING.fieldOf("category").xmap({ category -> SoundCategory.valueOf(category) }, { category ->  category.name }).forGetter(SoundC2S::category),
				Vec3d.CODEC.fieldOf("position").forGetter(SoundC2S::position),
				Codec.DOUBLE.fieldOf("volume").forGetter(SoundC2S::volume),
				Codec.DOUBLE.fieldOf("pitch").forGetter(SoundC2S::pitch)
			).apply(instance, ::SoundC2S)
		}

		fun read(buf: PacketByteBuf): SoundC2S = SoundC2S(
			type = buf.readIdentifier(),
			category = SoundCategory.entries[buf.readVarInt()],
			position = Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble()),
			volume = buf.readDouble(),
			pitch = buf.readDouble()
		)
	}
}