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
import org.iso.standards.iso._19115.__3.lan._1.PTLocalePropertyType;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * The S100 Support File Discovery Metadata Builder Class.
 * <p/>
 * This is a basic builder class that enables the generation of the S100
 * Dataset Discovery Metadata contents.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class S100SupportFileDiscoveryMetadataBuilder {

    // Class Variables
    protected String fileName;
    protected S100SupportFileRevisionStatus revisionStatus;
    protected BigInteger editionNumber;
    protected LocalDate issueDate;
    protected String supportFileSpecificationName;
    protected String supportFileSpecificationVersion;
    protected LocalDate supportFileSpecificationDate;
    protected S100SupportFileFormat dataType;
    protected String otherDataTypeDescription;
    protected String comment;
    protected boolean compressionFlag;
    protected S100SEDigitalSignatureReference digitalSignatureReference;
    protected List<S100SupportFileDiscoveryMetadata.DigitalSignatureValue> digitalSignatureValues;
    protected Locale defaultLocale;
    protected List<String> supportedResources;
    protected S100ResourcePurpose resourcePurpose;

    // Objects Factories
    private final ObjectFactory objectFactory;
    private final org.iso.standards.iso._19115.__3.lan._1.ObjectFactory lanObjectFactory;

    // Signature Provider
    private final S100ExchangeSetSignatureProvider signatureProvider;

    /**
     * Class Constructor.
     *
     * @param signatureProvider the signature provider for the exchange set dataset discovery metadata
     */
    public S100SupportFileDiscoveryMetadataBuilder(S100ExchangeSetSignatureProvider signatureProvider) {
        this.signatureProvider = signatureProvider;

        // Initialise the object factories
        this.objectFactory = new ObjectFactory();
        this.lanObjectFactory = new org.iso.standards.iso._19115.__3.lan._1.ObjectFactory();
    }

    /**
     * Sets file name.
     *
     * @param fileName the file name
     * @return the S-100 support file discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     * Sets revision status.
     *
     * @param revisionStatus the revision status
     * @return the S-100 support file discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setRevisionStatus(S100SupportFileRevisionStatus revisionStatus) {
        this.revisionStatus = revisionStatus;
        return this;
    }

    /**
     * Sets edition number.
     *
     * @param editionNumber the edition number
     * @return the S-100 support file discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setEditionNumber(BigInteger editionNumber) {
        this.editionNumber = editionNumber;
        return this;
    }

    /**
     * Sets issue date.
     *
     * @param issueDate the issue date
     * @return the S-100 support file discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    /**
     * Sets support file specification name.
     *
     * @param supportFileSpecificationName the support file specification name
     * @return the S-100 support file discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setSupportFileSpecificationName(String supportFileSpecificationName) {
        this.supportFileSpecificationName = supportFileSpecificationName;
        return this;
    }

    /**
     * Sets support file specification version.
     *
     * @param supportFileSpecificationVersion the support file specification version
     * @return the S-100 support file discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setSupportFileSpecificationVersion(String supportFileSpecificationVersion) {
        this.supportFileSpecificationVersion = supportFileSpecificationVersion;
        return this;
    }

    /**
     * Sets support file specification date.
     *
     * @param supportFileSpecificationDate the support file specification date
     * @return the S-100 support file discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setSupportFileSpecificationDate(LocalDate supportFileSpecificationDate) {
        this.supportFileSpecificationDate = supportFileSpecificationDate;
        return this;
    }

    /**
     * Sets data type.
     *
     * @param dataType the data type
     * @return the S-100 support file discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setDataType(S100SupportFileFormat dataType) {
        this.dataType = dataType;
        return this;
    }

    /**
     * Sets other data type description.
     *
     * @param otherDataTypeDescription the other data type description
     * @return the S-100 support file discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setOtherDataTypeDescription(String otherDataTypeDescription) {
        this.otherDataTypeDescription = otherDataTypeDescription;
        return this;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     * @return the S-100 support file discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setComment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * Sets compression flag.
     *
     * @param compressionFlag the compression flag
     * @return the S-100 support file discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setCompressionFlag(boolean compressionFlag) {
        this.compressionFlag = compressionFlag;
        return this;
    }

    /**
     * Sets digital signature reference.
     *
     * @param digitalSignatureReference the digital signature reference
     * @return the S-100 dataset discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setDigitalSignatureReference(S100SEDigitalSignatureReference digitalSignatureReference) {
        this.digitalSignatureReference = digitalSignatureReference;
        return this;
    }

    /**
     * Sets digital signature values.
     *
     * @param digitalSignatureValues the digital signature values
     * @return the S-100 dataset discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setDigitalSignatureValues(List<S100SupportFileDiscoveryMetadata.DigitalSignatureValue> digitalSignatureValues) {
        this.digitalSignatureValues = digitalSignatureValues;
        return this;
    }

    /**
     * Sets default locale.
     *
     * @param defaultLocale the default locale
     * @return the S-100 support file discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
        return this;
    }

    /**
     * Sets supported resources.
     *
     * @param supportedResources the supported resources
     * @return the S-100 support file discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setSupportedResources(List<String> supportedResources) {
        this.supportedResources = supportedResources;
        return this;
    }

    /**
     * Sets resource purpose.
     *
     * @param resourcePurpose the resource purpose
     * @return the S-100 support file discovery metadata builder
     */
    public S100SupportFileDiscoveryMetadataBuilder setResourcePurpose(S100ResourcePurpose resourcePurpose) {
        this.resourcePurpose = resourcePurpose;
        return this;
    }

    /**
     * The main building function of the S-100 exchange set support file
     * discovery metadata object.
     *
     * @return the built S-100 exchange set support file discovery metadata object
     */
    public S100SupportFileDiscoveryMetadata build(byte[] payload) {
        // Create the metadata object
        final S100SupportFileDiscoveryMetadata metadata = new S100SupportFileDiscoveryMetadata();

        // Assign the variables
        metadata.setFileName(this.fileName);
        metadata.setRevisionStatus(this.revisionStatus);
        metadata.setEditionNumber(this.editionNumber);
        metadata.setIssueDate(this.issueDate);
        if(Objects.nonNull(this.supportFileSpecificationName)) {
            final S100SupportFileSpecification supportFileSpecification = new S100SupportFileSpecification();
            supportFileSpecification.setName(this.supportFileSpecificationName);
            supportFileSpecification.setVersion(this.supportFileSpecificationVersion);
            supportFileSpecification.setDate(this.supportFileSpecificationDate);
            metadata.setSupportFileSpecification(supportFileSpecification);
        }
        metadata.setDataType(this.dataType);
        metadata.setOtherDataTypeDescription(this.otherDataTypeDescription);
        metadata.setComment(S100ExchangeSetUtils.createCharacterStringPropertyType(this.comment));
        metadata.setCompressionFlag(this.compressionFlag);
        if(Objects.nonNull(this.defaultLocale)) {
            PTLocalePropertyType ptLocaleProperty = new PTLocalePropertyType();
            ptLocaleProperty.setPTLocale(this.lanObjectFactory.createPTLocale(
                    S100ExchangeSetUtils.createPTLocaleType(this.defaultLocale)
            ));
            metadata.setDefaultLocale(ptLocaleProperty);
        }
        metadata.setSupportedResources(this.supportedResources);
        metadata.setResourcePurpose(this.resourcePurpose);

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
        if(Objects.nonNull(this.signatureProvider)) {
            // Generate the signature
            final S100SEDigitalSignature signature = this.signatureProvider.generateSignature(
                    this.fileName,
                    signatureReference,
                    payload);

            // And add it to the metadata
            final S100SupportFileDiscoveryMetadata.DigitalSignatureValue digitalSignatureValue = new S100SupportFileDiscoveryMetadata.DigitalSignatureValue();
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
