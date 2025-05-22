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

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Double List Adapter Class.
 *
 * This is used to translate between the Java Double Array objects and the XML
 * String elements.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class DoubleListAdapter extends XmlAdapter<String, Double[]> {

    /**
     * Marshall a Java Double Array object into an XML element.
     *
     * @param doubleList    The java Double Array object
     * @return The XML element
     */
    @Override
    public String marshal(Double[] doubleList) {
        return Stream.of(doubleList)
                .map(d -> String.format("%.7f", d))
                .collect(Collectors.joining(" "));
    }

    /**
     * Unmarshall an XML element into a Java Double Array object.
     *
     * @param xml           The XML element
     * @return The Java Double Array object
     */
    @Override
    public Double[] unmarshal(String xml) {
        // First parse all the coordinates
        return Arrays.stream(xml.split("\\s"))
                .map(Double::parseDouble)
                .toArray(Double[]::new);
    }

}