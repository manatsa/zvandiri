<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <title>${pageTitle}</title>
        <!-- external libs from cdnjs -->
        <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" /> " rel="stylesheet">
        <link href="<c:url value="/resources/css/plugins/metisMenu/metisMenu.min.css" /> " rel="stylesheet">
        <link href="<c:url value="/resources/css/sb-admin-2.css" /> " rel="stylesheet">
        <link href="<c:url value="/resources/font-awesome-4.1.0/css/font-awesome.min.css" /> " rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.11/c3.min.css">
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.11/c3.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

        <!-- PivotTable.js libs from ../dist -->
        <link href="<c:url value="/resources/pivot/pivot.min.css" /> " rel="stylesheet">
        <link href="<c:url value="/resources/jquery-ui-1.11.4/jquery-ui.min.css" /> " rel="stylesheet">
        <script src="<c:url value="/resources/pivot/pivot.min.js" /> "></script>
        <script src="<c:url value="/resources/pivot/c3_renderers.min.js" /> "></script>
        <script src="<c:url value="/resources/datatables/js/jquery.dataTables.min.js" /> "></script>
        <script src="<c:url value="/resources/jquery-ui-1.11.4/jquery-ui.min.js" /> "></script>
        <script src="<c:url value="/resources/datatables/bootstrap/3/dataTables.bootstrap.js" /> "></script>
        <script src="<c:url value="/resources/script/plugins/metisMenu/metisMenu.min.js" /> "></script>
        <script src="<c:url value="/resources/script/plugins/jquery-validate/jquery.validate.js" /> "></script>
        <script src="<c:url value="/resources/script/sb-admin-2.js" /> "></script>
        <style>
            body {font-family: Verdana;}

            .c3-line, .c3-focused {stroke-width: 3px !important;}
            .c3-bar {stroke: white !important; stroke-width: 1;}
            .c3 text { font-size: 12px; color: grey;}
            .tick line {stroke: white;}
            .c3-axis path {stroke: grey;}
            .c3-circle { opacity: 1 !important; }
        </style>

        <!-- optional: mobile support with jqueryui-touch-punch -->
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui-touch-punch/0.2.3/jquery.ui.touch-punch.min.js"></script>
    </head>
    <body>
        <c:set var="page" value="${pageContext.request.contextPath}"/>
        <script type="text/javascript">
            var beneficiary = "<c:out value="${yearRanges.beneficiary}"/>";
            var caregiver = "<c:out value="${yearRanges.caregiver}"/>";
            var general = "<c:out value="${yearRanges.general}"/>";
            var path = "<c:out value="${page}"/>";
        </script>
        <div id="wrapper">
            <!-- Navigation -->
            <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header col-lg-12">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <!--<a class="navbar-brand" style="" href="${page}/index.htm">-->
                    <span style="color:#fff; font-weight: bold; font-size: 24px;">
                        ${pageTitle}
                    </span>    
                    <!--</a>-->
                    <ul id="user-settings" class="nav navbar-top-links navbar-right">
                        <%@include file="../../template/usersettings.jspf" %>
                    </ul>
                </div>

                <!-- /.navbar-top-links -->
                <%@include file="../../template/sidebarnavigation.jspf" %>
            </nav>
            <div id="page-wrapper"><br/>
                <a title="Toggle side bar" href="#" id="toggle-rem-side-bar"><span class="toggle-span fa fa-long-arrow-left"> &nbsp;</span></a>
                <form:form commandName="item" role="form">
                    <table class="table">
                        <tbody>
                            <tr>
                                <td>
                                    <div class="form-group">
                                        <label>Region</label>
                                        <form:select path="province" class="form-control">
                                            <form:option value="" label="--Select Item--"/>
                                            <form:options items="${provinces}" itemLabel="name" itemValue="id"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="province"/>
                                        </p>
                                    </div> 
                                </td>
                                <td>
                                    <div class="form-group">
                                        <label>District</label>
                                        <form:select path="district" class="form-control">
                                            <form:option value="" label="--Select Item--"/>
                                            <form:options items="${districts}" itemLabel="name" itemValue="id"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="district"/>
                                        </p>
                                    </div> 
                                </td>
                                <td>
                                    <div class="form-group">
                                        <label>Start Date (Date Joined)</label>
                                        <form:input path="startDate" class="form-control general"/>
                                        <p class="help-block">
                                            <form:errors path="startDate"/>
                                        </p>
                                    </div> 
                                </td>
                                <td>
                                    <div class="form-group">
                                        <label>End Date (Date Joined)</label>
                                        <form:input path="endDate" class="form-control general"/>
                                        <p class="help-block">
                                            <form:errors path="endDate"/>
                                        </p>
                                    </div> 
                                </td>
                                <td>
                                    <div class="form-group">
                                        <label>&nbsp;</label><br/>
                                        <button class="btn btn-primary" type="submit">Search</button>
                                    </div> 
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </form:form>
                <script type="text/javascript">
                    $(function () {
                        var renderers = $.extend($.pivotUtilities.renderers, $.pivotUtilities.c3_renderers);
                        var searchParams = {
                            "province": $("#province").val(),
                            "district": $("#district").val(),
                            "startDate": $("#startDate").val(),
                            "endDate": $("#endDate").val()
                        };
                        $.get(path + "/report/pivot/patient/getpatient", searchParams, function (mps) {
                            $("#output").pivotUI(mps, {
                                renderers: renderers,
                                cols: ["Age Group"], rows: ["Gender"],
                                rendererName: "Bar Chart"
                            });
                        });
                    });
                </script>
                <div id="output" style="margin: 30px;">Please wait report being generated ........</div>
            </div>    
        </div>
        <script src="<c:url value="/resources/script/custom.js" /> "></script>
        <script type="text/javascript">
            $(".sidebar-nav").addClass("custom-side-bar-ref");
            $("#page-wrapper").addClass("main-wrp");
            // ensire toggle side bar is pointing right
            $("span.toggle-span").addClass("fa-long-arrow-right");
            $("span.toggle-span").removeClass("fa-long-arrow-left");
        </script>
    </body>
</html>
