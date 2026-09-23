package miyucomics.efhexs.misc

import miyucomics.efhexs.c2s.ParticleC2S
import miyucomics.efhexs.c2s.SoundC2S

interface PlayerEntityMinterface {
	fun getParticles(): ArrayList<ParticleC2S>
	fun getSounds(): ArrayList<SoundC2S>
}