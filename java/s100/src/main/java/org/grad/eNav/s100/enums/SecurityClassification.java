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
 * This Enum implements the S-100 Security Classification codes.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public enum SecurityClassification implements CodeListValueTypeProvider {
    UNCLASSIFIED(1, "unclassified"),
    RESTRICTED(2, "restricted"),
    CONFIDENTIAL(3, "confidential"),
    SECRET(4, "secret"),
    TOP_SECRET(5, "top secret"),
    SENSITIVE_BUT_UNCLASSIFIED(6, "SBU"),
    FOR_OFFICIAL_USE_ONLY(7, "for official use only"),
    PROTECTED(8, "protected"),
    LIMITED_DISTRIBUTION(9, "limited distribution");

    // Enum Variables
    private final int code;
    private final String value;

    /**
     * Security Classification Constructor.
     *
     * @param code the security classification code;
     */
    SecurityClassification(int code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    @Override
    public String getCode() {
        return String.valueOf(code);
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

