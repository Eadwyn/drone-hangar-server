var map;
var markers = [];
var polylines = [];

function initMap() {
	AMapLoader.load({
			key: "e9033faeecc84dcbc08e0ef3b5819b57",
			version: "2.0",
			plugins: ["AMap.Scale"],
		})
		.then((AMap) => {
			map = new AMap.Map("container", {
				zoom: 16,
				center: [113.5975887, 22.7541939],
				layers: [
					new AMap.TileLayer.Satellite(),
					new AMap.TileLayer.RoadNet(),
				],
			});
			map.addControl(new AMap.Scale());
		})
		.catch((e) => {
			console.error(e);
		});
}

function setPosition(lon, lat) {
	if (!map) {
		return;
	}
	var icon = new AMap.Icon({
		size: new AMap.Size(36, 36),
		image: "./resources/images/aircraft.png",
		imageOffset: new AMap.Pixel(0, 0),
		imageSize: new AMap.Size(36, 36),
	});
	var marker = new AMap.Marker({
		position: new AMap.LngLat(lon, lat),
		offset: new AMap.Pixel(-10, -10),
		icon: icon,
	});
	map.remove(markers);
	markers.length = 0;
	markers.push(marker);
	map.add(marker);
}