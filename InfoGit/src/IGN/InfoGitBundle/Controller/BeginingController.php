<?php

namespace IGN\InfoGitBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class BeginingController extends Controller
{
    /* ----------------------------
        \function index

    */
    public function homeAction()
    {
        return $this->render('IGNInfoGitBundle:Begining:home.html.twig');
    }


    /* ----------------------------
        \function index


    */
    public function treeAction()
    {
        return $this->render('IGNInfoGitBundle:Begining:tree.html.twig');
    }


    /* ----------------------------
        \function index


    */
    public function abranchAction()
    {
        return $this->render('IGNInfoGitBundle:Brances:abranch.html.twig');
    }


    /* ----------------------------
        \function index


    */
    public function allbranchesAction()
    {
        return $this->render('IGNInfoGitBundle:Brances:allbranches.html.twig');
    }


    /* ----------------------------
        \function index


    */
    public function acontributorAction()
    {
        return $this->render('IGNInfoGitBundle:Contributors:acontributor.html.twig');
    }


    /* ----------------------------
        \function index


    */
    public function allcontributorsAction()
    {
        return $this->render('IGNInfoGitBundle:Contributors:allcontributors.html.twig');
    }
}
