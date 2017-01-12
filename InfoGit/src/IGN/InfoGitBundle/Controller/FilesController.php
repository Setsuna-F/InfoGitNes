<?php

namespace IGN\InfoGitBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use IGN\InfoGitBundle\Model\parserjson;

class FilesController extends Controller
{

    private $parse_json;

    public function treeAction()
    {
        $this->parse_json = new parserjson();
        $gitType=$this->parse_json->getGitType();
        $this->parse_json = $this->parse_json->getAllCommit();
        //die(var_dump($this->parse_json->branche));
        //die(var_dump($this->parse_json->getAllCommit()[0]->getCommits()->getNbCommits()));//getPerson()->{"Steven Nance"}[0]));
        //die(var_dump($this->parse_json[1]->getCommits()->getNbAdd()));//getPerson()->{"Steven Nance"}[0]));

        return $this->render('IGNInfoGitBundle:Files:files.html.twig', array('infoBranches' => $this->parse_json, 'gitType'=> $gitType));
    }

}
