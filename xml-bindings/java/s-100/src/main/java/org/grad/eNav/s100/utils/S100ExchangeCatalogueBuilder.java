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
import jakarta.xml.bind.JAXBException;
import org.grad.eNav.s100.enums.CodeListValueTypeProvider;
import org.grad.eNav.s100.enums.TelephoneType;
import org.iso.standards.iso._19115.__3.cit._2.CITelephoneTypeCodePropertyType;
import org.iso.standards.iso._19115.__3.lan._1.PTLocalePropertyType;

import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The S100 Exchange Catalogue Builder Class.
 * <p/>
 * This is a basic builder class that enables the generation of the S100
 * Exchange Set Catalogue file (named "CATALOG.XML") contents.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class S100ExchangeCatalogueBuilder {

    // Class Variables
    protected String identifier;
    protected LocalDateTime dateTime;
    protected String dataServerIdentifier;
    protected String organization;
    protected List<String> electronicMailAddresses;
    protected String phone;
    protected TelephoneType phoneType;
    protected String city;
    protected String postalCode;
    protected String country;
    protected List<Locale> locales;
    protected String administrativeArea;
    protected String description;
    protected String comment;
    protected List<S100ProductSpecification> productSpecifications;

    // Certificate Information
    protected Map<String, X509Certificate> certificateMap;

    // Objects Factories
    private final ObjectFactory objectFactory;
    private final org.iso.standards.iso._19115.__3.lan._1.ObjectFactory lanObjectFactory;

    // Signature Provider
    private final S100ExchangeSetSignatureProvider signatureProvider;

    // Metadata Providers
    private List<DatasetDiscoveryMetadataProvider> datasetDiscoveryMetadataProviders;
    private List<SupportFileDiscoveryMetadataProvider> supportFileDiscoveryMetadataProviders;
    private List<CatalogueDiscoveryMetadataProvider> catalogueDiscoveryMetadataProviders;

    /**
     * Class Constructor.
     *
     * @param signatureProvider the signature provider for the exchange set
     */
    public S100ExchangeCatalogueBuilder(S100ExchangeSetSignatureProvider signatureProvider) {
        this.signatureProvider = signatureProvider;

        // Initialise the object factories
        this.objectFactory = new ObjectFactory();
        this.lanObjectFactory = new org.iso.standards.iso._19115.__3.lan._1.ObjectFactory();

        // Initialise the certificates map
        this.certificateMap = new HashMap<>();

        // And initialise the metadata provider lists
        this.datasetDiscoveryMetadataProviders = new ArrayList<>();
        this.supportFileDiscoveryMetadataProviders = new ArrayList<>();
        this.catalogueDiscoveryMetadataProviders = new ArrayList<>();
    }

    /**
     * Sets the exchange set identifier string.
     *
     * @param identifier the exchange set identifier string
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    /**
     * Sets the exchange set date time.
     *
     * @param dateTime the exchange set date time
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    /**
     * Sets the exchange set data server identifier.
     *
     * @param dataServerIdentifier the exchange set data server identifier
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setDataServerIdentifier(String dataServerIdentifier) {
        this.dataServerIdentifier = dataServerIdentifier;
        return this;
    }

    /**
     * Sets the exchange set organization.
     *
     * @param organization the exchange set organization
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    /**
     * Sets the exchange set electronic mail addresses.
     *
     * @param electronicMailAddresses the exchange set electronic mail addresses
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setElectronicMailAddresses(List<String> electronicMailAddresses) {
        this.electronicMailAddresses = electronicMailAddresses;
        return this;
    }

    /**
     * Sets the exchange set phone.
     *
     * @param phone the exchange set phone
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Sets the exchange set phone type.
     *
     * @param phoneType the exchange set phone type
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setPhoneType(TelephoneType phoneType) {
        this.phoneType = phoneType;
        return this;
    }

    /**
     * Sets the exchange set city.
     *
     * @param city the exchange set city
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    /**
     * Sets the exchange set postal code.
     *
     * @param postalCode the exchange set postal code
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    /**
     * Sets the exchange set country.
     *
     * @param country the exchange set country
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    /**
     * Sets the exchange set locales.
     *
     * @param locales the exchange set locales
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setLocales(List<Locale> locales) {
        this.locales = locales;
        return this;
    }

    /**
     * Sets the exchange set administrative area.
     *
     * @param administrativeArea the exchange set administrative area
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setAdministrativeArea(String administrativeArea) {
        this.administrativeArea = administrativeArea;
        return this;
    }

    /**
     * Sets the exchange set description information.
     *
     * @param description the exchange set description information
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the exchange set comment information.
     *
     * @param comment the exchange set comment information
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setComment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * Sets the exchange set list of product specifications.
     *
     * @param productSpecifications the list of exchange set product specifications
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setProductSpecification(List<S100ProductSpecification> productSpecifications) {
        this.productSpecifications = productSpecifications;
        return this;
    }

    /**
     * Sets the exchange set certificates.
     *
     * @param certificateMap the certificates to be used
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setCertificates(Map<String, X509Certificate> certificateMap) {
        this.certificateMap.putAll(certificateMap);
        return this;
    }

    /**
     * Sets the exchange set certificates in PEM representation.
     *
     * @param certificateMap the certificates to be used
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder setCertificatesByPem(Map<String, String> certificateMap) throws CertificateException {
        // Translate the byte array map to an X.509 certificate one
        final Map<String, X509Certificate> x509CertificateMap = certificateMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e-> {
                            try {
                                return S100ExchangeSetUtils.getCertFromPem(e.getValue());
                            } catch (CertificateException ex) {
                                throw new RuntimeException(ex);
                            }
                        }));
        // And use the default setter
        return this.setCertificates(x509CertificateMap);
    }

    /**
     * Appends a new dataset metadata provider.
     *
     * @param provider the dataset metadata provider to be appended
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder addDatasetMetadata(DatasetDiscoveryMetadataProvider provider) {
        this.datasetDiscoveryMetadataProviders.add(provider);
        return this;
    }

    /**
     * Appends a new support file metadata provider.
     *
     * @param provider the support file metadata provider to be appended
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder addSupportFileMetadata(SupportFileDiscoveryMetadataProvider provider) {
        this.supportFileDiscoveryMetadataProviders.add(provider);
        return this;
    }

    /**
     * Appends a new catalogue metadata provider.
     *
     * @param provider the catalogue metadata provider to be appended
     * @return the S100 exchange set catalogue builder
     */
    public S100ExchangeCatalogueBuilder addCatalogueMetadata(CatalogueDiscoveryMetadataProvider provider) {
        this.catalogueDiscoveryMetadataProviders.add(provider);
        return this;
    }

    /**
     * The main function that builds the exchange  set catalog XML file.
     *
     * @return the exchange set catalogue XML file contents
     * @throws JAXBException for any exceptions while marshalling the XML content
     */
    public S100ExchangeCatalogue build() throws JAXBException, CertificateEncodingException {
        // Create a new exchange set catalogue
        final S100ExchangeCatalogue exchangeCatalogue  = new S100ExchangeCatalogue();

        // ================================================================== //
        //             Set the exchange set identifier information            //
        // ================================================================== //
        final S100ExchangeCatalogueIdentifier exchangeCatalogueIdentifier = new S100ExchangeCatalogueIdentifier();
        exchangeCatalogueIdentifier.setIdentifier(
                this.identifier);
        exchangeCatalogueIdentifier.setDateTime(
                Optional.ofNullable(this.dateTime).orElse(LocalDateTime.now()));
        exchangeCatalogue.setIdentifier(exchangeCatalogueIdentifier);

        // Create a UUID for this server from a constant string
        exchangeCatalogue.setDataServerIdentifier(this.dataServerIdentifier);
        // ================================================================== //

        // ================================================================== //
        //        Set the exchange set point of contact information           //
        // ================================================================== //
        final S100CataloguePointofContact cataloguePointOfContact = new S100CataloguePointofContact();
        cataloguePointOfContact.setOrganization(S100ExchangeSetUtils.createCharacterStringPropertyType(this.organization));

        // Set the address
        final S100AddressType addressType = new S100AddressType();
        addressType.setElectronicMailAddresses(S100ExchangeSetUtils.createCharacterStringPropertyTypeList(this.electronicMailAddresses));
        addressType.setAdministrativeArea(S100ExchangeSetUtils.createCharacterStringPropertyType(this.administrativeArea));
        addressType.setPostalCode(S100ExchangeSetUtils.createCharacterStringPropertyType(this.postalCode));
        addressType.setCity(S100ExchangeSetUtils.createCharacterStringPropertyType(this.city));
        addressType.setCountry(S100ExchangeSetUtils.createCharacterStringPropertyType(this.country));
        cataloguePointOfContact.setAddress(addressType);

        // Set the phone number
        final S100PhoneType phoneType = new S100PhoneType();
        phoneType.setNumber(S100ExchangeSetUtils.createCharacterStringPropertyType(this.phone));
        final CITelephoneTypeCodePropertyType ciTelephoneTypeCodePropertyType = new CITelephoneTypeCodePropertyType();
        ciTelephoneTypeCodePropertyType.setCITelephoneTypeCode(Optional.ofNullable(this.phoneType)
                .map(CodeListValueTypeProvider::getCodeListValueType)
                .orElse(null));
        phoneType.setNumberType(ciTelephoneTypeCodePropertyType);
        cataloguePointOfContact.setPhone(phoneType);

        // And set the point of content information
        exchangeCatalogue.setContact(cataloguePointOfContact);
        // ================================================================== //

        // ================================================================== //
        //             Set the Data Product Specification                     //
        // ================================================================== //
        exchangeCatalogue.setProductSpecifications(this.productSpecifications);
        // ================================================================== //

        // ================================================================== //
        //                 Set the Certificate Information                    //
        // ================================================================== //
        final S100SECertificateContainerType s100SECertificateContainerType = new S100SECertificateContainerType();
        final S100SECertificateContainerType.SchemeAdministrator schemeAdministrator = new S100SECertificateContainerType.SchemeAdministrator();
        schemeAdministrator.setId(this.organization);
        s100SECertificateContainerType.setSchemeAdministrator(schemeAdministrator);
        if(Objects.nonNull(this.certificateMap)) {
            for(Map.Entry<String, X509Certificate> certificateEntry : this.certificateMap.entrySet()) {
                final S100SECertificateType certificateType = new S100SECertificateType();
                certificateType.setId(certificateEntry.getKey());
                certificateType.setIssuer(certificateEntry.getValue().getIssuerX500Principal().getName());
                certificateType.setValue(S100ExchangeSetUtils.getPemFromCert(certificateEntry.getValue()));
                s100SECertificateContainerType.getCertificates().add(certificateType);
            }
        }
        exchangeCatalogue.getCertificates().add(s100SECertificateContainerType);
        // ================================================================== //

        // ================================================================== //
        //                  Set the Locale Information                        //
        // ================================================================== //
        for (Locale locale : this.locales) {
            PTLocalePropertyType ptLocaleProperty = new PTLocalePropertyType();
            ptLocaleProperty.setPTLocale(this.lanObjectFactory.createPTLocale(
                    S100ExchangeSetUtils.createPTLocaleType(locale)
            ));

            // Check if to add as the default or as another locale
            if (Objects.isNull(exchangeCatalogue.getDefaultLocale())) {
                exchangeCatalogue.setDefaultLocale(ptLocaleProperty);
            } else {
                exchangeCatalogue.getOtherLocales().add(ptLocaleProperty);
            }
        }
        // ================================================================== //

        // ================================================================== //
        //                  Set the Description/Comments                      //
        // ================================================================== //
        Optional.ofNullable(this.description)
                .map(S100ExchangeSetUtils::createCharacterStringPropertyType)
                .ifPresent(exchangeCatalogue::setExchangeCatalogueDescription);
        Optional.ofNullable(this.comment)
                .map(S100ExchangeSetUtils::createCharacterStringPropertyType)
                .ifPresent(exchangeCatalogue::setExchangeCatalogueComment);
        // ================================================================== //

        // ================================================================== //
        //                 Append the Metadata Information                    //
        // ================================================================== //
        // Initialise the metadata lists
        exchangeCatalogue.setDatasetDiscoveryMetadata(
                new S100ExchangeCatalogue.DatasetDiscoveryMetadata()
        );
        exchangeCatalogue.setSupportFileDiscoveryMetadata(
                new S100ExchangeCatalogue.SupportFileDiscoveryMetadata()
        );
        exchangeCatalogue.setCatalogueDiscoveryMetadata(
                new S100ExchangeCatalogue.CatalogueDiscoveryMetadata()
        );
        // Add more metadata lists from the providers if any
        exchangeCatalogue.getDatasetDiscoveryMetadata()
                .getS100DatasetDiscoveryMetadatas()
                .addAll(this.datasetDiscoveryMetadataProviders.stream()
                        .map(provider -> provider.buildMetadata(
                                new S100DatasetDiscoveryMetadataBuilder(this.signatureProvider)
                        ))
                        .toList());
        exchangeCatalogue.getSupportFileDiscoveryMetadata()
                .getS100SupportFileDiscoveryMetadatas()
                .addAll(this.supportFileDiscoveryMetadataProviders.stream()
                        .map(provider -> provider.buildMetadata(
                                new S100SupportFileDiscoveryMetadataBuilder(this.signatureProvider)
                        ))
                        .toList());
        exchangeCatalogue.getCatalogueDiscoveryMetadata()
                .getS100CatalogueDiscoveryMetadatas()
                .addAll(this.catalogueDiscoveryMetadataProviders.stream()
                        .map(provider -> provider.buildMetadata(
                                new S100CatalogueDiscoveryMetadataBuilder(this.signatureProvider)
                        ))
                        .toList());
        // ================================================================== //

        // And finally marshall to the XML output
        return exchangeCatalogue;
    }

    //========================================================================//
    //               Metadata-Builder Function Interfaces                     //
    // ---------------------------------------------------------------------- //
    // For easier use the metadata builders the main exchange set catalogue   //
    // builder provides a set of interfaces that can be used as providers by  //
    // the implementation software to package a whole exchange set in a       //
    // streamlined way.                                                       //
    //========================================================================//
    public interface DatasetDiscoveryMetadataProvider {
        S100DatasetDiscoveryMetadata buildMetadata(S100DatasetDiscoveryMetadataBuilder builder);
    }
    public interface SupportFileDiscoveryMetadataProvider {
        S100SupportFileDiscoveryMetadata buildMetadata(S100SupportFileDiscoveryMetadataBuilder builder);
    }
    public interface CatalogueDiscoveryMetadataProvider {
        S100CatalogueDiscoveryMetadata buildMetadata(S100CatalogueDiscoveryMetadataBuilder builder);
    }
    //========================================================================//
}
