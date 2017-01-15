<?php

namespace IGN\InfoGitBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use IGN\InfoGitBundle\Model\parserjson;
use IGN\InfoGitBundle\Model\Commit;

class BranchesController extends Controller
{

    private $parse_json;

    /* ----------------------------
        \function index

    */
    public function abranchAction($name)
    {
        $this->parse_json = new parserjson();
        $gitType=$this->parse_json->getGitType();
        $allCommit = $this->parse_json->getAllCommit();

        $nbComTotal=0; // Nombre de commit total pour cette branche
        $infoUser = array(); //Les commits de chaque utilisateur. la taille de ce tableau est le nombre d'utilisateur pour cette branche.
        $commits = array(); // Le tableau contenant tous les commits.
        $commits_ = array(); // Le tableau contenant tous les commits.
        $contributors = array(); // Le tableau contenant tous les contributeurs.

        $usrs = $this->parse_json->getAllPerson(); // liste de tous les utilisateurs.

        for ($i=0; $i < count($usrs); $i++) {
            $nbCom=0;
            $nbAdd=0;
            $nbDel=0;
            $nbRnm=0;
            $nbMod=0;
            $commits = $this->parse_json->getCommitsListByBrancheAndByContributor($this->parse_json->getBranchNameWithFormatedName_2($name), $usrs[$i]);
            $commits_[] = $commits;
            $nbComTotal = $nbComTotal + count($commits);
            $nbCom = count($commits);
            for ($j=0; $j < $nbCom; $j++) {
                $nbAdd = $nbAdd + $commits[$j]->getNbAdd();
                $nbDel = $nbDel + $commits[$j]->getNbDel();
                $nbRnm = $nbRnm + $commits[$j]->getNbRnm();
                $nbMod = $nbMod + $commits[$j]->getNbMdf();
            }
            if($nbCom != 0){
                $infoUser[$usrs[$i]] = new Commit($nbCom, $nbAdd, $nbDel, $nbRnm, $nbMod);
                $contributors[] = $usrs[$i];
            }
        }

        //phase de teste et de recuperation de donnees.
        $nbCom=0;
        $nbAdd=0;
        $nbDel=0;
        $nbRnm=0;
        $nbMdf=0;
        $ActifCom="";
        $ActifAdd="";
        $ActifDel="";
        $ActifRnm="";
        $ActifMdf="";
        /*echo "nombre de commit : ";
        echo $nbComTotal;
        echo "<hr />";
        echo "nombre de contributeur : ";
        echo count($infoUser);
        echo "<hr />";
        echo "liste de contributeurs : ";*/
        for ($k=0; $k < count($contributors); $k++) {
            //echo $contributors[$k];
            //echo "; ";

            //-- Plus actif en commit
            if($infoUser[$contributors[$k]]->getNbCommits() >= $nbCom){
                $nbCom = $infoUser[$contributors[$k]]->getNbCommits();
                $ActifCom = $contributors[$k];
            }
            //-- Plus actif en addition
            if($infoUser[$contributors[$k]]->getNbAdd() >= $nbAdd){
                $nbAdd = $infoUser[$contributors[$k]]->getNbAdd();
                $ActifAdd = $contributors[$k];
            }
            //-- Plus actif en Suppresssion
            if($infoUser[$contributors[$k]]->getNbDel() >= $nbDel){
                $nbDel = $infoUser[$contributors[$k]]->getNbDel();
                $ActifDel = $contributors[$k];
            }
            //-- Plus actif en Renommage
            if($infoUser[$contributors[$k]]->getNbRnm() >= $nbRnm){
                $nbRnm = $infoUser[$contributors[$k]]->getNbRnm();
                $ActifRnm = $contributors[$k];
            }
            //-- Plus actif en Modification
            if($infoUser[$contributors[$k]]->getNbMdf() >= $nbMdf){
                $nbMdf = $infoUser[$contributors[$k]]->getNbMdf();
                $ActifMdf = $contributors[$k];
            }
        }
/*        echo "<hr />";

        echo "Plus actif en Commit : ";
        echo $ActifCom;
        echo "<hr />";

        echo "Plus actif en Ajout : ";
        echo $ActifAdd;
        echo "<hr />";

        echo "Plus actif en Suppresssion : ";
        echo $ActifDel;
        echo "<hr />";

        echo "Plus actif en renommage : ";
        echo $ActifRnm;
        echo "<hr />";

        echo "Plus actif en modification : ";
        echo $ActifMdf;
        echo "<hr />";
*/

        //die(var_dump( count($infoUser) ));

        return $this->render('IGNInfoGitBundle:Branches:abranch.html.twig',
                    array(  'name'=> $name,
                            'infoContributor' => $this->parse_json,
                            'gitType'=> $gitType,

                            'actifCom'=> $ActifCom,
                            'actifAdd'=> $ActifAdd,
                            'actifDel'=> $ActifDel,
                            'actifRnm'=> $ActifRnm,
                            'actifMdf'=> $ActifMdf,

                            'nbComTotal'=> $nbComTotal,
                            'infoUser'=> $infoUser,
                            'contributors'=> $contributors,
                            'commits'=> $commits,
                            'commits_'=> $commits_,
                        ));
    }


    /* ----------------------------
        \function index

    */
    public function allbranchesAction()
    {
        $this->parse_json = new parserjson();
        $gitType=$this->parse_json->getGitType();
        $allCommit = $this->parse_json->getAllCommit();

        $allMostActif = array();

        for ($i=0; $i <count($allCommit); $i++) {
            $allMostActif[$allCommit[$i]->getName()] = $this->parse_json->getMostActifByBranch($allCommit[$i]->getName());
        }
        //die(var_dump( $allMostActif ));

        //die(var_dump($this->parse_json));
        //die(var_dump($this->parse_json->getAllCommit()[0]->getCommits()->getNbCommits()));//getPerson()->{"Steven Nance"}[0]));
        //die(var_dump($this->parse_json[1]->getCommits()->getNbAdd()));//getPerson()->{"Steven Nance"}[0]));

        return $this->render('IGNInfoGitBundle:Branches:allbranches.html.twig', array('infoBranches' => $allCommit, 'mostActif' => $allMostActif, 'gitType'=> $gitType));
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
