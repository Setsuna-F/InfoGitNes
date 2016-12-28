/* ----------------------------------------    JS pour les couleurs des contributeurs */

function nb_aleatoire(min, max){
     var nb = min + (max-min+1)*Math.random();
     return Math.floor(nb);
}
function randomColor(opacity){
   return "rgba("+nb_aleatoire(0, 255)+", "+nb_aleatoire(0, 255)+", "+nb_aleatoire(0, 255)+", ";//+opacity+")";
}
function getRealColor(color, opacity){
    return color+opacity+")";
}
