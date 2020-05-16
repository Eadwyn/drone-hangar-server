function loadWayPoint() {
	Ajax.post(
		"/aircrafts/loadWayPoint", {
			code: $("#wayPoint").val(),
		},
		function (response) {
			if (response.code == "0") {} else {
				alert(response.message);
			}
		}
	);
}

function takeOff() {
	Ajax.post("/aircrafts/takeOff", {});
}

function landing() {
	Ajax.post("/aircrafts/landing", {});
}

function returnHome() {
	Ajax.post("/aircrafts/returnHone", {});
}

function pause() {
	Ajax.post("/aircrafts/pause", {});
}

function resume() {
	Ajax.post("/aircrafts/resume", {});
}

function auto() {
	Ajax.post("/aircrafts/auto", {});
}

function charger() {
	Ajax.post("/aircrafts/charger", {});
}

function hangarStart() {
	Ajax.post("/aircrafts/hangarStart", {});
}

function hangerStop() {
	Ajax.post("/aircrafts/hangerStop", {});
}

function hangarSleep() {
	Ajax.post("/aircrafts/hangarSleep", {});
}