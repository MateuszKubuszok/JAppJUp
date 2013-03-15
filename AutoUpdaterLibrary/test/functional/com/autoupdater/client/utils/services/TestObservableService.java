/**
 * Copyright 2012-2013 Maciej Jaworski, Mariusz Kapcia, Paweł Kędzia, Mateusz Kubuszok
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
package com.autoupdater.client.utils.services;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class TestObservableService {
    private boolean notified;
    private ObservableService<Integer> observable;
    private Integer message;

    @Test
    public void testDontNotifyObserversWhenObservableHasntChanged() {
        // given
        ObservableService<Integer> observable = new ObservableService<Integer>();
        IObserver<Integer> observer = new TestObserver();

        // when
        observable.addObserver(observer);
        observable.hasChanged();
        observable.notifyObservers(1);
        reset();
        observable.notifyObservers(1);

        // then
        assertThat(notified)
                .as("notifyObservers(<message>) should not notify observers when observable hasn't changed")
                .isFalse();
    }

    @Test
    public void testNotifyObserversWhenObservableHasChanged() {
        // given
        ObservableService<Integer> observable = new ObservableService<Integer>();
        IObserver<Integer> observer = new TestObserver();

        // when
        reset();
        observable.addObserver(observer);
        observable.hasChanged();
        observable.notifyObservers(2);

        // then
        assertThat(notified)
                .as("notifyObservers(<message>) should notify all observers, if hasChanged() was called before")
                .isTrue();
        assertThat(this.observable).as(
                "notifyObservers(<message>) should pass instance of itself to its observers")
                .isEqualTo(observable);
        assertThat(message.intValue()).as(
                "notifyObservers(<message>) should pass message to its observers").isEqualTo(2);
    }

    @Test
    public void testRemoveObserver() {
        // given
        ObservableService<Integer> observable = new ObservableService<Integer>();
        IObserver<Integer> observer = new TestObserver();

        // when
        reset();
        observable.addObserver(observer);
        observable.addObserver(observer);
        observable.removeObserver(observer);
        observable.hasChanged();
        observable.notifyObservers(3);

        // then
        assertThat(notified).as("notifyObservers(<message>) should not notify removed observers")
                .isFalse();
    }

    private void reset() {
        notified = false;
        observable = null;
        message = null;
    }

    private class TestObserver implements IObserver<Integer> {
        @Override
        public void update(ObservableService<Integer> passedObservable, Integer passedMessage) {
            notified = true;
            observable = passedObservable;
            message = passedMessage;
        }
    }
}
