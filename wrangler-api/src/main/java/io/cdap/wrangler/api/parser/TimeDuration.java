/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.cdap.wrangler.api.parser;

public class TimeDuration extends Token {
    private final double value;
    private final String unit;

    public TimeDuration(String input) {
        super(Type.TIME_DURATION, input);
        input = input.trim().toLowerCase();
        this.unit = input.replaceAll("[0-9.]", "");
        this.value = Double.parseDouble(input.replaceAll("[^0-9.]", ""));
    }

    public long getMilliseconds() {
        switch (unit) {
            case "ms": return (long)(value);
            case "s":
            case "sec": return (long)(value * 1000);
            case "m":
            case "min": return (long)(value * 60 * 1000);
            case "h": return (long)(value * 3600 * 1000);
            default: throw new IllegalArgumentException("Unknown unit: " + unit);
        }
    }

    public long getNanoseconds() {
        return getMilliseconds() * 1_000_000;
    }
}
