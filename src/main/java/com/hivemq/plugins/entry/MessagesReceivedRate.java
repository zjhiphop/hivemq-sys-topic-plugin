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

import com.codahale.metrics.Meter;
import com.google.inject.Inject;
import com.hivemq.plugins.entry.abstracts.RateEntry;
import com.hivemq.spi.metrics.HiveMQMetrics;
import com.hivemq.spi.services.BlockingMetricService;
import com.hivemq.spi.services.MetricService;
import com.hivemq.spi.topic.sys.Type;

/**
 * @author Lukas Brandl
 */
public class MessagesReceivedRate extends RateEntry {

    @Inject
    protected MessagesReceivedRate(BlockingMetricService metricService) {
        super(metricService);
    }

    @Override
    protected Meter meter() {
        return metricService.getHiveMQMetric(HiveMQMetrics.INCOMING_MESSAGE_RATE);
    }

    @Override
    public String topic() {
        return "$SYS/broker/load/messages/received/";
    }

    @Override
    public Type type() {
        return Type.STANDARD;
    }

    @Override
    public String description() {
        return "The moving average of the number of all types of MQTT messages received by the broker over different time intervals." +
                " The final \"+\" of the hierarchy can be 1min, 5min or 15min." +
                " The value returned represents the number of messages received in 1 minute, averaged over 1, 5 or 15 minutes.";
    }

}
