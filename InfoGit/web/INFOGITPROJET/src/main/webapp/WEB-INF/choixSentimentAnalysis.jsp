<%-- 
    Document   : choixSentimentAnalysis
    Created on : 4 mars 2016, 14:07:42
    Author     : Elsa Martel
--%>

<%@include file="head.jsp" %>

    <body>
        <%@include file="topMenu.jsp" %>
        <div class="zone">
            <table >
                <!--thead>
                  <tr>
                    <th width="200">Type of analysis</th>
                    <th>Description</th>
                    <th width="150">Number of models</th>
                    <th width="150">Table Header</th>
                  </tr>
                </thead!-->
                <tbody>
                  <tr class="blue-hover">
                    <td><i class="fi-social-twitter"></i> French tweets polarity</td>
                    <td>${tweetPolarity}</td>
                    <td><form method="post" action="<c:url value="/AffichageModeleExistant"/>">
                            <input value="FrenchTweetsPolarity" name="typeAnalysis" hidden>
                            <input type="submit" value="Classify" class="button" />
                        </form>
                    </td>
                  </tr>
                  <tr class="blue-hover">
                    <td><i class="fi-social-twitter"></i> French  tweets subjectivity</td>
                    <td>${tweetSubjectivity}</td>
                    <td><form method="post" action="<c:url value="/AffichageModeleExistant"/>">
                            <input value="FrenchTweetsSubjectivity" name="typeAnalysis" hidden>
                            <input type="submit" value="Classify" class="button" />
                        </form>
                    </td>
                  </tr>
                  <tr class="blue-hover">
                    <td><i class="fi-social-twitter"></i> French  tweets emotions</td>
                    <td>${tweetEmotion}</td>
                    <td><form method="post" action="<c:url value="/AffichageModeleExistant"/>">
                            <input value="FrenchTweetsEmotions" name="typeAnalysis" hidden>
                            <input type="submit" value="Classify" class="button" />
                        </form>
                    </td>
                  </tr>
                  <tr class="blue-hover">
                    <td><i class="fi-video"></i> French product reviews</td>
                    <td>${productReviews}</td>
                    <td><form method="post" action="<c:url value="/AffichageModeleExistant"/>">
                            <input value="FrenchProductReviews" name="typeAnalysis" hidden>
                            <input type="submit" value="Classify" class="button" />
                        </form>
                    </td>
                  </tr>
                  <tr class="blue-hover">
                    <td><i class="fi-book"></i> French videos games</td>
                    <td>${videosGames}</td>
                    <td><form method="post" action="<c:url value="/AffichageModeleExistant"/>">
                            <input value="FrenchVideosGames" name="typeAnalysis" hidden>
                            <input type="submit" value="Classify" class="button" />
                        </form>
                    </td>
                  </tr>
                  <tr class="blue-hover">
                    <td><i class="fi-torsos-all"></i> French parlementary debates</td>
                    <td>${parlementaryDebates}</td>
                    <td><form method="post" action="<c:url value="/AffichageModeleExistant"/>">
                            <input value="FrenchParlementaryDebates" name="typeAnalysis" hidden>
                            <input type="submit" value="Classify" class="button" />
                        </form>
                    </td>
                  </tr>
                </tbody>
            </table>
        </div>
        <%@include file="foot.jsp" %>
        <%@include file="foundation.jsp" %>
    </body>
</html>
