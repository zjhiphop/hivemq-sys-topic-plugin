/*
 * Copyright 2015 dc-square GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hivemq.plugins.entry;

import com.google.common.base.Supplier;
import com.hivemq.spi.topic.sys.SYSTopicEntry;
import com.hivemq.spi.topic.sys.Type;

import javax.annotation.PostConstruct;

/**
 * @author Lukas Brandl
 */
public class UpTimeTotal implements SYSTopicEntry {

    private Long startTime;

    @PostConstruct
    private void stopTime() {
        startTime = System.currentTimeMillis() / 1000;
    }

    @Override
    public String topic() {
        return "$SYS/broker/uptime";
    }

    @Override
    public Supplier<byte[]> payload() {
        return new SysTopicSupplier();
    }

    @Override
    public Type type() {
        return Type.STATIC;
    }

    @Override
    public String description() {
        return "The amount of time in seconds the broker has been online.";
    }

    private class SysTopicSupplier implements Supplier<byte[]> {

        @Override
        public byte[] get() {
            return Long.toString(System.currentTimeMillis() / 1000 - startTime).getBytes();
        }
    }
}
