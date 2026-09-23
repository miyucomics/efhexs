package miyucomics.efhexs.mixin;

import miyucomics.efhexs.c2s.ParticleC2S;
import miyucomics.efhexs.c2s.SoundC2S;
import miyucomics.efhexs.misc.PlayerEntityMinterface;
import miyucomics.efhexs.misc.RingBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements PlayerEntityMinterface {
	@Unique private final ArrayList<ParticleC2S> particles = new ArrayList<>(32);
	@Unique private final ArrayList<SoundC2S> sounds = new ArrayList<>(32);

	@Override
	public @NotNull ArrayList<ParticleC2S> getParticles() {
		return particles;
	}

	@Override
	public @NotNull ArrayList<SoundC2S> getSounds() {
		return sounds;
	}
}