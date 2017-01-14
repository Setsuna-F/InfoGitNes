<%-- 
    Document   : affichageModeleExistant
    Created on : 4 mars 2016, 14:15:39
    Author     : Elsa Martel
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
                <p>${titre}</p> 

                <ul class="tabs" data-tabs id="example-tabs">
                    <li class="tabs-title"><a href="#panel1">Description</a></li>
                    <li class="tabs-title is-active"><a href="#panel2" aria-selected="true">Use Model</a></li>
                </ul>

                <div class="tabs-content" data-tabs-content="example-tabs">
                    <div class="tabs-panel" id="panel1">
                        <div>
                            <h3>Description </h3>
                            <p>${description}</p>
                        </div>
                        <br/>
                        <div>
                            <hr>
                            <h3>Global</h3>
                            <p><b>Sample : ${root.getSample()}</b></p>
                            <div class="row medium-uncollapse large-uncollapse">
                                <div class="small-6 columns">
                                    <p>Micro precision</p>
                                    <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                        <span class="progress-meter" style="width: ${root.getMicroprecision()}">
                                            <p class="progress-meter-text">${root.getMicroprecision()}</p>
                                        </span>
                                    </div>    
                                    <p>Micro recall</p>
                                    <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                        <span class="progress-meter" style="width: ${root.getMicrorecall()}">
                                            <p class="progress-meter-text">${root.getMicrorecall()}</p>
                                        </span>
                                    </div>   
                                    <p>Micro f-measure</p>
                                    <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                        <span class="progress-meter" style="width: ${root.getMicrofmeasure()}">
                                            <p class="progress-meter-text">${root.getMicrofmeasure()}</p>
                                        </span>
                                    </div>
                                </div>
                                <div class="small-6 columns">
                                    <p>Macro precision</p>
                                    <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                        <span class="progress-meter" style="width: ${root.getMacroprecision()}">
                                            <p class="progress-meter-text">${root.getMacroprecision()}</p>
                                        </span>
                                    </div>
                                    <p>Macro recall</p>
                                    <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                        <span class="progress-meter" style="width: ${root.getMacrorecall()}">
                                            <p class="progress-meter-text">${root.getMacrorecall()}</p>
                                        </span>
                                    </div>
                                    <p>Macro f-measure</p>
                                    <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                        <span class="progress-meter" style="width: ${root.getMacrofmeasure()}">
                                            <p class="progress-meter-text">${root.getMacrofmeasure()}</p>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br/>
                        <div>
                            <hr>
                            <h3>Classes</h3>
                            <ul class="vertical menu" data-accordion-menu>
                                <c:forEach var="c" items="${classe}">
                                    <li>
                                        <a href="#">${c.getClasse()}</a>
                                        <ul class="menu vertical nested">
                                            <p><b>Sample : ${c.getSample()}</b></p>
                                            <p>F-measure</p>
                                            <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                                <span class="progress-meter" style="width: ${c.getFmeasure()}">
                                                    <p class="progress-meter-text">${c.getFmeasure()}</p>
                                                </span>
                                            </div>
                                            <p>Precision</p>
                                            <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                                <span class="progress-meter" style="width: ${c.getPrecision()}">
                                                    <p class="progress-meter-text">${c.getPrecision()}</p>
                                                </span>
                                            </div>
                                            <p>Recall</p>
                                            <div class="progress" role="progressbar" tabindex="0" aria-valuenow="20" aria-valuemin="0" aria-valuetext="25 percent" aria-valuemax="100">
                                                <span class="progress-meter" style="width: ${c.getRecall()}">
                                                    <p class="progress-meter-text">${c.getRecall()}</p>
                                                </span>
                                            </div>
                                        </ul>                                
                                    </li>
                                </c:forEach>
                            </ul>
                            <br/>
                            <p>You can download the results in xml file.</p>
                            <a href="download?folder=XML&filename=${fichierXML}" target="_blank">Click here to download file</a>
                        </div>
                    </div>
                    <div class="tabs-panel is-active" id="panel2">
                        <div>
                            <h3>Write text</h3>
                            <form method="post" action="<c:url value="/AffichageModeleExistant"/>">
                                <div class="row">
                                    <div class="large-12 columns">
                                        <label>Write your tweet : 
                                            <textarea id="tweetAAnalyser" name="tweetAAnalyser" placeholder=""></textarea>
                                        </label>
                                    </div>
                                </div>
                                <input value="saisieTexte" name="choix" hidden>
                                <input value="${typeAnalysis}" name="typeAnalysis" hidden>
                                <input type="submit" value="Classify" class="button" />
                            </form>
                            <c:if test="${erreur == 1 }">
                                <p>
                                    <c:out value="${tweet[0]}"/> 
                                    <span class="info label"> <c:out value="${tweet[1]}"/></span>                                 
                                    <br/>
                                </p>
                            </c:if>
                        </div>
                        <br/>
                        <br/>
                        <div>
                            <hr>
                            <h3>Upload file</h3>
                            <form method="post" action="<c:url value="/AffichageModeleExistant"/>" enctype="multipart/form-data">
                                <p>Write a tweet per line. The format of the text is .txt. The encoding is UTF-8.</p>
                                <label for="fileUpload" class="button">Upload File</label>
                                <input type="file" id="fileUpload" name="fileUpload" class="show-for-sr">
                                <input value="uploadFile" name="choix" hidden>
                                <input value="${typeAnalysis}" name="typeAnalysis"  hidden>
                                <input type="submit" value="Classify" class="button" id="submit"/>
                            </form>  
                            <c:if test="${erreur == 2}">
                                <p>
                                    <p>You can download the results in json file.</p>
                                    <a href="download?folder=fichiers&filename=${fichierJSON}" target="_blank">Click here to download file</a>
                                    <br/>
                                    <br/>
                                </p>
                            </c:if>
                            ${information}
                         </div>
                    </div>
                </div>
            </div>
            </div>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>       
            
            <script>
               
                document.getElementById("submit").onclick = function(){
                   var opts = {
                        lines: 13 // The number of lines to draw
                      , length: 28 // The length of each line
                      , width: 14 // The line thickness
                      , radius: 42 // The radius of the inner circle
                      , scale: 1 // Scales overall size of the spinner
                      , corners: 1 // Corner roundness (0..1)
                      , color: '#000' // #rgb or #rrggbb or array of colors
                      , opacity: 0.10 // Opacity of the lines
                      , rotate: 0 // The rotation offset
                      , direction: 1 // 1: clockwise, -1: counterclockwise
                      , speed: 1 // Rounds per second
                      , trail: 80 // Afterglow percentage
                      , fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
                      , zIndex: 2e9 // The z-index (defaults to 2000000000)
                      , className: 'spinner' // The CSS class to assign to the spinner
                      , top: '50%' // Top position relative to parent
                      , left: '50%' // Left position relative to parent
                      , shadow: true // Whether to render a shadow
                      , hwaccel: false // Whether to use hardware acceleration
                      , position: 'absolute' // Element positioning
                      }
                      var target = document.getElementById('main')
                      var spinner = new Spinner(opts).spin(target);
                };
                

    
            $(window).on('mouseover', (function () {
                window.onbeforeunload = null;

            }));
            $(window).on('mouseout', (function () {
                window.onbeforeunload = ConfirmLeave;

            }));
            function ConfirmLeave() {
                
                return "";
            }
            var prevKey="";
            $(document).keydown(function (e) {            
                if (e.key=="F5") {
                    window.onbeforeunload = ConfirmLeave;
                }
                else if (e.key.toUpperCase() == "W" && prevKey == "CONTROL") {                
                    window.onbeforeunload = ConfirmLeave;   
                }
                else if (e.key.toUpperCase() == "R" && prevKey == "CONTROL") {
                    window.onbeforeunload = ConfirmLeave;
                }
                else if (e.key.toUpperCase() == "F4" && (prevKey == "ALT" || prevKey == "CONTROL")) {
                    window.onbeforeunload = ConfirmLeave;
                }
                prevKey = e.key.toUpperCase();
            });

            </script>
            <%@include file="foot.jsp" %>
            <%@include file="foundation.jsp" %>
        </c:if>
    </body>
</html>
