/*
 * Copyright (c) 2024 GLA Research and Development Directorate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.grad.eNav.s100.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static org.grad.eNav.s100.adapters.DateAdapter.S100_DATE_FORMAT;
import static org.grad.eNav.s100.adapters.TimeAdapter.S100_TIME_FORMAT;

/**
 * The DateTime Adapter Class.
 * <p/>
 * This is used to translate between the Java util.Date objects and the XML
 * dateTime elements.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class DateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    public static final DateTimeFormatter S100_DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern(S100_DATE_FORMAT)
            .appendPattern("'T'")
            .appendPattern(S100_TIME_FORMAT)
            .optionalStart()
            .parseLenient()
            .appendOffset("+HHMM", "Z")
            .parseStrict()
            .toFormatter();

    /**
     * Marshall a Java Date object into an XML element.
     *
     * @param date      The java Date object
     * @return The XML element
     */
    @Override
    public String marshal(LocalDateTime date) {
        synchronized (DateTimeFormatter.ISO_OFFSET_DATE_TIME) {
            return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(date.atZone(ZoneOffset.systemDefault()));
        }
    }

    /**
     * Unmarshall an XML element into a Java Date object.
     *
     * @param xml       The XML element
     * @return The Java Date object
     */
    @Override
    public LocalDateTime unmarshal(String xml) {
        synchronized (DateTimeFormatter.ISO_OFFSET_DATE_TIME) {
            return LocalDateTime.parse(xml, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
    }

}