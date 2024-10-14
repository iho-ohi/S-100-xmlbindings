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

import _int.iho.s100.catalog._5_2.ObjectFactory;
import _int.iho.s100.catalog._5_2.*;
import org.grad.eNav.s100.enums.CodeListValueTypeProvider;
import org.grad.eNav.s100.enums.MaintenanceFrequency;
import org.grad.eNav.s100.enums.RoleCode;
import org.grad.eNav.s100.enums.SecurityClassification;
import org.iso.standards.iso._19115.__3.cit._2.*;
import org.iso.standards.iso._19115.__3.gco._1.DatePropertyType;
import org.iso.standards.iso._19115.__3.gco._1.TMPeriodDurationPropertyType;
import org.iso.standards.iso._19115.__3.mcc._1.AbstractTypedDatePropertyType;
import org.iso.standards.iso._19115.__3.mmi._1.MDMaintenanceFrequencyCodePropertyType;
import org.iso.standards.iso._19115.__3.mmi._1.MDMaintenanceInformationPropertyType;
import org.iso.standards.iso._19115.__3.mmi._1.MDMaintenanceInformationType;
import org.iso.standards.iso._19115.__3.mri._1.MDUsageType;
import org.locationtech.jts.geom.Geometry;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The S100 Dataset Discovery Metadata Builder Class.
 * <p/>
 * This is a basic builder class that enables the generation of the S100
 * Dataset Discovery Metadata contents.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class S100DatasetDiscoveryMetadataBuilder {

    // Class Variables
    protected String fileName;
    protected String datasetID;
    protected String description;
    protected boolean compressionFlag;
    protected boolean dataProtection;
    protected S100ProtectionScheme protectionScheme;
    protected S100SEDigitalSignatureReference digitalSignatureReference;
    protected List<S100DatasetDiscoveryMetadata.DigitalSignatureValue> digitalSignatureValues;
    protected boolean copyright;
    protected SecurityClassification classification;
    protected S100Purpose purpose;
    protected boolean notForNavigation;
    protected String specificUsage;
    protected BigInteger editionNumber;
    protected BigInteger updateNumber;
    protected LocalDate updateApplicationDate;
    protected LocalDate issueDate;
    protected LocalTime issueTime;
    protected Geometry boundingBox;
    protected LocalDateTime timeInstantBegin;
    protected LocalDateTime timeInstantEnd;
    protected S100ProductSpecification productSpecification;
    protected String producingAgency;
    protected RoleCode producingAgencyRole;
    protected String producerCode;
    protected S100EncodingFormat encodingFormat;
    protected Geometry dataCoverages;
    protected String comment;
    protected LocalDate metadataDateStamp;
    protected boolean replacedData;
    protected List<S100NavigationPurpose> navigationPurposes;
    protected MaintenanceFrequency maintenanceFrequency;
    protected LocalDate maintenanceDate;
    protected Duration maintenancePeriod;

    // Objects Factories
    private final ObjectFactory objectFactory;
    private final org.iso.standards.iso._19115.__3.cit._2.ObjectFactory citObjectFactory;

    // Signature Provider
    private final S100ExchangeSetSignatureProvider signatureProvider;

    /**
     * Class Constructor.
     *
     * @param signatureProvider the signature provider for the exchange set dataset discovery metadata
     */
    public S100DatasetDiscoveryMetadataBuilder(S100ExchangeSetSignatureProvider signatureProvider) {
        this.signatureProvider = signatureProvider;

        // Initialise the object factories
        this.objectFactory = new ObjectFactory();
        this.citObjectFactory = new org.iso.standards.iso._19115.__3.cit._2.ObjectFactory();
    }

    /**
     * Sets file name.
     *
     * @param fileName the file name
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     * Sets description.
     *
     * @param description the description
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets dataset id.
     *
     * @param datasetID the dataset id
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setDatasetID(String datasetID) {
        this.datasetID = datasetID;
        return this;
    }

    /**
     * Sets compression flag.
     *
     * @param compressionFlag the compression flag
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setCompressionFlag(boolean compressionFlag) {
        this.compressionFlag = compressionFlag;
        return this;
    }

    /**
     * Sets data protection.
     *
     * @param dataProtection the data protection
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setDataProtection(boolean dataProtection) {
        this.dataProtection = dataProtection;
        return this;
    }

    /**
     * Sets protection scheme.
     *
     * @param protectionScheme the protection scheme
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setProtectionScheme(S100ProtectionScheme protectionScheme) {
        this.protectionScheme = protectionScheme;
        return this;
    }

    /**
     * Sets digital signature reference.
     *
     * @param digitalSignatureReference the digital signature reference
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setDigitalSignatureReference(S100SEDigitalSignatureReference digitalSignatureReference) {
        this.digitalSignatureReference = digitalSignatureReference;
        return this;
    }

    /**
     * Sets digital signature values.
     *
     * @param digitalSignatureValues the digital signature values
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setDigitalSignatureValues(List<S100DatasetDiscoveryMetadata.DigitalSignatureValue> digitalSignatureValues) {
        this.digitalSignatureValues = digitalSignatureValues;
        return this;
    }

    /**
     * Sets copyright.
     *
     * @param copyright the copyright
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setCopyright(boolean copyright) {
        this.copyright = copyright;
        return this;
    }

    /**
     * Sets classification.
     *
     * @param classification the classification
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setClassification(SecurityClassification classification) {
        this.classification = classification;
        return this;
    }

    /**
     * Sets purpose.
     *
     * @param purpose the purpose
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setPurpose(S100Purpose purpose) {
        this.purpose = purpose;
        return this;
    }

    /**
     * Sets not for navigation.
     *
     * @param notForNavigation the not for navigation
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setNotForNavigation(boolean notForNavigation) {
        this.notForNavigation = notForNavigation;
        return this;
    }

    /**
     * Sets specific usage.
     *
     * @param specificUsage the specific usage
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setSpecificUsage(String specificUsage) {
        this.specificUsage = specificUsage;
        return this;
    }

    /**
     * Sets edition number.
     *
     * @param editionNumber the edition number
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setEditionNumber(BigInteger editionNumber) {
        this.editionNumber = editionNumber;
        return this;
    }

    /**
     * Sets update number.
     *
     * @param updateNumber the update number
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setUpdateNumber(BigInteger updateNumber) {
        this.updateNumber = updateNumber;
        return this;
        
    }

    /**
     * Sets update application date.
     *
     * @param updateApplicationDate the update application date
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setUpdateApplicationDate(LocalDate updateApplicationDate) {
        this.updateApplicationDate = updateApplicationDate;
        return this;
    }

    /**
     * Sets issue date.
     *
     * @param issueDate the issue date
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    /**
     * Sets issue time.
     *
     * @param issueTime the issue time
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setIssueTime(LocalTime issueTime) {
        this.issueTime = issueTime;
        return this;
    }

    /**
     * Sets bounding box.
     *
     * @param boundingBox the bounding box
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setBoundingBox(Geometry boundingBox) {
        this.boundingBox = boundingBox;
        return this;
    }

    /**
     * Sets time instant begin.
     *
     * @param timeInstantBegin the time instant begin
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setTimeInstantBegin(LocalDateTime timeInstantBegin) {
        this.timeInstantBegin = timeInstantBegin;
        return this;
    }

    /**
     * Sets time instant end.
     *
     * @param timeInstantEnd the time instant end
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setTimeInstantEnd(LocalDateTime timeInstantEnd) {
        this.timeInstantEnd = timeInstantEnd;
        return this;
    }

    /**
     * Sets product specification.
     *
     * @param productSpecification the product specification
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setProductSpecification(S100ProductSpecification productSpecification) {
        this.productSpecification = productSpecification;
        return this;
    }

    /**
     * Sets producing agency.
     *
     * @param producingAgency the producing agency
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setProducingAgency(String producingAgency) {
        this.producingAgency = producingAgency;
        return this;
    }

    /**
     * Sets producing agency role.
     *
     * @param producingAgencyRole the producing agency role
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setProducingAgencyRole(RoleCode producingAgencyRole) {
        this.producingAgencyRole = producingAgencyRole;
        return this;
    }

    /**
     * Sets producer code.
     *
     * @param producerCode the producer code
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setProducerCode(String producerCode) {
        this.producerCode = producerCode;
        return this;
    }

    /**
     * Sets encoding format.
     *
     * @param encodingFormat the encoding format
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setEncodingFormat(S100EncodingFormat encodingFormat) {
        this.encodingFormat = encodingFormat;
        return this;
    }

    /**
     * Sets data coverages.
     *
     * @param dataCoverages the data coverages
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setDataCoverages(Geometry dataCoverages) {
        this.dataCoverages = dataCoverages;
        return this;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setComment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * Sets metadata date stamp.
     *
     * @param metadataDateStamp the metadata date stamp
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setMetadataDateStamp(LocalDate metadataDateStamp) {
        this.metadataDateStamp = metadataDateStamp;
        return this;
    }

    /**
     * Sets replaced data.
     *
     * @param replacedData the replaced data
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setReplacedData(boolean replacedData) {
        this.replacedData = replacedData;
        return this;
    }

    /**
     * Sets navigation purposes.
     *
     * @param navigationPurposes the navigation purposes
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setNavigationPurposes(List<S100NavigationPurpose> navigationPurposes) {
        this.navigationPurposes = navigationPurposes;
        return this;
    }

    /**
     * Sets maintenance frequency.
     *
     * @param maintenanceFrequency the maintenance frequency
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setMaintenanceFrequency(MaintenanceFrequency maintenanceFrequency) {
        this.maintenanceFrequency = maintenanceFrequency;
        return this;
    }

    /**
     * Sets maintenance date.
     *
     * @param maintenanceDate the maintenance date
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
        return this;
    }

    /**
     * Sets maintenance period.
     *
     * @param maintenancePeriod the maintenance period
     * @return the S-100 dataset discovery metadata builder
     */
    public S100DatasetDiscoveryMetadataBuilder setMaintenancePeriod(Duration maintenancePeriod) {
        this.maintenancePeriod = maintenancePeriod;
        return this;
    }

    /**
     * The main building function of the S-100 exchange set dataset discovery
     * metadata object.
     *
     * @return the built S-100 exchange set dataset discovery metadata object
     */
    public S100DatasetDiscoveryMetadata build(byte[] payload) {
        // Create the metadata object
        final S100DatasetDiscoveryMetadata metadata = new S100DatasetDiscoveryMetadata();

        // Assign the variables
        metadata.setFileName(this.fileName);
        metadata.setDatasetID(this.datasetID);
        metadata.setDescription(S100ExchangeSetUtils.createCharacterStringPropertyType(this.description));
        metadata.setCompressionFlag(this.compressionFlag);
        metadata.setDataProtection(this.dataProtection);
        metadata.setProtectionScheme(S100ProtectionScheme.S_100_P_15);
        metadata.setCopyright(this.copyright);
        final S100ClassificationCodePropertyType s100ClassificationCodePropertyType = new S100ClassificationCodePropertyType();
        s100ClassificationCodePropertyType.setMDClassificationCode(Optional.ofNullable(this.classification)
                .map(CodeListValueTypeProvider::getCodeListValueType)
                .orElse(null));
        metadata.setClassification(s100ClassificationCodePropertyType);
        metadata.setPurpose(this.purpose);
        metadata.setNotForNavigation(this.notForNavigation);
        if(Objects.nonNull(this.specificUsage)) {
            final S100UsagePropertyType s100UsagePropertyType = new S100UsagePropertyType();
            final MDUsageType mdUsageType = new MDUsageType();
            mdUsageType.setSpecificUsage(S100ExchangeSetUtils.createCharacterStringPropertyType(this.specificUsage));
            s100UsagePropertyType.setMDUsage(mdUsageType);
            metadata.setSpecificUsage(s100UsagePropertyType);
        }
        metadata.setEditionNumber(this.editionNumber);
        metadata.setUpdateNumber(this.updateNumber);
        metadata.setUpdateApplicationDate(this.updateApplicationDate);
        metadata.setIssueDate(this.issueDate);
        metadata.setIssueTime(this.issueTime);
        metadata.setBoundingBox(S100ExchangeSetUtils.createS100GeographicBoundingBoxType(this.boundingBox));
        metadata.setTemporalExtent(null);
        metadata.setProductSpecification(this.productSpecification);
        metadata.setProducerCode(this.producerCode);
        S100EncodingFormatPropertyType s100EncodingFormatPropertyType = new S100EncodingFormatPropertyType();
        s100EncodingFormatPropertyType.setValue(this.encodingFormat);
        metadata.setDataCoverages(S100ExchangeSetUtils.createS100DataCoverages(this.dataCoverages));
        metadata.setEncodingFormat(s100EncodingFormatPropertyType);
        metadata.setComment(S100ExchangeSetUtils.createCharacterStringPropertyType(this.comment));
        metadata.setReplacedData(false);
        metadata.setNavigationPurposes(this.navigationPurposes);

        // Responsible Authority
        final CIResponsibilityPropertyType ciResponsibilityPropertyType = new CIResponsibilityPropertyType();
        final CIResponsibilityType ciResponsibilityType = new CIResponsibilityType();
        if(Objects.nonNull(this.producingAgency)) {
            final CIOrganisationType ciOrganisationType = new CIOrganisationType();
            ciOrganisationType.setName(S100ExchangeSetUtils.createCharacterStringPropertyType(this.producingAgency));
            final AbstractCIPartyPropertyType abstractCIPartyPropertyType = new AbstractCIPartyPropertyType();
            abstractCIPartyPropertyType.setAbstractCIParty(this.citObjectFactory.createCIOrganisation(ciOrganisationType));
            ciResponsibilityType.setParties(Collections.singletonList(abstractCIPartyPropertyType));
        }
        if(Objects.nonNull(this.producingAgencyRole)) {
            final CIRoleCodePropertyType ciRoleCodePropertyType = new CIRoleCodePropertyType();
            ciRoleCodePropertyType.setCIRoleCode(this.producingAgencyRole.getCodeListValueType());
            ciResponsibilityType.setRole(ciRoleCodePropertyType);
        }
        ciResponsibilityPropertyType.setCIResponsibility(ciResponsibilityType);
        metadata.setProducingAgency(ciResponsibilityPropertyType);

        // Maintenance information
        final MDMaintenanceInformationPropertyType mdMaintenanceInformationPropertyType = new MDMaintenanceInformationPropertyType();
        final MDMaintenanceInformationType mdMaintenanceInformationType = new MDMaintenanceInformationType();
        if(Objects.nonNull(this.maintenanceFrequency)) {
            final MDMaintenanceFrequencyCodePropertyType maintenanceAndUpdateFrequency = new MDMaintenanceFrequencyCodePropertyType();
            maintenanceAndUpdateFrequency.setMDMaintenanceFrequencyCode(this.maintenanceFrequency.getCodeListValueType());
            mdMaintenanceInformationType.setMaintenanceAndUpdateFrequency(maintenanceAndUpdateFrequency);
        }
        if(Objects.nonNull(this.maintenanceDate)) {
            final AbstractTypedDatePropertyType abstractTypedDatePropertyType = new AbstractTypedDatePropertyType();
            final CIDateType ciDateType = new CIDateType();
            final DatePropertyType datePropertyType = new DatePropertyType();
            datePropertyType.setDate(this.maintenanceDate);
            ciDateType.setDate(datePropertyType);
            abstractTypedDatePropertyType.setAbstractTypedDate(
                    new org.iso.standards.iso._19115.__3.mcc._1.ObjectFactory().createAbstractTypedDate(ciDateType)
            );
            mdMaintenanceInformationType.setMaintenanceDates(Collections.singletonList(abstractTypedDatePropertyType));
        }
        if(Objects.nonNull(this.maintenancePeriod)) {
            final TMPeriodDurationPropertyType userDefinedMaintenanceFrequency = new TMPeriodDurationPropertyType();
            userDefinedMaintenanceFrequency.setTMPeriodDuration(this.maintenancePeriod);
            mdMaintenanceInformationType.setUserDefinedMaintenanceFrequency(userDefinedMaintenanceFrequency);
        }
        mdMaintenanceInformationPropertyType.setMDMaintenanceInformation(mdMaintenanceInformationType);
        metadata.setResourceMaintenance(mdMaintenanceInformationPropertyType);

        // Set the metadata date-stamp
        metadata.setMetadataDateStamp(Optional.ofNullable(this.metadataDateStamp)
                .orElse(this.issueDate));

        //====================================================================//
        //                        METADATA SIGNATURES                         //
        //====================================================================//
        // First choose the signature reference to be used
        final S100SEDigitalSignatureReference signatureReference = Optional.ofNullable(this.digitalSignatureReference)
                .orElse(S100SEDigitalSignatureReference.DSA);
        // And populate the metadata
        final S100SEDigitalSignatureReferencePropertyType digitalSignatureReferencePropertyType = new S100SEDigitalSignatureReferencePropertyType();
        digitalSignatureReferencePropertyType.setValue(signatureReference);
        metadata.setDigitalSignatureReference(digitalSignatureReferencePropertyType);

        // Sign the dataset file if a provider detected
        if(Objects.nonNull(this.signatureProvider) && Objects.nonNull(payload)) {
            // Generate the signature
            final S100SEDigitalSignature signature = this.signatureProvider.generateSignature(
                    this.fileName,
                    signatureReference,
                    payload);

            // And add it to the metadata
            final S100DatasetDiscoveryMetadata.DigitalSignatureValue digitalSignatureValue = new S100DatasetDiscoveryMetadata.DigitalSignatureValue();
            digitalSignatureValue.setS100SEDigitalSignature(this.objectFactory.createS100SEDigitalSignature(signature));
            metadata.getDigitalSignatureValues().add(digitalSignatureValue);
        }
        // Or use the existing signatures if provided
        else if(Objects.nonNull(this.digitalSignatureValues)) {
            metadata.getDigitalSignatureValues().addAll(this.digitalSignatureValues);
        }
        //====================================================================//

        // And return the constructed metadata object
        return metadata;
    }
}
