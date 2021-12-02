package luiz787.uniquedigit.core;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class LRUCache<K, V> {

  private final Map<K, V> cache = Collections.synchronizedMap(new LimitedCapacityLinkedHashMap<>());

  public Function<K, V> memoize(final Function<K, V> originalFn) {
    return (key) -> cache.computeIfAbsent(key, originalFn);
  }

  private static class LimitedCapacityLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    private static final int MAX_CAPACITY = 10;

    public LimitedCapacityLinkedHashMap() {
      super(16, 0.75F, true);
    }

    @Override
    protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
      return size() > MAX_CAPACITY;
    }
  }
}
