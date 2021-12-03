package calculator.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
class LRUCacheTest {

  @Mock Function<Integer, Integer> originalFunction;

  @BeforeEach
  void setup() {
    MockitoAnnotations.initMocks(this);
    when(originalFunction.apply(0)).thenReturn(0);
  }

  @Test
  void memoize_ReturnValueShouldBeEqualToReturnValueOfOriginalFunction() {
    final Function<Integer, Integer> originalFn = Function.identity();

    final LRUCache<Integer, Integer> cache = new LRUCache<>();
    final var memoizedFunction = cache.memoize(originalFn);

    assertEquals(originalFn.apply(1), memoizedFunction.apply(1));
  }

  @Test
  void memoize_OriginalFunctionShouldBeCalledOnlyOnceForTenCallsWithSameParameter() {
    /*
    This test checks if the original function is called only once
    in a scenario that the memoized function is called 10 times.
     */
    final LRUCache<Integer, Integer> cache = new LRUCache<>();
    final var memoizedFunction = cache.memoize(originalFunction);

    for (int i = 0; i < 10; i++) {
      memoizedFunction.apply(0);
    }

    verify(originalFunction, times(1)).apply(0);
  }

  @Test
  void memoize_ShouldCallOriginalFunctionOnceIfKeyWasNotEvictedDueToRecentUsage() {
    /*
    This test checks if the original function is called only once with the most recently used parameter.
    Firstly, we add 0 to the cache, and then we add 9 other items.
    After that, we "use" 0 again, to make 1 the least recently used parameter.
    Following that, we add 50 to the cache, and call the memoized function for 0 again.
    That last call should not call the original function, as the key 0 should not be evicted.
     */

    final LRUCache<Integer, Integer> cache = new LRUCache<>();
    final var memoizedFunction = cache.memoize(originalFunction);

    addZeroToCache(memoizedFunction);
    addItemsToCache(memoizedFunction, 9);
    callMemoizedFunctionWithZeroTenTimes(memoizedFunction);

    when(originalFunction.apply(50)).thenReturn(50);
    memoizedFunction.apply(50);

    memoizedFunction.apply(0);
    verify(originalFunction, times(1)).apply(0);
  }

  @Test
  void memoize_ShouldCallOriginalTwiceIfKeyWasEvicted() {
    /*
    This test checks if the original function is called twice.
    Firstly, we add 0 to the cache, and then we add 9 other items.
    After that, we "use" 0 again, to make 1 the least recently used parameter.
    Following that, we add 50 to the cache, and call the memoized function for 0 again.
    That last call should not call the original function, as the key 0 should not be evicted.
     */

    final LRUCache<Integer, Integer> cache = new LRUCache<>();
    final var memoizedFunction = cache.memoize(originalFunction);

    addZeroToCache(memoizedFunction);
    callMemoizedFunctionWithZeroTenTimes(memoizedFunction);

    addItemsToCache(memoizedFunction, 10); // Add 10 items to cause 0 to be evicted.

    callMemoizedFunctionWithZeroTenTimes(
        memoizedFunction); // Call the memoized function with zero 10 more times

    verify(originalFunction, times(2)).apply(0);
  }

  private void callMemoizedFunctionWithZeroTenTimes(Function<Integer, Integer> memoizedFunction) {
    for (int i = 0; i < 10; i++) {
      memoizedFunction.apply(0);
    }
  }

  private void addZeroToCache(final Function<Integer, Integer> memoizedFunction) {
    memoizedFunction.apply(0);
  }

  private void addItemsToCache(
      final Function<Integer, Integer> memoizedFunction, final int amount) {
    for (int i = 1; i < amount + 1; i++) {
      when(originalFunction.apply(i)).thenReturn(i);
      memoizedFunction.apply(i);
    }
  }
}
