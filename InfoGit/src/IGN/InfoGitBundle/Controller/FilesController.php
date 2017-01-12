<?php

namespace IGN\InfoGitBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use IGN\InfoGitBundle\Model\json2tree;

class FilesController extends Controller
{

    private $listeFile;
    private $type;
    private $line;
    private $name;

    public function treeAction()
    {
        $this->listeFile = new json2tree();
        $liste = $this->listeFile->getFiles();
        foreach ($liste as $key => $value) {
            //echo "$key";
            foreach ($value as $key => $value) {
                //echo " $key -> $value</br>";
                if ($key == "type") {
                    $type = $value;
                }
                if ($key == "line") {
                    $line = $value;
                }
                if ($key == "path") {
                    # code...

                }
                if ($key == "name") {
                    $name = $value;
                }
            }
            echo "-> $name is a $type of $line lines </br>";
        }

        return $this->render('IGNInfoGitBundle:Files:files.html.twig', array('infoBranches' => $this->parse_json));
    }

}
