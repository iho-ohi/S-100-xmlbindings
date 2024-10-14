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

class S100SupportFileDiscoveryMetadataBuilderTest {

    // Test Variables
    private S100SupportFileDiscoveryMetadataBuilder s100SupportFileDiscoveryMetadataBuilder;

    // Fixed Variables
    private DateTimeFormatter timeFormat = DateTimeFormatter.ISO_TIME;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE;
    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_DATE_TIME;

    /**
     * Common setup for all the tests.
     */
    @BeforeEach
    void setup() throws IOException {
        this.s100SupportFileDiscoveryMetadataBuilder = new S100SupportFileDiscoveryMetadataBuilder((id, algorithm, payload) -> {
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
        assertNotNull(this.s100SupportFileDiscoveryMetadataBuilder);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.fileName);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.revisionStatus);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.editionNumber);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.issueDate);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.supportFileSpecificationName);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.supportFileSpecificationVersion);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.supportFileSpecificationDate);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.dataType);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.otherDataTypeDescription);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.comment);
        assertFalse(this.s100SupportFileDiscoveryMetadataBuilder.compressionFlag);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.digitalSignatureReference);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.digitalSignatureValues);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.defaultLocale);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.supportedResources);
        assertNull(this.s100SupportFileDiscoveryMetadataBuilder.resourcePurpose);
    }

    /**
     * Test that if provided the builder arguments will be correctly set to
     * the builder.
     */
    @Test
    void testSetters() {
        // Perform the setting operations
        this.s100SupportFileDiscoveryMetadataBuilder
                .setFileName("file:/supportFile.XML")
                .setRevisionStatus(S100SupportFileRevisionStatus.NEW)
                .setEditionNumber(BigInteger.ONE)
                .setIssueDate(LocalDate.parse("2023-01-02", this.dateFormat))
                .setSupportFileSpecificationName("supportFileSpecificationName")
                .setSupportFileSpecificationVersion("supportFileSpecificationVersion")
                .setSupportFileSpecificationDate(LocalDate.parse("2023-01-01", this.dateFormat))
                .setDataType(S100SupportFileFormat.XML)
                .setOtherDataTypeDescription("test")
                .setComment("comment")
                .setCompressionFlag(false)
                .setDefaultLocale(Locale.UK)
                .setSupportedResources(Collections.singletonList("supportedResource"))
                .setResourcePurpose(S100ResourcePurpose.SUPPORT_FILE);

        assertNotNull(this.s100SupportFileDiscoveryMetadataBuilder);
        assertEquals("file:/supportFile.XML", this.s100SupportFileDiscoveryMetadataBuilder.fileName);
        assertEquals(S100SupportFileRevisionStatus.NEW, this.s100SupportFileDiscoveryMetadataBuilder.revisionStatus);
        assertEquals(BigInteger.ONE, this.s100SupportFileDiscoveryMetadataBuilder.editionNumber);
        assertEquals(LocalDate.parse("2023-01-02", this.dateFormat), this.s100SupportFileDiscoveryMetadataBuilder.issueDate);
        assertEquals("supportFileSpecificationName", this.s100SupportFileDiscoveryMetadataBuilder.supportFileSpecificationName);
        assertEquals("supportFileSpecificationVersion", this.s100SupportFileDiscoveryMetadataBuilder.supportFileSpecificationVersion);
        assertEquals(LocalDate.parse("2023-01-01", this.dateFormat), this.s100SupportFileDiscoveryMetadataBuilder.supportFileSpecificationDate);
        assertEquals(S100SupportFileFormat.XML, this.s100SupportFileDiscoveryMetadataBuilder.dataType);
        assertEquals("test", this.s100SupportFileDiscoveryMetadataBuilder.otherDataTypeDescription);
        assertEquals("comment", this.s100SupportFileDiscoveryMetadataBuilder.comment);
        assertFalse(this.s100SupportFileDiscoveryMetadataBuilder.compressionFlag);
        assertEquals(Locale.UK, this.s100SupportFileDiscoveryMetadataBuilder.defaultLocale);
        assertNotNull(this.s100SupportFileDiscoveryMetadataBuilder.supportedResources);
        assertEquals(1, this.s100SupportFileDiscoveryMetadataBuilder.supportedResources.size());
        assertEquals("supportedResource", this.s100SupportFileDiscoveryMetadataBuilder.supportedResources.get(0));
        assertEquals(S100ResourcePurpose.SUPPORT_FILE, this.s100SupportFileDiscoveryMetadataBuilder.resourcePurpose);
    }

    /**
     * Test that the S-100 Exchange Set Support File Discovery Metadata builder
     * can correctly build a discovery metadata XML if the appropriate
     * parameters have been provided.
     */
    @Test
    void testBuild() {
        // Perform the setting operations and build
        final S100SupportFileDiscoveryMetadata metadata = this.s100SupportFileDiscoveryMetadataBuilder
                .setFileName("file:/supportFile.XML")
                .setRevisionStatus(S100SupportFileRevisionStatus.NEW)
                .setEditionNumber(BigInteger.ONE)
                .setIssueDate(LocalDate.parse("2023-01-02", this.dateFormat))
                .setSupportFileSpecificationName("supportFileSpecificationName")
                .setSupportFileSpecificationVersion("supportFileSpecificationVersion")
                .setSupportFileSpecificationDate(LocalDate.parse("2023-01-01", this.dateFormat))
                .setDataType(S100SupportFileFormat.XML)
                .setOtherDataTypeDescription("test")
                .setComment("comment")
                .setCompressionFlag(false)
                .setDefaultLocale(Locale.UK)
                .setSupportedResources(Collections.singletonList("supportedResource"))
                .setResourcePurpose(S100ResourcePurpose.SUPPORT_FILE)
                .build("supportFile".getBytes());

        // Assess the main information
        assertNotNull(metadata);
        assertEquals("file:/supportFile.XML", metadata.getFileName());
        assertEquals(S100SupportFileRevisionStatus.NEW, metadata.getRevisionStatus());
        assertEquals(BigInteger.ONE, metadata.getEditionNumber());
        assertEquals(LocalDate.parse("2023-01-02", this.dateFormat), metadata.getIssueDate());
        assertNotNull(metadata.getSupportFileSpecification());
        assertEquals("supportFileSpecificationName", metadata.getSupportFileSpecification().getName());
        assertEquals("supportFileSpecificationVersion", metadata.getSupportFileSpecification().getVersion());
        assertEquals(LocalDate.parse("2023-01-01", this.dateFormat), metadata.getSupportFileSpecification().getDate());
        assertEquals(S100SupportFileFormat.XML, metadata.getDataType());
        assertEquals("test", metadata.getOtherDataTypeDescription());
        assertNotNull(metadata.getComment());
        assertNotNull(metadata.getComment().getCharacterString());
        assertEquals("comment", metadata.getComment().getCharacterString().getValue());
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

        // Assess the support information
        assertNotNull(metadata.getSupportedResources());
        assertEquals(1, metadata.getSupportedResources().size());
        assertEquals("supportedResource", metadata.getSupportedResources().get(0));
        assertEquals(S100ResourcePurpose.SUPPORT_FILE, metadata.getResourcePurpose());

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