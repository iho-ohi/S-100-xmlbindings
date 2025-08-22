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

package org.grad.eNav.s125.utils;

import _int.iho.s_125.s_100.gml.base._5_2.*;
import _int.iho.s_125.s_100.gml.base._5_2.impl.DataSetIdentificationTypeImpl;
import _int.iho.s_125.s_100.gml.base._5_2.impl.PointPropertyImpl;
import _int.iho.s_125.s_100.gml.base._5_2.impl.PointTypeImpl;
import _int.iho.s_125.s_100.gml.profiles._5_2.AbstractGMLType;
import _int.iho.s_125.s_100.gml.profiles._5_2.BoundingShapeType;
import _int.iho.s_125.s_100.gml.profiles._5_2.EnvelopeType;
import _int.iho.s_125.s_100.gml.profiles._5_2.Pos;
import _int.iho.s_125.s_100.gml.profiles._5_2.impl.BoundingShapeTypeImpl;
import _int.iho.s_125.s_100.gml.profiles._5_2.impl.EnvelopeTypeImpl;
import _int.iho.s_125.s_100.gml.profiles._5_2.impl.PosImpl;
import _int.iho.s_125.s_100.gml.profiles._5_2.impl.ReferenceTypeImpl;
import _int.iho.s_125.gml.cs0._1.S100TruncatedDate;
import _int.iho.s_125.gml.cs0._1.*;
import _int.iho.s_125.gml.cs0._1.impl.*;
import jakarta.xml.bind.JAXBException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class S125UtilsTest {

    // Test Variables
    private Dataset dataset;
    private String datasetXml;
    private VirtualAISAidToNavigation vaton;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE;

    /**
     * Common setup for all the tests.
     */
    @BeforeEach
    void setup() throws IOException {
        final InputStream in = ClassLoader.getSystemResourceAsStream("s125-msg.xml");
        assert in != null;
        this.datasetXml = IOUtils.toString(in, StandardCharsets.UTF_8);

        // Initialise the dataset
        this.dataset = new DatasetImpl();
        this.dataset.setId("CorkHoleTestDataset");

        //====================================================================//
        //                       BOUNDED BY SECTION                           //
        //====================================================================//
        this.dataset.setBoundedBy(this.generateBoundingShape(new double[]{51.8916667, 1.4233333}));

        //====================================================================//
        //                  DATASET IDENTIFICATION SECTION                    //
        //====================================================================//
        final DataSetIdentificationType dataSetIdentificationType = new DataSetIdentificationTypeImpl();
        dataSetIdentificationType.setEncodingSpecification("S-100 Part 10b");
        dataSetIdentificationType.setEncodingSpecificationEdition("1.0");
        dataSetIdentificationType.setProductIdentifier("S-125");
        dataSetIdentificationType.setProductEdition("0.0.1");
        dataSetIdentificationType.setApplicationProfile("test");

        dataSetIdentificationType.setDatasetFileIdentifier("junit");
        dataSetIdentificationType.setDatasetTitle("S-125 Cork Hole Test Dataset");
        dataSetIdentificationType.setDatasetReferenceDate(LocalDate.parse("2001-01-01", dateFormat));
        dataSetIdentificationType.setDatasetLanguage(Locale.UK.getISO3Language());
        dataSetIdentificationType.setDatasetAbstract("Test dataset for unit testing");
        dataSetIdentificationType.getDatasetTopicCategories().add(MDTopicCategoryCode.OCEANS);
        dataSetIdentificationType.setDatasetPurpose(DatasetPurposeType.BASE);
        dataSetIdentificationType.setUpdateNumber(BigInteger.ZERO);
        this.dataset.setDatasetIdentificationInformation(dataSetIdentificationType);

        //====================================================================//
        //                      DATASET MEMBERS SECTION                       //
        //====================================================================//
        this.vaton = new VirtualAISAidToNavigationImpl();
        vaton.setId("ID001");
        vaton.setBoundedBy(this.generateBoundingShape(new double[]{51.8916667, 1.4233333}));
        FeatureNameType featureNameType1 = new FeatureNameTypeImpl();
        featureNameType1.setName("Test AtoN for Cork Hole");
        featureNameType1.setLanguage(Locale.UK.getISO3Language());
        featureNameType1.setDisplayName(Boolean.TRUE);
        FeatureNameType featureNameType2 = new FeatureNameTypeImpl();
        featureNameType2.setName("Test AtoN pour Cork Hole");
        featureNameType2.setLanguage(Locale.FRENCH.getISO3Language());
        featureNameType2.setDisplayName(Boolean.FALSE);
        vaton.getFeatureNames().add(featureNameType1);
        vaton.getFeatureNames().add(featureNameType2);
        vaton.setIDCode("urn:mrn:grad:aton:test:corkhole");
        vaton.setEstimatedRangeOfTransmission(20.0);
        vaton.setMMSICode("992359598");
        final S100TruncatedDate s100TruncatedDateStart = new S100TruncatedDateImpl();
        s100TruncatedDateStart.setDate(LocalDate.parse("2001-01-01", dateFormat));
        final S100TruncatedDate s100TruncatedDateStop = new S100TruncatedDateImpl();
        s100TruncatedDateStop.setDate(LocalDate.parse("2099-01-01", dateFormat));
        final FixedDateRangeTypeImpl fixedDateRange = new FixedDateRangeTypeImpl();
        fixedDateRange.setDateStart(s100TruncatedDateStart);
        fixedDateRange.setDateEnd(s100TruncatedDateStop);
        vaton.setFixedDateRange(fixedDateRange);
        vaton.getStatuses().add(StatusType.CONFIRMED);
        vaton.getSeasonalActionRequireds().add("none");
        vaton.setVirtualAISAidToNavigationType(VirtualAISAidToNavigationTypeType.SPECIAL_PURPOSE);

        // Set the Geometry
        final VirtualAISAidToNavigation.Geometry geometry = new VirtualAISAidToNavigationImpl.GeometryImpl();
        final PointProperty pointProperty = new PointPropertyImpl();
        final PointType pointType = new PointTypeImpl();
        final Pos pos = new PosImpl();
        pos.setSrsName("EPSG:4326");
        pos.setSrsDimension(BigInteger.ONE);
        pos.setValue(new Double[]{51.891666666, 1.4233333333});
        pointType.setId("AtoNPoint1");
        pointType.setSrsName("EPSG:4326");
        pointType.setSrsDimension(BigInteger.ONE);
        pointType.setPos(pos);
        pointProperty.setPoint(pointType);
        geometry.setPointProperty(pointProperty);
        vaton.getGeometries().add(geometry);

        // Set the AtoN Status
        AtonStatusInformation atonStatusInformation = new AtonStatusInformationImpl();
        atonStatusInformation.setId("ID002");
        atonStatusInformation.setChangeTypes(ChangeTypesType.ADVANCED_NOTICE_OF_CHANGES);
        ChangeDetailsType changeDetailsType = new ChangeDetailsTypeImpl();
        changeDetailsType.setElectronicAtonChange(ElectronicAtonChangeType.AIS_TRANSMITTER_OPERATING_PROPERLY);
        atonStatusInformation.setChangeDetails(changeDetailsType);
        ReferenceTypeImpl atonStatusRef = new ReferenceTypeImpl();
        atonStatusRef.setHref(this.vaton.getId());
        atonStatusRef.setRole("association");
        atonStatusRef.setArcrole("urn:IALA:S125:roles:association");
        vaton.setStatuspart(atonStatusRef);

        // Now package everything back to the dataset
        S125Utils.addDatasetMembers(dataset, Collections.singletonList(vaton));
        S125Utils.addDatasetMembers(dataset, Collections.singletonList(atonStatusInformation));
    }

    /**
     * A helper function to build the bounding boxes required for the S-125
     * datasets.
     *
     * @param bbox      the bounding box double array...
     * @return the bounding box shape
     */
    private BoundingShapeType generateBoundingShape(double[] bbox) {
        Pos lowerCorner = new PosImpl();
        lowerCorner.setValue(new Double[]{bbox[0], bbox[1]});
        Pos upperCorner = new PosImpl();
        upperCorner.setValue(new Double[]{bbox[bbox.length - 2], bbox[bbox.length - 1]});

        // And create the bounding by envelope
        BoundingShapeType boundingShapeType = new BoundingShapeTypeImpl();
        EnvelopeType envelopeType = new EnvelopeTypeImpl();
        envelopeType.setSrsName("EPSG:4326");
        envelopeType.setSrsDimension(BigInteger.ONE);
        envelopeType.setLowerCorner(lowerCorner);
        envelopeType.setUpperCorner(upperCorner);
        boundingShapeType.setEnvelope(envelopeType);

        // Finally, return the result
        return boundingShapeType;
    }

    /**
     * Test that we can create (marshall) and XML based on an S-125 Dataset type
     * object.
     *
     * @throws JAXBException a JAXB exception thrown during the marshalling operation
     */
    @Test
    void testMarchallS125() throws JAXBException {
        String xml = S125Utils.marshalS125(this.dataset);
        assertNotNull(xml);
        assertEquals(this.datasetXml, xml);
    }

    /**
     * Test that we can create (marshall) and XML based on an S-125 Dataset type
     * object.
     *
     * @throws JAXBException a JAXB exception thrown during the marshalling operation
     */
    @Test
    void testMarchallS125WithFormat() throws JAXBException {
        String xml = S125Utils.marshalS125(this.dataset, Boolean.TRUE);
        assertNotNull(xml);
        assertEquals(this.datasetXml, xml);
    }

    /**
     * Test that we can generate (unmarshall) an S-125 POJO based on a valid
     * XML S-125 dataset.
     *
     * @throws JAXBException a JAXB exception thrown during the unmarshalling operation
     */
    @Test
    void testUnmarshalS125() throws JAXBException {
        // Unmarshall it to a G1128 service instance object
        Dataset result = S125Utils.unmarshallS125(this.datasetXml);

        // Assert all information is correct
        assertNotNull(result);
        assertEquals(this.dataset.getId(), result.getId());

        // Assert the dataset type envelop is correct
        EnvelopeType datasetEnvelope = this.dataset.getBoundedBy().getEnvelope();
        EnvelopeType resultEnvelope = result.getBoundedBy().getEnvelope();
        assertEquals(datasetEnvelope.getSrsName(), resultEnvelope.getSrsName());
        assertEquals(datasetEnvelope.getLowerCorner().getValue().length, resultEnvelope.getLowerCorner().getValue().length);
        assertTrue(Arrays.asList(datasetEnvelope.getLowerCorner().getValue()).containsAll(Arrays.asList(resultEnvelope.getLowerCorner().getValue())));
        assertEquals(datasetEnvelope.getUpperCorner().getValue().length, resultEnvelope.getUpperCorner().getValue().length);
        assertTrue(Arrays.asList(datasetEnvelope.getUpperCorner().getValue()).containsAll(Arrays.asList(resultEnvelope.getUpperCorner().getValue())));

        // Assert that the dataset identification information is correct
        DataSetIdentificationType datasetIdentification = this.dataset.getDatasetIdentificationInformation();
        DataSetIdentificationType resultIdentification = result.getDatasetIdentificationInformation();
        assertEquals(datasetIdentification.getEncodingSpecification(), resultIdentification.getEncodingSpecification());
        assertEquals(datasetIdentification.getEncodingSpecificationEdition(), resultIdentification.getEncodingSpecificationEdition());
        assertEquals(datasetIdentification.getProductIdentifier(), resultIdentification.getProductIdentifier());
        assertEquals(datasetIdentification.getProductEdition(), resultIdentification.getProductEdition());
        assertEquals(datasetIdentification.getDatasetFileIdentifier(), resultIdentification.getDatasetFileIdentifier());
        assertEquals(datasetIdentification.getEncodingSpecification(), resultIdentification.getEncodingSpecification());
        assertEquals(datasetIdentification.getDatasetReferenceDate(), resultIdentification.getDatasetReferenceDate());
        assertEquals(datasetIdentification.getDatasetLanguage(), resultIdentification.getDatasetLanguage());
        assertEquals(datasetIdentification.getDatasetAbstract(), resultIdentification.getDatasetAbstract());
        assertNotNull(resultIdentification.getDatasetTopicCategories());
        assertEquals(datasetIdentification.getDatasetTopicCategories().size(), resultIdentification.getDatasetTopicCategories().size());
        assertEquals(datasetIdentification.getDatasetTopicCategories().getFirst(), resultIdentification.getDatasetTopicCategories().getFirst());
        assertEquals(datasetIdentification.getDatasetPurpose(), resultIdentification.getDatasetPurpose());
        assertEquals(datasetIdentification.getUpdateNumber(), resultIdentification.getUpdateNumber());

        // Assert the S-125 AidsToNavigation feature information is populated
        assertNotNull(this.dataset.getMembers());
        assertEquals(1, this.dataset.getMembers().getVirtualAISAidToNavigation().size());
        VirtualAISAidToNavigation datasetMember = (VirtualAISAidToNavigation) this.dataset.getMembers().getVirtualAISAidToNavigation().get(0);
        VirtualAISAidToNavigation resultMember =(VirtualAISAidToNavigation) result.getMembers().getVirtualAISAidToNavigation().getFirst();

        // Assert the S-125 AidsToNavigation envelop is correct
        EnvelopeType datasetMemberEnvelope = datasetMember.getBoundedBy().getEnvelope();
        EnvelopeType resultMemberEnvelope = resultMember.getBoundedBy().getEnvelope();
        assertEquals(datasetMemberEnvelope.getSrsName(), resultMemberEnvelope.getSrsName());
        assertEquals(datasetMemberEnvelope.getLowerCorner().getValue().length, resultMemberEnvelope.getLowerCorner().getValue().length);
        assertTrue(Arrays.asList(datasetMemberEnvelope.getLowerCorner().getValue()).containsAll(Arrays.asList(resultMemberEnvelope.getLowerCorner().getValue())));
        assertEquals(datasetMemberEnvelope.getUpperCorner().getValue().length, resultMemberEnvelope.getUpperCorner().getValue().length);
        assertTrue(Arrays.asList(datasetMemberEnvelope.getUpperCorner().getValue()).containsAll(Arrays.asList(resultMemberEnvelope.getUpperCorner().getValue())));

        // Assert the S-125 AidsToNavigation feature information is correct
        assertEquals(datasetMember.getMMSICode(), resultMember.getMMSICode());
        assertEquals(datasetMember.getIDCode(), resultMember.getIDCode());
        assertNotNull(resultMember.getFixedDateRange());
        assertNotNull(resultMember.getFixedDateRange().getDateStart());
        assertNotNull(resultMember.getFixedDateRange().getDateStart().getDate());
        assertEquals(datasetMember.getFixedDateRange().getDateStart().getDate(), resultMember.getFixedDateRange().getDateStart().getDate());
        assertNotNull(resultMember.getFixedDateRange().getDateEnd());
        assertNotNull(resultMember.getFixedDateRange().getDateEnd().getDate());
        assertEquals(datasetMember.getFixedDateRange().getDateStart().getDate(), resultMember.getFixedDateRange().getDateStart().getDate());
        assertNotNull(resultMember.getFeatureNames());
        assertEquals(datasetMember.getFeatureNames().size(), resultMember.getFeatureNames().size());
        assertEquals(datasetMember.getFeatureNames().getFirst().getName(), resultMember.getFeatureNames().getFirst().getName());
        assertEquals(datasetMember.getFeatureNames().getFirst().getLanguage(), resultMember.getFeatureNames().getFirst().getLanguage());
        assertEquals(datasetMember.getFeatureNames().getFirst().isDisplayName(), resultMember.getFeatureNames().getFirst().isDisplayName());
        assertEquals(datasetMember.getEstimatedRangeOfTransmission(), resultMember.getEstimatedRangeOfTransmission());
        assertEquals(datasetMember.getMMSICode(), resultMember.getMMSICode());
        assertNotNull(resultMember.getStatuses());
        assertEquals(datasetMember.getStatuses().size(), resultMember.getStatuses().size());
        assertEquals(datasetMember.getStatuses().getFirst(), resultMember.getStatuses().getFirst());
        assertEquals(datasetMember.getVirtualAISAidToNavigationType(), resultMember.getVirtualAISAidToNavigationType());
    }

    /**
     * Test that we can read directly the members of an S-125 dataset XMl
     * representation.
     *
     * @throws JAXBException a JAXB exception thrown during the unmarshalling operation
     */
    @Test
    void testGetS125Members() throws JAXBException {
        // Unmarshall it to an S-125 dataset object
        List<? extends AbstractGMLType> members = S125Utils.getDatasetMembers(this.datasetXml);

        // Assert that we read all the included members
        assertNotNull(members);
        assertEquals(2, members.size());

        // Get the dataset feature member and evaluate
        final AbstractGMLType member = members
                .stream()
                .filter(AbstractFeatureType.class::isInstance)
                .map(AbstractFeatureType.class::cast)
                .toList()
                .getFirst();
        assertNotNull(member);
        assertTrue(member instanceof VirtualAISAidToNavigation);
    }

    /**
     * This test checks that we can retrieve the members from an existing
     * dataset object correctly.
     */
    @Test
    void testGetDatasetMembers() {
        // Get the members from the test dataset
        List<? extends AbstractGMLType> members = S125Utils.getDatasetMembers(this.dataset);

        // Assert that we read all the included members
        assertNotNull(members);
        assertEquals(2, members.size());

        // Get the dataset member and evaluate
        // Get the dataset feature member and evaluate
        final AbstractGMLType member = members
                .stream()
                .filter(AbstractFeatureType.class::isInstance)
                .map(AbstractFeatureType.class::cast)
                .toList()
                .getFirst();
        assertNotNull(member);
        assertTrue(member instanceof VirtualAISAidToNavigation);
    }

    /**
     * This test checks that for empty inputs, the member extraction operation
     * will still work, returning an empty list
     */
    @Test
    void testGetDatasetMembersEmpty() {
        assertTrue(S125Utils.getDatasetMembers((Dataset)null).isEmpty());
        assertTrue(S125Utils.getDatasetMembers(new DatasetImpl()).isEmpty());
    }

    /**
     * This test checks that the S125Utils addDatasetMembers function to easily
     * add a member entry to the correct dataset list i.e. in the Virtual
     * AIS AtoNs.
     */
    @Test
    void testAddDatasetMembers()  {
        // Create a test dataset
        Dataset emptyDataset = new DatasetImpl();

        // Add some entries
        S125Utils.addDatasetMembers(emptyDataset, Collections.singleton(this.vaton));

        // And make sure they can be retrieved
        assertNotNull(emptyDataset);
        assertNotNull(emptyDataset.getMembers());
        assertNotNull(emptyDataset.getMembers().getVirtualAISAidToNavigation());
        assertEquals(1, emptyDataset.getMembers().getVirtualAISAidToNavigation().size());
        assertNotNull(emptyDataset.getMembers().getVirtualAISAidToNavigation().getFirst());
    }

    /**
     * Test that for null inputs, the S125Utils addDatasetMembers function
     * will not throw any exceptions.
     */
    @Test
    void testAddDatasetMembersEmpty()  {
        S125Utils.addDatasetMembers(null, Collections.emptyList());
        S125Utils.addDatasetMembers(this.dataset, null);
    }

    /**
     * Test that if invalid or null inputs are provided, the generation method
     * for the S-125 Aids to Navigation geometries list will return an empty
     * list.
     */
    @Test
    void testGenerateS125AidsToNavigationTypeGeometriesListEmpty() {
        // Test for empty inputs
        assertTrue(S125Utils.generateS125AidsToNavigationTypeGeometriesList(null, null).isEmpty());
        assertTrue(S125Utils.generateS125AidsToNavigationTypeGeometriesList(VirtualAISAidToNavigation.class, null).isEmpty());
        assertTrue(S125Utils.generateS125AidsToNavigationTypeGeometriesList(null, Collections.emptyList()).isEmpty());
    }

    /**
     * Test that based on a provided list of S100SpatialAttributeType objects
     * and given a specific S-125 Aids to Navigation feature, we can generate
     * the geometry object required for it correctly.
     */
    @Test
    void testGenerateS125AidsToNavigationTypeGeometriesList() {
        // Get a list of spatial attribute types
        final List<S100SpatialAttributeType> values = this.vaton.getGeometries().stream()
                .map(VirtualAISAidToNavigation.Geometry::getPointProperty)
                .collect(Collectors.toList());

        // Test for a valid input
        List<?> atonGeometriesList = S125Utils.generateS125AidsToNavigationTypeGeometriesList(VirtualAISAidToNavigationImpl.class, values);
        assertNotNull(atonGeometriesList);
        assertEquals(values.size(), atonGeometriesList.size());
        for(int i=0; i<atonGeometriesList.size(); i++) {
            assertTrue(atonGeometriesList.get(i) instanceof VirtualAISAidToNavigation.Geometry);
            assertEquals(values.get(i), ((VirtualAISAidToNavigation.Geometry)atonGeometriesList.get(i)).getPointProperty());
        }
    }

    /**
     * Test that based on a provided list of S100SpatialAttributeType objects
     * and given a specific S-125 Aids to Navigation feature, we can generate
     * the geometry object required for it correctly. In this case however, the
     * provided class does not define a custom Geometry but its parent does.
     */
    @Test
    void testGenerateS125AidsToNavigationTypeGeometriesListFromParent() {
        // Get a list of spatial attribute types
        final List<S100SpatialAttributeType> values = this.vaton.getGeometries().stream()
                .map(VirtualAISAidToNavigation.Geometry::getPointProperty)
                .collect(Collectors.toList());

        // Test for a valid input that extends a parent with a geometry
        List<?> atonGeometriesList = S125Utils.generateS125AidsToNavigationTypeGeometriesList(LighthouseImpl.class, values);
        assertNotNull(atonGeometriesList);
        assertEquals(values.size(), atonGeometriesList.size());
        for(int i=0; i<atonGeometriesList.size(); i++) {
            assertTrue(atonGeometriesList.get(i) instanceof LandmarkTypeImpl.Geometry);
            assertEquals(values.get(i), ((LandmarkTypeImpl.Geometry)atonGeometriesList.get(i)).getPointProperty());
        }
    }

    /**
     * Test that we can correctly retrieve the geometry from an S-125 Aids to
     * Navigation feature.
     *
     * @throws JAXBException a JAXB exception thrown during the unmarshalling operation
     */
    @Test
    void testGetS125AidsToNavigationTypeGeometriesList() throws JAXBException {
        // Unmarshall it to a G1128 service instance object
        Dataset result = S125Utils.unmarshallS125(this.datasetXml);

        // Assert the S-125 AidsToNavigation feature information is populated
        assertNotNull(result.getMembers());
        assertEquals(1, result.getMembers().getVirtualAISAidToNavigation().size());
        VirtualAISAidToNavigation resultMember = (VirtualAISAidToNavigation) result.getMembers().getVirtualAISAidToNavigation().getFirst();

        // Extract the geometry
        List<S100SpatialAttributeType> memberGeometries = S125Utils.getS125AidsToNavigationTypeGeometriesList(resultMember);
        List<PointProperty> pointProperties = memberGeometries.stream().filter(PointProperty.class::isInstance).map(PointProperty.class::cast).collect(Collectors.toList());
        List<CurveProperty> curveProperties = memberGeometries.stream().filter(CurveProperty.class::isInstance).map(CurveProperty.class::cast).collect(Collectors.toList());
        List<SurfaceProperty> surfaceProperties = memberGeometries.stream().filter(SurfaceProperty.class::isInstance).map(SurfaceProperty.class::cast).collect(Collectors.toList());

        // Assert that it looks correct
        assertNotNull(pointProperties);
        assertNotNull(curveProperties);
        assertNotNull(surfaceProperties);
        assertFalse(pointProperties.isEmpty());
        assertTrue(curveProperties.isEmpty());
        assertTrue(surfaceProperties.isEmpty());

        assertEquals(1, pointProperties.size());
        assertNotNull(pointProperties.getFirst());
        assertNotNull(pointProperties.getFirst().getPoint());
        assertNotNull(pointProperties.getFirst().getPoint().getPos());
        assertNotNull(pointProperties.getFirst().getPoint().getPos().getValue());

        // Get the geometry position list
        Double[] resultPosList = pointProperties.getFirst().getPoint().getPos().getValue();
        assertNotNull(resultPosList);
        assertEquals(2, resultPosList.length);
        assertEquals(51.8916667, resultPosList[0]);
        assertEquals(1.4233333, resultPosList[1]);
    }

    /**
     * Assert that we can correctly handle specific feature types of Aids to
     * Navigation automatically, without checking every type.
     */
    @Test
    public void testHandlePerS125AidsToNavigationType() {
        final AtomicInteger runs = new AtomicInteger(0);
        this.dataset.getMembers()
                .getVirtualAISAidToNavigation()
                .stream()
                .filter(VirtualAISAidToNavigation.class::isInstance)
                .map(VirtualAISAidToNavigation.class::cast)
                .forEach(m -> S125Utils.handlePerS125AidsToNavigationType(m, VirtualAISAidToNavigation.class, a -> {
                    assertEquals(String.format("ID00%d", runs.incrementAndGet()), a.getId());
                    return null;
                }));

        // Assert that we had a correct number of runs
        assertEquals(1, runs.get());
    }

    /**
     * Test that we can correctly translate XML S100Truncated objects into
     * Java LocalDate objects.
     */
    @Test
    public void testS100TruncatedDateToLocalDate() throws DatatypeConfigurationException {
        // Get a time reference
        final LocalDate now = LocalDate.now();
        final GregorianCalendar nowCal = GregorianCalendar.from(now.atStartOfDay(ZoneId.systemDefault()));

        // Then create the objects for the test
        final S100TruncatedDate s100TruncatedDateNull = null;
        final S100TruncatedDate s100TruncatedDateEmpty = new S100TruncatedDateImpl();
        final S100TruncatedDate s100TruncatedDateDate = new S100TruncatedDateImpl();
        s100TruncatedDateDate.setDate(now);
        final S100TruncatedDate s100TruncatedDateDayMonthTime = new S100TruncatedDateImpl();
        s100TruncatedDateDayMonthTime.setGDay(DatatypeFactory.newInstance().newXMLGregorianCalendar(nowCal));
        s100TruncatedDateDayMonthTime.setGMonth(DatatypeFactory.newInstance().newXMLGregorianCalendar(nowCal));
        s100TruncatedDateDayMonthTime.setGYear(DatatypeFactory.newInstance().newXMLGregorianCalendar(nowCal));

        // Now make the assertions
        assertNull(S125Utils.s100TruncatedDateToLocalDate(s100TruncatedDateNull));
        assertNull(S125Utils.s100TruncatedDateToLocalDate(s100TruncatedDateEmpty));
        assertEquals(now, S125Utils.s100TruncatedDateToLocalDate(s100TruncatedDateDate));
        assertEquals(now, S125Utils.s100TruncatedDateToLocalDate(s100TruncatedDateDayMonthTime));
    }

    /**
     * Test that we can correctly translate LocalDate objects into Java XML
     * S100Truncated objects.
     */
    @Test
    public void testLocalDateToS100TruncatedDate() {
        // Get a time reference
        final LocalDate now = LocalDate.now();

        // Now make the assertions
        assertNull(S125Utils.localDateToS100TruncatedDate(null));
        assertNotNull(S125Utils.localDateToS100TruncatedDate(now));
        assertEquals(now, S125Utils.localDateToS100TruncatedDate(now).getDate());
    }

}