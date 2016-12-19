<?php

namespace IGN\InfoGitBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class BeginingController extends Controller
{
    /* ----------------------------
        \function index

    */
    public function indexAction()
    {
        return $this->render('IGNInfoGitBundle::index.html.twig');
    }


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
}
