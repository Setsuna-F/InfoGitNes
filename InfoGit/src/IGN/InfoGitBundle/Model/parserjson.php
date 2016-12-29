<?php

namespace IGN\InfoGitBundle\Model;
use IGN\InfoGitBundle\Model\Commit;
use IGN\InfoGitBundle\Model\Branche;


class parserjson {
    private $url        = "";
    private $nbUsers    = 0;
    private $nbCommits  = 0;
    private $nbBranches = 0;
    //private $person     = array();
    private $branches   = array();
    private $allPerson  = array();
    private $branchesByContributor  = array();
    private $commitsList=array();


    private $parsed_json;

    public function init(){
        //$json = file_get_contents("../../InfoGitJar/branches/branche_1.json");
        $json = file_get_contents("branches/branche_1.json");
        $this->parsed_json = json_decode($json);
        $this->url = $this->parsed_json->{'url'};
        $this->nbUsers = $this->parsed_json->{'nbUsers'};
        $this->nbCommits = $this->parsed_json->{'nbCommits'};
        $this->nbBranches = $this->parsed_json->{'nbBranches'};
        //$this->person = $this->parsed_json->{'person'};
        $this->allPerson = $this->parsed_json->personName;
    }

    public function mostActif(){
        $commits_tmp = $this->getCommitsByAllContributor();
        $bestCommits = $commits_tmp;
        sort($bestCommits);
        $bestCommits = array_unique($bestCommits);
        $maxValues = array_slice($bestCommits, -2, 2);

        $maxVal=array();
        foreach($commits_tmp as $key => $val){
            if($val===$maxValues[0] || $val===$maxValues[1])
                $maxVal[$key] = $commits_tmp[$key];
        }

        return $maxVal;
    }

    public function getCommitsByAllContributor(){
        $personlist = $this->parsed_json->{'personName'};
        $countCommits=array();
        for($i = 0; $i < count($this->parsed_json->branches); ++$i) { //pour toutes les branches
            for($j = 0; $j < count($personlist); ++$j) {//pour tous les contributeurs
                if(isset($this->parsed_json->branches[$i]->commits->{$personlist[$j]})){//si le contributeur se trouve dans la branche
                    if(isset($countCommits[$personlist[$j]])) { //Si on a deja recupere le combre de commit d'un contributeur donné
                        $countCommits[$personlist[$j]] = $countCommits[$personlist[$j]] + count($this->parsed_json->branches[$i]->commits->{$personlist[$j]}) ; //Alors on ajoute l'ancienne valeur a la nouvelle
                    }
                    else{
                        $countCommits[$personlist[$j]] = count($this->parsed_json->branches[$i]->commits->{$personlist[$j]}) ; //Sinon on ajoute pour la premiere foi le nombre de commit d'un utilisateur donné.
                    }
                }
            }
        }
        return $countCommits;
    }

    public function getNbCommitsByContributor($contributor){
        $nb=0;
        $personlist = $this->parsed_json->{'personName'};
        $countCommits=array();
        for($i = 0; $i < count($this->parsed_json->branches); ++$i) { //pour toutes les branches
            if(isset($this->parsed_json->branches[$i]->commits->{$contributor})){
                    $nb=$nb + count($this->parsed_json->branches[$i]->commits->{$contributor}) ;
            }
        }

        return $nb;
    }
    public function getNbAddByContributor($contributor){
        $nb=0;
        $personlist = $this->parsed_json->{'personName'};
        $countCommits=array();
        for($i = 0; $i < count($this->parsed_json->branches); ++$i) { //pour toutes les branches
            if(isset($this->parsed_json->branches[$i]->commits->{$contributor})){
                for($j = 0; $j < count($this->parsed_json->branches[$i]->commits->{$contributor}); ++$j) { //pour toutes les branches
                    //$commits[]= $this->parsed_json->branches[$i]->commits->{$contributor}[$j];
                    $nb=$nb + $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbAdd'};
                }
            }
        }

        return $nb;
    }
    public function getNbRemoveByContributor($contributor){
        $nb=0;
        $personlist = $this->parsed_json->{'personName'};
        $countCommits=array();
        for($i = 0; $i < count($this->parsed_json->branches); ++$i) { //pour toutes les branches
            if(isset($this->parsed_json->branches[$i]->commits->{$contributor})){
                for($j = 0; $j < count($this->parsed_json->branches[$i]->commits->{$contributor}); ++$j) { //pour toutes les branches
                    //$commits[]= $this->parsed_json->branches[$i]->commits->{$contributor}[$j];
                    $nb=$nb + $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbDelete'};
                }
            }
        }

        return $nb;
    }
    public function getNbRenameByContributor($contributor){
        $nb=0;
        $personlist = $this->parsed_json->{'personName'};
        $countCommits=array();
        for($i = 0; $i < count($this->parsed_json->branches); ++$i) { //pour toutes les branches
            if(isset($this->parsed_json->branches[$i]->commits->{$contributor})){
                for($j = 0; $j < count($this->parsed_json->branches[$i]->commits->{$contributor}); ++$j) { //pour toutes les branches
                    //$commits[]= $this->parsed_json->branches[$i]->commits->{$contributor}[$j];
                    $nb=$nb + $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbRename'};
                }
            }
        }

        return $nb;
    }
    public function getNbModifyByContributor($contributor){
        $nb=0;
        $personlist = $this->parsed_json->{'personName'};
        $countCommits=array();
        for($i = 0; $i < count($this->parsed_json->branches); ++$i) { //pour toutes les branches
            if(isset($this->parsed_json->branches[$i]->commits->{$contributor})){
                for($j = 0; $j < count($this->parsed_json->branches[$i]->commits->{$contributor}); ++$j) { //pour toutes les branches
                    //$commits[]= $this->parsed_json->branches[$i]->commits->{$contributor}[$j];
                    $nb=$nb + $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbModify'};
                }
            }
        }

        return $nb;
    }


    public function getContributors(){
        return $this->parsed_json->personName;
    }

    public function getMailsByContributor($contributor){
        return $this->parsed_json->person->{$contributor};
    }

    public function getAllCommit(){
        for($i = 0; $i < count($this->parsed_json->branches); ++$i) {
            $this->branches[$i]=new Branche(
                $this->parsed_json->branches[$i]->branchName,
                new Commit($this->parsed_json->branches[$i]->nbCommits,
                            0,
                            0,
                            0,
                            0),
                count($this->parsed_json->branches[$i]->tousLesNomDUtilisateur)
            );
        }
        return $this->branches;
    }


    public function getInfoCommitsByBrancheAndContributor($branch, $contributor){
        $nbCmt=0;
        $nbAdd=0;
        $nbRmv=0;
        $nbMdy=0;
        $nbRnm=0;
        for($i = 0; $i < count($this->parsed_json->branches); ++$i) {
            if($this->parsed_json->branches[$i]->branchName === $branch){
                if(isset($this->parsed_json->branches[$i]->commits->{$contributor})){
                    for($j = 0; $j < count($this->parsed_json->branches[$i]->commits->{$contributor}); ++$j) { //pour toutes les branches
                        $nbCmt++;
                        $nbAdd=$nbAdd + $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbAdd'};
                        $nbRmv=$nbRmv + $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbDelete'};
                        $nbMdy=$nbMdy + $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbModify'};
                        $nbRnm=$nbRnm + $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbRename'};
                    }
                }
            }
        }
        return new Commit($nbCmt, $nbAdd, $nbRmv, $nbRnm, $nbMdy);
    }

public function getBranchesByContributor($contributor){
    for($i = 0; $i < count($this->parsed_json->branches); ++$i) {
        if(isset($this->parsed_json->branches[$i]->commits->{$contributor})){
            //echo $this->parsed_json->branches[$i]->commits->{$contributor}[0];
            $this->branchesByContributor[$i]=new Branche(
                                                $this->parsed_json->branches[$i]->branchName,
                                                $this->getInfoCommitsByBrancheAndContributor($this->parsed_json->branches[$i]->branchName, $contributor),
                                                count($this->parsed_json->branches[$i]->tousLesNomDUtilisateur)
                                            );
        }
    }
    return $this->branchesByContributor;
}


    public function getInfoCommitsByContributor($contributor){
        $nbCmt=0;
        $nbAdd=0;
        $nbRmv=0;
        $nbMdy=0;
        $nbRnm=0;

        $index=0;
        for($i = 0; $i < count($this->parsed_json->branches); ++$i) {
            if(isset($this->parsed_json->branches[$i]->commits->{$contributor})){
                for($j = 0; $j < count($this->parsed_json->branches[$i]->commits->{$contributor}); ++$j) { //pour toutes les branches
                    $this->commitsList[] = new Commit(
                        $index++,
                        $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbAdd'},
                        $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbDelete'},
                        $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbRename'},
                        $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbModify'}
                    );
                }
            }
        }
        return $this->commitsList;
    }


        public function getCommitsListByBrancheAndByContributor($branch, $contributor){
            $nbCmt=0;
            $nbAdd=0;
            $nbRmv=0;
            $nbMdy=0;
            $nbRnm=0;

            $index=0;
            for($i = 0; $i < count($this->parsed_json->branches); ++$i) {
                if($this->parsed_json->branches[$i]->branchName === $branch){
                    if(isset($this->parsed_json->branches[$i]->commits->{$contributor})){
                        for($j = 0; $j < count($this->parsed_json->branches[$i]->commits->{$contributor}); ++$j) { //pour toutes les branches
                            $this->commitsList[] = new Commit(
                                $index++,
                                $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbAdd'},
                                $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbDelete'},
                                $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbRename'},
                                $this->parsed_json->branches[$i]->commits->{$contributor}[$j]->{'nbModify'}
                            );
                        }
                    }
                }
            }
            return $this->commitsList;
        }

        public function getBranchNameWithFormatedName($contributor, $fname){
            $branches = $this->getBranchesByContributor($contributor);
            for($i = 0; $i < count($branches); ++$i) {
                if(preg_replace('#/?.+/(.+)/?#i', '$1', $branches[$i]->getName()) === $fname)
                    return $branches[$i]->getName();
            }
            return "SOKA";
        }




    /* ----------------- CONTRUCTOR/GETTER/SETTER ---------------------- */
    function __construct() {
        $this->init();
    }

    public function getUrl(){
        return $this->url;
    }
    public function getNbUsers(){
        return $this->nbUsers;
    }
    public function getNbCommits(){
        return $this->nbCommits;
    }
    public function getNbBranches(){
        return $this->nbBranches;
    }
    /*public function getPerson(){
        return $this->person;
    }*/
    public function getParsed_json(){
        return $this->parsed_json;
    }
    public function getAllPerson(){
        return $this->allPerson;
    }
    public function getBranchesByUser(){
        return $this->branchesByContributor;
    }
    public function getCommitsList(){
        return $this->commitsList;
    }
}

class Branche{
    private $commits;
    private $name = "";
    private $nbUser=0;
    function __construct($name, $commit, $nbUser) {
        $this->name    = $name;
        $this->commits = $commit;
        $this->nbUser  = $nbUser;
    }

    public function getCommits(){
        return $this->commits;
    }
    public function getName(){
        return $this->name;
    }
    public function getNbUsers(){
        return $this->nbUser;
    }
    public function getFormatedName(){
        return preg_replace('#/?.+/(.+)/?#i', '$1', $this->name);
    }
}

class Commit{
    private $commits = 0;
    private $add = 0;
    private $del = 0;
    private $rnm = 0;
    private $mdf = 0;


    function __construct($commits, $add, $del, $rnm, $mdf) {
        $this->commits = $commits;
        $this->add = $add;
        $this->del = $del;
        $this->rnm = $rnm;
        $this->mdf = $mdf;
    }

    public function getNbCommits(){
        return $this->commits;
    }

    public function getNbAdd(){
        return $this->add;
    }

    public function getNbDel(){
        return $this->del;
    }

    public function getNbRnm(){
        return $this->rnm;
    }

    public function getNbMdf(){
        return $this->mdf;
    }
}
