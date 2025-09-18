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
import org.grad.eNav.s100.enums.MaintenanceFrequency;
import org.grad.eNav.s100.enums.RoleCode;
import org.grad.eNav.s100.enums.SecurityClassification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class S100DatasetDiscoveryMetadataBuilderTest {

    // Test Variables
    private S100DatasetDiscoveryMetadataBuilder s100DatasetDiscoveryMetadataBuilder;

    // Fixed Variables
    private DateTimeFormatter timeFormat = DateTimeFormatter.ISO_TIME;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE;
    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_DATE_TIME;

    /**
     * Common setup for all the tests.
     */
    @BeforeEach
    void setup() throws IOException {
        this.s100DatasetDiscoveryMetadataBuilder = new S100DatasetDiscoveryMetadataBuilder((id, algorithm, payload) -> {
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
        assertNotNull(this.s100DatasetDiscoveryMetadataBuilder);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.fileName);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.datasetID);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.description);
        assertFalse(this.s100DatasetDiscoveryMetadataBuilder.compressionFlag);
        assertFalse(this.s100DatasetDiscoveryMetadataBuilder.dataProtection);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.protectionScheme);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.digitalSignatureReference);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.digitalSignatureValues);
        assertFalse(this.s100DatasetDiscoveryMetadataBuilder.copyright);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.classification);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.purpose);
        assertFalse(this.s100DatasetDiscoveryMetadataBuilder.notForNavigation);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.specificUsage);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.editionNumber);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.updateNumber);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.updateApplicationDate);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.issueDate);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.issueTime);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.boundingBox);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.timeInstantBegin);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.timeInstantEnd);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.productSpecification);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.producingAgency);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.producingAgencyRole);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.producingAgencyRole);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.producerCode);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.encodingFormat);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.dataCoverages);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.metadataDateStamp);
        assertFalse(this.s100DatasetDiscoveryMetadataBuilder.replacedData);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.navigationPurposes);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.maintenanceFrequency);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.maintenanceDate);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.maintenancePeriod);
    }

    /**
     * Test that if provided the builder arguments will be correctly set to
     * the builder.
     */
    @Test
    void testSetters() {
        // Perform the setting operations
        this.s100DatasetDiscoveryMetadataBuilder
                .setFileName("file:/dataset.XML")
                .setDatasetID("urn:mrn:gla:grad:s125:datasets:XXXX")
                .setDescription("description")
                .setCompressionFlag(false)
                .setDataProtection(false)
                .setProtectionScheme(S100ProtectionScheme.S_100_P_15)
                .setCopyright(true)
                .setClassification(SecurityClassification.UNCLASSIFIED)
                .setPurpose(S100Purpose.NEW_DATASET)
                .setNotForNavigation(true)
                .setSpecificUsage("testing")
                .setEditionNumber(BigInteger.ONE)
                .setUpdateNumber(BigInteger.ZERO)
                .setUpdateApplicationDate(LocalDate.parse("2023-01-01", this.dateFormat))
                .setIssueDate(LocalDate.parse("2023-01-02", this.dateFormat))
                .setIssueTime(LocalTime.parse("00:00:00", this.timeFormat))
                .setProductSpecification(new S100ProductSpecification())
                .setProducingAgency("producingAgency")
                .setProducingAgencyRole(RoleCode.ORIGINATOR)
                .setProducerCode("producerCode")
                .setEncodingFormat(S100EncodingFormat.GML)
                .setDataCoverages(null)
                .setComment("comment")
                .setMetadataDateStamp(LocalDate.parse("2023-01-03", this.dateFormat))
                .setReplacedData(false)
                .setNavigationPurposes(Collections.singletonList(S100NavigationPurpose.OVERVIEW))
                .setMaintenanceFrequency(MaintenanceFrequency.CONTINUAL);

        assertNotNull(this.s100DatasetDiscoveryMetadataBuilder);
        assertEquals("file:/dataset.XML", this.s100DatasetDiscoveryMetadataBuilder.fileName);
        assertEquals("urn:mrn:gla:grad:s125:datasets:XXXX", this.s100DatasetDiscoveryMetadataBuilder.datasetID);
        assertEquals("description", this.s100DatasetDiscoveryMetadataBuilder.description);
        assertFalse(this.s100DatasetDiscoveryMetadataBuilder.compressionFlag);
        assertFalse(this.s100DatasetDiscoveryMetadataBuilder.dataProtection);
        assertEquals(S100ProtectionScheme.S_100_P_15, this.s100DatasetDiscoveryMetadataBuilder.protectionScheme);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.digitalSignatureReference);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.digitalSignatureValues);
        assertTrue(this.s100DatasetDiscoveryMetadataBuilder.copyright);
        assertEquals(SecurityClassification.UNCLASSIFIED, this.s100DatasetDiscoveryMetadataBuilder.classification);
        assertEquals(S100Purpose.NEW_DATASET, this.s100DatasetDiscoveryMetadataBuilder.purpose);
        assertTrue(this.s100DatasetDiscoveryMetadataBuilder.notForNavigation);
        assertEquals("testing", this.s100DatasetDiscoveryMetadataBuilder.specificUsage);
        assertEquals(BigInteger.ONE, this.s100DatasetDiscoveryMetadataBuilder.editionNumber);
        assertEquals(BigInteger.ZERO, this.s100DatasetDiscoveryMetadataBuilder.updateNumber);
        assertEquals(LocalDate.parse("2023-01-01", this.dateFormat), this.s100DatasetDiscoveryMetadataBuilder.updateApplicationDate);
        assertEquals(LocalDate.parse("2023-01-02", this.dateFormat),this.s100DatasetDiscoveryMetadataBuilder.issueDate);
        assertEquals(LocalTime.parse("00:00:00", this.timeFormat), this.s100DatasetDiscoveryMetadataBuilder.issueTime);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.boundingBox);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.timeInstantBegin);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.timeInstantEnd);
        assertNotNull(this.s100DatasetDiscoveryMetadataBuilder.productSpecification);
        assertEquals("producingAgency", this.s100DatasetDiscoveryMetadataBuilder.producingAgency);
        assertEquals(RoleCode.ORIGINATOR,this.s100DatasetDiscoveryMetadataBuilder.producingAgencyRole);
        assertEquals("producerCode", this.s100DatasetDiscoveryMetadataBuilder.producerCode);
        assertEquals(S100EncodingFormat.GML, this.s100DatasetDiscoveryMetadataBuilder.encodingFormat);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.dataCoverages);
        assertEquals(LocalDate.parse("2023-01-03", this.dateFormat), this.s100DatasetDiscoveryMetadataBuilder.metadataDateStamp);
        assertFalse(this.s100DatasetDiscoveryMetadataBuilder.replacedData);
        assertNotNull(this.s100DatasetDiscoveryMetadataBuilder.navigationPurposes);
        assertTrue(this.s100DatasetDiscoveryMetadataBuilder.navigationPurposes.contains(S100NavigationPurpose.OVERVIEW));
        assertEquals(MaintenanceFrequency.CONTINUAL, this.s100DatasetDiscoveryMetadataBuilder.maintenanceFrequency);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.maintenanceDate);
        assertNull(this.s100DatasetDiscoveryMetadataBuilder.maintenancePeriod);
    }

    /**
     * Test that the S-100 Exchange Set Catalogue Discovery Metadata builder can
     * correctly build a discovery metadata XML if the appropriate parameters
     * have been provided.
     */
    @Test
    void testBuild() {
        // Perform the setting operations and build
        final S100DatasetDiscoveryMetadata metadata = this.s100DatasetDiscoveryMetadataBuilder
                .setFileName("file:/dataset.XML")
                .setDatasetID("urn:mrn:gla:grad:s125:datasets:XXXX")
                .setDescription("description")
                .setCompressionFlag(false)
                .setDataProtection(false)
                .setProtectionScheme(S100ProtectionScheme.S_100_P_15)
                .setCopyright(true)
                .setClassification(SecurityClassification.UNCLASSIFIED)
                .setPurpose(S100Purpose.NEW_DATASET)
                .setNotForNavigation(true)
                .setSpecificUsage("testing")
                .setEditionNumber(BigInteger.ONE)
                .setUpdateNumber(BigInteger.ZERO)
                .setUpdateApplicationDate(LocalDate.parse("2023-01-01", this.dateFormat))
                .setIssueDate(LocalDate.parse("2023-01-02", this.dateFormat))
                .setIssueTime(LocalTime.parse("00:00:00", this.timeFormat))
                .setProductSpecification(new S100ProductSpecification())
                .setProducingAgency("producingAgency")
                .setProducingAgencyRole(RoleCode.ORIGINATOR)
                .setProducerCode("producerCode")
                .setEncodingFormat(S100EncodingFormat.GML)
                .setDataCoverages(null)
                .setComment("comment")
                .setMetadataDateStamp(LocalDate.parse("2023-01-03", this.dateFormat))
                .setReplacedData(false)
                .setNavigationPurposes(Collections.singletonList(S100NavigationPurpose.OVERVIEW))
                .setMaintenanceFrequency(MaintenanceFrequency.CONTINUAL)
                .build("dataset".getBytes());

        // Assess the main information
        assertNotNull(metadata);
        assertEquals("file:/dataset.XML", metadata.getFileName());
        assertEquals("urn:mrn:gla:grad:s125:datasets:XXXX", metadata.getDatasetID());
        assertNotNull(metadata.getDescription());
        assertNotNull(metadata.getDescription().getCharacterString());
        assertEquals("description", metadata.getDescription().getCharacterString().getValue());
        assertFalse(metadata.isCompressionFlag());
        assertFalse(metadata.isDataProtection());
        assertEquals(S100ProtectionScheme.S_100_P_15, metadata.getProtectionScheme());
        assertTrue(metadata.isCopyright());
        assertNotNull(metadata.getClassification());
        assertNotNull(metadata.getClassification().getIsoType());
        assertNotNull(metadata.getClassification().getMDClassificationCode());
        assertEquals(SecurityClassification.UNCLASSIFIED.getValue(), metadata.getClassification().getMDClassificationCode().getValue());
        assertEquals(S100Purpose.NEW_DATASET, metadata.getPurpose());
        assertTrue(metadata.isNotForNavigation());
        assertNotNull(metadata.getSpecificUsage());
        assertNotNull(metadata.getSpecificUsage().getMDUsage());
        assertNotNull(metadata.getSpecificUsage().getMDUsage().getSpecificUsage());
        assertNotNull(metadata.getSpecificUsage().getMDUsage().getSpecificUsage().getCharacterString());
        assertEquals("testing", metadata.getSpecificUsage().getMDUsage().getSpecificUsage().getCharacterString().getValue());
        assertEquals(BigInteger.ONE, metadata.getEditionNumber());
        assertEquals(BigInteger.ZERO, metadata.getUpdateNumber());
        assertEquals(LocalDate.parse("2023-01-01", this.dateFormat), metadata.getUpdateApplicationDate());
        assertEquals(LocalDate.parse("2023-01-02", this.dateFormat), metadata.getIssueDate());
        assertEquals(LocalTime.parse("00:00:00", this.timeFormat), metadata.getIssueTime());
        assertNotNull(metadata.getProductSpecification());

        // Assess the producing agency information
        assertNotNull(metadata.getProducingAgency());
        assertNotNull(metadata.getProducingAgency().getCIResponsibility());
        assertNotNull(metadata.getProducingAgency().getCIResponsibility().getParties());
        assertEquals(1, metadata.getProducingAgency().getCIResponsibility().getParties().size());
        assertNotNull(metadata.getProducingAgency().getCIResponsibility().getParties().get(0));
        assertNotNull(metadata.getProducingAgency().getCIResponsibility().getParties().get(0).getAbstractCIParty());
        assertNotNull(metadata.getProducingAgency().getCIResponsibility().getParties().get(0).getAbstractCIParty().getValue());
        assertNotNull(metadata.getProducingAgency().getCIResponsibility().getParties().get(0).getAbstractCIParty().getValue());
        assertNotNull(metadata.getProducingAgency().getCIResponsibility().getRole());
        assertNotNull(metadata.getProducingAgency().getCIResponsibility().getRole().getCIRoleCode());
        assertEquals(RoleCode.ORIGINATOR.getValue(), metadata.getProducingAgency().getCIResponsibility().getRole().getCIRoleCode().getValue());

        // Assess the maintenance information
        assertEquals("producerCode", metadata.getProducerCode());
        assertNotNull(metadata.getEncodingFormat());
        assertEquals(S100EncodingFormat.GML, metadata.getEncodingFormat().getValue());
        assertNotNull(metadata.getDataCoverages());
        assertTrue(metadata.getDataCoverages().isEmpty());
        assertNotNull(metadata.getComment());
        assertNotNull(metadata.getComment().getCharacterString());
        assertEquals("comment", metadata.getComment().getCharacterString().getValue());
        assertEquals(LocalDate.parse("2023-01-03", this.dateFormat), metadata.getMetadataDateStamp());
        assertFalse(metadata.isReplacedData());
        assertNotNull(metadata.getNavigationPurposes());
        assertEquals(1, metadata.getNavigationPurposes().size());
        assertTrue(metadata.getNavigationPurposes().contains(S100NavigationPurpose.OVERVIEW));
        assertNotNull(metadata.getResourceMaintenance());
        assertNotNull(metadata.getResourceMaintenance().getMDMaintenanceInformation());
        assertNotNull(metadata.getResourceMaintenance().getMDMaintenanceInformation().getMaintenanceAndUpdateFrequency());
        assertNotNull(metadata.getResourceMaintenance().getMDMaintenanceInformation().getMaintenanceDates());
        assertTrue(metadata.getResourceMaintenance().getMDMaintenanceInformation().getMaintenanceDates().isEmpty());
        assertNull(metadata.getResourceMaintenance().getMDMaintenanceInformation().getUserDefinedMaintenanceFrequency());
        assertNotNull(metadata.getResourceMaintenance().getMDMaintenanceInformation().getMaintenanceAndUpdateFrequency().getMDMaintenanceFrequencyCode());
        assertEquals(MaintenanceFrequency.CONTINUAL.getValue(), metadata.getResourceMaintenance().getMDMaintenanceInformation().getMaintenanceAndUpdateFrequency().getMDMaintenanceFrequencyCode().getValue());

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