<%-- 
    Document   : manage
    Created on : 11 mars 2016, 13:15:22
    Author     : Niels
--%>

<%@include file="head.jsp" %>
    <body>
        
      <%@include file="topMenu.jsp" %>
      <c:if test="${ sessionScope.isLog != 1}">
            <%@include file="forbidden.jsp" %>
        </c:if>
        <c:if test="${ sessionScope.isLog == 1}">
            
            <c:if test="${erreur != null}">
                    <div class="row medium-8 large-7 columns">
                        <div class="blog-post">
                            <div class="large-12 large-centered large-5 large-centered columns">
                                <div large large-centered large-5 large-centered columns >
                                    <div class="alert callout primary">
                                        <h5>Information:</h5>
                                        <p>${erreur}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
            </c:if>
            <div class="zone" id="main">
                
                <c:choose>  
                    <c:when  test="${tableau != ''}">   
                        <table class="">
                            <thead>
                                <tr>
                                    <th>Model name</th>
                                    <th>Information</th>
                                    <th>Last update</th>
                                    <th>Configure</th>
                                    <th>Train</th>
                                    <th>Delete</th>    
                                </tr>
                            </thead>
                            <tbody>
                                ${tableau}
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <div class="callout primary">
                            <h5>You don't have yet any models.</h5>
                            <p>After your first model creation, your models appear here.</p>
                        </div>
                    </c:otherwise>
                </c:choose> 
                
            </div>
            </div>
            <div class="zone">          
                <div class="blog-post">
                    <form method="post" action="<c:url value="/manage"/>" data-abide novalidate>
                        <div data-abide-error class="alert callout" style="display: none;">
                            <p><i class="fi-alert"></i> There are some errors in your form.</p>
                        </div>
                        <div class="row column log-on-form">
                            <h4 class="text-center">Create a new model</h4>
                            <label>File name
                                <input pattern="text" value="" name="file_name" id="file_name" type="text" placeholder="File name" required>
                            </label>
                            <label>Additional information
                                <textarea id="info" value="" name="info" placeholder="Additional information"></textarea>
                            </label>
                            <input type="submit" value="Create new file" class="button" />  
                        </div>   
                    </form> 
                </div>
            </div> 
            
        </c:if>
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
     </script>
        <%@include file="foot.jsp" %>    
        <%@include file="foundation.jsp" %>
    </body>
</html>
