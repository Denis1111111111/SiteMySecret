<?php
class DAO
{
    public $USERNAME = "root";
    public $PASSWORD = "";
    public $HOST = "localhost";
    public $DB = "test";

    public $connection;

    public function connect()
    {
        $this->connection = new PDO("mysql:dbname=" . $this->DB . ";host=" . $this->HOST, $this->USERNAME, $this->PASSWORD);
    }

    public function requestAdmin($reqStr)
    {
        $text = json_decode($reqStr, true);

        switch ($text['command']) {
            case 'readIndex':
                {
                    return 'Admin page open';
                }

            case 'readAdmin':
                {
                    $query = 'SELECT count(*) From table1';
                    $count = $this->request($query)[0]['count(*)'];
                    $query = 'SELECT * FROM table1';
                    $answer = $this->request($query);
                    $str = $count . ', ';

                    for ($i = 0; $i < $count; $i++) {
                        $str = $str . $answer[$i]['id'] . ' ' . $answer[$i]['name'] . ' ' . $answer[$i]['password'] . ' ' . $answer[$i]['secret'] . ', ';
                    }
                    return $str;

                }

            case 'updateAdmin':
                {
                    $ar = explode(',', $text['secret']);
                    $query = 'UPDATE table1 SET  secret=' . '\'' . $ar[1] . '\'WHERE id=' . '\'' . $ar[0] . '\'';
                    $this->request($query);
                    return 'field secret updated';
                }

            case 'deleteAdmin':
                {
                    $query = 'DELETE FROM table1 WHERE id=' . '\'' . $text['secret'] . '\'';
                    $this->request($query);
                    return 'account deleted';
                }

            case 'createAdmin':
                {
                    $ar = explode(',', $text['secret']);

                    $query = 'SELECT * FROM table1 WHERE name=' . '\'' . $ar[0] . '\'';
                    if ($this->request($query) != null) {
                        return 'use a different name';
                    }

                    $query = 'INSERT INTO table1 (name,password,secret) VALUE (' . '\'' . $ar[0] . '\',' . '\'' . $ar[1] . '\',' . '\'' . $ar[2] . '\')';
                    $this->request($query);
                    return 'account created';
                }

            case 'validate':
                {
                    return $this->validate($reqStr);
                }
        }
    }

    public function requestUser($reqStr)
    {
        $text = json_decode($reqStr, true);

        switch ($text['command']) {
            case 'createIndex':
                {
                    $query = 'SELECT * FROM table1 WHERE name=' . '\'' . $text['name'] . '\'';
                    if ($this->request($query) != null) {
                        return 'use a different name';
                    }

                    $query = 'INSERT INTO table1 (name,password,secret) VALUE (' . '\'' . $text['name'] . '\',' . '\'' . $text['password'] . '\',' . '\'' . $text['secret'] . '\')';
                    $this->request($query);
                    return 'account created';
                }

            case 'readIndex':
                {
                    $query = 'SELECT secret FROM table1 WHERE name=' . '\'' . $text['name'] . '\'AND password=' . '\'' . $text['password'] . '\'';
                    $answer = $this->request($query);
                    return $answer[0]['secret'];
                }

            case 'readUser':
                {
                    $query = 'SELECT secret FROM table1 WHERE name=' . '\'' . $text['name'] . '\'';
                    $answer = $this->request($query);
                    return $answer[0]['secret'];
                }

            case 'updateUser':
                {
                    $query = 'UPDATE table1 SET  secret=' . '\'' . $text['secret'] . '\'WHERE name=' . '\'' . $text['name'] . '\'';
                    $this->request($query);
                    return 'field secret updated';
                }

            case 'deleteUser':
                {
                    $query = 'DELETE FROM table1 WHERE name=' . '\'' . $text['name'] . '\'';
                    $this->request($query);
                    return 'account deleted';
                }

            case 'validate':
                {
                    return $this->validate($reqStr);
                }
        }
    }

    public function request($query)
    {

        $stmt = $this->connection->prepare($query);
        $stmt->execute();

        $personList = $stmt->fetchAll(PDO::FETCH_ASSOC);
        return $personList;
    }

    public function validate($reqStr)
    {
        $text = json_decode($reqStr, true);
        $query = 'SELECT * FROM table1 WHERE name=' . '\'' . $text['name'] . '\'AND password=' . '\'' . $text['password'] . '\'';

        $stmt = $this->connection->prepare($query);
        $stmt->execute();

        $personList = $stmt->fetchAll(PDO::FETCH_ASSOC);

        return $personList;
    }
}
