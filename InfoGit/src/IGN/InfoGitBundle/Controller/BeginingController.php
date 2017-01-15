<?php

namespace IGN\InfoGitBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\Extension\Core\Type\UrlType;
use Symfony\Component\HttpFoundation\Request;
use IGN\InfoGitBundle\Model\parserjson;

class BeginingController extends Controller
{
    private $parse_json;

    /* ----------------------------
        \function index

    */
    public function indexAction(Request $request)
    {
            $data = array();
            $form = $this->createFormBuilder($data)
                         ->add('urlgit', UrlType::class, array('label' => 'URL Git : ', 'required' => true, 'attr' => array('id' => 'formurl', 'class' => 'input-recherche', 'placeholder' => 'URL Git')))
                        ->getForm();

          $form->handleRequest($request);
          $isloadurl=0;

          if ($form->isSubmitted() && $form->isValid()) {
              /* On recupere les données */
              $data = $form->getData();

              /* Appel au programme java permetant de generer le json */
              $cmdline = 'java -jar ../../InfoGitJar/InfoGitNes.jar '.$data['urlgit'];
              $last_line = system($cmdline);

              /* On prepare le chargement */
              $isloadurl=1;

              /* On change de page pour aller a la page principale */
              return $this->redirectToRoute('ign_info_git_homepage');
          }


          return $this->render('IGNInfoGitBundle::index.html.twig', array('form' => $form->createView(),'isloadurl'=>$isloadurl));
      }


    /* ----------------------------
        \function index

    */
    public function homeAction()
    {
        $monfichier = fopen('processus.nes', 'r'); //On ouvre le fichier processus.nes
        $ligne = fgets($monfichier); //On lit la premiere ligne
        if(strlen($ligne)==0) //Si le fichier est vide
            return $this->redirectToRoute('ign_info_git_index'); //Alors on redirige la route vers l'index (la barre de recherche)

        while(intval($ligne) <= 0){ // Tant que la valeur de la ligne est inferieur ou égal a zero.
            fseek($monfichier, 0); // On remet le curseur au debut du fichier
            $ligne = fgets($monfichier); //On lit la premiere ligne
        }
        fclose($monfichier); //On ferme le fichier processus.nes

        $this->parse_json = new parserjson();

        return $this->render('IGNInfoGitBundle:Begining:home.html.twig', array('infoGeneral' => $this->parse_json, 'gitType'=> $this->parse_json->getGitType()));
    }


    /* ----------------------------
        \function index


    */
    public function treeAction()
    {
        $this->parse_json = new parserjson();

        return $this->render('IGNInfoGitBundle:Begining:tree.html.twig',  array('gitType'=> $this->parse_json->getGitType()));
    }
}
