<%-- 
    Document   : affichageAPITweet
    Created on : 9 févr. 2016, 13:31:02
    Author     : Elsa Martel
--%>

<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@include file="head.jsp" %>

    <body>
        <jsp:include page="topMenu.jsp">
            <jsp:param name="topMenuName" value="${topMenuName}"/>
        </jsp:include>
        
        <jsp:include page="Breadcrumbs.jsp">
            <jsp:param name="breadcrumbs" value="${breadcrumbs}"/>
        </jsp:include>
        <div class="row medium-8 large-7 columns">
            <ul class="tabs" data-tabs id="example-tabs">
                <li class="tabs-title is-active"><a href="#panel1" aria-selected="true">API</a></li>
                <li class="tabs-title"><a href="#panel2">Description</a></li>
                <li class="tabs-title"><a href="#panel3">Statistics</a></li>
            </ul>

            <div class="tabs-content" data-tabs-content="example-tabs">
                <div class="tabs-panel is-active" id="panel1">
                    <h4>${message}</h4>
                    <c:if test="${ !erreur}">
                        <p>
                            <p>You can download the results in json file.</p>
                            <button class="info button" name="download" id="download">Download</button>
                            <br/>
                            <br/>
                            <ul>
                                <c:forEach var="t" items="${tweet}">
                                    <li>
                                        <c:out value="${t.key}"/> 
                                        <c:if test="${t.value.equals('+')}" >
                                            <span class="success label"><i class="fi-plus"></i> Positif</span>
                                        </c:if>
                                        <c:if test="${t.value.equals('-')}" >
                                            <span class="alert label"><i class="fi-minus"></i> Negatif</span>
                                        </c:if>
                                        <c:if test="${t.value.equals('=')}" >
                                            <span class="info label"><i class="fi-list"></i> Neutre</span>
                                        </c:if>
                                    </li>
                                    <br/>
                                </c:forEach>
                            </ul>
                        </p>
                    </c:if>
                </div>
                <div class="tabs-panel" id="panel2">
                    <p>${description}</p>
                </div>
                <div class="tabs-panel" id="panel3">
                    <p>You can download the results in xml file.</p>
                    <a href="download?filename=tweet.xml" target="_blank">Click here to download file</a>
                    <br/>
                    <br/>
                    <ul class="vertical menu" data-accordion-menu>
                        <li>
                            <a href="#">Root</a>
                            <ul class="menu vertical nested">
                                <p>Micro f-measure</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${root.getMicrofmeasure()}">
                                        <p class="progress-meter-text">${root.getMicrofmeasure()}</p>
                                    </span>
                                </div>
                                <p>Macro f-measure</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${root.getMacrofmeasure()}">
                                        <p class="progress-meter-text">${root.getMacrofmeasure()}</p>
                                    </span>
                                </div>
                                <p>Micro precision</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${root.getMicroprecision()}">
                                        <p class="progress-meter-text">${root.getMicroprecision()}</p>
                                    </span>
                                </div>
                                <p>Macro precision</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${root.getMacroprecision()}">
                                        <p class="progress-meter-text">${root.getMacroprecision()}</p>
                                    </span>
                                </div>
                                <p>Micro recall</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${root.getMicrorecall()}">
                                        <p class="progress-meter-text">${root.getMicrorecall()}</p>
                                    </span>
                                </div>
                                <p>Macro recall</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${root.getMacrorecall()}">
                                        <p class="progress-meter-text">${root.getMacrorecall()}</p>
                                    </span>
                                </div>
                            </ul>
                        </li>
                        <li>
                            <a href="#">Positive</a>
                            <ul class="menu vertical nested">
                                <p>F-measure</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${positive.getFmeasure()}">
                                        <p class="progress-meter-text">${positive.getFmeasure()}</p>
                                    </span>
                                </div>
                                <p>Precision</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${positive.getPrecision()}">
                                        <p class="progress-meter-text">${positive.getPrecision()}</p>
                                    </span>
                                </div>
                                <p>Recall</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${positive.getRecall()}">
                                        <p class="progress-meter-text">${positive.getRecall()}</p>
                                    </span>
                                </div>
                            </ul>
                        </li>
                        <li>
                            <a href="#">Neutre</a>
                            <ul class="menu vertical nested">
                                <p>F-measure</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${neutre.getFmeasure()}">
                                        <p class="progress-meter-text">${neutre.getFmeasure()}</p>
                                    </span>
                                </div>
                                <p>Precision</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${neutre.getPrecision()}">
                                        <p class="progress-meter-text">${neutre.getPrecision()}</p>
                                    </span>
                                </div>
                                <p>Recall</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${neutre.getRecall()}">
                                        <p class="progress-meter-text">${neutre.getRecall()}</p>
                                    </span>
                                </div>
                            </ul>
                        </li>
                        <li>
                            <a href="#">Negative</a>
                            <ul class="menu vertical nested">
                                <p>F-measure</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${negative.getFmeasure()}">
                                        <p class="progress-meter-text">${negative.getFmeasure()}</p>
                                    </span>
                                </div>
                                <p>Precision</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${negative.getPrecision()}">
                                        <p class="progress-meter-text">${negative.getPrecision()}</p>
                                    </span>
                                </div>
                                <p>Recall</p>
                                <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                    <span class="progress-meter" style="width: ${negative.getRecall()}">
                                        <p class="progress-meter-text">${negative.getRecall()}</p>
                                    </span>
                                </div>
                            </ul>
                        </li>
                      </ul>
                </div>
            </div>
        </div>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
         <%@include file="foot.jsp" %>
        <%@include file="foundation.jsp" %>
    </body>
</html>
