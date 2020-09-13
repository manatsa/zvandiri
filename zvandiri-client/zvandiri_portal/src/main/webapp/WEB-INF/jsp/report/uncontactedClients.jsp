<%@include file="../template/header.jspf"%>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">${pageTitle}</div>
        <div class="panel-body">
            <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To
                Reports DashBoard Home</a><br />
            <%@include file="../template/searchClientFragment.jspf"%>
    </div>
</div>
<%@include file="../template/footer.jspf"%>
<script type="text/javascript">
    $(".sidebar-nav").addClass("custom-side-bar-ref");
    $("#page-wrapper").addClass("main-wrp");
    // ensire toggle side bar is pointing right
    $("span.toggle-span").addClass("fa-long-arrow-right");
    $("span.toggle-span").removeClass("fa-long-arrow-left");
</script>
