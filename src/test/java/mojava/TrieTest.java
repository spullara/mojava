package mojava;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * TODO: Edit this
 * <p/>
 * User: sam
 * Date: 7/16/11
 * Time: 11:42 AM
 */
public class TrieTest {
  @Test
  public void full() {
    Trie<String, String> trie = new Trie<String, String>();
    trie.put(Arrays.asList("sam", "pullara", "test"), "sam pullara test");
    trie.put(Arrays.asList("sam", "pullara"), "sam pullara");
    trie.put(Arrays.asList("sam", "pullara", "test2"), "sam pullara test2");
    trie.put(Arrays.asList("sam", "pullara3", "test"), "sam pullara3 test");
    trie.put(Arrays.asList("anna", "pullara3", "test"), "anna pullara3 test");
    assertEquals("sam pullara test", trie.get(Arrays.asList("sam", "pullara", "test")));
    assertEquals(Arrays.asList("sam pullara", "sam pullara test", "sam pullara test2"), trie.getAll(
            Arrays.asList("sam", "pullara")));
    assertTrue(trie.getAll(Arrays.asList("sam", "pullara", "fred")).isEmpty());
  }
}
