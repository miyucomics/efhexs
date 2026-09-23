package miyucomics.efhexs.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import miyucomics.efhexs.ClientState;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
	@WrapMethod(method = "createParticle")
	private <T extends ParticleEffect> Particle onCreateParticle(T particleEffect, double d, double e, double f, double g, double h, double i, Operation<Particle> original) {
		Particle x = original.call(particleEffect, d, e, f, g, h, i);
		ClientState.pushParticle(particleEffect, x);
		return x;
	}
}