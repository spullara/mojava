package mojava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Edit this
 * <p/>
 * User: sam
 * Date: 7/16/11
 * Time: 11:23 AM
 */
public class Trie<K extends List, V> {
  private Map<Object, Trie<K, V>> root = new HashMap<Object, Trie<K, V>>();
  private V value;

  public void put(List list, V value) {
    if (list.size() == 0) {
      this.value = value;
      return;
    }
    Object key = list.get(0);
    Trie<K, V> next = root.get(key);
    if (next == null) {
      next = new Trie<K, V>();
      root.put(key, next);
    }
    next.put(list.subList(1, list.size()), value);
  }

  public V get(List list) {
    if (list.size() == 0) {
      return value;
    }
    return root.get(list.get(0)).get(list.subList(1, list.size()));
  }

  public List<V> getAll(List list) {
    List<V> values = new ArrayList<V>();
    if (list.size() == 0 && value != null) values.add(value);
    if (list.size() == 0) {
      for (Map.Entry<Object, Trie<K, V>> entry : root.entrySet()) {
        values.addAll(entry.getValue().getAll(list));
      }
    } else {
      return root.get(list.get(0)).getAll(list.subList(1, list.size()));
    }
    return values;
  }
}
