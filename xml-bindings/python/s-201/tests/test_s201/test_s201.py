import pytest
import os
import re

from xsdata.models.datatype import XmlDate
from xsdata.formats.dataclass.parsers import XmlParser
from xsdata.formats.dataclass.serializers import XmlSerializer
from xsdata.formats.dataclass.serializers.config import SerializerConfig

from datetime import date
from iso639 import Lang

from org.grad.enav.s201 import *


@pytest.fixture
def s201_msg_xml():
    # Get absolute path of this test file
    test_dir = os.path.dirname(__file__)

    # Open the file
    s201_msg_file = open(test_dir + '/s201-msg.xml', mode="rb")

    # And return the contents
    yield s201_msg_file.read().decode("utf-8")


@pytest.fixture
def bounding_shape():
    bounding_shape = BoundingShapeType()
    bounding_shape.envelope = EnvelopeType()
    bounding_shape.envelope.srs_name = 'EPSG:4326'
    bounding_shape.envelope.srs_dimension = '1'
    bounding_shape.envelope.lower_corner = [51.8916667, 1.4233333]
    bounding_shape.envelope.upper_corner = [51.8916667, 1.4233333]

    yield bounding_shape


@pytest.fixture
def virtual_ais_aton(bounding_shape):
    virtual_ais_aton = VirtualAisaidToNavigation()
    virtual_ais_aton.bounded_by = bounding_shape
    virtual_ais_aton.id = 'ID001'
    virtual_ais_aton.i_dcode = 'urn:mrn:grad:aton:test:corkhole'
    virtual_ais_aton.seasonal_action_required = ['none']
    virtual_ais_aton.m_msicode = '992359598'
    virtual_ais_aton.source = 'CHT'
    virtual_ais_aton.source_date = XmlDate(2000, 1, 1)
    virtual_ais_aton.pictorial_representation = 'N/A'
    virtual_ais_aton.installation_date = XmlDate(2000, 1, 1)
    virtual_ais_aton.inspection_frequency = 'yearly'
    virtual_ais_aton.inspection_requirements = 'IALA'
    virtual_ais_aton.a_to_nmaintenance_record = 'urn:mrn:grad:aton:test:corkhole:maintenance:x001'
    virtual_ais_aton.virtual_aisaid_to_navigation_type = VirtualAisaidToNavigationTypeType.SPECIAL_PURPOSE
    virtual_ais_aton.status = [StatusType.CONFIRMED]
    virtual_ais_aton.virtual_aisbroadcasts = []
    
    # Setup the feature name
    virtual_ais_aton.feature_name = []
    feature_name = FeatureNameType()
    feature_name.display_name = 1
    feature_name.language = Lang("English").pt3
    feature_name.name = 'Test AtoN for Cork Hole'
    virtual_ais_aton.feature_name.append(feature_name)

    # Setup the date range
    fixed_date_range = FixedDateRangeType()
    fixed_date_range.date_start = S100TruncatedDate2()
    fixed_date_range.date_start.date = XmlDate(2001, 1, 1)
    fixed_date_range.date_end = S100TruncatedDate2()
    fixed_date_range.date_end.date = XmlDate(2099, 1, 1)
    virtual_ais_aton.fixed_date_range = fixed_date_range

    # Setup the geometry
    geometry = VirtualAisaidToNavigationType.Geometry()
    geometry.point_property = PointProperty2()
    point = Point2()
    pos = Pos()
    pos.id = 'AtoNPoint'
    pos.srs_name = 'EPSG:4326'
    pos.srs_dimension = 1
    pos.value = [51.8916667, 1.4233333]
    point.pos = pos
    geometry.point_property.point = point
    virtual_ais_aton.geometry = [geometry]

    yield virtual_ais_aton


@pytest.fixture
def aton_status_information():
    aton_status_information = AtonStatusInformation()
    aton_status_information.id = 'ID002'
    aton_status_information.change_details = ChangeDetailsType()
    aton_status_information.change_details.electronic_aton_change = ElectronicAtonChangeType.AIS_TRANSMITTER_OPERATING_PROPERLY
    aton_status_information.change_types =  ChangeTypesType.ADVANCED_NOTICE_OF_CHANGES 

    yield aton_status_information


@pytest.fixture
def s201_dataset(bounding_shape, virtual_ais_aton, aton_status_information):
    # Create a new dataset
    s201_dataset = Dataset()

    # Initialise the dataset
    s201_dataset.id = "CorkHoleTestDataset"
    s201_dataset.bounded_by = bounding_shape    

    # Add the dataset identification information
    dataset_identification_type = DataSetIdentificationType()
    dataset_identification_type.encoding_specification = 'S-100 Part 10b'
    dataset_identification_type.encoding_specification_edition = '1.0'
    dataset_identification_type.product_identifier = 'S-201'
    dataset_identification_type.product_edition = '0.0.1'
    dataset_identification_type.application_profile = 'test'
    dataset_identification_type.dataset_file_identifier = 'junit'
    dataset_identification_type.dataset_title = 'S-201 Cork Hole Test Dataset'
    dataset_identification_type.dataset_reference_date = XmlDate(2001, 1, 1)
    dataset_identification_type.dataset_language = Lang("English").pt3
    dataset_identification_type.dataset_abstract = 'Test dataset for unit testing'
    dataset_identification_type.dataset_topic_category = [MdTopicCategoryCode.OCEANS]
    dataset_identification_type.dataset_purpose = DatasetPurposeType.BASE
    dataset_identification_type.update_number = 2
    s201_dataset.dataset_identification_information = dataset_identification_type

    # Link the aton and its status
    virtual_ais_aton.statuspart = ReferenceType()
    virtual_ais_aton.statuspart.href = aton_status_information.id
    virtual_ais_aton.statuspart.role = "association"
    virtual_ais_aton.statuspart.arcrole = "urn:IALA:S201:roles:association"

    # Add the dataset members - A single Virtual AIS Aid to Navigation and its status
    s201_dataset.members = ThisDatasetType.Members()
    s201_dataset.members.virtual_aisaid_to_navigation = [
        virtual_ais_aton
    ]
    s201_dataset.members.aton_status_information = [
        aton_status_information
    ]


    # And return the dataset
    yield s201_dataset


def test_marshall(s201_dataset, s201_msg_xml):
    """
    Test that we can successfully marshall an S-201 dataset from the generated
    python objects using the PYXB library.
    """    
    # 3. Create the XML serializer
    config = SerializerConfig(indent="    ")
    serializer = XmlSerializer(config=config)

    # And Marshall to XMl
    s201_dataset_xml = serializer.render(s201_dataset)
    
    # Remove the namespaces from the datasets
    s201_dataset_xml_without_ns = re.sub("<ns\\d:Dataset[^>]*>","<Dataset>", s201_dataset_xml).replace("\r\n", "").replace("\n", "")
    s201_msg_xml_without_ns = re.sub("<ns\\d:Dataset[^>]*>","<Dataset>", s201_msg_xml).replace("\r\n", "").replace("\n", "")

    # Make sure the XMl seems correct
    assert s201_dataset_xml_without_ns == s201_msg_xml_without_ns


def test_unmarshall(s201_msg_xml):
    """
    Test that we can successfully unmarshall a packaged S-201 dataset using the 
    generated python objects of the PYXB library.
    """
    # Parse the S201 test message XML
    parser = XmlParser()
    dataset = parser.from_string(s201_msg_xml, Dataset)

    # Make sure the dataset identification information seems correct
    assert dataset.id == "CorkHoleTestDataset"
    assert dataset.dataset_identification_information != None
    assert dataset.dataset_identification_information.encoding_specification == 'S-100 Part 10b'
    assert dataset.dataset_identification_information.encoding_specification_edition == '1.0'
    assert dataset.dataset_identification_information.product_identifier == 'S-201'
    assert dataset.dataset_identification_information.product_edition == '0.0.1'
    assert dataset.dataset_identification_information.application_profile == 'test'
    assert dataset.dataset_identification_information.dataset_file_identifier == 'junit'
    assert dataset.dataset_identification_information.dataset_title == 'S-201 Cork Hole Test Dataset'
    assert dataset.dataset_identification_information.dataset_reference_date == XmlDate(2001, 1, 1)
    assert dataset.dataset_identification_information.dataset_language == Lang("English").pt3
    assert dataset.dataset_identification_information.dataset_abstract == 'Test dataset for unit testing'
    assert dataset.dataset_identification_information.dataset_topic_category == [MdTopicCategoryCode.OCEANS]
    assert dataset.dataset_identification_information.dataset_purpose == DatasetPurposeType.BASE
    assert dataset.dataset_identification_information.update_number == 2

    # And make sure the AtoN contents seems correct
    assert dataset.members.virtual_aisaid_to_navigation != None
    assert len(dataset.members.virtual_aisaid_to_navigation) == 1
    assert dataset.members.virtual_aisaid_to_navigation[0].id == 'ID001'
    assert dataset.members.virtual_aisaid_to_navigation[0].feature_name != None
    assert len(dataset.members.virtual_aisaid_to_navigation[0].feature_name) == 1
    assert dataset.members.virtual_aisaid_to_navigation[0].feature_name[0].name == 'Test AtoN for Cork Hole'
    assert dataset.members.virtual_aisaid_to_navigation[0].feature_name[0].language == Lang("English").pt3
    assert dataset.members.virtual_aisaid_to_navigation[0].feature_name[0].display_name == 1
    assert dataset.members.virtual_aisaid_to_navigation[0].i_dcode == 'urn:mrn:grad:aton:test:corkhole'
    assert dataset.members.virtual_aisaid_to_navigation[0].seasonal_action_required == ['none']
    assert dataset.members.virtual_aisaid_to_navigation[0].m_msicode == '992359598'
    assert dataset.members.virtual_aisaid_to_navigation[0].source == 'CHT'
    assert dataset.members.virtual_aisaid_to_navigation[0].source_date == XmlDate(2000, 1, 1)
    assert dataset.members.virtual_aisaid_to_navigation[0].pictorial_representation == 'N/A'
    assert dataset.members.virtual_aisaid_to_navigation[0].installation_date == XmlDate(2000, 1, 1)
    assert dataset.members.virtual_aisaid_to_navigation[0].inspection_frequency == 'yearly'
    assert dataset.members.virtual_aisaid_to_navigation[0].inspection_requirements == 'IALA'
    assert dataset.members.virtual_aisaid_to_navigation[0].a_to_nmaintenance_record == 'urn:mrn:grad:aton:test:corkhole:maintenance:x001'
    assert dataset.members.virtual_aisaid_to_navigation[0].virtual_aisaid_to_navigation_type == VirtualAisaidToNavigationTypeType.SPECIAL_PURPOSE
    assert dataset.members.virtual_aisaid_to_navigation[0].status == [StatusType.CONFIRMED]
    assert dataset.members.virtual_aisaid_to_navigation[0].virtual_aisbroadcasts == []

    # And make sure the AtoN status information seems correct
    assert dataset.members.aton_status_information != None
    assert len(dataset.members.aton_status_information) == 1
    assert dataset.members.aton_status_information[0].id == 'ID002'
    assert dataset.members.aton_status_information[0].change_details != None
    assert dataset.members.aton_status_information[0].change_details.electronic_aton_change == ElectronicAtonChangeType.AIS_TRANSMITTER_OPERATING_PROPERLY
    assert dataset.members.aton_status_information[0].change_types ==  ChangeTypesType.ADVANCED_NOTICE_OF_CHANGES 


