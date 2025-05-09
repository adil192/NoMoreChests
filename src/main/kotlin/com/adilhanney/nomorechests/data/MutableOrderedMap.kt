package com.adilhanney.nomorechests.data

class MutableOrderedMap<K, V> {
  private val _keys = mutableListOf<K>()
  private val _map = mutableMapOf<K, V>()

  val keys: List<K> get() = _keys

  /**
   * Sets the value of the given [key] to the given [value] in the [_map].
   *
   * @return `true` if the value was updated, `false` if the new [value] was already set
   */
  operator fun set(key: K, value: V): Boolean {
    val prevValue = _map[key]
    if (prevValue == value) return false
    if (prevValue == null) {
      // Key was not present
      _keys.add(key)
    }
    _map[key] = value
    return true
  }

  /**
   * @return The value associated with the given [key], or `null` if the [key] is not present
   */
  operator fun get(key: K): V? = _map[key]

  /**
   * Removes the entry with the given [key] from the [_map].
   *
   * @return The value that was associated with the given [key], or `null` if the [key] was not present
   */
  fun remove(key: K): V? {
    val value = _map.remove(key)
    _keys.remove(key)
    return value
  }

  /**
   * Clears all entries from the map and the list of keys.
   *
   * @return `true` if any entries were removed, `false` if the map was already empty
   */
  fun clear(): Boolean {
    if (_map.isEmpty()) return false
    _keys.clear()
    _map.clear()
    return true
  }
}
