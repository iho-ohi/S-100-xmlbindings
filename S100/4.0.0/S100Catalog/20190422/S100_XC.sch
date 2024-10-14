<?xml version="1.0" encoding="UTF-8"?>
<!-- ============================================================================================= -->
<!--
	© Copyright 2015-2017 (IHB) ... (Draft - Copyright statement tbd)
  © Copyright 2018 (IHO)
	Prepared under NOAA contract
	
	License
  Certain parts of the text of this document refer to or are based on the standards of the International
  Organization for Standardization (ISO). The ISO standards can be obtained from any ISO member and from the
  Web site of the ISO Central Secretariat at www.iso.org.
    
  Permission to copy and distribute this document is hereby granted provided that this notice is retained
  on all copies, and that IHO & NOAA are credited when the material is redistributed or used in part or
	whole in derivative works.
	
  Certain parts of this work are derived from or were originally prepared as works for the UK Location Programme
  (UKLP) and GEMINI project and are (C) Crown copyright (UK). These parts are included under and subject to the
  terms of the Open Government license.

	Disclaimer	(Draft)
	This work is provided without warranty, expressed or implied, that it is complete or accurate or that it
	is fit for any particular purpose.  All such warranties are expressly disclaimed and excluded.
	
	Document history
	Version 1.0	2015-08-18	Raphael Malyankar (Jeppesen) for NOAA / IIC Technologies / IHO
            1.1 2015-09-16  Raphael Malyankar (Jeppesen) corrected element datasetDiscoveryMetadata to S100_DatasetDiscoveryMetadata
  3.0.0-20170430  Raphael Malyankar updated for S-100 3.0.0; vertical and sounding datum in dataset discovery metadata no
                  longer checked (some product specifications do not need them); schema version number convention
                  now <S-100 version>-<file build date (YYYYMMDD)>
  4.0.0-20180502  Updated for Edition 4.0.0 and ISO 19115-3
        20180619  Build number updated to match exchange catalogue schema
        20180702  Build number updated to match exchange catalogue schema
        20181015  Build number updated to match exchange catalogue schema
        20190331  Updated to remove superfluous namespaces; updated display scale rules
        20190422  Updated rules for generic checks; query binding is still XSLT 1 pending normalization of implementations
                  to XSLT 2 or 3; replaced selected usages of the generic S100 exchange catalogue namespace with checks for
                  tag "local names" only, to facilitate use with product-specific extensions to exchange catalogues and added
                  informational checks to detect such product-specific extensions
	-->
<!-- ============================================================================================= -->
<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" schemaVersion="4.0.0-20190331" queryBinding="xslt">
  <sch:ns prefix="cit" uri="http://standards.iso.org/iso/19115/-3/cit/2.0"/>
  <sch:ns prefix="gco" uri="http://standards.iso.org/iso/19115/-3/gco/1.0"/>
  <sch:ns prefix="gcx" uri="http://standards.iso.org/iso/19115/-3/gcx/1.0"/>
  <sch:ns prefix="gex" uri="http://standards.iso.org/iso/19115/-3/gex/1.0"/>
  <sch:ns prefix="lan" uri="http://standards.iso.org/iso/19115/-3/lan/1.0"/>
  <sch:ns prefix="mco" uri="http://standards.iso.org/iso/19115/-3/mco/1.0"/>
  <sch:ns prefix="mdb" uri="http://standards.iso.org/iso/19115/-3/mdb/1.0"/>
  <sch:ns prefix="mri" uri="http://standards.iso.org/iso/19115/-3/mri/1.0"/>

  <sch:ns prefix="gml" uri="http://www.opengis.net/gml/3.2"/>

  <sch:ns uri="http://www.iho.int/s100/xc" prefix="S100XC"/>

  <sch:title>IHO S-100 Schematron validation rules for S-100 Exchange Catalogues</sch:title>

  <!-- Rules to check display scale values -->
  <sch:pattern fpi="S100_4.0.0.4a-D.displayScale">
    <sch:title>Comparative validity of maximum and minimum display scales</sch:title>
    <sch:rule context="//S100XC:maximumDisplayScale">
      <sch:assert test="number() > 0">maximumDisplayScale must be a positive number.</sch:assert>
    </sch:rule>
    <sch:rule context="//S100XC:minimumDisplayScale">
      <sch:p>Compare max and min display scales if min display scale is not nilled</sch:p>
      <sch:let name="DSVAL" value="number()"/>
      <sch:assert test="(@gco:nilReason and normalize-space() = '') or ($DSVAL > 0)">minimumDisplayScale must be nilled or a positive number.</sch:assert>
      <sch:assert test="@gco:nilReason or not(../S100XC:maximumDisplayScale) or (number(../S100XC:maximumDisplayScale) &lt; $DSVAL)">
        maximumDisplayScale must be less than minimumDisplayScale
      </sch:assert>
    </sch:rule>
    <sch:rule context="//S100XC:optimumDisplayScale">
      <sch:assert test="number() > 0">optimumDisplayScale must be a positive number.</sch:assert>
      <sch:report test="../S100XC:maximumDisplayScale and (number(../S100XC:maximumDisplayScale) &gt; number())">Warning: optimum display scale is greater than maximum display scale</sch:report>
      <sch:report test="../S100XC:minimumDisplayScale and not(../S100XC:minimumDisplayScale/@gco:nilReason) and (number(../S100XC:minimumDisplayScale) &lt; number())">Warning: optimum display scale is smaller than minimum display scale</sch:report>
    </sch:rule>
  </sch:pattern>

  <!-- Rules to test validity of bounding box coordinates (if any). Assumes lat/lon in decimal degrees, ranges +/-90.0 and +/-180.0. -->
  <sch:pattern is-a="S100_ValidBBoxPattern" fpi="S100_XC_ValidBBox">
    <sch:title>Validity of bounding box corners</sch:title>
    <sch:param name="bbox" value="//S100XC:boundingBox | //*[@gco:isoType = 'gmd:EX_GeographicBoundingBox']"/>
  </sch:pattern>

  <sch:pattern id="S100_ValidBBoxPattern" abstract="true" fpi="S100_BBox_LLDD_MinMax">
    <sch:title>Check the values of the bounding box min/max. Assumes values are latitude and longitude in decimal degrees in +/-90 or +/-180 range respectively.</sch:title>
    <sch:rule context="$bbox">
      <sch:assert test="gex:westBoundLongitude and (string-length(gex:westBoundLongitude) > 0) and (gex:westBoundLongitude >= -180.0) and (gex:westBoundLongitude &lt;= 180.0)" flag="badWB">
          westBoundLongitude must be present and in the range [-180.0, 180.0].
      </sch:assert>
      <sch:assert test="gex:eastBoundLongitude and (string-length(gex:eastBoundLongitude) > 0) and (gex:eastBoundLongitude >= -180.0) and (gex:eastBoundLongitude &lt;= 180.0)" flag="badEB">
          eastBoundLongitude must be present and in the range [-180.0, 180.0].
      </sch:assert>
      <sch:assert test="gex:southBoundLatitude and (string-length(gex:southBoundLatitude) > 0) and (gex:southBoundLatitude >= -90.0) and (gex:southBoundLatitude &lt;= 90.0)" flag="badSB">
          southBoundLatitude must be present and in the range [-90.0, 90.0].
      </sch:assert>
      <sch:assert test="gex:northBoundLatitude and (string-length(gex:northBoundLatitude) > 0) and (gex:northBoundLatitude >= -90.0) and (gex:northBoundLatitude &lt;= 90.0)" flag="badNB">
          northBoundLatitude must be present and in the range [-90.0, 90.0].
      </sch:assert>
      <sch:assert test="not(badEB or badWB) and (number(gex:westBoundLongitude) &lt; number(gex:eastBoundLongitude))">
        westBoundLongitude (<sch:value-of select="gex:westBoundLongitude"/>) must be less than eastBoundLongitude (<sch:value-of select="gex:eastBoundLongitude"/>)
      </sch:assert>
      <sch:assert test="not(badNB or badSB) and (number(gex:southBoundLatitude) &lt; number(gex:northBoundLatitude))">
        northBoundLatitude (<sch:value-of select="gex:northBoundLatitude"/>) must be greater than southBoundLatitude (<sch:value-of select="gex:southBoundLatitude"/>)
      </sch:assert>
    </sch:rule>
  </sch:pattern>

  <!-- Test for availability or nilling of reference to ISO 19115 metadata file -->
  <sch:pattern fpi="S100_XC_ISO19115Filename">
    <sch:rule context="//S100XC:S100_19115DatasetMetadata">
      <sch:assert test="@gco:nilReason or gcx:FileName[string-length(normalize-space(@src)) > 0]">
        Reference to ISO 19115 metadata file must be nilled or have the gcx:FileName@src attribute populated.
      </sch:assert>
    </sch:rule>
  </sch:pattern>

  <!-- Tests that mandatory elements with string types are either nilled or non-empty / non-blank.    -->
  <sch:pattern fpi="S100_XC_cString_generic_exchangecatalog">
    <sch:title>Rule to check for presence of mandatory elements of type string</sch:title>
    <sch:rule context="/*/S100XC:identifier/S100XC:identifier
        | /*/S100XC:identifier/S100XC:editionNumber
        | /*/S100XC:exchangeCatalogueName
        | /*/S100XC:exchangeCatalogueDescription
        | /*/S100XC:metadataLanguage">
      <sch:assert test="@gco:nilReason or string-length(normalize-space())"><sch:name/> must contain a non-blank, non-empty string value or have a gco:nilReason attribute</sch:assert>
    </sch:rule>
  </sch:pattern>

  <!-- Test for sub-elements of dataset discovery -->
  <!-- This test is also governed by the 4.0.0 rule that mandatory attributes are nillable (4a-5.5) and therefore
        made an informative check instead of an error. The rules for product specifications should
        check for required mandatory attributes specific to the PS
  -->
  
  <sch:pattern fpi="S100_XC_cString_generic_DSDMD.1" >
    <sch:title>Rule to WARN about missing, empty, or blank mandatory elements in S100_DatasetDiscoveryMetadata</sch:title>
    <sch:p>Note: filePath CAN be empty, but should not be blank</sch:p>
    <sch:rule context="/*/S100XC:datasetDiscoveryMetadata/*[contains(local-name(), '_DatasetDiscoveryMetadata')]" role="warn">
      <sch:report test="string-length(normalize-space(S100XC:fileName)) = 0">DatasetDiscoveryMetadata.filename is empty or blank</sch:report>
      <sch:report test="(string-length(S100XC:filePath) > 0) and (string-length(normalize-space(S100XC:filePath)) = 0)">DatasetDiscoveryMetadata.filePath is blank (note - an empty filePath is allowed by the generic S-100 checks)</sch:report>
      <sch:report test="string-length(normalize-space(S100XC:description)) = 0">DatasetDiscoveryMetadata.description is empty or blank</sch:report>
      <sch:report test="string-length(normalize-space(S100XC:dataType)) = 0">DatasetDiscoveryMetadata.dataType is empty or blank</sch:report>
      <sch:report test="string-length(normalize-space(S100XC:dataTypeVersion)) = 0">DatasetDiscoveryMetadata.dataTypeVersion is empty or blank</sch:report>
      <sch:report test="string-length(normalize-space(S100XC:metadataFileIdentifier)) = 0">DatasetDiscoveryMetadata.metadataFileIdentifier is empty or blank</sch:report>
      <sch:report test="string-length(normalize-space(S100XC:metadataLanguage)) = 0">DatasetDiscoveryMetadata.metadataLanguage is empty or blank</sch:report>
    </sch:rule>
  </sch:pattern>


  <!-- Test for sub-elements of product specification sub-element of dataset discovery -->
  <sch:pattern fpi="S100_XC_cString_generic_productSpecification">
    <sch:title>Rule to check for presence of mandatory elements of type string</sch:title>
    <sch:rule context="
        //S100XC:datasetDiscoveryMetadata/*/S100XC:productSpecification/S100XC:name
        | //S100XC:datasetDiscoveryMetadata/*/S100XC:productSpecification/S100XC:version">
      <sch:assert test="string-length(normalize-space())"><sch:name/> must contain a string value that is not all whitespace</sch:assert>
    </sch:rule>
  </sch:pattern>

  <!-- rule to check that role is either nilled or not empty -->
  <sch:pattern fpi="S100_XC_DSMD_role_Nillable">
    <sch:rule context="//S100XC:datasetDiscoveryMetadata//cit:role">
      <sch:assert test="
          (count(*) > 0) or (@gco:nilReason = 'inapplicable' or
          @gco:nilReason = 'missing' or
          @gco:nilReason = 'template' or
          @gco:nilReason = 'unknown' or
          @gco:nilReason = 'withheld' or
          starts-with(@gco:nilReason, 'other:'))" flag="isNilled">
      The <sch:name/> element shall have a value or a valid Nil Reason.
    </sch:assert>
      <sch:assert test="isNilled or (cit:CI_RoleCode and (string-length(cit:CI_RoleCode/@codeListValue) > 0))">
        The codeListValue attribute does not have a value.
      </sch:assert>
    </sch:rule>
  </sch:pattern>

  <!-- 
    Rule to require support file name to be populated in S100_SupportFileDiscoveryMetadata.
  -->
  <sch:pattern fpi="S100_XC_productSpecification">
    <sch:rule context="/*/*[local-name() = 'supportFileDiscoveryMetadata']/S100XC:supportFileSpecification">
      <sch:assert test="string-length(normalize-space(S100XC:name)) > 0">
        The name element of support file discovery metadata must be populated.
      </sch:assert>
    </sch:rule>
  </sch:pattern>

  <!-- Rule to require support file specification date and version to be populated in S100_SupportFileDiscoveryMetadata if dataType is not ASCII -->
  <sch:pattern fpi="S100_XC_productSpecification_1">
    <sch:rule context="/*/*[local-name() = 'supportFileDiscoveryMetadata']" role="warn">
      <sch:assert test="(S100XC:dataType = 'ASCII') or ((string-length(normalize-space(S100XC:supportFileSpecification/S100XC:date)) > 0) and (string-length(normalize-space(S100XC:supportFileSpecification/S100XC:version)) > 0))">
        Warning: Support file discovery metadata element for non-text support files should contain a supportFileSpecification element with date and version populated
      </sch:assert>
    </sch:rule>
  </sch:pattern>

  <!-- Check for presence or absence of both digital signature reference and value for support file metadata -->
  <sch:pattern>
    <sch:rule context="/*/*[local-name() = 'supportFileDiscoveryMetadata']/S100XC:digitalSignatureValue" role="warn">
      <sch:report test="not(../S100XC:digitalSignatureReference)">Digital signature reference not provided, but digital signature is present</sch:report>
    </sch:rule>
    <sch:rule context="/*/*[local-name() = 'supportFileDiscoveryMetadata']/S100XC:digitalSignatureReference" role="warn">
      <sch:report test="not(../S100XC:digitalSignatureValue)">Digital signature reference is provided, but digital signature is not present</sch:report>
    </sch:rule>
  </sch:pattern>

  <!-- Warn about support files not referenced by a dataset discovery block -->
  <sch:pattern fpi="S100_generic_unreferenced_support_file">
  <sch:rule context="/*/S100XC:supportFileDiscoveryMetadata" role="info">
    <sch:assert test="S100XC:fileName = /*/S100XC:datasetDiscoveryMetadata/*[contains(local-name(),'_DatasetDiscoveryMetadata')]/S100XC:supportFileDiscoveryMetadataReference/text()">
      INFORMATION: Discovery block for support file '<sch:value-of select="S100XC:fileName"/>' exists, but this file is not named in any dataset discovery block.
    </sch:assert>
  </sch:rule>
  </sch:pattern>

  <!-- Diagnostic information about namespaces -->
  <!-- print filter duplicate values in namespaces after upgrade to xslt2 -->
  <sch:pattern fpi="NAMESPACESINFO">
    <sch:rule context="/*"  role="info">
      <sch:let name="ROOTNS" value="namespace-uri(/*)"/>
      <!--<sch:report test="$ROOTNS">Exchange catalogue namespace: '<sch:value-of select="namespace-uri()"/>' catalogue root: '<sch:value-of select="local-name()"/>'</sch:report>-->
      <sch:let name="NUMSFDBLOCKS" value="count(*[(local-name() = 'supportFileDiscoveryMetadata') and (namespace-uri() = $ROOTNS)])"/>
      <sch:let name="NUMOTHERSFDBLOCKS" value="count(*[(local-name() = 'supportFileDiscoveryMetadata') and (namespace-uri() != $ROOTNS)])"/>
      <sch:let name="NUMCATBLOCKS" value="count(*[(local-name() = 'S100_CatalogueMetadata') and (namespace-uri() = $ROOTNS)])"/>
      <sch:let name="NUMOTHERCATBLOCKS" value="count(*[(local-name() = 'S100_CatalogueMetadata') and (namespace-uri() != $ROOTNS)])"/>
      <sch:report test="$ROOTNS"><sch:value-of select="$NUMSFDBLOCKS + $NUMOTHERSFDBLOCKS"/> total support file discovery blocks<!-- (<sch:value-of select="$NUMOTHERSFDBLOCKS"/> in a different namespace from the catalogue root)--></sch:report>
      <sch:report test="$ROOTNS"><sch:value-of select="$NUMCATBLOCKS + $NUMOTHERCATBLOCKS"/> total catalogue file discovery blocks<!-- (<sch:value-of select="$NUMOTHERCATBLOCKS"/> in a different namespace from the catalogue root)--></sch:report>
    </sch:rule>
    <sch:rule context="/*/S100XC:datasetDiscoveryMetadata" role="info">
      <sch:let name="ROOTNS" value="namespace-uri(/*)"/>
      <sch:let name="NUMDSDBLOCKS" value="count(*[namespace-uri() = $ROOTNS])"/>
      <sch:let name="NUMOTHERDSDBLOCKS" value="count(*[namespace-uri() != $ROOTNS])"/>
      <sch:report test="$ROOTNS"><sch:value-of select="$NUMDSDBLOCKS + $NUMOTHERDSDBLOCKS"/> total dataset discovery blocks<!-- (<sch:value-of select="$NUMOTHERDSDBLOCKS"/> in a different namespace from the catalogue root)--></sch:report>
    </sch:rule>
  </sch:pattern>

  <!-- ========================================================================================== -->
  <!-- The following patterns are extracts from the Schematron Schema for the UK GEMINI Standard  -->
  <!-- Version 2.1 and are incuded subject to the terms applicable to that schema, reproduced     -->
  <!-- below.                                                                                     -->
  <!-- ========================================================================================== -->

  <!-- 
     James Rapaport                                
     SeaZone Solutions Limited                                                  
     2010-07-13
     
     This Schematron schema has been developed for the UK Location Programme (UKLP) by
     SeaZone Solutions Limited (SeaZone), with funding from Defra and CLG.
     
     It is designed to validate the constraints introduced in the GEMINI2.1 draft standard.
     Constraints have been taken from:
     
     UK GEMINI Standard, Version 2.1, August 2010.
     
     The schema has been developed for XSLT Version 1.0 and tested with the ISO 19757-3 Schematron
     XML Stylesheets issued on 2009-03-18 at http://www.schematron.com/tmp/iso-schematron-xslt1.zip 
     
     The schema tests constraints on ISO / TS 19139 encoded metadata. The rules expressed in this 
     schema apply in addition to validation by the ISO / TS 19139 XML schemas.
     
     The schema is designed to test ISO 19139 encoded metadata incorporating ISO 19136 (GML Version
     3.2.1) elements where necessary. Note that GML elements must be mapped to the Version 3.2.1 
     GML namespace - http://www.opengis.net/gml/3.2
     
     (C) Crown copyright, 2011
     
     You may use and re-use the information in this publication (not including logos) free of charge
     in any format or medium, under the terms of the Open Government Licence.
   
   
     Document History:
     
     2010-10-14 - Version 1.0
     Baselined version for beta release.  No technical changes against v0.11.
     
     2011-01-18 - Version 1.1
     - Metadata Point of Contact (Metadata Item 35) - test now ensures that at least one 
     metadata contact has a role of pointOfContact.
     - Temporal element (Metadata Item 7) - test changed so that it is nolonger the case 
     that only one gmd:temporalElement can be encoded for any gmd:extent element.
     
     2011-04-19 - Version 1.2a
     Changes to allow round trip interoperability with INSPIRE metadata.
     - Temporal extent (Metadata Item 7) - remove rule constraining temporal extent to 
     one only.
     - Geographic bounding box (Metadata Item 11, 12, 13, 14) - allow one or more geographic bounding box
     - Spatial reference system (Metadata Item 17) - remove rule constraining spatial reference
     system to only one.
     - Frequency of update (Metadata Item 24) - remove rule constraining frequency of update to single
     - Unique resource identifier (Metadata Item 36) - amend rule to allow 1 or more Unique resource identifier
     - Equivalent scale (Metadata Item 43) - remove rule constraining Equivalent scale to zero or 1
     
     2011-04-20 - Version 1.2b
     - Unique resource identifier (Metadata item 36) - change sense of failure message to 'one or more'
     - Spatial resolution (Metadata item 18) - remove rule that tests for units of measure
     
     2011-04-21 - Version 1.2c
     - Temporal extent (Metadata item 7) - temporal extent can be implemented as a gml:TimePeriod or 
     gml:TimeInstant. If gml:TimePeriod is used, the child elements are allowed to be gml:begin or gml:beginPosition, and
     gml:end or gml:endPosition.
     
     2011-05-09 - Version 1.2
     - Vertical extent (Metadata item 16) - remove rule restricting number of vertical extent elements
     to 0 or 1.
	 
	2012-12-20 - Version 1.3a
	 - Metadata language - ensure that metadata contains one metadata language element. Gemini2-mi33 edited and rule added.
	2013-02-01 - Version 1.3
	 - as above, but tested by data.gov.uk & EDINA, so accepted for publication
-->

  <!-- ========================================================================================== -->
  <!-- Abstract Patterns                                                                          -->
  <!-- ========================================================================================== -->

  <!-- Test that an element has a value or has a valid nilReason value -->
  <!-- Provisionally extended to account for child elements - RMM -->
  <sch:pattern abstract="true" id="TypeNillablePattern_S100Ext">
    <sch:rule context="$context">
      <sch:assert test="
          (string-length(.) &gt; 0) or (count(*) > 0) or
          (@gco:nilReason = 'inapplicable' or
          @gco:nilReason = 'missing' or
          @gco:nilReason = 'template' or
          @gco:nilReason = 'unknown' or
          @gco:nilReason = 'withheld' or
          starts-with(@gco:nilReason, 'other:'))">
        The <sch:name/> element shall have a value, element content, or a valid Nil Reason.
      </sch:assert>
    </sch:rule>
  </sch:pattern>

  <!-- Test that an element has a value - the value is not nillable -->
  <sch:pattern abstract="true" id="TypeNotNillablePattern">
    <sch:rule context="$context">
      <sch:assert test="string-length(.) &gt; 0 and count(./@gco:nilReason) = 0">
        The <sch:name/> element is not nillable and shall have a value.
      </sch:assert>
    </sch:rule>
  </sch:pattern>

  <!-- Test ISO code lists -->
  <sch:pattern abstract="true" id="IsoCodeListPattern">
    <sch:rule context="$context">
      <sch:assert test="string-length(@codeListValue) &gt; 0">
        The codeListValue attribute does not have a value.
      </sch:assert>
    </sch:rule>
  </sch:pattern>

  <!-- End of patterns included from GEMINI 2.1 Schematron Schema -->


</sch:schema>