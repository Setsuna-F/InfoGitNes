<?php

namespace IGN\InfoGitBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class BranchesController extends Controller
{
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
        return $this->render('IGNInfoGitBundle:Branches:allbranches.html.twig');
    }
}
