<?php

namespace IGN\InfoGitBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class ContributorsController extends Controller
{
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
