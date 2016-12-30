<?php

namespace IGN\InfoGitBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use IGN\InfoGitBundle\Model\parserjson;

class ContributorsController extends Controller
{
    /* ----------------------------
        \function index


    */
    public function acontributorAction($name)
    {
        $this->parse_json = new parserjson();
        $nbBranch = $this->parse_json->getBranchesByContributor($name);
        $this->parse_json_commit = $this->parse_json->getAllCommit();

        //die(var_dump($this->parse_json->getBranchesByUser()[0]->getName()));
        //die(var_dump($this->parse_json->getBranchesByContributor('Francesco Tassi')));
        //die(var_dump($this->parse_json->getInfoCommitsByBrancheAndContributor('refs/remotes/origin/master', 'Francesco Tassi')->getNbAdd()));

        return $this->render('IGNInfoGitBundle:Contributors:acontributor.html.twig', array('infoContributor' => $this->parse_json, 'infoBranches' => $this->parse_json_commit, 'name'=>$name, 'nbBranch'=>count($nbBranch), 'gitType'=> $this->parse_json->getGitType()));
    }


    /* ----------------------------
        \function index


    */
    public function allcontributorsAction()
    {
        $this->parse_json = new parserjson();

        //die(var_dump($this->parse_json->getCommitsByAllContributor()));
        //die(var_dump($this->parse_json->getNbCommitsByContributor('Francesco Tassi')));

        //$this->parse_json->getContributorsAndMails();
        //die(var_dump($this->parse_json->getMailsByContributor('patrick-mcdougle')[0]));
        //die(var_dump($this->parse_json->getAllPerson()));
        //die(var_dump($this->parse_json->getPerson()['patrick-mcdougle']));
        //die(var_dump($this->parse_json->getPerson()['patrick-mcdougle'][0]));


        //die(var_dump($this->parse_json->getNbBranches()));
        //die(var_dump($this->parse_json->getPerson()->{'Steven Nance'} ));//->getContributorsAndMails()));
        //die(var_dump($this->parse_json->getPerson()->{"Steven Nance"}[0]));

        return $this->render('IGNInfoGitBundle:Contributors:allcontributors.html.twig', array('infoContributor' => $this->parse_json, 'gitType'=> $this->parse_json->getGitType()));
    }
}
