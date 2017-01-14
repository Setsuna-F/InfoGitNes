<%-- 
    Document   : SaisieTexteTweet
    Created on : 5 févr. 2016, 15:17:31
    Author     : Elsa Martel
--%>
<%@include file="head.jsp" %>

    <body>
        <jsp:include page="topMenu.jsp">
            <jsp:param name="topMenuName" value="${topMenuName}"/>
        </jsp:include>
        
        <jsp:include page="Breadcrumbs.jsp">
            <jsp:param name="breadcrumbs" value="${breadcrumbs}"/>
        </jsp:include>
        <div class="row medium-8 large-7 columns">
            <p>Welcom to the ${title} of the web sit!</p> 
            
            <ul class="tabs" data-tabs id="example-tabs">
                <li class="tabs-title is-active"><a href="#panel1" aria-selected="true">Write text</a></li>
                <li class="tabs-title"><a href="#panel2">Upload file</a></li>
            </ul>
            
            <div class="tabs-content" data-tabs-content="example-tabs">
                <div class="tabs-panel is-active" id="panel1">
                    <form method="post" action="<c:url value="/affichageAPITweet"/>">
                        <div class="row">
                            <div class="large-12 columns">
                                <label>Write your tweet : 
                                    <textarea id="tweetAAnalyser" name="tweetAAnalyser" placeholder=""></textarea>
                                </label>
                            </div>
                        </div>
                        <input value="saisieTexte" name="choix" hidden>
                        <input type="submit" value="Submit" class="button" />
                    </form>
                </div>
                <div class="tabs-panel" id="panel2">
                    <form method="post" action="<c:url value="/affichageAPITweet"/>" enctype="multipart/form-data">
                        <p>Write a tweet for line and skip a line. The format of the text is .txt. </p>
                        <label for="fileUpload" class="button">Upload File</label>
                        <input type="file" id="fileUpload" name="fileUpload" class="show-for-sr">
                        <input value="uploadFile" name="choix" hidden>
                        <input type="submit" value="Submit" class="button" />
                    </form>
                </div>
            </div>
        </div>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>

        <%@include file="foot.jsp" %>
        <%@include file="foundation.jsp" %>
    </body>
</html>
