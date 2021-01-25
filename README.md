# WebBlog
---
##### com.rjh.blog
 + config
   - auth
     + princlpalDetail : 로그인 사용자의 정보를 저장 
     + principlaDetailService : 로그인 처리 
   - oauth
     + provider : FaceBookUserInfo, FooglerUserInfo, NaverUserInfo, OAuth2UserInfo
      : 각 플랫폼의 정보를 받아와서 가공하고 담는다   
     + PrincipalOAuth2UserService : oauth를 통한 로그인 처리 
    - SecutiryConfig : Security처리. 페이지권한관리.
 + controller
   - api 
     + BoardApiController, UserApiController : RESTful Api를 활용한 각종 데이터 처리 (post, put, delete)
   - BoardController, UserController : GetMapping을 통한 페이지이동 처리 
 + dto : 필요한 데이터를 담기위한 공간 
   - ReplySaveRequestDto
   - ReponseDto
 + handler : 에러처리를 담아 한곳에서 처리하기 위한 공간 
   - GlobalExceptionHandler 
 + model : DB를 생성하고 필요한 정보를 담기위한 공간 
   - Board
   - KakaoProfile : OAuth2라이브러리를 활용하지않고 코딩으로 가입을 진행하기위해 만든 모델
   - OAuthToken : 카카오 로그인시 토큰을 넘겨받기위한 모델 
   - Reply
   - RoleType : enum타입으로 roleType생성시 오류를 막기위해 생성 
   - User
 + repository : JpaRepository를 extends하여 CRUD의 역할을 수행 
   - BoardRepository
   - ReplyRepository
   - UserRepository
 + service
   - BoardService : 글작성, 읽기 등 board와 관련된 각종 역할을 수행 
   - UserService : 회원가입, 수정 등 User와 관련된 각종 역할을 수행 
---
##### resources
+ js : 화면(View)에서 일어난 이벤트를 넘겨받아서 데이터를 가공 및 처리하여 넘겨줌
  - board.js   
  - user.js
---
##### WEB-INF : 부트스트랩을 활용한 화면 구현 
+ board
  - detail : 글 상세보기 화면. 댓글작성 및 보기,삭제 가능 
  - saveForm : 글쓰기 화면. summernote 사용 
  - updateForm : 글 수정 화면 
+ layout : 모든 화면에 Include하여 지속적으로 노출하는 화면 
  - footer
  - header
+ user
  - joinForm : 회원가입 
  - loginForm : 로그인
  - updateForm : 회원정보 수정
+ index

