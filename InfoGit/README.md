# InfoGitNes
Projet d'evolution et restructuration sur git

----------------------

### Projet en 2 partie:
Tapez la ligne de commande suivante pour cloner le projet.
    `git clone https://github.com/Setsuna-F/InfoGitNes.git`

#### Partie Java
Parcours et recuperation des information d'un projet git.

#### Partie Symfony 3 
Creation d'un site de type dashboard dans le but d'examiner et d'analyser un projet git.

----------------------

### Pour lancer le projet sur symfony 3
Il faut:
- avoir php >= 5.3 d'installer.
- taper la ligne de commande suivante:<br />
    `$ composer install`<br />
    `$ php bin/console cache:clear --env=prod`<br />
    `$ php bin/console cache:clear`<br />
- puis lancer votre navigateur prefere (Safari) pour accéder au site.<br/>
    `http://localhost:8888/`

- s'il y a un probleme avec les graphiques, alors verifier les dossier dans web/assets/vendor/node_modules/... Si le dossier est manquant alors faire la ligne de commande suivante.<br/>
    `$ npm install chart.js --save`