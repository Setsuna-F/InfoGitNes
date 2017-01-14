<%-- 
    Document   : testSecur
    Created on : 11 févr. 2016, 19:07:23
    Author     : niels
--%>

<%@include file="head.jsp" %>
    <body>
        <jsp:include page="topMenu.jsp">
            <jsp:param name="topMenuName" value="${topMenuName}"/>
        </jsp:include>
        
        <jsp:include page="Breadcrumbs.jsp">
            <jsp:param name="breadcrumbs" value="${breadcrumbs}"/>
        </jsp:include>
        <c:if test="${ sessionScope.isLog != 1}">
            <%@include file="forbidden.jsp" %>
        </c:if>
        <c:if test="${ sessionScope.isLog == 1}">
            <div class="row medium-8 large-7 columns">
            <div class="blog-post">
                <h1>Page de test</h1>
                <p>Welcom to the ${title} of the web sit!</p>
                <p>${isLog}</p>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
            </div>
        </div>
                <%@page import="analysedesentiments.AnalyseDeSentiments"%>
        <% AnalyseDeSentiments a = new AnalyseDeSentiments();
        out.println(a.start("coucou"));%>
        
        </c:if>
        
        <%@include file="foot.jsp" %>
        <%@include file="foundation.jsp" %>
    </body>
</html>
