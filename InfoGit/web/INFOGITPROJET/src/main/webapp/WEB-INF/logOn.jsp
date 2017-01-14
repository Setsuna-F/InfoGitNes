<%-- 
    Document   : logon
    Created on : 13 févr. 2016, 10:32:15
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
                    <c:otherwise>
                        <div class="callout">
                            <h5 class="text-center">Information</h5>
                            <p class="text-center">Please use a password with at last 5 characters</p>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div class="blog-post">
                    <div class="medium-7 medium-centered large-5 large-centered columns">
                        <form method="post" action="<c:url value="/logOn"/>" data-abide novalidate>
                            <div data-abide-error class="alert callout" style="display: none;">
                                <p><i class="fi-alert"></i> There are some errors in your form.</p>
                            </div>
                            <div class="row column log-on-form">
                                <h4 class="text-center">Register</h4>
                                <label>First name
                                    <input pattern="text" name="Fname" id="Fname" type="text" placeholder="First name" required>
                                </label>
                                <label>Last name
                                    <input pattern="text" name="Lname" id="Lname" type="text" placeholder="Last name" required>
                                </label>
                                <label>Email
                                    <input pattern="email" name="mail" id="mail" type="text" placeholder="somebody@example.com" required>
                                </label>
                                <label>Password
                                    <input  name="pass" id="pass" type="password" placeholder="Password" required >
                                </label>
                                <label>Password
                                    <input  name="rePass" id="rePass" type="password" placeholder="Password" required >
                                </label>
                                <p>I accept the <u>Terms and Conditions</u></p>
                                <div class="switch small">
                                    <input class="switch-input" id="yes-no" type="checkbox" name="Switch" required="true">
                                    <label class="switch-paddle" for="yes-no">
                                        <span class="show-for-sr">I accept the <u>Terms and Conditions</u></span>
                                        <span class="switch-inactive" aria-hidden="true">No</span>
                                        <span class="switch-active" aria-hidden="true">Yes</span>
                                    </label>  
                                </div>
                                <input type="submit" value="Register!" class="button" />  
                                <button class="hollow button" type="button" data-toggle="example-dropdown" data-open="logIn">Already a member?</button>
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