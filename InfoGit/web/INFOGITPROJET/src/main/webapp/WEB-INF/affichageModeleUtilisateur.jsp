<%-- 
    Document   : affichageModeleUtilisateur
    Created on : 17 avr. 2016, 17:11:01
    Author     : Niels
--%>

<%@include file="head.jsp" %>

    <body>
        <%@include file="topMenu.jsp" %>
        <c:if test="${ sessionScope.isLog != 1}">
            <%@include file="forbidden.jsp" %>
        </c:if>
        <c:if test="${ sessionScope.isLog == 1}">
            <div class="zone" id="main">
            <div class="row medium-8 large-12 columns" >
                
                        <div>
                           
                            <h3>Global</h3>
                            <p><b>Sample : root</b></p>
                            <div class="row medium-uncollapse large-uncollapse">
                                <div class="small-6 columns">
                                    <p>Micro precision</p>
                                    <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                        <span class="progress-meter" style="width: ${microprecision}%">
                                            <p class="progress-meter-text">${microprecision}</p>
                                        </span>
                                    </div>
                                    <p>Micro recall</p>
                                    <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                        <span class="progress-meter" style="width: ${microrecall}%">
                                            <p class="progress-meter-text">${microrecall}</p>
                                        </span>
                                    </div>   
                                    <p>Micro f-measure</p>
                                    <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                        <span class="progress-meter" style="width: ${microfmeasure}%">
                                            <p class="progress-meter-text">${microfmeasure}</p>
                                        </span>
                                    </div>
                                </div>
                                <div class="small-6 columns">
                                    <p>Macro precision</p>
                                    <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                        <span class="progress-meter" style="width: ${macroprecision}%">
                                            <p class="progress-meter-text">${macroprecision}</p>
                                        </span>
                                    </div>
                                    <p>Macro recall</p>
                                    <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                        <span class="progress-meter" style="width: ${macrorecall}%">
                                            <p class="progress-meter-text">${macrorecall}</p>
                                        </span>
                                    </div>
                                    <p>Macro f-measure</p>
                                    <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                        <span class="progress-meter" style="width: ${macrofmeasure}%">
                                            <p class="progress-meter-text">${macrofmeasure}</p>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                            <br/>
                            <p>You can download the results in xml file.</p>
                            <a href="download?folder=${path}&filename=result.xml" target="_blank"><i class=" fi-download"></i> Click here to download file</a>
                            <hr>
                            <a href="<c:url value="/manage"/>"><i class=" fi-arrow-left"></i> Back to your models</a>
                        </div>
                        
                    </div>
                   
                </div>
            </div>
            <%@include file="foot.jsp" %>
            <%@include file="foundation.jsp" %>
        </c:if>
    </body>
</html>