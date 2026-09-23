package miyucomics.efhexs

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.api.utils.asInt
import miyucomics.efhexs.c2s.SoundC2S
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtOps
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import kotlin.enums.enumEntries

class SoundIota(val sound: SoundC2S) : Iota(TYPE, sound) {
	override fun isTruthy() = true
	override fun toleratesOther(that: Iota) = (typesMatch(this, that) && that is SoundIota) && this.sound == that.sound
	override fun serialize(): NbtElement = SoundC2S.CODEC.encodeStart(NbtOps.INSTANCE, sound).get().orThrow()

	companion object {
		val TYPE: IotaType<SoundIota> = object : IotaType<SoundIota>() {
			override fun color() = -1
			override fun deserialize(tag: NbtElement, world: ServerWorld) = SoundIota(SoundC2S.CODEC.parse(NbtOps.INSTANCE, tag).get().orThrow())
			override fun display(tag: NbtElement) = SoundC2S.CODEC.parse(NbtOps.INSTANCE, tag).get().orThrow().subtitle
		}
	}
}

fun List<Iota>.getSound(idx: Int, argc: Int = 0): SoundC2S {
	val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
	if (x is SoundIota)
		return x.sound
	throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "sound")
}