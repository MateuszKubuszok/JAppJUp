/**
 * Copyright 2012-2013 Mateusz Kubuszok
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p> 
 * 
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
package net.jsdpu.process.queue;

import java.util.Collection;
import java.util.Iterator;

public class ProcessQueue implements Collection<EnqueuedProcess> {
    @Override
    public Iterator<EnqueuedProcess> iterator() {
        return null;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean add(EnqueuedProcess e) {
        throw new UnsupportedOperationException(
                "Started process queue cannot have any process removed");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException(
                "Started process queue cannot have any process removed");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends EnqueuedProcess> c) {
        throw new UnsupportedOperationException(
                "Started process queue cannot have any process removed");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException(
                "Started process queue cannot have any process removed");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException(
                "Started process queue cannot have any process removed");
    }

    @Override
    public void clear() {
    }
}
