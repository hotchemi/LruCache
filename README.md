# LruCache

[![Build Status](https://travis-ci.org/hotchemi/LruCache.svg)](https://travis-ci.org/hotchemi/LruCache)

A tiny, thread safe memory cache implementation which uses a LRU policy.

> This implementation gives priority to simplicity of API.
> If you want to use a rich API, Use the `LruCache` in Android framework.

## How to use

### Cache

[`Cache`](https://github.com/hotchemi/LruCache/blob/master/src/main/java/hotchemi/com/github/Cache.java) is a interface.

It provides six methods as bellow.

- `V get(K key)` - Gets an value for the specified key.
- `V put(K key, V value)` - Puts an value in the cache for the specified key.
- `V remove(K key)` - Removes the entry for key.
- `void clear()` - Clears all the entries in the cache.
- `int getMaxMemorySize()` - Returns the max memory size of the cache.
- `int getMemorySize()` - Returns the current memory size of the cache.

#### LruCache

[`LruCache`](https://github.com/hotchemi/LruCache/blob/master/src/main/java/hotchemi/com/github/LruCache.java)'s API is definitely simple.

```java
Cache<String, String> cache = new LruCache<>();
cache.put("a", "A");
```

#### Note

- Default cache item capacity is ten. You can change the capacity via constructor.
- Please override the `getValueSize` if you control memory size correctly.

### BitmapLruCache

[`BitmaplruCache`](https://github.com/hotchemi/LruCache/blob/master/src/main/java/hotchemi/com/github/BitmapLruCache.java) is the class that specialized in caching `Bitmap`.

```java
private static final Bitmap A = Bitmap.createBitmap(1, 1, ALPHA_8);

Cache cache = new BitmapLruCache();
cache.put("a", A);
```

## Test

```sh
mvn clean test
```

## Support

Java 1.7 or greater.
