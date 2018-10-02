<?php
class DAOFile
{
    public $file = 'File.txt';

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
                    $section = file_get_contents('./File.txt');
                    $arr = json_decode($section, true);
                    $count = count($arr);

                    $str = $count . ', ';

                    for ($i = 0; $i < $count; $i++) {
                        $str = $str . $arr[$i]['id'] . ' ' . $arr[$i]['name'] . ' ' . $arr[$i]['password'] . ' ' . $arr[$i]['secret'] . ', ';
                    }
                    return $str;
                }

            case 'updateAdmin':
                {
                    $ar = explode(',', $text['secret']);

                    $section = file_get_contents('./File.txt');
                    $arr = json_decode($section, true);

                    $json = '';
                    foreach ($arr as $pers) {
                        if ($pers['id'] === $ar[0]) {
                            $pers['secret'] = $ar[1];
                        }
                        $pers = json_encode($pers);
                        $pers = strval($pers);
                        if ($json === '') {
                            $json = $json . $pers;
                        } else {
                            $json = $json . ',' . $pers;
                        }
                    }
                    $json = '[' . $json . ']';

                    $f = fopen('File.txt', 'w');
                    fclose($f);

                    $file = 'File.txt';
                    $current = file_get_contents($file);
                    $current .= $json;
                    file_put_contents($file, $current);

                    return 'field secret updated';
                }

            case 'deleteAdmin':
                {
                    $section = file_get_contents('./File.txt');
                    $arr = json_decode($section, true);

                    $json = '';
                    foreach ($arr as $key => $pers) {
                        if ($pers['id'] === $text['secret']) {
                            unset($arr[$key]);
                            continue;
                        }
                        $pers = json_encode($pers);
                        $pers = strval($pers);
                        if ($json === '') {
                            $json = $json . $pers;
                        } else {
                            $json = $json . ',' . $pers;
                        }
                    }
                    $json = '[' . $json . ']';

                    $f = fopen('File.txt', 'w');
                    fclose($f);

                    $file = 'File.txt';
                    $current = file_get_contents($file);
                    $current .= $json;
                    file_put_contents($file, $current);

                    return 'account deleted';
                }

            case 'createAdmin':
                {
                    $ar = explode(',', $text['secret']);

                    $person = new Person();
                    $person->id = $this->findId();
                    $person->name = $ar[0];
                    $person->password = $ar[1];
                    $person->secret = $ar[2];

                    $person = json_encode($person);

                    $section = file_get_contents('./File.txt');

                    $json = json_decode($section, true);
                    $json = json_encode($json);
                    $json = strval($json);
                    $arr = str_split($json);

                    if ($arr[0] === '[') {
                        $arr[0] = '';
                        $arr[count($arr) - 1] = '';
                    }
                    $json = implode("", $arr);
                    $json = $json . ',' . $person;
                    $json = '[' . $json . ']';

                    $f = fopen('File.txt', 'w');
                    fclose($f);

                    $file = 'File.txt';
                    $current = file_get_contents($file);
                    $current .= $json;
                    file_put_contents($file, $current);
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

                    $person = new Person();
                    $person->id = $this->findId();
                    $person->name = $text['name'];
                    $person->password = $text['password'];
                    $person->secret = $text['secret'];

                    $person = json_encode($person);

                    $section = file_get_contents('./File.txt');

                    if ($section != '') {
                        $json = json_decode($section, true);
                        $json = json_encode($json);
                        $json = strval($json);
                        $arr = str_split($json);

                        if ($arr[0] === '[') {
                            $arr[0] = '';
                            $arr[count($arr) - 1] = '';
                        }
                        $json = implode("", $arr);
                        $json = $json . ',' . $person;
                        $json = '[' . $json . ']';

                        $f = fopen('File.txt', 'w');
                        fclose($f);

                        $file = 'File.txt';
                        $current = file_get_contents($file);
                        $current .= $json;
                        file_put_contents($file, $current);
                        return 'account created';
                    }

                    $json = strval($person);

                    $file = 'File.txt';
                    $current = file_get_contents($file);
                    $current .= $json;
                    file_put_contents($file, $current);
                    return 'account created';
                }

            case 'readIndex':
                {
                    return 'User page open';
                }

            case 'readUser':
                {
                    $section = file_get_contents('./File.txt');
                    $arr = json_decode($section, true);

                    foreach ($arr as $pers) {
                        if ($pers['name'] === $text['name'] && $pers['password'] === $text['password']) {
                            return $pers['secret'];
                        }
                    }
                    break;
                }

            case 'updateUser':
                {
                    $section = file_get_contents('./File.txt');
                    $arr = json_decode($section, true);

                    $json = '';
                    foreach ($arr as $pers) {
                        if ($pers['name'] === $text['name'] && $pers['password'] === $text['password']) {
                            $pers['secret'] = $text['secret'];
                        }
                        $pers = json_encode($pers);
                        $pers = strval($pers);
                        if ($json === '') {
                            $json = $json . $pers;
                        } else {
                            $json = $json . ',' . $pers;
                        }
                    }
                    $json = '[' . $json . ']';

                    $f = fopen('File.txt', 'w');
                    fclose($f);

                    $file = 'File.txt';
                    $current = file_get_contents($file);
                    $current .= $json;
                    file_put_contents($file, $current);

                    return 'file secret update';
                }

            case 'deleteUser':
                {
                    $section = file_get_contents('./File.txt');
                    $arr = json_decode($section, true);

                    $json = '';
                    foreach ($arr as $key => $pers) {
                        if ($pers['name'] === $text['name'] && $pers['password'] === $text['password']) {
                            unset($arr[$key]);
                            continue;
                        }
                        $pers = json_encode($pers);
                        $pers = strval($pers);
                        if ($json === '') {
                            $json = $json . $pers;
                        } else {
                            $json = $json . ',' . $pers;
                        }
                    }
                    $json = '[' . $json . ']';

                    $f = fopen('File.txt', 'w');
                    fclose($f);

                    $file = 'File.txt';
                    $current = file_get_contents($file);
                    $current .= $json;
                    file_put_contents($file, $current);

                    return 'account deleted';
                }

            case 'validate':
                {
                    return $this->validate($reqStr);
                }
        }
    }

    public function validate($reqStr)
    {
        $text = json_decode($reqStr, true);

        $section = file_get_contents('./File.txt');

        $arr = json_decode($section, true);

        foreach ($arr as $pers) {
            if ($pers['name'] === $text['name'] && $pers['password'] === $text['password']) {
                return 'authorise';
            }
        }
        return null;
    }

    public function findId()
    {
        $id = 1;
        $section = file_get_contents('./File.txt');
        if ($section !== '') {
            $arr = json_decode($section, true);

            if (isset($arr['id'])) {
                $id = 2;
                return strval($id);
            }
            $id = count($arr) + 1;
        }
        return strval($id);
    }
}

class Person
{
    public $id = '';
    public $name = '';
    public $password = '';
    public $secret = '';
}
