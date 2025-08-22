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

package org.grad.eNav.s100.utils;

import _int.iho.s100.catalog._5_2.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class S100CatalogueDiscoveryMetadataBuilderTest {

    // Test Variables
    private S100CatalogueDiscoveryMetadataBuilder s100CatalogueDiscoveryMetadataBuilder;

    // Fixed Variables
    private DateTimeFormatter timeFormat = DateTimeFormatter.ISO_TIME;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE;
    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_DATE_TIME;

    /**
     * Common setup for all the tests.
     */
    @BeforeEach
    void setup() throws IOException {
        this.s100CatalogueDiscoveryMetadataBuilder = new S100CatalogueDiscoveryMetadataBuilder((id, algorithm, payload) -> {
            S100SEDigitalSignature s100SEDigitalSignature = new S100SEDigitalSignature();
            s100SEDigitalSignature.setId("sig");
            s100SEDigitalSignature.setCertificateRef("ref");
            s100SEDigitalSignature.setValue("signature".getBytes());
            return s100SEDigitalSignature;
        });
    }

    /**
     * Test that the builder will get constructed correctly with all its
     * elements null.
     */
    @Test
    void testConstructor() {
        assertNotNull(this.s100CatalogueDiscoveryMetadataBuilder);
        assertNull(this.s100CatalogueDiscoveryMetadataBuilder.fileName);
        assertNull(this.s100CatalogueDiscoveryMetadataBuilder.purpose);
        assertNull(this.s100CatalogueDiscoveryMetadataBuilder.editionNumber);
        assertNull(this.s100CatalogueDiscoveryMetadataBuilder.scope);
        assertNull(this.s100CatalogueDiscoveryMetadataBuilder.versionNumber);
        assertNull(this.s100CatalogueDiscoveryMetadataBuilder.issueDate);
        assertNull(this.s100CatalogueDiscoveryMetadataBuilder.productSpecification);
        assertNull(this.s100CatalogueDiscoveryMetadataBuilder.digitalSignatureReference);
        assertNull(this.s100CatalogueDiscoveryMetadataBuilder.digitalSignatureValues);
        assertFalse(this.s100CatalogueDiscoveryMetadataBuilder.compressionFlag);
        assertNull(this.s100CatalogueDiscoveryMetadataBuilder.defaultLocale);
        assertNull(this.s100CatalogueDiscoveryMetadataBuilder.otherLocales);
    }

    /**
     * Test that if provided the builder arguments will be correctly set to
     * the builder.
     */
    @Test
    void testSetters() {
        // Perform the setting operations
        this.s100CatalogueDiscoveryMetadataBuilder
                .setFileName("file:/catalogue.XML")
                .setPurpose(S100Purpose.NEW_EDITION)
                .setEditionNumber(BigInteger.ONE)
                .setScope(S100CatalogueScope.FEATURE_CATALOGUE)
                .setVersionNumber("versionNumber")
                .setIssueDate(LocalDate.parse("2023-01-01", this.dateFormat))
                .setProductSpecification(new S100ProductSpecification())
                .setCompressionFlag(false)
                .setDefaultLocale(Locale.UK)
                .setOtherLocales(Collections.singletonList(Locale.ENGLISH));

        assertNotNull(this.s100CatalogueDiscoveryMetadataBuilder);
        assertEquals("file:/catalogue.XML", this.s100CatalogueDiscoveryMetadataBuilder.fileName);
        assertEquals(S100Purpose.NEW_EDITION, this.s100CatalogueDiscoveryMetadataBuilder.purpose);
        assertEquals(BigInteger.ONE, this.s100CatalogueDiscoveryMetadataBuilder.editionNumber);
        assertEquals(S100CatalogueScope.FEATURE_CATALOGUE, this.s100CatalogueDiscoveryMetadataBuilder.scope);
        assertEquals("versionNumber", this.s100CatalogueDiscoveryMetadataBuilder.versionNumber);
        assertEquals(LocalDate.parse("2023-01-01", this.dateFormat), this.s100CatalogueDiscoveryMetadataBuilder.issueDate);
        assertNotNull(this.s100CatalogueDiscoveryMetadataBuilder.productSpecification);
        assertFalse(this.s100CatalogueDiscoveryMetadataBuilder.compressionFlag);
        assertEquals(Locale.UK, this.s100CatalogueDiscoveryMetadataBuilder.defaultLocale);
        assertNotNull(this.s100CatalogueDiscoveryMetadataBuilder.otherLocales);
        assertEquals(1, this.s100CatalogueDiscoveryMetadataBuilder.otherLocales.size());
        assertNotNull(this.s100CatalogueDiscoveryMetadataBuilder.otherLocales.get(0));
        assertEquals(Locale.ENGLISH, this.s100CatalogueDiscoveryMetadataBuilder.otherLocales.get(0));
    }

    /**
     * Test that the S-100 Exchange Set Catalogue Discovery Metadata builder can
     * correctly build a discovery metadata XML if the appropriate parameters
     * have been provided.
     */
    @Test
    void testBuild() {
        // Perform the setting operations and build
        final S100CatalogueDiscoveryMetadata metadata = this.s100CatalogueDiscoveryMetadataBuilder
                .setFileName("file:/catalogue.XML")
                .setPurpose(S100Purpose.NEW_EDITION)
                .setEditionNumber(BigInteger.ONE)
                .setScope(S100CatalogueScope.FEATURE_CATALOGUE)
                .setVersionNumber("versionNumber")
                .setIssueDate(LocalDate.parse("2023-01-01", this.dateFormat))
                .setProductSpecification(new S100ProductSpecification())
                .setCompressionFlag(false)
                .setDefaultLocale(Locale.UK)
                .setOtherLocales(Collections.singletonList(Locale.ENGLISH))
                .build("supportFile".getBytes());

        // Assess the main information
        assertNotNull(metadata);
        assertEquals("file:/catalogue.XML", metadata.getFileName());
        assertEquals(S100Purpose.NEW_EDITION, metadata.getPurpose());
        assertEquals(BigInteger.ONE, metadata.getEditionNumber());
        assertEquals(S100CatalogueScope.FEATURE_CATALOGUE, metadata.getScope());
        assertEquals("versionNumber", metadata.getVersionNumber());
        assertEquals(LocalDate.parse("2023-01-01", this.dateFormat), metadata.getIssueDate());
        assertNotNull(metadata.getProductSpecification());
        assertFalse(metadata.isCompressionFlag());

        // Assess the default locale
        assertNotNull(metadata.getDefaultLocale());
        assertNotNull(metadata.getDefaultLocale().getPTLocale().getValue());
        assertNotNull(metadata.getDefaultLocale().getPTLocale().getValue().getLanguage());
        assertNotNull(metadata.getDefaultLocale().getPTLocale().getValue().getLanguage().getLanguageCode());
        assertEquals(Locale.UK.getDisplayLanguage(), metadata.getDefaultLocale().getPTLocale().getValue().getLanguage().getLanguageCode().getValue());
        assertNotNull(metadata.getDefaultLocale().getPTLocale().getValue().getCountry());
        assertNotNull(metadata.getDefaultLocale().getPTLocale().getValue().getCountry().getCountryCode());
        assertEquals(Locale.UK.getDisplayCountry(), metadata.getDefaultLocale().getPTLocale().getValue().getCountry().getCountryCode().getValue());
        assertNotNull(metadata.getDefaultLocale().getPTLocale().getValue().getCharacterEncoding());
        assertNotNull(metadata.getDefaultLocale().getPTLocale().getValue().getCharacterEncoding().getMDCharacterSetCode());
        assertEquals(StandardCharsets.UTF_8.displayName(), metadata.getDefaultLocale().getPTLocale().getValue().getCharacterEncoding().getMDCharacterSetCode().getValue());

        // Assess the other locales
        assertNotNull(metadata.getOtherLocales());
        assertEquals(1, metadata.getOtherLocales().size());
        assertNotNull(metadata.getOtherLocales().get(0));
        assertNotNull(metadata.getOtherLocales().get(0).getPTLocale().getValue());
        assertNotNull(metadata.getOtherLocales().get(0).getPTLocale().getValue().getLanguage());
        assertNotNull(metadata.getOtherLocales().get(0).getPTLocale().getValue().getLanguage().getLanguageCode());
        assertEquals(Locale.ENGLISH.getDisplayLanguage(), metadata.getOtherLocales().get(0).getPTLocale().getValue().getLanguage().getLanguageCode().getValue());
        assertNotNull(metadata.getOtherLocales().get(0).getPTLocale().getValue().getCountry());
        assertNotNull(metadata.getOtherLocales().get(0).getPTLocale().getValue().getCountry().getCountryCode());
        assertEquals(Locale.ENGLISH.getDisplayCountry(), metadata.getOtherLocales().get(0).getPTLocale().getValue().getCountry().getCountryCode().getValue());
        assertNotNull(metadata.getOtherLocales().get(0).getPTLocale().getValue().getCharacterEncoding());
        assertNotNull(metadata.getOtherLocales().get(0).getPTLocale().getValue().getCharacterEncoding().getMDCharacterSetCode());
        assertEquals(StandardCharsets.UTF_8.displayName(), metadata.getOtherLocales().get(0).getPTLocale().getValue().getCharacterEncoding().getMDCharacterSetCode().getValue());

        // Assess the signature
        assertNotNull(metadata.getDigitalSignatureReference());
        assertNotNull(metadata.getDigitalSignatureReference().getValue());
        assertEquals(S100SEDigitalSignatureReference.DSA, metadata.getDigitalSignatureReference().getValue());
        assertNotNull(metadata.getDigitalSignatureValues());
        assertEquals(1, metadata.getDigitalSignatureValues().size());
        assertNotNull(metadata.getDigitalSignatureValues().get(0));
        assertNotNull(metadata.getDigitalSignatureValues().get(0).getS100SEDigitalSignature());
        assertNotNull(metadata.getDigitalSignatureValues().get(0).getS100SEDigitalSignature().getValue());
        assertNotNull(metadata.getDigitalSignatureValues().get(0).getS100SEDigitalSignature().getValue().getValue());
        assertEquals("signature".getBytes().length, metadata.getDigitalSignatureValues().get(0).getS100SEDigitalSignature().getValue().getValue().length);
    }
}