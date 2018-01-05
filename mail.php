
<?php


$mailcontent=$HTTP_POST_VARS['mailcontent'];

$lettertype=$HTTP_POST_VARS['lettertype'];

$toaddress= 'admin77song@virtmarket.net';

$addition ="content-type: text/plain; charset=utf-8";

$mailcontent='From Server To Client : '.$mailcontent;


$subject= 'Subject of Message From Server To Client : '.$lettertype;

mail($toaddress, $subject , $mailcontent, $addition);

echo $subject;
echo $mailcontent;

?>

