function toggleEye(){
	const pwd=document.getElementById("password");
	const eye=document.getElementById("eye");
	if(pwd.type==="password"){
		pwd.type="text";
		eye.classList.replace("bi-eye-slash","bi-eye");
	}else{
		pwd.type="password";
		eye.classList.replace("bi-eye","bi-eye-slash");
	}
}