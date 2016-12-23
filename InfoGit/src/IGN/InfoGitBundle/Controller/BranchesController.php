<?php

namespace IGN\InfoGitBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use IGN\InfoGitBundle\Model\parserjson;

class BranchesController extends Controller
{

    private $parse_json;

    /* ----------------------------
        \function index


    */
    public function abranchAction()
    {
        return $this->render('IGNInfoGitBundle:Branches:abranch.html.twig');
    }


    /* ----------------------------
        \function index


    */
    public function allbranchesAction()
    {
        $this->parse_json = new parserjson("http://...soka.com");
        $this->parse_json = $this->parse_json->getAllCommit();
        //die(var_dump($this->parse_json->branche));
        //die(var_dump($this->parse_json->getAllCommit()[0]->getCommits()->getNbCommits()));//getPerson()->{"Steven Nance"}[0]));

        return $this->render('IGNInfoGitBundle:Branches:allbranches.html.twig', array('infoBranches' => $this->parse_json));
    }


    /* ----------------------------
        \function index


    */
    public function acontributorwithbranchAction($contributorName, $branchName)
    {
            return $this->render('IGNInfoGitBundle:Branches:acontributorwithbranch.html.twig');
    }

}
