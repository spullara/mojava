package mojava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple counter for doing inmemory counts.
 * <p/>
 * User: sam
 * Date: 10/22/10
 * Time: 1:05 PM
 */
public class Counter<T> extends HashMap<T, Long> {
  public long increment(T t) {
    return increment(t, 1L);
  }

  public long increment(T t, long inc) {
    Long i = get(t);
    if (i == null) {
      put(t, inc);
      return inc;
    } else {
      put(t, i + inc);
      return i + inc;
    }
  }

  public long ensure(T t) {
    Long i = get(t);
    if (i == null) {
      put(t, 0L);
      return 0;
    }
    return i;
  }

  public List<Map.Entry<T, Long>> getDescending() {
    List<Map.Entry<T, Long>> list = new ArrayList<Map.Entry<T, Long>>(entrySet());
    Collections.sort(list, new Comparator<Map.Entry<T, Long>>() {
      public int compare(Map.Entry<T, Long> tIntegerEntry, Map.Entry<T, Long> tIntegerEntry1) {
        long diff = tIntegerEntry1.getValue() - tIntegerEntry.getValue();
        return diff < 0 ? -1 : diff > 0 ? 1 : 0;
      }
    });
    return list;
  }
}
