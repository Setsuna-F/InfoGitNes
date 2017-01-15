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
    private $path;
    private $compteur = 0;
    //private $list;

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
                    $path = $value;

                }
                if ($key == "name") {
                    $name = $value;
                }
            }
            $paths[$path] = $type."@".$line;
        }
        $tree = $this->build_tree($paths);
        $list = $this->build_list($tree);
        return $this->render('IGNInfoGitBundle:Files:files.html.twig', array('list' => $list));
    }


    function build_tree($path_list) {
        $path_tree = array();
        foreach ($path_list as $path => $title) {
            $list = explode('/', trim($path, '/'));
            $last_dir = &$path_tree;
            foreach ($list as $dir) {
                $last_dir =& $last_dir[$dir];
            }
            $last_dir['__title'] = $title;
        }
        return $path_tree;
    }

    function build_list($tree) {
        $ul = '';
        foreach ($tree as $key => $value) {
            $li = '';
            if (is_array($value)) {
                if (array_key_exists('__title', $value)) {
                    $tab = split("@", $value['__title']);
                    if($tab[0] == "Directory")
                    {
                        $t = $this->compteur;
                        $li .= "<input type=\"checkbox\" id=\"c$t\" /><i class=\"glyphicon glyphicon-folder-close\"></i><i class=\"glyphicon glyphicon-folder-open\"></i><label for=\"c$t\"> $key </label>";
                        $this->compteur++;
                    }
                    else if ($tab[1] <= 1) {
                        $li .= "<i class=\"glyphicon glyphicon-file\"></i>$key<p>$tab[1] ligne</p>";
                    }
                    else $li .= "<i class=\"glyphicon glyphicon-file\"></i>$key<p>$tab[1] lignes</p>";
                } else {
                    $li .= "$key";
                }
                $li .= $this->build_list($value);
                $ul .= strlen($li) ? "<li>$li</li>" : '';
            }
        }
        return strlen($ul) ? "<ul>$ul</ul>" : '';
    }
}
