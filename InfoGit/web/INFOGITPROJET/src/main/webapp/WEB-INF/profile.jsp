<%-- 
    Document   : profile
    Created on : 14 févr. 2016, 17:17:06
    Author     : Niels
--%>

<%@include file="head.jsp" %>
    <body>
        
        <%@include file="topMenu.jsp" %>
        <div class="zone">
            <c:if test="${info != null}">
                    <div class="row medium-8 large-7 columns">
                        <div class="blog-post">
                            <div class="large-12 large-centered large-5 large-centered columns">
                                <div large large-centered large-5 large-centered columns >
                                    <div class="${classe} callout primary">
                                        <h5>Information:</h5>
                                        <p>${info}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
            </c:if>
            <div class="row medium-8 large-7 columns">
                <div class="blog-post">
                    <div class="medium-7 medium-centered large-5 large-centered columns">

                        <h1>Profile</h1>

                        <c:choose>
                            <c:when test="${edit == 1}">
                                <form  method="post" action="<c:url value="/profile"/>" data-abide novalidate>
                                    <div data-abide-error class="alert callout" style="display: none;">
                                        <p><i class="fi-alert"></i> There are some errors in your form.</p>
                                    </div>
                                    <input name="change" value="settings" hidden>
                                    <div class="row collapse">
                                        <div class="small-2  columns">
                                            <span class="prefix"><i class="fi-torso"></i></span>
                                        </div>
                                        <div class="small-10  columns">
                                            <input  pattern="text" type="text" name="Lname" value="${sessionScope.nom}" required>
                                        </div>
                                    </div>

                                    <div class="row collapse">
                                        <div class="small-2  columns">
                                            <span class="prefix"><i class="fi-torso"></i></span>
                                        </div>
                                        <div class="small-10  columns" >
                                            <input pattern="text" type="text" name="Fname" value="${sessionScope.prenom}" required>
                                        </div>
                                    </div>

                                    <div class="row collapse">
                                        <div class="small-2 columns">
                                            <span class="prefix"><i class="fi-mail"></i></span>
                                        </div>
                                        <div class="small-10  columns">
                                            <input pattern="eMail" type="text" name="Mail" value="${sessionScope.mail}" required>
                                        </div>
                                    </div>
                                    <button type="submit" class="alert hollow button">Send</button>
                                </form>
                                <form  method="get" action="<c:url value="/profile"/>">
                                    <input value="cancel" name="edit" hidden>
                                    <button type="submit" class="hollow button">Cancel</button>
                                </form> 
                            </c:when>

                            <c:when test="${edit == 2}">
                                <form  method="post" action="<c:url value="/profile"/>">
                                    <input name="change" value="password" hidden>
                                    <div class="row collapse">
                                        <div class="small-2 columns ">
                                            <span class="prefix"><i class="fi-lock"></i></span>
                                        </div>
                                        <div class="small-10 columns ">
                                            <input  name="pass" type="password" value="" required>
                                        </div>
                                    </div>
                                    <div class="row collapse">
                                        <div class="small-2 columns ">
                                            <span class="prefix"><i class="fi-lock"></i></span>
                                        </div>
                                        <div class="small-10 columns ">
                                            <input name="rePass" type="password" value="" required>
                                        </div>
                                    </div>
                                    <button class="alert hollow button">Send</button>
                                </form>
                                <form  method="get" action="<c:url value="/profile"/>">
                                    <input value="cancel" name="edit" hidden>
                                    <button type="submit" class="hollow button">Cancel</button>
                                </form>    
                            </c:when>

                            <c:otherwise>
                                <div class="row collapse">
                                    <div class="small-2  columns">
                                        <span class="prefix"><i class="fi-torso"></i></span>
                                    </div>
                                    <div class="small-10  columns">
                                        <input type="text" value="${sessionScope.nom}" disabled="disabled">
                                    </div>
                                </div>

                                <div class="row collapse">
                                    <div class="small-2  columns">
                                        <span class="prefix"><i class="fi-torso"></i></span>
                                    </div>
                                    <div class="small-10  columns" >
                                        <input type="text" value="${sessionScope.prenom}" disabled="disabled">
                                    </div>
                                </div>

                                <div class="row collapse">
                                    <div class="small-2 columns">
                                        <span class="prefix"><i class="fi-mail"></i></span>
                                    </div>
                                    <div class="small-10  columns">
                                        <input type="text" value="${sessionScope.mail}"disabled="disabled">
                                    </div>
                                </div>

                                <div class="row collapse">
                                    <div class="small-2 columns ">
                                        <span class="prefix"><i class="fi-lock"></i></span>
                                    </div>
                                    <div class="small-10 columns ">
                                        <input type="password" value="Realy?" disabled="disabled">
                                    </div>
                                </div>
                                    <form  method="get" action="<c:url value="/profile"/>">
                                        <input value="settings" name="edit" hidden>
                                        <button type="submit" class="hollow button">Change setting</button>
                                    </form>
                                    <form  method="get" action="<c:url value="/profile"/>">
                                        <input value="password" name="edit" hidden>
                                        <button type="submit" class="warning hollow button">Change password</button>
                                    </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    <%@include file="foot.jsp" %>
    <%@include file="foundation.jsp" %>
    </body>
</html>
