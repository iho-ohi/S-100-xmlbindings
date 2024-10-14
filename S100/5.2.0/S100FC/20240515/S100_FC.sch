<?xml version="1.0" encoding="UTF-8"?>
<!-- ============================================================================================= -->
<!--
	© Copyright 2022-2024 International Hydrographic Organization (IHO)
	
	License
  Certain parts of the text of this document refer to or are based on the standards of the International
  Organization for Standardization (ISO). The ISO standards can be obtained from any ISO member and from the
  Web site of the ISO Central Secretariat at www.iso.org.
    
  Permission to copy and distribute this document is hereby granted provided that this notice is retained
  on all copies, and that IHO are credited when the material is redistributed or used in part or
	whole in derivative works.
	
  Certain parts of this work are derived from or were originally prepared as works for the UK Location Programme
  (UKLP) and GEMINI project and are (C) Crown copyright (UK). These parts are included under and subject to the
  terms of the Open Government license.

	Disclaimer
	This work is provided without warranty, expressed or implied, that it is complete or accurate or that it
	is fit for any particular purpose.  All such warranties are expressly disclaimed and excluded.
	
	Document history
	Version 5.0.0 2022-03-31	Raphael Malyankar (Portolan Sciences LLC) for S-100 Edition 5.0.0.
    Version 5.0.0 is the first version of this file. The number is set for compatibility with the corresponding edition of S-100.
    Build 20220610: build in version updated due to update in S100FC XSD
    Build 20231214: Build number updated to conform to S100FC
	-->
<!-- 2023-12-04: Updated for S-100 5.2.0 Eivind Mong (CCG)
	Modifications: namespace updated due to update in S100 FC XSD;
	Build 20231214: Namespaces for S100CI, S100Base, S100CD reverted following discussion with OEMs (RMM)
	Build 20240515: Additional Schematron rules arising after S-101 PT 12 (RMM):
    Check that all values of DefinitionReference are included as DefinitionSource entries. 
    Report  all simple attributes of type real or integer which do not have have units of measure populated. 
    Check for consistent attributes/value of upper bound on multiplicity
    Check for duplicate feature and information bindings

 -->
 
<!-- ============================================================================================= -->
<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" schemaVersion="5.2.0-20240515">
  <!--<sch:ns prefix="fn" uri="http://www.w3.org/2005/xpath-functions"/>-->
  <!-- S-100 namespaces -->
  <sch:ns prefix="S100FC"   uri="http://www.iho.int/S100FC/5.2"/>
  <sch:ns prefix="S100Base" uri="http://www.iho.int/S100Base/5.0"/>
  <sch:ns prefix="S100CI"   uri="http://www.iho.int/S100CI/5.0"/>
  <sch:ns prefix="S100CD"   uri="http://www.iho.int/S100CD/5.0"/>

  <!-- ISO and OGC namespaces -->
  <sch:ns prefix="cat" uri="http://standards.iso.org/iso/19115/-3/cat/1.0"/>
  <sch:ns prefix="cit" uri="http://standards.iso.org/iso/19115/-3/cit/2.0"/>
  <sch:ns prefix="gco" uri="http://standards.iso.org/iso/19115/-3/gco/1.0"/>

<!--
  <sch:ns prefix="gcx" uri="http://standards.iso.org/iso/19115/-3/gcx/1.0"/>
  <sch:ns prefix="gex" uri="http://standards.iso.org/iso/19115/-3/gex/1.0"/>
  <sch:ns prefix="gmw" uri="http://standards.iso.org/iso/19115/-3/gmw/1.0"/>
-->
  <sch:ns prefix="lan" uri="http://standards.iso.org/iso/19115/-3/lan/1.0"/>
<!--  
  <sch:ns prefix="mac" uri="http://standards.iso.org/iso/19115/-3/mac/1.0"/>
  <sch:ns prefix="mas" uri="http://standards.iso.org/iso/19115/-3/mas/1.0"/>
  <sch:ns prefix="mcc" uri="http://standards.iso.org/iso/19115/-3/mcc/1.0"/>
  <sch:ns prefix="mco" uri="http://standards.iso.org/iso/19115/-3/mco/1.0"/>
  <sch:ns prefix="mdb" uri="http://standards.iso.org/iso/19115/-3/mdb/1.0"/>
  <sch:ns prefix="mex" uri="http://standards.iso.org/iso/19115/-3/mex/1.0"/>
  <sch:ns prefix="mmi" uri="http://standards.iso.org/iso/19115/-3/mmi/1.0"/>
  <sch:ns prefix="mpc" uri="http://standards.iso.org/iso/19115/-3/mpc/1.0"/>
  <sch:ns prefix="mrc" uri="http://standards.iso.org/iso/19115/-3/mrc/2.0"/>
  <sch:ns prefix="mrd" uri="http://standards.iso.org/iso/19115/-3/mrd/1.0"/>
  <sch:ns prefix="mri" uri="http://standards.iso.org/iso/19115/-3/mri/1.0"/>
  <sch:ns prefix="mrl" uri="http://standards.iso.org/iso/19115/-3/mrl/2.0"/>
  <sch:ns prefix="mrs" uri="http://standards.iso.org/iso/19115/-3/mrs/1.0"/>
  <sch:ns prefix="msr" uri="http://standards.iso.org/iso/19115/-3/msr/2.0"/>
  <sch:ns prefix="srv" uri="http://standards.iso.org/iso/19115/-3/srv/2.0"/>
  
  <sch:ns prefix="mds" uri="http://standards.iso.org/iso/19115/-3/mds/1.0"/>
  <sch:ns prefix="md1" uri="http://standards.iso.org/iso/19115/-3/md1/1.0"/>
  <sch:ns prefix="mda" uri="http://standards.iso.org/iso/19115/-3/mda/1.0"/>
  <sch:ns prefix="mdt" uri="http://standards.iso.org/iso/19115/-3/mdt/1.0"/>
  <sch:ns prefix="md2" uri="http://standards.iso.org/iso/19115/-3/md2/1.0"/>
-->
  
<!--
  <sch:ns prefix="gmd" uri="http://www.isotc211.org/2005/gmd"/>
  <sch:ns prefix="gmx" uri="http://www.isotc211.org/2005/gmx"/>
-->
  <!--<sch:ns prefix="gml" uri="http://www.opengis.net/gml/3.2"/>-->
  <!--<sch:ns uri="http://www.isotc211.org/2005/gmi" prefix="gmi"/>-->
  
  <sch:ns prefix="xsi" uri="http://www.w3.org/2001/XMLSchema-instance"/>

  <sch:title>IHO S-100 Schematron validation rules for S-100 Feature Catalogues</sch:title>

  <sch:pattern fpi="S100_5.0.5-A.16_permittedValues" id="S100.5.0.5-A.16.PERMVAL">
    <sch:title>Permitted values allowed only for enum or codelist attributes</sch:title>
    <sch:rule context="//S100FC:attributeBinding[S100FC:permittedValues]">
      <sch:let name="ATTR" value="S100FC:attribute/@ref"/>
      <sch:report test="/S100FC:S100_FC_FeatureCatalogue/S100FC:S100_FC_SimpleAttributes/S100FC:S100_FC_SimpleAttribute[(S100FC:code = $ATTR) and ((S100FC:valueType != 'enumeration') and (S100FC:valueType != 'S100_CodeList'))]" role="warning">
        <sch:value-of select="../S100FC:code"/> / <sch:value-of select="S100FC:attribute/@ref"/>: permittedValues applies only to attributes of type enumeration or S100_Codelist
      </sch:report>
    </sch:rule>
  </sch:pattern>

  <sch:pattern fpi="S100_5.2.5-A.16_numericsuom" id="S100.5.2.5-A.16.NUMERICS.UOM">
    <sch:title>Simple attributes of numeric types generally should have units of measure</sch:title>
    <sch:rule context="/S100FC:S100_FC_FeatureCatalogue/S100FC:S100_FC_SimpleAttributes/S100FC:S100_FC_SimpleAttribute[(S100FC:valueType = 'real') or (S100FC:valueType = 'integer')]" role="warning">
      <sch:assert test="S100FC:uom">
        Attribute <sch:value-of select="S100FC:code"/> (<sch:value-of select="S100FC:valueType"/>) has no UoM, verify omission of UoM
      </sch:assert>
    </sch:rule>
  </sch:pattern>
  
  <sch:pattern fpi="S100_5.2.5-A.16_emptyuom" id="S100.5.2.5-A.16.EMPTY.UOM">
    <sch:title>Check that UoM elements have name, definition, and symbol populated</sch:title>
    <sch:rule context="/S100FC:S100_FC_FeatureCatalogue/S100FC:S100_FC_SimpleAttributes/S100FC:S100_FC_SimpleAttribute/S100FC:uom" role="warning">
      <sch:assert test="S100Base:name and string-length(normalize-space(S100Base:name)) > 0">
        UoM for <sch:value-of select="../S100FC:code"/> does not have a name
      </sch:assert>
      <!--  Check for definition checks only that it is populated if present; importance of UoM definition being present in FCs is TBD -->
      <sch:assert test="(count(S100Base:definition) = 0) or (string-length(normalize-space(S100Base:definition)) > 0)">
        UoM for <sch:value-of select="../S100FC:code"/> does not have a definition
      </sch:assert>
      <sch:assert test="S100Base:symbol and string-length(normalize-space(S100Base:symbol)) > 0">
        UoM for <sch:value-of select="../S100FC:code"/> does not have a symbol
      </sch:assert>
    </sch:rule>
  </sch:pattern>
  
  <!-- Report inconsistent values within feature, info type, and complex attribute multiplicity.  -->
  <sch:pattern fpi="S100_5.2.5-A.16_bindmult_1" id="S100.5.2.5-A.16.MULT.UPPER">
    <sch:title>Compatibility of xsi:nil and infinite attributes and multiplicity upper bound</sch:title>
    <sch:p>count(@infinite) check is insurance against non-compliant implementations</sch:p>
    <sch:rule context="//S100FC:multiplicity/S100Base:upper[count(@xsi:nil) = 0]" role="warning" id="S100.5.2.5-A.16.MULT.UPPER.1">
      <sch:assert test="(count(@infinite) = 0) or (@infinite = 'false')">
        MULT.1: <sch:value-of select="ancestor::S100FC:*[S100FC:code]/S100FC:code"/>.<sch:value-of select="../../S100FC:attribute/@ref"/>: Upper bound not nilled but has infinite=<sch:value-of select="@infinite"/>
      </sch:assert>
    </sch:rule>
    <sch:rule context="//S100FC:multiplicity/S100Base:upper[(count(@xsi:nil) > 0) and (@xsi:nil = 'true')]" role="warning" id="S100.5.2.5-A.16.BIND.MULT.UPPER.2">
      <sch:assert test="@infinite and (@infinite = 'true') and (string-length(normalize-space(.)) = 0)">
        MULT.2: <sch:value-of select="ancestor::S100FC:*[S100FC:code]/S100FC:code"/>.<sch:value-of select="../../S100FC:attribute/@ref"/>: Allowed combinations of (xsi:nil, infinite, value) are (true, true, &lt;absent&gt;), (false, false, &lt;nonNegativeInteger&gt;), (&lt;absent&gt;, &lt;absent&gt;, &lt;nonNegativeInteger&gt;)
      </sch:assert>
    </sch:rule>
    <sch:rule context="//S100FC:multiplicity/S100Base:upper[(count(@xsi:nil) > 0) and @xsi:nil = 'false']" role="warning" id="S100.5.2.5-A.16.BIND.MULT.UPPER.3">
      <sch:assert test="@infinite and (@infinite = 'false') and (string-length(normalize-space(.)) &gt; 0)">
        MULT.3: <sch:value-of select="ancestor::S100FC:*[S100FC:code]/S100FC:code"/>.<sch:value-of select="../../S100FC:attribute/@ref"/>: Allowed combinations of (xsi:nil, infinite, value) are (true, true, &lt;absent&gt;), (false, false, &lt;nonNegativeInteger&gt;), (&lt;absent&gt;, &lt;absent&gt;, &lt;nonNegativeInteger&gt;)
      </sch:assert>
    </sch:rule>
  </sch:pattern>
  
  <!-- Report feature/information bindings to the same feature/info type with the same role name.  -->
  <sch:pattern fpi="S100_5.2.5-A.16_dup_binding" id="S100.5.2.5-A.16.DUP.BIND">
    <sch:title>Multiple roles for the same target in feature and information bindings</sch:title>
    <sch:rule context="/S100FC:S100_FC_FeatureCatalogue/S100FC:S100_FC_FeatureTypes/S100FC:S100_FC_FeatureType[count(S100FC:informationBinding) &gt; 1]/S100FC:informationBinding | 
                       /S100FC:S100_FC_FeatureCatalogue/S100FC:S100_FC_InformationTypes/S100FC:S100_FC_InformationType[count(S100FC:informationBinding) &gt; 1]/S100FC:informationBinding"
              role="warning" id="S100.5.2.5-A.16.DUP.BIND.R1">
      <sch:let name="ITYPE" value="S100FC:informationType/@ref"/>
      <sch:let name="ROLE" value="S100FC:role/@ref"/>
      <sch:assert test="count(preceding-sibling::S100FC:informationBinding[(S100FC:informationType/@ref = $ITYPE) and (S100FC:role/@ref = $ROLE)]) = 0">
        DB.1: Duplicate binding <sch:value-of select="../S100FC:code"/>.<sch:value-of select="$ROLE"/>=&gt;<sch:value-of select="$ITYPE"/>
      </sch:assert>
    </sch:rule>
    <sch:rule context="/S100FC:S100_FC_FeatureCatalogue/S100FC:S100_FC_FeatureTypes/S100FC:S100_FC_FeatureType[count(S100FC:featureBinding) &gt; 1]/S100FC:featureBinding" role="warning" id="S100.5.2.5-A.16.DUP.BIND.R2">
      <sch:let name="FTYPE" value="S100FC:featureType/@ref"/>
      <sch:let name="ROLE" value="S100FC:role/@ref"/>
      <sch:assert test="count(preceding-sibling::S100FC:featureBinding[(S100FC:featureType/@ref = $FTYPE) and (S100FC:role/@ref = $ROLE)]) = 0">
        DB.2: Duplicate binding <sch:value-of select="../S100FC:code"/>.<sch:value-of select="$ROLE"/>=&gt;<sch:value-of select="$FTYPE"/>
      </sch:assert>
    </sch:rule>
  </sch:pattern>
  
  <!-- Report feature/information bindings with the same roles and multiplicities -->
  <sch:pattern fpi="S100_5.2.5-A.16_dup_role_mult_binding" id="S100.5.2.5-A.16.DUP.ROLE.MULT.BIND">
    <sch:title>Duplicate roles + multiplicities in feature and information bindings</sch:title>
    <sch:rule context="/S100FC:S100_FC_FeatureCatalogue/S100FC:S100_FC_FeatureTypes/S100FC:S100_FC_FeatureType[count(S100FC:featureBinding) &gt; 1]/S100FC:featureBinding" role="warning" id="S100.5.2.5-A.16.DUP.ROLE.MULT.BIND.R1">
      <sch:let name="ROLE" value="S100FC:role/@ref"/>
      <sch:let name="LB" value="S100FC:multiplicity/S100Base:lower"/>
      <sch:let name="UB" value="S100FC:multiplicity/S100Base:upper"/>
      <sch:let name="ROLETYPE" value="@roleType"/>
      <sch:let name="ASSOC" value="S100FC:association/@ref"/>
      <sch:assert test="count(preceding-sibling::S100FC:featureBinding[(S100FC:role/@ref = $ROLE) and (S100FC:multiplicity/S100Base:lower = $LB) and (S100FC:multiplicity/S100Base:upper = $UB) and (S100FC:association/@ref = $ASSOC)]) = 0">
        DRM.1: Separate bindings with same roles/multiplicities: <sch:value-of select="local-name(.)"/> <sch:value-of select="../S100FC:code"/>.<sch:value-of select="$ROLE"/>(<sch:value-of select="$LB"/>..<sch:value-of select="$UB"/>)
      </sch:assert>
    </sch:rule>
    <sch:rule context="/S100FC:S100_FC_FeatureCatalogue/S100FC:S100_FC_FeatureTypes/S100FC:S100_FC_FeatureType[count(S100FC:informationBinding) &gt; 1]/S100FC:informationBinding |
                       /S100FC:S100_FC_FeatureCatalogue/S100FC:S100_FC_InformationTypes/S100FC:S100_FC_InformationType[count(S100FC:informationBinding) &gt; 1]/S100FC:informationBinding"
              role="warning" id="S100.5.2.5-A.16.DUP.ROLE.MULT.BIND.R2">
      <sch:let name="ROLE" value="S100FC:role/@ref"/>
      <sch:let name="LB" value="S100FC:multiplicity/S100Base:lower"/>
      <sch:let name="UB" value="S100FC:multiplicity/S100Base:upper"/>
      <sch:let name="ROLETYPE" value="@roleType"/>
      <sch:let name="ASSOC" value="S100FC:association/@ref"/>
      <sch:assert test="count(preceding-sibling::S100FC:informationBinding[(S100FC:role/@ref = $ROLE) and (S100FC:multiplicity/S100Base:lower = $LB) and (S100FC:multiplicity/S100Base:upper = $UB) and (S100FC:association/@ref = $ASSOC)]) = 0">
        DRM.2: Separate bindings with same roles/multiplicities: <sch:value-of select="local-name(.)"/> <sch:value-of select="../S100FC:code"/>.<sch:value-of select="$ROLE"/>(<sch:value-of select="$LB"/>..<sch:value-of select="$UB"/>)
      </sch:assert>
    </sch:rule>
  </sch:pattern>
  
  <!-- TextPattern used with numeric, boolean, codelist, or enumeration types -->
  <sch:pattern fpi="S100_5.2.5-A.16_textPattern" id="S100.5.2.5-A16.TEXTPATTERN">
    <sch:title>Report use of textPattern with enumeration, codelist, or numeric datatypes</sch:title>
    <sch:rule context="/S100FC:S100_FC_FeatureCatalogue/S100FC:S100_FC_SimpleAttributes/S100FC:S100_FC_SimpleAttribute[(S100FC:valueType = 'real') or (S100FC:valueType = 'integer') or (S100FC:valueType = 'enumeration') or (S100FC:valueType = 'S100_CodeList')]" id="S100.5.2.5-A16.TEXTPATTERN.R1" role="warning">
      <sch:assert test="count(S100FC:constraints/S100CD:textPattern) = 0">
        Attribute <sch:value-of select="S100FC:code"/>: textPattern used with <sch:value-of select="S100FC:valueType"/> datatype
      </sch:assert>
      <sch:assert test="count(S100FC:constraints/S100CD:stringLength) = 0">
        Attribute <sch:value-of select="S100FC:code"/>: stringLength used with <sch:value-of select="S100FC:valueType"/> datatype
      </sch:assert>
    </sch:rule>
  </sch:pattern>
  
  <!-- Use or non-use of Precision types -->
  <sch:pattern fpi="S100_5.2.5-A.16_precision" id="S100.5.2.5-A16.PRECISION">
    <sch:title>Report use of precision with non-numeric datatypes</sch:title>
    <sch:rule context="/S100FC:S100_FC_FeatureCatalogue/S100FC:S100_FC_SimpleAttributes/S100FC:S100_FC_SimpleAttribute[(S100FC:valueType != 'real') and (S100FC:valueType != 'integer')]" id="S100.5.2.5-A16.PRECISION.R1" role="warning">
      <sch:assert test="count(S100FC:constraints/S100CD:precision) = 0">
        Attribute <sch:value-of select="S100FC:code"/>: precision used with <sch:value-of select="S100FC:valueType"/> datatype
      </sch:assert>
    </sch:rule>
    <sch:rule context="/S100FC:S100_FC_FeatureCatalogue/S100FC:S100_FC_SimpleAttributes/S100FC:S100_FC_SimpleAttribute[(S100FC:valueType = 'real')]" id="S100.5.2.5-A16.PRECISION.R2" role="warning">
      <sch:assert test="count(S100FC:constraints/S100CD:precision) = 1">
        Attribute <sch:value-of select="S100FC:code"/>: precision not used with 'real' datatype
      </sch:assert>
    </sch:rule>
  </sch:pattern>
  
  <!-- Constraints used with enumeration or codelist types-->
  <sch:pattern fpi="S100_5.2.5-A.16_constraints" id="S100.5.2.5-A16.CONSTRAINTS">
    <sch:title>Report use of constraints with enumeration, codelist, or boolean datatypes</sch:title>
    <sch:rule context="/S100FC:S100_FC_FeatureCatalogue/S100FC:S100_FC_SimpleAttributes/S100FC:S100_FC_SimpleAttribute[(S100FC:valueType = 'enumeration') or (S100FC:valueType = 'S100_CodeList') or (S100FC:valueType = 'boolean')]" id="S100.5.2.5-A16.CONSTRAINTS.R1" role="warning">
      <sch:assert test="count(S100FC:constraints) = 0">
        Attribute <sch:value-of select="S100FC:code"/>: constraints used with <sch:value-of select="S100FC:valueType"/> datatype
      </sch:assert>
    </sch:rule>
  </sch:pattern>
  
</sch:schema>