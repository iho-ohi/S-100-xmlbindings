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
public class S100CatalogueDiscoveryMetadataBuilder {

    // Class Variables
    protected String fileName;
    protected S100Purpose purpose;
    protected BigInteger editionNumber;
    protected LocalDate issueDate;
    protected S100CatalogueScope scope;
    protected String versionNumber;
    protected S100ProductSpecification productSpecification;
    protected S100SEDigitalSignatureReference digitalSignatureReference;
    protected List<S100CatalogueDiscoveryMetadata.DigitalSignatureValue> digitalSignatureValues;
    protected boolean compressionFlag;
    protected Locale defaultLocale;
    protected List<Locale> otherLocales;
    

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
    public S100CatalogueDiscoveryMetadataBuilder(S100ExchangeSetSignatureProvider signatureProvider) {
        this.signatureProvider = signatureProvider;

        // Initialise the object factories
        this.objectFactory = new ObjectFactory();
        this.lanObjectFactory = new org.iso.standards.iso._19115.__3.lan._1.ObjectFactory();
    }

    /**
     * Sets file name.
     *
     * @param fileName the file name
     * @return the S-100 catalogue discovery metadata builder
     */
    public S100CatalogueDiscoveryMetadataBuilder setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     * Sets purpose.
     *
     * @param purpose the purpose
     * @return the S-100 catalogue discovery metadata builder
     */
    public S100CatalogueDiscoveryMetadataBuilder setPurpose(S100Purpose purpose) {
        this.purpose = purpose;
        return this;
    }

    /**
     * Sets edition number.
     *
     * @param editionNumber the edition number
     * @return the S-100 catalogue discovery metadata builder
     */
    public S100CatalogueDiscoveryMetadataBuilder setEditionNumber(BigInteger editionNumber) {
        this.editionNumber = editionNumber;
        return this;
    }

    /**
     * Sets scope.
     *
     * @param scope the scope
     * @return the S-100 catalogue discovery metadata builder
     */
    public S100CatalogueDiscoveryMetadataBuilder setScope(S100CatalogueScope scope) {
        this.scope = scope;
        return this;
    }

    /**
     * Sets version number.
     *
     * @param versionNumber the version number
     * @return the S-100 catalogue discovery metadata builder
     */
    public S100CatalogueDiscoveryMetadataBuilder setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
        return this;
    }

    /**
     * Sets issue date.
     *
     * @param issueDate the issue date
     * @return the S-100 catalogue discovery metadata builder
     */
    public S100CatalogueDiscoveryMetadataBuilder setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    /**
     * Sets product specification.
     *
     * @param productSpecification the product specification
     * @return the S-100 catalogue discovery metadata builder
     */
    public S100CatalogueDiscoveryMetadataBuilder setProductSpecification(S100ProductSpecification productSpecification) {
        this.productSpecification = productSpecification;
        return this;
    }

    /**
     * Sets digital signature reference.
     *
     * @param digitalSignatureReference the digital signature reference
     * @return the S-100 catalogue discovery metadata builder
     */
    public S100CatalogueDiscoveryMetadataBuilder setDigitalSignatureReference(S100SEDigitalSignatureReference digitalSignatureReference) {
        this.digitalSignatureReference = digitalSignatureReference;
        return this;
    }

    /**
     * Sets digital signature values.
     *
     * @param digitalSignatureValues the digital signature values
     * @return the S-100 catalogue discovery metadata builder
     */
    public S100CatalogueDiscoveryMetadataBuilder setDigitalSignatureValues(List<S100CatalogueDiscoveryMetadata.DigitalSignatureValue> digitalSignatureValues) {
        this.digitalSignatureValues = digitalSignatureValues;
        return this;
    }

    /**
     * Sets compression flag.
     *
     * @param compressionFlag the compression flag
     * @return the S-100 catalogue discovery metadata builder
     */
    public S100CatalogueDiscoveryMetadataBuilder setCompressionFlag(boolean compressionFlag) {
        this.compressionFlag = compressionFlag;
        return this;
    }

    /**
     * Sets default locale.
     *
     * @param defaultLocale the default locale
     * @return the S-100 catalogue discovery metadata builder
     */
    public S100CatalogueDiscoveryMetadataBuilder setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
        return this;
    }

    /**
     * Sets other locales.
     *
     * @param otherLocales the other locales
     * @return the S-100 catalogue discovery metadata builder
     */
    public S100CatalogueDiscoveryMetadataBuilder setOtherLocales(List<Locale> otherLocales) {
        this.otherLocales = otherLocales;
        return this;
    }

    /**
     * The main building function of the S-100 exchange set catalogue discovery
     * metadata object.
     *
     * @return the built S-100 exchange set catalogue discovery metadata object
     */
    public S100CatalogueDiscoveryMetadata build(byte[] payload) {
        // Create the metadata object
        final S100CatalogueDiscoveryMetadata metadata = new S100CatalogueDiscoveryMetadata();

        // Assign the variables
        metadata.setFileName(this.fileName);
        metadata.setPurpose(this.purpose);
        metadata.setEditionNumber(this.editionNumber);
        metadata.setIssueDate(this.issueDate);
        metadata.setScope(this.scope);
        metadata.setVersionNumber(this.versionNumber);
        metadata.setIssueDate(this.issueDate);
        metadata.setProductSpecification(this.productSpecification);
        metadata.setCompressionFlag(this.compressionFlag);
        if(Objects.nonNull(this.defaultLocale)) {
            PTLocalePropertyType ptLocaleProperty = new PTLocalePropertyType();
            ptLocaleProperty.setPTLocale(this.lanObjectFactory.createPTLocale(
                    S100ExchangeSetUtils.createPTLocaleType(this.defaultLocale)
            ));
            metadata.setDefaultLocale(ptLocaleProperty);
        }
        if(Objects.nonNull(this.otherLocales)) {
            this.otherLocales.stream()
                    .map(S100ExchangeSetUtils::createPTLocaleType)
                    .map(this.lanObjectFactory::createPTLocale)
                    .map(jaxb -> {
                        PTLocalePropertyType ptLocaleProperty = new PTLocalePropertyType();
                        ptLocaleProperty.setPTLocale(jaxb);
                        return ptLocaleProperty;
                    })
                    .forEach(metadata.getOtherLocales()::add);
        }

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
            final S100CatalogueDiscoveryMetadata.DigitalSignatureValue digitalSignatureValue = new S100CatalogueDiscoveryMetadata.DigitalSignatureValue();
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
