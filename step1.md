# Step 1

## 설정, 엔티티, 로그인(Http, jwt token)
* yaml(jpa)
* entity(사용자)
* jwt token 로그인
* 로그인 exception 처리
  * 헤더에 토큰 없을 경우 - 401 Unauthorized
  * 토큰 포맷이 틀린 경우 - 401 Unauthorized
  * 토큰 값이 틀린 경우 - 401 Unauthorized
  * 토큰 만료됐을 경우 - 403 forbidden
  * 없는 사용자일 경우
* token 만료 refresh
* 단위테스트 꼭 작성!
* swagger-ui api 명세서

## 🚀 WIKI
API에 대한 상세 설명과 이슈 내용 정리
- 👉 [API 상세스펙](https://github.com/whoamixzerone/spring-boot-order-service/wiki#api-%EC%8A%A4%ED%8E%99)

## 📔 Issue
프로젝트를 진행하면서 고민했던 이슈들
- 👉 [Issue](https://github.com/whoamixzerone/spring-boot-order-service/wiki#Issue)

## 기능 명세
<table>
<tr>
    <th colspan="2">필요 기능</th>
</tr>
<tr>
    <th>1</th>
    <td>회원 로그인 기능</td>
</tr>
<tr>
    <th>2</th>
    <td>회원정보 요청 기능</td>
</tr>
<tr>
    <th>3</th>
    <td>회원 로그아웃 기능</td>
</tr>
</table>

## API 스펙
<table>
<tr>
    <th>Type</th><th>Endpoint</th><th>기능</th><th>Request</th><th>Response</th>
</tr>
<tr>
    <td>POST</td>
    <td>/member/login</td>
    <td>회원 로그인</td>
    <td>
    {
    <br/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    "id": "admin",
    <br/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    "password": "1234"
    <br/>
    }
    </td>
    <td>
    {
    <br/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    "accessToken": "token value"
    <br/>
    }
    </td>
</tr>
<tr>
    <td>GET</td>
    <td>/member/me</td>
    <td>회원정보 요청</td>
    <td>
    {
    <br/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    "Authorization": "Bearer ${token}"
    <br/>
    }
    </td>
    <td>
    {
    <br/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    "id": "admin",
    <br/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    "name": "관리자",
    <br/>
    &nbsp;&nbsp;&nbsp;&nbsp;
    "auth": 0
    <br/>
    }
    </td>
</tr>
<tr>
    <td>GET</td>
    <td>/member/logout</td>
    <td>회원 로그아웃</td>
    <td></td>
    <td>200(Status Code OK)</td>
</tr>
</table>