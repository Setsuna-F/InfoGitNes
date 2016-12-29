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
//         echo "Chargement";
//        //$last_line = system('ls', $retval);
//        $last_line = system('java -jar ../../InfoGitJar/InfoGitNes.jar https://github.com/ddeboer/GuzzleBundle.git', $retval);
//        //$last_line = exec ('java -jar InfoGitNes.jar https://github.com/apple/swift.git', $output, $retval );
//        echo '
//            </pre>
//            <hr />_____La dernière ligne en sortie de la commande : ' . $last_line . '
//            ';/*'<hr />Valeur de l\'output : ';
//            for($o=0; $o < count($output); $o++){
//                echo $output[$o];
//                echo "s";
//            }*/
//
//        echo '<hr />Valeur retournée : ' . $retval;
//        die();

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

              /*die(var_dump($data['urlgit']));
                die(var_dump($last_line));*/

              /* On change de page pour aller a la page principale */
              return $this->redirectToRoute('ign_info_git_homepage');
          }


          return $this->render('IGNInfoGitBundle::index.html.twig', array('form' => $form->createView(),'isloadurl'=>$isloadurl,));
      }


    /* ----------------------------
        \function index

    */
    public function homeAction()
    {
        //$monfichier = fopen('../../InfoGitJar/processus.nes', 'r'); //On ouvre le fichier processus.nes
        $monfichier = fopen('processus.nes', 'r'); //On ouvre le fichier processus.nes
        $ligne = fgets($monfichier); //On lit la premiere ligne
        if(strlen($ligne)==0) //Si le fichier est vide
            return $this->redirectToRoute('ign_info_git_index'); //Alors on redirige la route vers l'index (la barre de recherche)

        //die(var_dump($ligne));

        while(intval($ligne) <= 0){ // Tant que la valeur de la ligne est inferieur ou égal a zero.
            fseek($monfichier, 0); // On remet le curseur au debut du fichier
            $ligne = fgets($monfichier); //On lit la premiere ligne
        }
        fclose($monfichier); //On ferme le fichier processus.nes

        //die(var_dump($ligne));


        $this->parse_json = new parserjson();

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
