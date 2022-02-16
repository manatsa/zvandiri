<%@include file="../template/header.jspf" %>
<form:form commandName="item">
    <legend>${pageTitle}</legend> 
    <div>
        <table class="table table-bordered" width="100%">
            <tr>
                <td>
                    <a href="${page}/admin/province/">Manage Regions</a>
                </td>  
                <td>
                    <a href="${page}/admin/district/">Manage District</a> 
                </td>  
            </tr>
            <tr>
                <td>
                    <a href="${page}/admin/support-group/">Manage Support Groups</a>
                </td>  
                <td>
                    <a href="${page}/admin/relationship/">Manage Relationship</a> 
                </td>  
            </tr>
            <tr>
                <td>
                    <a href="${page}/admin/refer/">Manage Referer</a>
                </td>  
                <td>
                    <a href="${page}/admin/orphan-status/">Manage Orphan Status</a> 
                </td>  
            </tr>
            <tr>
                <td>
                    <a href="${page}/admin/education/">Manage Education</a>
                </td>  
                <td>
                    <a href="${page}/admin/education-level/">Manage Education Level</a> 
                </td>  
            </tr>
            <tr>
                <td>
                    <a href="${page}/admin/location/">Manage Place of Contact</a>
                </td>  
                <td>
                    <a href="${page}/admin/position/">Manage Contacted By</a> 
                </td>  
            </tr>
            <tr>
                <td>
                    <a href="${page}/admin/settings/">Manage System Settings</a>
                </td>  
                <td>
                    <a href="${page}/admin/internal-referral/">Manage Internal Referral</a> 
                </td>  
            </tr>
            <tr>
                <td>
                    <a href="${page}/admin/external-referral/">Manage External Referral</a>
                </td>  
                <td>
                    <a href="${page}/admin/chronic-infection/">Manage Chronic Infection</a>
                </td>  
            </tr>
            <tr>
                <td>
                    <a href="${page}/admin/stable/">Manage Standard Followup</a>
                </td>  
                <td>
                    <a href="${page}/admin/enhanced/">Manage Enhanced Followup</a> 
                </td>  
            </tr>
            <tr>
                <td>
                    <a href="${page}/admin/intensive/">Manage Intensive Followup</a>
                </td>  
                <td>
                    <a href="${page}/admin/services-referred/">Manage Services Referred</a> 
                </td>  
            </tr>
            <tr>
                <td>
                    <a href="${page}/admin/hiv-co-infection/">Manage HIV co-Infections</a>
                </td>  
                <td>
                    <a href="${page}/admin/mental-health/">Manage Mental Health</a> 
                </td>  
            </tr>
            <tr>
                <td>
                    <a href="${page}/admin/substance/">Manage Substance</a>
                </td>  
                <td>
                    <a href="${page}/admin/mental-health/">Manage Mental Health</a> 
                </td>  
            </tr>
            <tr>
                <td>
                    <a href="${page}/admin/user/">Manage System User</a> 
                </td>
                <td>
                    <a href="${page}/admin/facility/">Manage Facility</a>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="${page}/admin/disability-category/">Manage Disability Category</a> 
                </td>
                <td>
                    <a href="${page}/admin/assessment/">Manage Assessment Item Category</a> 
                </td>
            </tr>
            <tr>
                <td>
                    <a href="${page}/admin/arv-medicine/">Manage ARV Medicine</a> 
                </td>
                <td>
                    <a href="${page}/admin/hosp-cause/">Manage Hospitalisation Cause</a> 
                </td>
            </tr>
            <tr>
                <td><a href="${page}/admin/action-taken/">Manage Action Taken</a> </td>
                <td>
                    <a href="${page}/admin/reason-for-not-reaching-olevel/">Manage Reason For Not Reaching O-Level</a> 
                </td>
            </tr>
            <tr>
                <td><a href="${page}/admin/service-offered/">Manage Service Offered</a> </td>
                <td><a href="${page}/admin/lab-service/">Manage Lab Task</a> </td>
            </tr>
        </table>
    </div>
    <div>
    </div>
</form:form>
<%@include file="../template/footer.jspf" %>