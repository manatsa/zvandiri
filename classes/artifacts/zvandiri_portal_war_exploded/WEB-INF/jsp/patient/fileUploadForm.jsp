<%@include file="../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <%@include file="../template/message.jspf" %>
                <div class="row">
                    <div class="col-lg-10">
                        <div class="panel panel-default">                            
                            <%@include file="../template/dashboard/patientProfile.jspf" %>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-10">
                        <form method="post"  enctype="multipart/form-data">
                            <input type="hidden" name="patient" value="${patient.id}"/>
                            <div class="form-group">
                                <label>Upload profile photo</label><br/>
                                <input type="text" name="photo" />
                                <input type="file" name="file">
                                <p class="help-block">
                                    
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Upload consent form</label><br/>
                                <input type="text" name="consent" />
                                <input type="file" name="file">
                                <p class="help-block">
                                    
                                </p>
                            </div>                                
                            <div class="form-group">
                                <label>Upload M-health consent form photo</label><br/>
                                <input type="text" name="mhealth" />
                                <input type="file" name="file">
                                <p class="help-block">
                                    
                                </p>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Upload Files</button>
                                <a href="${itemDelete}"><button class="btn btn-primary" type="button">Cancel</button></a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>