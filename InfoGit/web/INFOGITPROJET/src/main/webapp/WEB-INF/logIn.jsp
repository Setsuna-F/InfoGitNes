<%-- 
    Document   : logIn
    Created on : 9 févr. 2016, 15:06:25
    Author     : Niels
--%>

<%@include file="head.jsp" %>
    <body>
        <%@include file="topMenu.jsp" %>
        <div class="zone">
            <div class="row medium-8 large-7 columns">
                <c:choose>
                    <c:when test="${polarity == 0}"> 
                        <div class="callout alert">
                            <h5 class="text-center">Oups!</h5>
                            <p class="text-center">${info}</p>
                        </div>
                    </c:when>
                    <c:when test="${polarity == 1}">
                        <div class="callout success">
                            <h5 class="text-center">Super!</h5>
                            <p class="text-center">${info}</p>
                        </div>
                    </c:when>
                </c:choose>
                <div class="blog-post">
                    <div class="medium-7 medium-centered large-5 large-centered columns">
                        <form method="post" action="<c:url value="/logIn"/>" data-abide novalidate >
                            <div data-abide-error class="alert callout" style="display: none;">
                                <p><i class="fi-alert"></i> There are some errors in your form.</p>
                            </div>
                            <div class="row column log-in-form">
                                <h4 class="text-center">Log in with your email account</h4>
                                <label>Email
                                    <input pattern="email" name="mail" id="mail" type="text" placeholder="somebody@example.com" required>
                                </label>
                                <label>Password
                                    <input  name="pass" id="pass" type="password" placeholder="Password" required >
                                </label>
                                <input type="submit" value="Login!" class="button" />  
                                <button type="button" class="success hollow button" onClick="window.location.href='<c:url value="/logOn"/>';">need to log on?</button>
                                <button type="button" class="warning hollow button">Forgot your password?</button>
                            </div>   
                        </form>
                    </div>
                </div>
            </div>
        </div>
    <%@include file="foot.jsp" %>
    <%@include file="foundation.jsp" %>
    </body>
</html>