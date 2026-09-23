package miyucomics.efhexs

import miyucomics.efhexs.c2s.ClientState
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents

class EfhexsClient : ClientModInitializer {
	override fun onInitializeClient() {
		ClientTickEvents.END_WORLD_TICK.register {
			ClientState.tick()
		}
	}
}