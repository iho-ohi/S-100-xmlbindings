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

package org.grad.eNav.s100.enums;

/**
 * The ISO 19115-1 Phone Type Enum.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public enum TelephoneType implements CodeListValueTypeProvider {
    VOICE("voice"),
    FAX("fax"),
    SMS("sms");

    // Enum Variables
    private final String value;

    /**
     * The Telephone Type Constructor.
     *
     * @param value the telephone type value
     */
    TelephoneType(String value) {
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * Gets the list of the code list.
     *
     * @return the list of the code list
     */
    @Override
    public String getList() {
        return CODELIST;
    }

    /**
     * Gets the code space of the code list.
     *
     * @return the code space of the code list
     */
    @Override
    public String getSpace() {
        return CODESPACE;
    }

    /**
     * The Enum Codespace.
     */
    public static final String CODESPACE = "https://schemas.isotc211.org/19115/-3/cit/1.0";

    /**
     * The Enum Codelist.
     */
    public static final String CODELIST = "https://standards.iso.org/iso/19115/resources/Codelists/cat/codelists.xml";
}
