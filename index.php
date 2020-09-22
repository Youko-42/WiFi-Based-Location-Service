<?php
    function my_login($user_name, $password) {
        $con_db = mysqli_connect("localhost", "root", "root", "hw1_db");
        if (mysqli_connect_errno($con_db)) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
        }

        $sql_command = "SELECT id, full_name, interest, update_frequency 
                        FROM tb_users 
                        WHERE user_name = '{$user_name}' AND password = '{$password}'";
        $result = mysqli_query($con_db, $sql_command);
        $num_rows = mysqli_num_rows($result);
        if ($num_rows > 0) {
            $row = mysqli_fetch_array($result);
            $id = $row[0];
            $full_name = $row[1];
            $interest = $row[2];
            $update_frequency = $row[3];
            echo $id . ';' . $user_name . ';' . $full_name . ';' . $interest . ';' . $update_frequency;
        } else {
            echo 'Failed';
        }

        mysqli_close($con_db);
    }

    function my_regis($user_name, $pass, $device_mac) {
        $con_db = mysqli_connect("localhost", "root", "root", "hw1_db");
        if (mysqli_connect_errno($con_db)) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
        }

        $sql_command = "INSERT INTO tb_users (user_name, password, device_mac) 
                        VALUES ('{$user_name}', '{$pass}', '{$device_mac}')";
        if (mysqli_query($con_db, $sql_command)) {
            echo "New record created successfully";
        } else {
            echo $con_db->error;
        }

        mysqli_close($con_db);
    }

    function my_config($id, $user_name, $full_name, $interest, $frequency) {
        $con_db = mysqli_connect("localhost", "root", "root", "hw1_db");
        if (mysqli_connect_errno($con_db)) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
        }
        $sql_command = "UPDATE tb_users 
                        SET user_name = '{$user_name}', full_name = '{$full_name}', interest = '{$interest}', update_frequency = '{$frequency}' 
                        WHERE id = {$id}";
        mysqli_query($con_db, $sql_command);

        $sql_command = "SELECT id, full_name, interest, update_frequency 
                        FROM tb_users 
                        WHERE id = {$id}";
        $result = mysqli_query($con_db, $sql_command);
        $num_rows = mysqli_num_rows($result);
        if ($num_rows > 0) {
            $row = mysqli_fetch_array($result);
            $id = $row[0];
            $full_name = $row[1];
            $interest = $row[2];
            $update_frequency = $row[3];
            echo $id . ';' . $user_name . ';' . $full_name . ';' . $interest . ';' . $update_frequency;
        } else {
            echo 'Failed';
        }

        mysqli_close($con_db);
    }

    function my_news($interest) {
        $con_db = mysqli_connect("localhost", "root", "root", "hw1_db");
        if (mysqli_connect_errno($con_db)) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
        }

        $sql_command = "SELECT URL 
                        FROM tb_news 
                        WHERE category = '{$interest}'";
        $result = mysqli_query($con_db, $sql_command);
        $num_rows = mysqli_num_rows($result);
        while ($num_rows > 0) {
            $row = mysqli_fetch_array($result);
            $URL = $row[0];
            echo $URL . "@@@";

            $num_rows = $num_rows - 1;
        }

        mysqli_close($con_db);
    }

    function my_friends($part_name) {
        $con_db = mysqli_connect("localhost", "root", "root", "hw1_db");
        if (mysqli_connect_errno($con_db)) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
        }

        $sql_command = "SELECT tb_users.full_name, tb_users.online, tb_location.building, tb_location.floor 
                        FROM tb_users, tb_location 
                        WHERE tb_users.full_name Like '%{$part_name}%' AND tb_users.connected_mac = tb_location.mac";
        $result = mysqli_query($con_db, $sql_command);
        $num_rows = mysqli_num_rows($result);
        while ($num_rows > 0) {
            $row = mysqli_fetch_array($result);
            $full_name = $row[0];
            $prev_time = $row[1];
            $building = $row[2];
            $floor = $row[3];

            $now_time = date('Y-m-d', time());
            $interval = floor((strtotime($now_time) - strtotime($prev_time)) % 86400 / 60);
            echo $full_name . ":   " . $floor . ",  " . $building . " (" . $interval .  " min ago)" . "@@@";

            $num_rows = $num_rows - 1;
        }

        mysqli_close($con_db);
    }

    function my_wifi($id, $connected_mac, $wifi_list) {
        $con_db = mysqli_connect("localhost", "root", "root", "hw1_db");
        if (mysqli_connect_errno($con_db)) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
        }

        $sql_command = "UPDATE tb_users 
                        SET connected_mac = '{$connected_mac}', around_wifi_list = '{$wifi_list}', online = CURRENT_TIMESTAMP() 
                        WHERE id = {$id}";
        if (mysqli_query($con_db, $sql_command)) {
            echo "New record created successfully";
        } else {
            echo $con_db->error;
        }

        mysqli_close($con_db);
    }

    $method = $_POST['method'];
    switch ($method) {
        case 'login':
            $user_name = $_POST['user_name'];
            $password = $_POST['password'];
            my_login($user_name, $password);
            break;
        case 'regis':
            $user_name = $_POST['user_name'];
            $pass = $_POST['pass'];
            $device_mac = $_POST['device_mac'];
            my_regis($user_name, $pass, $device_mac);
            break;
        case 'config':
            $id = $_POST['id'];
            $user_name = $_POST['user_name'];
            $full_name = $_POST['full_name'];
            $interest = $_POST['interest'];
            $frequency = $_POST['frequency'];
            my_config($id, $user_name, $full_name, $interest, $frequency);
            break;
        case 'news':
            $interest = $_POST['interest'];
            my_news($interest);
            break;
        case 'friend':
            $part_name = $_POST['part_name'];
            my_friends($part_name);
            break;
        case 'wifi':
            $id = $_POST['id'];
            $connected_mac = $_POST['connected_mac'];
            $wifi_list = $_POST['wifi_list'];
            my_wifi($id, $connected_mac, $wifi_list);
            break;
        default:
            break;
    }
?>