<%-- 
    Document   : trainForm
    Created on : 5 avr. 2016, 19:18:27
    Author     : Niels
--%>

<%@include file="head.jsp" %>
    <body>
        <%@include file="topMenu.jsp" %>
        <div class="zone">
            <form method="post" action="<c:url value="/train-data"/>" data-abide novalidate enctype="multipart/form-data">
                <div class="row medium-centered">
                    <h4>Train data for ${fileName}</h4>
                    <div data-abide-error class="alert callout" style="display: none;">
                        <p><i class="fi-alert"></i> There are some errors in your form.</p>
                    </div>
                        
                    <label for="fileUpload">Train Data 
                        <label for="fileUpload" class="button">Upload file in arff</label>
                        <input type="file" id="fileUpload" name="fileUpload" class="show-for-sr" required>
                    </label>
                    
                    <input type="radio" name="classification" value="Cross" id="Cross" checked="checked"><label for="Cross">Cross Validation</label>
                    <label>Folds
                        <select id="select" class="small-5" name="folds">
                            <option value="10">10</option>
                            <option value="5">5</option>
                            <option value="3">3</option>
                        </select>
                    </label>

                    <input type="radio" name="classification" value="Test" id="Test"><label for="Test">Test data</label>
                    <label for="fileUpload2" class="button">Upload file</label>
                    <input type="file" id="fileUpload2" name="fileUpload2" class="show-for-sr">          
                   
                    <fieldset>
                        <legend>Mode</legend>
                            
                        <input type="radio" name="mode" value="text" id="text" checked="checked">
                        <label for="text">Default free text 
                            <span data-tooltip aria-haspopup="true" class="has-tip" data-disable-hover="false" tabindex="1" title="Default configuration for free text."><i class=" fi-info "></i></span>
                        </label>
                         
                        <br />
                           
                        <input type="radio" name="mode" value="tweet" id="tweet" >
                        <label for="tweet">Default tweet 
                            <span data-tooltip aria-haspopup="true" class="has-tip" data-disable-hover="false" tabindex="1" title="Default configuration for tweet text."><i class=" fi-info "></i></span>
                        </label>
                            
                        <br />
                          
                        <input type="radio" name="mode" value="advenced" id="advenced">
                        <label for="advenced">Advenced</label>
                            
                        <br />
                            
                    </fieldset>
                        
                    <input value="${fileId}" name="fileId" hidden>
                    <input value="${fileName}" name="fileName" hidden>
                    <input type="submit" value="Next" class="hollow button">
                   
                </div>
            </form>
        </div>  
        
    <%@include file="foot.jsp" %>
    <%@include file="foundation.jsp" %>
    </body>
</html>
