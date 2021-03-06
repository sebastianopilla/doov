/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.doov.core.serial.adapter;

import static java.lang.String.valueOf;

import java.util.Date;

import io.doov.core.FieldInfo;
import io.doov.core.serial.TypeAdapter;

public class DateTypeAdapter implements TypeAdapter {

    @Override
    public boolean accept(FieldInfo info) {
        return Date.class.equals(info.type());
    }

    @Override
    public boolean accept(Object value) {
        return value instanceof Date;
    }

    @Override
    @SuppressWarnings("deprecation")
    public String toString(Object value) {
        Date date = (Date) value;
        final int day = date.getDate();
        final int month = date.getMonth() + 1;
        final int year = date.getYear() + 1900;
        return valueOf(year) + (month < 10 ? "0" + month : month) + (day < 10 ? "0" + day : day);
    }

    @Override
    @SuppressWarnings("deprecation")
    public Object fromString(FieldInfo info, String value) {
        int year = Integer.parseInt(value.substring(0, 4));
        int month = Integer.parseInt(value.substring(4, 6));
        int day = Integer.parseInt(value.substring(6, 8));

        Date date = new Date();
        date.setDate(day);
        date.setMonth(month - 1);
        date.setYear(year - 1900);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date;
    }
}
