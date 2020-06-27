package ru.otus.cachehw;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы

    private final WeakHashMap<K, V> cache = new WeakHashMap<>();
    private final List<SoftReference<HwListener<K, V>>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyListeners(key, value, "put");
    }

    @Override
    public void remove(K key) {
        var removedValue = cache.remove(key);
        notifyListeners(key, removedValue, "remove");
    }

    @Override
    public V get(K key) {
        var foundValue = cache.get(key);
        notifyListeners(key, foundValue, "get");
        return foundValue;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(new SoftReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        var iterator = listeners.iterator();
        while (iterator.hasNext()) {
            var ref = iterator.next();
            if (ref.get() == listener) {
                ref.clear();
            } else if (ref.get() == null) {
                iterator.remove();
            }
            iterator.remove();
        }
    }

    private void notifyListeners(K key, V value, String action) {

        var iterator = listeners.iterator();
        while (iterator.hasNext()) {
            var ref = iterator.next();
            var listener = ref.get();
            if (listener == null) {
                iterator.remove();
            } else {
                listener.notify(key, value, action);
            }
        }
    }

}
