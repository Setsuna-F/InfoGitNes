<?php

namespace IGN\InfoGitBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use IGN\InfoGitBundle\Model\parserjson;
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
        $this->parse_json = new parserjson();
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
        return $this->render('IGNInfoGitBundle:Files:files.html.twig', array('list' => $list, 'gitType'=> $this->parse_json->getGitType()));
    }

    public function showAction($url)
    {
        $code = file_get_contents(base64_decode($url));
        $fichier = base64_decode($url);
        switch (substr($fichier, -3)) {
            case 'png':
                return $this->render('IGNInfoGitBundle:Files:file.html.twig', array('code' => html_entity_decode("<img src=\"../$fichier\" alt=\"image\" />"), 'img' => true));
                break;
            case 'PNG':
                return $this->render('IGNInfoGitBundle:Files:file.html.twig', array('code' => html_entity_decode("<img src=\"../$fichier\" alt=\"image\" />"), 'img' => true));
                break;
            case 'jpeg':
                return $this->render('IGNInfoGitBundle:Files:file.html.twig', array('code' => html_entity_decode("<img src=\"../$fichier\" alt=\"image\" />"), 'img' => true));
                break;
            case 'JPEG':
                return $this->render('IGNInfoGitBundle:Files:file.html.twig', array('code' => html_entity_decode("<img src=\"../$fichier\" alt=\"image\" />"), 'img' => true));
                break;
            case 'gif':
                return $this->render('IGNInfoGitBundle:Files:file.html.twig', array('code' => html_entity_decode("<img src=\"../$fichier\" alt=\"image\" />"), 'img' => true));
                break;
            case 'GIF':
                return $this->render('IGNInfoGitBundle:Files:file.html.twig', array('code' => html_entity_decode("<img src=\"../$fichier\" alt=\"image\" />"), 'img' => true));
                break;
             case 'ico':
                return $this->render('IGNInfoGitBundle:Files:file.html.twig', array('code' => html_entity_decode("<img src=\"../$fichier\" alt=\"image\" />"), 'img' => true));
                break;
             case 'ICO':
                return $this->render('IGNInfoGitBundle:Files:file.html.twig', array('code' => html_entity_decode("<img src=\"../$fichier\" alt=\"image\" />"), 'img' => true));
                break;

            
            default:
                return $this->render('IGNInfoGitBundle:Files:file.html.twig', array('code' => $code ));
                break;
        }
    }


    function build_tree($path_list) {
        $path_tree = array();
        foreach ($path_list as $path => $title) {
            $list = explode('/', trim($path, '/'));
            $last_dir = &$path_tree;
            foreach ($list as $dir) {
                
                if ($dir!= "."&& $dir!= "INFOGITPROJET" ) {
                   $last_dir =& $last_dir[$dir];
                }
                
            }
            $path = substr($path, 2);
            $last_dir['__title'] = "$title@$path";
        }
        return $path_tree;
    }

    function build_list($tree) {
        $ul = '';
        foreach ($tree as $key => $value) {
            $li = '';
            if (is_array($value)) {
                if (array_key_exists('__title', $value)) {
                    $tab = explode("@", $value['__title']);
                    if($tab[0] == "Directory")
                    {
                        $t = $this->compteur;
                        $li .= "<input style='color:red;' type=\"checkbox\" id=\"c$t\" /><i class=\"glyphicon glyphicon-folder-close\"></i><i class=\"glyphicon glyphicon-folder-open\"></i><label for=\"c$t\"> $key </label>";
                        $this->compteur++;
                    }
                    else if ($tab[1] <= 1) {
                        $url = base64_encode($tab[2]);
                        $extention = substr($tab[2], -3);
                        if ($extention == "pdf") {
                        $li .= "<a href=\"../$tab[2]\" target=\"about_blank\"><i class=\"glyphicon glyphicon-file\"></i>$key</a>";
                        }
                        else if ($extention == "jar") {
                            $li .= "<a ><i class=\"glyphicon glyphicon-file\"></i>$key<p>$tab[1] ligne</p></a>";
                        }
                        else $li .= "<a href=\"../file/$url\" target=\"about_blank\"><i class=\"glyphicon glyphicon-file\"></i>$key<p>$tab[1] ligne</p></a>";
                    }
                   else {
                    $url = base64_encode($tab[2]);
                    $extention = substr($tab[2], -3);
                    if ($extention == "pdf") {
                        $li .= "<a href=\"../$tab[2]\" target=\"about_blank\"><i class=\"glyphicon glyphicon-file\"></i>$key</a>";
                    }
                    else if ($extention == "jar") {
                            $li .= "<i class=\"glyphicon glyphicon-file\"></i>$key<p>$tab[1] lignes</p>";
                        }
                    else $li .= "<a href=\"../file/$url\" target=\"about_blank\"><i class=\"glyphicon glyphicon-file\"></i>$key<p>$tab[1] lignes</p></a>";
                    }
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
