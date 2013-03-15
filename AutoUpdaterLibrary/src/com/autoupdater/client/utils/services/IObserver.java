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

/**
 * Interface describing Services' Observable - replaces Java's default
 * Observable
 * 
 * <p>
 * Makes use of generics to specify type of received messages.
 * </p>
 * 
 * @param <Message>
 *            type of messages sent by Observable
 */
public interface IObserver<Message> {
    /**
     * Receives message sent by ObservableService.
     * 
     * @param observable
     *            observed service
     * @param message
     *            message describing change
     */
    public void update(ObservableService<Message> observable, Message message);
}
