<?php

namespace IGN\InfoGitBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use IGN\InfoGitBundle\Model\parserjson;

class BeginingController extends Controller
{
    private $parse_json;

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
        $this->parse_json = new parserjson("http://...soka.com");

        //die(var_dump($this->parse_json->getCommitsByAllContributor()));
        //die(var_dump($this->parse_json->mostActif()));

        //$this->parse_json->getContributorsAndMails();
        //die(var_dump($this->parse_json->getMailsByContributor('patrick-mcdougle')[0]));
        //die(var_dump($this->parse_json->getAllPerson()));
        //die(var_dump($this->parse_json->getPerson()['patrick-mcdougle']));
        //die(var_dump($this->parse_json->getPerson()['patrick-mcdougle'][0]));


        //die(var_dump($this->parse_json->getNbBranches()));
        //die(var_dump($this->parse_json->getPerson()->{'Steven Nance'} ));//->getContributorsAndMails()));
        //die(var_dump($this->parse_json->getPerson()->{"Steven Nance"}[0]));
        return $this->render('IGNInfoGitBundle:Begining:home.html.twig', array('infoGeneral' => $this->parse_json));
    }


    /* ----------------------------
        \function index


    */
    public function treeAction()
    {
        return $this->render('IGNInfoGitBundle:Begining:tree.html.twig');
    }
}
