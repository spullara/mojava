package mojava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Store values using a list of keys. Look them up by a list of keys or grab all below a certain prefix key.
 * <p/>
 * User: sam
 * Date: 7/16/11
 * Time: 11:23 AM
 */
public class Trie<K, V> {
  private Map<K, Trie<K, V>> root = new HashMap<K, Trie<K, V>>();
  private V value;

  public void put(List<K> list, V value) {
    if (list.size() == 0) {
      this.value = value;
      return;
    }
    K key = list.get(0);
    Trie<K, V> next = root.get(key);
    if (next == null) {
      next = new Trie<K, V>();
      root.put(key, next);
    }
    next.put(list.subList(1, list.size()), value);
  }

  public V get(List<K> list) {
    if (list.size() == 0) {
      return value;
    }
    return root.get(list.get(0)).get(list.subList(1, list.size()));
  }

  public List<V> getAll(List<K> list) {
    List<V> values = new ArrayList<V>();
    if (list.size() == 0) {
      if (value != null) values.add(value);
      for (Map.Entry<K, Trie<K, V>> entry : root.entrySet()) {
        values.addAll(entry.getValue().getAll(list));
      }
    } else {
      return root.get(list.get(0)).getAll(list.subList(1, list.size()));
    }
    return values;
  }
}
