package miyucomics.efhexs.misc

class RingBuffer<T>(private val capacity: Int) : Iterable<T> {
	@Suppress("UNCHECKED_CAST")
	private val buffer = arrayOfNulls<Any?>(capacity) as Array<T?>
	private var head = 0
	private var count = 0

	val size: Int get() = count

	fun add(element: T) {
		val writeIndex = (head + count) % capacity
		buffer[writeIndex] = element
		if (count == capacity) {
			head = (head + 1) % capacity
		} else {
			count++
		}
	}

	fun get(index: Int): T {
		require(index in 0 until count) { "Index out of range: $index" }
		@Suppress("UNCHECKED_CAST")
		return buffer[(head + index) % capacity] as T
	}

	override fun iterator(): Iterator<T> = object : Iterator<T> {
		var i = 0
		override fun hasNext() = i < count
		override fun next(): T = get(i++)
	}
}