/* ----------------------------------------    JS pour le sidebar */
$("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("active");
});



/* ----------------------------------------   DEBUT JS pour l'incrementation auto */
var resultNodeNbContributeur = document.getElementById("resultNbContributeur");
var counter448 = 0;

var interval448 = setInterval(function() {
    if(resultNodeNbContributeur){
       resultNodeNbContributeur.innerHTML=counter448;
        if(counter448 > 448) {
            clearInterval(interval448);
            resultNodeNbContributeur.innerHTML = 448;
        }
        counter448++;
    }}, 0)


var resultNodeNbBranches = document.getElementById("resultNbBranches");
var counter63 = 0;

var interval63 = setInterval(function() {
    if(resultNodeNbBranches){
       resultNodeNbBranches.innerHTML=counter63;
        if(counter63 > 63) {
            clearInterval(interval63);
            resultNodeNbBranches.innerHTML = 63;
        }
        counter63++;
    }}, 0)


var resultNodeNbCommits = document.getElementById("resultNbCommits");
var counter1710670 = 0;

var interval1710670 = setInterval(function() {
    if(resultNodeNbCommits){
       resultNodeNbCommits.innerHTML=counter1710670;
        if(counter1710670 > 1710670) {
            clearInterval(interval1710670);
            resultNodeNbCommits.innerHTML = 1710670;
        }
        counter1710670+=2050;counter1710670++;
    }}, 0)
/* ----------------------------------------   FIN JS pour l'incrementation auto */
