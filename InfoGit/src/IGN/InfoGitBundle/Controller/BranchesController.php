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
        $this->parse_json = new parserjson();
        $gitType=$this->parse_json->getGitType();
        $this->parse_json = $this->parse_json->getAllCommit();
        //die(var_dump($this->parse_json->branche));
        //die(var_dump($this->parse_json->getAllCommit()[0]->getCommits()->getNbCommits()));//getPerson()->{"Steven Nance"}[0]));
        //die(var_dump($this->parse_json[1]->getCommits()->getNbAdd()));//getPerson()->{"Steven Nance"}[0]));

        return $this->render('IGNInfoGitBundle:Branches:allbranches.html.twig', array('infoBranches' => $this->parse_json, 'gitType'=> $gitType));
    }


    /* ----------------------------
        \function index

    */
    public function acontributorwithbranchAction($contributor, $branch)
    {
        $this->parse_json = new parserjson();
        //die(var_dump($contributor));
        //die(var_dump($branch));
        //die(var_dump($this->parse_json->getBranchNameWithFormatedName($contributor, $branch)));


        $commitslist = $this->parse_json->getCommitsListByBrancheAndByContributor( $this->parse_json->getBranchNameWithFormatedName($contributor, $branch), $contributor);
        //die(var_dump($this->parse_json->getCommitsList()));
        //die(var_dump($this->parse_json = $this->parse_json->getInfoCommitsByContributor($contributor)));
        return $this->render('IGNInfoGitBundle:Branches:acontributorwithbranch.html.twig', array('infoBranches' => $this->parse_json, 'name' => $contributor, 'branchName' => $branch, 'commitslist' => $commitslist, 'gitType'=> $this->parse_json->getGitType()));
    }

}
