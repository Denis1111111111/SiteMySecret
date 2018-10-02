<?php
include "DAO.php";
include "DAOFile.php";

class Controller
{

    public function execute($reqStr)
    {
        if ($this->isValidData($reqStr) === 'incorrect data') {
            return;
        }

        if ($this->validate($reqStr) !== 'wrong name or password') {
            return $this->requestToDB($reqStr);
        } else {
            return 'wrong name or password';
        }
    }

    private function requestToDB($reqStr)
    {
        //=============
        //DAOFile
        //=============

        $dao = new DAOFile();
        $personList = $dao->requestUser($reqStr);

        // //=========
        // //DAOSQL
        // //=========

        // $dao = new DAO();
        // $dao->connect();

        if ($this->isAdmin($reqStr) === 'true') {
            $personList = $dao->requestAdmin($reqStr);
        } else {
            $personList = $dao->requestUser($reqStr);
        }
        return $personList;
    }

    private function validate($reqStr)
    {
        $text = json_decode($reqStr, true);
        if ($text['command'] === 'createIndex') {
            return;
        }

        $text['command'] = 'validate';
        $reqStr = json_encode($text);

        if ($this->requestToDB($reqStr) == null) {
            return 'wrong name or password';
        }
    }

    private function isAdmin($reqStr)
    {
        $text = json_decode($reqStr, true);
        if ($text['isAdmin'] === 'true' || $text['name'] === 'Admin') {
            return 'true';
        } else {
            return 'false';
        }
    }

    private function isValidData($reqStr)
    {
        $text = json_decode($reqStr, true);

        if ($text['secret'] === '' && $text['command'] === 'createIndex' || $text['secret'] === '' && $text['command'] === 'createAdmin' || $text['secret'] === '' && $text['command'] === 'updateAdmin' || $text['secret'] === '' && $text['command'] === 'deleteAdmin') {
            return 'incorrect data';
        }

        // switch ($text['command']) {
        //     case 'createIndex':
        //         {

        //         }

        //     case 'createAdmin':
        //         {

        //         }

        //     case 'updateAdmin':
        //         {

        //         }

        //     case 'deleteAdmin':
        //         {

        //         }
        // }

    }
}
