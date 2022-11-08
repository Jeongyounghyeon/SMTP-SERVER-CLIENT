# 세종대학교 컴퓨터네트워크 - SMTP 구현 

### 사용가능 메일 : gmail, naver

-------
### SMTP server connect (terminal)
- 네이버
```
openssl s_client -debug -starttls smtp -crlf -connect smtp.naver.com:587
```

- gmail
```
openssl s_client -debug -starttls smtp -crlf -connect smtp.gmail.com:587
```
- HELO or EHLO
```
EHLO 블라블라
```

- AUTH PLAIN LOGIN
```
AUTH PLAIN "\000id\000pw"(base64)
```

- MAIL FORM
```
MAIL FROM: <ID> (네이버는 아이디만 입력해야함 ex: vo0v0000)
MAIL FROM: <EMAIL> (gmail은 이메일을 입력해야함 ex: denovolife0000@gamil.com)
```

- RCPT TO
```
RCPT TO: <받는사람 EMAIL>
```

- CONTENTS
```
data
headers.....(네이버는 TO ,FROM 헤더 필수. gmail은 필요없음)
(ex:
DATE: Mon Nov 07 12:42:31 KST 2022
TO: vo0v0000@naver.com
FROM: vo0v0000@naver.com
)
subject: 메일제목
mail contents~~~~
~~~~~~
~~~~~
.  < 메일내용 종료
QUIT
```

---------
## Client 
- naver
<img width="1447" alt="스크린샷 2022-11-07 오후 10 13 34" src="https://user-images.githubusercontent.com/63547292/200324637-ac512f0c-ff0e-4cc8-b6be-fa332296b22c.png">

- gmail
<img width="1490" alt="스크린샷 2022-11-07 오후 10 15 03" src="https://user-images.githubusercontent.com/63547292/200324711-09c9d092-c241-42d8-89fd-978fc4111566.png">

- error 
<img width="1480" alt="스크린샷 2022-11-07 오후 10 15 51" src="https://user-images.githubusercontent.com/63547292/200324982-5d385ff3-489f-4e6b-8ee7-79849d952328.png">


----------
## Server 
- naver connect
<img width="1482" alt="스크린샷 2022-11-07 오후 10 13 50" src="https://user-images.githubusercontent.com/63547292/200325115-36cc5391-cf1e-40a5-87ff-c9a21d77ed85.png">
<img width="1480" alt="스크린샷 2022-11-07 오후 10 14 00" src="https://user-images.githubusercontent.com/63547292/200325157-204e72bd-d2d3-45a1-9d02-6f6380a64daf.png">

- gmail connect
<img width="1424" alt="스크린샷 2022-11-07 오후 10 15 22" src="https://user-images.githubusercontent.com/63547292/200325320-a506f570-0848-4c6b-a8a4-19534e4494fe.png">
<img width="1493" alt="스크린샷 2022-11-07 오후 10 15 30" src="https://user-images.githubusercontent.com/63547292/200325335-3e8e5bce-42a0-44da-9d4e-6c660cde586d.png">


-------
## 
- 받은메일함 확인
<img width="1029" alt="스크린샷 2022-11-07 오후 10 24 21" src="https://user-images.githubusercontent.com/63547292/200325652-3f271903-e2b7-42b8-81d5-26c621b2f14c.png">

- 보낸메일함 확인
<img width="1242" alt="스크린샷 2022-11-07 오후 10 46 07" src="https://user-images.githubusercontent.com/63547292/200325858-084060a2-a6a0-4db4-9f1a-53c348395e3a.png">

