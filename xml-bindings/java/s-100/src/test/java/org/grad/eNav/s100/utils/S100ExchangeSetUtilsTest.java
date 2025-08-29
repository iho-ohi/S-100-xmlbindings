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
import net.opengis.gml._3.*;
import org.grad.eNav.s100.enums.MaintenanceFrequency;
import org.grad.eNav.s100.enums.RoleCode;
import org.grad.eNav.s100.enums.SecurityClassification;
import org.grad.eNav.s100.enums.TelephoneType;
import org.iso.standards.iso._19115.__3.gco._1.CharacterStringPropertyType;
import org.iso.standards.iso._19115.__3.gco._1.CodeListValueType;
import org.iso.standards.iso._19115.__3.gco._1.DecimalPropertyType;
import org.iso.standards.iso._19115.__3.lan._1.PTLocaleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class S100ExchangeSetUtilsTest {

    // Test Variables
    private S100ExchangeCatalogueBuilder s100ExchangeCatalogueBuilder;
    private S100DatasetDiscoveryMetadataBuilder s100DatasetDiscoveryMetadataBuilder;
    private S100ExchangeCatalogue s100ExchangeCatalogue;
    private String s100ExchangeSetXml;
    private Geometry geometry;

    // Fixed Variables
    private String isoType = "ISO 19103:2015";
    private DateTimeFormatter timeFormat = DateTimeFormatter.ISO_OFFSET_TIME;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE;
    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /**
     * Common setup for all the tests.
     */
    @BeforeEach
    void setup() throws IOException, CertificateException, JAXBException {
        // Create a geometry first
        final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        this.geometry = geometryFactory.createPolygon(new Coordinate[]{
                new Coordinate(-10, -10),
                new Coordinate(-11, 10),
                new Coordinate(10, 11),
                new Coordinate(11, -11),
                new Coordinate(-10, -10),
        });

        // Create an exchange set catalogue builder
        this.s100ExchangeCatalogueBuilder = new S100ExchangeCatalogueBuilder((id, algorithm, payload) -> {
            S100SEDigitalSignature s100SEDigitalSignature = new S100SEDigitalSignature();
            s100SEDigitalSignature.setId("sig");
            s100SEDigitalSignature.setCertificateRef("ref");
            s100SEDigitalSignature.setValue("signature".getBytes());
            return s100SEDigitalSignature;
        });

        // Create the medatata file builders
        this.s100DatasetDiscoveryMetadataBuilder = new S100DatasetDiscoveryMetadataBuilder((id, algorithm, payload) -> {
            S100SEDigitalSignature s100SEDigitalSignature = new S100SEDigitalSignature();
            s100SEDigitalSignature.setId("sig");
            s100SEDigitalSignature.setCertificateRef("ref");
            s100SEDigitalSignature.setValue("signature".getBytes());
            return s100SEDigitalSignature;
        });

        //====================================================================//
        //              Load a test Exchange Set Catalogue XML                //
        //====================================================================//
        final InputStream inES = ClassLoader.getSystemResourceAsStream("s100-exchange-set.xml");
        assertNotNull(inES);
        this.s100ExchangeSetXml = new String(inES.readAllBytes(), StandardCharsets.UTF_8);
        //====================================================================//

        //====================================================================//
        //                   Load a test X.509 Certificate                    //
        //====================================================================//
        final InputStream inCert = ClassLoader.getSystemResourceAsStream("test.pem");
        assertNotNull(inCert);
        final String inCertPem = new String(inCert.readAllBytes(), StandardCharsets.UTF_8)
                .replaceAll("-----BEGIN CERTIFICATE-----","")
                .replaceAll("-----END CERTIFICATE-----","")
                .replaceAll("\n","")
                .replaceAll("\r\n","")
                .replaceAll(System.lineSeparator(),"");
        //====================================================================//

        //====================================================================//
        //         The S100 Exchange Catalogue product Specification          //
        //====================================================================//
        final S100ProductSpecification s100ProductSpecification = new S100ProductSpecification();
        s100ProductSpecification.setName("S-125");
        s100ProductSpecification.setProductIdentifier("S-125");
        s100ProductSpecification.setNumber(BigInteger.ONE);
        s100ProductSpecification.setDate(LocalDate.parse("2022-10-22", this.dateFormat));
        s100ProductSpecification.setCompliancyCategory(S100CompliancyCategory.CATEGORY_1);
        //====================================================================//

        // Initialise the Dataset
        this.s100ExchangeCatalogue = this.s100ExchangeCatalogueBuilder
                .setIdentifier("Test Exchange Set")
                .setDateTime(LocalDateTime.parse("2023-01-01T00:00:00Z", this.dateTimeFormat))
                .setDataServerIdentifier("2d7c8116-75a9-4fb8-b1b3-7a698d416b97")
                .setOrganization("GRAD")
                .setElectronicMailAddresses(Collections.singletonList("test@gla-rad.org"))
                .setPhone("+44 1255 245000")
                .setPhoneType(TelephoneType.VOICE)
                .setCity("Harwich")
                .setPostalCode("postalCode")
                .setCountry("UK and Ireland")
                .setLocales(Collections.singletonList(Locale.UK))
                .setAdministrativeArea("England, Wales, Scotland and the whole of Ireland")
                .setDescription("Test Exchange Set Description.")
                .setComment("Test Exchange Set Comment.")
                .setProductSpecification(Collections.singletonList(s100ProductSpecification))
                .setCertificatesByPem(Collections.singletonMap("CRT1", inCertPem))
                .addDatasetMetadata(builder -> builder
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
                        .setIssueTime(LocalTime.parse("00:00:00Z", this.timeFormat))
                        .setProductSpecification(null)
                        .setProducingAgency("producingAgency")
                        .setProducingAgencyRole(RoleCode.ORIGINATOR)
                        .setProducerCode("producerCode")
                        .setEncodingFormat(S100EncodingFormat.GML)
                        .setDataCoverages(this.geometry)
                        .setComment("comment")
                        .setMetadataDateStamp(LocalDate.parse("2023-01-03", this.dateFormat))
                        .setReplacedData(false)
                        .setNavigationPurposes(Collections.singletonList(S100NavigationPurpose.OVERVIEW))
                        .setMaintenanceFrequency(MaintenanceFrequency.CONTINUAL)
                        .setMaintenanceDate(LocalDate.parse("2023-01-04", this.dateFormat))
                        .setMaintenancePeriod(Duration.ofDays(100))
                        .build("dataset".getBytes()))
                .build();
    }

    /**
     * Test that we can successfully create a character string from a simple
     * Java string.
     */
    @Test
    void testCreateCharacterString() {
        final CharacterStringPropertyType cspt = S100ExchangeSetUtils.createCharacterStringPropertyType("test");

        // Assert that the CharacterStringPropertyType is not empty and seems valid
        assertNotNull(cspt);
        assertNotNull(cspt.getCharacterString());
        assertEquals("test", cspt.getCharacterString().getValue());
    }

    /**
     * Test that for a null string the CharacterStringPropertyType generation
     * method will return an empty but valid object.
     */
    @Test
    void testCreateCharacterStringNull() {
        final CharacterStringPropertyType cspt = S100ExchangeSetUtils.createCharacterStringPropertyType(null);

        // Assert that the CharacterStringPropertyType is not empty and seems valid
        assertNotNull(cspt);
        assertNotNull(cspt.getCharacterString());
        assertNull(cspt.getCharacterString().getValue());
    }

    /**
     * Test that we can successfully create a list of character strings from a
     * simple list of Java strings.
     */
    @Test
    void testCreateCharacterStringList() {
        final List<CharacterStringPropertyType> cspts = S100ExchangeSetUtils.createCharacterStringPropertyTypeList(Collections.singletonList("test"));

        // Assert that the CharacterStringPropertyType is not empty and seems valid
        assertNotNull(cspts);
        assertNotNull(cspts.get(0));
        assertEquals(1, cspts.size());
        assertNotNull(cspts.get(0).getCharacterString());
        assertEquals("test", cspts.get(0).getCharacterString().getValue());
    }

    /**
     * The that for a null list, the CharacterStringPropertyType list generation
     * method will return an empty list respectively.
     */
    @Test
    void testCreateCharacterStringListNull() {
        final List<CharacterStringPropertyType> cspts = S100ExchangeSetUtils.createCharacterStringPropertyTypeList(null);

        // Assert that the CharacterStringPropertyType is not empty and seems valid
        assertNotNull(cspts);
        assertEquals(0, cspts.size());
    }

    /**
     * Test that for a valid decimal input, the DecimalPropertyType generation
     * method will return a valid and populated S-100 object.
     */
    @Test
    void testCreateDecimalPropertyType() {
        final DecimalPropertyType decimalPropertyType = S100ExchangeSetUtils.createDecimalPropertyType(BigDecimal.ONE);

        // Assert that the DecimalPropertyType is not empty and seems valid
        assertNotNull(decimalPropertyType);
        assertEquals(BigDecimal.ONE, decimalPropertyType.getDecimal());
    }

    /**
     * Test that for a null decimal input, the DecimalPropertyType generation
     * method will return a valid but empty S-100 object.
     */
    @Test
    void testCreateDecimalPropertyTypeNull() {
        final DecimalPropertyType decimalPropertyType = S100ExchangeSetUtils.createDecimalPropertyType(null);

        // Assert that the DecimalPropertyType is not empty and seems valid
        assertNotNull(decimalPropertyType);
        assertNull(decimalPropertyType.getDecimal());
    }

    /**
     * Test that we can successfully generate the S-100 Code List Value type
     * objects provided that we pass the correct parameters.
     */
    @Test
    void testCreateCodeListValueType() {
        // Create the S-100 Code List Value Type object
        final CodeListValueType codeListValueType = S100ExchangeSetUtils.createCodeListValueType(
                "list",
                "space",
                "code",
                "value"
        );

        // Assess the result
        assertNotNull(codeListValueType);
        assertEquals("list", codeListValueType.getCodeList());
        assertEquals("space", codeListValueType.getCodeSpace());
        assertEquals("code", codeListValueType.getCodeListValue());
        assertEquals("value", codeListValueType.getValue());
    }

    /**
     * Test that provided a valid Java local we can successfully generate an
     * S-100 PTLocaleType object.
     */
    @Test
    void testCreatePTLocaleType() {
        // Generate the S-100 PT Locale Type
        final PTLocaleType ptLocaleType = S100ExchangeSetUtils.createPTLocaleType(Locale.UK);

        // Assess the result
        assertNotNull(ptLocaleType);
        assertNotNull(ptLocaleType.getLanguage());
        assertNotNull(ptLocaleType.getLanguage().getLanguageCode());
        assertEquals(Locale.UK.getDisplayLanguage(), ptLocaleType.getLanguage().getLanguageCode().getValue());
        assertNotNull(ptLocaleType.getCountry());
        assertNotNull(ptLocaleType.getCountry().getCountryCode());
        assertEquals(Locale.UK.getDisplayCountry(), ptLocaleType.getCountry().getCountryCode().getValue());
        assertNotNull(ptLocaleType.getCharacterEncoding());
        assertNotNull(ptLocaleType.getCharacterEncoding().getMDCharacterSetCode());
        assertEquals(StandardCharsets.UTF_8.displayName(), ptLocaleType.getCharacterEncoding().getMDCharacterSetCode().getValue());
    }

    /**
     * Test that provided a null Java Locale this the S-100 Utils function will
     * return a null output.
     */
    @Test
    void testCreatePTLocaleTypeNull() {
        // Generate the S-100 PT Locale Type
        PTLocaleType ptLocaleType = S100ExchangeSetUtils.createPTLocaleType(null);

        assertNull(ptLocaleType);
    }

    /**
     * Test that provided a valid geometry, the S100GeographicBoundingBoxType
     * generation method will generate and populate the respective S-100
     * boundary object correctly.
     * <p/>
     * Note that for this test the geometry is not a perfect square, so that
     * we make sure we can calculate correctly the bounding boxes for generic
     * shapes.
     */
    @Test
    void testCreateS100GeographicBoundingBoxType() {
        // Generate the S-100 bounding box
        S100GeographicBoundingBoxType s100GeographicBoundingBoxType = S100ExchangeSetUtils.createS100GeographicBoundingBoxType(this.geometry);

        // Assert that the bounding box is not empty and seems valid
        assertNotNull(s100GeographicBoundingBoxType);
        assertNotNull(s100GeographicBoundingBoxType.getWestBoundLongitude());
        assertNotNull(s100GeographicBoundingBoxType.getEastBoundLongitude());
        assertNotNull(s100GeographicBoundingBoxType.getSouthBoundLatitude());
        assertNotNull(s100GeographicBoundingBoxType.getNorthBoundLatitude());
        assertNotNull(s100GeographicBoundingBoxType.getWestBoundLongitude().getDecimal());
        assertNotNull(s100GeographicBoundingBoxType.getEastBoundLongitude().getDecimal());
        assertNotNull(s100GeographicBoundingBoxType.getSouthBoundLatitude().getDecimal());
        assertNotNull(s100GeographicBoundingBoxType.getNorthBoundLatitude().getDecimal());
        assertEquals(-11.0, s100GeographicBoundingBoxType.getWestBoundLongitude().getDecimal().doubleValue());
        assertEquals(11.0, s100GeographicBoundingBoxType.getEastBoundLongitude().getDecimal().doubleValue());
        assertEquals(-11.0, s100GeographicBoundingBoxType.getSouthBoundLatitude().getDecimal().doubleValue());
        assertEquals(11.0,s100GeographicBoundingBoxType.getNorthBoundLatitude().getDecimal().doubleValue());
    }

    /**
     * Test that for a null geometry input, the S100GeographicBoundingBoxType
     * generation method will return a simple null output.
     */
    @Test
    void testCreateS100GeographicBoundingBoxTypeNull() {
        assertNull(S100ExchangeSetUtils.createS100GeographicBoundingBoxType(null));
    }

    /**
     * Test that we can successfully generate a geographical data coverage
     * description in S-100 if we provide a valid geometry.
     */
    @Test
    void testCreateS100DataCoverages() {
        // Generate the data coverage
        List<S100DataCoverage> dataCoverage = S100ExchangeSetUtils.createS100DataCoverages(this.geometry);

        // Assert that the data coverage is not empty and seems valid
        assertNotNull(dataCoverage);
        assertEquals(1, dataCoverage.size());
        assertNotNull(dataCoverage.get(0));
        assertNotNull(dataCoverage.get(0).getBoundingPolygon());
        assertNotNull(dataCoverage.get(0).getBoundingPolygon());
        assertNotNull(dataCoverage.get(0).getBoundingPolygon().getPolygons());
        assertEquals(1, dataCoverage.get(0).getBoundingPolygon().getPolygons().size());
        assertNotNull(dataCoverage.get(0).getBoundingPolygon().getPolygons().get(0));
        assertNotNull(dataCoverage.get(0).getBoundingPolygon().getPolygons().get(0).getAbstractGeometry());
        assertNotNull(dataCoverage.get(0).getBoundingPolygon().getPolygons().get(0).getAbstractGeometry().getValue());
        assertTrue(dataCoverage.get(0).getBoundingPolygon().getPolygons().get(0).getAbstractGeometry().getValue() instanceof PolygonType);

        // Now investigate the polygon itself
        PolygonType polygonType = (PolygonType) dataCoverage.get(0).getBoundingPolygon().getPolygons().get(0).getAbstractGeometry().getValue();
        assertNotNull(polygonType.getExterior());
        assertNotNull(polygonType.getExterior().getAbstractRing());
        assertNotNull(polygonType.getExterior().getAbstractRing().getValue());
        assertTrue(polygonType.getExterior().getAbstractRing().getValue() instanceof LinearRingType);

        // Then investigate the linear ring type
        LinearRingType linearRingType = (LinearRingType)polygonType.getExterior().getAbstractRing().getValue();
        assertNotNull(linearRingType.getPosList());

        // Finally investigate all the segments
        for(int i=0; i<linearRingType.getPosList().getValue().length/2; i++){
            assertEquals(geometry.getCoordinates()[i].getX(), linearRingType.getPosList().getValue()[2*i]);
            assertEquals(geometry.getCoordinates()[i].getY(), linearRingType.getPosList().getValue()[2*i+1]);
        }
    }

    /**
     * Test that for a null geometry input, the S100DataCoverage list
     * generation method will return a simple null output.
     */
    @Test
    void testCreateS100DataCoveragesNull() {
        assertNull(S100ExchangeSetUtils.createS100DataCoverages(null));
    }

    /**
     * Test that we can create (marshall) and XML based on an S-125 Dataset type
     * object.
     *
     * @throws JAXBException a JAXB exception thrown during the marshalling operation
     */
    @Test
    void testMarchallS125() throws JAXBException {
        String xml = S100ExchangeSetUtils.marshalS100ExchangeSetCatalogue(this.s100ExchangeCatalogue);

        // Assert the XML is not empty and seems valid
        assertNotNull(xml);

        // The marshalling operation ofter messes up the namespace order so
        // we might as well remove it before we continue with the one-to-one
        // matching
        String s100ExchangeSetXmlWithoutNamespaces = this.s100ExchangeSetXml
                .replaceAll("S100_ExchangeCatalogue .+>","S100_ExchangeCatalogue>")
                .replaceAll("ns\\d+:","");
        String xmlWithoutNamespaces = xml
                .replaceAll("S100_ExchangeCatalogue .+>","S100_ExchangeCatalogue>")
                .replaceAll("ns\\d+:","");
        assertEquals(s100ExchangeSetXmlWithoutNamespaces, xmlWithoutNamespaces);
    }

    /**
     * Test that we can generate (unmarshall) an S-125 POJO based on a valid
     * XML S-125 Dataset.
     *
     * @throws JAXBException a JAXB exception thrown during the unmarshalling operation
     */
    @Test
    void testUnmarshalS125() throws JAXBException {
        // Unmarshall it to a G1128 service instance object
        S100ExchangeCatalogue result = S100ExchangeSetUtils.unmarshallS100ExchangeSetCatalogue(this.s100ExchangeSetXml);

        // Assert all information is correct
        assertNotNull(result);
        assertEquals(this.s100ExchangeCatalogue.getDataServerIdentifier(), result.getDataServerIdentifier());
        assertNotNull( result.getExchangeCatalogueDescription());
        assertNotNull( result.getExchangeCatalogueDescription().getCharacterString());
        assertEquals(this.s100ExchangeCatalogue.getExchangeCatalogueDescription().getCharacterString().getValue(), result.getExchangeCatalogueDescription().getCharacterString().getValue());
        assertNotNull( result.getExchangeCatalogueComment());
        assertNotNull( result.getExchangeCatalogueComment().getCharacterString());
        assertEquals(this.s100ExchangeCatalogue.getExchangeCatalogueComment().getCharacterString().getValue(), result.getExchangeCatalogueComment().getCharacterString().getValue());
        assertNotNull(result.getDatasetDiscoveryMetadata());
        assertNotNull(result.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas());
        assertEquals(1, result.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas().size());

        // Now verify the dataset discovery metadata
        final S100DatasetDiscoveryMetadata metadata = result.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas().get(0);
        assertEquals(this.s100ExchangeCatalogue.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas().get(0).getFileName(), metadata.getFileName());
        assertEquals(this.s100ExchangeCatalogue.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas().get(0).getDatasetID(), metadata.getDatasetID());
        assertNotNull(metadata.getDescription().getCharacterString());
        assertNotNull(metadata.getClassification());
        assertEquals(this.s100ExchangeCatalogue.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas().get(0).getPurpose(), metadata.getPurpose());
        assertTrue(metadata.isNotForNavigation());
        assertEquals(this.s100ExchangeCatalogue.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas().get(0).getEditionNumber(), metadata.getEditionNumber());
        assertEquals(this.s100ExchangeCatalogue.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas().get(0).getUpdateNumber(), metadata.getUpdateNumber());
        assertEquals(this.s100ExchangeCatalogue.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas().get(0).getUpdateApplicationDate(), metadata.getUpdateApplicationDate());
        assertEquals(this.s100ExchangeCatalogue.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas().get(0).getIssueDate(), metadata.getIssueDate());
        assertNull(metadata.getBoundingBox());
        assertNull(metadata.getTemporalExtent());
        assertNotNull(metadata.getProducingAgency());
        assertEquals(this.s100ExchangeCatalogue.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas().get(0).getProducerCode(), metadata.getProducerCode());
        assertEquals(this.s100ExchangeCatalogue.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas().get(0).getMetadataDateStamp(), metadata.getMetadataDateStamp());
        assertEquals(this.s100ExchangeCatalogue.getDatasetDiscoveryMetadata().getS100DatasetDiscoveryMetadatas().get(0).isReplacedData(), metadata.isReplacedData());
    }

    /**
     * Test the translation operations between the certificate and the
     * respective PEM representation performed by the utility.
     *
     * @throws IOException for IO Exceptions
     * @throws CertificateException for issues with the certificate loading
     */
    @Test
    void testCertPemOperations() throws IOException, CertificateException {
        final InputStream in = ClassLoader.getSystemResourceAsStream("test.pem");
        assertNotNull(in);
        final String inString = new String(in.readAllBytes(), StandardCharsets.UTF_8)
                .replaceAll("-----BEGIN CERTIFICATE-----","")
                .replaceAll("-----END CERTIFICATE-----","")
                .replaceAll(System.lineSeparator(),"")
                .replaceAll("\n","")
                .replaceAll("\r\n","");

        // Perform the translations
        X509Certificate certificate = S100ExchangeSetUtils.getCertFromPem(inString);
        byte[] pem = S100ExchangeSetUtils.getPemFromCert(certificate);

        // Make sure the translation operations worked correctly
        assertNotNull(certificate);
        assertNotNull(pem);
    }

    /**
     * Test that we can correctly convert an array of JTS coordinates into a
     * simple Double array.
     */
    @Test
    void testCoordinatesToArray() {
        // Create a test coordinates array
        Coordinate[] coordinates = new Coordinate[] {
                new Coordinate(1, 2, 0),
                new Coordinate(3, 4, 0),
                new Coordinate(5, 6, 0),
        };

        // Get the coordinates into a double array
        Double[] array = S100ExchangeSetUtils.coordinatesToArray(coordinates);

        // Make sure it seems OK
        assertNotNull(array);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
        assertEquals(5, array[4]);
        assertEquals(6, array[5]);
    }

    /**
     * Test that we can correctly convert an array of JTS coordinates into a
     * simple Double List.
     */
    @Test
    void testCoordinatesToList() {
        // Create a test coordinates array
        Coordinate[] coordinates = new Coordinate[] {
                new Coordinate(1, 2, 0),
                new Coordinate(3, 4, 0),
                new Coordinate(5, 6, 0),
        };

        // Get the coordinates into a double array
        List<Double[]> list = S100ExchangeSetUtils.coordinatesToArrayList(coordinates);

        // Make sure it seems OK
        assertNotNull(list);
        assertEquals(1, list.get(0)[0]);
        assertEquals(2, list.get(0)[1]);
        assertEquals(3, list.get(1)[0]);
        assertEquals(4, list.get(1)[1]);
        assertEquals(5, list.get(2)[0]);
        assertEquals(6, list.get(2)[1]);
    }

    /**
     * Test that we can correctly convert an array of JTS coordinates into a
     * GML Position List.
     */
    @Test
    void testCoordinatesToGmlPosList() {
        // Create a test coordinates array
        Coordinate[] coordinates = new Coordinate[] {
                new Coordinate(1, 2, 0),
                new Coordinate(3, 4, 0),
                new Coordinate(5, 6, 0),
        };

        // Get the coordinates into a double array
        PosList posList = S100ExchangeSetUtils.coordinatesToGmlPosList(coordinates);

        // Make sure it seems OK
        assertNotNull(posList);
        assertNotNull(posList.getValue());
        assertEquals(1, posList.getValue()[0]);
        assertEquals(2, posList.getValue()[1]);
        assertEquals(3, posList.getValue()[2]);
        assertEquals(4, posList.getValue()[3]);
        assertEquals(5, posList.getValue()[4]);
        assertEquals(6, posList.getValue()[5]);
    }
}