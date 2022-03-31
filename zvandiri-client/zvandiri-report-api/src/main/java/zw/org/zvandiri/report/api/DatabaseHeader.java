/*
 * Copyright 2017 jmuzinda.
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
package zw.org.zvandiri.report.api;

/**
 * @author jmuzinda
 */
public interface DatabaseHeader {

    public final String[] PATIENT_HEADER = {
            "UIC", "Name", "OI/ Art Number", "Date of Birth", "Age", "Date Of Entry", "Date Joined", "Gender", "Address", "Mobile Number",
            "Consent To M-Health", "Education", "Highest Education",
            "Refer", "Region", "District", "Primary Clinic", "Support Group", "Date Tested", "HIV Disclosure Location",
            "Has Disability", "IS CATS", "In YMM Programme", "In YMD Programme", "HIV Transmission Mode", "Current Regimen",
            "HIV Status Known", "Patient Status", "Date Status Changed", "Last Contact Date", "Current Care Level", "Viral Load", "Date Taken"
    };

    public final String[] UNIQUE_PATIENT_HEADER = {
            "UIC", "Name", "OI/ Art Number", "Date of Birth", "Age", "Date Joined", "Gender", "Address", "Mobile Number",
            "Consent To M-Health", "Education", "Highest Education",
            "Refer", "Region", "District", "Primary Clinic", "Support Group", "Date Tested", "HIV Disclosure Location",
            "Has Disability", "IS CATS", "Is In Young Mum Group", "HIV Transmission Mode",
            "HIV Status Known", "Patient Status", "Date Status Changed"
    };

    public final String[] DEPENDANT_HEADER = {
            "UIC", "Client Name", "Clinet Date of Birth", "Client Age", "Client Gender", "Name", "Region", "District", "Primary Clinic", "Gender",
            "Date Of Birth", "HIV Status", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] OPPORTUNISTIC_INFECTION_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "Infection", "Date Diagnosed",
            "Medication", "Current Status", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] HIV_CO_INFECTION_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "Infection", "Date Diagnosed",
            "Medication", "Resolution", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] MENTAL_HIST_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "Mental Health", "Past", "Current",
            "Received Professional Help", "Professional Help Start Date",
            "Professional Help End Date", "Medication", "Psychiatric Hospitalization", "Description", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] OBSTERIC_HIST_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "Is Pregnant",
            "Currently Breast Feeding", "Currently Pregnant", "Number of ANC Visits", "Gestational Age of First Pregnancy", "Art Started",
            "Number of Children", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] CONTACT_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "Contact Date", "Care Level",
            "Location", "Position", "Reason", "Followup", "Subjective", "Objective", "Plan", "Action Taken",
            "Last Clinic Appointment Date", "Attended Clinic Appointment", "Next Clinic Appointment", "Visit Outcome", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] ASSESSMENT_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "Contact Date",
            "Care Level", "Assessment Type", "Assessment", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] UNCONTACTED_CLIENTS_HEADER = {
            "Client Name", "Date of Birth", "Age", "Gender", "Status", "Address", "Secondary Address", "Mobile Number", "Second Mobile Number", "Region",
            "District", "Primary Clinic", "IS CATS", "In YMM Programme", "In YMD Programme"
//            "Current Care Level","Last Contact Date","LastVL Result",
//            "Last VL Date Taken","Mental Health Risk","Mental Health Risks", "Date Screened"
    };


    public final String[] UNIQUE_CONTACTED_CLIENTS_HEADER = {
            "OI/ART Number", "First Name", "Last Name", "Age", "Gender", "Mobile Number", "Second Mobile Number", "Address", "Secondary Address", "Region",
            "District", "Primary Clinic", "IS CATS", "In YMM Programme", "In YMD Programme"
    };


    public final String[] SOCIAL_HISTORY_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "Live With", "Relationship", "Abused", "Disclosed Abuse",
            "Feel Safe Now", "Abuse Type", "Abuse Outcome", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] SUBSTANCE_USE_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "Substance", "Current", "Past", "Start Date", "End Date",
            "Drug Intervention", "Duration", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] FAMILY_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Orphan Status", "Number of Siblings",
            "IS CATS", "Is In Young Mum Group"
    };

    public final String[] REFERRAL_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "Referral Date", "Expected Visit Date", "Organisation",
            "Designation", "Attending Officer", "Date Attended", "Action Taken",
            "HIV & STI Services Referred", "HIV & STI Services Provided", "OI/ ART Services Referred", "OI/ ART Services Provided", "SRH Services Referred",
            "SRH Services Provided", "Laboratory Services Referred", "Laboratory Services Provided", "TB Services Referred", "TB Services Provided",
            "Psych Services Referred", "Psych Services Provided", "Legal Services Referred", "Legal Services Provided", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] REFERRAL_SPECIFI_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "Referral Date", "Expected Visit Date", "Organisation",
            "Designation", "Attending Officer", "Date Attended", "Action Taken", "Services Referred/Provided", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] CD4_COUNT_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "Test Type", "Date Taken",
            "Count", "Source", "Next Lab Due", "VLSuppressionStatus", "Result Taken", "TND", "Record Source", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] ARV_HISTORY_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "ARV Medicine",
            "Start Date", "End Date", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] MORTALITY_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "Date Of Death",
            "Cause of Death", "Cause of Death Details", "Received Enhanced Care", "Date Put On Enhanced Care", "Case Background",
            "Care Provided", "Home", "Beneficiary", "Facility", "CATS", "ZMs", "Other", "Contact with ZMs", "Date of Contact with ZMs",
            "Description of Case", "Learning Points", "Action Plan", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] TB_IPT_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "Screened for TB",
            "Date Screened", "Indentified with TB", "TB Identification Outcome", "Date Started Treatment",
            "Referral for Sputum", "TB Treatment Outcome", "Referred for IPT", "On IPT", "Date Started IPT", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] LAST_CONTACT_HEADER = {
            "OINumber", "First Name", "Last Name", "Gender", "Date Of Birth", "Date Joined", "Address", "Mobile Number", "Status", "IS CATS", "In YMM Programme", "In YMD Programme",
            "Primary Clinic", "District", "Region", "Last Contact Date", "Last Care Level", "CD4 Count", "VL Result", "Plan"
    };

    public final String[] MENTAL_HEALTH_SCREENING_HEADER = {
            "UIC", "Client Name", "Date of Birth", "Age", "Gender", "Region", "District", "Primary Clinic", "Date Of Entry", "Screened For Mental Health", "Date Screened",
            "Mental health Screening Type", "Risk", "Identified Risks", "Received Support", "Support Received", "Referral",
            "Referrals Received", "Diagnosis", "Diagnosis Done", "Other Diagnosis", "Intervention", "Interventions", "Other Interventions", "IS CATS", "In YMM Programme", "In YMD Programme"
    };
    String[] VLS_CLIENTS_HEADER = {
            "Client Name", "Date of Birth", "Age", "Gender", "Test Result", "Test Type", "Suppression Status", "Entry Date", "Date Taken",
            "Address", "Mobile Phone", "Referrer", "Region", "District", "Primary Clinic", "IS CATS", "In YMM Programme", "In YMD Programme"
    };

    public final String[] CADRE_PHONES_HEADER = {
            "First Name", "Last Name", "Age", "Date of Birth", "Gender", "Cadre Status",
            "Primary Clinic", "District", "Region", "Phone Make", "Phone Model", "Serial Number", "Date Issued", "Date Recovered", "Date Of Entry",
            "Phone Condition", "Phone Status", "IMEI Number 1", "IMEI Number 2", "Phone Number 1", "Phone Number 2", "Phone Issues"
    };

    public final String[] CADRE_BIKES_HEADER = {
            "First Name", "Last Name", "Age", "Date of Birth", "Gender", "Cadre Status",
            "Primary Clinic", "District", "Region", "Bicycle Type", "Date Issued", "Date Recovered", "Date Of Entry",
            "Bicycle Condition", "Bicycle Status", "Bicycle Issues"
    };

    public final String[] CADRE_TOOL_HEADER = {
            "First Name", "Last Name", "Age", "Date of Birth", "Gender", "Cadre Status", "Cadre Type",
            "Primary Clinic", "District", "Region", "Phone Make", "Phone Model", "Date Issued", "Date Recovered", "Date Of Entry",
            "Phone Condition", "Phone Status", "IMEI Number 1", "IMEI Number 2", "Phone Number 1", "Phone Number 2", "Phone Issues",
            "Bicycle Type", "Date Issued", "Date Recovered", "Date Of Entry", "Bicycle Condition", "Bicycle Status", "Bicycle Issues"
    };
}
