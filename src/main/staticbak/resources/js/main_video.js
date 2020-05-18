var player;
function initVideo() {
	var options = {};
	player = videojs("video-player", options, function onPlayerReady() {
		this.src([{
			type: "application/vnd.apple.mpegurl",
			src: "http://112.74.216.37:8080/hls/hangar2-gimbal-video.m3u8"
		}]);

		this.play();
	});
}

function changeVideoSrc() {
	var val = $("#videoSelect").val();
	switch (val) {
		case "1":
			player.src([{
				type: "application/vnd.apple.mpegurl",
				src: "http://112.74.216.37:8080/hls/hangar2-out-video.m3u8"
			}]);

			player.play();
			break;
		case "2":
			player.src([{
				type: "application/vnd.apple.mpegurl",
				src: "http://112.74.216.37:8080/hls/hangar2-in-video.m3u8"
			}]);

			player.play();
			break;
		case "2":
			player.src([{
				type: "application/vnd.apple.mpegurl",
				src: "http://112.74.216.37:8080/hls/hangar2-gimbal-video.m3u8"
			}]);

			player.play();
			break;
	}
}