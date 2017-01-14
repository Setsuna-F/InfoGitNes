<%-- 
    Document   : trainAdvenced
    Created on : 12 avr. 2016, 10:58:13
    Author     : niels
--%>

<%@include file="head.jsp" %>
    <body>
        <%@include file="topMenu.jsp" %>
        <form method="post" action="<c:url value="/train-data-advenced"/>" data-abide novalidate >
            
            <div class="zone column-row"> 
                <div class="row medium-centered" >
                    <h3>Data</h3>
                    <c:if test="${classification == 'Cross'}">
                        <label>number folds: 
                            <input class="small-3" type="number" pattern="wholeNumber" name="Data.nbFolds" max="10" min="1" value="${folds}" required>
                        </label>
                    </c:if> 
                </div>
                <div class="row medium-centered" >
                    <h3>Ngrams</h3>
                    <label>minimum: 
                        <input class="small-3" type="number" pattern="wholeNumber" name="Ngrams.min" max="3" min="1" value="1" required>
                    </label>
                    <label>maximum: 
                        <input class="small-3" type="number" pattern="wholeNumber" name="Ngrams.max" max="3" min="1" value="1" required>
                    </label>
                </div>
            </div>
            
            <div class="zone">
                <div class="row medium-centered">
                <h3>Preprocessings</h3>
            
                <p>lowercase</p>
                <div class="switch">
                    <input class="switch-input" id="yes-no1" type="checkbox" checked name="lowercase">
                    <label class="switch-paddle" for="yes-no1">
                        <span class="show-for-sr">lowercase</span>
                        <span class="switch-active" aria-hidden="true">Yes</span>
                        <span class="switch-inactive" aria-hidden="true">No</span>
                    </label>
                </div>
            
                <p>lemmatize</p>
                <div class="switch">
                    <input class="switch-input" id="yes-no2" type="checkbox" checked name="lemmatize">
                    <label class="switch-paddle" for="yes-no2">
                        <span class="show-for-sr">lemmatize</span>
                        <span class="switch-active" aria-hidden="true">Yes</span>
                        <span class="switch-inactive" aria-hidden="true">No</span>
                    </label>
                </div>
            
                <p>remove stop words</p>
                <div class="switch">
                    <input class="switch-input" id="yes-no3" type="checkbox" checked name="removeStopWords">
                    <label class="switch-paddle" for="yes-no3">
                        <span class="show-for-sr">remove stop words</span>
                        <span class="switch-active" aria-hidden="true">Yes</span>
                        <span class="switch-inactive" aria-hidden="true">No</span>
                    </label>
                </div>
            
                <p>normalize slang</p>
                <div class="switch">
                    <input class="switch-input" id="yes-no4" type="checkbox" name="normalizeSlang">
                    <label class="switch-paddle" for="yes-no4">
                        <span class="show-for-sr">normalize slang</span>
                        <span class="switch-active" aria-hidden="true">Yes</span>
                        <span class="switch-inactive" aria-hidden="true">No</span>
                    </label>
                </div>
            
                <p>normalize hyperlinks</p>
                <div class="switch">
                    <input class="switch-input" id="yes-no5" type="checkbox" checked name="normalizeHyperlinks">
                    <label class="switch-paddle" for="yes-no5">
                        <span class="show-for-sr">normalize hyperlinks</span>
                        <span class="switch-active" aria-hidden="true">Yes</span>
                        <span class="switch-inactive" aria-hidden="true">No</span>
                    </label>
                </div>
            
                <p>normalize emails</p>
                <div class="switch">
                    <input class="switch-input" id="yes-no6" type="checkbox" name="normalizeEmails">
                    <label class="switch-paddle" for="yes-no6">
                        <span class="show-for-sr">normalize emails</span>
                        <span class="switch-active" aria-hidden="true">Yes</span>
                        <span class="switch-inactive" aria-hidden="true">No</span>
                    </label>
                </div>
            
                <p>replace pseudonyms</p>
                <div class="switch">
                    <input class="switch-input" id="yes-no7" type="checkbox" checked name="replacePseudonyms">
                    <label class="switch-paddle" for="yes-no7">
                        <span class="show-for-sr">replace pseudonyms</span>
                        <span class="switch-active" aria-hidden="true">Yes</span>
                        <span class="switch-inactive" aria-hidden="true">No</span>
                    </label>
                </div>
            </div>
            
            <div class="row medium-centered" >
                <h3>Lexicons</h3>
                <div class="row align-spaced">
                    <p>feel polarity</p>
                    <div class="switch">
                        <input class="switch-input" id="yes-no8" type="checkbox"  name="feelPol">
                        <label class="switch-paddle" for="yes-no8">
                            <span class="show-for-sr">feel polarity</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                        </label>
                    </div>
                    <p>polarimots polarity</p>
                    <div class="switch">
                        <input class="switch-input" id="yes-no9" type="checkbox"  name="polarimotsPol">
                        <label class="switch-paddle" for="yes-no9">
                            <span class="show-for-sr">fpolarimots polarity</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                      </label>
                    </div>
                    <p>affects polarity</p>
                    <div class="switch">
                        <input class="switch-input" id="yes-no10" type="checkbox"  name="affectsPol">
                        <label class="switch-paddle" for="yes-no10">
                            <span class="show-for-sr">affects polarity</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                        </label>
                    </div>
                    <p>diko polarity</p>
                    <div class="switch">
                        <input class="switch-input" id="yes-no11" type="checkbox"  name="dikoPol">
                        <label class="switch-paddle" for="yes-no11">
                            <span class="show-for-sr">diko polarity</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                        </label>
                    </div>
                    <p>feel emoticon</p>
                    <div class="switch">
                        <input class="switch-input" id="yes-no12" type="checkbox"  name="feelEmo">
                        <label class="switch-paddle" for="yes-no12">
                            <span class="show-for-sr">feel emoticon</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                        </label>
                    </div>
                    <p>affects emoticon</p>
                    <div class="switch">
                        <input class="switch-input" id="yes-no13" type="checkbox"  name="affectsEmo">
                        <label class="switch-paddle" for="yes-no13">
                            <span class="show-for-sr">affects emoticon</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                        </label>
                    </div>
                    <p>diko emoticon</p>
                    <div class="switch">
                        <input class="switch-input" id="yes-no14" type="checkbox"  name="dikoEmo">
                        <label class="switch-paddle" for="yes-no14">
                            <span class="show-for-sr">diko emoticon</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                        </label>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="zone">
            <div class="row large-centered " >
                <h3>Syntactic Features</h3>
                <div class="row align-spaced">
                    <p>count capitalizations</p>
                    <div class="switch">
                        <input class="switch-input" id="yes-no15" type="checkbox"  name="countCapitalizations">
                        <label class="switch-paddle" for="yes-no15">
                            <span class="show-for-sr">count capitalizations</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                        </label>
                    </div>
                    <p>count elongated words</p>
                    <div class="switch">
                        <input class="switch-input" id="yes-no16" type="checkbox"  name="countElongatedWords">
                        <label class="switch-paddle" for="yes-no16">
                            <span class="show-for-sr">count elongated words</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                      </label>
                    </div>
                    <p>count hashtags</p>
                    <div class="switch">
                        <input class="switch-input" id="yes-no17" type="checkbox"  name="countHashtags">
                        <label class="switch-paddle" for="yes-no17">
                            <span class="show-for-sr">count hashtags</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                        </label>
                    </div>
                    <p>count negators</p>
                    <div class="switch">
                        <input class="switch-input" id="yes-no18" type="checkbox"  name="countNegators">
                        <label class="switch-paddle" for="yes-no18">
                            <span class="show-for-sr">count negators</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                        </label>
                    </div>
                    <p>presence smileys</p>
                    <div class="switch">
                        <input class="switch-input" id="yes-no19" type="checkbox"  name="presenceSmileys">
                        <label class="switch-paddle" for="yes-no19">
                            <span class="show-for-sr">presence smileys</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                        </label>
                    </div>
                    <p>presence punctuation</p>
                    <div class="switch">
                        <input class="switch-input" id="yes-no20" type="checkbox"  name="presencePunctuation">
                        <label class="switch-paddle" for="yes-no20">
                            <span class="show-for-sr">presence punctuation</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                        </label>
                    </div>
                    <p>presence part of speech tags</p>
                    <div class="switch">
                        <input class="switch-input" id="yes-no21" type="checkbox"  name="presencePartOfSpeechTags">
                        <label class="switch-paddle" for="yes-no21">
                            <span class="show-for-sr">presence part of speech tags</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                        </label>
                    </div>
                </div>
            </div>
            <div class="row  align-stretch" >
                <h3>Feature Selection</h3>
                <div class="switch">
                        <input class="switch-input" id="yes-no22" type="checkbox" checked name="perform">
                        <label class="switch-paddle" for="yes-no22">
                            <span class="show-for-sr">perform</span>
                            <span class="switch-active" aria-hidden="true">Yes</span>
                            <span class="switch-inactive" aria-hidden="true">No</span>
                        </label>
                    </div>
            </div>
        </div>
        <div class="zone">
            <div class="row medium-centered " >
                <h3>Generate the configuration file</h3>
                <p>If in doubt , you can use the "reset" button to return to the default configuration.</p>
                <input  type="submit" value="Send" class="hollow button">
                <input value="true" name="config" hidden>
                <input value="${fileId}" name="fileId" hidden>
                <input value="${fileName}" name="fileName" hidden>
                <input type="reset" name="reset" value="Reset" class="hollow warning button">
            </div>
        </div>
    </form>
        
    <%@include file="foot.jsp" %>
    <%@include file="foundation.jsp" %>
    </body>
</html>
