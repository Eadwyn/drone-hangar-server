function login() {
	if ($("#username").val().length != 6) {
		alert("账号应为长度为6的字符");
		return;
	}
	if ($("#password").val().length != 6) {
		alert("密码应为长度为6的字符");
		return;
	}
	Ajax.post(
		"/login", {
			username: $("#username").val(),
			password: $("#password").val(),
		},
		function (response) {
			if (response.code == "0") {
				$("#header .connection_status").html("已连接");
			} else {
				$("#header .connection_status").html("未连接");
				alert(response.message);
			}
		}
	);
}

$(function () {
	initMap();
	initVideo();
	initRefreshData();

	$("#btnLogin").bind("click", login);
	$("#wayPoint").bind("change", loadWayPoint);
	$("#btnTakeOff").bind("click", takeOff);
	$("#btnLanding").bind("click", landing);
	$("#btnReturn").bind("click", returnHome);
	$("#btnPause").bind("click", pause);
	$("#btnResume").bind("click", resume);
	$("#btnAuto").bind("click", auto);
	$("#btnCharger").bind("click", charger);
	$("#btnHangarStart").bind("click", hangarStart);
	$("#btnHangerStop").bind("click", hangerStop);
	$("#btnHangarSleep").bind("click", hangarSleep);
});