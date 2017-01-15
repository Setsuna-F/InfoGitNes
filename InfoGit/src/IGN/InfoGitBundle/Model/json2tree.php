<?php

namespace IGN\InfoGitBundle\Model;


class json2tree {
    private $parsed_json;

     public function init(){
        $this->parsed_json = json_decode(file_get_contents("branches/infos.json"));
     }

    public function getFiles()
    {
        $this->init();
        return $this->parsed_json;
    }
    public function getNbLines()
    {
    	$compteur = 0;
    	$json = $this->getFiles();
    	foreach ($json as $key => $value) {
            foreach ($value as $key => $value) {
             
                if ($key == "line") {
                    $compteur += $value;
                }
  
            }
        }
        return $compteur;
    }
}