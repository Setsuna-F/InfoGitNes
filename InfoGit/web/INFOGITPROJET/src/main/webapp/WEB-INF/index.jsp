<%-- 
    Document   : index
    Created on : 2 févr. 2016, 20:13:36
    Author     : Niels
--%>
<%@include file="head.jsp" %>
    <body>
      <%@include file="topMenu.jsp" %>
      <div class="zone">
        <div class="row medium-8 large-7 columns">
            <div class="blog-post">
                <h3>French text analysis.</h3>
                <br />
                <h4>The best performing analysis Tweet in French!</h4>
                
                <P>This web platform allows you to perform sentiment classification of pieces of text. 
                    You can use the available classification models or build your own models using tagged datasets. 
                    The available models have been learned on <a href="https://deft.limsi.fr/2015/">DEFT 2015 benchmarks</a>.</p> 
                <p>In order to learn your own models, you have to upload tagged data and choose between a default mode and an advanced mode. 
                    In the default mode, the features and parameters are predefined according to the text nature, while in the advanced mode, 
                    you have to set the different features and methods.</p>
                
               
        </div>
      </div>
        <%@include file="foot.jsp" %>    
        <%@include file="foundation.jsp" %>
    </body>
</html>
