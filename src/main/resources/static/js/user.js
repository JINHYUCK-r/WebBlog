let index = {
    init: function(){
        $("#btn-save").on("click",()=>{ //on 함수의 첫번째 파라미터는 발생될 이벤트, 두번째 파라미터는 해야할 일 설정 
													//function(){}를 쓰지 않고 ()=> {}  화살표 함수를 쓴것은 this를 바인딩하기 위해서  
            this.save();
        });
/*
        $("#btn-login").on("click",()=>{ 
													
            this.login();
        });
*/
    },

  save : function(){
        //alert('user의 save함수 호출됨');	
	let data = {		//각 값을 찾아서 data 오브젝트에 넣음 
		username: $("#username").val(),
		password: $("#password").val(),
		email: $("#email").val()
	};
	//console.log(data);
	
	//ajax 호출은 default가 비동기 호출 
	//	 ajax통신을 이용해서 3개의 데이터를 Json으로 변경하여 insert 요청 
	// ajax 가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해줌 
	// dataType 을 설정해주지 않아도 변환이 가능해짐 
	$.ajax({
		//회원가입 수행요청 
		type:"POST",
		url:"/auth/joinProc",
		data: JSON.stringify(data), //위에서 만든 data는 자바스크립트 객체라서 자바가 이해할수 있도록 json으로 바꾸어 주어야함 // http body 데이터 
		contentType: "application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (MIME)
		dataType:"json" //응답. 요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 string (생긴것이 json이라면 javascript 형태로 바꾸어줌 )
		
	}).done(function(resp){	//resp	에 UserApicontroller의 save함수의 리턴값 1이 담기게 된다. 
		//정상이면 이부분이 실행 
		alert("회원가입이 완료 되었습니다.");
		//console.log(resp);
		location.href = "/" //메인화면으로 이동 
	}).fail(function(error){
		//실패면 이 부분이 실행 
		alert(JSON.stringify(error));
	}); 
    }
/*
 login : function(){
        
	let data = {		
		username: $("#username").val(),
		password: $("#password").val(),
	};
	
	$.ajax({

		type:"POST", //get방식으로 할경우 주소에 id와 password가 남기때문에 위험 
		url:"/api/user/login",
		data: JSON.stringify(data), 
		contentType: "application/json; charset=utf-8", 
		dataType:"json" 
		
	}).done(function(resp){	
		alert("로그인이  완료 되었습니다.");
		location.href = "/" 
	}).fail(function(error){
 
		alert(JSON.stringify(error));
	}); 
    }
*/
}
index.init();