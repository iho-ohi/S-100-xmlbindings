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

import org.grad.eNav.s100.utils.S100ExchangeSetUtils;
import org.iso.standards.iso._19115.__3.gco._1.CodeListValueType;

import java.util.Objects;

/**
 * The CodeListValueTypeProvider Interface.
 * <p/>
 * This interface can be used by all the defined enums that represent code list
 * values to generate the S-100 CodeListValueType objects.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface CodeListValueTypeProvider {

    /**
     * In most case the code is the same as the value of the code list object,
     * so we can just implement that and override it if required.
     *
     * @return the code list code to be used
     */
    default String getCode() {
        return this.getValue();
    }

    /**
     * Returns the code list to be used.
     *
     * @return the code list to be used
     */
    String getList();

    /**
     * Returns the code list space to be used.
     *
     * @return the code list space to be used
     */
    String getSpace();

    /**
     * Returns the code list value to be used.
     *
     * @return the code list value to be used
     */
    String getValue();

    /**
     * Returns the constructed code list value type object.
     *
     * @return the constructed code list value type object
     */
    default CodeListValueType getCodeListValueType() {
        return S100ExchangeSetUtils.createCodeListValueType(
                this.getList(),
                Objects.isNull(this.getList()) ? this.getSpace() : null,
                this.getCode(),
                this.getValue()
        );
    };

}
