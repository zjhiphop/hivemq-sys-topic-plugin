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

import com.codahale.metrics.Gauge;
import com.google.common.base.Supplier;
import com.google.inject.Inject;
import com.hivemq.plugins.entry.abstracts.AbstractMetricEntry;
import com.hivemq.spi.metrics.HiveMQMetrics;
import com.hivemq.spi.services.BlockingMetricService;
import com.hivemq.spi.services.MetricService;
import com.hivemq.spi.topic.sys.SYSTopicEntry;
import com.hivemq.spi.topic.sys.Type;

/**
 * @author Lukas Brandl
 */
public class RetainedCurrent extends AbstractMetricEntry implements SYSTopicEntry {

    @Inject
    protected RetainedCurrent(BlockingMetricService metricService) {
        super(metricService);
    }

    @Override
    public String topic() {
        return "$SYS/broker/messages/retained/count";
    }

    @Override
    public Supplier<byte[]> payload() {
        return new SysTopicSupplier();
    }

    @Override
    public Type type() {
        return Type.STANDARD;
    }

    @Override
    public String description() {
        return "The total number of retained messages active on the broker.";
    }

    private class SysTopicSupplier implements Supplier<byte[]> {

        @Override
        public byte[] get() {
            final Gauge<Number> metric = metricService.getHiveMQMetric(HiveMQMetrics.RETAINED_MESSAGES_CURRENT);
            return Long.toString(metric.getValue().longValue()).getBytes();
        }
    }
}
