function initVideo() {
	var options = {};
	var player = videojs("video-player", options, function onPlayerReady() {
		this.src([{
			type: "application/vnd.apple.mpegurl",
			src: "http://112.74.216.37:8080/hls/hangar2-gimbal-video.m3u8"
		}]);

		this.play();
	});
}