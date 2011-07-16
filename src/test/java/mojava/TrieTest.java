package mojava;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

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
    Trie<List<String>, String> trie = new Trie<List<String>, String>();
    trie.put(Arrays.asList("sam", "pullara", "test"), "sam pullara test");
    trie.put(Arrays.asList("sam", "pullara", "test2"), "sam pullara test2");
    trie.put(Arrays.asList("sam", "pullara3", "test"), "sam pullara3 test");
    trie.put(Arrays.asList("anna", "pullara3", "test"), "anna pullara3 test");
    assertEquals("sam pullara test", trie.get(Arrays.asList("sam", "pullara", "test")));
    assertEquals(Arrays.asList("sam pullara test", "sam pullara test2"), trie.getAll(Arrays.asList("sam", "pullara")));
  }
}
