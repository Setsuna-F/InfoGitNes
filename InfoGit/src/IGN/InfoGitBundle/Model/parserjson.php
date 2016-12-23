<?php

namespace IGN\InfoGitBundle\Model;
use IGN\InfoGitBundle\Model\Commit;
use IGN\InfoGitBundle\Model\Branche;


class parserjson {
    private $url        = "";
    private $nbUsers    = 0;
    private $nbCommits  = 0;
    private $nbBranches = 0;
    private $person     = array();
    private $branches   = array();

    private $parsed_json;

    public function init($url){
        $this->url = $url;

        /* au code java avec l'url */

        $json = file_get_contents("../../InfoGitJson/branche_1.json");
        $this->parsed_json = json_decode($json);
        $this->url = $this->parsed_json->{'url'};
        $this->nbUsers = $this->parsed_json->{'nbUsers'};
        $this->nbCommits = $this->parsed_json->{'nbCommits'};
        $this->nbBranches = $this->parsed_json->{'nbBranches'};
        $this->person = $this->parsed_json->{'person'};
    }


    public function htable($person_temp){
        /*for($i = 0; $i < count($person_temp); ++$i) {
            $this->person[$person_temp[0]]=;



            //$person[$i]['salt'] = mt_rand(000000, 999999);
        }*/
    }


    public function getAllCommit(){
        for($i = 0; $i < count($this->parsed_json->branches); ++$i) {
            $this->branches[$i]=new Branche(
                $this->parsed_json->branches[$i]->branchName,
                new Commit($this->parsed_json->branches[$i]->nbCommits,0,0,0,0),
                count($this->parsed_json->branches[$i]->tousLesNomDUtilisateur)
            );
        }
        return $this->branches;
    }



    function __construct($url) {
        $this->init($url);
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
    public function getPerson(){
        return $this->person;
    }
    public function getParsed_json(){
        return $this->parsed_json;
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
