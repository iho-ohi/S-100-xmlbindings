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
import org.grad.eNav.s100.enums.TelephoneType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class S100ExchangeCatalogueBuilderTest {

    // Test Variables
    private S100ExchangeCatalogueBuilder s100ExchangeCatalogueBuilder;
    private S100DatasetDiscoveryMetadataBuilder s100DatasetDiscoveryMetadataBuilder;

    // Fixed Variables
    private DateTimeFormatter timeFormat = DateTimeFormatter.ISO_OFFSET_TIME;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE;
    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /**
     * Common setup for all the tests.
     */
    @BeforeEach
    void setup() throws IOException {
        this.s100ExchangeCatalogueBuilder = new S100ExchangeCatalogueBuilder((id, algorithm, payload) -> {
            S100SEDigitalSignature s100SEDigitalSignature = new S100SEDigitalSignature();
            s100SEDigitalSignature.setId("sig");
            s100SEDigitalSignature.setCertificateRef("ref");
            s100SEDigitalSignature.setValue("signature".getBytes());
            return s100SEDigitalSignature;
        });
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
     * elements null, but with the certificates maps and metadata lists already
     * initialised.
     */
    @Test
    void testConstructor() {
        assertNotNull(this.s100ExchangeCatalogueBuilder);
        assertNull(this.s100ExchangeCatalogueBuilder.identifier);
        assertNull(this.s100ExchangeCatalogueBuilder.dateTime);
        assertNull(this.s100ExchangeCatalogueBuilder.dataServerIdentifier);
        assertNull(this.s100ExchangeCatalogueBuilder.organization);
        assertNull(this.s100ExchangeCatalogueBuilder.electronicMailAddresses);
        assertNull(this.s100ExchangeCatalogueBuilder.phone);
        assertNull(this.s100ExchangeCatalogueBuilder.phoneType);
        assertNull(this.s100ExchangeCatalogueBuilder.city);
        assertNull(this.s100ExchangeCatalogueBuilder.postalCode);
        assertNull(this.s100ExchangeCatalogueBuilder.country);
        assertNull(this.s100ExchangeCatalogueBuilder.locales);
        assertNull(this.s100ExchangeCatalogueBuilder.administrativeArea);
        assertNull(this.s100ExchangeCatalogueBuilder.description);
        assertNull(this.s100ExchangeCatalogueBuilder.comment);
        assertNull(this.s100ExchangeCatalogueBuilder.productSpecifications);
        assertNotNull(this.s100ExchangeCatalogueBuilder.certificateMap);
    }

    /**
     * Test that if provided the builder arguments will be correctly set to
     * the builder.
     */
    @Test
    void testSetters() throws JAXBException, CertificateEncodingException {
        // Perform the setting operations
        this.s100ExchangeCatalogueBuilder.setIdentifier("identifier")
                .setDateTime(LocalDateTime.parse("2023-01-01T00:00:00Z", this.dateTimeFormat))
                .setDataServerIdentifier("dataServerIdentifier")
                .setOrganization("organisation")
                .setElectronicMailAddresses(Collections.singletonList("test@test.com"))
                .setPhone("+00 0000 000000")
                .setPhoneType(TelephoneType.VOICE)
                .setCity("city")
                .setPostalCode("postalCode")
                .setCountry("country")
                .setLocales(Collections.singletonList(Locale.UK))
                .setAdministrativeArea("adminArea")
                .setDescription("description")
                .setComment("comment")
                .setProductSpecification(Collections.singletonList(new S100ProductSpecification()));

        // Assert that the builder arguments were correctly allocated
        assertNotNull(this.s100ExchangeCatalogueBuilder);
        assertEquals("identifier", this.s100ExchangeCatalogueBuilder.identifier);
        assertEquals("dataServerIdentifier", this.s100ExchangeCatalogueBuilder.dataServerIdentifier);
        assertEquals("organisation", this.s100ExchangeCatalogueBuilder.organization);
        assertNotNull(this.s100ExchangeCatalogueBuilder.electronicMailAddresses);
        assertEquals(1, this.s100ExchangeCatalogueBuilder.electronicMailAddresses.size());
        assertEquals("test@test.com", this.s100ExchangeCatalogueBuilder.electronicMailAddresses.get(0));
        assertEquals("+00 0000 000000", this.s100ExchangeCatalogueBuilder.phone);
        assertEquals(TelephoneType.VOICE, this.s100ExchangeCatalogueBuilder.phoneType);
        assertEquals("city", this.s100ExchangeCatalogueBuilder.city);
        assertEquals("postalCode", this.s100ExchangeCatalogueBuilder.postalCode);
        assertEquals("country", this.s100ExchangeCatalogueBuilder.country);
        assertNotNull(this.s100ExchangeCatalogueBuilder.locales);
        assertEquals(1, this.s100ExchangeCatalogueBuilder.locales.size());
        assertEquals(Locale.UK, this.s100ExchangeCatalogueBuilder.locales.get(0));
        assertEquals("adminArea", this.s100ExchangeCatalogueBuilder.administrativeArea);
        assertEquals("description", this.s100ExchangeCatalogueBuilder.description);
        assertEquals("comment", this.s100ExchangeCatalogueBuilder.comment);
        assertNotNull(this.s100ExchangeCatalogueBuilder.productSpecifications);
        assertEquals(1, this.s100ExchangeCatalogueBuilder.electronicMailAddresses.size());
        assertNotNull(this.s100ExchangeCatalogueBuilder.certificateMap);
    }

    /**
     * Test that the S-100 Exchange Set Catalogue builder can correctly build
     * an exchange set XML if the appropriate parameters have been provided.
     */
    @Test
    void testBuild() throws IOException, CertificateException, JAXBException {
        // Load an X.509 certificate
        final InputStream in = ClassLoader.getSystemResourceAsStream("test.pem");
        assertNotNull(in);
        final String inString = new String(in.readAllBytes(), StandardCharsets.UTF_8)
                .replaceAll("-----BEGIN CERTIFICATE-----","")
                .replaceAll("-----END CERTIFICATE-----","")
                .replaceAll(System.lineSeparator(),"")
                .replaceAll("\n","")
                .replaceAll("\r\n","");
        final X509Certificate certificate = S100ExchangeSetUtils.getCertFromPem(inString);

        // Perform the setting operations
        S100ExchangeCatalogue exchangeCatalogue = this.s100ExchangeCatalogueBuilder
                .setIdentifier("identifier")
                .setDateTime(LocalDateTime.parse("2023-01-01T00:00:00Z", this.dateTimeFormat))
                .setDataServerIdentifier("dataServerIdentifier")
                .setOrganization("organisation")
                .setElectronicMailAddresses(Collections.singletonList("test@test.com"))
                .setPhone("+00 0000 000000")
                .setPhoneType(TelephoneType.VOICE)
                .setCity("city")
                .setPostalCode("postalCode")
                .setCountry("country")
                .setLocales(Collections.singletonList(Locale.UK))
                .setAdministrativeArea("adminArea")
                .setDescription("description")
                .setComment("comment")
                .setProductSpecification(Collections.singletonList(new S100ProductSpecification()))
                .setCertificates(Collections.singletonMap("CRT1", certificate))
                .addDatasetMetadata(builder -> new S100DatasetDiscoveryMetadata())
                .addSupportFileMetadata(builder -> new S100SupportFileDiscoveryMetadata())
                .addCatalogueMetadata(builder -> new S100CatalogueDiscoveryMetadata())
                .build();

        // Assert that the building took place correctly
        assertNotNull(exchangeCatalogue);
        assertEquals("identifier", exchangeCatalogue.getIdentifier().getIdentifier());
        assertEquals(LocalDateTime.parse("2023-01-01T00:00:00Z", this.dateTimeFormat), exchangeCatalogue.getIdentifier().getDateTime());
        assertEquals("dataServerIdentifier", exchangeCatalogue.getDataServerIdentifier());
        assertNotNull(exchangeCatalogue.getContact().getOrganization());
        assertNotNull(exchangeCatalogue.getContact().getOrganization().getCharacterString());
        assertEquals("organisation", exchangeCatalogue.getContact().getOrganization().getCharacterString().getValue());
        assertNotNull(exchangeCatalogue.getContact().getAddress());
        assertNotNull(exchangeCatalogue.getContact().getAddress().getElectronicMailAddresses());
        assertEquals(1, exchangeCatalogue.getContact().getAddress().getElectronicMailAddresses().size());
        assertNotNull(exchangeCatalogue.getContact().getAddress().getElectronicMailAddresses().get(0));
        assertNotNull(exchangeCatalogue.getContact().getAddress().getElectronicMailAddresses().get(0).getCharacterString());
        assertEquals("test@test.com", exchangeCatalogue.getContact().getAddress().getElectronicMailAddresses().get(0).getCharacterString().getValue());
        assertNotNull(exchangeCatalogue.getContact().getPhone());
        assertNotNull(exchangeCatalogue.getContact().getPhone().getNumber());
        assertNotNull(exchangeCatalogue.getContact().getPhone().getNumber().getCharacterString());
        assertEquals("+00 0000 000000", exchangeCatalogue.getContact().getPhone().getNumber().getCharacterString().getValue());
        assertNotNull(exchangeCatalogue.getContact().getAddress().getCity());
        assertNotNull(exchangeCatalogue.getContact().getAddress().getCity().getCharacterString());
        assertEquals("city", exchangeCatalogue.getContact().getAddress().getCity().getCharacterString().getValue());
        assertNotNull(exchangeCatalogue.getContact().getAddress().getPostalCode());
        assertNotNull(exchangeCatalogue.getContact().getAddress().getPostalCode().getCharacterString());
        assertEquals("postalCode", exchangeCatalogue.getContact().getAddress().getPostalCode().getCharacterString().getValue());
        assertNotNull(exchangeCatalogue.getContact().getAddress().getAdministrativeArea());
        assertNotNull(exchangeCatalogue.getContact().getAddress().getAdministrativeArea().getCharacterString());
        assertEquals("adminArea", exchangeCatalogue.getContact().getAddress().getAdministrativeArea().getCharacterString().getValue());

        // Assess the locales
        assertNotNull(exchangeCatalogue.getDefaultLocale());
        assertNotNull(exchangeCatalogue.getDefaultLocale().getPTLocale().getValue());
        assertNotNull(exchangeCatalogue.getDefaultLocale().getPTLocale().getValue().getLanguage());
        assertNotNull(exchangeCatalogue.getDefaultLocale().getPTLocale().getValue().getLanguage().getLanguageCode());
        assertEquals(Locale.UK.getDisplayLanguage(), exchangeCatalogue.getDefaultLocale().getPTLocale().getValue().getLanguage().getLanguageCode().getValue());
        assertNotNull(exchangeCatalogue.getDefaultLocale().getPTLocale().getValue().getCountry());
        assertNotNull(exchangeCatalogue.getDefaultLocale().getPTLocale().getValue().getCountry().getCountryCode());
        assertEquals(Locale.UK.getDisplayCountry(), exchangeCatalogue.getDefaultLocale().getPTLocale().getValue().getCountry().getCountryCode().getValue());
        assertNotNull(exchangeCatalogue.getDefaultLocale().getPTLocale().getValue().getCharacterEncoding());
        assertNotNull(exchangeCatalogue.getDefaultLocale().getPTLocale().getValue().getCharacterEncoding().getMDCharacterSetCode());
        assertEquals(StandardCharsets.UTF_8.displayName(), exchangeCatalogue.getDefaultLocale().getPTLocale().getValue().getCharacterEncoding().getMDCharacterSetCode().getValue());
        assertNotNull(exchangeCatalogue.getOtherLocales());
        assertTrue(exchangeCatalogue.getOtherLocales().isEmpty());

        // Assess the description and comment
        assertNotNull(exchangeCatalogue.getExchangeCatalogueDescription());
        assertNotNull(exchangeCatalogue.getExchangeCatalogueDescription().getCharacterString());
        assertEquals("description", exchangeCatalogue.getExchangeCatalogueDescription().getCharacterString().getValue());
        assertNotNull(exchangeCatalogue.getExchangeCatalogueComment());
        assertNotNull(exchangeCatalogue.getExchangeCatalogueComment().getCharacterString());
        assertEquals("comment", exchangeCatalogue.getExchangeCatalogueComment().getCharacterString().getValue());

        // Assess the certificates
        assertNotNull(exchangeCatalogue.getProductSpecifications());
        assertEquals(1, exchangeCatalogue.getProductSpecifications().size());
        assertNotNull(exchangeCatalogue.getCertificates());
        assertEquals(1, exchangeCatalogue.getCertificates().size());
        assertNotNull(exchangeCatalogue.getCertificates().get(0));
        assertNotNull(exchangeCatalogue.getCertificates().get(0).getCertificates());
        assertEquals(1, exchangeCatalogue.getCertificates().get(0).getCertificates().size());
        assertNotNull(exchangeCatalogue.getCertificates().get(0).getCertificates().get(0));
        assertEquals("CRT1", exchangeCatalogue.getCertificates().get(0).getCertificates().get(0).getId());
        assertNotNull(exchangeCatalogue.getCertificates().get(0).getCertificates().get(0).getValue());

        // Assess the file metadata
        assertNotNull(exchangeCatalogue.getDatasetDiscoveryMetadata());
        assertNotNull(exchangeCatalogue.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas());
        assertEquals(1, exchangeCatalogue.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas().size());
        assertNotNull(exchangeCatalogue.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas().get(0));
        assertNotNull(exchangeCatalogue.getSupportFileDiscoveryMetadata());
        assertNotNull(exchangeCatalogue.getSupportFileDiscoveryMetadata().getS100SupportFileDiscoveryMetadatas());
        assertEquals(1, exchangeCatalogue.getSupportFileDiscoveryMetadata().getS100SupportFileDiscoveryMetadatas().size());
        assertNotNull(exchangeCatalogue.getSupportFileDiscoveryMetadata().getS100SupportFileDiscoveryMetadatas().get(0));
        assertNotNull(exchangeCatalogue.getCatalogueDiscoveryMetadata());
        assertNotNull(exchangeCatalogue.getCatalogueDiscoveryMetadata().getS100CatalogueDiscoveryMetadatas());
        assertEquals(1, exchangeCatalogue.getCatalogueDiscoveryMetadata().getS100CatalogueDiscoveryMetadatas().size());
        assertNotNull(exchangeCatalogue.getCatalogueDiscoveryMetadata().getS100CatalogueDiscoveryMetadatas().get(0));
    }


}