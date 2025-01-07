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

package org.grad.eNav.s201.utils;

import _int.iho.s201.s100.gml.base._5_2.*;
import _int.iho.s201.s100.gml.base._5_2.impl.DataSetIdentificationTypeImpl;
import _int.iho.s201.s100.gml.base._5_2.impl.PointPropertyImpl;
import _int.iho.s201.s100.gml.base._5_2.impl.PointTypeImpl;
import _int.iho.s201.s100.gml.profiles._5_2.AbstractGMLType;
import _int.iho.s201.s100.gml.profiles._5_2.BoundingShapeType;
import _int.iho.s201.s100.gml.profiles._5_2.EnvelopeType;
import _int.iho.s201.s100.gml.profiles._5_2.Pos;
import _int.iho.s201.s100.gml.profiles._5_2.impl.BoundingShapeTypeImpl;
import _int.iho.s201.s100.gml.profiles._5_2.impl.EnvelopeTypeImpl;
import _int.iho.s201.s100.gml.profiles._5_2.impl.PosImpl;
import _int.iho.s201.s100.gml.profiles._5_2.impl.ReferenceTypeImpl;
import _int.iho.s201.gml.cs0._1.*;
import _int.iho.s201.gml.cs0._1.S100TruncatedDate;
import _int.iho.s201.gml.cs0._1.impl.*;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class S201UtilsTest {

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
        final InputStream in = ClassLoader.getSystemResourceAsStream("s201-msg.xml");
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
        dataSetIdentificationType.setProductIdentifier("S-201");
        dataSetIdentificationType.setProductEdition("0.0.1");
        dataSetIdentificationType.setApplicationProfile("test");

        dataSetIdentificationType.setDatasetFileIdentifier("junit");
        dataSetIdentificationType.setDatasetTitle("S-201 Cork Hole Test Dataset");
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
        FeatureNameType featureNameType = new FeatureNameTypeImpl();
        featureNameType.setName("Test AtoN for Cork Hole");
        featureNameType.setLanguage(Locale.UK.getISO3Language());
        featureNameType.setDisplayName(Boolean.TRUE);
        vaton.getFeatureNames().add(featureNameType);
        vaton.setIdCode("urn:mrn:grad:aton:test:corkhole");
        vaton.setEstimatedRangeOfTransmission(BigInteger.valueOf(20L));
        vaton.setMMSICode(BigInteger.valueOf(992359598L));
        vaton.setSourceDate(LocalDateTime.of(2000, 1, 1, 0, 0));
        vaton.setSourceIndication("CHT");
        vaton.setPictorialRepresentation("N/A");
        S100TruncatedDate s100TruncatedDateInstallation = new S100TruncatedDateImpl();
        s100TruncatedDateInstallation.setDate(LocalDate.parse("2000-01-01", dateFormat));
        vaton.setInstallationDate(s100TruncatedDateInstallation);
        S100TruncatedDate s100TruncatedDateStart = new S100TruncatedDateImpl();
        s100TruncatedDateStart.setDate(LocalDate.parse("2001-01-01", dateFormat));
        vaton.setDateStart(s100TruncatedDateStart);
        S100TruncatedDate s100TruncatedDateStop = new S100TruncatedDateImpl();
        s100TruncatedDateStop.setDate(LocalDate.parse("2099-01-01", dateFormat));
        vaton.setDateEnd(s100TruncatedDateStop);
        S100TruncatedDate s100TruncatedDatePeriodStart = new S100TruncatedDateImpl();
        s100TruncatedDatePeriodStart.setDate(LocalDate.parse("2001-01-02", dateFormat));
        vaton.setPeriodStart(s100TruncatedDatePeriodStart);
        S100TruncatedDate s100TruncatedDatePeriodStop = new S100TruncatedDateImpl();
        s100TruncatedDatePeriodStop.setDate(LocalDate.parse("2001-01-30", dateFormat));
        vaton.setPeriodEnd(s100TruncatedDatePeriodStop);
        vaton.setInspectionFrequency("yearly");
        vaton.setInspectionRequirements("IALA");
        vaton.setAtoNMaintenanceRecord("urn:mrn:grad:aton:test:corkhole:maintenance:x001");
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
        pos.setValue(new Double[]{51.89166666, 1.4233333});
        pointType.setId("AtoNPoint1");
        pointType.setSrsName("EPSG:4326");
        pointType.setSrsDimension(BigInteger.ONE);
        pointType.setPos(pos);
        pointProperty.setPoint(pointType);
        geometry.setPointProperty(pointProperty);
        vaton.getGeometries().add(geometry);

        // Set the AtoN Status
        AtoNStatusInformation atonStatusInformation = new AtoNStatusInformationImpl();
        atonStatusInformation.setId("ID002");
        atonStatusInformation.setChangeTypes(ChangeTypesType.ADVANCE_NOTICE_OF_CHANGES);
        ChangeDetailsType changeDetailsType = new ChangeDetailsTypeImpl();
        changeDetailsType.setRadioAidsChange(RadioAidsChangeType.AIS_TRANSMITTER_OPERATING_PROPERLY);
        atonStatusInformation.setChangeDetails(changeDetailsType);
        ReferenceTypeImpl atonStatusRef = new ReferenceTypeImpl();
        atonStatusRef.setHref(this.vaton.getId());
        atonStatusRef.setRole("association");
        atonStatusRef.setArcrole("urn:IALA:S201:roles:association");
        vaton.setAtonStatus(atonStatusRef);

        // Now package everything back to the dataset
        S201Utils.addDatasetMembers(dataset, Collections.singletonList(vaton));
        S201Utils.addDatasetMembers(dataset, Collections.singletonList(atonStatusInformation));
    }

    /**
     * A helper function to build the bounding boxes required for the S-201
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
     * Test that we can create (marshall) and XML based on an S-201 Dataset type
     * object.
     *
     * @throws JAXBException a JAXB exception thrown during the marshalling operation
     */
    @Test
    void testMarchallS201() throws JAXBException {
        String xml = S201Utils.marshalS201(this.dataset);
        assertNotNull(xml);
        assertEquals(this.datasetXml, xml);
    }

    /**
     * Test that we can create (marshall) and XML based on an S-201 Dataset type
     * object.
     *
     * @throws JAXBException a JAXB exception thrown during the marshalling operation
     */
    @Test
    void testMarchallS201WithFormat() throws JAXBException {
        String xml = S201Utils.marshalS201(this.dataset, Boolean.TRUE);
        assertNotNull(xml);
        assertEquals(this.datasetXml, xml);
    }

    /**
     * Test that we can generate (unmarshall) an S-201 POJO based on a valid
     * XML S-201 dataset.
     *
     * @throws JAXBException a JAXB exception thrown during the unmarshalling operation
     */
    @Test
    void testUnmarshalS201() throws JAXBException {
        // Unmarshall it to a G1128 service instance object
        Dataset result = S201Utils.unmarshallS201(this.datasetXml);

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

        // Assert the S-201 AidsToNavigation feature information is populated
        assertNotNull(this.dataset.getMembers());
        assertEquals(1, this.dataset.getMembers().getVirtualAISAidToNavigation().size());
        VirtualAISAidToNavigation datasetMember = (VirtualAISAidToNavigation) this.dataset.getMembers().getVirtualAISAidToNavigation().get(0);
        VirtualAISAidToNavigation resultMember =(VirtualAISAidToNavigation) result.getMembers().getVirtualAISAidToNavigation().getFirst();

        // Assert the S-201 AidsToNavigation envelop is correct
        EnvelopeType datasetMemberEnvelope = datasetMember.getBoundedBy().getEnvelope();
        EnvelopeType resultMemberEnvelope = resultMember.getBoundedBy().getEnvelope();
        assertEquals(datasetMemberEnvelope.getSrsName(), resultMemberEnvelope.getSrsName());
        assertEquals(datasetMemberEnvelope.getLowerCorner().getValue().length, resultMemberEnvelope.getLowerCorner().getValue().length);
        assertTrue(Arrays.asList(datasetMemberEnvelope.getLowerCorner().getValue()).containsAll(Arrays.asList(resultMemberEnvelope.getLowerCorner().getValue())));
        assertEquals(datasetMemberEnvelope.getUpperCorner().getValue().length, resultMemberEnvelope.getUpperCorner().getValue().length);
        assertTrue(Arrays.asList(datasetMemberEnvelope.getUpperCorner().getValue()).containsAll(Arrays.asList(resultMemberEnvelope.getUpperCorner().getValue())));

        // Assert the S-201 AidsToNavigation feature information is correct
        //assertEquals(datasetMember.getAtonNumber(), resultMember.getAtonNumber());
        assertEquals(datasetMember.getIdCode(), resultMember.getIdCode());
        assertNotNull(resultMember.getInstallationDate());
        assertEquals(datasetMember.getInstallationDate().getDate(), resultMember.getInstallationDate().getDate());
        assertNotNull(resultMember.getDateStart());
        assertEquals(datasetMember.getDateStart().getDate(), resultMember.getDateStart().getDate());
        assertNotNull(resultMember.getDateEnd());
        assertEquals(datasetMember.getDateEnd().getDate(), resultMember.getDateEnd().getDate());
        assertNotNull(resultMember.getPeriodStart());
        assertEquals(datasetMember.getPeriodStart().getDate(), resultMember.getPeriodStart().getDate());
        assertNotNull(resultMember.getPeriodEnd());
        assertEquals(datasetMember.getPeriodEnd().getDate(), resultMember.getPeriodEnd().getDate());
        assertEquals(datasetMember.getSourceDate(), resultMember.getSourceDate());
        assertEquals(datasetMember.getSourceIndication(), resultMember.getSourceIndication());
        assertEquals(datasetMember.getPictorialRepresentation(), resultMember.getPictorialRepresentation());
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
        assertEquals(datasetMember.getInspectionFrequency(), resultMember.getInspectionFrequency());
        assertEquals(datasetMember.getInspectionRequirements(), resultMember.getInspectionRequirements());
    }

    /**
     * Test that we can read directly the members of an S-201 dataset XMl
     * representation.
     *
     * @throws JAXBException a JAXB exception thrown during the unmarshalling operation
     */
    @Test
    void testGetS201Members() throws JAXBException {
        // Unmarshall it to an S-201 dataset object
        List<? extends AbstractGMLType> members = S201Utils.getDatasetMembers(this.datasetXml);

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
        List<? extends AbstractGMLType> members = S201Utils.getDatasetMembers(this.dataset);

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
        assertTrue(S201Utils.getDatasetMembers((Dataset)null).isEmpty());
        assertTrue(S201Utils.getDatasetMembers(new DatasetImpl()).isEmpty());
    }

    /**
     * This test checks that the S201Utils addDatasetMembers function to easily
     * add a member entry to the correct dataset list i.e. in the Virtual
     * AIS AtoNs.
     */
    @Test
    void testAddDatasetMembers()  {
        // Create a test dataset
        Dataset emptyDataset = new DatasetImpl();

        // Add some entries
        S201Utils.addDatasetMembers(emptyDataset, Collections.singleton(this.vaton));

        // And make sure they can be retrieved
        assertNotNull(emptyDataset);
        assertNotNull(emptyDataset.getMembers());
        assertNotNull(emptyDataset.getMembers().getVirtualAISAidToNavigation());
        assertEquals(1, emptyDataset.getMembers().getVirtualAISAidToNavigation().size());
        assertNotNull(emptyDataset.getMembers().getVirtualAISAidToNavigation().getFirst());
    }

    /**
     * Test that for null inputs, the S201Utils addDatasetMembers function
     * will not throw any exceptions.
     */
    @Test
    void testAddDatasetMembersEmpty()  {
        S201Utils.addDatasetMembers(null, Collections.emptyList());
        S201Utils.addDatasetMembers(this.dataset, null);
    }

    /**
     * Test that if invalid or null inputs are provided, the generation method
     * for the S-201 Aids to Navigation geometries list will return an empty
     * list.
     */
    @Test
    void testGenerateS201AidsToNavigationTypeGeometriesListEmpty() {
        // Test for empty inputs
        assertTrue(S201Utils.generateS201AidsToNavigationTypeGeometriesList(null, null).isEmpty());
        assertTrue(S201Utils.generateS201AidsToNavigationTypeGeometriesList(VirtualAISAidToNavigation.class, null).isEmpty());
        assertTrue(S201Utils.generateS201AidsToNavigationTypeGeometriesList(null, Collections.emptyList()).isEmpty());
    }

    /**
     * Test that based on a provided list of S100SpatialAttributeType objects
     * and given a specific S-201 Aids to Navigation feature, we can generate
     * the geometry object required for it correctly.
     */
    @Test
    void testGenerateS201AidsToNavigationTypeGeometriesList() {
        // Get a list of spatial attribute types
        final List<S100SpatialAttributeType> values = this.vaton.getGeometries().stream()
                .map(VirtualAISAidToNavigation.Geometry::getPointProperty)
                .collect(Collectors.toList());

        // Test for a valid input
        List<?> atonGeometriesList = S201Utils.generateS201AidsToNavigationTypeGeometriesList(VirtualAISAidToNavigationImpl.class, values);
        assertNotNull(atonGeometriesList);
        assertEquals(values.size(), atonGeometriesList.size());
        for(int i=0; i<atonGeometriesList.size(); i++) {
            assertTrue(atonGeometriesList.get(i) instanceof VirtualAISAidToNavigation.Geometry);
            assertEquals(values.get(i), ((VirtualAISAidToNavigation.Geometry)atonGeometriesList.get(i)).getPointProperty());
        }
    }

    /**
     * Test that based on a provided list of S100SpatialAttributeType objects
     * and given a specific S-201 Aids to Navigation feature, we can generate
     * the geometry object required for it correctly. In this case however, the
     * provided class does not define a custom Geometry but its parent does.
     */
    @Test
    void testGenerateS201AidsToNavigationTypeGeometriesListFromParent() {
        // Get a list of spatial attribute types
        final List<S100SpatialAttributeType> values = this.vaton.getGeometries().stream()
                .map(VirtualAISAidToNavigation.Geometry::getPointProperty)
                .collect(Collectors.toList());

        // Test for a valid input that extends a parent with a geometry
        List<?> atonGeometriesList = S201Utils.generateS201AidsToNavigationTypeGeometriesList(LighthouseImpl.class, values);
        assertNotNull(atonGeometriesList);
        assertEquals(values.size(), atonGeometriesList.size());
        for(int i=0; i<atonGeometriesList.size(); i++) {
            assertTrue(atonGeometriesList.get(i) instanceof LandmarkTypeImpl.Geometry);
            assertEquals(values.get(i), ((LandmarkTypeImpl.Geometry)atonGeometriesList.get(i)).getPointProperty());
        }
    }

    /**
     * Test that we can correctly retrieve the geometry from an S-201 Aids to
     * Navigation feature.
     *
     * @throws JAXBException a JAXB exception thrown during the unmarshalling operation
     */
    @Test
    void testGetS201AidsToNavigationTypeGeometriesList() throws JAXBException {
        // Unmarshall it to a G1128 service instance object
        Dataset result = S201Utils.unmarshallS201(this.datasetXml);

        // Assert the S-201 AidsToNavigation feature information is populated
        assertNotNull(result.getMembers());
        assertEquals(1, result.getMembers().getVirtualAISAidToNavigation().size());
        VirtualAISAidToNavigation resultMember = (VirtualAISAidToNavigation) result.getMembers().getVirtualAISAidToNavigation().getFirst();

        // Extract the geometry
        List<S100SpatialAttributeType> memberGeometries = S201Utils.getS201AidsToNavigationTypeGeometriesList(resultMember);
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
    public void testHandlePerS201AidsToNavigationType() {
        final AtomicInteger runs = new AtomicInteger(0);
        this.dataset.getMembers()
                .getVirtualAISAidToNavigation()
                .stream()
                .filter(VirtualAISAidToNavigation.class::isInstance)
                .map(VirtualAISAidToNavigation.class::cast)
                .forEach(m -> S201Utils.handlePerS201AidsToNavigationType(m, VirtualAISAidToNavigation.class, a -> {
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
        assertNull(S201Utils.s100TruncatedDateToLocalDate(s100TruncatedDateNull));
        assertNull(S201Utils.s100TruncatedDateToLocalDate(s100TruncatedDateEmpty));
        assertEquals(now, S201Utils.s100TruncatedDateToLocalDate(s100TruncatedDateDate));
        assertEquals(now, S201Utils.s100TruncatedDateToLocalDate(s100TruncatedDateDayMonthTime));
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
        assertNull(S201Utils.localDateToS100TruncatedDate(null));
        assertNotNull(S201Utils.localDateToS100TruncatedDate(now));
        assertEquals(now, S201Utils.localDateToS100TruncatedDate(now).getDate());
    }

}