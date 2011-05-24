package mojava;

import com.google.common.collect.Iterables;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by IntelliJ IDEA.
 * <p/>
 * User: sam
 * Date: 11/20/10
 * Time: 11:41 PM
 */
public class Abbrev {
  public static final Iterator EMPTY_ITERATOR = new Iterator() {

    @Override
    public boolean hasNext() {
      return false;
    }

    @Override
    public Object next() {
      throw new NoSuchElementException();
    }

    @Override
    public void remove() {
    }
  };
  public static final Iterable EMPTY_ITERABLE = new Iterable() {
    @Override
    public Iterator iterator() {
      return EMPTY_ITERATOR;
    }
  };

  public static <S, T> Iterable<T> t(Iterable<S> iterable, F<S, T> function) {
    return Iterables.transform(iterable, function);
  }

  public static <S, T> Iterable<Future<T>> t(final ExecutorService es, final Iterable<S> iterable, final F<S, T> function) {
    checkNotNull(iterable);
    checkNotNull(function);
    return new Iterable<Future<T>>() {
      public Iterator<Future<T>> iterator() {
        final Iterator<S> fromIterator = iterable.iterator();
        checkNotNull(fromIterator);
        checkNotNull(function);
        return new Iterator<Future<T>>() {
          public boolean hasNext() {
            return fromIterator.hasNext();
          }
          public Future<T> next() {
            final S from = fromIterator.next();
            return es.submit(new Callable<T>() {
              @Override
              public T call() throws Exception {
                return function.apply(from);
              }
            });
          }
          public void remove() {
            fromIterator.remove();
          }
        };
      }
    };
  }

  public static <T> Iterable<T> t(Iterable<Future<T>> iterable) {
    return t(iterable, new F<Future<T>, T>() {
      public T apply(Future<T> input) {
        try {
          return input.get();
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    });
  }

  public static <T> Iterable<T> f(Iterable<T> iterable, P<T> predicate) {
    return Iterables.filter(iterable, predicate);
  }
  public static <T> T find(Iterable<T> iterable, P<T> predicate) {
    for (T t : iterable) {
      if (predicate.apply(t)) return t;
    }
    return null;
  }

  public static <T> Iterable<T> l(final Iterable<T> iterable, final int skip, final int count) {
    if (iterable == null) {
      return null;
    } else {
      return new Iterable<T>() {
        @Override
        public Iterator<T> iterator() {
          final Iterator<T> iterator = iterable.iterator();
          for (int i = 0; i < skip; i++) {
            if (iterator.hasNext()) {
              iterator.next();
            } else break;
          }
          return new Iterator<T>() {
            int i = 0;
            Iterator<T> updateIterator = iterator;

            @Override
            public boolean hasNext() {
              return i < count && updateIterator.hasNext();
            }

            @Override
            public T next() {
              i++;
              return updateIterator.next();
            }

            @Override
            public void remove() {
            }
          };
        }
      };
    }
  }

  public static <T> Iterable<T> l(final Iterable<T> iterable, final int count) {
    return l(iterable, 0, count);
  }

  public static <T> Iterable<T> l(final Iterator<T> updateIterator, final int count) {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return new Iterator<T>() {
          int i = 0;

          @Override
          public boolean hasNext() {
            return i < count && updateIterator.hasNext();
          }

          @Override
          public T next() {
            i++;
            return updateIterator.next();
          }

          @Override
          public void remove() {
          }
        };
      }
    };
  }

  public static String e(String s) {
    try {
      return s == null ? "" : URLEncoder.encode(s, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      return s;
    }
  }

  public static String d(String s) {
    try {
      return s == null ? "" : URLDecoder.decode(s, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      return s;
    }
  }

  public static String s(String s) {
    return s == null ? "" : s;
  }

  public static boolean n(Boolean b) {
    if (b == null) {
      return false;
    }
    return b;
  }

  public static int n(Integer b) {
    if (b == null) {
      return 0;
    }
    return b;
  }

  public static int n(Integer b, int def) {
    if (b == null) {
      return def;
    }
    return b;
  }

  public static <T> Iterable<T> n(Iterable<T> i) {

    if (i == null) {
      return EMPTY_ITERABLE;
    }
    return i;
  }

  public static <T> Iterable<T> i(final T o) {
    if (o == null) {
      return EMPTY_ITERABLE;
    }
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return new Iterator<T>() {
          boolean hasNext = true;

          @Override
          public boolean hasNext() {
            return hasNext;
          }

          @Override
          public T next() {
            hasNext = false;
            return o;
          }

          @Override
          public void remove() {
          }
        };
      }
    };
  }
}
