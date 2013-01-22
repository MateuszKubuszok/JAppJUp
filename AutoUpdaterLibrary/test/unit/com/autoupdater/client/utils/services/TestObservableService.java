package com.autoupdater.client.utils.services;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.autoupdater.client.utils.services.IObserver;
import com.autoupdater.client.utils.services.ObservableService;

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
